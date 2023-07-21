/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.importer;

/**
 * @author     Rubén Pulido
 * @deprecated As of Athanasius (7.3.x), in favour of {@link
 *             #LayoutPageTemplatesImporterResultEntry}
 */
@Deprecated
public class MasterLayoutsImporterResultEntry {

	public MasterLayoutsImporterResultEntry(String name, Status status) {
		_name = name;
		_status = status;
	}

	public MasterLayoutsImporterResultEntry(
		String name, Status status, String errorMessage) {

		_name = name;
		_status = status;
		_errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getName() {
		return _name;
	}

	public Status getStatus() {
		return _status;
	}

	public enum Status {

		IGNORED("ignored"), IMPORTED("imported"), INVALID("invalid");

		public String getLabel() {
			return _label;
		}

		private Status(String label) {
			_label = label;
		}

		private final String _label;

	}

	private String _errorMessage;
	private final String _name;
	private final Status _status;

}