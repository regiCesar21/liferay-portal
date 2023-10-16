/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Riccardo Ferrari
 */
public class SegmentsExperimentUtil {

	public static JSONObject toGoalJSONObject(
		Locale locale, UnicodeProperties typeSettingsUnicodeProperties) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, SegmentsExperimentUtil.class);

		String goal = typeSettingsUnicodeProperties.getProperty("goal");

		return JSONUtil.put(
			"label", LanguageUtil.get(resourceBundle, goal)
		).put(
			"target", typeSettingsUnicodeProperties.getProperty("goalTarget")
		).put(
			"value", goal
		);
	}

	public static JSONObject toSegmentsExperimentJSONObject(
			Locale locale, SegmentsExperiment segmentsExperiment)
		throws PortalException {

		if (segmentsExperiment == null) {
			return null;
		}

		return JSONUtil.put(
			"confidenceLevel", segmentsExperiment.getConfidenceLevel()
		).put(
			"description", segmentsExperiment.getDescription()
		).put(
			"detailsURL",
			_getViewSegmentsExperimentDetailsURL(segmentsExperiment)
		).put(
			"editable", _isEditable(segmentsExperiment)
		).put(
			"goal",
			toGoalJSONObject(
				locale, segmentsExperiment.getTypeSettingsProperties())
		).put(
			"name", segmentsExperiment.getName()
		).put(
			"segmentsEntryName", segmentsExperiment.getSegmentsEntryName(locale)
		).put(
			"segmentsExperienceId",
			String.valueOf(segmentsExperiment.getSegmentsExperienceId())
		).put(
			"segmentsExperimentId",
			String.valueOf(segmentsExperiment.getSegmentsExperimentId())
		).put(
			"status", toStatusJSONObject(locale, segmentsExperiment.getStatus())
		);
	}

	public static JSONObject toSegmentsExperimentRelJSONObject(
			Locale locale, SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		if (segmentsExperimentRel == null) {
			return null;
		}

		return JSONUtil.put(
			"control", segmentsExperimentRel.isControl()
		).put(
			"name", segmentsExperimentRel.getName(locale)
		).put(
			"segmentsExperienceId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperienceId())
		).put(
			"segmentsExperimentId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperimentId())
		).put(
			"segmentsExperimentRelId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperimentRelId())
		).put(
			"split", segmentsExperimentRel.getSplit()
		);
	}

	public static JSONObject toStatusJSONObject(Locale locale, int status) {
		Optional<SegmentsExperimentConstants.Status> statusObjectOptional =
			SegmentsExperimentConstants.Status.parse(status);

		if (!statusObjectOptional.isPresent()) {
			return null;
		}

		SegmentsExperimentConstants.Status statusObject =
			statusObjectOptional.get();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, SegmentsExperimentUtil.class);

		return JSONUtil.put(
			"label", LanguageUtil.get(resourceBundle, statusObject.getLabel())
		).put(
			"value", statusObject.getValue()
		);
	}

	private static String _getLiferayAnalyticsURL(long companyId) {
		return PrefsPropsUtil.getString(companyId, "liferayAnalyticsURL");
	}

	private static String _getViewSegmentsExperimentDetailsURL(
		SegmentsExperiment segmentsExperiment) {

		if (segmentsExperiment == null) {
			return StringPool.BLANK;
		}

		String liferayAnalyticsURL = _getLiferayAnalyticsURL(
			segmentsExperiment.getCompanyId());

		if (Validator.isNull(liferayAnalyticsURL)) {
			return StringPool.BLANK;
		}

		return liferayAnalyticsURL + "/tests/overview/" +
			segmentsExperiment.getSegmentsExperimentKey();
	}

	private static boolean _isEditable(SegmentsExperiment segmentsExperiment) {
		SegmentsExperimentConstants.Status status =
			SegmentsExperimentConstants.Status.valueOf(
				segmentsExperiment.getStatus());

		return status.isEditable();
	}

}