/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {config} from '../config/index';

let nextId = 0;

export const useId = () => {
	return useMemo(() => `${config.portletNamespace}_useId_${nextId++}`, []);
};
