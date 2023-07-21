/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.alloy.mvc.sample.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the TodoList service. Represents a row in the &quot;AlloyMVCSample_TodoList&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TodoListModel
 * @generated
 */
@ImplementationClassName("com.liferay.alloy.mvc.sample.model.impl.TodoListImpl")
@ProviderType
public interface TodoList extends PersistedModel, TodoListModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.alloy.mvc.sample.model.impl.TodoListImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TodoList, Long> TODO_LIST_ID_ACCESSOR =
		new Accessor<TodoList, Long>() {

			@Override
			public Long get(TodoList todoList) {
				return todoList.getTodoListId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<TodoList> getTypeClass() {
				return TodoList.class;
			}

		};

}