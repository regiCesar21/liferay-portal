/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React, {useContext} from 'react';

import getLocalizedLearnMessageObject from '../utils/language/get_localized_learn_message_object';
import ThemeContext from './ThemeContext';

/**
 * LearnMessage is used to render links to resources, like Liferay Learn
 * articles. The json object `learnMessages` contains the messages and urls
 * and is taken from portal/learn-resources.
 *
 * Example of `learnMessages`:
 * {
 *	"general": {
 *		"en_US": {
 *			"message": "Tell me more",
 *			"url": "https://learn.liferay.com/"
 *		}
 *	}
 * }
 *
 * @param {string} resourceKey Identifies which resource to render
 */
export default function LearnMessage({resourceKey}) {
	const {defaultLocale, learnMessages, locale} = useContext(ThemeContext);

	const learnMessageObject = getLocalizedLearnMessageObject(
		resourceKey,
		learnMessages,
		locale,
		defaultLocale
	);

	if (learnMessageObject.url) {
		return (
			<ClayLink
				className="learn-message"
				href={learnMessageObject.url}
				rel="noopener noreferrer"
				target="_blank"
			>
				{learnMessageObject.message}
			</ClayLink>
		);
	}

	return <></>;
}
