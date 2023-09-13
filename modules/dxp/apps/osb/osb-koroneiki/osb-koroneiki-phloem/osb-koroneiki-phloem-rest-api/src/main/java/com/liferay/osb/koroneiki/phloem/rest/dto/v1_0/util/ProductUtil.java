/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Product;
import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.portal.vulcan.util.TransformUtil;

/**
 * @author Amos Fong
 */
public class ProductUtil {

	public static com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product
			toClientProduct(ProductEntry productEntry)
		throws Exception {

		return new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
			Product() {

			{
				dateCreated = productEntry.getCreateDate();
				dateModified = productEntry.getModifiedDate();
				externalLinks = TransformUtil.transformToArray(
					productEntry.getExternalLinks(),
					ExternalLinkUtil::toClientExternalLink,
					com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
						ExternalLink.class);
				key = productEntry.getProductEntryKey();
				name = productEntry.getName();
				properties = productEntry.getProductFieldsMap();
			}
		};
	}

	public static Product toProduct(ProductEntry productEntry)
		throws Exception {

		return new Product() {
			{
				dateCreated = productEntry.getCreateDate();
				dateModified = productEntry.getModifiedDate();
				externalLinks = TransformUtil.transformToArray(
					productEntry.getExternalLinks(),
					ExternalLinkUtil::toExternalLink, ExternalLink.class);
				key = productEntry.getProductEntryKey();
				name = productEntry.getName();
				properties = productEntry.getProductFieldsMap();
			}
		};
	}

}