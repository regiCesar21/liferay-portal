/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.constants;

import com.liferay.osb.customer.admin.model.ProductEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Kyle Bischof
 */
public class ProductEntryConstants {

	public static final int COMMERCE_LICENSE_VERSION_1 = 47000;

	public static final int COMMERCE_VERSION_1_0 = 44000;

	public static final int COMMERCE_VERSION_1_1 = 44020;

	public static final int COMMERCE_VERSION_2_0 = 44100;

	public static final int COMMERCE_VERSION_2_1 = 44120;

	public static final int COMMERCE_VERSION_3_0 = 44200;

	public static final int COMMERCE_VERSION_4_0 = 44300;

	public static final int COMMERCE_VERSION_OTHER = 44999;

	public static final int DIGITAL_ENTERPRISE_MAJOR_VERSION_7 = 42000;

	public static final int DIGITAL_ENTERPRISE_MINOR_VERSION_7_0 = 43000;

	public static final int DIGITAL_ENTERPRISE_MINOR_VERSION_7_1 = 43001;

	public static final int DIGITAL_ENTERPRISE_MINOR_VERSION_7_2 = 43002;

	public static final int DIGITAL_ENTERPRISE_MINOR_VERSION_7_3 = 43003;

	public static final int DIGITAL_ENTERPRISE_MINOR_VERSION_7_4 = 43004;

	public static final int DIGITAL_ENTERPRISE_VERSION_7_0_10 = 41000;

	public static final int DIGITAL_ENTERPRISE_VERSION_7_0_20 = 41019;

	public static final int DIGITAL_ENTERPRISE_VERSION_7_1_10 = 41100;

	public static final int DIGITAL_ENTERPRISE_VERSION_7_2_10 = 41200;

	public static final int DIGITAL_ENTERPRISE_VERSION_7_3_10 = 41300;

	public static final int DIGITAL_ENTERPRISE_VERSION_7_4_10 = 41400;

	public static final int ENVIRONMENT_ANY = 1;

	public static final int ENVIRONMENT_BACKUP = 2;

	public static final int ENVIRONMENT_DEVELOPMENT = 3;

	public static final int ENVIRONMENT_NON_PRODUCTION = 4;

	public static final int ENVIRONMENT_NONE = 5;

	public static final int ENVIRONMENT_PRODUCTION = 6;

	public static final Date EOPS_DATE_PORTAL_VERSION_5_1 = PortalUtil.getDate(
		Calendar.DECEMBER, 16, 2012);

	public static final Date EOPS_DATE_PORTAL_VERSION_5_2 = PortalUtil.getDate(
		Calendar.JUNE, 1, 2013);

	public static final Date EOPS_DATE_PORTAL_VERSION_6_0 = PortalUtil.getDate(
		Calendar.SEPTEMBER, 10, 2014);

	public static final Date EOPS_DATE_PORTAL_VERSION_6_1 = PortalUtil.getDate(
		Calendar.FEBRUARY, 22, 2016);

	public static final Date EOPS_DATE_PORTAL_VERSION_6_2 = PortalUtil.getDate(
		Calendar.DECEMBER, 2, 2017);

	public static final String LIST_TYPE_COMMERCE_ALL_VERSIONS =
		ProductEntry.class.getName() + ".commerceAllVersions";

	public static final String LIST_TYPE_COMMERCE_LICENSE_VERSIONS =
		ProductEntry.class.getName() + ".commerceLicenseVersions";

	public static final String LIST_TYPE_COMMERCE_MAJOR_VERSIONS =
		ProductEntry.class.getName() + ".commerceMajorVersions";

	public static final String LIST_TYPE_DIGITAL_ENTERPRISE_ALL_VERSIONS =
		ProductEntry.class.getName() + ".dxpAllVersions";

	public static final String LIST_TYPE_DIGITAL_ENTERPRISE_MAJOR_VERSIONS =
		ProductEntry.class.getName() + ".dxpMajorVersions";

	public static final String LIST_TYPE_DIGITAL_ENTERPRISE_MINOR_VERSIONS =
		ProductEntry.class.getName() + ".dxpMinorVersions";

	public static final String LIST_TYPE_PORTAL_ALL_VERSIONS =
		ProductEntry.class.getName() + ".portalAllVersions";

	public static final String LIST_TYPE_PORTAL_MAJOR_VERSIONS =
		ProductEntry.class.getName() + ".portalMajorVersions";

	public static final String LIST_TYPE_PORTAL_MINOR_VERSIONS =
		ProductEntry.class.getName() + ".portalMinorVersions";

	public static final String LIST_TYPE_SOCIAL_OFFICE_ALL_VERSIONS =
		ProductEntry.class.getName() + ".soAllVersions";

	public static final String LIST_TYPE_SOCIAL_OFFICE_MAJOR_VERSIONS =
		ProductEntry.class.getName() + ".soMajorVersions";

	public static final String LIST_TYPE_SOCIAL_OFFICE_MINOR_VERSIONS =
		ProductEntry.class.getName() + ".soMinorVersions";

	public static final int[] LIST_TYPES_DEPRECATED = {
		ProductEntryConstants.PORTAL_VERSION_4_4_0,
		ProductEntryConstants.PORTAL_VERSION_5_1_3,
		ProductEntryConstants.PORTAL_VERSION_5_1_4,
		ProductEntryConstants.PORTAL_VERSION_5_1_5,
		ProductEntryConstants.PORTAL_VERSION_5_1_6,
		ProductEntryConstants.PORTAL_VERSION_5_1_7,
		ProductEntryConstants.PORTAL_VERSION_5_1_8,
		ProductEntryConstants.PORTAL_VERSION_5_1_9
	};

	public static final int PORTAL_MAJOR_VERSION_5 = 21000;

	public static final int PORTAL_MAJOR_VERSION_6 = 21001;

	public static final int PORTAL_MINOR_VERSION_5_1 = 22000;

	public static final int PORTAL_MINOR_VERSION_5_2 = 22001;

	public static final int PORTAL_MINOR_VERSION_6_0 = 22002;

	public static final int PORTAL_MINOR_VERSION_6_1 = 22003;

	public static final int PORTAL_MINOR_VERSION_6_2 = 22004;

	public static final int PORTAL_VERSION_4_4_0 = 20000;

	public static final int PORTAL_VERSION_5 = 21000;

	public static final int PORTAL_VERSION_5_1 = 22000;

	public static final int PORTAL_VERSION_5_1_3 = 20001;

	public static final int PORTAL_VERSION_5_1_4 = 20002;

	public static final int PORTAL_VERSION_5_1_5 = 20003;

	public static final int PORTAL_VERSION_5_1_6 = 20004;

	public static final int PORTAL_VERSION_5_1_7 = 20005;

	public static final int PORTAL_VERSION_5_1_8 = 20006;

	public static final int PORTAL_VERSION_5_1_9 = 20007;

	public static final int PORTAL_VERSION_5_2_4 = 20020;

	public static final int PORTAL_VERSION_5_2_5 = 20021;

	public static final int PORTAL_VERSION_5_2_6 = 20022;

	public static final int PORTAL_VERSION_5_2_7 = 20023;

	public static final int PORTAL_VERSION_5_2_8 = 20024;

	public static final int PORTAL_VERSION_5_2_9 = 20025;

	public static final int PORTAL_VERSION_6_0_10 = 20040;

	public static final int PORTAL_VERSION_6_0_11 = 20041;

	public static final int PORTAL_VERSION_6_0_12 = 20042;

	public static final int PORTAL_VERSION_6_1_10 = 20060;

	public static final int PORTAL_VERSION_6_1_20 = 20061;

	public static final int PORTAL_VERSION_6_1_30 = 20062;

	public static final int PORTAL_VERSION_6_2_10 = 20080;

	public static final int PORTAL_VERSION_6_2_20 = 20099;

	public static final int PORTAL_VERSION_OTHER = 20999;

	public static final String PRODUCT_ID_COMMERCE =
		"9a473157-06a6-44b6-b017-a360ffaf5f38";

	public static final String PRODUCT_ID_PORTAL = "Portal";

	public static final String ROOT_COMMERCE_SUBSCRIPTION =
		"Commerce Subscription";

	public static final String ROOT_DXP_CLOUD = "DXP Cloud";

	public static final String ROOT_LIFERAY_PAAS = "Liferay PaaS";

	public static final String ROOT_NAME_DXP = "DXP";

	public static final String ROOT_NAME_PORTAL = "Portal";

	public static final int SOCIAL_OFFICE_VERSION_1_5_0 = 23000;

	public static final int SOCIAL_OFFICE_VERSION_1_6_0 = 23001;

	public static final int SOCIAL_OFFICE_VERSION_2_0_3 = 23002;

	public static final int SOCIAL_OFFICE_VERSION_2_0_4 = 23003;

	public static final int SOCIAL_OFFICE_VERSION_2_0_5 = 23004;

	public static final int SOCIAL_OFFICE_VERSION_2_1_0 = 23020;

	public static final int SOCIAL_OFFICE_VERSION_3_0_0 = 23040;

	public static final int SOCIAL_OFFICE_VERSION_3_0_1 = 23041;

	public static final int SOCIAL_OFFICE_VERSION_3_0_2 = 23042;

	public static final int SOCIAL_OFFICE_VERSION_3_1_0 = 23060;

	public static final int SOCIAL_OFFICE_VERSION_3_1_1 = 23061;

	public static final int SOCIAL_OFFICE_VERSION_OTHER = 23999;

	public static String getAllListType(String majorListType) {
		if (majorListType.equals(LIST_TYPE_COMMERCE_MAJOR_VERSIONS)) {
			return LIST_TYPE_COMMERCE_ALL_VERSIONS;
		}
		else if (majorListType.equals(LIST_TYPE_PORTAL_MAJOR_VERSIONS)) {
			return LIST_TYPE_PORTAL_ALL_VERSIONS;
		}
		else if (majorListType.equals(
					LIST_TYPE_DIGITAL_ENTERPRISE_MAJOR_VERSIONS)) {

			return LIST_TYPE_DIGITAL_ENTERPRISE_ALL_VERSIONS;
		}

		return majorListType;
	}

	public static String getEnvironmentLabel(int environment) {
		if (environment == ENVIRONMENT_ANY) {
			return "any";
		}
		else if (environment == ENVIRONMENT_BACKUP) {
			return "backup";
		}
		else if (environment == ENVIRONMENT_DEVELOPMENT) {
			return "development";
		}
		else if (environment == ENVIRONMENT_NON_PRODUCTION) {
			return "non-production";
		}
		else if (environment == ENVIRONMENT_NONE) {
			return "none";
		}
		else if (environment == ENVIRONMENT_PRODUCTION) {
			return "production";
		}

		return "N/A";
	}

	public static Date getEOPSDate(int version) {
		if ((version >= PORTAL_VERSION_5_1_3) &&
			(version < PORTAL_VERSION_5_2_4)) {

			return EOPS_DATE_PORTAL_VERSION_5_1;
		}
		else if (isPortalVersion5_2(version)) {
			return EOPS_DATE_PORTAL_VERSION_5_2;
		}
		else if (isPortalVersion6_0(version)) {
			return EOPS_DATE_PORTAL_VERSION_6_0;
		}
		else if (isPortalVersion6_1(version)) {
			return EOPS_DATE_PORTAL_VERSION_6_1;
		}
		else if (isPortalVersion6_2(version)) {
			return EOPS_DATE_PORTAL_VERSION_6_2;
		}

		return null;
	}

	public static int getMajorVersion(int productVersion) {
		if (isDigitalEnterpriseVersion7_0(productVersion) ||
			isDigitalEnterpriseVersion7_1(productVersion) ||
			isDigitalEnterpriseVersion7_2(productVersion) ||
			isDigitalEnterpriseVersion7_3(productVersion) ||
			isDigitalEnterpriseVersion7_4(productVersion)) {

			return DIGITAL_ENTERPRISE_MAJOR_VERSION_7;
		}
		else if (isPortalVersion6_2(productVersion) ||
				 isPortalVersion6_1(productVersion) ||
				 isPortalVersion6_0(productVersion)) {

			return PORTAL_MAJOR_VERSION_6;
		}
		else if (isPortalVersion5_2(productVersion) ||
				 isPortalVersion5_1(productVersion)) {

			return PORTAL_MAJOR_VERSION_5;
		}

		return 0;
	}

	public static int getMinorVersion(int productVersion) {
		if (isDigitalEnterpriseVersion7_4(productVersion)) {
			return DIGITAL_ENTERPRISE_MINOR_VERSION_7_4;
		}
		else if (isDigitalEnterpriseVersion7_3(productVersion)) {
			return DIGITAL_ENTERPRISE_MINOR_VERSION_7_3;
		}
		else if (isDigitalEnterpriseVersion7_2(productVersion)) {
			return DIGITAL_ENTERPRISE_MINOR_VERSION_7_2;
		}
		else if (isDigitalEnterpriseVersion7_1(productVersion)) {
			return DIGITAL_ENTERPRISE_MINOR_VERSION_7_1;
		}
		else if (isDigitalEnterpriseVersion7_0(productVersion)) {
			return DIGITAL_ENTERPRISE_MINOR_VERSION_7_0;
		}
		else if (isPortalVersion6_2(productVersion)) {
			return PORTAL_MINOR_VERSION_6_2;
		}
		else if (isPortalVersion6_1(productVersion)) {
			return PORTAL_MINOR_VERSION_6_1;
		}
		else if (isPortalVersion6_0(productVersion)) {
			return PORTAL_MINOR_VERSION_6_0;
		}
		else if (isPortalVersion5_2(productVersion)) {
			return PORTAL_MINOR_VERSION_5_2;
		}
		else if (isPortalVersion5_1(productVersion)) {
			return PORTAL_MINOR_VERSION_5_1;
		}

		return 0;
	}

	public static String getSupportPhaseLabel(int version) {
		Date now = new Date();

		Date endOfPremiumSupportDate = getEOPSDate(version);

		if ((endOfPremiumSupportDate != null) &&
			now.after(endOfPremiumSupportDate)) {

			return "limited";
		}

		return StringPool.BLANK;
	}

	public static boolean isCommerce(long listTypeId) {
		if (listTypeId == COMMERCE_LICENSE_VERSION_1) {
			return true;
		}
		else if ((listTypeId >= COMMERCE_VERSION_1_0) &&
				 (listTypeId < COMMERCE_VERSION_OTHER)) {

			return true;
		}

		return false;
	}

	public static boolean isCommerceVersion1_1(long commerceVersion) {
		if (commerceVersion == COMMERCE_VERSION_1_1) {
			return true;
		}

		return false;
	}

	public static boolean isCommerceVersion2_0(long commerceVersion) {
		if (commerceVersion == COMMERCE_VERSION_2_0) {
			return true;
		}

		return false;
	}

	public static boolean isCommerceVersion2_1(long commerceVersion) {
		if (commerceVersion == COMMERCE_VERSION_2_1) {
			return true;
		}

		return false;
	}

	public static boolean isCommerceVersion3_0(long commerceVersion) {
		if (commerceVersion == COMMERCE_VERSION_3_0) {
			return true;
		}

		return false;
	}

	public static boolean isCommerceVersion4_0(long commerceVersion) {
		if (commerceVersion == COMMERCE_VERSION_4_0) {
			return true;
		}

		return false;
	}

	public static boolean isDigitalEnterpriseMajorVersion7(
		long digitalEnterpriseMajorVersion) {

		if (digitalEnterpriseMajorVersion ==
				DIGITAL_ENTERPRISE_MAJOR_VERSION_7) {

			return true;
		}

		return false;
	}

	public static boolean isDigitalEnterpriseVersion7_0(
		long digitalEnterpriseVersion) {

		if ((digitalEnterpriseVersion >= DIGITAL_ENTERPRISE_VERSION_7_0_10) &&
			(digitalEnterpriseVersion < DIGITAL_ENTERPRISE_VERSION_7_0_20)) {

			return true;
		}

		return false;
	}

	public static boolean isDigitalEnterpriseVersion7_1(
		long digitalEnterpriseVersion) {

		if (digitalEnterpriseVersion == DIGITAL_ENTERPRISE_VERSION_7_1_10) {
			return true;
		}

		return false;
	}

	public static boolean isDigitalEnterpriseVersion7_2(
		long digitalEnterpriseVersion) {

		if (digitalEnterpriseVersion == DIGITAL_ENTERPRISE_VERSION_7_2_10) {
			return true;
		}

		return false;
	}

	public static boolean isDigitalEnterpriseVersion7_3(
		long digitalEnterpriseVersion) {

		if (digitalEnterpriseVersion == DIGITAL_ENTERPRISE_VERSION_7_3_10) {
			return true;
		}

		return false;
	}

	public static boolean isDigitalEnterpriseVersion7_4(
		long digitalEnterpriseVersion) {

		if (digitalEnterpriseVersion == DIGITAL_ENTERPRISE_VERSION_7_4_10) {
			return true;
		}

		return false;
	}

	public static boolean isPortalVersion(long portalVersion) {
		if ((portalVersion >= PORTAL_VERSION_4_4_0) &&
			(portalVersion < PORTAL_VERSION_6_2_20)) {

			return true;
		}

		return false;
	}

	public static boolean isPortalVersion5_1(long portalVersion) {
		if ((portalVersion >= PORTAL_VERSION_5_1_3) &&
			(portalVersion < PORTAL_VERSION_5_2_4)) {

			return true;
		}

		return false;
	}

	public static boolean isPortalVersion5_2(long portalVersion) {
		if ((portalVersion >= PORTAL_VERSION_5_2_4) &&
			(portalVersion < PORTAL_VERSION_6_0_10)) {

			return true;
		}

		return false;
	}

	public static boolean isPortalVersion6_0(long portalVersion) {
		if ((portalVersion >= PORTAL_VERSION_6_0_10) &&
			(portalVersion < PORTAL_VERSION_6_1_10)) {

			return true;
		}

		return false;
	}

	public static boolean isPortalVersion6_1(long portalVersion) {
		if ((portalVersion >= PORTAL_VERSION_6_1_10) &&
			(portalVersion < PORTAL_VERSION_6_2_10)) {

			return true;
		}

		return false;
	}

	public static boolean isPortalVersion6_2(long portalVersion) {
		if ((portalVersion >= PORTAL_VERSION_6_2_10) &&
			(portalVersion < PORTAL_VERSION_6_2_20)) {

			return true;
		}

		return false;
	}

}