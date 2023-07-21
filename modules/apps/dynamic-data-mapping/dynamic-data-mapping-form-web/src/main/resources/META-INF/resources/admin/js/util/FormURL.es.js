/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

class FormURL {
	constructor(
		formInstanceId,
		published = false,
		requireAuthentication = false
	) {
		this.formInstanceId = formInstanceId;
		this.published = published;
		this.requireAuthentication = requireAuthentication;
	}

	create() {
		let formURL;

		if (this.requireAuthentication) {
			formURL = Liferay.DDM.FormSettings.restrictedFormURL;
		}
		else {
			formURL = Liferay.DDM.FormSettings.sharedFormURL;
		}

		return formURL + this.formInstanceId;
	}
}

export default FormURL;
