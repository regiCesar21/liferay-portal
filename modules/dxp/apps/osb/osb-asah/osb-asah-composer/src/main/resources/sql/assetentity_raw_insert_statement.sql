INSERT INTO `{{ dag.default_args['ac_project_id'] }}.assetentity_raw` (
	assetCategoryIds,
	assetTagNames,
	channelId,
	className,
	classPK,
	classTypeId,
	classTypeName,
	createDate,
	dataSourceId,
	expirationDate,
	groupId,
	id,
	modifiedDate,
	projectId,
	publishDate,
	title,
	uploadDate,
	uploadType
)

SELECT
	assetCategoryIds,
	assetTagNames,
	channelId,
	className,
	classPK,
	classTypeId,
	classTypeName,
	createDate,
	CAST('{{ params['dataSourceId'] }}' AS INTEGER) AS dataSourceId,
	expirationDate,
	groupId,
	id,
	modifiedDate,
	'{{ dag.default_args['ac_project_id'] }}' AS projectId,
	publishDate,
	title,
	CAST('{{ params['uploadDate'] }}' AS TIMESTAMP) AS uploadDate,
	'{{ params['uploadType'] }}' AS uploadType
FROM
	`{{ dag.default_args['ac_project_id'] }}.dxp_asset_external_{{ ts_nodash }}`