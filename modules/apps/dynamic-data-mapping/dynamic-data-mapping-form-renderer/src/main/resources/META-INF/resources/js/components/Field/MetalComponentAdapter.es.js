/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef} from 'react';

import {EVENT_TYPES} from '../../actions/eventTypes.es';
import {useForm} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import MetalFieldAdapter from './MetalFieldAdapter.es';

class NoRender extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		const {forwardRef, ...otherProps} = this.props;

		return <div ref={forwardRef} {...otherProps} />;
	}
}

export const MetalComponentAdapter = ({
	onBlur,
	onChange,
	onFocus,
	type,
	...field
}) => {
	const {activePage, editable, pageIndex, spritemap} = usePage();
	const dispatch = useForm();

	const component = useRef(null);
	const container = useRef(null);

	useEffect(() => {
		if (!component.current && container.current) {
			component.current = new MetalFieldAdapter(
				{
					activePage,
					editable,
					field,
					onBlur,
					onChange,
					onFocus,
					onRemoved: (_, event) =>
						dispatch({
							payload: event,
							type: EVENT_TYPES.FIELD_REMOVED,
						}),
					onRepeated: (_, event) =>
						dispatch({
							payload: event,
							type: EVENT_TYPES.FIELD_REPEATED,
						}),
					pageIndex,
					spritemap,
					type,
				},
				container.current
			);
		}

		return () => {
			if (component.current) {
				component.current.dispose();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (component.current) {
			component.current.setState({
				activePage,
				editable,
				field,
				onChange,
				pageIndex,
				spritemap,
			});
		}
	}, [activePage, editable, onChange, pageIndex, spritemap, field]);

	return <NoRender forwardRef={container} />;
};
