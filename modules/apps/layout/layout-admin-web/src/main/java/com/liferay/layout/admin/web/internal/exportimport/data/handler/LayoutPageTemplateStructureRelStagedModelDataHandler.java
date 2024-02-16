/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class LayoutPageTemplateStructureRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutPageTemplateStructureRel> {

	public static final String[] CLASS_NAMES = {
		LayoutPageTemplateStructureRel.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws Exception {

		Element layoutPageTemplateStructureRelElement =
			portletDataContext.getExportDataElement(
				layoutPageTemplateStructureRel);

		if (layoutPageTemplateStructureRel.getSegmentsExperienceId() !=
				SegmentsExperienceConstants.ID_DEFAULT) {

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					layoutPageTemplateStructureRel.getSegmentsExperienceId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateStructureRel,
				segmentsExperience, PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		Consumer<JSONObject> consumer = jsonObject -> {
			long classPK = jsonObject.getLong("classPK");

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.fetchAssetListEntry(classPK);

			if (assetListEntry != null) {
				try {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, layoutPageTemplateStructureRel,
						assetListEntry,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
				catch (PortletDataException portletDataException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portletDataException, portletDataException);
					}
				}
			}
		};

		String data = _processReferenceStagedModels(
			consumer,
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, layoutPageTemplateStructureRel,
					layoutPageTemplateStructureRel.getData(), false, false));

		layoutPageTemplateStructureRel.setData(data);

		portletDataContext.addClassedModel(
			layoutPageTemplateStructureRelElement,
			ExportImportPathUtil.getModelPath(layoutPageTemplateStructureRel),
			layoutPageTemplateStructureRel);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws Exception {

		Map<Long, Long> layoutPageTemplateStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateStructure.class);

		long layoutPageTemplateStructureId = MapUtil.getLong(
			layoutPageTemplateStructureIds,
			layoutPageTemplateStructureRel.getLayoutPageTemplateStructureId(),
			layoutPageTemplateStructureRel.getLayoutPageTemplateStructureId());

		Map<Long, Long> segmentsExperienceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SegmentsExperience.class);

		long segmentsExperienceId = MapUtil.getLong(
			segmentsExperienceIds,
			layoutPageTemplateStructureRel.getSegmentsExperienceId(),
			layoutPageTemplateStructureRel.getSegmentsExperienceId());

		LayoutPageTemplateStructureRel importedLayoutPageTemplateStructureRel =
			(LayoutPageTemplateStructureRel)
				layoutPageTemplateStructureRel.clone();

		importedLayoutPageTemplateStructureRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedLayoutPageTemplateStructureRel.setCompanyId(
			portletDataContext.getCompanyId());
		importedLayoutPageTemplateStructureRel.setLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId);
		importedLayoutPageTemplateStructureRel.setSegmentsExperienceId(
			segmentsExperienceId);

		Consumer<JSONObject> consumer = jsonObject -> {
			long classPK = jsonObject.getLong("classPK");

			Map<Long, Long> assetListEntryNewPrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					AssetListEntry.class.getName());

			long newClassPK = MapUtil.getLong(
				assetListEntryNewPrimaryKeys, classPK, -1);

			if (newClassPK == -1) {
				try {
					StagedModelDataHandlerUtil.importReferenceStagedModel(
						portletDataContext, layoutPageTemplateStructureRel,
						AssetListEntry.class, classPK);
				}
				catch (Exception exception) {
					StringBundler exceptionSB = new StringBundler(6);

					exceptionSB.append("Unable to process asset list entry ");
					exceptionSB.append(classPK);
					exceptionSB.append(" for ");
					exceptionSB.append(
						layoutPageTemplateStructureRel.getModelClassName());
					exceptionSB.append(" with primary key ");
					exceptionSB.append(
						layoutPageTemplateStructureRel.getPrimaryKeyObj());

					ExportImportContentProcessorException
						exportImportContentProcessorException =
							new ExportImportContentProcessorException(
								exceptionSB.toString(), exception);

					if (_log.isDebugEnabled()) {
						_log.debug(
							exceptionSB.toString(),
							exportImportContentProcessorException);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(exceptionSB.toString());
					}
				}
			}

			newClassPK = MapUtil.getLong(
				assetListEntryNewPrimaryKeys, classPK, classPK);

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.fetchAssetListEntry(newClassPK);

			if (assetListEntry != null) {
				jsonObject.put(
					"classNameId",
					_portal.getClassNameId(assetListEntry.getAssetEntryType())
				).put(
					"classPK", String.valueOf(newClassPK)
				).put(
					"itemSubtype", assetListEntry.getAssetEntrySubtype()
				).put(
					"itemType", assetListEntry.getAssetEntryType()
				).put(
					"title", assetListEntry.getTitle()
				);
			}
		};

		String data = _processReferenceStagedModels(
			consumer,
			_dlReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, layoutPageTemplateStructureRel,
					layoutPageTemplateStructureRel.getData()));

		importedLayoutPageTemplateStructureRel.setData(data);

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				layoutPageTemplateStructureRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingLayoutPageTemplateStructureRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedLayoutPageTemplateStructureRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedLayoutPageTemplateStructureRel);
		}
		else {
			importedLayoutPageTemplateStructureRel.setMvccVersion(
				existingLayoutPageTemplateStructureRel.getMvccVersion());
			importedLayoutPageTemplateStructureRel.
				setLayoutPageTemplateStructureRelId(
					existingLayoutPageTemplateStructureRel.
						getLayoutPageTemplateStructureRelId());

			importedLayoutPageTemplateStructureRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedLayoutPageTemplateStructureRel);
		}

		portletDataContext.importClassedModel(
			layoutPageTemplateStructureRel,
			importedLayoutPageTemplateStructureRel);
	}

	@Override
	protected StagedModelRepository<LayoutPageTemplateStructureRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	private String _processReferenceStagedModels(
			Consumer<JSONObject> consumer, String data)
		throws Exception {

		if (!JSONUtil.isValid(data)) {
			return data;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

		if (!jsonObject.has("items")) {
			return data;
		}

		JSONObject itemsJSONObject = jsonObject.getJSONObject("items");

		if (itemsJSONObject == null) {
			return data;
		}

		for (String key : itemsJSONObject.keySet()) {
			JSONObject itemJSONObject = itemsJSONObject.getJSONObject(key);

			if (!Objects.equals(
					itemJSONObject.get("type"),
					LayoutDataItemTypeConstants.TYPE_COLLECTION) ||
				!itemJSONObject.has("config")) {

				continue;
			}

			JSONObject configJSONObject = itemJSONObject.getJSONObject(
				"config");

			if (!configJSONObject.has("collection")) {
				continue;
			}

			JSONObject collectionJSONObject = configJSONObject.getJSONObject(
				"collection");

			String type = collectionJSONObject.getString("type");

			if (!Objects.equals(
					type, InfoListItemSelectorReturnType.class.getName())) {

				continue;
			}

			consumer.accept(collectionJSONObject);
		}

		return jsonObject.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureRelStagedModelDataHandler.class);

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference(target = "(content.processor.type=DLReferences)")
	private ExportImportContentProcessor<String>
		_dlReferencesExportImportContentProcessor;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateStructureRel>
		_stagedModelRepository;

}