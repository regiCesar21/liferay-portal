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

from airflow.models import DagRun, \
	Variable
from airflow.models.baseoperator import BaseOperator, \
	chain
from airflow.providers.google.cloud.operators.cloud_sql import CloudSQLExecuteQueryOperator
from airflow.providers.google.cloud.operators.dataflow import DataflowStartFlexTemplateOperator
from airflow.utils.context import Context
from airflow.utils.dates import days_ago
from airflow.utils.session import provide_session
from airflow.utils.trigger_rule import TriggerRule

from datetime import date

from liferay.bigquery import BigQueryInsertJobFromTemplateOperator

from sqlalchemy import func

import airflow
import os
import pendulum
import re
import requests

class GetDataReplicationStartDateDateOperator(BaseOperator):

	def __init__(self, **kwargs):
		super().__init__(**kwargs)

	@provide_session
	def execute(self, context: Context, session=None) -> date:
		dag_id = context["dag"].dag_id

		previous_successful_dag_run = session.query(
			func.max(DagRun.execution_date)
		).filter(
			DagRun.dag_id == dag_id, DagRun.state == "success",
			DagRun.execution_date < context["execution_date"]
		).scalar()

		if previous_successful_dag_run:
			result_date = previous_successful_dag_run.date()
		else:
			result_date = days_ago(30).date()

		context["ti"].xcom_push(
			key="data_replication_start_date", value=result_date)

		return result_date

def create_dag(ac_project_id, ac_project_time_zone_id, dag_id, dag_description):
	with airflow.DAG(
		dag_id=dag_id,
		default_args={
			'ac_project_id': ac_project_id,
			'ac_project_time_zone_id': ac_project_time_zone_id,
			'google_project_id': os.environ['GOOGLE_PROJECT_ID'],
			'owner': 'Liferay'
		},
		description=dag_description,
		max_active_runs=1,
		schedule_interval='0 2 * * *',
		start_date=pendulum.now(ac_project_time_zone_id) - pendulum.duration(days=2)
	) as dag:

		get_data_replication_start_date = GetDataReplicationStartDateDateOperator(
			task_id='get_data_replication_start_date'
		)

		bq_individual_activity_export_job = BigQueryInsertJobFromTemplateOperator(
			task_id='individual_activity_export'
		)

		bq_individual_export_job = BigQueryInsertJobFromTemplateOperator(
			task_id='individual_export'
		)

		bq_individual_interest_export_job = BigQueryInsertJobFromTemplateOperator(
			task_id='individual_interest_export'
		)

		bq_membership_export_job = BigQueryInsertJobFromTemplateOperator(
			task_id='membership_export'
		)

		postgresql_create_temp_individual_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'CREATE TABLE IF NOT EXISTS {{dag.default_args["ac_project_id"]}}."individual_{{ts}}"(LIKE {{dag.default_args["ac_project_id"]}}.individual INCLUDING CONSTRAINTS INCLUDING DEFAULTS INCLUDING INDEXES);',
			task_id='create_temp_individual_table'
		)

		postgresql_create_temp_individualactivity_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'CREATE TABLE IF NOT EXISTS {{dag.default_args["ac_project_id"]}}."individualactivity_{{ts}}"(LIKE {{dag.default_args["ac_project_id"]}}.individualactivity INCLUDING CONSTRAINTS INCLUDING DEFAULTS INCLUDING INDEXES);',
			task_id='create_temp_individualactivity_table'
		)

		postgresql_create_temp_individualinterest_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'CREATE TABLE IF NOT EXISTS {{dag.default_args["ac_project_id"]}}."individualinterest_{{ts}}"(LIKE {{dag.default_args["ac_project_id"]}}.individualinterest INCLUDING CONSTRAINTS INCLUDING DEFAULTS INCLUDING INDEXES);',
			task_id='create_temp_individualinterest_table'
		)

		postgresql_create_temp_individualsegment_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'CREATE TABLE IF NOT EXISTS {{dag.default_args["ac_project_id"]}}."individualsegment_{{ts}}"(LIKE {{dag.default_args["ac_project_id"]}}.individualsegment INCLUDING CONSTRAINTS INCLUDING DEFAULTS INCLUDING INDEXES);',
			task_id='create_temp_individualsegment_table'
		)

		postgresql_replication_dataflow_trigger = DataflowStartFlexTemplateOperator(
			task_id='replication_dataflow_trigger',
			body={
				'launchParameter': {
					'containerSpecGcsPath': 'gs://{}-dataflow/flex-templates/postgresql-replication-pipeline.json'.format(os.environ['GOOGLE_PROJECT_ID']),
					'environment': {
						'maxWorkers': 1,
						'numWorkers': 1,
						'serviceAccountEmail': Variable.get('osb.asah.service.account.email'),
						'subnetwork': 'https://www.googleapis.com/compute/v1/projects/{}/regions/{}/subnetworks/{}'.format(os.environ['GOOGLE_PROJECT_ID'], os.environ['GOOGLE_REGION'], re.sub('(-analytics-internal|-ac-internal)$', '', os.environ['SUBNETWORK']))
					},
					'jobName': 'postgresql-replication-pipeline-{}-{}'.format(ac_project_id, date.today()),
					'parameters': {
						'cloudSQLConnectionName': '{}:{}:{}'.format(os.environ['GOOGLE_PROJECT_ID'], os.environ['GOOGLE_REGION'], Variable.get('osb.asah.sql.instance')),
						'databaseUser': re.sub('.gserviceaccount.com', '', Variable.get('osb.asah.service.account.email')),
						'individualActivityColumns': 'id,applicationId,channelId,context,eventDate,eventId,individualId,properties',
						'individualActivityInputDirectory': 'gs://{}-data-replica/{}/{}/individual-activity/*.csv'.format(os.environ['GOOGLE_PROJECT_ID'], ac_project_id, '{{ts}}'),
						'individualActivityPrimaryKey': 'id',
						'individualColumns': 'id,emailAddress,fields,suppressed',
						'individualInputDirectory': 'gs://{}-data-replica/{}/{}/individual/*.csv'.format(os.environ['GOOGLE_PROJECT_ID'], ac_project_id, '{{ts}}'),
						'individualInterestColumns': 'channelId,identityId,individualId,interested,interestScore,keyword,recordedDate',
						'individualInterestInputDirectory': 'gs://{}-data-replica/{}/{}/individual-interest/*.csv'.format(os.environ['GOOGLE_PROJECT_ID'], ac_project_id, '{{ts}}'),
						'individualInterestPrimaryKey': 'channelId,identityId,individualId,keyword,recordedDate',
						'individualPrimaryKey': 'id',
						'individualSegmentColumns': 'createDate,channelId,individualId,modifiedDate,segmentId,status',
						'individualSegmentInputDirectory': 'gs://{}-data-replica/{}/{}/individual-segment/*.csv'.format(os.environ['GOOGLE_PROJECT_ID'], ac_project_id, '{{ts}}'),
						'individualSegmentPrimaryKey': 'channelId,individualId,segmentId',
						'projectId': ac_project_id,
						'tempTableSuffix': '{{ts}}'
					}
				}
			},
			location=os.environ['GOOGLE_REGION'],
			project_id=os.environ['GOOGLE_PROJECT_ID'],
			gcp_conn_id='google_cloud_default',
			wait_until_finished=True
		)

		postgresql_merge_temp_individual_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= """
				BEGIN;

				TRUNCATE TABLE {{dag.default_args["ac_project_id"]}}.individual;

				INSERT INTO {{dag.default_args["ac_project_id"]}}.individual(id, emailAddress, fields, suppressed)
					SELECT id, emailAddress, fields, suppressed FROM {{dag.default_args["ac_project_id"]}}."individual_{{ts}}";

				COMMIT;
			""",
			task_id='merge_temp_individual_table'
		)

		postgresql_merge_temp_individualactivity_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= """
				MERGE INTO {{dag.default_args["ac_project_id"]}}.individualactivity AS replica
					USING {{dag.default_args["ac_project_id"]}}."individualactivity_{{ts}}" AS staging
					ON replica.id = staging.id
					WHEN MATCHED THEN DO NOTHING
					WHEN NOT MATCHED THEN INSERT (id, applicationId, channelId, eventDate, eventId, properties, individualId) VALUES (staging.id, staging.applicationId, staging.channelId, staging.eventDate, staging.eventId, staging.properties, staging.individualId);
			""",
			task_id='merge_temp_individualactivity_table'
		)

		postgresql_merge_temp_individualinterest_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= """
				MERGE INTO {{dag.default_args["ac_project_id"]}}.individualinterest AS replica
					USING {{dag.default_args["ac_project_id"]}}."individualinterest_{{ts}}" AS staging
					ON (
						replica.channelId = staging.channelId
						AND replica.identityId = staging.identityId
						AND replica.individualId = staging.individualId
						AND replica.keyword = staging.keyword
						AND replica.recordedDate = staging.recordedDate
					)
					WHEN MATCHED THEN DO NOTHING
					WHEN NOT MATCHED THEN INSERT (channelId, identityId, individualId, interested, interestScore, keyword, recordedDate) VALUES (staging.channelId, staging.identityId, staging.individualId, staging.interested, staging.interestScore, staging.keyword, staging.recordedDate);
			""",
			task_id='merge_temp_individualinterest_table'
		)

		postgresql_merge_temp_individualsegment_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= """
				BEGIN;

				TRUNCATE TABLE {{dag.default_args["ac_project_id"]}}.individualsegment;

				INSERT INTO {{dag.default_args["ac_project_id"]}}.individualsegment(createDate, channelId, individualId, modifiedDate, segmentId, status)
					SELECT createDate, channelId, individualId, modifiedDate, segmentId, status FROM {{dag.default_args["ac_project_id"]}}."individualsegment_{{ts}}";

				COMMIT;
			""",
			task_id='merge_temp_individualsegment_table'
		)

		postgresql_cleanup_temp_individual_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'DROP TABLE IF EXISTS {{dag.default_args["ac_project_id"]}}."individual_{{ts}}";',
			task_id='cleanup_temp_individual_table',
			trigger_rule=TriggerRule.ALL_DONE
		)

		postgresql_cleanup_temp_individualactivity_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'DROP TABLE IF EXISTS {{dag.default_args["ac_project_id"]}}."individualactivity_{{ts}}";',
			task_id='cleanup_temp_individualactivity_table',
			trigger_rule=TriggerRule.ALL_DONE
		)

		postgresql_cleanup_temp_individualinterest_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'DROP TABLE IF EXISTS {{dag.default_args["ac_project_id"]}}."individualinterest_{{ts}}";',
			task_id='cleanup_temp_individualinterest_table',
			trigger_rule=TriggerRule.ALL_DONE
		)

		postgresql_cleanup_temp_individualsegment_table_job = CloudSQLExecuteQueryOperator(
			gcp_cloudsql_conn_id='google_cloud_sql',
			sql= 'DROP TABLE IF EXISTS {{dag.default_args["ac_project_id"]}}."individualsegment_{{ts}}";',
			task_id='cleanup_temp_individualsegment_table',
			trigger_rule=TriggerRule.ALL_DONE
		)

		chain(
			get_data_replication_start_date, bq_individual_export_job,
			bq_individual_activity_export_job,
			bq_individual_interest_export_job, bq_membership_export_job,
			[postgresql_create_temp_individual_table_job, postgresql_create_temp_individualactivity_table_job, postgresql_create_temp_individualinterest_table_job, postgresql_create_temp_individualsegment_table_job],
			postgresql_replication_dataflow_trigger,
			[postgresql_merge_temp_individual_table_job, postgresql_merge_temp_individualactivity_table_job, postgresql_merge_temp_individualinterest_table_job, postgresql_merge_temp_individualsegment_table_job],
			[postgresql_cleanup_temp_individual_table_job, postgresql_cleanup_temp_individualactivity_table_job, postgresql_cleanup_temp_individualinterest_table_job, postgresql_cleanup_temp_individualsegment_table_job]
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
	enabled_features = project.get('enabledFeatures')

	if (enabled_features is None or 'API_REPORTS_POSTGRES_DATA_REPLICATION' not in enabled_features):
		continue

	dag_id = 'data_replication_{}'.format(
		project.get('id')
	)

	globals()[dag_id] = create_dag(
		project.get('id'), project.get('timeZoneId'), dag_id,
		'BigQuery Data Replication DAG For {}'.format(
			project.get('id')
		)
	)