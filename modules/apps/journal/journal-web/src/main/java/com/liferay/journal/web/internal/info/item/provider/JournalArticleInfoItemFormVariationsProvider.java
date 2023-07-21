/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormVariationsProvider.class)
public class JournalArticleInfoItemFormVariationsProvider
	implements InfoItemFormVariationsProvider<JournalArticle> {

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId) {

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>();

		try {
			long[] groupIds = _getCurrentAndAncestorSiteGroupIds(groupId);

			List<DDMStructure> ddmStructures =
				_ddmStructureLocalService.getStructures(
					groupIds,
					_portal.getClassNameId(JournalArticle.class.getName()));

			for (DDMStructure ddmStructure : ddmStructures) {
				infoItemFormVariations.add(
					new InfoItemFormVariation(
						String.valueOf(ddmStructure.getStructureId()),
						InfoLocalizedValue.<String>builder(
						).values(
							ddmStructure.getNameMap()
						).build()));
			}

			return infoItemFormVariations;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"An unexpected error occurred", portalException);
		}
	}

	private long[] _getCurrentAndAncestorSiteGroupIds(long groupId)
		throws PortalException {

		DepotEntryLocalService depotEntryLocalService = _depotEntryLocalService;

		if (depotEntryLocalService == null) {
			return _portal.getCurrentAndAncestorSiteGroupIds(groupId);
		}

		return ArrayUtil.append(
			_portal.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				depotEntryLocalService.getGroupConnectedDepotEntries(
					groupId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private Portal _portal;

}