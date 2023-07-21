/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {HideFromSearchField} from '../../../../app/components/fragment-configuration-fields/HideFromSearchField';
import getLayoutDataItemPropTypes from '../../../../prop-types/getLayoutDataItemPropTypes';

export default function FragmentAdvancedPanel({item}) {
	return <HideFromSearchField item={item} />;
}

FragmentAdvancedPanel.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};
