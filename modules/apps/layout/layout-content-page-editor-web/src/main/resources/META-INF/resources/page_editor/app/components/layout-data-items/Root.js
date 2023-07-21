/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import TopperEmpty from '../TopperEmpty';

const Root = React.forwardRef(({children, item}, ref) => {
	return (
		<TopperEmpty item={item}>
			<div className={classNames('page-editor__root')} ref={ref}>
				{React.Children.count(children) ? (
					children
				) : (
					<div
						className={classNames(
							'page-editor__no-fragments-message'
						)}
					>
						<div className="page-editor__no-fragments-message__title">
							{Liferay.Language.get('place-fragments-here')}
						</div>
					</div>
				)}
			</div>
		</TopperEmpty>
	);
});

Root.displayName = 'Root';

Root.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default Root;
