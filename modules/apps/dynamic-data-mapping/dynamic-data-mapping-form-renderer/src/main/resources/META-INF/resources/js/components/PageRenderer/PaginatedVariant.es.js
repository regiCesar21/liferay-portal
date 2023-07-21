/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {Pagination} from '../Pagination.es';
import {PaginationControls} from '../PaginationControls.es';
import * as DefaultVariant from './DefaultVariant.es';

/* eslint-disable react/jsx-fragments */
export const Container = ({
	activePage,
	children,
	pageIndex,
	pages,
	readOnly,
	showSubmitButton,
	strings,
	submitLabel,
}) => (
	<div className="ddm-form-page-container paginated">
		<DefaultVariant.Container activePage={activePage} pageIndex={pageIndex}>
			{children}
		</DefaultVariant.Container>

		{pageIndex === activePage && (
			<React.Fragment>
				{pages.length > 0 && (
					<React.Fragment>
						<Pagination activePage={activePage} pages={pages} />
						<PaginationControls
							activePage={activePage}
							readOnly={readOnly}
							showSubmitButton={showSubmitButton}
							strings={strings}
							submitLabel={submitLabel}
							total={pages.length}
						/>
					</React.Fragment>
				)}

				{!pages.length && showSubmitButton && (
					<ClayButton
						className="float-right lfr-ddm-form-submit"
						id="ddm-form-submit"
						type="submit"
					>
						{submitLabel}
					</ClayButton>
				)}
			</React.Fragment>
		)}
	</div>
);
