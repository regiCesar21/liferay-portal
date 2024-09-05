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
from airflow.operators.bash import BashOperator

import airflow
import pendulum

scale_down_schedule_interval = Variable.get(
	'osb.asah.composer.scale.down.schedule.interval', None
)

scale_up_schedule_interval = Variable.get(
	'osb.asah.composer.scale.up.schedule.interval', None
)

if scale_down_schedule_interval and scale_up_schedule_interval:
	default_min_workers = Variable.get('osb.asah.composer.default.min.workers', 1)

	scaled_min_workers = Variable.get('osb.asah.composer.scaled.min.workers', 2)

	with airflow.DAG(
		dag_id='composer_proactive_scale_down',
		default_args={
			'owner': 'Liferay'
		},
		description='Proactively Scale DOWN the min workers count',
		max_active_runs=1,
		schedule_interval=scale_down_schedule_interval,
		start_date=pendulum.now() - pendulum.duration(days=2)
	) as dag_down:

		bash_operator = BashOperator(
			bash_command="gcloud composer environments update ${COMPOSER_ENVIRONMENT} --location ${COMPOSER_LOCATION} --min-workers " + str(default_min_workers),
			task_id="scale_down_bash_operator"
		)

	with airflow.DAG(
		dag_id='composer_proactive_scale_up',
		default_args={
			'owner': 'Liferay'
		},
		description='Proactively Scale UP the min workers count',
		max_active_runs=1,
		schedule_interval=scale_up_schedule_interval,
		start_date=pendulum.now() - pendulum.duration(days=2)
	) as dag_up:

		bash_operator = BashOperator(
			bash_command="gcloud composer environments update ${COMPOSER_ENVIRONMENT} --location ${COMPOSER_LOCATION} --min-workers " + str(scaled_min_workers),
			task_id="scale_up_bash_operator"
		)