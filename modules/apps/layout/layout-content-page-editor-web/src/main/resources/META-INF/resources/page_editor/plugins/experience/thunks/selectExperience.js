/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ExperienceService from '../../../app/services/ExperienceService';
import selectExperienceAction from '../actions/selectExperience';

export default function selectExperience({id}) {
	return (dispatch) => {
		return ExperienceService.selectExperience({
			body: {
				segmentsExperienceId: id,
			},
			dispatch,
		})
			.then((portletIds) => {
				return dispatch(
					selectExperienceAction({
						portletIds,
						segmentsExperienceId: id,
					})
				);
			})
			.catch((error) => {
				return error;
			});
	};
}
