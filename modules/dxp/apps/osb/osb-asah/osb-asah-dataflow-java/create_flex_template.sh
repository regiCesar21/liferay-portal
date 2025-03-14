#!/bin/bash

declare -A templates=( ["postgresql-replication-pipeline"]="com.liferay.osb.asah.dataflow.replication.PostgreSQLReplicationPipeline" )

if [ "$#" -lt 2 ]
then
	echo -e "Please provide template name and version as arguments.\nPossible template names:"
	printf '  * %s\n' "${!templates[@]}"

	GIT_TAGS=$(git tag --points-at HEAD)

	if [ ! -z "$GIT_TAGS" ]
	then
	  SUGGESTED_VERSION_NAMES=($GIT_TAGS)

	  echo -e "\nSuggested template version(s):"
	  printf '  * %s\n' "${SUGGESTED_VERSION_NAMES[@]}"
	fi

	exit 1
fi

if [ ! -v templates["$1"] ]
then
	echo "Template $1 does not exist. Possible values:"
	printf '  * %s\n' "${!templates[@]}"
	exit 1
fi

if [ ! -f build/libs/osb-asah-dataflow-java-all.jar ]
then
	echo "Unable to find JAR file build/libs/osb-asah-dataflow-java.jar. Please run gradlew clean shadowJar --refresh-dependencies."
	exit 1
fi

FLEX_TEMPLATE_JAVA_MAIN_CLASS=${templates["$1"]}
FLEX_TEMPLATE_NAME=$1
FLEX_TEMPLATE_VERSION=$2
PROJECT_ID=$(gcloud config get-value project)
REGION=$(gcloud config get-value compute/region)

ARTIFACT_REPOSITORY=$(gcloud artifacts repositories list --location=${REGION} --filter='name="projects/'"${PROJECT_ID}"'/locations/'"${REGION}"'/repositories/ac-dataflow"')

if [ -z "$ARTIFACT_REPOSITORY" ]
then
	echo "Unable to find repository in artifact registry. Please run create_flex_template_repository.sh"
	exit 1
fi

FLEX_TEMPLATE_BUCKET=$(gcloud storage ls gs://${PROJECT_ID}-dataflow/flex-templates | grep -e "gs://${PROJECT_ID}-dataflow/flex-templates/$" )

if [ -z "$FLEX_TEMPLATE_BUCKET" ]
then
	echo "Unable to find GCS bucket for flex templates. Please run create_flex_template_bucket.sh"
	exit 1
fi

gcloud dataflow flex-template build gs://${PROJECT_ID}-dataflow/flex-templates/${FLEX_TEMPLATE_NAME}.json \
	--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="${FLEX_TEMPLATE_JAVA_MAIN_CLASS}" \
	--flex-template-base-image JAVA21 \
	--image-gcr-path "${REGION}-docker.pkg.dev/${PROJECT_ID}/ac-dataflow/${FLEX_TEMPLATE_NAME}:$FLEX_TEMPLATE_VERSION" \
	--jar "build/libs/osb-asah-dataflow-java-all.jar" \
	--metadata-file "flex-templates/metadata/${FLEX_TEMPLATE_NAME}.json" \
	--sdk-language "JAVA"