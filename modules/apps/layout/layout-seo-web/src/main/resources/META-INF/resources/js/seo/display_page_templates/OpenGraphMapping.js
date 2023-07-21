/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';

import MappingInputs from './components/MappingInputs';
import lang from './utils/lang';

export default function OpenGraphMapping({
	fields,
	openGraphDescription,
	openGraphImage,
	openGraphImageAlt,
	openGraphTitle,
	portletNamespace,
	selectedSource,
}) {
	return (
		<MappingInputs
			fields={fields}
			inputs={[
				{
					fieldType: 'text',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('text'),
						Liferay.Language.get('title')
					),
					label: Liferay.Language.get('title'),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphTitle--`,
					selectedFieldKey: openGraphTitle,
				},
				{
					fieldType: 'text',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('text'),
						Liferay.Language.get('description')
					),
					label: Liferay.Language.get('description'),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphDescription--`,
					selectedFieldKey: openGraphDescription,
				},
				{
					fieldType: 'image',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('image'),
						Liferay.Language.get('image')
					),
					label: Liferay.Language.get('image'),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphImage--`,
					selectedFieldKey: openGraphImage,
				},
				{
					fieldType: 'text',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('text'),
						Liferay.Language.get('open-graph-image-alt-description')
					),
					label: Liferay.Language.get(
						'open-graph-image-alt-description'
					),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphImageAlt--`,
					selectedFieldKey: openGraphImageAlt,
				},
			]}
			selectedSource={selectedSource}
		/>
	);
}

OpenGraphMapping.propTypes = {
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	openGraphDescription: PropTypes.string,
	openGraphImage: PropTypes.string,
	openGraphImageAlt: PropTypes.string,
	openGraphTitle: PropTypes.string,
	selectedSource: PropTypes.shape({
		classNameLabel: PropTypes.string,
		classTypeLabel: PropTypes.string,
	}).isRequired,
};
