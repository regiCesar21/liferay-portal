/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import FormURL from '../../../src/main/resources/META-INF/resources/admin/js/util/FormURL.es';

const formInstanceId = 1;

describe('FormURL', () => {
	it('gets a preview url', () => {
		const published = false;
		const requireAuthentication = false;

		const formURL = new FormURL(
			formInstanceId,
			published,
			requireAuthentication
		);

		expect(formURL.create()).toBe(
			Liferay.DDM.FormSettings.sharedFormURL + formInstanceId
		);
	});

	it('gets a valid restricted url', () => {
		const published = true;
		const requireAuthentication = true;

		const formURL = new FormURL(
			formInstanceId,
			published,
			requireAuthentication
		);

		expect(formURL.create()).toBe(
			Liferay.DDM.FormSettings.restrictedFormURL + formInstanceId
		);
	});

	it('gets a valid shared url', () => {
		const published = true;
		const requireAuthentication = false;

		const formURL = new FormURL(
			formInstanceId,
			published,
			requireAuthentication
		);

		expect(formURL.create()).toBe(
			Liferay.DDM.FormSettings.sharedFormURL + formInstanceId
		);
	});
});
