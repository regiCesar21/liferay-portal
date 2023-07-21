/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';

import MappingInput from './MappingInput';

function MappingInputs({fields, inputs, selectedSource}) {
	return (
		<>
			{inputs.map((props) => (
				<MappingInput
					initialFields={fields}
					key={props.name}
					selectedSource={selectedSource}
					{...props}
				/>
			))}
		</>
	);
}

MappingInputs.propTypes = {
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	inputs: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			name: PropTypes.string,
			selectedFieldKey: PropTypes.string,
		})
	).isRequired,
};

export default MappingInputs;
