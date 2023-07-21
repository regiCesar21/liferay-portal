/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.servlet.taglib;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.taglib.internal.display.context.RenderLayoutStructureDisplayContext;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class RenderLayoutStructureTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		_previousRenderLayoutStructureDisplayContext =
			(RenderLayoutStructureDisplayContext)request.getAttribute(
				RenderLayoutStructureDisplayContext.class.getName());

		request.setAttribute(
			RenderLayoutStructureDisplayContext.class.getName(),
			new RenderLayoutStructureDisplayContext(
				getFieldValues(),
				ServletContextUtil.getFrontendTokenDefinitionRegistry(),
				request,
				PipingServletResponse.createPipingServletResponse(pageContext),
				ServletContextUtil.getInfoItemServiceTracker(),
				ServletContextUtil.getInfoListRendererTracker(),
				ServletContextUtil.getLayoutDisplayPageProviderTracker(),
				ServletContextUtil.getLayoutListRetrieverTracker(),
				getLayoutStructure(),
				ServletContextUtil.getListObjectReferenceFactoryTracker(),
				getMainItemId(), getMode(),
				ServletContextUtil.getRequestContextMapper(),
				ServletContextUtil.getSegmentsEntryRetriever(),
				isShowPreview()));

		request.setAttribute(LayoutWebKeys.LAYOUT_STRUCTURE, _layoutStructure);

		return super.doStartTag();
	}

	public Map<String, Object> getFieldValues() {
		return _fieldValues;
	}

	public LayoutStructure getLayoutStructure() {
		return _layoutStructure;
	}

	public String getMainItemId() {
		return _mainItemId;
	}

	public String getMode() {
		return _mode;
	}

	public boolean isShowPreview() {
		return _showPreview;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setLayoutStructure(LayoutStructure layoutStructure) {
		_layoutStructure = layoutStructure;
	}

	public void setMainItemId(String mainItemId) {
		_mainItemId = mainItemId;
	}

	public void setMode(String mode) {
		_mode = mode;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowPreview(boolean showPreview) {
		_showPreview = showPreview;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		if (_previousRenderLayoutStructureDisplayContext != null) {
			request.setAttribute(
				RenderLayoutStructureDisplayContext.class.getName(),
				_previousRenderLayoutStructureDisplayContext);
		}
		else {
			request.removeAttribute(
				RenderLayoutStructureDisplayContext.class.getName());
		}

		_fieldValues = null;
		_layoutStructure = null;
		_mainItemId = null;
		_mode = FragmentEntryLinkConstants.VIEW;
		_previousRenderLayoutStructureDisplayContext = null;
		_showPreview = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             LayoutWebKeys.LAYOUT_STRUCTURE}
	 */
	@Deprecated
	protected static final String LAYOUT_STRUCTURE =
		RenderLayoutStructureTag.class.getName() + "#LAYOUT_STRUCTURE";

	private static final String _PAGE = "/render_layout_structure/page.jsp";

	private Map<String, Object> _fieldValues;
	private LayoutStructure _layoutStructure;
	private String _mainItemId;
	private String _mode = FragmentEntryLinkConstants.VIEW;
	private RenderLayoutStructureDisplayContext
		_previousRenderLayoutStructureDisplayContext;
	private boolean _showPreview;

}