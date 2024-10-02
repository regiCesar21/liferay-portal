MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.group` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				group_.classPK,
				group_.dataSourceId,
				group_.modifiedDate,
				group_.projectId,
				group_.type,
				group_.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(group_.fields)
					WHERE
						name = 'groupId'
				) AS groupId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(group_.fields)
					WHERE
						name = 'name'
				) AS name,
				ROW_NUMBER() OVER (
					PARTITION BY
						group_.projectId, group_.dataSourceId, group_.classPK
					ORDER BY
						group_.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(group_.projectId, '#', group_.dataSourceId, '#', group_.classPK)
					)
				) AS sha256HexId
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS group_
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
					) AS groupId,
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
				analyticsDeleteMessage.className = group_.type AND
				analyticsDeleteMessage.dataSourceId = group_.dataSourceId AND
				analyticsDeleteMessage.groupId = SAFE_CAST(group_.classPK AS INT64) AND
				analyticsDeleteMessage.projectId = group_.projectId
			WHERE
				(
					analyticsDeleteMessage.groupId IS NOT NULL OR
					group_.uploadDate = CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				) AND
				group_.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				group_.type = 'com.liferay.portal.kernel.model.Group'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	SAFE_CAST(staging.classPK AS INT64) = replica.groupId AND
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
		`groupId`,
		`id`,
		`modifiedDate`,
		`name`,
		`projectId`
	)
	VALUES (
		staging.dataSourceId,
		staging.groupId,
		staging.sha256HexId,
		staging.modifiedDate,
		staging.name,
		staging.projectId
	)