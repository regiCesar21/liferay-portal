/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

[Element, CharacterData, DocumentType].forEach(function (item) {
	if (!item.prototype.remove) {
		item.prototype.remove = function () {
			if (this.parentNode === null) {
				return;
			}

			this.parentNode.removeChild(this);
		};
	}
});
