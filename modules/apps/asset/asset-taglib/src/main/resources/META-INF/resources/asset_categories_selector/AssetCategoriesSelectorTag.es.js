/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useState} from 'react';

import AssetCategoriesSelector from './AssetCategoriesSelector.es';
import {ASSET_VOCABULARY_VISIBILITY_TYPES} from './assetVocabularyVisibilityTypes.es';

function AssetCategoriesSelectorTag({
	eventName,
	groupIds = [],
	id,
	initialVocabularies = [],
	inputName,
	portletURL,
}) {
	const [vocabularies, setVocabularies] = useState(initialVocabularies);

	return (
		<AssetCategoriesSelector
			eventName={eventName}
			groupIds={groupIds}
			id={id}
			inputName={inputName}
			onVocabulariesChange={setVocabularies}
			portletURL={portletURL}
			vocabularies={vocabularies}
		/>
	);
}

AssetCategoriesSelectorTag.propTypes = {
	eventName: PropTypes.string,
	groupIds: PropTypes.array,
	id: PropTypes.string,
	initialVocabularies: PropTypes.array,
	inputName: PropTypes.string,
	learnHowURL: PropTypes.string,
	portletURL: PropTypes.string,
};

export default function (props) {
	const initialPublicVocabularies = props.vocabularies.filter(
		(vocabulary) =>
			vocabulary.visibilityType ===
			ASSET_VOCABULARY_VISIBILITY_TYPES.public
	);
	const initialInternalVocabularies = props.vocabularies.filter(
		(vocabulary) =>
			vocabulary.visibilityType ===
			ASSET_VOCABULARY_VISIBILITY_TYPES.internal
	);

	return (
		<>
			{props.learnHowURL && (
				<p
					className="text-secondary"
					dangerouslySetInnerHTML={{
						__html: Liferay.Util.sub(
							Liferay.Language.get(
								'x-learn-how-x-to-tailor-categories-to-your-needs'
							),
							`<a href=${props.learnHowURL} target="_blank">`,
							'</a>'
						),
					}}
				/>
			)}

			{initialPublicVocabularies && initialPublicVocabularies.length > 0 && (
				<>
					<div className="border-0 mb-0 sheet-subtitle text-uppercase">
						{Liferay.Language.get('public-categories')}
					</div>

					<p className="text-secondary">
						{Liferay.Language.get(
							'they-can-be-displayed-through-pages-widgets-fragments-and-searches'
						)}
					</p>

					<AssetCategoriesSelectorTag
						{...props}
						initialVocabularies={initialPublicVocabularies}
					/>
				</>
			)}

			{initialInternalVocabularies &&
				initialInternalVocabularies.length > 0 && (
					<>
						<div className="border-0 mb-0 sheet-subtitle text-uppercase">
							{Liferay.Language.get('internal-categories')}
						</div>

						<p className="text-secondary">
							{Liferay.Language.get(
								'they-are-displayed-inside-the-administration-only'
							)}
						</p>

						<AssetCategoriesSelectorTag
							{...props}
							initialVocabularies={initialInternalVocabularies}
						/>
					</>
				)}
		</>
	);
}
