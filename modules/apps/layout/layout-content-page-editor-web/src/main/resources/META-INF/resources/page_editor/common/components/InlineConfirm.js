/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import Button from './Button';

export default function InlineConfirm({
	cancelButtonLabel,
	confirmButtonLabel,
	message,
	onCancelButtonClick,
	onConfirmButtonClick,
}) {
	const [performingAction, setPerformingAction] = useState(false);
	const wrapper = useRef(null);
	const isMounted = useIsMounted();

	const _handleConfirmButtonClick = () => {
		if (wrapper.current) {
			wrapper.current.focus();
		}

		setPerformingAction(true);

		onConfirmButtonClick().then(() => {
			if (isMounted()) {
				setPerformingAction(false);
			}
		});
	};

	useEffect(() => {
		if (wrapper.current) {
			wrapper.current.focus();
		}
	}, []);

	useEffect(() => {
		if (wrapper.current) {
			const confirmButton = wrapper.current.querySelector(
				'page-editor__inline-confirm-button'
			);

			if (confirmButton) {
				confirmButton.focus();
			}
		}

		const _handleDocumentFocusOut = () => {
			requestAnimationFrame(() => {
				if (wrapper.current && !performingAction) {
					if (
						!wrapper.current.contains(document.activeElement) &&
						wrapper.current !== document.activeElement
					) {
						onCancelButtonClick();
					}
				}
			});
		};

		document.addEventListener('focusout', _handleDocumentFocusOut, true);

		return () =>
			window.removeEventListener(
				'focusout',
				_handleDocumentFocusOut,
				true
			);
	}, [performingAction, onCancelButtonClick]);

	return (
		<div
			className="page-editor__inline-confirm"
			onKeyDown={(e) => e.key === 'Escape' && onCancelButtonClick()}
			ref={wrapper}
			role="alertdialog"
			tabIndex="-1"
		>
			<p className="text-center text-secondary">
				<strong>{message}</strong>
			</p>

			<ClayButton.Group spaced>
				<Button
					className="page-editor__inline-confirm-button"
					disabled={performingAction}
					displayType="primary"
					loading={performingAction}
					onClick={_handleConfirmButtonClick}
					small
				>
					{confirmButtonLabel}
				</Button>

				<Button
					disabled={performingAction}
					displayType="secondary"
					onClick={onCancelButtonClick}
					small
					type="button"
				>
					{cancelButtonLabel}
				</Button>
			</ClayButton.Group>
		</div>
	);
}

InlineConfirm.propTypes = {
	cancelButtonLabel: PropTypes.string,
	confirmButtonLabel: PropTypes.string,
	message: PropTypes.string,
	onCancelButtonClick: PropTypes.func,
	onConfirmButtonClick: PropTypes.func,
};
