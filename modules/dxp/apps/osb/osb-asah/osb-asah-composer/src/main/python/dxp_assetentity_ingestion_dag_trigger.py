#
# Copyright (c) 2024-present Liferay, Inc. All rights reserved.
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

def create_dag(ac_project_id, dag_id, dag_description):
	with airflow.DAG(
		dag_id=dag_id,
		default_args={
			'ac_project_id': ac_project_id,
			'google_project_id': os.environ['GOOGLE_PROJECT_ID'],
			'owner': 'Liferay'
		},
		description=dag_description,
		max_active_runs=1,
		schedule_interval=None,
		start_date=pendulum.now() - pendulum.duration(days=2)
	) as dag:

		create_assetentity_external_table = BigQueryCreateExternalTableOperator(
			bucket="{{ params['bucketName'] }}",
			compression='GZIP',
			destination_project_dataset_table="{{ dag.default_args['ac_project_id'] }}.assetentity_external_{{ ts_nodash }}",
			schema_fields=[
				{
					"mode": "REPEATED",
					"name": "assetCategoryIds",
					"type": "INT64"
				},
				{
					"mode": "REPEATED",
					"name": "assetTagNames",
					"type": "STRING"
				},
				{
					"mode": "NULLABLE",
					"name": "channelId",
					"type": "INT64"
				},
				{
					"mode": "REQUIRED",
					"name": "className",
					"type": "STRING"
				},
				{
					"mode": "REQUIRED",
					"name": "classPK",
					"type": "INT64"
				},
				{
					"mode": "NULLABLE",
					"name": "classTypeId",
					"type": "INT64"
				},
				{
					"mode": "NULLABLE",
					"name": "classTypeName",
					"type": "STRING"
				},
				{
					"mode": "REQUIRED",
					"name": "createDate",
					"type": "TIMESTAMP"
				},
				{
					"mode": "NULLABLE",
					"name": "expirationDate",
					"type": "TIMESTAMP"
				},
				{
					"mode": "NULLABLE",
					"name": "groupId",
					"type": "INT64"
				},
				{
					"mode": "REQUIRED",
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
					"name": "publishDate",
					"type": "TIMESTAMP"
				},
				{
					"mode": "NULLABLE",
					"name": "title",
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
				}
			],
			source_format='NEWLINE_DELIMITED_JSON',
			source_objects=["{{ dag.default_args['ac_project_id'] }}/{{ params['bucketFolder'] }}/{{ params['uploadDate'] }}.gz"],
			task_id="create_assetentity_external_table"
		)

		assetentity_raw_insert_job  = BigQueryInsertJobFromTemplateOperator(
			task_id='assetentity_raw_insert'
		)

		assetentity_merge_job  = BigQueryInsertJobFromTemplateOperator(
			task_id='asset_entity_merge'
		)
		
		delete_assetentity_external_table = BigQueryDeleteTableOperator(
			deletion_dataset_table="{{ dag.default_args['ac_project_id'] }}.assetentity_external_{{ ts_nodash }}",
			task_id="delete_assetentity_external_table"
		)

		chain(
			create_assetentity_external_table, assetentity_raw_insert_job, 
			assetentity_merge_job, delete_assetentity_external_table
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

	#
	# Asset
	#

	dag_id = 'dxp_asset_ingestion_{}'.format(
		project.get('id')
	)

	globals()[dag_id] = create_dag(
		project.get('id'), dag_id,
		'DXP Asset Ingestion DAG For {}'.format(
			project.get('id')
		)
	)