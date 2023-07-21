/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateEditableValuesAction from '../actions/updateEditableValues';
import updatePageContents from '../actions/updatePageContents';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';

export default function updateEditableValues({
	editableValues,
	fragmentEntryLinkId,
	segmentsExperienceId,
}) {
	return (dispatch) =>
		FragmentService.updateEditableValues({
			editableValues,
			fragmentEntryLinkId,
			onNetworkStatus: dispatch,
		})
			.then((fragmentEntryLink) => {
				dispatch(
					updateEditableValuesAction({
						content: fragmentEntryLink.content,
						editableValues,
						fragmentEntryLinkId,
						segmentsExperienceId,
					})
				);
			})
			.then(() => {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch,
				}).then((pageContents) => {
					dispatch(
						updatePageContents({
							pageContents,
						})
					);
				});
			});
}
