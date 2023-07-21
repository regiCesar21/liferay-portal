/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {logError} from '../utilities/logError';
import TooltipTextRenderer from './TooltipTextRenderer';

function DefaultRenderer({value}) {
	if (
		typeof value === 'number' ||
		typeof value === 'string' ||
		value === undefined ||
		value === null
	) {
		return <>{value ?? ''}</>;
	}
	else if (value.icon) {
		return <ClayIcon symbol={value.icon} />;
	}
	else if (!!value.iconSymbol && !!value.text) {
		return <TooltipTextRenderer value={value} />;
	}
	else if (value.label) {
		return <>{value.label}</>;
	}

	logError(
		`The object ${JSON.stringify(value)} doesn't match the template schema`
	);

	return null;
}

DefaultRenderer.propTypes = {
	value: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.number,
		PropTypes.shape({
			label: PropTypes.string,
		}),
		PropTypes.shape({
			icon: PropTypes.string,
		}),
		PropTypes.shape({
			iconSymbol: PropTypes.string,
			text: PropTypes.string,
		}),
	]),
};

export default DefaultRenderer;
