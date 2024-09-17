#
# Copyright (c) 2000-present Liferay, Inc. All rights reserved.
#
# The contents of this file are subject to the terms of the Liferay Enterprise
# Subscription License ("License"). You may not use this file except in
# compliance with the License. You can obtain a copy of the License by
# contacting Liferay, Inc. See the License for the specific language governing
# permissions and limitations under the License, including but not limited to
# distribution rights of the Software.
#

from airflow.models import Variable
from airflow.models.baseoperator import chain
from airflow.providers.google.cloud.operators.bigquery import \
	BigQueryCreateExternalTableOperator, BigQueryDeleteTableOperator

from liferay.bigquery import BigQueryInsertJobFromTemplateOperator

import airflow
import os
import pendulum
import requests

def create_dag(ac_project_id, dag_id, dag_description, entity, schema_fields):
	with airflow.DAG(
		dag_id=dag_id,
		default_args={
			'ac_project_id': ac_project_id,
			'entity': entity,
			'google_project_id': os.environ['GOOGLE_PROJECT_ID'],
			'region': os.environ['GOOGLE_REGION'],
			'owner': 'Liferay'
		},
		description=dag_description,
		max_active_runs=1,
		schedule_interval=None,
		start_date=pendulum.now() - pendulum.duration(days=2)
	) as dag:

		create_external_table = BigQueryCreateExternalTableOperator(
			bucket="{{ params['bucketName'] }}",
			compression='GZIP',
			destination_project_dataset_table="{{ dag.default_args['ac_project_id'] }}.{{ dag.default_args['entity'] }}_external_{{ ts_nodash }}",
			schema_fields=schema_fields,
			source_format='NEWLINE_DELIMITED_JSON',
			source_objects=["{{ dag.default_args['ac_project_id'] }}/{{ params['bucketFolder'] }}/{{ params['uploadDate'] }}.gz"],
			task_id=f"create_{ entity }_external_table"
		)

		insert_job = BigQueryInsertJobFromTemplateOperator(
			task_id=f'{ entity }_raw_insert'
		)

		merge_job = BigQueryInsertJobFromTemplateOperator(
			task_id=f'{ entity }_merge'
		)

		delete_external_table = BigQueryDeleteTableOperator(
			deletion_dataset_table="{{ dag.default_args['ac_project_id'] }}.{{ dag.default_args['entity'] }}_external_{{ ts_nodash }}",
			task_id=f"delete_{ entity }_external_table"
		)

		chain(
			create_external_table, insert_job, merge_job, delete_external_table
		)

		return dag

response = requests.get(
	Variable.get('osb.asah.backend.url'),
	headers={
		'OSB-Asah-Faro-Backend-Security-Signature': Variable.get('osb.asah.faro.backend.security.signature'),
		'OSB-Asah-Project-ID': 'osbasah',
		'User-Agent': 'LiferayAnalyticsCloud'
	}
)

for project in response.json():

	if not project.get('commerceChannelsSelected'):
		continue

	#
	# Commerce Order
	#

	dag_id = 'dxp_order_ingestion_{}'.format(
		project.get('id')
	)

	globals()[dag_id] = create_dag(
		project.get('id'), dag_id,
		'DXP Order Ingestion DAG For {}'.format(
			project.get('id')
		),
		'order',
		[
			{
				"mode": "NULLABLE",
				"name": "accountId",
				"type": "INT64"
			},
			{
				"mode": "REQUIRED",
				"name": "channelId",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "createDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "currencyCode",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "customFields",
				"type": "JSON"
			},
			{
				"mode": "NULLABLE",
				"name": "externalReferenceCode",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "id",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "modifiedDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "orderDate",
				"type": "TIMESTAMP"
			},
			{
				"fields": [
					{
						"mode": "NULLABLE",
						"name": "cpDefinitionId",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "createDate",
						"type": "TIMESTAMP"
					},
					{
						"mode": "NULLABLE",
						"name": "customFields",
						"type": "JSON"
					},
					{
						"mode": "NULLABLE",
						"name": "externalReferenceCode",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "finalPrice",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "id",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "modifiedDate",
						"type": "TIMESTAMP"
					},
					{
						"mode": "NULLABLE",
						"name": "name",
						"type": "JSON"
					},
					{
						"mode": "NULLABLE",
						"name": "options",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "parentOrderItemId",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "quantity",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "sku",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "subscription",
						"type": "BOOL"
					},
					{
						"mode": "NULLABLE",
						"name": "unitOfMeasure",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "unitPrice",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "userId",
						"type": "INT64"
					}
				],
				"mode": "REPEATED",
				"name": "orderItems",
				"type": "RECORD"
			},
			{
				"mode": "NULLABLE",
				"name": "orderStatus",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "orderTypeExternalReferenceCode",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "orderTypeId",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "paymentMethod",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "paymentStatus",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "status",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "total",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "uploadDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "uploadType",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "userId",
				"type": "INT64"
			}
		]
	)

	#
	# Commerce Product
	#

	dag_id = 'dxp_product_ingestion_{}'.format(
		project.get('id')
	)

	globals()[dag_id] = create_dag(
		project.get('id'), dag_id,
		'DXP Product Ingestion DAG For {}'.format(
			project.get('id')
		),
		'product',
		[
			{
				"mode": "NULLABLE",
				"name": "catalogId",
				"type": "INT64"
			},
			{
				"mode": "REPEATED",
				"name": "categoryIds",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "createDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "customFields",
				"type": "JSON"
			},
			{
				"mode": "NULLABLE",
				"name": "description",
				"type": "JSON"
			},
			{
				"mode": "NULLABLE",
				"name": "displayDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "expirationDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "externalReferenceCode",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "id",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "metaDescription",
				"type": "JSON"
			},
			{
				"mode": "NULLABLE",
				"name": "metaKeyword",
				"type": "JSON"
			},
			{
				"mode": "NULLABLE",
				"name": "metaTitle",
				"type": "JSON"
			},
			{
				"mode": "NULLABLE",
				"name": "modifiedDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "name",
				"type": "JSON"
			},
			{
				"mode": "REPEATED",
				"name": "productChannelIds",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "productId",
				"type": "INT64"
			},
			{
				"fields": [
					{
						"mode": "NULLABLE",
						"name": "key",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "optionKey",
						"type": "STRING"
					},
					{
						"mode": "REPEATED",
						"name": "values",
						"type": "JSON"
					}
				],
				"mode": "REPEATED",
				"name": "productOptions",
				"type": "RECORD"
			},
			{
				"fields": [
					{
						"mode": "NULLABLE",
						"name": "id",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "label",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "optionCategoryId",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "priority",
						"type": "NUMERIC"
					},
					{
						"mode": "NULLABLE",
						"name": "specificationId",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "specificationKey",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "value",
						"type": "JSON"
					}
				],
				"mode": "REPEATED",
				"name": "productSpecifications",
				"type": "RECORD"
			},
			{
				"mode": "NULLABLE",
				"name": "productType",
				"type": "STRING"
			},
			{
				"fields": [
					{
						"mode": "NULLABLE",
						"name": "cost",
						"type": "NUMERIC"
					},
					{
						"mode": "NULLABLE",
						"name": "discontinued",
						"type": "BOOL"
					},
					{
						"mode": "NULLABLE",
						"name": "displayDate",
						"type": "TIMESTAMP"
					},
					{
						"mode": "NULLABLE",
						"name": "expirationDate",
						"type": "TIMESTAMP"
					},
					{
						"mode": "NULLABLE",
						"name": "externalReferenceCode",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "gtin",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "id",
						"type": "INT64"
					},
					{
						"mode": "NULLABLE",
						"name": "manufacturerPartNumber",
						"type": "STRING"
					},
					{
						"mode": "NULLABLE",
						"name": "published",
						"type": "BOOL"
					},
					{
						"mode": "NULLABLE",
						"name": "purchasable",
						"type": "BOOL"
					},
					{
						"mode": "NULLABLE",
						"name": "sku",
						"type": "STRING"
					}
				],
				"mode": "REPEATED",
				"name": "skus",
				"type": "RECORD"
			},
			{
				"mode": "NULLABLE",
				"name": "status",
				"type": "INT64"
			},
			{
				"mode": "NULLABLE",
				"name": "subscriptionEnabled",
				"type": "BOOL"
			},
			{
				"mode": "REPEATED",
				"name": "tags",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "uploadDate",
				"type": "TIMESTAMP"
			},
			{
				"mode": "NULLABLE",
				"name": "uploadType",
				"type": "STRING"
			},
			{
				"mode": "NULLABLE",
				"name": "urls",
				"type": "JSON"
			}
		]
	)