/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceReportException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Marcos Martins
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMFormInstanceRecordModelListenerTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpDDMFormInstanceRecordModelListener();

		_ddmFormInstanceRecordModelListener.ddmFormInstanceReportLocalService =
			_ddmFormInstanceReportLocalService;
	}

	@Test
	public void testOnBeforeRemove() throws PortalException {
		when(
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(Mockito.anyLong())
		).thenThrow(
			new NoSuchFormInstanceReportException()
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getFormInstanceId()
		).thenReturn(
			0L
		);

		_ddmFormInstanceRecordModelListener.onBeforeRemove(
			ddmFormInstanceRecord);
	}

	private void _setUpDDMFormInstanceRecordModelListener() {
		_ddmFormInstanceRecordModelListener =
			new DDMFormInstanceRecordModelListener();
	}

	private DDMFormInstanceRecordModelListener
		_ddmFormInstanceRecordModelListener;

	@Mock
	private DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService;

}