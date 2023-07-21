/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {dropLayoutBuilderField} from '../../../actions.es';
import FieldSets from '../../../components/field-sets/FieldSets.es';
import FieldTypeList from '../../../components/field-types/FieldTypeList.es';
import Sidebar from '../../../components/sidebar/Sidebar.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';

export default function ({keywords, setKeywords}) {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);

	const [
		{
			config: {allowFieldSets},
		},
	] = useContext(AppContext);

	const onDoubleClick = ({name}) => {
		const {activePage, pages} = dataLayoutBuilder.getStore();

		dataLayoutBuilder.dispatch(
			'fieldAdded',
			dropLayoutBuilderField({
				addedToPlaceholder: true,
				dataLayoutBuilder,
				fieldTypeName: name,
				indexes: {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: pages[activePage].rows.length,
				},
			})
		);
	};

	const fieldTypes = dataLayoutBuilder
		.getFieldTypes()
		.filter(({group}) => group === 'basic');

	fieldTypes.sort(({displayOrder: a}, {displayOrder: b}) => a - b);

	const tabs = [
		{
			label: Liferay.Language.get('fields'),
			render: () => (
				<FieldTypeList
					fieldTypes={fieldTypes}
					keywords={keywords}
					onDoubleClick={onDoubleClick}
				/>
			),
		},
	];

	if (allowFieldSets) {
		tabs.push({
			label: Liferay.Language.get('fieldsets'),
			render: () => <FieldSets keywords={keywords} />,
		});
	}

	return <Sidebar.Tabs setKeywords={setKeywords} tabs={tabs} />;
}
