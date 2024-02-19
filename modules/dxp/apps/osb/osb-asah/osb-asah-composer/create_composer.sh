#!/bin/bash

LCP_PROJECT_ID=${1:-asahdev}
NETWORK=${2:-default}
PROJECT_ID=$(gcloud config get-value project)
PROJECT_NUMBER=$(gcloud projects list \
	--filter="${PROJECT_ID}" \
	--format="value(PROJECT_NUMBER)")
REGION=$(gcloud config get-value compute/region)
SERVICE_ACCOUNT=ac-composer-admin@${PROJECT_ID}.iam.gserviceaccount.com

if [ "${NETWORK}" != "default" ];
then
	SUBNETWORK=${3}

	CUSTOM_NETWORK="--network ${NETWORK} --subnetwork ${SUBNETWORK}"
	CUSTOM_NETWORK_ENV=",NETWORK=${NETWORK},SUBNETWORK=${SUBNETWORK}"
fi

COMPOSER_ENVIRONMENT_NAME=ac-composer-${LCP_PROJECT_ID}

gcloud projects add-iam-policy-binding ${PROJECT_ID} \
	--member serviceAccount:${SERVICE_ACCOUNT} \
	--role roles/composer.ServiceAgentV2Ext

gcloud projects add-iam-policy-binding ${PROJECT_ID} \
	--member serviceAccount:service-${PROJECT_NUMBER}@cloudcomposer-accounts.iam.gserviceaccount.com \
	--role roles/composer.ServiceAgentV2Ext

gcloud composer environments create ${COMPOSER_ENVIRONMENT_NAME} \
	--airflow-configs=core-dagbag_import_timeout=120,scheduler-min_file_process_interval=1800 \
	--airflow-version=2 \
	--async \
	--enable-master-authorized-networks \
	--environment-size large \
	--env-variables LCP_PROJECT_ID=${LCP_PROJECT_ID},GOOGLE_PROJECT_ID=${PROJECT_ID},GOOGLE_REGION=${REGION}${CUSTOM_NETWORK_ENV} \
	--location ${REGION} \
	--master-authorized-networks ${MASTER_AUTHORIZED_NETWORKS} \
	--project ${PROJECT_ID} \
	--service-account ac-composer-admin@${PROJECT_ID}.iam.gserviceaccount.com \
	${CUSTOM_NETWORK}