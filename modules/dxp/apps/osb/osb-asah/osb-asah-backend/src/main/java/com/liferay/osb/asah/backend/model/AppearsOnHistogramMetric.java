/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.model;

import java.util.Objects;

/**
 * @author Marcos Martins
 */
public class AppearsOnHistogramMetric extends HistogramMetric {

	public AppearsOnHistogramMetric(
		String key, Metric metric, String pageTitle) {

		super(key, metric);

		_pageTitle = pageTitle;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!super.equals(obj) || !(obj instanceof AppearsOnHistogramMetric)) {
			return false;
		}

		AppearsOnHistogramMetric pageHistogramMetric =
			(AppearsOnHistogramMetric)obj;

		if (super.equalsMetric(pageHistogramMetric) &&
			Objects.equals(_pageTitle, pageHistogramMetric._pageTitle)) {

			return true;
		}

		return false;
	}

	public String getPageTitle() {
		return _pageTitle;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Objects.hash(_pageTitle);
	}

	private final String _pageTitle;

}