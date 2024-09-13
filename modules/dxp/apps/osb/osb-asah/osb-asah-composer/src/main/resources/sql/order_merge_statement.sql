MERGE INTO
	`{{ dag.default_args['ac_project_id'] }}.order` AS replica
USING
	(
		SELECT
			analyticsDeleteMessage.deleted,
			`order`.*
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
					`{{ dag.default_args['ac_project_id'] }}.order_raw`
				)
			WHERE
				rowNumber = 1
		) AS `order`
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
				className = 'com.liferay.commerce.model.CommerceOrder'
		) AS analyticsDeleteMessage
		ON
			`order`.dataSourceId = analyticsDeleteMessage.dataSourceId AND
			`order`.id = analyticsDeleteMessage.classPK AND
			`order`.projectId = analyticsDeleteMessage.projectId
		WHERE
			`order`.dataSourceId = CAST('{{ params['dataSourceId'] }}' AS INTEGER) AND
			`order`.uploadDate >=
				{% if params.uploadType == 'FULL' %}
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
		replica.accountId = staging.accountId,
		replica.channelId = staging.channelId,
		replica.commerceChannelId = staging.commerceChannelId,
		replica.currencyCode = staging.currencyCode,
		replica.externalReferenceCode = staging.externalReferenceCode,
		replica.modifiedDate = staging.modifiedDate,
		replica.orderDate = staging.orderDate,
		replica.orderItems = staging.orderItems,
		replica.orderStatus = staging.orderStatus,
		replica.orderTypeId = staging.orderTypeId,
		replica.paymentMethod = staging.paymentMethod,
		replica.paymentStatus = staging.paymentStatus,
		replica.status = staging.status,
		replica.total = staging.total,
		replica.userId = staging.userId
WHEN MATCHED AND staging.deleted = true THEN
	DELETE
WHEN NOT MATCHED BY TARGET AND staging.deleted IS NULL THEN
	INSERT (
		`accountId`,
		`channelId`,
		`commerceChannelId`,
		`createDate`,
		`currencyCode`,
		`dataSourceId`,
		`externalReferenceCode`,
		`id`,
		`modifiedDate`,
		`orderDate`,
		`orderItems`,
		`orderStatus`,
		`orderTypeId`,
		`paymentMethod`,
		`paymentStatus`,
		`projectId`,
		`status`,
		`total`,
		`userId`
	)
	VALUES (
		staging.accountId,
		staging.channelId,
		staging.commerceChannelId,
		staging.createDate,
		staging.currencyCode,
		staging.dataSourceId,
		staging.externalReferenceCode,
		staging.id,
		staging.modifiedDate,
		staging.orderDate,
		staging.orderItems,
		staging.orderStatus,
		staging.orderTypeId,
		staging.paymentMethod,
		staging.paymentStatus,
		staging.projectId,
		staging.status,
		staging.total,
		staging.userId
	)