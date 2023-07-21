/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.script.Scripts;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutor getDocumentRequestExecutor() {
		return _documentRequestExecutor;
	}

	public void setUp() {
		_documentRequestExecutor = createDocumentRequestExecutor(
			_elasticsearchClientResolver, _elasticsearchDocumentFactory);
	}

	protected static ElasticsearchBulkableDocumentRequestTranslator
		createBulkableDocumentRequestTranslator(
			ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		return new ElasticsearchBulkableDocumentRequestTranslatorImpl() {
			{
				setElasticsearchDocumentFactory(elasticsearchDocumentFactory);
			}
		};
	}

	protected static BulkDocumentRequestExecutor
		createBulkDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new BulkDocumentRequestExecutorImpl() {
			{
				setElasticsearchBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new DeleteByQueryDocumentRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);

				com.liferay.portal.search.elasticsearch7.internal.legacy.query.
					ElasticsearchQueryTranslatorFixture
						legacyElasticsearchQueryTranslatorFixture =
							new com.liferay.portal.search.elasticsearch7.
								internal.legacy.query.ElasticsearchQueryTranslatorFixture();

				setLegacyQueryTranslator(
					legacyElasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				setQueryTranslator(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());
			}
		};
	}

	protected static DeleteDocumentRequestExecutor
		createDeleteDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new DeleteDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static DocumentRequestExecutor createDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		ElasticsearchBulkableDocumentRequestTranslator
			bulkableDocumentRequestTranslator =
				createBulkableDocumentRequestTranslator(
					elasticsearchDocumentFactory);

		return new ElasticsearchDocumentRequestExecutor() {
			{
				setBulkDocumentRequestExecutor(
					createBulkDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setDeleteByQueryDocumentRequestExecutor(
					createDeleteByQueryDocumentRequestExecutor(
						elasticsearchClientResolver));
				setDeleteDocumentRequestExecutor(
					createDeleteDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setGetDocumentRequestExecutor(
					createGetDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setIndexDocumentRequestExecutor(
					createIndexDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setUpdateByQueryDocumentRequestExecutor(
					createUpdateByQueryDocumentRequestExecutor(
						elasticsearchClientResolver));
				setUpdateDocumentRequestExecutor(
					createUpdateDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
			}
		};
	}

	protected static GetDocumentRequestExecutor
		createGetDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new GetDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setDocumentBuilderFactory(new DocumentBuilderFactoryImpl());
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setGeoBuilders(new GeoBuildersImpl());
			}
		};
	}

	protected static IndexDocumentRequestExecutor
		createIndexDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new IndexDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static UpdateByQueryDocumentRequestExecutor
		createUpdateByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new UpdateByQueryDocumentRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);

				com.liferay.portal.search.elasticsearch7.internal.legacy.query.
					ElasticsearchQueryTranslatorFixture
						lecacyElasticsearchQueryTranslatorFixture =
							new com.liferay.portal.search.elasticsearch7.
								internal.legacy.query.ElasticsearchQueryTranslatorFixture();

				setLegacyQueryTranslator(
					lecacyElasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				setQueryTranslator(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				setScripts(_scripts);
			}
		};
	}

	protected static UpdateDocumentRequestExecutor
		createUpdateDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new UpdateDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	protected void setElasticsearchDocumentFactory(
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		_elasticsearchDocumentFactory = elasticsearchDocumentFactory;
	}

	private static final Scripts _scripts = new ScriptsImpl();

	private DocumentRequestExecutor _documentRequestExecutor;
	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}