#!/bin/bash

set -e

function build_and_push_docker_images {
	if [ "$(docker images -q liferaycloud/com-liferay-osb-asah-private 2> /dev/null)" ]
	then
		echo ""
		echo "Removing local images."

		docker rmi -f $(docker images -q liferaycloud/com-liferay-osb-asah-private) >/dev/null
	fi

	for file_name in `ls`
	do
		if [ -z "$(ls -A ${file_name}/LCP.*.json 2> /dev/null)" ] ||
		   [ ! -e ${file_name}/Dockerfile ]
		then
			continue
		fi

		local docker_image_tag=$(get_docker_image_tag ${file_name})

		build_docker_image ${docker_image_tag} ${file_name}

		echo ""
		echo "Pushing ${docker_image_tag}."
		echo ""

		docker push ${docker_image_tag}
	done
}

function build_docker_image {
	local docker_image_tag=${1}
	local file_name=${2}

	echo ""
	echo "Building ${docker_image_tag}."
	echo ""

	docker build \
		--build-arg LABEL_BUILD_DATE=$(date "${CURRENT_DATE}" +'%Y-%m-%dT%H:%M:%SZ') \
		--build-arg LABEL_VCS_REF=$(git rev-parse HEAD) \
		--build-arg LABEL_VCS_URL=$(git config --get remote.origin.url) \
		--tag ${docker_image_tag} \
		${file_name}

	git checkout --quiet ${file_name}/Dockerfile
}

function check_repository {
	gradlew formatSource

	if [ -n "$(git status --porcelain -uno)" ]
	then
		echo "There are source formatter changes. Please fix them and try again.";

		exit
	fi
}

function compile_repository {
	gradlew clean assemble
}

function date {
	export LC_ALL=en_US.UTF-8
	export TZ=America/Los_Angeles

	if [ -z ${1+x} ] || [ -z ${2+x} ]
	then
		if [ "$(uname)" == "Darwin" ]
		then
			/bin/date
		elif [ -e /bin/date ]
		then
			/bin/date --iso-8601=seconds
		else
			/usr/bin/date --iso-8601=seconds
		fi
	else
		if [ "$(uname)" == "Darwin" ]
		then
			/bin/date -jf "%a %b %e %H:%M:%S %Z %Y" "${1}" "${2}"
		elif [ -e /bin/date ]
		then
			/bin/date -d "${1}" "${2}"
		else
			/usr/bin/date -d "${1}" "${2}"
		fi
	fi
}

function generate_wedeploy_profile {
	local profile_name=${1}
	local service_name=${2}
	local file_path=${3}

	local destination_file_path=.wedeploy_profiles/${profile_name}/${service_name}

	mkdir -p ${destination_file_path}

	cp ${file_path} ${destination_file_path}/LCP.json

	local file_content=$(<${destination_file_path}/LCP.json)

	if [[ ${file_content} != *\"image\"* ]]
	then
		sed "s@\"id\"@\"image\": \"$(get_docker_image_tag ${service_name})\", \"id\"@" ${destination_file_path}/LCP.json

		python3 -m json.tool --sort-keys ${destination_file_path}/LCP.json > ${destination_file_path}/LCP.json.formatted

		mv ${destination_file_path}/LCP.json.formatted ${destination_file_path}/LCP.json

		sed $'s/    /\t/g' ${destination_file_path}/LCP.json

		perl -e 'chomp if eof' -pi ${destination_file_path}/LCP.json

		rm -f ${destination_file_path}/LCP.json.bak
	fi
}

function generate_wedeploy_profiles {
	rm -fr .wedeploy_profiles

	for file_path in **/LCP.*.json
	do
		local service_name="${file_path%%/*}"
		local profile_name="$(basename ${file_path#$service_name/LCP.} .json)"

		generate_wedeploy_profile ${profile_name} ${service_name} ${file_path}
	done

	git add .wedeploy_profiles

	git commit -m "Generate WeDeploy profiles at $(date "${CURRENT_DATE}" +'%Y%m%d')-${GIT_HASH}" .wedeploy_profiles
}

function generate_tag {
	git tag $(date "${CURRENT_DATE}" +'%Y%m%d')-${GIT_HASH} HEAD
}

function get_docker_image_tag {
	echo "liferaycloud/com-liferay-osb-asah-private:${1}-$(date "${CURRENT_DATE}" +'%Y%m%d')-${GIT_HASH}"
}

function gradlew {
	./gradlew "$@"

	if [ $? -ne 0 ]
	then
		exit 1
	fi
}

function main {
	check_repository

	compile_repository

	build_and_push_docker_images

	generate_wedeploy_profiles

	generate_tag

	push_to_github
}

function push_to_github {
	git push origin
	git push upstream
	git push upstream $(date "${CURRENT_DATE}" +'%Y%m%d')-${GIT_HASH}
}

function sed {
	if [ "$(uname)" == "Darwin" ]
	then
		/usr/bin/sed -i .bak "${1}" "${2}"
	else
		/usr/bin/sed -i "${1}" "${2}"
	fi
}

CURRENT_DATE=$(date)
GIT_HASH=$(git rev-parse --short=7 HEAD)

main