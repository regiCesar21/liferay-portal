/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.rest.client.dto.v1_0.Account;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import org.junit.After;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountResourceTest extends BaseAccountResourceTestCase {

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Account randomAccount() throws Exception {
		Account account = super.randomAccount();

		account.setParentAccountId(AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
		account.setStatus(WorkflowConstants.STATUS_APPROVED);

		return account;
	}

	@Override
	protected Account testDeleteAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testDeleteAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return _postAccount();
	}

	@Override
	protected Account testGetAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testGetAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return _postAccount();
	}

	@Override
	protected Account testGetAccountsPage_addAccount(Account account)
		throws Exception {

		return _postAccount(account);
	}

	@Override
	protected Account testGraphQLAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testPatchAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testPatchAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return _postAccount();
	}

	@Override
	protected Account testPostAccount_addAccount(Account account)
		throws Exception {

		return _postAccount(account);
	}

	@Override
	protected Account testPutAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testPutAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return _postAccount();
	}

	private Account _postAccount() throws Exception {
		return _postAccount(randomAccount());
	}

	private Account _postAccount(Account account) throws Exception {
		return accountResource.postAccount(account);
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

}