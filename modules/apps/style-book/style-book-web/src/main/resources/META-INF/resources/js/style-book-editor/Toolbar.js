/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext} from 'react';

import {StyleBookContext} from './StyleBookContext';
import {config} from './config';
import {DRAFT_STATUS} from './constants/draftStatusConstants';

const STATUS_TO_LABEL = {
	[DRAFT_STATUS.draftSaved]: Liferay.Language.get('draft-saved'),
	[DRAFT_STATUS.notSaved]: '',
	[DRAFT_STATUS.saving]: `${Liferay.Language.get('saving')}...`,
};

export default function Toolbar() {
	const {draftStatus} = useContext(StyleBookContext);

	const handleSubmit = (event) => {
		if (
			!confirm(
				Liferay.Language.get(
					'once-published,-these-changes-will-affect-all-instances-of-the-site-using-these-properties'
				)
			)
		) {
			event.preventDefault();
		}
	};

	return (
		<div className="p-3 style-book-editor__toolbar">
			<div>
				{draftStatus === DRAFT_STATUS.draftSaved && (
					<ClayIcon
						className="mt-0 style-book-editor__status-icon"
						symbol="check-circle"
					/>
				)}
				<span
					className={classNames(
						'ml-1 style-book-editor__status-text',
						{
							'text-success':
								draftStatus === DRAFT_STATUS.draftSaved,
						}
					)}
				>
					{STATUS_TO_LABEL[draftStatus]}
				</span>
			</div>

			<form action={config.publishURL} method="POST">
				<input
					name={`${config.namespace}redirect`}
					type="hidden"
					value={config.redirectURL}
				/>
				<input
					name={`${config.namespace}styleBookEntryId`}
					type="hidden"
					value={config.styleBookEntryId}
				/>

				<ClayButton
					disabled={config.pending}
					displayType="primary"
					onClick={handleSubmit}
					small
					type="submit"
				>
					{Liferay.Language.get('publish')}
				</ClayButton>
			</form>
		</div>
	);
}
