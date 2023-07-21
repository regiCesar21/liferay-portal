/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Panel.scss';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {EVENT_TYPES, useForm} from 'dynamic-data-mapping-form-renderer';
import React from 'react';

import useHeightTransition from './useHeightTransition.es';

/**
 * Alternative component for ClayPanel,
 * since the original component don't have to much flexibility
 * for adding items on the ClayPanel trigger.
 */
const Panel = ({
	children,
	name,
	readOnly,
	repeatable,
	showLabel,
	showRepeatableRemoveButton,
	title,
}) => {
	const panelRef = React.useRef(null);
	const [expanded, setExpanded] = React.useState(true);

	const dispatch = useForm();

	const [
		transitioning,
		handleTransitionEnd,
		startTransition,
	] = useHeightTransition(expanded, setExpanded, panelRef);

	const showIconCollapsed = !(
		(!expanded && transitioning) ||
		(expanded && !transitioning)
	);

	return (
		<div
			className={classNames(
				'collapsable-panel',
				'panel',
				'panel-unstyled'
			)}
			role="tablist"
		>
			<>
				<ClayButton
					aria-expanded={expanded}
					className={classNames(
						'collapse-icon',
						'collapse-icon-middle',
						'panel-header',
						'panel-header-link',
						{
							collapsed: showIconCollapsed,
						}
					)}
					displayType="unstyled"
					onClick={startTransition}
					role="tab"
				>
					<>
						{showLabel && (
							<span className="panel-title">
								<label className="text-uppercase">
									{title}
								</label>
							</span>
						)}

						{repeatable && (
							<span className="actions collapse-icon-options">
								<div className="lfr-ddm-form-field-repeatable-toolbar">
									{showRepeatableRemoveButton && (
										<ClayButton
											className="ddm-form-field-repeatable-delete-button lfr-portal-tooltip p-0"
											disabled={readOnly}
											onClick={(event) => {
												event.stopPropagation();

												dispatch({
													payload: name,
													type:
														EVENT_TYPES.FIELD_REMOVED,
												});
											}}
											small
											title={Liferay.Language.get(
												'remove'
											)}
										>
											<ClayIcon symbol="hr" />
										</ClayButton>
									)}

									<ClayButton
										className="ddm-form-field-repeatable-add-button lfr-portal-tooltip p-0"
										disabled={readOnly}
										onClick={(event) => {
											event.stopPropagation();

											dispatch({
												payload: name,
												type:
													EVENT_TYPES.FIELD_REPEATED,
											});
										}}
										small
										title={Liferay.Language.get(
											'duplicate'
										)}
									>
										<ClayIcon symbol="plus" />
									</ClayButton>
								</div>
							</span>
						)}

						<span
							className={classNames(
								'actions',
								'collapse-icon-closed'
							)}
						>
							<ClayIcon symbol="angle-down" />
						</span>
						<span
							className={classNames(
								'actions',
								'collapse-icon-open'
							)}
						>
							<ClayIcon symbol="angle-up" />
						</span>
					</>
				</ClayButton>

				<div
					className={classNames('panel-collapse', {
						collapse: !transitioning,
						collapsing: transitioning,
						show: expanded,
					})}
					onTransitionEnd={handleTransitionEnd}
					ref={panelRef}
					role="tabpanel"
				>
					{children}
				</div>
			</>
		</div>
	);
};

export default Panel;
