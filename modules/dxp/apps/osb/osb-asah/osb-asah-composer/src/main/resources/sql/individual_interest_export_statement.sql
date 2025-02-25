EXPORT DATA
	OPTIONS (
		field_delimiter = ';',
		format = 'CSV',
		header = false,
		overwrite = true,
		uri = 'gs://{{dag.default_args['google_project_id']}}-data-replica/{{dag.default_args['ac_project_id']}}/individual-interest/{{ts}}/*.csv'
	)
AS (
	SELECT
		channelId, identityId, individualId, interested, interestScore, keyword, recordedDate
	FROM
		`{{dag.default_args['ac_project_id']}}.identityinterestscore` IdentityInterestScore
	INNER JOIN
		`{{dag.default_args['ac_project_id']}}.identity` Identity
	ON
		IdentityInterestScore.identityId = Identity.id
	WHERE
		DATE(CAST(recordedDate AS TIMESTAMP), '{{dag.default_args['ac_project_time_zone_id']}}') = DATE(TIMESTAMP('{{data_interval_start.to_datetime_string()}}'), '{{dag.default_args['ac_project_time_zone_id']}}')
);