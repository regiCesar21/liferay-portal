/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput, ClaySelect} from '@clayui/form';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import ExportTranslationContext from './ExportTranslationContext.es';

const noop = () => {};

const ExportTranslationModal = ({
	articleIds,
	availableExportFileFormats,
	availableSourceLocales,
	availableTargetLocales,
	defaultSourceLanguageId,
	exportTranslationURL,
	observer,
	onModalClose = noop,
}) => {
	const {namespace} = useContext(ExportTranslationContext);

	const [exportMimeType, setExportMimeType] = useState(
		availableExportFileFormats[0].mimeType
	);

	const [sourceLanguageId, setSourceLanguageId] = useState(
		defaultSourceLanguageId
	);

	const [selectedTargetLanguageIds, setSelectedTargetLanguageIds] = useState(
		[]
	);

	const exportTranslationPortletURL = Liferay.Util.PortletURL.createPortletURL(
		exportTranslationURL,
		{
			articleId: articleIds[0],
			exportMimeType,
			sourceLanguageId,
			targetLanguageIds: selectedTargetLanguageIds.join(','),
		}
	);

	const onChangeTarget = (checked, selectedLanguageId) => {
		if (checked) {
			setSelectedTargetLanguageIds(
				selectedTargetLanguageIds.concat(selectedLanguageId)
			);
		}
		else {
			setSelectedTargetLanguageIds(
				selectedTargetLanguageIds.filter(
					(languageId) => languageId != selectedLanguageId
				)
			);
		}
	};

	const SourceLocales = () => {
		if (availableSourceLocales.length == 1) {
			return (
				<ClayInput
					readOnly
					value={availableSourceLocales[0].displayName}
				/>
			);
		}
		else {
			return (
				<ClaySelect
					name={`_${namespace}_sourceLanguageId`}
					onChange={(e) => {
						setSourceLanguageId(e.currentTarget.value);
					}}
					value={sourceLanguageId}
				>
					{availableSourceLocales.map((locale) => (
						<ClaySelect.Option
							key={locale.languageId}
							label={locale.displayName}
							value={locale.languageId}
						/>
					))}
				</ClaySelect>
			);
		}
	};

	const ExportFileFormats = () => {
		if (availableExportFileFormats.length == 1) {
			return (
				<ClayInput
					readOnly
					value={availableExportFileFormats[0].displayName}
				/>
			);
		}
		else {
			return (
				<ClaySelect
					name={`_${namespace}_exportMimeType`}
					onChange={(e) => {
						setExportMimeType(e.currentTarget.value);
					}}
					value={exportMimeType}
				>
					{availableExportFileFormats.map((exportFileFormat) => (
						<ClaySelect.Option
							key={exportFileFormat.mimeType}
							label={exportFileFormat.displayName}
							value={exportFileFormat.mimeType}
						/>
					))}
				</ClaySelect>
			);
		}
	};

	const TargetLocale = ({locale}) => {
		const languageId = locale.languageId;
		const checked = selectedTargetLanguageIds.indexOf(languageId) != -1;

		return (
			<ClayCheckbox
				checked={checked}
				disabled={languageId === sourceLanguageId}
				label={locale.displayName}
				onChange={() => {
					onChangeTarget(!checked, languageId);
				}}
			/>
		);
	};

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('export-for-translation')}
			</ClayModal.Header>

			<ClayForm
				className="export-modal-content"
				onSubmit={(e) => {
					e.preventDefault();
					onModalClose();
					location.href = Liferay.Util.addParams(
						'download=true',
						exportTranslationPortletURL.toString()
					);
				}}
			>
				<ClayModal.Body scrollable>
					<h5>{Liferay.Language.get('export-file-format')}</h5>

					<ClayForm.Group className="w-50">
						<ExportFileFormats />
					</ClayForm.Group>

					<h5>{Liferay.Language.get('original-language')}</h5>

					<ClayForm.Group>
						<SourceLocales />
					</ClayForm.Group>

					<h5>
						<p>
							{Liferay.Language.get('languages-to-translate-to')}
						</p>
					</h5>

					<ClayForm.Group>
						{availableTargetLocales.map((locale) => (
							<TargetLocale
								key={locale.languageId}
								locale={locale}
							/>
						))}
					</ClayForm.Group>

					<ClayInput
						name={`_${namespace}_articleIdsIds`}
						type="hidden"
						value={articleIds}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onModalClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={
									selectedTargetLanguageIds.length === 0
								}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('export')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

ExportTranslationModal.propTypes = {
	articleIds: PropTypes.array,
	availableExportFileFormats: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			mimeType: PropTypes.string,
		})
	).isRequired,
	availableSourceLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			languageId: PropTypes.string,
		})
	).isRequired,
	availableTargetLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			languageId: PropTypes.string,
		})
	).isRequired,
	defaultSourceLanguageId: PropTypes.string.isRequired,
};

export default ExportTranslationModal;
