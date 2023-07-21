/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.web.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.language.LanguageResources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class ExpandoValueConstraintResolver
	implements ConstraintResolver<ExpandoValue> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-expando-value";
	}

	@Override
	public Class<ExpandoValue> getModelClass() {
		return ExpandoValue.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-conflicting-expando-value-was-deleted";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"tableId", "columnId", "classPK"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<ExpandoValue> constraintResolverContext)
		throws PortalException {

		ExpandoValue expandoValue =
			constraintResolverContext.getTargetCTModel();

		_expandoValueLocalService.deleteExpandoValue(expandoValue);
	}

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

}