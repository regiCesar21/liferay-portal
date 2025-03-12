/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.model;

import com.liferay.osb.asah.common.model.ResultBag;

import org.springframework.hateoas.EntityModel;

/**
 * @author Marcellus Tavares
 */
public class ResultBagEntityModel<T>
	extends EntityModel<ResultBag<EntityModel<T>>> {

	public ResultBagEntityModel(ResultBag<EntityModel<T>> content) {
		super(content);
	}

}