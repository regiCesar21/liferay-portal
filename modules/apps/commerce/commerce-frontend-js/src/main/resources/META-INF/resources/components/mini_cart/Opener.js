/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import MiniCartContext from './MiniCartContext';

function Opener({openCart}) {
	const {cartState, displayTotalItemsQuantity, spritemap} = useContext(
		MiniCartContext
	);

	const {cartItems, itemsQuantity: initialItemsQuantity, summary} = cartState,
		[numberOfItems, setNumberOfItems] = useState(0);

	useEffect(() => {
		setNumberOfItems(initialItemsQuantity);

		return () => {};
	}, [initialItemsQuantity, setNumberOfItems]);

	useEffect(() => {
		const itemsQuantityCountSource = displayTotalItemsQuantity
			? summary
			: cartItems;

		if (itemsQuantityCountSource) {
			setNumberOfItems(
				itemsQuantityCountSource.itemsQuantity ||
					itemsQuantityCountSource.length
			);
		}
	}, [cartItems, displayTotalItemsQuantity, summary, setNumberOfItems]);

	return (
		<button
			className={classnames(
				'mini-cart-opener',
				!!numberOfItems && 'has-badge'
			)}
			data-badge-count={numberOfItems}
			onClick={openCart}
		>
			<ClayIcon spritemap={spritemap} symbol={'shopping-cart'} />
		</button>
	);
}

Opener.propTypes = {
	openCart: PropTypes.func,
};

export default Opener;
