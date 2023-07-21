/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.internal.listener;

import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ContentPageEditorListenerTracker.class)
public class ContentPageEditorListenerTrackerImpl
	implements ContentPageEditorListenerTracker {

	@Override
	public List<ContentPageEditorListener> getContentPageEditorListeners() {
		return new ArrayList<>(_contentPageEditorListeners);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setContentPageEditorListener(
		ContentPageEditorListener contentPageEditorListener) {

		_contentPageEditorListeners.add(contentPageEditorListener);
	}

	protected void unsetContentPageEditorListener(
		ContentPageEditorListener contentPageEditorListener) {

		_contentPageEditorListeners.remove(contentPageEditorListener);
	}

	private final List<ContentPageEditorListener> _contentPageEditorListeners =
		new CopyOnWriteArrayList<>();

}