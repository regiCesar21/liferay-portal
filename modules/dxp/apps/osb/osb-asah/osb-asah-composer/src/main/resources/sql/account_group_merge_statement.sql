MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.accountgroup` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				accountGroup.classPK,
				accountGroup.dataSourceId,
				accountGroup.modifiedDate,
				accountGroup.projectId,
				accountGroup.type,
				accountGroup.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(accountGroup.fields)
					WHERE
						name = 'accountGroupId'
				) AS accountGroupId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountGroup.fields)
					WHERE
						name = 'type'
				) AS accountGroupType,
				(
					SELECT
						SAFE_CAST(TIMESTAMP_MILLIS(CAST(value AS INT64)) AS TIMESTAMP)
					FROM
						UNNEST(accountGroup.fields)
					WHERE
						name = 'createDate'
				) AS createDate,
				(
					SELECT
						SAFE_CAST(CAST(value AS STRING) AS BOOL)
					FROM
						UNNEST(accountGroup.fields)
					WHERE
						name = 'defaultAccountGroup'
				) AS defaultAccountGroup,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountGroup.fields)
					WHERE
						name = 'description'
				) AS description,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountGroup.fields)
					WHERE
						name = 'name'
				) AS name,
				ROW_NUMBER() OVER (
					PARTITION BY
						accountGroup.projectId, accountGroup.dataSourceId, accountGroup.classPK
					ORDER BY
						accountGroup.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(accountGroup.projectId, '#', accountGroup.dataSourceId, '#', accountGroup.classPK)
					)
				) AS sha256HexId
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS accountGroup
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
					) AS accountGroupId,
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
				analyticsDeleteMessage.accountGroupId = SAFE_CAST(accountGroup.classPK AS INT64) AND
				analyticsDeleteMessage.className = accountGroup.type
			WHERE
				(
					analyticsDeleteMessage.accountGroupId IS NOT NULL OR
					accountGroup.uploadDate = CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				) AND
				accountGroup.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				accountGroup.type = 'com.liferay.account.model.AccountGroup'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	SAFE_CAST(staging.classPK AS INT64) = replica.accountGroupId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.defaultAccountGroup = staging.defaultAccountGroup,
		replica.description = staging.description,
		replica.modifiedDate = staging.modifiedDate,
		replica.name = staging.name,
		replica.type = staging.accountGroupType
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY SOURCE AND '{{ params['uploadType'] }}' = 'FULL' THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`accountGroupId`,
		`createDate`,
		`dataSourceId`,
		`defaultAccountGroup`,
		`description`,
		`id`,
		`modifiedDate`,
		`name`,
		`projectId`,
		`type`
	)
	VALUES (
		staging.accountGroupId,
		staging.createDate,
		staging.dataSourceId,
		staging.defaultAccountGroup,
		staging.description,
		staging.sha256HexId,
		staging.modifiedDate,
		staging.name,
		staging.projectId,
		staging.accountGroupType
	)