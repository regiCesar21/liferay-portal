/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ResourcedModel;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;
import com.liferay.portal.search.model.uid.UIDFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BaseModelDocumentFactory.class)
public class BaseModelDocumentFactoryImpl implements BaseModelDocumentFactory {

	@Override
	public com.liferay.portal.kernel.search.Document createDocument(
		BaseModel<?> baseModel) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		Tuple classPKResourcePrimKeyTuple = getClassPKResourcePrimKey(
			baseModel);

		documentBuilder.setString(
			Field.ENTRY_CLASS_NAME, baseModel.getModelClassName()
		).setLong(
			Field.ENTRY_CLASS_PK, (Long)classPKResourcePrimKeyTuple.getObject(0)
		).setLong(
			Field.ROOT_ENTRY_CLASS_PK,
			getRootEntryClassPK(classPKResourcePrimKeyTuple)
		);

		uidFactory.setUID(baseModel, documentBuilder);

		Document document = documentBuilder.build();

		_enforceStandardUID(document);

		return toLegacyDocument(document);
	}

	protected Tuple getClassPKResourcePrimKey(BaseModel<?> baseModel) {
		long classPK = 0;
		long resourcePrimKey = 0;

		if (baseModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)baseModel;

			classPK = resourcedModel.getResourcePrimKey();
			resourcePrimKey = resourcedModel.getResourcePrimKey();
		}
		else {
			classPK = (Long)baseModel.getPrimaryKeyObj();
		}

		return new Tuple(classPK, resourcePrimKey);
	}

	protected Long getRootEntryClassPK(Tuple classPKResourcePrimKeyTuple) {
		long resourcePrimKey = (Long)classPKResourcePrimKeyTuple.getObject(1);

		if (resourcePrimKey > 0) {
			return resourcePrimKey;
		}

		return null;
	}

	protected com.liferay.portal.kernel.search.Document toLegacyDocument(
		Document document) {

		DocumentImpl documentImpl = new DocumentImpl();

		Map<String, com.liferay.portal.search.document.Field> fields =
			document.getFields();

		fields.forEach(
			(key, field) -> documentImpl.add(
				new Field(key, String.valueOf(field.getValue()))));

		return documentImpl;
	}

	@Reference
	protected DocumentBuilderFactory documentBuilderFactory;

	@Reference
	protected UIDFactory uidFactory;

	private void _enforceStandardUID(Document document) {
		uidFactory.getUID(document);
	}

}