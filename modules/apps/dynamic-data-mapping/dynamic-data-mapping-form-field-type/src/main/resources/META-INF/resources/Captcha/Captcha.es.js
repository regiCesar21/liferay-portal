/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {globalEval} from 'metal-dom';
import React, {useEffect, useMemo, useRef} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const Captcha = ({html, name, ...otherProps}) => {
	// eslint-disable-next-line react-hooks/exhaustive-deps
	const contentMemoized = useMemo(() => html.content, []);
	const elRef = useRef(null);

	useEffect(() => {
		if (window.grecaptcha) {
			window.grecaptcha.ready(() => {
				try {
					window.grecaptcha.reset();
				}
				catch (error) {
					console.warn('Could not reset reCAPTCHA');
				}
			});
		}
	}, []);

	useEffect(() => {
		if (elRef.current) {
			globalEval.runScriptsInElement(elRef.current);
		}
	}, [elRef]);

	useEffect(() => {
		if (elRef.current) {
			const fieldIndex = name.indexOf('_ddm');
			Liferay.fire(
				`${name.substring(0, fieldIndex)}_simplecaptcha_attachEvent`
			);
		}
	}, [elRef, name]);

	return (
		<FieldBase {...otherProps} name={name} visible={true}>
			<div
				dangerouslySetInnerHTML={{__html: contentMemoized}}
				ref={elRef}
			/>
			<input id={name} type="hidden" />
		</FieldBase>
	);
};

export default Captcha;
