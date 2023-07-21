/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	var adjustScrollTop = function () {
		var controlMenuContainer;
		var controlMenuContainerScroll;
		var errorFieldLabel;
		var labelScroll;

		errorFieldLabel = document.querySelector(
			'.form-group.has-error .control-label'
		);

		if (errorFieldLabel) {
			labelScroll = errorFieldLabel.clientHeight || 0;

			window.scrollBy(0, -labelScroll);
		}

		controlMenuContainer = document.querySelector(
			'.control-menu-container'
		);

		if (controlMenuContainer) {
			controlMenuContainerScroll = controlMenuContainer.offsetHeight || 0;

			window.scrollBy(0, -controlMenuContainerScroll);
		}
	};

	window.addEventListener('hashchange', adjustScrollTop);

	var handlePageLoad = function () {
		if (window.location.hash) {
			adjustScrollTop();
		}
	};

	window.addEventListener('load', handlePageLoad);

	var handleFormRegistered = function (event) {
		if (event.form && event.form.formValidator) {
			AUI().Do.after(
				adjustScrollTop,
				event.form.formValidator,
				'focusInvalidField'
			);
		}
	};

	Liferay.on('form:registered', handleFormRegistered);
})();
