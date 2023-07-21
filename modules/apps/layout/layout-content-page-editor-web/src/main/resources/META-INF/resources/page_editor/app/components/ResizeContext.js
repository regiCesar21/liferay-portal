/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

const INITIAL_STATE = {
	resizing: false,
	setCustomRow: () => null,
	setResizing: () => null,
	setUpdatedLayoutData: () => null,
	updatedLayoutData: null,
};

const ResizeContext = React.createContext(INITIAL_STATE);

const ResizeContextProvider = ResizeContext.Provider;

const useResizeContext = () => {
	return useContext(ResizeContext).resizing;
};

const useSetResizeContext = () => {
	return useContext(ResizeContext).setResizing;
};

const useSetUpdatedLayoutDataContext = () => {
	return useContext(ResizeContext).setUpdatedLayoutData;
};

const useUpdatedLayoutDataContext = () => {
	return useContext(ResizeContext).updatedLayoutData;
};

export {
	ResizeContext,
	ResizeContextProvider,
	useResizeContext,
	useSetResizeContext,
	useSetUpdatedLayoutDataContext,
	useUpdatedLayoutDataContext,
};
