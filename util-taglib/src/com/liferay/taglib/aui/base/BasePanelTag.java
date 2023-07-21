/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 */
public abstract class BasePanelTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getCollapsed() {
		return _collapsed;
	}

	public boolean getCollapsible() {
		return _collapsible;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getLabel() {
		return _label;
	}

	public boolean getLocalizeLabel() {
		return _localizeLabel;
	}

	public void setCollapsed(boolean collapsed) {
		_collapsed = collapsed;
	}

	public void setCollapsible(boolean collapsible) {
		_collapsible = collapsible;
	}

	public void setId(java.lang.String id) {
		_id = id;
	}

	public void setLabel(java.lang.String label) {
		_label = label;
	}

	public void setLocalizeLabel(boolean localizeLabel) {
		_localizeLabel = localizeLabel;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_collapsed = false;
		_collapsible = false;
		_id = null;
		_label = null;
		_localizeLabel = true;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "collapsed", _collapsed);
		setNamespacedAttribute(request, "collapsible", _collapsible);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "label", _label);
		setNamespacedAttribute(request, "localizeLabel", _localizeLabel);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:panel:";

	private static final String _END_PAGE =
		"/html/taglib/aui/panel/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/panel/start.jsp";

	private boolean _collapsed = false;
	private boolean _collapsible = false;
	private java.lang.String _id = null;
	private java.lang.String _label = null;
	private boolean _localizeLabel = true;

}