/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import Collapse from './Collapse';
import {StyleBookContext} from './StyleBookContext';
import {FRONTEND_TOKEN_TYPES} from './constants/frontendTokenTypes';
import BooleanFrontendToken from './frontend_tokens/BooleanFrontendToken';
import ColorFrontendToken from './frontend_tokens/ColorFrontendToken';
import SelectFrontendToken from './frontend_tokens/SelectFrontendToken';
import TextFrontendToken from './frontend_tokens/TextFrontendToken';

export default function FrontendTokenSet({frontendTokens, label}) {
	const {frontendTokensValues = {}, setFrontendTokensValues} = useContext(
		StyleBookContext
	);

	const updateFrontendTokensValues = (frontendToken, value) => {
		const {mappings = [], name} = frontendToken;

		const cssVariableMapping = mappings.find(
			(mapping) => mapping.type === 'cssVariable'
		);

		if (value) {
			setFrontendTokensValues({
				...frontendTokensValues,
				[name]: {
					cssVariableMapping: cssVariableMapping.value,
					value,
				},
			});
		}
	};

	return (
		<Collapse label={label}>
			{frontendTokens.map((frontendToken) => {
				const FrontendTokenComponent = getFrontendTokenComponent(
					frontendToken
				);

				return (
					<FrontendTokenComponent
						frontendToken={frontendToken}
						key={frontendToken.name}
						onValueSelect={(value) =>
							updateFrontendTokensValues(frontendToken, value)
						}
						value={
							frontendTokensValues[frontendToken.name]?.value ||
							frontendToken.defaultValue
						}
					/>
				);
			})}
		</Collapse>
	);
}

function getFrontendTokenComponent(frontendToken) {
	if (frontendToken.editorType === 'ColorPicker') {
		return ColorFrontendToken;
	}

	if (frontendToken.validValues) {
		return SelectFrontendToken;
	}

	if (frontendToken.type === FRONTEND_TOKEN_TYPES.boolean) {
		return BooleanFrontendToken;
	}

	return TextFrontendToken;
}

FrontendTokenSet.propTypes = {
	frontendTokens: PropTypes.arrayOf(
		PropTypes.shape({
			name: PropTypes.string.isRequired,
		})
	),
	name: PropTypes.string.isRequired,
};
