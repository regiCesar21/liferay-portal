<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.fragment.constants.FragmentActionKeys" %><%@
page import="com.liferay.fragment.model.FragmentEntryLink" %><%@
page import="com.liferay.fragment.renderer.DefaultFragmentRendererContext" %><%@
page import="com.liferay.fragment.renderer.FragmentRendererController" %><%@
page import="com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil" %><%@
page import="com.liferay.info.constants.InfoDisplayWebKeys" %><%@
page import="com.liferay.info.list.renderer.InfoListRenderer" %><%@
page import="com.liferay.layout.display.page.LayoutDisplayPageProvider" %><%@
page import="com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys" %><%@
page import="com.liferay.layout.responsive.ResponsiveLayoutStructureUtil" %><%@
page import="com.liferay.layout.taglib.internal.display.context.RenderLayoutStructureDisplayContext" %><%@
page import="com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem" %><%@
page import="com.liferay.layout.util.structure.ColumnLayoutStructureItem" %><%@
page import="com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem" %><%@
page import="com.liferay.layout.util.structure.DropZoneLayoutStructureItem" %><%@
page import="com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem" %><%@
page import="com.liferay.layout.util.structure.LayoutStructure" %><%@
page import="com.liferay.layout.util.structure.LayoutStructureItem" %><%@
page import="com.liferay.layout.util.structure.RootLayoutStructureItem" %><%@
page import="com.liferay.layout.util.structure.RowStyledLayoutStructureItem" %><%@
page import="com.liferay.portal.kernel.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portal.kernel.model.LayoutTemplate" %><%@
page import="com.liferay.portal.kernel.model.LayoutTemplateConstants" %><%@
page import="com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.template.StringTemplateResource" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Objects" %>