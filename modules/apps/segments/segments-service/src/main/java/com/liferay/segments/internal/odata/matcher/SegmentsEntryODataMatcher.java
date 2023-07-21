/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.odata.matcher;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.segments.internal.odata.entity.SegmentsEntryEntityModel;
import com.liferay.segments.odata.matcher.ODataMatcher;

import java.util.Map;
import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "target.class.name=com.liferay.segments.model.SegmentsEntry",
	service = ODataMatcher.class
)
public class SegmentsEntryODataMatcher implements ODataMatcher<Map<?, ?>> {

	@Override
	public boolean matches(String filterString, Map<?, ?> pattern)
		throws PortalException {

		try {
			Predicate<Map<?, ?>> predicate = _getPredicate(filterString);

			return predicate.test(pattern);
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to match filter: " + exception.getMessage(), exception);
		}
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + SegmentsEntryEntityModel.NAME + ")",
		unbind = "unbindFilterParser"
	)
	public void setFilterParser(FilterParser filterParser) {
		if (_log.isInfoEnabled()) {
			_log.info("Binding " + filterParser);
		}

		_filterParser = filterParser;
	}

	public void unbindFilterParser(FilterParser filterParser) {
		if (_log.isInfoEnabled()) {
			_log.info("Unbinding " + filterParser);
		}

		_filterParser = null;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + SegmentsEntryEntityModel.NAME + ")",
		unbind = "unbindEntityModel"
	)
	protected void setEntityModel(EntityModel entityModel) {
		if (_log.isInfoEnabled()) {
			_log.info("Binding " + entityModel);
		}

		_entityModel = entityModel;
	}

	protected void unbindEntityModel(EntityModel entityModel) {
		if (_log.isInfoEnabled()) {
			_log.info("Unbinding " + entityModel);
		}

		_entityModel = null;
	}

	private Predicate<Map<?, ?>> _getPredicate(String filterString)
		throws Exception {

		Filter filter = new Filter(_filterParser.parse(filterString));

		try {
			return _expressionConvert.convert(
				filter.getExpression(), LocaleUtil.getDefault(), _entityModel);
		}
		catch (Exception exception) {
			throw new InvalidFilterException(
				"Invalid filter: " + exception.getMessage(), exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryODataMatcher.class);

	private volatile EntityModel _entityModel;

	@Reference(target = "(result.class.name=java.util.function.Predicate)")
	private ExpressionConvert<Predicate<Map<?, ?>>> _expressionConvert;

	private FilterParser _filterParser;

}