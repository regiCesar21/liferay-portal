MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.accountentry` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				accountEntry.classPK,
				accountEntry.dataSourceId,
				accountEntry.modifiedDate,
				accountEntry.projectId,
				accountEntry.type,
				accountEntry.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'accountEntryId'
				) AS accountEntryId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'type'
				) AS accountEntryType,
				(
					SELECT
						SAFE_CAST(TIMESTAMP_MILLIS(CAST(value AS INT64)) AS TIMESTAMP)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'createDate'
				) AS createDate,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'defaultCPaymentMethodKey'
				) AS defaultCPaymentMethodKey,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'description'
				) AS description,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'domains'
				) AS domains,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'emailAddress'
				) AS emailAddress,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'logoId'
				) AS logoId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'name'
				) AS name,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'parentAccountEntryId'
				) AS parentAccountEntryId,
				ROW_NUMBER() OVER (
					PARTITION BY
						accountEntry.projectId, accountEntry.dataSourceId, accountEntry.classPK
					ORDER BY
						accountEntry.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(accountEntry.projectId, '#', accountEntry.dataSourceId, '#', accountEntry.classPK)
					)
				) AS sha256HexId,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'status'
				) AS status,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'taxExemptionCode'
				) AS taxExemptionCode,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(accountEntry.fields)
					WHERE
						name = 'taxIdNumber'
				) AS taxIdNumber
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS accountEntry
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
					) AS accountEntryId,
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
				analyticsDeleteMessage.accountEntryId = SAFE_CAST(accountEntry.classPK AS INT64) AND
				analyticsDeleteMessage.className = accountEntry.type
			WHERE
				(
					analyticsDeleteMessage.accountEntryId IS NOT NULL OR
					accountEntry.uploadDate = CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				) AND
				accountEntry.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				accountEntry.type = 'com.liferay.account.model.AccountEntry'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	SAFE_CAST(staging.classPK AS INT64) = replica.accountEntryId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.defaultCPaymentMethodKey = staging.defaultCPaymentMethodKey,
		replica.description = staging.description,
		replica.domains = staging.domains,
		replica.emailAddress = staging.emailAddress,
		replica.logoId = staging.logoId,
		replica.modifiedDate = staging.modifiedDate,
		replica.name = staging.name,
		replica.parentAccountEntryId = staging.parentAccountEntryId,
		replica.status = staging.status,
		replica.taxExemptionCode = staging.taxExemptionCode,
		replica.taxIdNumber = staging.taxIdNumber,
		replica.type = staging.accountEntryType
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY SOURCE AND '{{ params['uploadType'] }}' = 'FULL' THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`accountEntryId`,
		`createDate`,
		`dataSourceId`,
		`defaultCPaymentMethodKey`,
		`description`,
		`domains`,
		`emailAddress`,
		`id`,
		`logoId`,
		`modifiedDate`,
		`name`,
		`parentAccountEntryId`,
		`projectId`,
		`taxExemptionCode`,
		`taxIdNumber`,
		`type`,
		`status`
	)
	VALUES (
		staging.accountEntryId,
		staging.createDate,
		staging.dataSourceId,
		staging.defaultCPaymentMethodKey,
		staging.description,
		staging.domains,
		staging.emailAddress,
		staging.sha256HexId,
		staging.logoId,
		staging.modifiedDate,
		staging.name,
		staging.parentAccountEntryId,
		staging.projectId,
		staging.taxExemptionCode,
		staging.taxIdNumber,
		staging.accountEntryType,
		staging.status
	)