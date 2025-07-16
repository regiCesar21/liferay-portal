MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.objectentrydaily` AS replica
USING
	(
		SELECT
			canonicalUrl,
			dataSourceId,
			SUM(downloads) AS downloads,
			TIMESTAMP_TRUNC(eventDate, DAY, '{{ dag.default_args['ac_project_time_zone_id'] }}') AS eventDate,
			externalReferenceCode,
			groupId,
			SUM(impressions) AS impressions,
			userId,
			SUM(views) AS views
		FROM
			`{{ dag.default_args['ac_project_id'] }}.objectentry_hourly`(TIMESTAMP(DATE(TIMESTAMP('{{ data_interval_end.to_datetime_string() }}'), '{{ dag.default_args['ac_project_time_zone_id'] }}'), '{{ dag.default_args['ac_project_time_zone_id'] }}'), TIMESTAMP(DATE(TIMESTAMP('{{ data_interval_start.to_datetime_string() }}'), '{{ dag.default_args['ac_project_time_zone_id'] }}'), '{{ dag.default_args['ac_project_time_zone_id'] }}'))
		WHERE
			DATE(eventDate, '{{ dag.default_args['ac_project_time_zone_id'] }}') = DATE(TIMESTAMP('{{ data_interval_start.to_datetime_string() }}'), '{{ dag.default_args['ac_project_time_zone_id'] }}')
		GROUP BY
			canonicalUrl, dataSourceId, eventDate, externalReferenceCode, groupId, userId
	) AS staging
ON (
	staging.dataSourceId = replica.dataSourceId AND
	DATE(replica.eventDate, '{{ dag.default_args['ac_project_time_zone_id'] }}') = DATE(TIMESTAMP('{{ data_interval_start.to_datetime_string() }}'), '{{ dag.default_args['ac_project_time_zone_id'] }}') AND
	DATE(staging.eventDate) = DATE(replica.eventDate) AND
	staging.externalReferenceCode = replica.externalReferenceCode AND
	staging.groupId = replica.groupId AND
	staging.userId = replica.userId
)

WHEN NOT MATCHED THEN
	INSERT (
		`canonicalUrl`,
		`dataSourceId`,
		`downloads`,
		`eventDate`,
		`externalReferenceCode`,
		`groupId`,
		`impressions`,
		`userId`,
		`views`
	)
	VALUES (
		staging.canonicalUrl,
		staging.dataSourceId,
		staging.downloads,
		staging.eventDate,
		staging.externalReferenceCode,
		staging.groupId,
		staging.impressions,
		staging.userId,
		staging.views
	)