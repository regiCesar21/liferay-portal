/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Uses the `actions` object from the REST response to determine if the user has
 * permission to perform the action.
 *
 * `actionPermissions` has a structure like this:
 * {
 *   "delete": {"method": "DELETE", "href": "http://localhost:8080/o/search-experiences-rest/v1.0/sxp-blueprints/1"},
 *   "get": {"method": "GET", "href": "http://localhost:8080/o/search-experiences-rest/v1.0/sxp-blueprints/1"},
 *   "update": {"method": "UPDATE", "href": "http://localhost:8080/o/search-experiences-rest/v1.0/sxp-blueprints/1"}
 * }
 *
 * @see SXPBlueprintResourceImpl.java for action key implementation
 *
 * @param {string} actionId
 * @param {object} actionPermissions
 * @returns
 */
export function checkPermission(actionId, actionPermissions = {}) {
	return Object.keys(actionPermissions).includes(actionId);
}
