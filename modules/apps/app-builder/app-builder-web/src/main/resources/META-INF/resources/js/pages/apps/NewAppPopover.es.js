/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {compile} from 'path-to-regexp';
import React, {useState} from 'react';

import Popover from '../../components/popover/Popover.es';
import useBackUrl from '../../hooks/useBackUrl.es';
import SelectObjects from './SelectObjectsDropDown.es';

const NewAppPopover = (
	{alignElement, editPath, history, onCancel, visible},
	forwardRef
) => {
	const [selectedObject, setSelectedObject] = useState({});
	const withBackUrl = useBackUrl();

	const onClick = () => {
		history.push(
			withBackUrl(
				compile(editPath[0])({dataDefinitionId: selectedObject.id})
			)
		);
	};

	return (
		<>
			<Popover
				alignElement={alignElement}
				className="apps-popover"
				content={() => (
					<div className="px-2">
						<label>{Liferay.Language.get('object')}</label>

						<SelectObjects
							alignElement={alignElement}
							label={Liferay.Language.get('select-object')}
							onSelect={setSelectedObject}
							selectedValue={selectedObject}
							visible={visible}
						/>
					</div>
				)}
				footer={() => (
					<div
						className="border-top mt-3 px-4 py-3"
						style={{width: 450}}
					>
						<div className="d-flex justify-content-end">
							<ClayButton
								className="mr-3"
								displayType="secondary"
								onClick={() => {
									setSelectedObject({});
									onCancel();
								}}
								small
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!selectedObject.id}
								onClick={onClick}
								small
							>
								{Liferay.Language.get('continue')}
							</ClayButton>
						</div>
					</div>
				)}
				ref={forwardRef}
				showArrow={false}
				title={() => (
					<div className="pt-2 px-2">
						<h4 className="mb-3">
							{Liferay.Language.get('new-app')}
						</h4>

						<span className="font-weight-light text-secondary">
							{Liferay.Language.get(
								'create-an-app-to-collect-and-manage-an-objects-data'
							)}
						</span>
					</div>
				)}
				visible={visible}
			/>
		</>
	);
};

export default React.forwardRef(NewAppPopover);
