/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.util.configuration;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public interface FragmentEntryConfigurationParser {

	public JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration);

	public JSONObject getConfigurationJSONObject(
			String configuration, String editableValues)
		throws JSONException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getConfigurationJSONObject(String, String)}
	 *             #getContextObjects(JSONObject, String, long[])}
	 */
	@Deprecated
	public JSONObject getConfigurationJSONObject(
			String configuration, String editableValues,
			long[] segmentsExperienceIds)
		throws JSONException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getContextObjects(JSONObject, String, long[])}
	 */
	@Deprecated
	public Map<String, Object> getContextObjects(
		JSONObject configurationValuesJSONObject, String configuration);

	public Map<String, Object> getContextObjects(
		JSONObject configurationValuesJSONObject, String configuration,
		long[] segmentsEntryIds);

	public Object getFieldValue(
		FragmentConfigurationField fragmentConfigurationField, String value);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Object getFieldValue(
		String configuration, String editableValues,
		long[] segmentsExperienceIds, String name);

	public Object getFieldValue(
		String configuration, String editableValues, String name);

	public List<FragmentConfigurationField> getFragmentConfigurationFields(
		String configuration);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public JSONObject getSegmentedConfigurationValues(
		long[] segmentsExperienceIds, JSONObject configurationValuesJSONObject);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean isPersonalizationSupported(JSONObject jsonObject);

	public String translateConfiguration(
		JSONObject jsonObject, ResourceBundle resourceBundle);

}