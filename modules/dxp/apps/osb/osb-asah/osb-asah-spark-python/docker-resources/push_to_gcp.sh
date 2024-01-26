#!/bin/bash

if [ -z "$GIT_HASH" ] || [ -z "$PROJECT_ID" ] || [ -z "$REGION" ]
then
	echo "Set the environment set variables \"GIT_HASH\", \"PROJECT_ID\", and \"REGION.\"";

	exit 1
fi

CURRENT_DATE=$(date)
GCP_BUCKET=gs://${PROJECT_ID}-dataproc-${REGION}

function date {
	export LC_ALL=en_US.UTF-8

	if [ -z ${1+x} ] || [ -z ${2+x} ]
	then
		if [ "$(uname)" == "Darwin" ]
		then
			echo $(/bin/date)
		elif [ -e /bin/date ]
		then
			echo $(/bin/date)
		else
			echo $(/usr/bin/date)
		fi
	else
		if [ "$(uname)" == "Darwin" ]
		then
			echo $(/bin/date -jf "%a %b %e %H:%M:%S %Z %Y" "${1}" "${2}")
		elif [ -e /bin/date ]
		then
			echo $(/bin/date -d "${1}" "${2}")
		else
			echo $(/usr/bin/date -d "${1}" "${2}")
		fi
	fi
}

function gradlew {
	../gradlew "$@"

	if [[ $? -ne 0 ]]
	then
		exit 1
	fi
}

function replace_yaml_files_env_variables {
	local resources_build="build/resources/main"
	local resources_src="src/main/resources"

	mkdir -p ${resources_build}

	for file_name in `ls ${resources_src}/*.yaml | xargs -n 1 basename`
	do
		envsubst < ${resources_src}/${file_name} > ${resources_build}/${file_name}
	done
}

echo ""
echo "Pushing osb-asah-spark-python-driver.py to ${GCP_BUCKET}."
echo ""

gsutil -h x-goog-meta-git-hash:${GIT_HASH} cp build/libs/osb-asah-spark-python-driver.py ${GCP_BUCKET}

echo ""
echo "Pushing osb-asah-spark-python.zip to ${GCP_BUCKET}."
echo ""

gsutil -h x-goog-meta-git-hash:${GIT_HASH} cp build/libs/osb-asah-spark-python.zip ${GCP_BUCKET}

replace_yaml_files_env_variables

echo ""
echo "Pushing resources to ${GCP_BUCKET}/resources."
echo ""

gsutil -h x-goog-meta-git-hash:${GIT_HASH} cp build/resources/main/*.* ${GCP_BUCKET}/resources/