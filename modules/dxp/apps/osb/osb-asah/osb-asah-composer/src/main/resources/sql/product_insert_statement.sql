INSERT INTO `{{ dag.default_args['ac_project_id'] }}.product_raw` (
	catalogId,
	categoryIds,
	channelId,
	createDate,
	dataSourceId,
	description,
	displayDate,
	expirationDate,
	externalReferenceCode,
	id,
	metaDescription,
	metaKeyword,
	metaTitle,
	modifiedDate,
	name,
	productChannelIds,
	productId,
	productOptions,
	productSpecifications,
	productType,
	projectId,
	skus,
	status,
	subscriptionEnabled,
	tags,
	uploadDate,
	uploadType,
	urls
)

SELECT
	catalogId,
	categoryIds,
	catalogId AS channelId,
	createDate,
	CAST('{{ params['dataSourceId'] }}' AS INTEGER) AS dataSourceId,
	TO_JSON_STRING(description) AS description,
	displayDate,
	expirationDate,
	externalReferenceCode,
	id,
	TO_JSON_STRING(metaDescription) AS metaDescription,
	TO_JSON_STRING(metaKeyword) AS metaKeyword,
	TO_JSON_STRING(metaTitle) AS metaTitle,
	modifiedDate,
	TO_JSON_STRING(name) AS name,
	productChannelIds,
	productId,
	ARRAY(
		SELECT AS STRUCT
			key,
			optionKey,
			ARRAY(
				SELECT
					TO_JSON_STRING(value)
				FROM
					UNNEST(values) AS value
			) AS values
		FROM
			UNNEST(productOptions)
	) AS productOptions,
	ARRAY(
		SELECT AS STRUCT
			id,
			label,
			optionCategoryId,
			priority,
			specificationId,
			specificationKey,
			TO_JSON_STRING(value) AS value
		FROM
			UNNEST(productSpecifications)
	) AS productSpecifications,
	productType,
	'{{ dag.default_args['ac_project_id'] }}' AS projectId,
	skus,
	status,
	subscriptionEnabled,
	tags,
	CAST('{{ params['uploadDate'] }}' AS TIMESTAMP) AS uploadDate,
	'{{ params['uploadType'] }}' AS uploadType,
	TO_JSON_STRING(urls) AS urls
FROM
	`{{ dag.default_args['ac_project_id'] }}.{{ dag.default_args['entity'] }}_external_{{ ts_nodash }}`