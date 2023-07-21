/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import {useSelector} from '../../store/index';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import Topper from '../Topper';
import Container from './Container';

const ContainerWithControls = React.forwardRef(({children, item}, ref) => {
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const [setRef, itemElement] = useSetRef(ref);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	const {widthType} = itemConfig;

	const {
		height,
		marginLeft,
		marginRight,
		maxWidth,
		minWidth,
		shadow,
		width,
	} = itemConfig.styles;

	const style = {};

	style.boxShadow = getFrontendTokenValue(shadow);
	style.maxWidth = maxWidth;
	style.minWidth = minWidth;
	style.width = width;

	return (
		<Topper
			className={classNames({
				container: widthType === 'fixed',
				[`ml-${marginLeft}`]: widthType !== 'fixed',
				[`mr-${marginRight}`]: widthType !== 'fixed',
				'p-0': widthType === 'fixed',
			})}
			item={item}
			itemElement={itemElement}
			style={style}
		>
			<Container
				className={classNames({
					empty: !item.children.length && !height,
					'page-editor__container': canUpdateItemConfiguration,
				})}
				item={item}
				ref={setRef}
			>
				{children}
			</Container>
		</Topper>
	);
});

ContainerWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default ContainerWithControls;
