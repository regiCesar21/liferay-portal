/* eslint-disable react-hooks/exhaustive-deps */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import React, {createContext, useContext, useEffect, useState} from 'react';

import {ErrorContext} from '../../../../shared/components/request/Error.es';
import {LoadingContext} from '../../../../shared/components/request/Loading.es';
import {baseURL, headers} from '../../../../shared/rest/fetch.es';
import {TimeRangeContext} from '../../filter/store/TimeRangeStore.es';
import {VelocityUnitContext} from '../../filter/store/VelocityUnitStore.es';

const useVelocityData = processId => {
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {getSelectedVelocityUnit} = useContext(VelocityUnitContext);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);
	const [velocityData, setVelocityData] = useState();

	const velocityTimeRange = getSelectedTimeRange();
	const velocityUnit = getSelectedVelocityUnit();

	const fetchData = (processId, dateEnd, dateStart, unitKey) => {
		setError(null);
		setLoading(true);

		fetch(
			`${baseURL}/processes/${processId}/metric?dateEnd=${dateEnd.toISOString()}&dateStart=${dateStart.toISOString()}&unit=${unitKey}`,
			{
				headers,
				method: 'GET'
			}
		)
			.then(response => response.json())
			.then(data => {
				setVelocityData(data);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	useEffect(() => {
		if (
			processId &&
			velocityTimeRange &&
			velocityTimeRange.dateEnd &&
			velocityTimeRange.dateStart &&
			velocityUnit
		) {
			fetchData(
				processId,
				velocityTimeRange.dateEnd,
				velocityTimeRange.dateStart,
				velocityUnit.key
			);
		}
	}, [processId, velocityUnit]);

	return {
		velocityData
	};
};

const VelocityDataContext = createContext();

const VelocityDataProvider = ({children, processId}) => {
	return (
		<VelocityDataContext.Provider value={useVelocityData(processId)}>
			{children}
		</VelocityDataContext.Provider>
	);
};

export {VelocityDataProvider, VelocityDataContext, useVelocityData};
