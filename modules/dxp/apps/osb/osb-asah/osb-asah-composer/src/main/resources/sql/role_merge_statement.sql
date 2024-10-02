MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.role` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				role.classPK,
				role.dataSourceId,
				role.modifiedDate,
				role.projectId,
				role.type,
				role.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(role.fields)
					WHERE
						name = 'name'
				) AS name,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(role.fields)
					WHERE
						name = 'roleId'
				) AS roleId,
				ROW_NUMBER() OVER (
					PARTITION BY
						role.projectId, role.dataSourceId, role.classPK
					ORDER BY
						role.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(role.projectId, '#', role.dataSourceId, '#', role.classPK)
					)
				) AS sha256HexId
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS role
			LEFT JOIN (
				SELECT
					*,
					TRUE AS deleted,
					(
						SELECT
							SAFE_CAST(value AS INT64)
						FROM
							UNNEST(fields)
						WHERE
							name = 'classPK'
					) AS roleId,
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
				analyticsDeleteMessage.className = role.type AND
				analyticsDeleteMessage.dataSourceId = role.dataSourceId AND
				analyticsDeleteMessage.projectId = role.projectId AND
				analyticsDeleteMessage.roleId = SAFE_CAST(role.classPK AS INT64)
			WHERE
				(
					analyticsDeleteMessage.roleId IS NOT NULL OR
					role.uploadDate = CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				) AND
				role.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				role.type = 'com.liferay.portal.kernel.model.Role'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	SAFE_CAST(staging.classPK AS INT64) = replica.roleId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.modifiedDate = staging.modifiedDate,
		replica.name = staging.name
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY SOURCE AND '{{ params['uploadType'] }}' = 'FULL' THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`dataSourceId`,
		`id`,
		`modifiedDate`,
		`name`,
		`projectId`,
		`roleId`
	)
	VALUES (
		staging.dataSourceId,
		staging.sha256HexId,
		staging.modifiedDate,
		staging.name,
		staging.projectId,
		staging.roleId
	)