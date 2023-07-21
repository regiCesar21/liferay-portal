/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import Topper from '../Topper';
import FragmentContent from '../fragment-content/FragmentContent';
import getAllPortals from './getAllPortals';

const FragmentWithControls = React.forwardRef(({item}, ref) => {
	const getPortals = useCallback((element) => getAllPortals(element), []);

	const [setRef, itemElement] = useSetRef(ref);

	return (
		<Topper item={item} itemElement={itemElement}>
			<FragmentContent
				elementRef={setRef}
				fragmentEntryLinkId={item.config.fragmentEntryLinkId}
				getPortals={getPortals}
				item={item}
				withinTopper
			/>
		</Topper>
	);
});

FragmentWithControls.displayName = 'FragmentWithControls';

FragmentWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default FragmentWithControls;
