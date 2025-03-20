EXPORT DATA
	OPTIONS (
		field_delimiter = ';',
		format = 'CSV',
		header = false,
		overwrite = true,
		uri = 'gs://{{dag.default_args['google_project_id']}}-data-replica/{{dag.default_args['ac_project_id']}}/{{ts}}/individual/*.csv'
	)
AS (
	SELECT
		id, emailAddress, JSON_OBJECT(ARRAY_AGG(fields.name), ARRAY_AGG(fields.value)) AS fields, suppressed
	FROM
		`{{ dag.default_args['ac_project_id'] }}.individual`, UNNEST(fields) AS fields
	GROUP BY
		id, emailAddress, suppressed
);