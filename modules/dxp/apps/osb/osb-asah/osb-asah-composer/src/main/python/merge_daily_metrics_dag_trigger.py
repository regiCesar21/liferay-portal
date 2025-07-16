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

from liferay.bigquery import BigQueryInsertJobFromTemplateOperator

import airflow
import pendulum
import requests

def create_dag(ac_project_id, ac_project_time_zone_id, dag_id, dag_description):
	with airflow.DAG(
		dag_id=dag_id,
		default_args={
			'ac_project_id': ac_project_id,
			'ac_project_time_zone_id': ac_project_time_zone_id,
			'owner': 'Liferay'
		},
		description=dag_description,
		max_active_runs=1,
		schedule_interval='0 1 * * *',
		start_date=pendulum.now(ac_project_time_zone_id) - pendulum.duration(days=2)
	) as dag:
		[
			BigQueryInsertJobFromTemplateOperator(task_id='blog_daily_merge'),
			BigQueryInsertJobFromTemplateOperator(task_id='customasset_daily_merge'),
			BigQueryInsertJobFromTemplateOperator(task_id='document_library_daily_merge'),
			BigQueryInsertJobFromTemplateOperator(task_id='form_daily_merge'),
			BigQueryInsertJobFromTemplateOperator(task_id='identity_activity_summary_merge'),
			BigQueryInsertJobFromTemplateOperator(task_id='journal_daily_merge'),
			BigQueryInsertJobFromTemplateOperator(task_id='object_entry_daily_merge'),
			BigQueryInsertJobFromTemplateOperator(task_id='page_daily_merge')
		]

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
	dag_id = 'merge_daily_metrics_{}'.format(project.get('id'))

	if project.get('sitesSelected'):
		globals()[dag_id] = create_dag(
			project.get('id'), project.get('timeZoneId'), dag_id,
			'Daily Merge DAG For {}'.format(project.get('id'))
		)