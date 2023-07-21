/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.reader.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Mail_Attachment&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Attachment
 * @generated
 */
public class AttachmentTable extends BaseTable<AttachmentTable> {

	public static final AttachmentTable INSTANCE = new AttachmentTable();

	public final Column<AttachmentTable, Long> attachmentId = createColumn(
		"attachmentId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AttachmentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> messageId = createColumn(
		"messageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, String> contentPath = createColumn(
		"contentPath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, String> fileName = createColumn(
		"fileName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AttachmentTable() {
		super("Mail_Attachment", AttachmentTable::new);
	}

}