/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ProductEntry service. Represents a row in the &quot;OSB_ProductEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ProductEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.customer.admin.model.impl.ProductEntryImpl"
)
@ProviderType
public interface ProductEntry extends PersistedModel, ProductEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.customer.admin.model.impl.ProductEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ProductEntry, Long> PRODUCT_ENTRY_ID_ACCESSOR =
		new Accessor<ProductEntry, Long>() {

			@Override
			public Long get(ProductEntry productEntry) {
				return productEntry.getProductEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ProductEntry> getTypeClass() {
				return ProductEntry.class;
			}

		};
	public static final Accessor<ProductEntry, String>
		KORONEIKI_PRODUCT_KEY_ACCESSOR = new Accessor<ProductEntry, String>() {

			@Override
			public String get(ProductEntry productEntry) {
				return productEntry.getKoroneikiProductKey();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<ProductEntry> getTypeClass() {
				return ProductEntry.class;
			}

		};

	public java.util.List<com.liferay.portal.kernel.model.ListType>
		getAllLicenseVersionsListTypes();

	public java.util.List<com.liferay.portal.kernel.model.ListType>
		getAllVersionsListTypes();

	public String getEnvironmentLabel();

	public java.util.List<com.liferay.portal.kernel.model.ListType>
		getVersionsListTypes();

	public String getZendeskTag();

	public boolean isAnalyticsCloud();

	public boolean isAnalyticsCloudBusiness();

	public boolean isAnalyticsCloudEnterprise();

	public boolean isCommerce();

	public boolean isCommerceForDXPCloud();

	public boolean isCommerceSubscription();

	public boolean isDeveloperTools();

	public boolean isDeviceDetection();

	public boolean isDXP();

	public boolean isDXPCloud();

	public boolean isEnterpriseSearch();

	public boolean isExtendedPremiumSupport();

	public boolean isLiferayPaas();

	public boolean isManagementTools();

	public boolean isMobileExperience();

	public boolean isPortal();

	public boolean isProductivityTools();

	public boolean isSocialOffice();

	public boolean isUnlimitedEnterpriseWide();

}