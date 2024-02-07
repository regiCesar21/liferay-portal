/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>KaleoDefinitionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.workflow.kaleo.model.KaleoDefinitionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.workflow.kaleo.model.KaleoDefinition</code>, that is translated to a
 * <code>com.liferay.portal.workflow.kaleo.model.KaleoDefinitionSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class KaleoDefinitionServiceSoap {

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionSoap
			addKaleoDefinition(
				String name, String title, String description, String content,
				String scope, int version,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.workflow.kaleo.model.KaleoDefinition
				returnValue = KaleoDefinitionServiceUtil.addKaleoDefinition(
					name, title, description, content, scope, version,
					serviceContext);

			return com.liferay.portal.workflow.kaleo.model.KaleoDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionSoap
			updateKaleoDefinition(
				long kaleoDefinitionId, String title, String description,
				String content,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.workflow.kaleo.model.KaleoDefinition
				returnValue = KaleoDefinitionServiceUtil.updateKaleoDefinition(
					kaleoDefinitionId, title, description, content,
					serviceContext);

			return com.liferay.portal.workflow.kaleo.model.KaleoDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionServiceSoap.class);

}