/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import Topper from '../Topper';
import Collection from './Collection';

const CollectionWithControls = React.forwardRef(({children, item}, ref) => {
	const [setRef, itemElement] = useSetRef(ref);

	return (
		<Topper item={item} itemElement={itemElement}>
			<Collection item={item} ref={setRef}>
				{children}
			</Collection>
		</Topper>
	);
});

CollectionWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default CollectionWithControls;
