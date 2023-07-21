/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {useChartState} from '../context/ChartStateContext';

export default function Translation({
	defaultLanguage,
	onSelectedLanguageClick,
	viewURLs,
}) {
	const [active, setActive] = useState(false);

	const selectedLanguage = useMemo(() => {
		return (
			viewURLs.find((language) => language.selected)?.languageId ||
			defaultLanguage
		);
	}, [defaultLanguage, viewURLs]);

	const chartState = useChartState();

	return (
		<ClayLayout.ContentRow>
			<ClayLayout.ContentCol expand>
				<h5>{Liferay.Language.get('languages-translated-into')}</h5>
				<span className="text-secondary">
					{Liferay.Language.get(
						'select-language-to-view-its-metrics'
					)}
				</span>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol>
				<ClayDropDown
					active={active}
					hasLeftSymbols
					onActiveChange={setActive}
					trigger={
						<ClayButton
							className="btn-monospaced"
							displayType="secondary"
							small
						>
							<ClayIcon symbol={selectedLanguage.toLowerCase()} />
							<span
								className="d-block font-weight-normal"
								style={{fontSize: '9px'}}
							>
								{selectedLanguage}
							</span>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{Object.values(viewURLs).map((language, index) => (
							<ClayDropDown.Item
								active={
									language.selected && language.languageId
								}
								key={index}
								onClick={() => {
									onSelectedLanguageClick(
										language.viewURL,
										chartState.timeSpanKey,
										chartState.timeSpanOffset
									);
								}}
								symbolLeft={language.languageId.toLowerCase()}
							>
								<ClayLayout.ContentRow>
									<ClayLayout.ContentCol expand>
										<span>{language.languageId}</span>
									</ClayLayout.ContentCol>
									<ClayLayout.ContentCol>
										<ClayLabel
											displayType={
												language.default
													? 'primary'
													: 'success'
											}
										>
											{language.default
												? Liferay.Language.get(
														'default'
												  )
												: Liferay.Language.get(
														'translated'
												  )}
										</ClayLabel>
									</ClayLayout.ContentCol>
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
}

Translation.propTypes = {
	defaultLanguage: PropTypes.string.isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	viewURLs: PropTypes.arrayOf(
		PropTypes.shape({
			default: PropTypes.bool.isRequired,
			languageId: PropTypes.string.isRequired,
			selected: PropTypes.bool.isRequired,
			viewURL: PropTypes.string.isRequired,
		})
	).isRequired,
};
