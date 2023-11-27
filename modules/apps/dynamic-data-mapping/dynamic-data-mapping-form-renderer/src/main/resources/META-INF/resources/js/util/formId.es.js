/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dom from 'metal-dom';

export const getFormNode = (element) => dom.closest(element, 'form');

export const getFormId = (form) => form?.dataset.ddmforminstanceid;

export const getFormTitle = (form = document) => {
	return form?.querySelector('.ddm-form-basic-info .ddm-form-name')
		?.innerHTML;
};
