/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {fetch, openModal} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import NoPermissionState from '../../components/empty-state/NoPermissionState.es';
import {errorToast} from '../../utils/toast.es';

export default () => {
	const {appDeploymentType} = useContext(AppContext);
	const [bodyHTML, setBodyHTML] = useState();
	const isSignedIn = themeDisplay.isSignedIn();
	const showLoginButton = !isSignedIn && appDeploymentType === 'standalone';

	useEffect(() => {
		if (showLoginButton) {
			const {href, origin} = window.location;
			let signInURL = `${origin}/c/portal/login`;

			signInURL = Liferay.Util.addParams(
				`p_p_id=com_liferay_login_web_portlet_LoginPortlet`,
				signInURL
			);
			signInURL = Liferay.Util.addParams(
				`windowState=exclusive`,
				signInURL
			);

			fetch(`${signInURL}&redirect=${href}`)
				.then((response) => response.text())
				.then((html) => setBodyHTML(html))
				.catch((error) => errorToast(error.message));
		}
	}, [isSignedIn, showLoginButton]);

	const openSignInModal = () => {
		if (bodyHTML) {
			openModal({
				bodyHTML,
				height: '400px',
				onOpen: () =>
					Liferay.Util.focusFormField(
						'.modal #_com_liferay_login_web_portlet_LoginPortlet_login'
					),
				size: 'md',
				title: Liferay.Language.get('sign-in'),
			});
		}
	};

	return (
		<NoPermissionState
			description={
				isSignedIn
					? Liferay.Language.get(
							'you-do-not-have-permissions-to-access-this-app-contact-the-app-administrator-to-request-the-access'
					  )
					: Liferay.Language.get(
							'you-do-not-have-access-to-this-app-sign-in-to-access-it'
					  )
			}
			title={Liferay.Language.get('no-permissions')}
		>
			{showLoginButton && (
				<ClayButton onClick={openSignInModal}>
					{Liferay.Language.get('sign-in')}
				</ClayButton>
			)}
		</NoPermissionState>
	);
};
