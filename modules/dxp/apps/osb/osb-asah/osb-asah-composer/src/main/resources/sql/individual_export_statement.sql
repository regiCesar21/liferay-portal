EXPORT DATA
	OPTIONS (
		field_delimiter = ';',
		format = 'CSV',
		header = false,
		overwrite = true,
		uri = 'gs://{{dag.default_args['google_project_id']}}-data-replica/{{dag.default_args['ac_project_id']}}/individual/{{ts}}/*.csv'
	)
AS (
	SELECT
		id, emailAddress, TO_JSON_STRING(fields) AS fields
	FROM
		`{{ dag.default_args['ac_project_id'] }}.individual`
);