MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.usergroup` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				userGroup.classPK,
				userGroup.dataSourceId,
				userGroup.modifiedDate,
				userGroup.projectId,
				userGroup.type,
				userGroup.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(userGroup.fields)
					WHERE
						name = 'name'
				) AS name,
				ROW_NUMBER() OVER (
					PARTITION BY
						userGroup.projectId, userGroup.dataSourceId, userGroup.classPK
					ORDER BY
						userGroup.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(userGroup.projectId, '#', userGroup.dataSourceId, '#', userGroup.classPK)
					)
				) AS sha256HexId,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(userGroup.fields)
					WHERE
						name = 'userGroupId'
				) AS userGroupId
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS userGroup
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
					) AS userGroupId,
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
				analyticsDeleteMessage.className = userGroup.type AND
				analyticsDeleteMessage.dataSourceId = userGroup.dataSourceId AND
				analyticsDeleteMessage.projectId = userGroup.projectId AND
				analyticsDeleteMessage.userGroupId = SAFE_CAST(userGroup.classPK AS INT64)
			WHERE
				(
					analyticsDeleteMessage.userGroupId IS NOT NULL OR
					userGroup.uploadDate >=
						{% if '{{ params['uploadType'] }}' == 'FULL' %}
							'1970-01-01T00:00:00'
						{% else %}
							CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
						{% endif %}
				) AND
				userGroup.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				userGroup.type = 'com.liferay.portal.kernel.model.UserGroup'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	SAFE_CAST(staging.classPK AS INT64) = replica.userGroupId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.modifiedDate = staging.modifiedDate,
		replica.name = staging.name
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`dataSourceId`,
		`id`,
		`modifiedDate`,
		`name`,
		`projectId`,
		`userGroupId`
	)
	VALUES (
		staging.dataSourceId,
		staging.sha256HexId,
		staging.modifiedDate,
		staging.name,
		staging.projectId,
		staging.userGroupId
	)