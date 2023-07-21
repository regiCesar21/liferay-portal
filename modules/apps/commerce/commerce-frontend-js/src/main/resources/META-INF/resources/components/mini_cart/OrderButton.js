/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useContext} from 'react';

import {liferayNavigate} from '../../utilities/index';
import MiniCartContext from './MiniCartContext';
import {
	REVIEW_ORDER,
	SUBMIT_ORDER,
	WORKFLOW_STATUS_APPROVED,
} from './util/constants';
import {hasErrors} from './util/index';

function OrderButton() {
	const {actionURLs, cartState, labels} = useContext(MiniCartContext);

	const {checkoutURL, orderDetailURL} = actionURLs;
	const {cartItems = [], workflowStatusInfo = {}} = cartState;

	const errors = hasErrors(cartItems);
	const workflowStatus = workflowStatusInfo?.code || WORKFLOW_STATUS_APPROVED;

	return (
		<div className={'mini-cart-submit'}>
			<ClayButton
				block
				disabled={!cartItems.length}
				onClick={() => {
					liferayNavigate(errors ? orderDetailURL : checkoutURL);
				}}
			>
				{!errors && workflowStatus === WORKFLOW_STATUS_APPROVED
					? labels[SUBMIT_ORDER]
					: labels[REVIEW_ORDER]}
			</ClayButton>
		</div>
	);
}

export default OrderButton;
