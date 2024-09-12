MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.organization` AS replica
USING
	(
		SELECT
			*
		FROM (
			SELECT
				analyticsDeleteMessage.deleted,
				organization.classPK,
				organization.dataSourceId,
				organization.modifiedDate,
				organization.projectId,
				organization.type,
				organization.uploadDate,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(organization.fields)
					WHERE
						name = 'name'
				) AS name,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(organization.fields)
					WHERE
						name = 'organizationId'
				) AS organizationId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(organization.fields)
					WHERE
						name = 'type'
				) AS organizationType,
				(
					SELECT
						SAFE_CAST(value AS INT64)
					FROM
						UNNEST(organization.fields)
					WHERE
						name = 'parentOrganizationId'
				) AS parentOrganizationId,
				ROW_NUMBER() OVER (
					PARTITION BY
						organization.projectId, organization.dataSourceId, organization.classPK
					ORDER BY
						organization.modifiedDate DESC
				) AS rowNumber,
				TO_HEX(
					SHA256(
						CONCAT(organization.projectId, '#', organization.dataSourceId, '#', organization.classPK)
					)
				) AS sha256HexId,
				(
					SELECT
						SAFE_CAST(value AS STRING)
					FROM
						UNNEST(organization.fields)
					WHERE
						name = 'treePath'
				) AS treePath
			FROM
				`{{ dag.default_args['ac_project_id'] }}.dxpentity` AS organization
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
					) AS organizationId,
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
				analyticsDeleteMessage.className = organization.type AND
				analyticsDeleteMessage.dataSourceId = organization.dataSourceId AND
				analyticsDeleteMessage.organizationId = SAFE_CAST(organization.classPK AS INT64) AND
				analyticsDeleteMessage.projectId = organization.projectId
			WHERE
				(
					analyticsDeleteMessage.organizationId IS NOT NULL OR
					organization.uploadDate >=
						{% if '{{ params['uploadType'] }}' == 'FULL' %}
							'1970-01-01T00:00:00'
						{% else %}
							CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
						{% endif %}
				) AND
				organization.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
				organization.type = 'com.liferay.portal.kernel.model.Organization'
		)
		WHERE
			rowNumber = 1
	) AS staging
ON
	SAFE_CAST(staging.classPK AS INT64) = replica.organizationId AND
	staging.dataSourceId = replica.dataSourceId AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL THEN
	UPDATE SET
		replica.modifiedDate = staging.modifiedDate,
		replica.name = staging.name,
		replica.type = staging.organizationType,
		replica.parentOrganizationId = staging.parentOrganizationId,
		replica.treePath = staging.treePath
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`dataSourceId`,
		`id`,
		`modifiedDate`,
		`name`,
		`organizationId`,
		`parentOrganizationId`,
		`projectId`,
		`treePath`,
		`type`
	)
	VALUES (
		staging.dataSourceId,
		staging.sha256HexId,
		staging.modifiedDate,
		staging.name,
		staging.organizationId,
		staging.parentOrganizationId,
		staging.projectId,
		staging.treePath,
		staging.organizationType
	)