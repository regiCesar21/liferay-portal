/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ERROR_MESSAGES} from '../errorMessages';
import isEmpty from '../functions/is_empty';

export default function validateRequired(
	configValue,
	type,
	required = true,
	nullable = false
) {
	if (!required || nullable) {
		return;
	}

	if (isEmpty(configValue, type)) {
		return ERROR_MESSAGES.REQUIRED;
	}
}
