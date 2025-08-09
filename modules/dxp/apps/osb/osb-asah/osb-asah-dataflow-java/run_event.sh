#!/bin/bash

if [ "$#" -lt 3 ]
then
	echo "Usage: run-event.sh [dxp-cloud-project] [network] [subnetwork]"
	exit 1
fi

PROJECT_ID=$(gcloud config get-value project)

DXP_CLOUD_PROJECT=${1}
MAX_NUMBER_WORKERS=10
NETWORK=${2}
OUTPUT_FOLDER=gs://${PROJECT_ID}-analytics-events
PIPELINE_FOLDER=gs://${PROJECT_ID}-dataflow
REGION=$(gcloud config get-value compute/region)
SESSION_WINDOW_ALLOWED_LATENESS=5
SESSION_WINDOW_GAP_DURATION=30
SUBNETWORK=${3}

gcloud dataflow flex-template run eventingestionpipeline-${DXP_CLOUD_PROJECT} \
	--additional-experiments=disableStringSetMetrics \
	--enable-streaming-engine \
	--max-workers=${MAX_NUMBER_WORKERS} \
	--network=${NETWORK} \
	--parameters="inputSubscription=projects/${PROJECT_ID}/subscriptions/${DXP_CLOUD_PROJECT}_analytics_events_dataflow,\
outputDirectory=${OUTPUT_FOLDER},\
outputFileNamePrefix=analytics-events,\
sessionWindowAllowedLateness=${SESSION_WINDOW_ALLOWED_LATENESS},\
sessionWindowGapDuration=${SESSION_WINDOW_GAP_DURATION}" \
	--project=${PROJECT_ID} \
	--region=${REGION} \
	--subnetwork=regions/${REGION}/subnetworks/${SUBNETWORK} \
	--temp-location=${PIPELINE_FOLDER}/temp \
	--template-file-gcs-location="${PIPELINE_FOLDER}/flex-templates/event-ingestion-pipeline.json"