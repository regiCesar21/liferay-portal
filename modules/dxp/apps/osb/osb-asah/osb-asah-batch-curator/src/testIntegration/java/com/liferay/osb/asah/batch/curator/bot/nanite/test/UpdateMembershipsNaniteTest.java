/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.batch.curator.bot.nanite.test;

import com.liferay.osb.asah.batch.curator.OSBAsahBatchCuratorSpringTestContext;
import com.liferay.osb.asah.batch.curator.bot.nanite.UpdateMembershipsNanite;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.entity.Channel;
import com.liferay.osb.asah.common.entity.Segment;
import com.liferay.osb.asah.common.model.Transformation;
import com.liferay.osb.asah.common.repository.BQMembershipChangeRepository;
import com.liferay.osb.asah.common.repository.ChannelRepository;
import com.liferay.osb.asah.common.repository.SegmentRepository;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.configuration.JDBCTestConfiguration;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;
import com.liferay.osb.asah.test.util.util.RandomTestUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

/**
 * @author Rachael Koestartyo
 */
@Import(JDBCTestConfiguration.class)
public class UpdateMembershipsNaniteTest
	implements OSBAsahBatchCuratorSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@AfterEach
	public void tearDown() {
		_segmentRepository.deleteAll();

		_channelRepository.deleteAll();
	}

	@BQSQLResource
	@Test
	public void testRun1() {
		ProjectIdThreadLocal.setProjectId("test");

		Segment segment = new Segment();

		segment.setAuthorName("Test Test");

		Channel channel1 = _addChannel(
			RandomTestUtil.randomNumber(), "Test Channel");

		segment.setChannelId(channel1.getId());

		segment.setCreateDate(DateUtil.addDays(new Date(), -5));
		segment.setFilter(
			String.format("(channelId eq '%s')", channel1.getId()));
		segment.setIsNew(Boolean.TRUE);
		segment.setName("Segment 1");
		segment.setState("IN_PROGRESS");
		segment.setStatus("ACTIVE");
		segment.setType(Segment.Type.DYNAMIC);

		_segmentRepository.save(segment);

		_updateMembershipsNanite.run(null);

		Optional<Segment> segmentOptional =
			_segmentRepository.findByNameAndStatus("Segment 1", "ACTIVE");

		segment = segmentOptional.get();

		Assertions.assertEquals("READY", segment.getState());
	}

	@BQSQLResource(resourcePath = "segment_membership_change.sql")
	@Test
	public void testRun2() {
		ProjectIdThreadLocal.setProjectId("test");

		Segment segment = new Segment();

		segment.setAuthorName("Test Test");

		Channel channel1 = _addChannel(1L, "Test Channel");

		segment.setChannelId(channel1.getId());

		segment.setCreateDate(DateUtil.addDays(new Date(), -5));
		segment.setId(1L);
		segment.setIncludeAnonymousUsers(true);
		segment.setIsNew(Boolean.TRUE);
		segment.setName("Segment 1");
		segment.setState("IN_PROGRESS");
		segment.setStatus("ACTIVE");
		segment.setType(Segment.Type.DYNAMIC);

		_segmentRepository.save(segment);

		Optional<Segment> segmentOptional =
			_segmentRepository.findByNameAndStatus("Segment 1", "ACTIVE");

		segment = segmentOptional.get();

		List<Transformation> membershipChangeTransformations =
			_bqMembershipChangeRepository.getMembershipChangeTransformations(
				true, 1L, PageRequest.of(0, 20));

		Assertions.assertEquals(
			1, membershipChangeTransformations.size(),
			membershipChangeTransformations.toString());

		Transformation transformation = membershipChangeTransformations.get(0);

		_assertTransformationTermValue(
			5, "addedIndividualsCount", transformation);
		_assertTransformationTermValue(
			2, "anonymousIndividualsCount", transformation);
		_assertTransformationTermValue(5, "individualsCount", transformation);
		_assertTransformationTermValue(
			3, "knownIndividualsCount", transformation);
		_assertTransformationTermValue(
			0, "removedIndividualsCount", transformation);

		segment.setFilter(
			String.format(
				"(activities.filterByCount(filter='(activityKey eq ''" +
					"WebContent#webContentViewed#%s'')',operator='le'," +
						"value=1))",
				DigestUtils.sha256Hex("1") + "_" +
					DigestUtils.sha256Hex("Web Content 1")));

		_segmentRepository.save(segment);

		_updateMembershipsNanite.run(null);

		membershipChangeTransformations =
			_bqMembershipChangeRepository.getMembershipChangeTransformations(
				true, 1L, PageRequest.of(0, 20));

		Assertions.assertEquals(
			2, membershipChangeTransformations.size(),
			membershipChangeTransformations.toString());

		transformation = membershipChangeTransformations.get(1);

		_assertTransformationTermValue(
			0, "addedIndividualsCount", transformation);
		_assertTransformationTermValue(
			1, "anonymousIndividualsCount", transformation);
		_assertTransformationTermValue(3, "individualsCount", transformation);
		_assertTransformationTermValue(
			2, "knownIndividualsCount", transformation);
		_assertTransformationTermValue(
			2, "removedIndividualsCount", transformation);
	}

	private Channel _addChannel(long id, String name) {
		Channel channel = new Channel(name);

		channel.setId(id);
		channel.setIsNew(Boolean.TRUE);

		return _channelRepository.save(channel);
	}

	private void _assertTransformationTermValue(
		long expectedValue, String key, Transformation transformation) {

		Transformation.Term term = transformation.getTerm();

		Map<String, Object> termsMap = term.getTermsMap();

		Assertions.assertEquals(expectedValue, termsMap.get(key));
	}

	@Autowired
	private BQMembershipChangeRepository _bqMembershipChangeRepository;

	@Autowired
	private ChannelRepository _channelRepository;

	@Autowired
	private SegmentRepository _segmentRepository;

	@Autowired
	private UpdateMembershipsNanite _updateMembershipsNanite;

}