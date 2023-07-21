/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.PageWidgetInstanceDefinitionSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageWidgetInstanceDefinition implements Cloneable, Serializable {

	public static PageWidgetInstanceDefinition toDTO(String json) {
		return PageWidgetInstanceDefinitionSerDes.toDTO(json);
	}

	public FragmentStyle getFragmentStyle() {
		return fragmentStyle;
	}

	public void setFragmentStyle(FragmentStyle fragmentStyle) {
		this.fragmentStyle = fragmentStyle;
	}

	public void setFragmentStyle(
		UnsafeSupplier<FragmentStyle, Exception> fragmentStyleUnsafeSupplier) {

		try {
			fragmentStyle = fragmentStyleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentStyle fragmentStyle;

	public FragmentViewport[] getFragmentViewports() {
		return fragmentViewports;
	}

	public void setFragmentViewports(FragmentViewport[] fragmentViewports) {
		this.fragmentViewports = fragmentViewports;
	}

	public void setFragmentViewports(
		UnsafeSupplier<FragmentViewport[], Exception>
			fragmentViewportsUnsafeSupplier) {

		try {
			fragmentViewports = fragmentViewportsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentViewport[] fragmentViewports;

	public WidgetInstance getWidgetInstance() {
		return widgetInstance;
	}

	public void setWidgetInstance(WidgetInstance widgetInstance) {
		this.widgetInstance = widgetInstance;
	}

	public void setWidgetInstance(
		UnsafeSupplier<WidgetInstance, Exception>
			widgetInstanceUnsafeSupplier) {

		try {
			widgetInstance = widgetInstanceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WidgetInstance widgetInstance;

	@Override
	public PageWidgetInstanceDefinition clone()
		throws CloneNotSupportedException {

		return (PageWidgetInstanceDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageWidgetInstanceDefinition)) {
			return false;
		}

		PageWidgetInstanceDefinition pageWidgetInstanceDefinition =
			(PageWidgetInstanceDefinition)object;

		return Objects.equals(
			toString(), pageWidgetInstanceDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageWidgetInstanceDefinitionSerDes.toJSON(this);
	}

}