INSERT INTO `{{ dag.default_args['ac_project_id'] }}.dxpentity` (
	classPK,
	dataSourceId,
	expandoFields,
	fields,
	id,
	modifiedDate,
	projectId,
	type,
	uploadDate,
	uploadType
)

SELECT
	id AS classPK,
	CAST('{{ params['dataSourceId'] }}' AS INTEGER) AS dataSourceId,
	expandoFields,
	fields,
	id,
	modifiedDate,
	'{{ dag.default_args['ac_project_id'] }}' AS projectId,
	type,
	CAST('{{ params['uploadDate'] }}' AS TIMESTAMP) AS uploadDate,
	'{{ params['uploadType'] }}' AS uploadType
FROM
	(
		SELECT
			(
				SELECT
					TRUE
				FROM
					UNNEST(fields)
				JOIN
					`{{ dag.default_args['ac_project_id'] }}.suppression` AS Suppression
				ON
					value = Suppression.emailAddress
				WHERE
					NAME = 'emailAddress' AND
					TYPE = 'com.liferay.portal.kernel.model.User'
			) AS suppressed,
			*
		FROM
			`{{ dag.default_args['ac_project_id'] }}.dxpentity_external_{{ ts_nodash }}`
	) TMP
WHERE
	suppressed IS NULL