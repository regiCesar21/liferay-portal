/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.ingestion.dxp;

import com.liferay.osb.asah.dataflow.ingestion.dxp.entity.DXPEntityMessageWrapper;
import com.liferay.osb.asah.dataflow.ingestion.dxp.entity.Order;
import com.liferay.osb.asah.dataflow.ingestion.dxp.entity.OrderItem;
import com.liferay.osb.asah.dataflow.ingestion.dxp.function.OrderParserDoFn;
import com.liferay.osb.asah.dataflow.ingestion.dxp.transform.DXPEntityMessageWrapperZipReaderPTransform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.beam.sdk.coders.AtomicCoder;
import org.apache.beam.sdk.coders.VarLongCoder;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.commons.io.FileUtils;

import org.jetbrains.annotations.NotNull;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class DXPOrderIngestionPipelineTest {

	@Test
	public void testOrderExtractor() throws Exception {
		File orderTempFile = File.createTempFile("order", ".json");

		URL url = DXPOrderIngestionPipelineTest.class.getResource(
			"dependencies");

		String json = FileUtils.readFileToString(
			new File(url.getPath() + "/order.json"));

		FileUtils.write(
			orderTempFile, json.replaceAll("[\\n\\t ]", ""),
			Charset.defaultCharset());

		Path tempDirPath = Files.createTempDirectory("order");

		tempDirPath = tempDirPath.resolve(
			"projectId/datasourceId/resourceName/uploadType/");

		File tempDirFile = tempDirPath.toFile();

		tempDirFile.mkdirs();

		File zipFile = _createZipFile(
			orderTempFile.getPath(), tempDirFile.getAbsolutePath());

		PCollection<DXPEntityMessageWrapper>
			dxpEntityMessageWrapperPCollection = testPipeline.apply(
				new DXPEntityMessageWrapperZipReaderPTransform(
					tempDirFile.getAbsolutePath() + "/*.zip"));

		Map<Long, Long> commerceChannelIdMap = new HashMap<Long, Long>() {
			{
				put(33879L, 10033879L);
			}
		};

		PCollection<Map.Entry<Long, Long>> commerceChannelIdMapPCollection =
			testPipeline.apply(
				Create.of(
					commerceChannelIdMap.entrySet()
				).withCoder(
					MapEntryCoder.of()
				));

		PCollection<KV<Long, Long>> kvPCollection =
			commerceChannelIdMapPCollection.apply(
				MapElements.into(
					TypeDescriptors.kvs(
						TypeDescriptors.longs(), TypeDescriptors.longs())
				).via(
					entry -> {
						if (entry == null) {
							return null;
						}

						return KV.of(entry.getKey(), entry.getValue());
					}
				));

		PCollectionView<Map<Long, Long>> commerceChannelIdMapPCollectionView =
			kvPCollection.apply(View.asMap());

		PCollection<Order> pCollection =
			dxpEntityMessageWrapperPCollection.apply(
				"Parse Orders",
				ParDo.of(
					new OrderParserDoFn(commerceChannelIdMapPCollectionView)
				).withSideInputs(
					commerceChannelIdMapPCollectionView
				));

		Order order = _createOrder();

		PAssert.that(
			pCollection
		).containsInAnyOrder(
			Collections.singletonList(order)
		);

		testPipeline.run();

		FileUtils.delete(zipFile);
	}

	@Rule
	public final transient TestPipeline testPipeline = TestPipeline.create();

	private Order _createOrder() {
		Order order = new Order();

		order.accountId = 34090;
		order.commerceChannelId = 33879L;
		order.createDate = "2024-11-21T16:06:26Z";
		order.currencyCode = "USD";
		order.externalReferenceCode = "cae2331b-4612-610b-b22a-a4971546117a";
		order.id = 35955;
		order.modifiedDate = "2024-11-21T17:15:56Z";
		order.orderDate = "2024-11-21T17:15:56Z";
		order.orderItems = _createOrderItems();
		order.orderStatus = 1;
		order.orderTypeId = 0L;
		order.paymentMethod = "";
		order.status = 0L;
		order.paymentStatus = 0L;
		order.total = "107.8000000000000000";
		order.userId = 20123L;
		order.channelId = 10033879L;
		order.dataSourceId = "ingestion";
		order.projectId = "dataflow";
		order.uploadDate = "2024-11-28T19:47:33.979Z";
		order.uploadType = "FULL";

		return order;
	}

	private List<OrderItem> _createOrderItems() {
		List<OrderItem> orderItems = new ArrayList<>();

		OrderItem orderItem = new OrderItem();

		orderItem.cpDefinitionId = 34188L;
		orderItem.createDate = "2024-11-21T17:15:25Z";
		orderItem.customFields = new HashMap<>();
		orderItem.externalReferenceCode =
			"cd6859b9-b238-31e1-abb6-3f4d45ae3f14";
		orderItem.finalPrice = "72.0000000000000000";
		orderItem.id = 36096L;
		orderItem.modifiedDate = "2024-11-21T17:15:56Z";
		orderItem.name = new HashMap<>();
		orderItem.options = "[]";
		orderItem.parentOrderItemId = 0L;
		orderItem.quantity = 1L;
		orderItem.sku = "MIN93017";
		orderItem.subscription = false;
		orderItem.unitOfMeasure = "";
		orderItem.unitPrice = "90.0000000000000000";
		orderItem.userId = 20123L;

		orderItems.add(orderItem);

		orderItem = new OrderItem();

		orderItem.cpDefinitionId = 34261L;
		orderItem.createDate = "2024-11-21T17:15:25Z";
		orderItem.customFields = new HashMap<>();
		orderItem.externalReferenceCode =
			"ca0be4c6-4fe4-eba4-03dc-ce6852896944";
		orderItem.finalPrice = "20.8000000000000000";
		orderItem.id = 36095L;
		orderItem.modifiedDate = "2024-11-21T17:15:56Z";
		orderItem.name = new HashMap<>();
		orderItem.options = "[]";
		orderItem.parentOrderItemId = 0L;
		orderItem.quantity = 1L;
		orderItem.sku = "MIN93019";
		orderItem.subscription = false;
		orderItem.unitOfMeasure = "";
		orderItem.unitPrice = "26.0000000000000000";
		orderItem.userId = 20123;

		orderItems.add(orderItem);

		return orderItems;
	}

	@NotNull
	private File _createZipFile(String orderFilePath, String tempDir)
		throws Exception {

		File zipFile = new File(
			tempDir +
				"/com.liferay.headless.commerce.machine.learning.dto.v1_0." +
					"Order_FULL_2024-11-28T19:47:33.979Z.zip");

		try (FileInputStream fileInputStream = new FileInputStream(
				orderFilePath);
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			ZipOutputStream zipOutputStream = new ZipOutputStream(
				fileOutputStream)) {

			ZipEntry zipEntry = new ZipEntry("order.json");

			zipOutputStream.putNextEntry(zipEntry);

			int length = 0;
			byte[] buffer = new byte[1024];

			while ((length = fileInputStream.read(buffer)) >= 0) {
				zipOutputStream.write(buffer, 0, length);
			}

			zipOutputStream.closeEntry();
		}

		return zipFile;
	}

	private static class MapEntryCoder
		extends AtomicCoder<Map.Entry<Long, Long>> {

		public static MapEntryCoder of() {
			return new MapEntryCoder();
		}

		@Override
		public Map.Entry<Long, Long> decode(InputStream inputStream)
			throws IOException {

			Long key = VarLongCoder.of(
			).decode(
				inputStream
			);
			Long value = VarLongCoder.of(
			).decode(
				inputStream
			);

			return new HashMap.SimpleEntry<>(key, value);
		}

		@Override
		public void encode(
				Map.Entry<Long, Long> value, OutputStream outputStream)
			throws IOException {

			VarLongCoder.of(
			).encode(
				value.getKey(), outputStream
			);

			VarLongCoder.of(
			).encode(
				value.getValue(), outputStream
			);
		}

	}

}