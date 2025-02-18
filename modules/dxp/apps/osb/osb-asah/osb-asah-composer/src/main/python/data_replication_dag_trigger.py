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
from airflow.providers.google.cloud.operators.cloud_sql import \
	CloudSQLExecuteQueryOperator
from airflow.providers.google.cloud.operators.bigquery import \
	BigQueryCreateExternalTableOperator, BigQueryDeleteTableOperator

from liferay.bigquery import BigQueryInsertJobFromTemplateOperator
from liferay.cloud_sql import CloudSQLCSVImportOperator

import airflow
import os
import pendulum
import requests

def create_dag(ac_project_id, ac_project_time_zone_id, dag_id, dag_description):
	with airflow.DAG(
		dag_id=dag_id,
		default_args={
			'ac_sql_instance': Variable.get('osb.asah.sql.instance'),
			'ac_project_id': ac_project_id,
			'ac_project_time_zone_id': ac_project_time_zone_id,
			'google_project_id': os.environ['GOOGLE_PROJECT_ID'],
			'owner': 'Liferay'
		},
		description=dag_description,
		max_active_runs=1,
		schedule_interval=None,
		start_date=pendulum.now() - pendulum.duration(days=2)
	) as dag:

		bq_individual_export_job = BigQueryInsertJobFromTemplateOperator(
			task_id='individual_export'
		)

		bq_individual_activity_export_job = BigQueryInsertJobFromTemplateOperator(
			task_id='individual_activity_export'
		)

		psql_truncate_individual_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id="google_cloud_sql",
			sql="TRUNCATE TABLE {{dag.default_args['ac_project_id']}}.individual",
			task_id="truncate_individual_table"
		)

		psql_individual_import_job = CloudSQLCSVImportOperator(
			bucket_name="{{dag.default_args['google_project_id']}}-data-replica",
			bucket_prefix="{{dag.default_args['ac_project_id']}}/individual/{{ts}}",
			database="osbasah",
			gcp_conn_id="google_cloud_default",
			instance="{{dag.default_args['ac_sql_instance']}}",
			table="{{dag.default_args['ac_project_id']}}.individual",
			task_id='cloudsql_individual_import'
		)

		chain(
			bq_individual_export_job, bq_individual_activities_export_job,
			psql_truncate_individual_table_job, psql_individual_import_job
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
	dag_id = 'data_replication_{}'.format(
		project.get('id')
	)

	globals()[dag_id] = create_dag(
		project.get('id'), project.get('timeZoneId'), dag_id,
		'BigQuery Data Replication DAG For {}'.format(
			project.get('id')
		)
	)