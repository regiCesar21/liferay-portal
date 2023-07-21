/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../../../app/config/index';
import ExperienceService from '../../../app/services/ExperienceService';
import deleteExperienceAction from '../actions/deleteExperience';
import selectExperienceAction from '../actions/selectExperience';

export default function removeExperience({
	segmentsExperienceId,
	selectedExperienceId,
}) {
	return (dispatch) => {
		if (segmentsExperienceId === selectedExperienceId) {
			return ExperienceService.selectExperience({
				body: {
					segmentsExperienceId: config.defaultSegmentsExperienceId,
				},
				dispatch,
			}).then((portletIds) => {
				dispatch(
					selectExperienceAction({
						portletIds,
						segmentsExperienceId:
							config.defaultSegmentsExperienceId,
					})
				);

				ExperienceService.removeExperience({
					body: {
						segmentsExperienceId,
					},
					dispatch,
				}).then(() => {
					return dispatch(
						deleteExperienceAction({
							segmentsExperienceId,
						})
					);
				});
			});
		}
		else {
			return ExperienceService.removeExperience({
				body: {
					segmentsExperienceId,
				},
				dispatch,
			}).then(() => {
				return dispatch(
					deleteExperienceAction({
						segmentsExperienceId,
					})
				);
			});
		}
	};
}
