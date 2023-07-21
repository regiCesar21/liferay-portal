/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.info.internal.item.AssetEntryInfoItemFields;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFormProvider.class)
public class AssetEntryInfoItemFormProvider
	implements InfoItemFormProvider<AssetEntry> {

	@Override
	public InfoForm getInfoForm() {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		InfoLocalizedValue.Builder infoLocalizedValueBuilder =
			InfoLocalizedValue.builder();

		for (Locale locale : availableLocales) {
			infoLocalizedValueBuilder.value(
				locale,
				ResourceActionsUtil.getModelResource(
					locale, AssetEntry.class.getName()));
		}

		return InfoForm.builder(
		).infoFieldSetEntries(
			_getAssetEntryFieldSetEntries()
		).infoFieldSetEntry(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName())
		).labelInfoLocalizedValue(
			infoLocalizedValueBuilder.build()
		).name(
			AssetEntry.class.getName()
		).build();
	}

	private List<InfoFieldSetEntry> _getAssetEntryFieldSetEntries() {
		return Arrays.asList(
			AssetEntryInfoItemFields.titleInfoField,
			AssetEntryInfoItemFields.descriptionInfoField,
			AssetEntryInfoItemFields.summaryInfoField,
			AssetEntryInfoItemFields.userNameInfoField,
			AssetEntryInfoItemFields.createDateInfoField,
			AssetEntryInfoItemFields.modifiedDateInfoField,
			AssetEntryInfoItemFields.expirationDateInfoField,
			AssetEntryInfoItemFields.viewCountInfoField,
			AssetEntryInfoItemFields.urlInfoField,
			AssetEntryInfoItemFields.userProfileImage);
	}

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

}