/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.servlet.taglib.ui;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "form.navigator.entry.order:Integer=80",
	service = FormNavigatorEntry.class
)
public class JournalDisplayPageFormNavigatorEntry
	extends BaseJournalFormNavigatorEntry {

	@Override
	public String getKey() {
		return "display-page-template";
	}

	@Override
	public boolean isVisible(User user, JournalArticle article) {
		if (_isDepotArticle(article) || isGlobalScopeArticle(article) ||
			_isGlobalStructure(article)) {

			return false;
		}

		return true;
	}

	@Reference(target = "(view=private)", unbind = "-")
	public void setPrivateLayoutsItemSelectorView(
		ItemSelectorView<?> itemSelectorView) {
	}

	@Reference(target = "(view=public)", unbind = "-")
	public void setPublicLayoutsItemSelectorView(
		ItemSelectorView<?> itemSelectorView) {
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/article/asset_display_page.jsp";
	}

	private Group _getGroup(JournalArticle article) {
		if ((article != null) && (article.getId() > 0)) {
			return _groupLocalService.fetchGroup(article.getGroupId());
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		return themeDisplay.getScopeGroup();
	}

	private boolean _isDepotArticle(JournalArticle article) {
		Group group = _getGroup(article);

		if ((group != null) && group.isDepot()) {
			return true;
		}

		return false;
	}

	private boolean _isGlobalStructure(JournalArticle article) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		long classNameId = BeanParamUtil.getLong(
			article, portletRequest, "classNameId");

		if (classNameId != _portal.getClassNameId(DDMStructure.class)) {
			return false;
		}

		long classPK = BeanParamUtil.getLong(
			article, portletRequest, "classPK");

		if (classPK == 0) {
			return false;
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			classPK);

		if (ddmStructure == null) {
			long groupId = BeanParamUtil.getLong(
				article, portletRequest, "groupId");

			String ddmStructureKey = BeanParamUtil.getString(
				article, portletRequest, "ddmStructureKey");

			ddmStructure = _ddmStructureLocalService.fetchStructure(
				groupId, _portal.getClassNameId(JournalArticle.class),
				ddmStructureKey);
		}

		if (ddmStructure == null) {
			return false;
		}

		Group group = _groupLocalService.fetchGroup(ddmStructure.getGroupId());

		if (group == null) {
			return false;
		}

		if (group.isCompany()) {
			return true;
		}

		return false;
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}