/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import LayoutsTree from './LayoutsTree';
import {StyleBookContext} from './StyleBookContext';
import {useId} from './useId';

export default function LayoutSelector() {
	const [active, setActive] = useState(false);
	const [showPrivateLayouts, setShowPrivateLayouts] = useState(false);
	const {previewLayout} = useContext(StyleBookContext);

	const id = useId();

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomRight}
			menuElementAttrs={{
				className: 'style-book-editor__page-selector',
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="ml-2 pl-2 pr-1"
					displayType="unstyled"
					small
					symbol="time"
				>
					{previewLayout?.layoutName}
					<ClayIcon className="mt-0" symbol={'caret-bottom-l'} />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<div className="style-book-editor__page-type-selector">
					<ClayForm.Group small>
						<label className="sr-only" htmlFor={id}>
							{Liferay.Language.get('page-type-selector')}
						</label>
						<ClaySelectWithOption
							id={id}
							onChange={(event) =>
								setShowPrivateLayouts(
									event.target.value === 'private-pages'
								)
							}
							options={[
								{
									label: Liferay.Language.get('public-pages'),
									value: 'public-pages',
								},
								{
									label: Liferay.Language.get(
										'private-pages'
									),
									value: 'private-pages',
								},
							]}
						/>
					</ClayForm.Group>
				</div>

				<LayoutsTree showPrivateLayouts={showPrivateLayouts} />
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
