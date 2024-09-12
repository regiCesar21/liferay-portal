MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.expandocolumn` AS replica
USING
	(
		SELECT
			className,
			classPK,
			columnId,
			dataSourceId,
			dataType,
			deleted,
			displayType,
			modifiedDate,
			REPLACE(name, SUBSTR(name, STRPOS(name, CONCAT('-', dataType))), '') AS name,
			projectId,
			rowNumber,
			sha256HexId,
			type,
			uploadDate,
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				expandoColumn.classPK,
				expandoColumn.dataSourceId,
				expandoColumn.modifiedDate,
				expandoColumn.projectId,
				expandoColumn.type,
				expandoColumn.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(expandoColumn.fields)
					WHERE
						name = 'className'
				) AS className,
				(
					SELECT
						CASE WHEN SAFE_CAST(value AS STRING) = '' THEN NULL ELSE SAFE_CAST(value AS STRING) END
					FROM
						UNNEST(expandoColumn.fields)
					WHERE
						name = 'columnId'
				) AS columnId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(expandoColumn.fields)
					WHERE
						name = 'dataType'
				) AS dataType,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(expandoColumn.fields)
					WHERE
						name = 'displayType'
				) AS displayType,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(expandoColumn.fields)
					WHERE
						name = 'name'
				) AS name,
				ROW_NUMBER() OVER (
					PARTITION BY
						expandoColumn.projectId, expandoColumn.dataSourceId, expandoColumn.classPK
					ORDER BY
						expandoColumn.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(expandoColumn.projectId, '#', expandoColumn.dataSourceId, '#', expandoColumn.classPK)
					)
				) AS sha256HexId
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS expandoColumn
			LEFT JOIN (
				SELECT
					*,
					TRUE AS deleted,
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
					type = 'com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage'
			) AS analyticsDeleteMessage
			ON
				analyticsDeleteMessage.className = expandoColumn.type AND
				analyticsDeleteMessage.columnId = expandoColumn.classPK AND
				analyticsDeleteMessage.dataSourceId = expandoColumn.dataSourceId AND
				analyticsDeleteMessage.projectId = expandoColumn.projectId
			WHERE
				(
					analyticsDeleteMessage.columnId IS NOT NULL OR
					expandoColumn.uploadDate >=
						{% if '{{ params['uploadType'] }}' == 'FULL' %}
							'1970-01-01T00:00:00'
						{% else %}
							CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
						{% endif %}
				) AND
				expandoColumn.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				expandoColumn.type = 'com.liferay.expando.kernel.model.ExpandoColumn'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	(staging.columnId = replica.columnId OR replica.columnId = CONCAT(staging.name, '-', staging.dataType)) AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.className = staging.className,
		replica.columnId = COALESCE(staging.columnId, CONCAT(staging.name, '-', staging.dataType)),
		replica.dataType = staging.dataType,
		replica.displayType = staging.displayType,
		replica.modifiedDate = staging.modifiedDate,
		replica.name = staging.name
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`className`,
		`columnId`,
		`dataSourceId`,
		`dataType`,
		`displayType`,
		`id`,
		`modifiedDate`,
		`name`,
		`projectId`
	)
	VALUES (
		staging.className,
		COALESCE(staging.columnId, CONCAT(staging.name, '-', staging.dataType)),
		staging.dataSourceId,
		staging.dataType,
		staging.displayType,
		staging.sha256HexId,
		staging.modifiedDate,
		staging.name,
		staging.projectId
	)