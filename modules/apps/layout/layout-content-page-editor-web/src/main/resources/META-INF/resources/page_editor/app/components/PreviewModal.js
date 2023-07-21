/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {addParams} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';
import {useSelector} from '../store/index';
import {useId} from '../utils/useId';
import Translation from './Translation';
import ViewportSizeSelector from './ViewportSizeSelector';

const PreviewModal = ({observer}) => {
	const [languageId, setLanguageId] = useState(
		useSelector((state) => state.languageId)
	);

	const [viewportSize, setViewportSize] = useState(
		useSelector((state) => state.selectedViewportSize)
	);

	const initialSegmentsExperienceId = useSelector(
		(state) => state.segmentsExperienceId
	);

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [segmentsExperienceId, setSegmentsExperienceId] = useState(
		initialSegmentsExperienceId
	);

	const availableSegmentsExperiences = useSelector(
		(state) => state.availableSegmentsExperiences
	);
	const experienceSelectId = useId();

	const previewURL = useMemo(
		() =>
			addParams(
				{
					[`${config.portletNamespace}languageId`]: languageId,
					[`${config.portletNamespace}segmentsExperienceId`]: segmentsExperienceId,
				},
				config.previewPageURL
			),
		[languageId, segmentsExperienceId]
	);

	const modalBodyWidth = useMemo(() => {
		const {maxWidth, minWidth} = config.availableViewportSizes[
			viewportSize
		];

		return viewportSize === VIEWPORT_SIZES.desktop
			? '100%'
			: (maxWidth + minWidth) / 2;
	}, [viewportSize]);

	return (
		<ClayModal
			className="page-editor__preview-modal"
			observer={observer}
			size="full-screen"
		>
			<ClayModal.Header>
				<ClayLayout.ContainerFluid size={false}>
					<ClayModal.Title className="pb-3 pt-3">
						<div className="d-flex justify-content-between page-editor__theme-adapter-buttons responsive-mode">
							<ul className="navbar-nav page-editor__preview-modal__part">
								{availableSegmentsExperiences && (
									<li className="mr-2 nav-item">
										<div className="align-middle d-inline-flex">
											<label
												className="mr-2 mt-1"
												htmlFor={experienceSelectId}
											>
												{Liferay.Language.get(
													'experience'
												)}
											</label>

											<ClaySelect
												aria-label="Experience"
												className="form-control-sm"
												id={experienceSelectId}
												onChange={(event) => {
													setSegmentsExperienceId(
														event.target.options[
															event.target
																.selectedIndex
														].value
													);
												}}
												value={segmentsExperienceId}
											>
												{availableSegmentsExperiences &&
													Object.keys(
														availableSegmentsExperiences
													).map(
														(
															segmentsExperienceId
														) => (
															<ClaySelect.Option
																key={
																	segmentsExperienceId
																}
																label={
																	availableSegmentsExperiences[
																		segmentsExperienceId
																	].name
																}
																value={
																	segmentsExperienceId
																}
															/>
														)
													)}
											</ClaySelect>
										</div>
									</li>
								)}

								<li className="nav-item">
									<Translation
										availableLanguages={
											config.availableLanguages
										}
										defaultLanguageId={
											config.defaultLanguageId
										}
										dispatch={({
											languageId: nextLanguageId,
										}) => setLanguageId(nextLanguageId)}
										fragmentEntryLinks={fragmentEntryLinks}
										languageId={languageId}
										segmentsExperienceId={
											segmentsExperienceId
										}
										showNotTranslated={false}
									/>
								</li>
							</ul>

							<div className="page-editor__preview-modal__part">
								<ViewportSizeSelector
									onSizeSelected={setViewportSize}
									selectedSize={viewportSize}
								/>
							</div>

							<div className="page-editor__preview-modal__part"></div>
						</div>
					</ClayModal.Title>
				</ClayLayout.ContainerFluid>
			</ClayModal.Header>

			<ClayModal.Body
				iFrameProps={{
					style: {maxWidth: modalBodyWidth},
				}}
				url={previewURL}
			/>
		</ClayModal>
	);
};

PreviewModal.propTypes = {
	observer: PropTypes.object.isRequired,
};

export default PreviewModal;
