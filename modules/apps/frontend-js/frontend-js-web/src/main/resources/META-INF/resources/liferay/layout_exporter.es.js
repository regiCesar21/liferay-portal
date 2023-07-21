/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Hides layout pane
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function hideLayoutPane(options) {
	options = options || {};

	var obj = options.obj;
	var pane = options.pane;

	if (obj && obj.checked) {
		pane = document.querySelector(pane);

		if (pane) {
			pane.classList.add('hide');
		}
	}
}

/**
 * Gets layout icons
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function getLayoutIcons() {
	return {
		minus: themeDisplay.getPathThemeImages() + '/arrows/01_minus.png',
		plus: themeDisplay.getPathThemeImages() + '/arrows/01_plus.png',
	};
}

/**
 * Proposes layout
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function proposeLayout(options) {
	options = options || {};

	var namespace = options.namespace;
	var reviewers = options.reviewers;

	var contents = '<div><form action="' + options.url + '" method="post">';

	if (reviewers.length > 0) {
		contents +=
			'<textarea name="' +
			namespace +
			'description" style="height: 100px; width: 284px;"></textarea><br /><br />' +
			Liferay.Language.get('reviewer') +
			' <select name="' +
			namespace +
			'reviewUserId">';

		for (var i = 0; i < reviewers.length; i++) {
			contents +=
				'<option value="' +
				reviewers[i].userId +
				'">' +
				reviewers[i].fullName +
				'</option>';
		}

		contents +=
			'</select><br /><br />' +
			'<input type="submit" value="' +
			Liferay.Language.get('proceed') +
			'" />';
	}
	else {
		contents +=
			Liferay.Language.get('no-reviewers-were-found') +
			'<br />' +
			Liferay.Language.get(
				'please-contact-the-administrator-to-assign-reviewers'
			) +
			'<br /><br />';
	}

	contents += '</form></div>';

	Liferay.Util.openWindow({
		dialog: {
			destroyOnHide: true,
		},
		title: contents,
	});
}

/**
 * Publishes to live
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function publishToLive(options) {
	options = options || {};

	Liferay.Util.openWindow({
		dialog: {
			constrain: true,
			modal: true,
			on: {
				visibleChange(event) {
					var instance = this;

					if (!event.newVal) {
						instance.destroy();
					}
				},
			},
		},
		title: options.title,
		uri: options.url,
	});
}

/**
 * Shows layout pane
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function showLayoutPane(options) {
	options = options || {};

	var obj = options.obj;
	var pane = options.pane;

	if (obj && obj.checked) {
		pane = document.querySelector(pane);

		if (pane) {
			pane.classList.remove('hide');
		}
	}
}

/**
 * Toggles layout details
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function toggleLayoutDetails(options) {
	options = options || {};

	var detail = document.querySelector(options.detail);
	var img = document.querySelector(options.toggle);

	if (detail && img) {
		var icon = themeDisplay.getPathThemeImages() + '/arrows/01_plus.png';

		if (detail.classList.contains('hide')) {
			detail.classList.remove('hide');

			icon = themeDisplay.getPathThemeImages() + '/arrows/01_minus.png';
		}
		else {
			detail.classList.add('hide');
		}

		img.setAttribute('src', icon);
	}
}
