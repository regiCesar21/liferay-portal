/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useStateSafe} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect} from 'react';

import ConnectionContext from '../context/ConnectionContext';
import {StoreContext, useWarning} from '../context/StoreContext';
import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';

function TotalCount({
	className,
	dataProvider,
	label,
	languageTag,
	percentage = false,
	popoverAlign,
	popoverHeader,
	popoverMessage,
	popoverPosition,
}) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [value, setValue] = useStateSafe('-');

	const [, addWarning] = useWarning();

	const [{publishedToday}] = useContext(StoreContext);

	useEffect(() => {
		if (validAnalyticsConnection) {
			dataProvider()
				.then(setValue)
				.catch(() => {
					setValue('-');
					addWarning();
				});
		}
	}, [addWarning, dataProvider, setValue, validAnalyticsConnection]);

	let displayValue = '-';

	if (validAnalyticsConnection && !publishedToday) {
		displayValue =
			value !== '-' ? (
				percentage ? (
					<span>{`${value}%`}</span>
				) : (
					numberFormat(languageTag, value)
				)
			) : (
				value
			);
	}

	return (
		<div className={className}>
			<span className="text-secondary">{label}</span>
			<span className="text-secondary">
				<Hint
					align={popoverAlign}
					message={popoverMessage}
					position={popoverPosition}
					title={popoverHeader}
				/>
			</span>
			<span className="font-weight-bold">{displayValue}</span>
		</div>
	);
}

TotalCount.propTypes = {
	dataProvider: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired,
	languageTag: PropTypes.string,
	percentage: PropTypes.bool,
	popoverHeader: PropTypes.string.isRequired,
	popoverMessage: PropTypes.string.isRequired,
};

export default TotalCount;
