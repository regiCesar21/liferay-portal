/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addMappedInfoItems} from '../actions/index';
import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import updatePageContents from '../actions/updatePageContents';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {config} from '../config/index';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';

export default function updateFragmentConfiguration({
	configurationValues,
	fragmentEntryLink,
}) {
	const {editableValues, fragmentEntryLinkId} = fragmentEntryLink;

	const nextEditableValues = {
		...editableValues,
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: configurationValues,
	};

	return (dispatch) => {
		return FragmentService.updateConfigurationValues({
			configurationValues: nextEditableValues,
			fragmentEntryLinkId,
			onNetworkStatus: dispatch,
		})
			.then(({fragmentEntryLink, layoutData}) => {
				dispatch(
					updateFragmentEntryLinkConfiguration({
						fragmentEntryLink,
						fragmentEntryLinkId,
						layoutData,
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
							segmentsExperienceId:
								config.defaultSegmentsExperienceId,
						})
					);

					dispatch(
						addMappedInfoItems(
							pageContents.filter(
								(element) =>
									element.classNameId && element.classPK
							)
						)
					);
				});
			});
	};
}
