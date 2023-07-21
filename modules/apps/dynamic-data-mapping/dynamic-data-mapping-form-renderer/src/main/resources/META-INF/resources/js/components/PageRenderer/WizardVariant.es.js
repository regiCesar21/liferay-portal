/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classnames from 'classnames';
import React from 'react';

import {MultiStep} from '../MultiStep.es';
import {PaginationControls} from '../PaginationControls.es';

/* eslint-disable react/jsx-fragments */
export const Container = ({
	activePage,
	children,
	editable,
	pageIndex,
	pages,
	readOnly,
	showSubmitButton,
	strings = null,
	submitLabel,
}) => (
	<div className="ddm-form-page-container wizard">
		{pages.length > 1 && pageIndex === activePage && (
			<MultiStep
				activePage={activePage}
				editable={editable}
				pages={pages}
			/>
		)}

		<div
			className={classnames(
				'ddm-layout-builder ddm-page-container-layout',
				{
					hide: activePage !== pageIndex,
				}
			)}
		>
			<div className="form-builder-layout">{children}</div>
		</div>

		{pageIndex === activePage && (
			<React.Fragment>
				{pages.length > 0 && (
					<PaginationControls
						activePage={activePage}
						readOnly={readOnly}
						showSubmitButton={showSubmitButton}
						strings={strings}
						submitLabel={submitLabel}
						total={pages.length}
					/>
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
