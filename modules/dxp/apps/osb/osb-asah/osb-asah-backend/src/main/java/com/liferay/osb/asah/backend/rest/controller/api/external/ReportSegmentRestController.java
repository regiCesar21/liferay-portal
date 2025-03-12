/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.external;

import com.liferay.osb.asah.backend.dog.ReportIndividualDog;
import com.liferay.osb.asah.backend.dto.ActivityDTO;
import com.liferay.osb.asah.backend.dto.ReportIndividualDTO;
import com.liferay.osb.asah.backend.dto.ReportSegmentDTO;
import com.liferay.osb.asah.backend.model.ResultBagEntityModel;
import com.liferay.osb.asah.backend.rest.controller.BaseRestController;
import com.liferay.osb.asah.common.dog.SegmentDog;
import com.liferay.osb.asah.common.entity.IndividualInterest;
import com.liferay.osb.asah.common.entity.Segment;
import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.model.ResultBag;
import com.liferay.osb.asah.common.util.ListUtil;

import java.util.Collections;
import java.util.List;
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
@RequestMapping(produces = "application/json", value = "/api/reports/segments")
@RestController
public class ReportSegmentRestController extends BaseRestController {

	@GetMapping("/{segmentId}")
	public EntityModel<ReportSegmentDTO> getReportSegmentDTOEntityModel(
		@PathVariable Long segmentId) {

		return _toReportSegmentDTOEntityModel(
			_segmentDog.getSegment(segmentId));
	}

	@GetMapping
	public ResultBagEntityModel<ReportSegmentDTO>
		getReportSegmentDTOResultBagEntityModel(
			@RequestParam(required = false) Long channelId,
			@RequestParam(defaultValue = "0") Integer page) {

		Page<Segment> segmentPage = _segmentDog.getSegmentPage(
			channelId, page, _PAGE_SIZE);

		List<Segment> segments = segmentPage.getContent();

		return _toResultBagEntityModel(
			_getLink(
				channelId, _REL_NEXT,
				_getSegmentResultBagEntityModel(channelId, page + 1)),
			page,
			_getLink(
				channelId, _REL_PREV,
				_getSegmentResultBagEntityModel(channelId, page - 1)),
			segments, segmentPage.getTotalElements(),
			segment -> _toReportSegmentDTOEntityModel(segment));
	}

	@GetMapping("/{segmentId}/individuals")
	public ResultBagEntityModel<ReportIndividualDTO>
		getSegmentReportIndividualDTOResultBagEntityModel(
			@RequestParam(required = false) Long channelId,
			@PathVariable Long segmentId,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "") String query) {

		Page<ReportIndividual> reportIndividualPage =
			_reportIndividualDog.searchReportIndividualPage(
				channelId, page, query, segmentId, _PAGE_SIZE);

		ResultBag<ReportIndividualDTO> reportIndividualDTOResultBag =
			new ResultBag<>(
				ListUtil.map(
					reportIndividualPage.getContent(),
					ReportIndividualDTO::new),
				reportIndividualPage.getTotalElements());

		return _toResultBagEntityModel(
			_getLink(
				channelId, _REL_NEXT,
				_getSegmentReportIndividualDTOResultBagEntityModel(
					channelId, segmentId, page + 1, query)),
			page,
			_getLink(
				channelId, _REL_PREV,
				_getSegmentReportIndividualDTOResultBagEntityModel(
					channelId, segmentId, page - 1, query)),
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
		_getSegmentReportIndividualDTOResultBagEntityModel(
			Long channelId, Long segmentId, Integer page, String query) {

		return WebMvcLinkBuilder.methodOn(
			ReportSegmentRestController.class
		).getSegmentReportIndividualDTOResultBagEntityModel(
			channelId, segmentId, page, query
		);
	}

	private ResultBagEntityModel<ReportSegmentDTO>
		_getSegmentResultBagEntityModel(Long channelId, Integer page) {

		return WebMvcLinkBuilder.methodOn(
			ReportSegmentRestController.class
		).getReportSegmentDTOResultBagEntityModel(
			channelId, page
		);
	}

	private <T, R> List<EntityModel<R>> _toListEntityModel(
		List<T> results,
		Function<T, EntityModel<R>> resultEntityModelMapperFunction) {

		return ListUtil.map(results, resultEntityModelMapperFunction);
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

	private EntityModel<ReportSegmentDTO> _toReportSegmentDTOEntityModel(
		Segment segment) {

		return EntityModel.of(
			new ReportSegmentDTO(segment),
			WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
					ReportSegmentRestController.class
				).getReportSegmentDTOEntityModel(
					segment.getId()
				)
			).withSelfRel(),
			WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
					ReportSegmentRestController.class
				).getSegmentReportIndividualDTOResultBagEntityModel(
					null, segment.getId(), 0, null
				)
			).withRel(
				"individuals"
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
	private ReportIndividualDog _reportIndividualDog;

	@Autowired
	private SegmentDog _segmentDog;

}