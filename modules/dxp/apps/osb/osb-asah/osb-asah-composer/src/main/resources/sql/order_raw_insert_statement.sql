INSERT INTO `{{ dag.default_args['ac_project_id'] }}.order_raw` (
	accountId,
	channelId,
	commerceChannelId,
	createDate,
	currencyCode,
	dataSourceId,
	externalReferenceCode,
	id,
	modifiedDate,
	orderDate,
	orderItems,
	orderStatus,
	orderTypeExternalReferenceCode,
	orderTypeId,
	paymentMethod,
	paymentStatus,
	projectId,
	status,
	total,
	uploadDate,
	uploadType,
	userId
)

WITH CommerceChannelsMap AS (
	SELECT
		*
	FROM EXTERNAL_QUERY
	(
		'{{ dag.default_args['google_project_id'] }}.{{ dag.default_args['region'] }}.postgresql',
		'SELECT UNNEST(commercechannelids) AS commercechannelid, id AS asahChannelId FROM {{ dag.default_args['ac_project_id'] }}.channel JOIN {{ dag.default_args['ac_project_id'] }}.channeldatasource ON (channel.id = channeldatasource.channelid)'
	)
)

SELECT
	accountId,
	CommerceChannelsMap.asahChannelId AS channelId,
	channelId AS commerceChannelId,
	createDate,
	currencyCode,
	CAST('{{ params['dataSourceId'] }}' AS INTEGER) AS dataSourceId,
	externalReferenceCode,
	id,
	modifiedDate,
	orderDate,
	ARRAY(
		SELECT AS STRUCT
			cpDefinitionId,
			createDate,
			TO_JSON_STRING(oi.customFields) AS customFields,
			externalReferenceCode,
			finalPrice,
			id,
			modifiedDate,
			TO_JSON_STRING(oi.name) AS name,
			options,
			parentOrderItemId,
			quantity,
			sku,
			subscription,
			unitOfMeasure,
			unitPrice,
			userId
		FROM
			UNNEST(orderItems) oi
	) AS orderItems,
	orderStatus,
	orderTypeExternalReferenceCode,
	orderTypeId,
	paymentMethod,
	paymentStatus,
	'{{ dag.default_args['ac_project_id'] }}' AS projectId,
	status,
	total,
	CAST('{{ params['uploadDate'] }}' AS TIMESTAMP) AS uploadDate,
	'{{ params['uploadType'] }}' AS uploadType,
	userId
FROM
	`{{ dag.default_args['ac_project_id'] }}.{{ dag.default_args['entity'] }}_external_{{ ts_nodash }}`, CommerceChannelsMap