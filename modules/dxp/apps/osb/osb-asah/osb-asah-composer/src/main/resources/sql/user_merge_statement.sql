MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.user` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				user.classPK,
				user.dataSourceId,
				user.modifiedDate,
				user.projectId,
				user.type,
				user.uploadDate,
				(
					SELECT
						SAFE_CAST((
							SELECT
								DISTINCT(value)
							FROM
								UNNEST(user.fields)
							WHERE name = 'emailAddress'
						) AS STRING)
				) AS emailAddress,
				user.fields AS fields,
				(
					SELECT
						SAFE_CAST((
							SELECT
								DISTINCT(value)
							FROM
								UNNEST(user.fields)
							WHERE name = 'firstName'
						) AS STRING)
				) AS firstName,
				(
					SELECT
						SAFE_CAST((
							SELECT
								DISTINCT(value)
							FROM
								UNNEST(user.fields)
							WHERE
								name = 'lastName'
						) AS STRING)
				) AS lastName,
				(
					SELECT
						SAFE_CAST((
							SELECT
								DISTINCT(value)
							FROM
								UNNEST(user.fields)
							WHERE
								name = 'middleName'
						) AS STRING)
				) AS middleName,
				ROW_NUMBER() OVER (
					PARTITION BY
						user.projectId, user.dataSourceId, user.classPK
					ORDER BY
						user.modifiedDate DESC
				) AS rowNumber,
				(
					SELECT
						SAFE_CAST((
							SELECT
								DISTINCT(value)
							FROM
								UNNEST(user.fields)
							WHERE name = 'screenName'
						) AS STRING)
				) AS screenName,
				TO_HEX(
					SHA256(
						CONCAT(user.projectId, '#', user.dataSourceId, '#', user.classPK)
					)
				) AS sha256HexId,
				SAFE_CAST(user.classPK AS INT64) AS dxpUserId,
				(
					SELECT
						SAFE_CAST((
							SELECT
								DISTINCT(value)
							FROM
								UNNEST(user.fields)
							WHERE name = 'userName'
						) AS STRING)
				) AS userName,
				(
					SELECT
						SAFE_CAST((
							SELECT
								DISTINCT(value)
							FROM
								UNNEST(user.fields)
							WHERE name = 'uuid'
						) AS STRING)
				) AS uuid
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS user
			LEFT JOIN (
				SELECT
					dataSourceId,
					TRUE AS deleted,
					projectId,
					(
						SELECT
							SAFE_CAST(value AS INT64)
						FROM
							UNNEST(fields)
						WHERE
							name = 'classPK'
					) AS userId,
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
				analyticsDeleteMessage.className = user.type AND
				analyticsDeleteMessage.dataSourceId = user.dataSourceId AND
				analyticsDeleteMessage.projectId = user.projectId AND
				analyticsDeleteMessage.userId = SAFE_CAST(user.classPK AS INT64)
			WHERE
				(
					analyticsDeleteMessage.userId IS NOT NULL OR
					user.uploadDate = CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				) AND
				user.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				user.type = 'com.liferay.portal.kernel.model.User'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	staging.dxpUserId = replica.dxpUserId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.emailAddress = LOWER(staging.emailAddress),
		replica.fields = staging.fields,
		replica.firstName = staging.firstName,
		replica.individualId = TO_HEX(SHA256(LOWER(staging.emailAddress))),
		replica.lastName = staging.lastName,
		replica.middleName = staging.middleName,
		replica.modifiedDate = staging.modifiedDate,
		replica.screenName = staging.screenName,
		replica.userName = staging.userName,
		replica.uuid = COALESCE(staging.uuid, '')
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY SOURCE AND '{{ params['uploadType'] }}' = 'FULL' THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`dataSourceId`,
		`dxpUserId`,
		`emailAddress`,
		`fields`,
		`firstName`,
		`id`,
		`individualId`,
		`lastName`,
		`middleName`,
		`modifiedDate`,
		`projectId`,
		`screenName`,
		`userName`,
		`uuid`
	)
	VALUES (
		staging.dataSourceId,
		staging.dxpUserId,
		LOWER(staging.emailAddress),
		staging.fields,
		staging.firstName,
		staging.sha256HexId,
		TO_HEX(SHA256(LOWER(staging.emailAddress))),
		staging.lastName,
		staging.middleName,
		staging.modifiedDate,
		staging.projectId,
		staging.screenName,
		staging.userName,
		COALESCE(staging.uuid, '')
	)