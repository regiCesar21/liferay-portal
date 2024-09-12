MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.product` AS replica
USING
	(
		SELECT
			analyticsDeleteMessage.deleted,
			product.*
		FROM (
			SELECT
				*
			FROM (
				SELECT
					*,
					ROW_NUMBER() OVER (
						PARTITION BY
							channelId, id
						ORDER BY
							uploadDate DESC
					) AS rowNumber
				FROM
					`{{ dag.default_args['ac_project_id'] }}.product_raw`
			)
			WHERE
				rowNumber = 1
		) AS product
		LEFT JOIN (
			SELECT
				*
			FROM (
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
					) AS classPK,
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
			)
			WHERE
				className = 'com.liferay.commerce.product.model.CPDefinition'
		) AS analyticsDeleteMessage
		ON
			product.datasourceId = analyticsDeleteMessage.datasourceId AND
			product.id = analyticsDeleteMessage.classPK AND
			product.projectId = analyticsDeleteMessage.projectId
		WHERE
			product.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
			product.uploadDate >=
				{% if '{{ params['uploadType'] }}' == 'FULL' %}
					'1970-01-01T00:00:00'
				{% else %}
					CAST('{{ params['uploadDate'] }}' AS TIMESTAMP)
				{% endif %}
		) AS staging
ON
	staging.dataSourceId = replica.dataSourceId AND
	staging.id = replica.id AND
	staging.projectId = replica.projectId
WHEN MATCHED AND staging.deleted IS NULL AND staging.modifiedDate > replica.modifiedDate THEN
	UPDATE SET
		replica.catalogId = staging.catalogId,
		replica.categoryIds = staging.categoryIds,
		replica.channelId = staging.channelId,
		replica.description = staging.description,
		replica.displayDate = staging.displayDate,
		replica.expirationDate = staging.expirationDate,
		replica.externalReferenceCode = staging.externalReferenceCode,
		replica.metaDescription = staging.metaDescription,
		replica.metaKeyword = staging.metaKeyword,
		replica.metaTitle = staging.metaTitle,
		replica.modifiedDate = staging.modifiedDate,
		replica.name = staging.name,
		replica.productChannelIds = staging.productChannelIds,
		replica.productId = staging.productId,
		replica.productOptions = staging.productOptions,
		replica.productSpecifications = staging.productSpecifications,
		replica.productType = staging.productType,
		replica.skus = staging.skus,
		replica.status = staging.status,
		replica.subscriptionEnabled = staging.subscriptionEnabled,
		replica.tags = staging.tags,
		replica.urls = staging.urls
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`catalogId`,
		`categoryIds`,
		`channelId`,
		`createDate`,
		`dataSourceId`,
		`description`,
		`displayDate`,
		`expirationDate`,
		`externalReferenceCode`,
		`id`,
		`metaDescription`,
		`metaKeyword`,
		`metaTitle`,
		`modifiedDate`,
		`name`,
		`productChannelIds`,
		`productId`,
		`productOptions`,
		`productSpecifications`,
		`productType`,
		`projectId`,
		`skus`,
		`status`,
		`subscriptionEnabled`,
		`tags`,
		`urls`
	)
	VALUES (
		staging.catalogId,
		staging.categoryIds,
		staging.channelId,
		staging.createDate,
		staging.dataSourceId,
		staging.description,
		staging.displayDate,
		staging.expirationDate,
		staging.externalReferenceCode,
		staging.id,
		staging.metaDescription,
		staging.metaKeyword,
		staging.metaTitle,
		staging.modifiedDate,
		staging.name,
		staging.productChannelIds,
		staging.productId,
		staging.productOptions,
		staging.productSpecifications,
		staging.productType,
		staging.projectId,
		staging.skus,
		staging.status,
		staging.subscriptionEnabled,
		staging.tags,
		staging.urls
	)