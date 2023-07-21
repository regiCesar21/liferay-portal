/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useDrop as useDndDrop} from 'react-dnd';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {useForm} from '../hooks/useForm.es';
import {usePage} from './usePage.es';

const defaultSpec = {
	accept: [],
};

export const DND_ORIGIN_TYPE = {
	EMPTY: 'empty',
	FIELD: 'field',
};

export const useDrop = (sourceItem) => {
	const {dnd} = usePage();
	const dispatch = useForm();

	const spec = dnd ?? defaultSpec;

	const [{canDrop, overTarget}, drop] = useDndDrop({
		...spec,
		collect: (monitor) => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver(),
		}),
		drop: (item, monitor) =>
			dispatch({
				payload: {item, monitor, sourceItem},
				type: EVENT_TYPES.FIELD_DROP,
			}),
	});

	return {
		canDrop,
		drop,
		overTarget,
	};
};
