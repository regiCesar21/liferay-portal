/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.display.contributor;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 * com.liferay.info.item.provider.InfoItemObjectProvider and
 * com.liferay.layout.display.page.LayoutDisplayPageProvider}
 */
@Deprecated
@ProviderType
public interface InfoDisplayContributor<T> {

	public String getClassName();

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getInfoDisplayFields(long, Locale)}
	 */
	@Deprecated
	public default List<InfoDisplayField> getClassTypeInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		return Collections.emptyList();
	}

	public default List<ClassType> getClassTypes(long groupId, Locale locale)
		throws PortalException {

		return Collections.emptyList();
	}

	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException;

	public default Set<InfoDisplayField> getInfoDisplayFields(
			T t, Locale locale)
		throws PortalException {

		return getInfoDisplayFields(0, locale);
	}

	public Map<String, Object> getInfoDisplayFieldsValues(T t, Locale locale)
		throws PortalException;

	public default Object getInfoDisplayFieldValue(
			T t, String fieldName, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldsValues =
			getInfoDisplayFieldsValues(t, locale);

		return infoDisplayFieldsValues.get(fieldName);
	}

	public default long getInfoDisplayObjectClassPK(T object) {
		return 0;
	}

	public InfoDisplayObjectProvider<T> getInfoDisplayObjectProvider(
			long classPK)
		throws PortalException;

	public InfoDisplayObjectProvider<T> getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException;

	public String getInfoURLSeparator();

	public default String getLabel(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	public default InfoDisplayObjectProvider<T>
			getPreviewInfoDisplayObjectProvider(long classPK, int type)
		throws PortalException {

		return null;
	}

	public default Map<String, Object> getVersionInfoDisplayFieldsValues(
			T t, long versionClassPK, Locale locale)
		throws PortalException {

		return getInfoDisplayFieldsValues(t, locale);
	}

}