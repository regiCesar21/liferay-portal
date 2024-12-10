/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.downloads.proxy.web.internal.portlet;

import com.liferay.osb.customer.constants.OSBCustomerConstants;
import com.liferay.osb.customer.downloads.proxy.web.internal.constants.DownloadsProxyPortletKeys;
import com.liferay.osb.customer.downloads.proxy.web.internal.util.AgreementUtil;
import com.liferay.osb.customer.downloads.proxy.web.internal.util.DownloadsUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=osb-downloads-proxy-portlet",
		"com.liferay.portlet.display-category=category.osb",
		"com.liferay.portlet.preferences-company-wide=true",
		"javax.portlet.display-name=OSB Downloads Proxy",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.check-auth-token=false",
		"javax.portlet.init-param.mvc-command-names-default-views=/view",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.name=" + DownloadsProxyPortletKeys.DOWNLOADS_PROXY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class DownloadsProxyPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		if (!isProcessActionRequest(actionRequest)) {
			return;
		}

		String fileName = ParamUtil.getString(actionRequest, "fileName");

		actionResponse.sendRedirect(_URL_PREFIX + fileName);

		sendAudit(actionRequest, fileName);
	}

	protected String generateCode(String fileName) {
		String shortFileName = FileUtil.getShortFileName(fileName);

		return DigesterUtil.digestHex(Digester.MD5, shortFileName + _CODE_SALT);
	}

	protected boolean isCustomerAccess(
		ThemeDisplay themeDisplay, PortletPreferences preferences,
		String fileName) {

		if (themeDisplay.getScopeGroupId() ==
				OSBCustomerConstants.GROUP_CUSTOMER_ID) {

			return true;
		}

		String customerAccessPatternString = preferences.getValue(
			"customerAccessPattern", null);

		if (Validator.isNull(customerAccessPatternString)) {
			return false;
		}

		if ((_customerAccessPattern == null) ||
			!customerAccessPatternString.equals(_customerAccessPatternString)) {

			_customerAccessPatternString = customerAccessPatternString;

			_customerAccessPattern = Pattern.compile(
				customerAccessPatternString);
		}

		Matcher matcher = _customerAccessPattern.matcher(fileName);

		if (!matcher.matches()) {
			return false;
		}

		if (_organizationLocalService.hasUserOrganization(
				themeDisplay.getUserId(),
				OSBCustomerConstants.ORGANIZATION_CUSTOMER_DXP_ID) ||
			_organizationLocalService.hasUserOrganization(
				themeDisplay.getUserId(),
				OSBCustomerConstants.ORGANIZATION_LIFERAY_INC_ID) ||
			_organizationLocalService.hasUserOrganization(
				themeDisplay.getUserId(),
				OSBCustomerConstants.ORGANIZATION_PARTNER_ID)) {

			return true;
		}

		return false;
	}

	protected boolean isGuestAccess(
		PortletPreferences preferences, String fileName, String code) {

		String guestAccessPatternString = preferences.getValue(
			"guestAccessPattern", null);

		if (Validator.isNull(guestAccessPatternString)) {
			return false;
		}

		if ((_guestAccessPattern == null) ||
			!guestAccessPatternString.equals(_guestAccessPatternString)) {

			_guestAccessPatternString = guestAccessPatternString;

			_guestAccessPattern = Pattern.compile(guestAccessPatternString);
		}

		Matcher matcher = _guestAccessPattern.matcher(fileName);

		if (!matcher.matches()) {
			return false;
		}

		if (!code.equals(generateCode(fileName))) {
			return false;
		}

		return true;
	}

	protected boolean isPrivateFixPack(String fileName) throws IOException {
		if (!fileName.startsWith(_FIX_PACK_DIR)) {
			return false;
		}

		if (!fileName.endsWith(".zip")) {
			return true;
		}

		String[] fileNameArray = StringUtil.split(fileName, CharPool.SLASH);

		if (fileNameArray.length != 5) {
			return true;
		}

		String liferayVersion = fileNameArray[2];
		String componentName = fileNameArray[3];

		StringBundler sb = new StringBundler(4);

		sb.append(_URL_PREFIX);
		sb.append(_FIX_PACK_DIR);
		sb.append(liferayVersion);
		sb.append("/public-components.txt");

		String key = _http.URLtoString(sb.toString(), true);

		String[] publicComponents = DownloadsUtil.getPublicComponents(
			_URL_PREFIX + StringPool.SLASH + key, liferayVersion);

		if (ArrayUtil.contains(publicComponents, componentName)) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean isProcessPortletRequest(PortletRequest portletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			String fileName = ParamUtil.getString(portletRequest, "fileName");

			if ((fileName.indexOf(StringPool.DOUBLE_PERIOD) != -1) ||
				!fileName.startsWith(StringPool.SLASH)) {

				return false;
			}

			PortletPreferences preferences = portletRequest.getPreferences();

			String code = ParamUtil.getString(portletRequest, "code");

			if (isGuestAccess(preferences, fileName, code)) {
				return true;
			}

			if (!themeDisplay.isSignedIn()) {
				return false;
			}

			if (isPrivateFixPack(fileName)) {
				return false;
			}

			if (isCustomerAccess(themeDisplay, preferences, fileName)) {
				return true;
			}

			if (isTrial(themeDisplay, preferences, fileName)) {
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	protected boolean isTrial(
			ThemeDisplay themeDisplay, PortletPreferences preferences,
			String fileName)
		throws PortalException {

		String trialPatternString = preferences.getValue("trialPattern", null);

		if (Validator.isNull(trialPatternString)) {
			return false;
		}

		if ((_trialPattern == null) ||
			!trialPatternString.equals(_trialPatternString)) {

			_trialPatternString = trialPatternString;

			_trialPattern = Pattern.compile(trialPatternString);
		}

		Matcher matcher = _trialPattern.matcher(fileName);

		if (!matcher.matches()) {
			return false;
		}

		if (fileName.startsWith("/trial/sync/")) {
			if (AgreementUtil.hasLiferaySyncEULA(themeDisplay.getUserId())) {
				return true;
			}
		}
		else {
			if (AgreementUtil.hasTrialEULA(themeDisplay.getUserId())) {
				return true;
			}
		}

		return false;
	}

	protected void sendAudit(ActionRequest actionRequest, String fileName) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("layoutURL", _portal.getLayoutFullURL(themeDisplay));

			AuditMessage auditMessage = new AuditMessage(
				ActionKeys.VIEW, OSBCustomerConstants.COMPANY_ID,
				user.getUserId(), user.getFullName(),
				"com.liferay.osb.downloads.portlet.DownloadsPortlet",
				"fileName", fileName, null, jsonObject);

			AuditRouterUtil.route(auditMessage);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final String _CODE_SALT = "L1f3R@y";

	private static final String _FIX_PACK_DIR = "/fix-packs/";

	private static final String _URL_PREFIX = "https://downloads.liferay.com";

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadsProxyPortlet.class);

	private static Pattern _customerAccessPattern;
	private static String _customerAccessPatternString;
	private static Pattern _guestAccessPattern;
	private static String _guestAccessPatternString;
	private static Pattern _trialPattern;
	private static String _trialPatternString;

	@Reference
	private Http _http;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

}