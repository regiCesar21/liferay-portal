/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useState} from 'react';

const isCustomFilter = (filter) => filter.key === 'custom';

const useCustomFormState = () => {
	const [formVisible, setFormVisible] = useState(false);

	const onClickFilter = useCallback(
		(handleClick) => (currentItem) => {
			if (isCustomFilter(currentItem)) {
				setFormVisible(true);
			}
			else {
				handleClick(currentItem);
			}

			document.dispatchEvent(new Event('mousedown'));

			return true;
		},
		[]
	);

	return {
		formVisible,
		onClickFilter,
		setFormVisible,
	};
};

export {useCustomFormState};
