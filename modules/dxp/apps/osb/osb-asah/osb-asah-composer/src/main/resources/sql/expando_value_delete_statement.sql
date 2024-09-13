MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.expandovalue` AS replica
USING
	(
		SELECT
			classPK,
			dataSourceId,
			modifiedDate,
			projectId,
			type,
			uploadDate,
			(
				SELECT
					SAFE_CAST(value AS STRING)
				FROM
					UNNEST(fields)
				WHERE
					name = 'classPK'
			) AS columnId,
			(
				SELECT
					SAFE_CAST(value AS STRING)
				FROM
					UNNEST(fields)
				WHERE
					name = 'className'
			) AS className
		FROM
			`{{ dag.default_args['ac_project_id'] }}.dxpentity`
		WHERE
			dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
			type = 'com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage' AND
			uploadDate >=
				{% if params.uploadType == 'FULL' %}
					'1970-01-01T00:00:00'
				{% else %}
					CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				{% endif %}
	) AS staging
ON
	staging.className = 'com.liferay.expando.kernel.model.ExpandoColumn' AND
	staging.columnId = replica.columnId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED THEN
	DELETE