/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Pages from '../components/Pages.es';
import {FormNoopProvider} from '../hooks/useForm.es';

/**
 * It is an implementation of Form no-op, it just renders the
 * layout and propagates any event to the instance `dispatch`.
 */
export const FormNoop = React.forwardRef(({instance, ...otherProps}, ref) => (
	<FormNoopProvider
		onEvent={(type, payload) => instance?.context.dispatch(type, payload)}
	>
		<Pages {...otherProps} ref={ref} />
	</FormNoopProvider>
));

FormNoop.displayName = 'FormNoop';
