#!/bin/bash

PROJECT_ID=$(gcloud config get-value project)

BUCKET_NAME="${PROJECT_ID}-dataflow"

FLEX_TEMPLATE_BUCKET=$(gcloud storage ls --project=${PROJECT_ID} | grep -e "^gs://${BUCKET_NAME}/$" )

if [ -z "${FLEX_TEMPLATE_BUCKET}" ]
then
	echo "Unable to find bucket ${BUCKET_NAME}"

	exit 1
fi

gcloud storage folders create --recursive gs://${BUCKET_NAME}/flex-templates