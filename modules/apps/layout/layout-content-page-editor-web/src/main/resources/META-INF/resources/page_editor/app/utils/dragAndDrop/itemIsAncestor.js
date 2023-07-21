/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Checks if the given parent is indeed parent of given child.
 * @param {object} child
 * @param {object} parent
 * @param {{current: object}} layoutDataRef
 * @return {boolean}
 */
export default function itemIsAncestor(child, parent, layoutDataRef) {
	if (child && parent) {
		return child.itemId !== parent.itemId
			? itemIsAncestor(
					child,
					layoutDataRef.current.items[parent.parentId],
					layoutDataRef
			  )
			: true;
	}

	return false;
}
