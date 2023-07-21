/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {CollectionItemContext} from '../CollectionItemContext';
import TopperEmpty from '../TopperEmpty';

const CollectionItemWithControls = React.forwardRef(({children, item}, ref) => {
	const {collectionItem} = useContext(CollectionItemContext);

	return (
		<div className="page-editor__collection__block">
			<TopperEmpty item={item}>
				{React.Children.count(children) === 0 ? (
					<div className="page-editor__collection-item" ref={ref}>
						<div className="page-editor__collection-item__border">
							<p className="page-editor__collection-item__title">
								{collectionItem.title ||
									collectionItem.defaultTitle}
							</p>
						</div>
					</div>
				) : (
					<div ref={ref}>{children}</div>
				)}
			</TopperEmpty>
		</div>
	);
});

export default CollectionItemWithControls;
