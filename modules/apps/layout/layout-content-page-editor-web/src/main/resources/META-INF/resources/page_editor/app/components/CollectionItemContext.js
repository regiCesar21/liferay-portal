/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useContext, useEffect} from 'react';

import {updateFragmentEntryLinkContent} from '../actions/index';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';
import {useDispatch} from '../store/index';

const defaultFromControlsId = (itemId) => itemId;
const defaultToControlsId = (controlId) => controlId;

export const INITIAL_STATE = {
	collectionConfig: null,
	collectionItem: null,
	collectionItemIndex: null,
	fromControlsId: defaultFromControlsId,
	setCollectionItemContent: () => null,
	toControlsId: defaultToControlsId,
};

const CollectionItemContext = React.createContext(INITIAL_STATE);

const CollectionItemContextProvider = CollectionItemContext.Provider;

const useFromControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.fromControlsId || defaultFromControlsId;
};

const useCollectionItemIndex = () => {
	const context = useContext(CollectionItemContext);

	return context.collectionItemIndex;
};

const useToControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.toControlsId || defaultToControlsId;
};

const useCollectionConfig = () => {
	const context = useContext(CollectionItemContext);

	return context.collectionConfig;
};

const useGetContent = (fragmentEntryLink, segmentsExperienceId) => {
	const context = useContext(CollectionItemContext);
	const dispatch = useDispatch();

	const {className, classPK} = context.collectionItem || {};

	useEffect(() => {
		if (context.collectionItemIndex != null) {
			FragmentService.renderFragmentEntryLinkContent({
				collectionItemClassName: className,
				collectionItemClassPK: classPK,
				fragmentEntryLinkId: fragmentEntryLink.fragmentEntryLinkId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			}).then(({content}) => {
				dispatch(
					updateFragmentEntryLinkContent({
						collectionItemIndex: context.collectionItemIndex,
						content,
						fragmentEntryLinkId:
							fragmentEntryLink.fragmentEntryLinkId,
					})
				);
			});
		}
	}, [
		className,
		classPK,
		context.collectionItemIndex,
		dispatch,
		fragmentEntryLink.fragmentEntryLinkId,
		segmentsExperienceId,
		fragmentEntryLink.editableValues,
	]);

	if (context.collectionItemIndex != null) {
		const collectionContent = fragmentEntryLink.collectionContent || [];

		return (
			collectionContent[context.collectionItemIndex] ||
			fragmentEntryLink.content
		);
	}

	return fragmentEntryLink.content;
};

const useGetFieldValue = () => {
	const {collectionItem} = useContext(CollectionItemContext);

	const getFromServer = useCallback(
		({classNameId, classPK, fieldId, languageId}) =>
			InfoItemService.getInfoItemFieldValue({
				classNameId,
				classPK,
				fieldId,
				languageId,
				onNetworkStatus: () => {},
			}).then((response) => {
				const {fieldValue = ''} = response;

				return fieldValue;
			}),
		[]
	);

	const getFromCollectionItem = useCallback(
		({collectionFieldId}) =>
			collectionItem[collectionFieldId] !== null &&
			collectionItem[collectionFieldId] !== undefined
				? Promise.resolve(collectionItem[collectionFieldId])
				: Promise.reject(),
		[collectionItem]
	);

	if (collectionItem) {
		return getFromCollectionItem;
	}
	else {
		return getFromServer;
	}
};

const useRenderFragmentContent = () => {
	const context = useContext(CollectionItemContext);

	const {className, classPK} = context.collectionItem || {};

	return useCallback(
		({fragmentEntryLinkId, onNetworkStatus, segmentsExperienceId}) => {
			return FragmentService.renderFragmentEntryLinkContent({
				collectionItemClassName: className,
				collectionItemClassPK: classPK,
				fragmentEntryLinkId,
				onNetworkStatus,
				segmentsExperienceId,
			}).then(({content}) => {
				return {
					collectionItemIndex: context.collectionItemIndex,
					content,
				};
			});
		},
		[className, classPK, context.collectionItemIndex]
	);
};

export {
	CollectionItemContext,
	CollectionItemContextProvider,
	useRenderFragmentContent,
	useGetContent,
	useCollectionConfig,
	useCollectionItemIndex,
	useFromControlsId,
	useToControlsId,
	useGetFieldValue,
};
