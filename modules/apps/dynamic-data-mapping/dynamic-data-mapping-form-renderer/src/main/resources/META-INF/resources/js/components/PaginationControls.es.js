/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {useEvaluate} from '../hooks/useEvaluate.es';
import {useForm} from '../hooks/useForm.es';
import {usePage} from '../hooks/usePage.es';
import nextPage from '../thunks/nextPage.es';
import previousPage from '../thunks/previousPage.es';
import {getFormId, getFormNode} from '../util/formId.es';

export const PaginationControls = ({
	activePage,
	readOnly,
	showSubmitButton,
	strings = null,
	submitLabel,
	total,
}) => {
	const {
		cancelLabel,
		containerElement,
		redirectURL,
		showCancelButton,
	} = usePage();
	const createPreviousPage = useEvaluate(previousPage);
	const createNextPage = useEvaluate(nextPage);
	const dispatch = useForm();

	return (
		<div className="lfr-ddm-form-pagination-controls">
			{activePage > 0 && (
				<ClayButton
					className="lfr-ddm-form-pagination-prev"
					onClick={() =>
						dispatch(
							createPreviousPage({
								activePage,
								formId: getFormId(
									getFormNode(containerElement.current)
								),
							})
						)
					}
					type="button"
				>
					<ClayIcon symbol="angle-left" />
					{strings !== null
						? strings['previous']
						: Liferay.Language.get('previous')}
				</ClayButton>
			)}

			{activePage < total - 1 && (
				<ClayButton
					className="float-right lfr-ddm-form-pagination-next"
					onClick={() =>
						dispatch(
							createNextPage({
								activePage,
								formId: getFormId(
									getFormNode(containerElement.current)
								),
							})
						)
					}
					type="button"
				>
					{strings !== null
						? strings['next']
						: Liferay.Language.get('next')}
					<ClayIcon symbol="angle-right" />
				</ClayButton>
			)}

			{activePage === total - 1 && !readOnly && showSubmitButton && (
				<ClayButton
					className="float-right lfr-ddm-form-submit"
					id="ddm-form-submit"
					type="submit"
				>
					{submitLabel}
				</ClayButton>
			)}

			{showCancelButton && !readOnly && (
				<div className="ddm-btn-cancel float-right">
					<a
						className="btn btn-cancel btn-secondary"
						href={redirectURL}
					>
						{cancelLabel}
					</a>
				</div>
			)}
		</div>
	);
};
