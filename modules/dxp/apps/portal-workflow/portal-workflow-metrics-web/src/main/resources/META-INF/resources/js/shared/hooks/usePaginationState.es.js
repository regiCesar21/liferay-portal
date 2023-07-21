/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useMemo, useState} from 'react';

import {AppContext} from '../../components/AppContext.es';
import {paginateArray} from '../util/array.es';

const usePaginationState = (props) => {
	const {defaultDelta} = useContext(AppContext);
	const {
		initialPage = 1,
		initialPageSize = defaultDelta,
		items = false,
	} = props;

	const defaultPageSize =
		initialPageSize <= defaultDelta ? initialPageSize : defaultDelta;
	const [page, setPage] = useState(initialPage);
	const [pageSize, setPageSize] = useState(defaultPageSize);

	const pagination = useMemo(
		() => ({
			page,
			pageSize,
			setPage,
			setPageSize,
		}),
		[page, pageSize]
	);

	const paginatedItems = useMemo(
		() => (items ? paginateArray(items, page, pageSize) : []),
		[page, pageSize, items]
	);

	return {
		page,
		pageSize,
		paginatedItems,
		pagination,
	};
};

export {usePaginationState};
