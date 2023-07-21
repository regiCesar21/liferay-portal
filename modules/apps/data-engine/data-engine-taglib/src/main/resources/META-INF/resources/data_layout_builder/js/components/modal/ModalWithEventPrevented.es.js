/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

export default ({children}) => {
	const handleDocumentClick = (event) => {
		if (event.target.classList.contains('modal')) {
			return;
		}

		event.preventDefault();
	};

	useEffect(() => {
		document.addEventListener('click', handleDocumentClick);

		return () => {
			document.removeEventListener('click', handleDocumentClick);
		};
	}, []);

	return children;
};
