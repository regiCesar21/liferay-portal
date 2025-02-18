EXPORT DATA
	OPTIONS (
		field_delimiter = ';',
		format = 'CSV',
		header = false,
		overwrite = true,
		uri = 'gs://{{dag.default_args['google_project_id']}}-data-replica/{{dag.default_args['ac_project_id']}}/individual-activity/{{ts}}/*.csv'
	)
AS (
	SELECT
		Event.id, applicationId, channelId, context, eventDate, eventId,
		TO_JSON_STRING(properties), Individual.id AS individualId
	FROM
		`{{dag.default_args['ac_project_id']}}.event` Event
	INNER JOIN
		`{{dag.default_args['ac_project_id']}}.identity` Identity
	ON
		Event.userId = Identity.id
	INNER JOIN
		`{{dag.default_args['ac_project_id']}}.individual` Individual
	ON
		Identity.individualId = Individual.id
	WHERE
		DATE(eventDate, '{{dag.default_args['ac_project_time_zone_id']}}') = DATE(TIMESTAMP('{{data_interval_start.to_datetime_string()}}'), '{{dag.default_args['ac_project_time_zone_id']}}')
);