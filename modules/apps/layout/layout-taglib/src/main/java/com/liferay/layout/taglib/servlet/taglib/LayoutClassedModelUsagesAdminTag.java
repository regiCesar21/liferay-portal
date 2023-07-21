/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.servlet.taglib;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.taglib.internal.servlet.taglib.util.LayoutClassedModelUsagesTaglibUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class LayoutClassedModelUsagesAdminTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		LayoutClassedModelUsagesTaglibUtil.recordLayoutClassedModelUsage(
			getClassName(), getClassPK());

		request.setAttribute(
			ContentPageEditorWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER,
			ServletContextUtil.getFragmentCollectionContributorTracker());
		request.setAttribute(
			FragmentActionKeys.FRAGMENT_RENDERER_TRACKER,
			ServletContextUtil.getFragmentRendererTracker());

		return super.doStartTag();
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-layout:layout-classed-model-usages-admin:className",
			_className);
		httpServletRequest.setAttribute(
			"liferay-layout:layout-classed-model-usages-admin:classPK",
			String.valueOf(_classPK));
	}

	private static final String _PAGE =
		"/layout_classed_model_usages_admin/page.jsp";

	private String _className;
	private long _classPK;

}