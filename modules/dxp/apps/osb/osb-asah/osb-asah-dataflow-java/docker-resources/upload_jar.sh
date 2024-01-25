#!/bin/bash

if [ -z "$PROJECT_ID" ]
then
	echo "Set the environment variable \"PROJECT_ID\""

	exit 1
fi

if [ -f build/libs/osb-asah-dataflow-java.jar ]
then
	echo "Unable to find JAR file build/libs/osb-asah-dataflow-java.jar"

	exit 1
fi

gcloud storage cp build/libs/osb-asah-dataflow-java.jar gs://${PROJECT_ID}-dataflow/pipeline/