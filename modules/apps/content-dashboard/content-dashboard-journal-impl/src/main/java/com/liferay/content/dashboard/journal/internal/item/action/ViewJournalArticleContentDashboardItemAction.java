/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.journal.internal.item.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ViewJournalArticleContentDashboardItemAction
	implements ContentDashboardItemAction {

	public ViewJournalArticleContentDashboardItemAction(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		HttpServletRequest httpServletRequest, JournalArticle journalArticle,
		Language language) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_httpServletRequest = httpServletRequest;
		_journalArticle = journalArticle;
		_language = language;
	}

	@Override
	public String getIcon() {
		return "view";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "view");
	}

	@Override
	public String getName() {
		return "view";
	}

	@Override
	public Type getType() {
		return Type.VIEW;
	}

	@Override
	public String getURL() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getViewURL(themeDisplay.getLocale(), themeDisplay);
	}

	@Override
	public String getURL(Locale locale) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getViewURL(locale, themeDisplay);
	}

	private String _getViewURL(Locale locale, ThemeDisplay themeDisplay) {
		try {
			ThemeDisplay clonedThemeDisplay =
				(ThemeDisplay)themeDisplay.clone();

			clonedThemeDisplay.setScopeGroupId(_journalArticle.getGroupId());

			return Optional.ofNullable(
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					JournalArticle.class.getName(),
					_journalArticle.getResourcePrimKey(), locale,
					clonedThemeDisplay)
			).orElse(
				StringPool.BLANK
			);
		}
		catch (CloneNotSupportedException | PortalException exception) {
			_log.error(exception, exception);

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewJournalArticleContentDashboardItemAction.class);

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final JournalArticle _journalArticle;
	private final Language _language;

}