MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.team` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				team.classPK,
				team.dataSourceId,
				team.modifiedDate,
				team.projectId,
				team.type,
				team.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(team.fields)
					WHERE
						name = 'groupId'
				) AS groupId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(team.fields)
					WHERE
						name = 'name'
				) AS name,
				ROW_NUMBER() OVER (
					PARTITION BY
						team.projectId, team.dataSourceId, team.classPK
					ORDER BY
						team.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(team.projectId, '#', team.dataSourceId, '#', team.classPK)
					)
				) AS sha256HexId,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(team.fields)
					WHERE
						name = 'teamId'
				) AS teamId
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS team
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
					) AS teamId,
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
				analyticsDeleteMessage.className = team.type AND
				analyticsDeleteMessage.dataSourceId = team.dataSourceId AND
				analyticsDeleteMessage.projectId = team.projectId AND
				analyticsDeleteMessage.teamId = SAFE_CAST(team.classPK AS INT64)
			WHERE
				(
					analyticsDeleteMessage.teamId IS NOT NULL OR
					team.uploadDate = CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				) AND
				team.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				team.type = 'com.liferay.portal.kernel.model.Team'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	SAFE_CAST(staging.classPK AS INT64) = replica.teamId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.groupId = staging.groupId,
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
		`projectId`,
		`teamId`
	)
	VALUES (
		staging.dataSourceId,
		staging.groupId,
		staging.sha256HexId,
		staging.modifiedDate,
		staging.name,
		staging.projectId,
		staging.teamId
	)