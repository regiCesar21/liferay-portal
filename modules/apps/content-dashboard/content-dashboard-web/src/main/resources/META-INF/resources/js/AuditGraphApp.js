/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import AuditBarChart from './components/AuditBarChart';
import EmptyAuditBarChart from './components/EmptyAuditBarChart';

export default function ({context, props}) {
	const {languageDirection} = context;
	const {learnHowURL, vocabularies} = props;

	return vocabularies.length ? (
		<AuditBarChart
			rtl={languageDirection === 'rtl'}
			vocabularies={vocabularies}
		/>
	) : (
		<EmptyAuditBarChart learnHowURL={learnHowURL} />
	);
}
