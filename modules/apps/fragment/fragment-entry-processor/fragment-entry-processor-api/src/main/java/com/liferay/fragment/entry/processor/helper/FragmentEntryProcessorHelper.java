/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.helper;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface FragmentEntryProcessorHelper {

	public default String formatMappedValue(Object value, Locale locale) {
		return value.toString();
	}

	public String getEditableValue(JSONObject jsonObject, Locale locale);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getEditableValue(JSONObject, Locale)}
	 */
	@Deprecated
	public String getEditableValue(
		JSONObject jsonObject, Locale locale, long[] segmentsExperienceIds);

	public Object getMappedCollectionValue(
			JSONObject jsonObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException;

	public default Object getMappedInfoItemFieldValue(
			JSONObject jsonObject,
			Map<Long, InfoItemFieldValues> infoItemFieldValuesMap,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		Map<Long, Map<String, Object>> infoDisplaysFieldValuesMap =
			new HashMap<>();

		for (Map.Entry<Long, InfoItemFieldValues> entry :
				infoItemFieldValuesMap.entrySet()) {

			InfoItemFieldValues infoItemFieldValues = entry.getValue();

			infoDisplaysFieldValuesMap.put(
				entry.getKey(),
				infoItemFieldValues.getMap(
					fragmentEntryProcessorContext.getLocale()));
		}

		return getMappedValue(
			jsonObject, infoDisplaysFieldValuesMap,
			fragmentEntryProcessorContext);
	}

	public default Object getMappedInfoItemFieldValue(
			JSONObject jsonObject,
			Map<Long, InfoItemFieldValues> infoItemFieldValuesMap, String mode,
			Locale locale, long previewClassPK, long previewClassNameId,
			int previewType)
		throws PortalException {

		Map<Long, Map<String, Object>> infoDisplaysFieldValuesMap =
			new HashMap<>();

		for (Map.Entry<Long, InfoItemFieldValues> entry :
				infoItemFieldValuesMap.entrySet()) {

			InfoItemFieldValues infoItemFieldValues = entry.getValue();

			infoDisplaysFieldValuesMap.put(
				entry.getKey(), infoItemFieldValues.getMap(locale));
		}

		return getMappedValue(
			jsonObject, infoDisplaysFieldValuesMap, mode, locale,
			previewClassPK, previewClassNameId, previewType);
	}

	public Object getMappedLayoutValue(
			JSONObject jsonObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getMappedInfoItemFieldValue(JSONObject, Map, FragmentEntryProcessorContext)}
	 */
	@Deprecated
	public Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> infoDisplaysFieldValues,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException;

	public default Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> infoDisplaysFieldValues, String mode,
			Locale locale, long previewClassPK, int previewType)
		throws PortalException {

		return getMappedValue(
			jsonObject, infoDisplaysFieldValues, mode, locale, previewClassPK,
			0, previewType);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getMappedInfoItemFieldValue(JSONObject, Map, String, Locale, long, long, int)}
	 */
	@Deprecated
	public Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> infoDisplaysFieldValues, String mode,
			Locale locale, long previewClassPK, long previewClassNameId,
			int previewType)
		throws PortalException;

	public boolean isAssetDisplayPage(String mode);

	public boolean isMapped(JSONObject jsonObject);

	public boolean isMappedCollection(JSONObject jsonObject);

	public boolean isMappedLayout(JSONObject jsonObject);

	public String processTemplate(
			String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException;

}