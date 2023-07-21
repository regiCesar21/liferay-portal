/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import moment from 'moment';
import React, {useContext} from 'react';

import {removeEmptyValues} from '../../utils/data.es';
import Color from '../color/Color.es';
import {SidebarContext} from '../sidebar/SidebarContext.es';

export default ({data, field, summary, totalEntries, type}) => {
	const {portletNamespace, toggleSidebar} = useContext(SidebarContext);

	const formatDate = (field) => {
		const locale = themeDisplay.getLanguageId().split('_', 1).join('');

		return moment(field).locale(locale).format('L');
	};

	const checkType = (field, type) => {
		switch (type) {
			case 'color':
				return <Color hexColor={field} />;
			case 'date':
				return formatDate(field);
			default:
				return field;
		}
	};

	data = removeEmptyValues(data);

	return (
		<div className="field-list">
			<ul className="entries-list">
				{Array.isArray(data) &&
					data.map((field, index) => (
						<li key={index}>{checkType(field, type)}</li>
					))}

				{data.length == 5 && totalEntries > 5 ? (
					<li id={`${portletNamespace}-see-more`} key={'see-more'}>
						<ClayButton
							displayType="link"
							onClick={() =>
								toggleSidebar(
									field,
									summary,
									totalEntries,
									type
								)
							}
						>
							{Liferay.Language.get('see-all-entries')}
						</ClayButton>
					</li>
				) : null}
			</ul>
		</div>
	);
};
