/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.AccountTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.AccountPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AccountTableReferenceDefinition
	implements TableReferenceDefinition<AccountTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AccountTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AccountTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AccountTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).parentColumnReference(
			AccountTable.INSTANCE.accountId,
			AccountTable.INSTANCE.parentAccountId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _accountPersistence;
	}

	@Override
	public AccountTable getTable() {
		return AccountTable.INSTANCE;
	}

	@Reference
	private AccountPersistence _accountPersistence;

}