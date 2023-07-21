/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.search.experiences.service.SXPBlueprintServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>SXPBlueprintServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.search.experiences.model.SXPBlueprintSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.search.experiences.model.SXPBlueprint</code>, that is translated to a
 * <code>com.liferay.search.experiences.model.SXPBlueprintSoap</code>. Methods that SOAP
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
 * @see SXPBlueprintServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SXPBlueprintServiceSoap {

	public static com.liferay.search.experiences.model.SXPBlueprintSoap
			addSXPBlueprint(
				String externalReferenceCode, String configurationJSON,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String elementInstancesJSON,
				String schemaVersion, String[] titleMapLanguageIds,
				String[] titleMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);

			com.liferay.search.experiences.model.SXPBlueprint returnValue =
				SXPBlueprintServiceUtil.addSXPBlueprint(
					externalReferenceCode, configurationJSON, descriptionMap,
					elementInstancesJSON, schemaVersion, titleMap,
					serviceContext);

			return com.liferay.search.experiences.model.SXPBlueprintSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprintSoap
			deleteSXPBlueprint(long sxpBlueprintId)
		throws RemoteException {

		try {
			com.liferay.search.experiences.model.SXPBlueprint returnValue =
				SXPBlueprintServiceUtil.deleteSXPBlueprint(sxpBlueprintId);

			return com.liferay.search.experiences.model.SXPBlueprintSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprintSoap
			getSXPBlueprint(long sxpBlueprintId)
		throws RemoteException {

		try {
			com.liferay.search.experiences.model.SXPBlueprint returnValue =
				SXPBlueprintServiceUtil.getSXPBlueprint(sxpBlueprintId);

			return com.liferay.search.experiences.model.SXPBlueprintSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprintSoap
			getSXPBlueprintByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.search.experiences.model.SXPBlueprint returnValue =
				SXPBlueprintServiceUtil.getSXPBlueprintByExternalReferenceCode(
					companyId, externalReferenceCode);

			return com.liferay.search.experiences.model.SXPBlueprintSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprintSoap
			updateSXPBlueprint(
				long sxpBlueprintId, String configurationJSON,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String elementInstancesJSON,
				String schemaVersion, String[] titleMapLanguageIds,
				String[] titleMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);

			com.liferay.search.experiences.model.SXPBlueprint returnValue =
				SXPBlueprintServiceUtil.updateSXPBlueprint(
					sxpBlueprintId, configurationJSON, descriptionMap,
					elementInstancesJSON, schemaVersion, titleMap,
					serviceContext);

			return com.liferay.search.experiences.model.SXPBlueprintSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SXPBlueprintServiceSoap.class);

}