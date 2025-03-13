/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.backend.dog.ReportIndividualDog;
import com.liferay.osb.asah.backend.dto.ActivityDTO;
import com.liferay.osb.asah.backend.dto.ReportIndividualDTO;
import com.liferay.osb.asah.backend.model.ResultBagEntityModel;
import com.liferay.osb.asah.backend.rest.controller.BaseRestController;
import com.liferay.osb.asah.common.dog.BQEventDog;
import com.liferay.osb.asah.common.dog.BQIdentityDog;
import com.liferay.osb.asah.common.dog.BQIdentityInterestScoreDog;
import com.liferay.osb.asah.common.dog.IndividualActivityDog;
import com.liferay.osb.asah.common.dog.IndividualInterestDog;
import com.liferay.osb.asah.common.dog.ProjectFeatureDog;
import com.liferay.osb.asah.common.dog.SegmentDog;
import com.liferay.osb.asah.common.entity.BQEvent;
import com.liferay.osb.asah.common.entity.IndividualActivity;
import com.liferay.osb.asah.common.entity.IndividualInterest;
import com.liferay.osb.asah.common.entity.Segment;
import com.liferay.osb.asah.common.model.Feature;
import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.model.ResultBag;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.util.ListUtil;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcellus Tavares
 */
@RequestMapping(
	produces = "application/json", value = "/api/reports/individuals"
)
@RestController
public class ReportIndividualRestController extends BaseRestController {

	@GetMapping("/{individualId}/activities")
	public ResultBagEntityModel<ActivityDTO>
		getIndividualActivityResultBagEntityModel(
			@RequestParam(required = false) Long channelId,
			@PathVariable String individualId,
			@RequestParam(defaultValue = "0") Integer page) {

		Page<ActivityDTO> activityDTOPage = null;

		if (_projectFeatureDog.isFeatureEnabled(
				Feature.API_REPORTS_POSTGRES_CACHE,
				ProjectIdThreadLocal.getProjectId())) {

			Page<IndividualActivity> individualActivityPage =
				_individualActivityDog.getIndividualActivityPage(
					channelId, individualId, page, _PAGE_SIZE,
					TimeRange.LAST_30_DAYS);

			activityDTOPage = individualActivityPage.map(ActivityDTO::new);
		}
		else {
			Page<BQEvent> bqEventPage = _bqEventDog.getBQEventPage(
				channelId, null, page, _PAGE_SIZE, TimeRange.LAST_30_DAYS,
				_bqIdentityDog.getBQIdentityIds(individualId));

			activityDTOPage = bqEventPage.map(
				bqEvent -> {
					try {
						return new ActivityDTO(
							bqEvent, individualId,
							_objectMapper.readValue(
								bqEvent.getContext(),
								new TypeReference<Map<String, String>>() {
								}),
							_toMap(bqEvent.getProperties()));
					}
					catch (JsonProcessingException jsonProcessingException) {
						throw new RuntimeException(jsonProcessingException);
					}
				});
		}

		ResultBag<ActivityDTO> activityResultBag = new ResultBag<>(
			activityDTOPage.getContent(), activityDTOPage.getTotalElements());

		return _toResultBagEntityModel(
			_getLink(
				channelId, _REL_NEXT,
				_getIndividualActivityResultBagEntityModel(
					channelId, individualId, page + 1)),
			page,
			_getLink(
				channelId, _REL_PREV,
				_getIndividualActivityResultBagEntityModel(
					channelId, individualId, page - 1)),
			activityResultBag,
			activityDTO -> _toChildEntityModel(individualId, activityDTO));
	}

	@GetMapping("/{individualId}/interests")
	public ResultBagEntityModel<IndividualInterest>
		getIndividualInterestResultBagEntityModel(
			@RequestParam(required = false) Long channelId,
			@PathVariable String individualId,
			@RequestParam(defaultValue = "0") Integer page) {

		Page<? extends IndividualInterest> individualInterestPage = null;

		if (_projectFeatureDog.isFeatureEnabled(
				Feature.API_REPORTS_POSTGRES_CACHE,
				ProjectIdThreadLocal.getProjectId())) {

			individualInterestPage =
				_individualInterestDog.getIndividualInterestPage(
					channelId, individualId, _PAGE_SIZE, page * _PAGE_SIZE);
		}
		else {
			individualInterestPage =
				_bqIdentityInterestScoreDog.getBQIdentityInterestScorePage(
					channelId, individualId, _PAGE_SIZE, page * _PAGE_SIZE);
		}

		return _toResultBagEntityModel(
			_getLink(
				channelId, _REL_NEXT,
				_getIndividualInterestResultBagEntityModel(
					channelId, individualId, page + 1)),
			page,
			_getLink(
				channelId, _REL_PREV,
				_getIndividualInterestResultBagEntityModel(
					channelId, individualId, page - 1)),
			new ResultBag<>(
				individualInterestPage.getContent(),
				individualInterestPage.getTotalElements()),
			interest -> _toChildEntityModel(individualId, interest));
	}

	@GetMapping("/{individualId}/segments")
	public ResultBagEntityModel<Segment>
		getIndividualSegmentResultBagEntityModel(
			@PathVariable String individualId) {

		List<Segment> segments = _segmentDog.getIndividualSegments(
			individualId);

		return _toResultBagEntityModel(
			null, 0, null, new ResultBag<>(segments, segments.size()),
			segment -> _toChildEntityModel(individualId, segment));
	}

	@GetMapping("/{individualId}")
	public EntityModel<ReportIndividualDTO> getReportIndividualDTOEntityModel(
		@PathVariable String individualId) {

		ReportIndividual reportIndividual =
			_reportIndividualDog.fetchReportIndividual(individualId);

		return _toReportIndividualDTOEntityModel(
			new ReportIndividualDTO(reportIndividual));
	}

	@GetMapping
	public ResultBagEntityModel<ReportIndividualDTO>
		getReportIndividualDTOResultBagEntityModel(
			@RequestParam(required = false) Long channelId,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "") String query) {

		Page<ReportIndividual> reportIndividualPage =
			_reportIndividualDog.searchReportIndividualPage(
				channelId, page, query, null, _PAGE_SIZE);

		ResultBag<ReportIndividualDTO> reportIndividualDTOResultBag =
			new ResultBag<>(
				ListUtil.map(
					reportIndividualPage.getContent(),
					ReportIndividualDTO::new),
				reportIndividualPage.getTotalElements());

		return _toResultBagEntityModel(
			_getLink(
				channelId, _REL_NEXT,
				_getReportIndividualDTOResultBagEntityModel(
					channelId, page + 1, query)),
			page,
			_getLink(
				channelId, _REL_PREV,
				_getReportIndividualDTOResultBagEntityModel(
					channelId, page - 1, query)),
			reportIndividualDTOResultBag,
			this::_toReportIndividualDTOEntityModel);
	}

	private ResultBagEntityModel<ActivityDTO>
		_getIndividualActivityResultBagEntityModel(
			Long channelId, String individualId, Integer page) {

		return WebMvcLinkBuilder.methodOn(
			ReportIndividualRestController.class
		).getIndividualActivityResultBagEntityModel(
			channelId, individualId, page
		);
	}

	private ResultBagEntityModel<IndividualInterest>
		_getIndividualInterestResultBagEntityModel(
			Long channelId, String individualId, Integer page) {

		return WebMvcLinkBuilder.methodOn(
			ReportIndividualRestController.class
		).getIndividualInterestResultBagEntityModel(
			channelId, individualId, page
		);
	}

	private Link _getLink(Long channelId, String rel, Object methodInvocation) {
		return WebMvcLinkBuilder.linkTo(
			methodInvocation
		).withRel(
			rel
		).expand(
			Collections.singletonMap("channelId", channelId)
		);
	}

	private ResultBagEntityModel<ReportIndividualDTO>
		_getReportIndividualDTOResultBagEntityModel(
			Long channelId, Integer page, String query) {

		return WebMvcLinkBuilder.methodOn(
			ReportIndividualRestController.class
		).getReportIndividualDTOResultBagEntityModel(
			channelId, page, query
		);
	}

	private <T> EntityModel<T> _toChildEntityModel(String parentId, T t) {
		return EntityModel.of(
			t,
			WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
					ReportIndividualRestController.class
				).getReportIndividualDTOEntityModel(
					parentId
				)
			).withRel(
				"parent"
			));
	}

	private <T, R> List<EntityModel<R>> _toListEntityModel(
		List<T> results,
		Function<T, EntityModel<R>> resultEntityModelMapperFunction) {

		return ListUtil.map(results, resultEntityModelMapperFunction);
	}

	private Map<String, String> _toMap(List<BQEvent.Property> properties) {
		Map<String, String> map = new HashMap<>();

		for (BQEvent.Property property : properties) {
			map.put(property.getName(), property.getValue());
		}

		return map;
	}

	private EntityModel<ReportIndividualDTO> _toReportIndividualDTOEntityModel(
		ReportIndividualDTO reportIndividualDTO) {

		return EntityModel.of(
			reportIndividualDTO,
			WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
					ReportIndividualRestController.class
				).getReportIndividualDTOEntityModel(
					reportIndividualDTO.getId()
				)
			).withSelfRel(),
			WebMvcLinkBuilder.linkTo(
				_getIndividualActivityResultBagEntityModel(
					null, reportIndividualDTO.getId(), null)
			).withRel(
				"activities"
			),
			WebMvcLinkBuilder.linkTo(
				_getIndividualInterestResultBagEntityModel(
					null, reportIndividualDTO.getId(), null)
			).withRel(
				"interests"
			),
			WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
					ReportIndividualRestController.class
				).getIndividualSegmentResultBagEntityModel(
					reportIndividualDTO.getId()
				)
			).withRel(
				"segments"
			));
	}

	private <T, R> ResultBagEntityModel<R> _toResultBagEntityModel(
		Link nextPageLink, int page, Link prevPageLink, List<T> results,
		long total,
		Function<T, EntityModel<R>> resultEntityModelMapperFunction) {

		ResultBagEntityModel<R> resultBagEntityModel =
			new ResultBagEntityModel<>(
				new ResultBag<>(
					_toListEntityModel(
						results, resultEntityModelMapperFunction),
					total));

		if (((page + 1L) * _PAGE_SIZE) < total) {
			resultBagEntityModel.add(nextPageLink);
		}

		if (page > 0) {
			resultBagEntityModel.add(prevPageLink);
		}

		return resultBagEntityModel;
	}

	private <T, R> ResultBagEntityModel<R> _toResultBagEntityModel(
		Link nextPageLink, int page, Link prevPageLink, ResultBag<T> resultBag,
		Function<T, EntityModel<R>> resultEntityModelMapperFunction) {

		return _toResultBagEntityModel(
			nextPageLink, page, prevPageLink, resultBag.getResults(),
			resultBag.getTotal(), resultEntityModelMapperFunction);
	}

	private static final int _PAGE_SIZE = 20;

	private static final String _REL_NEXT = "next";

	private static final String _REL_PREV = "prev";

	@Autowired
	private BQEventDog _bqEventDog;

	@Autowired
	private BQIdentityDog _bqIdentityDog;

	@Autowired
	private BQIdentityInterestScoreDog _bqIdentityInterestScoreDog;

	@Autowired
	private IndividualActivityDog _individualActivityDog;

	@Autowired
	private IndividualInterestDog _individualInterestDog;

	@Autowired
	private ObjectMapper _objectMapper;

	@Autowired
	private ProjectFeatureDog _projectFeatureDog;

	@Autowired
	private ReportIndividualDog _reportIndividualDog;

	@Autowired
	private SegmentDog _segmentDog;

}