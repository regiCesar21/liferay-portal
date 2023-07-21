/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useMemo} from 'react';

import togglePermissions from '../../../app/actions/togglePermission';
import {config} from '../../../app/config/index';
import {useDispatch, useSelector} from '../../../app/store/index';
import ExperienceSelector from './ExperienceSelector';

// TODO: show how to colocate CSS with plugins (may use loaders)

export default function ExperienceToolbarSection({selectId}) {
	const availableSegmentsExperiences = useSelector(
		(state) => state.availableSegmentsExperiences
	);
	const dispatch = useDispatch();

	const segmentsExperienceId = useSelector(
		(state) => state.segmentsExperienceId
	);

	const experiences = useMemo(
		() =>
			Object.values(availableSegmentsExperiences)
				.sort((a, b) => b.priority - a.priority)
				.map((experience, _, experiences) => {
					const segmentsEntryName =
						config.availableSegmentsEntries[
							experience.segmentsEntryId
						].name;

					const firstExperience = experiences.find(
						(exp) =>
							exp.segmentsEntryId ===
								experience.segmentsEntryId ||
							exp.segmentsEntryId ===
								config.defaultSegmentsEntryId
					);

					return {
						...experience,
						active:
							firstExperience.segmentsExperienceId ===
							experience.segmentsExperienceId,
						segmentsEntryName,
					};
				}),
		[availableSegmentsExperiences]
	);
	const segments = useMemo(
		() => Object.values(config.availableSegmentsEntries),
		[]
	);

	const selectedExperience =
		availableSegmentsExperiences[segmentsExperienceId];

	useEffect(() => {
		dispatch(
			togglePermissions(
				'LOCKED_SEGMENTS_EXPERIMENT',
				selectedExperience.hasLockedSegmentsExperiment
			)
		);
	}, [dispatch, selectedExperience.hasLockedSegmentsExperiment]);

	return (
		<div className="mr-2 page-editor__toolbar-experience">
			<label className="d-lg-block d-none mr-2" htmlFor={selectId}>
				{Liferay.Language.get('experience')}
			</label>

			<ExperienceSelector
				editSegmentsEntryURL={config.editSegmentsEntryURL}
				experiences={experiences}
				segments={segments}
				selectId={selectId}
				selectedExperience={selectedExperience}
			/>
		</div>
	);
}
