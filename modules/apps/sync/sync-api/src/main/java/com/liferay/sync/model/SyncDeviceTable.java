/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sync.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SyncDevice&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDevice
 * @generated
 */
public class SyncDeviceTable extends BaseTable<SyncDeviceTable> {

	public static final SyncDeviceTable INSTANCE = new SyncDeviceTable();

	public final Column<SyncDeviceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Long> syncDeviceId = createColumn(
		"syncDeviceId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SyncDeviceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Long> buildNumber = createColumn(
		"buildNumber", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Integer> featureSet = createColumn(
		"featureSet", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, String> hostname = createColumn(
		"hostname", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private SyncDeviceTable() {
		super("SyncDevice", SyncDeviceTable::new);
	}

}