/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.search.web.internal.portlet.template;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetTermDisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	enabled = false, immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_SPECIFICATION_OPTION_FACETS,
	service = TemplateHandler.class
)
public class CPSpecificationOptionFacetsPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CPSpecificationOptionsSearchFacetTermDisplayContext.class.
			getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CPPortletKeys.CP_SPECIFICATION_OPTION_FACETS, resourceBundle);

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return CPPortletKeys.CP_SPECIFICATION_OPTION_FACETS;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup templateVariableGroup =
			templateVariableGroups.get("fields");

		templateVariableGroup.empty();

		templateVariableGroup.addVariable(
			"cp-specification-option-facet-display-context",
			CPSpecificationOptionsSearchFacetDisplayContext.class,
			"cpSpecificationOptionsSearchFacetDisplayContext");
		templateVariableGroup.addVariable(
			"term-frequency", Integer.class,
			PortletDisplayTemplateConstants.ENTRY, "getFrequency()");
		templateVariableGroup.addVariable(
			"term-name", String.class, PortletDisplayTemplateConstants.ENTRY,
			"getDisplayName()");
		templateVariableGroup.addCollectionVariable(
			"terms", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"term", CPSpecificationOptionsSearchFacetTermDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getDisplayName()");

		TemplateVariableGroup
			cpSpecificationOptionsServicesTemplateVariableGroup =
				new TemplateVariableGroup(
					"category-services", getRestrictedVariables(language));

		cpSpecificationOptionsServicesTemplateVariableGroup.
			setAutocompleteEnabled(false);

		templateVariableGroups.put(
			cpSpecificationOptionsServicesTemplateVariableGroup.getLabel(),
			cpSpecificationOptionsServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/commerce/product/content/search/web/internal" +
			"/portlet/template/dependencies/specification-option-facets" +
				"/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}