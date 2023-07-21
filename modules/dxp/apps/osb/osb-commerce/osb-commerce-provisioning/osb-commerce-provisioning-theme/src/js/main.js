/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI().ready(() => {
	const OSB_COMPONENTS_ROOT =
		'osb-commerce-provisioning-theme-impl@1.0.0/js/components';

	function run(module, ...args) {
		module.default(...args);
	}

	Liferay.Loader.require(`${OSB_COMPONENTS_ROOT}/header/Header`, run);
});
