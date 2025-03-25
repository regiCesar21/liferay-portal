EXPORT DATA
	OPTIONS (
		field_delimiter = ';',
		format = 'CSV',
		header = false,
		overwrite = true,
		uri = 'gs://{{dag.default_args['google_project_id']}}-data-replica/{{dag.default_args['ac_project_id']}}/{{ts}}/individual-activity/*.csv'
	)
AS (
	WITH EventProperties AS (
		SELECT
			Event.id AS id,
			JSON_OBJECT(ARRAY_AGG(properties.name), ARRAY_AGG(properties.value)) AS properties
		FROM
			`{{dag.default_args['ac_project_id']}}.event` Event,
			UNNEST(properties) AS properties
		GROUP BY
			id
	)
	SELECT
		Event.id AS id, applicationId, channelId, context, eventDate, eventId, Individual.id AS individualId,
		COALESCE(EventProperties.properties, JSON '{}') as properties
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
	LEFT JOIN
		EventProperties
	ON
		Event.id = EventProperties.id
	WHERE
		DATE(eventDate, '{{dag.default_args['ac_project_time_zone_id']}}') = DATE(TIMESTAMP('{{data_interval_start.to_datetime_string()}}'), '{{dag.default_args['ac_project_time_zone_id']}}')
);