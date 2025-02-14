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

from airflow.providers.google.cloud.hooks.cloud_sql import CloudSQLHook
from airflow.providers.google.cloud.hooks.gcs import GCSHook
from airflow.providers.google.cloud.operators.cloud_sql import CloudSQLBaseOperator
from airflow.providers.google.common.hooks.base_google import PROVIDE_PROJECT_ID

class CloudSQLCSVImportOperator(CloudSQLBaseOperator):

	template_fields = (
		"bucket_name", "bucket_prefix", "project_id", "instance", "table",
		"gcp_conn_id", "api_version", "impersonation_chain",
	)

	def __init__(self, instance, bucket_name, bucket_prefix, database, table,
		project_id = PROVIDE_PROJECT_ID, gcp_conn_id = "google_cloud_default",
		api_version = "v1beta4", impersonation_chain = None, **kwargs):

		super().__init__(
			api_version=api_version, gcp_conn_id=gcp_conn_id,
			impersonation_chain=impersonation_chain, instance=instance,
			project_id=project_id, **kwargs
		)

		self.bucket_name = bucket_name
		self.bucket_prefix = bucket_prefix
		self.database = database
		self.instance = instance
		self.table = table

	def execute(self, context):
		gcs_hook = GCSHook(gcp_conn_id=self.gcp_conn_id)

		files = gcs_hook.list(
			bucket_name=self.bucket_name, prefix=self.bucket_prefix
		)

		csv_file_uris = []

		for file in files:
			if file.endswith(".csv"):
				csv_file_uris.append(f"gs://{self.bucket_name}/{file}")

		if not csv_file_uris:
			return

		cloud_sql_hook = CloudSQLHook(
			api_version=self.api_version, gcp_conn_id=self.gcp_conn_id,
			impersonation_chain=self.impersonation_chain
		)

		for csv_file_uri in csv_file_uris:
			body = {
				"importContext": {
					"csvImportOptions": {
						"table": self.table,
						"quoteCharacter": "22",
						"fieldsTerminatedBy": "3B"
					},
					"database": self.database,
					"fileType": "CSV",
					"uri": csv_file_uri
				}
			}

			cloud_sql_hook.import_instance(
				body=body, instance=self.instance, project_id=self.project_id
			)