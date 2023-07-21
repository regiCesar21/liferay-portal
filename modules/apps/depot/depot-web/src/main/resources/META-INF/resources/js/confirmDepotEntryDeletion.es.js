/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function confirmDepotEntryDeletion() {
	return confirm(
		Liferay.Language.get(
			'removing-an-asset-library-can-affect-sites-that-use-the-contents-stored-in-it.-are-you-sure-you-want-to-continue-removing-this-asset-library'
		)
	);
}
