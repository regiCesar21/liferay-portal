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

def create_dag(
	ac_project_id, accounts_selected, contacts_selected, dag_id, dag_description):

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

		create_dxpentity_external_table = BigQueryCreateExternalTableOperator(
			bucket="{{ params['bucketName'] }}",
			compression='GZIP',
			destination_project_dataset_table="{{ dag.default_args['ac_project_id'] }}.dxpentity_external_{{ ts_nodash }}",
			schema_fields=[
				{
					"fields": [
						{
							"mode": "REQUIRED",
							"name": "columnId",
							"type": "STRING"
						},
						{
							"mode": "REQUIRED",
							"name": "name",
							"type": "STRING"
						},
						{
							"mode": "REQUIRED",
							"name": "value",
	   						"type": "STRING"
						}
					],
					"mode": "REPEATED",
					"name": "expandoFields",
					"type": "RECORD"
				},
				{
					"fields": [
						{
							"mode": "REQUIRED",
							"name": "name",
							"type": "STRING"
						},
						{
							"mode": "REQUIRED",
							"name": "value",
	   						"type": "STRING"
						}
					],
					"mode": "REPEATED",
					"name": "fields",
					"type": "RECORD"
				},
				{
					"mode": "REQUIRED",
					"name": "id",
					"type": "STRING"
				},
				{
					"mode": "REQUIRED",
					"name": "modifiedDate",
					"type": "TIMESTAMP"
				},
				{
					"mode": "REQUIRED",
					"name": "type",
					"type": "STRING"
				}
			],
			source_format='NEWLINE_DELIMITED_JSON',
			source_objects=["{{ dag.default_args['ac_project_id'] }}/{{ params['bucketFolder'] }}/{{ params['uploadDate'] }}.gz"],
			task_id="create_dxpentity_external_table"
		)

		dxpentity_insert_job  = BigQueryInsertJobFromTemplateOperator(
			task_id='dxp_entity_insert'
		)

		bigquery_jobs = []

		if accounts_selected:
			bigquery_jobs += [
				BigQueryInsertJobFromTemplateOperator(
					task_id='account_entry_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='account_group_merge'
				)
			]
		
		if contacts_selected:
			bigquery_jobs += [
				BigQueryInsertJobFromTemplateOperator(
					task_id='expando_column_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='expando_value_delete'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='expando_value_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='group_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='organization_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='role_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='team_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='user_group_merge'
				),
				BigQueryInsertJobFromTemplateOperator(
					task_id='user_merge'
				),
			]

		individual_merge_job = BigQueryInsertJobFromTemplateOperator(
			task_id='individual_merge'
		)

		delete_dxpentity_external_table = BigQueryDeleteTableOperator(
			deletion_dataset_table="{{ dag.default_args['ac_project_id'] }}.dxpentity_external_{{ ts_nodash }}",
			task_id="delete_dxpentity_external_table"
		)

		chain(
			create_dxpentity_external_table, dxpentity_insert_job, 
			bigquery_jobs, individual_merge_job, delete_dxpentity_external_table
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
	if project.get('accountsSelected') or project.get('contactsSelected'):
		dag_id = 'dxp_entity_ingestion_{}'.format(
			project.get('id')
		)

		globals()[dag_id] = create_dag(
			project.get('id'), project.get('accountsSelected'),
			project.get('contactsSelected'), dag_id,
			'DXP Entity Ingestion DAG For {}'.format(
				project.get('id')
			)
		)