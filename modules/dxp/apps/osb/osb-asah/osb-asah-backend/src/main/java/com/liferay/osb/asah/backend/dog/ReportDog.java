/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog;

import com.liferay.osb.asah.backend.model.AssetMetric;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.BlogMetric;
import com.liferay.osb.asah.backend.model.BlogMetricType;
import com.liferay.osb.asah.backend.model.DocumentLibraryMetric;
import com.liferay.osb.asah.backend.model.DocumentLibraryMetricType;
import com.liferay.osb.asah.backend.model.FormMetric;
import com.liferay.osb.asah.backend.model.FormMetricType;
import com.liferay.osb.asah.backend.model.JournalMetric;
import com.liferay.osb.asah.backend.model.JournalMetricType;
import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.backend.model.PageMetric;
import com.liferay.osb.asah.backend.repository.AssetMetricRepository;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.date.dog.TimeZoneDog;
import com.liferay.osb.asah.common.dog.ChannelDog;
import com.liferay.osb.asah.common.dog.PreferenceDog;
import com.liferay.osb.asah.common.entity.BQEvent;
import com.liferay.osb.asah.common.entity.Channel;
import com.liferay.osb.asah.common.entity.Preference;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.model.Field;
import com.liferay.osb.asah.common.model.Individual;
import com.liferay.osb.asah.common.model.MetricType;
import com.liferay.osb.asah.common.model.PageMetricType;
import com.liferay.osb.asah.common.model.Sort;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.repository.BQEventRepository;
import com.liferay.osb.asah.common.repository.BQIndividualRepository;
import com.liferay.osb.asah.common.repository.BQMembershipRepository;
import com.liferay.osb.asah.common.util.SetUtil;
import com.liferay.osb.asah.common.util.StringUtil;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import org.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author Marcos Martins
 */
@Component
public class ReportDog {

	@Autowired
	public ReportDog(
		List<AssetMetricRepository> assetMetricRepositories,
		BQEventRepository bqEventRepository,
		BQIndividualRepository bqIndividualRepository,
		BQMembershipRepository bqMembershipRepository, ChannelDog channelDog,
		PreferenceDog preferenceDog, TimeZoneDog timeZoneDog) {

		assetMetricRepositories.forEach(
			assetMetricAssetMetricRepository -> _assetMetricRepositoryMap.put(
				assetMetricAssetMetricRepository.getAssetType(),
				assetMetricAssetMetricRepository));

		_bqEventRepository = bqEventRepository;
		_bqIndividualRepository = bqIndividualRepository;
		_bqMembershipRepository = bqMembershipRepository;
		_channelDog = channelDog;
		_preferenceDog = preferenceDog;
		_timeZoneDog = timeZoneDog;
	}

	public File getCSVReport(
			@Nullable String assetId, @Nullable String assetType,
			Long channelId, String individualId, @Nullable String query,
			@Nullable Long segmentId, @Nullable String[] sorts,
			@Nullable TimeRange timeRange, String type)
		throws Exception {

		List<String[]> rows = null;

		if (StringUtils.equals(type, "blog")) {
			rows = _getAssetBlogRows(
				channelId, query,
				SetUtil.of(
					BlogMetricType.COMMENTS.getName(),
					BlogMetricType.RATINGS.getName(),
					BlogMetricType.READING_TIME.getName(),
					BlogMetricType.VIEWS.getName()),
				sorts, timeRange, type);
		}
		else if (StringUtils.equals(type, "document")) {
			rows = _getAssetDocumentsLibraryRows(
				channelId, query,
				SetUtil.of(
					DocumentLibraryMetricType.COMMENTS.getName(),
					DocumentLibraryMetricType.DOWNLOADS.getName(),
					DocumentLibraryMetricType.IMPRESSIONS.getName(),
					DocumentLibraryMetricType.RATINGS.getName()),
				sorts, timeRange, type);
		}
		else if (StringUtils.equals(type, "event")) {
			rows = _getEventRows(
				channelId, individualId, query, sorts, timeRange);
		}
		else if (StringUtils.equals(type, "form")) {
			rows = _getAssetFormRows(
				channelId, query,
				SetUtil.of(
					FormMetricType.ABANDONMENTS.getName(),
					FormMetricType.COMPLETION_TIME.getName(),
					FormMetricType.SUBMISSIONS.getName(),
					FormMetricType.VIEWS.getName()),
				sorts, timeRange, type);
		}
		else if (StringUtils.equals(type, "individual") &&
				 StringUtils.isEmpty(assetId) &&
				 StringUtils.isEmpty(assetType)) {

			rows = _getIndividualRows(channelId, query, sorts);
		}
		else if (StringUtils.equals(type, "individual") &&
				 !StringUtils.isEmpty(assetType)) {

			rows = _getAssetIndividualRows(
				assetId, assetType, channelId, query, sorts, timeRange);
		}
		else if (StringUtils.equals(type, "journal")) {
			rows = _getAssetJournalRows(
				channelId, query, SetUtil.of(JournalMetricType.VIEWS.getName()),
				sorts, timeRange, type);
		}
		else if (StringUtils.equals(type, "membership")) {
			rows = _getMembershipRows(channelId, segmentId, sorts);
		}
		else if (StringUtils.equals(type, "page")) {
			rows = _getAssetPageRows(
				channelId, query,
				SetUtil.of(
					PageMetricType.BOUNCE_RATE.getName(),
					PageMetricType.ENTRANCES.getName(),
					PageMetricType.EXIT_RATE.getName(),
					PageMetricType.TIME_ON_PAGE.getName(),
					PageMetricType.VIEWS.getName(),
					PageMetricType.VISITORS.getName()),
				sorts, timeRange, type);
		}
		else if (StringUtils.equals(type, "search-terms")) {
			rows = _getSearchTermsRows(
				channelId, timeRange, _timeZoneDog.getTimeZoneId());
		}

		File file = File.createTempFile("report", ".csv");

		if (rows != null) {
			try (CSVWriter csvWriter = new CSVWriter(
					new BufferedWriter(
						new OutputStreamWriter(
							new FileOutputStream(file, true),
							StandardCharsets.UTF_8)))) {

				for (String[] row : rows) {
					csvWriter.writeNext(row);
				}
			}
		}

		return file;
	}

	public Long getCSVReportCount(
		@Nullable String assetId, @Nullable String assetType, Long channelId,
		String individualId, @Nullable String query,
		@Nullable TimeRange timeRange, String type) {

		if (StringUtils.equals(type, "blog") ||
			StringUtils.equals(type, "document") ||
			StringUtils.equals(type, "form") ||
			StringUtils.equals(type, "journal") ||
			StringUtils.equals(type, "page")) {

			return _getAssetMetricsCount(channelId, query, timeRange, type);
		}
		else if (StringUtils.equals(type, "event")) {
			LocalDateTime rangeEndLocalDateTime = null;
			LocalDateTime rangeStartLocalDateTime = null;

			if (timeRange != null) {
				rangeEndLocalDateTime = timeRange.getEndLocalDateTime();
				rangeStartLocalDateTime = timeRange.getStartLocalDateTime();
			}

			Integer count = _bqEventRepository.countBQEvents(
				channelId, individualId, query, rangeEndLocalDateTime,
				rangeStartLocalDateTime, _timeZoneDog.getTimeZoneId());

			return count.longValue();
		}
		else if (StringUtils.equals(type, "individual") &&
				 StringUtils.isEmpty(assetId) &&
				 StringUtils.isEmpty(assetType)) {

			return _bqIndividualRepository.countBQIndividuals(
				null, channelId, null, null, null, query, null);
		}
		else if (StringUtils.equals(type, "individual") &&
				 !StringUtils.isEmpty(assetType)) {

			return _getAssetIndividualRowsCount(
				assetId, assetType, channelId, query, timeRange);
		}
		else if (StringUtils.equals(type, "search-terms")) {
			Map<String, Integer> searchTermsCounts =
				_bqEventRepository.getSearchTermsCounts(
					channelId, _getSearchQueryParams(), timeRange,
					_timeZoneDog.getTimeZoneId());

			Integer count = searchTermsCounts.get("totalDistinct");

			return count.longValue();
		}

		return null;
	}

	private List<String[]> _getAssetBlogRows(
		Long channelId, String keywords, Set<String> selectedMetrics,
		String[] sorts, TimeRange timeRange, String type) {

		List<String[]> rows = new ArrayList<>();

		rows.add(
			new String[] {
				"Name", "Id", "Views", "Reading Time", "Comments", "Rating",
				"Property Name"
			});

		Channel channel = _channelDog.getChannel(channelId);

		List<BlogMetric> blogMetrics = (List<BlogMetric>)_getAssetMetrics(
			channelId, keywords, selectedMetrics,
			_getSort(sorts, Order.desc(BlogMetricType.VIEWS.getName())),
			timeRange, type);

		for (BlogMetric blogMetric : blogMetrics) {
			rows.add(
				new String[] {
					blogMetric.getAssetTitle(), blogMetric.getAssetId(),
					_getMetricValueAsString(blogMetric.getViewsMetric()),
					_getMetricValueAsString(blogMetric.getReadingTimeMetric()),
					_getMetricValueAsString(blogMetric.getCommentsMetric()),
					_getMetricValueAsString(blogMetric.getRatingsMetric()),
					channel.getName()
				});
		}

		return rows;
	}

	private List<String[]> _getAssetDocumentsLibraryRows(
		Long channelId, String keywords, Set<String> selectedMetrics,
		String[] sorts, TimeRange timeRange, String type) {

		List<String[]> rows = new ArrayList<>();

		rows.add(
			new String[] {
				"Name", "Id", "Downloads", "Impressions", "Comments", "Rating",
				"Property Name"
			});

		Channel channel = _channelDog.getChannel(channelId);

		List<DocumentLibraryMetric> documentLibraryMetrics =
			(List<DocumentLibraryMetric>)_getAssetMetrics(
				channelId, keywords, selectedMetrics,
				_getSort(
					sorts,
					Order.desc(DocumentLibraryMetricType.DOWNLOADS.getName())),
				timeRange, type);

		for (DocumentLibraryMetric documentLibraryMetric :
				documentLibraryMetrics) {

			rows.add(
				new String[] {
					documentLibraryMetric.getAssetTitle(),
					documentLibraryMetric.getAssetId(),
					_getMetricValueAsString(
						documentLibraryMetric.getDownloadsMetric()),
					_getMetricValueAsString(
						documentLibraryMetric.getImpressionMadeMetric()),
					_getMetricValueAsString(
						documentLibraryMetric.getCommentsMetric()),
					_getMetricValueAsString(
						documentLibraryMetric.getRatingsMetric()),
					channel.getName()
				});
		}

		return rows;
	}

	private List<String[]> _getAssetFormRows(
		Long channelId, String keywords, Set<String> selectedMetrics,
		String[] sorts, TimeRange timeRange, String type) {

		List<String[]> rows = new ArrayList<>();

		rows.add(
			new String[] {
				"Name", "id", "Submissions", "Views", "Abandonment",
				"Completion time", "Property Name"
			});

		Channel channel = _channelDog.getChannel(channelId);

		List<FormMetric> formMetrics = (List<FormMetric>)_getAssetMetrics(
			channelId, keywords, selectedMetrics,
			_getSort(sorts, Order.desc(FormMetricType.SUBMISSIONS.getName())),
			timeRange, type);

		for (FormMetric formMetric : formMetrics) {
			rows.add(
				new String[] {
					formMetric.getAssetTitle(), formMetric.getAssetId(),
					_getMetricValueAsString(formMetric.getSubmissionsMetric()),
					_getMetricValueAsString(formMetric.getViewsMetric()),
					_getMetricValueAsString(formMetric.getAbandonmentsMetric()),
					_getMetricValueAsString(
						formMetric.getCompletionTimeMetric()),
					channel.getName()
				});
		}

		return rows;
	}

	private List<String[]> _getAssetIndividualRows(
		@Nullable String assetId, String assetType, Long channelId,
		@Nullable String query, @Nullable String[] sorts,
		@Nullable TimeRange timeRange) {

		List<String[]> rows = new ArrayList<>();

		rows.add(new String[] {"Name", "Email", "Property Name"});

		AssetMetricRepository assetMetricRepository =
			_assetMetricRepositoryMap.get(AssetType.of(assetType));

		List<com.liferay.osb.asah.backend.model.Individual> individuals =
			assetMetricRepository.getKnownIndividuals(
				assetId, null, channelId,
				_getMetricType(AssetType.of(assetType)),
				PageRequest.of(0, _MAX_SIZE, _getSort(sorts, Order.asc("id"))),
				query, timeRange);

		Channel channel = _channelDog.getChannel(channelId);

		for (com.liferay.osb.asah.backend.model.Individual individual :
				individuals) {

			rows.add(
				new String[] {
					individual.getName(), individual.getEmailAddress(),
					channel.getName()
				});
		}

		return rows;
	}

	private long _getAssetIndividualRowsCount(
		@Nullable String assetId, String assetType, Long channelId,
		@Nullable String query, @Nullable TimeRange timeRange) {

		AssetMetricRepository<?> assetMetricRepository =
			_assetMetricRepositoryMap.get(AssetType.of(assetType));

		return assetMetricRepository.getKnownIndividualsCount(
			assetId, null, channelId, _getMetricType(AssetType.of(assetType)),
			query, timeRange);
	}

	private List<String[]> _getAssetJournalRows(
		Long channelId, String keywords, Set<String> selectedMetrics,
		String[] sorts, TimeRange timeRange, String type) {

		List<String[]> rows = new ArrayList<>();

		rows.add(new String[] {"Name", "Id", "Views", "Property Name"});

		Channel channel = _channelDog.getChannel(channelId);

		List<JournalMetric> journalMetrics =
			(List<JournalMetric>)_getAssetMetrics(
				channelId, keywords, selectedMetrics,
				_getSort(sorts, Order.desc(JournalMetricType.VIEWS.getName())),
				timeRange, type);

		for (JournalMetric journalMetric : journalMetrics) {
			rows.add(
				new String[] {
					journalMetric.getAssetTitle(), journalMetric.getAssetId(),
					_getMetricValueAsString(journalMetric.getViewsMetric()),
					channel.getName()
				});
		}

		return rows;
	}

	private List<? extends AssetMetric> _getAssetMetrics(
		Long channelId, String keywords, Set<String> selectedMetrics,
		org.springframework.data.domain.Sort sorts, TimeRange timeRange,
		String type) {

		AssetMetricRepository assetMetricRepository =
			_assetMetricRepositoryMap.get(AssetType.of(type));

		return assetMetricRepository.getAssetMetrics(
			channelId, keywords, null, PageRequest.of(0, _MAX_SIZE, sorts),
			selectedMetrics, timeRange);
	}

	private Long _getAssetMetricsCount(
		Long channelId, String keywords, TimeRange timeRange, String type) {

		AssetMetricRepository assetMetricRepository =
			_assetMetricRepositoryMap.get(AssetType.of(type));

		return assetMetricRepository.getAssetMetricsCount(
			channelId, keywords, null, timeRange);
	}

	private List<String[]> _getAssetPageRows(
		Long channelId, String keywords, Set<String> selectedMetrics,
		String[] sorts, TimeRange timeRange, String type) {

		List<String[]> rows = new ArrayList<>();

		rows.add(
			new String[] {
				"Page Title", "Canonical URL", "Unique Visitors", "Views",
				"Time on Page", "Bounce Rate", "Entrances", "Exit %",
				"Property Name"
			});

		Channel channel = _channelDog.getChannel(channelId);

		List<PageMetric> pageMetrics = (List<PageMetric>)_getAssetMetrics(
			channelId, keywords, selectedMetrics,
			_getSort(sorts, Order.desc(PageMetricType.VISITORS.getName())),
			timeRange, type);

		for (PageMetric pageMetric : pageMetrics) {
			Metric timeOnPageMetric = pageMetric.getTimeOnPageMetric();

			Double timeOnPage = timeOnPageMetric.getValue();

			rows.add(
				new String[] {
					pageMetric.getAssetTitle(), pageMetric.getAssetId(),
					_getMetricValueAsString(pageMetric.getVisitorsMetric()),
					_getMetricValueAsString(pageMetric.getViewsMetric()),
					DurationFormatUtils.formatDuration(
						timeOnPage.longValue(), "HH:mm:ss.SSS"),
					_getMetricValueAsString(pageMetric.getBounceRateMetric()),
					_getMetricValueAsString(pageMetric.getEntrancesMetric()),
					_getMetricValueAsString(pageMetric.getExitRateMetric()),
					channel.getName()
				});
		}

		return rows;
	}

	private List<String[]> _getEventRows(
		Long channelId, String individualId, String keywords, String[] sorts,
		TimeRange timeRange) {

		List<String[]> rows = new ArrayList<>();

		rows.add(
			new String[] {
				"Date", "Hour", "Name of Event", "Canonical URL", "Referrer",
				"Title", "URL"
			});

		LocalDateTime rangeEndLocalDateTime = null;
		LocalDateTime rangeStartLocalDateTime = null;

		if (timeRange != null) {
			rangeEndLocalDateTime = timeRange.getEndLocalDateTime();
			rangeStartLocalDateTime = timeRange.getStartLocalDateTime();
		}

		List<BQEvent> bqEvents = _bqEventRepository.searchBQEvents(
			channelId, individualId, keywords,
			PageRequest.of(
				0, _MAX_SIZE, _getSort(sorts, Order.desc("eventDate"))),
			rangeEndLocalDateTime, rangeStartLocalDateTime,
			_timeZoneDog.getTimeZoneId());

		for (BQEvent bqEvent : bqEvents) {
			rows.add(
				new String[] {
					DateUtil.toUTCString(
						bqEvent.getEventDate(), DateUtil.PATTERN_SHORT),
					DateUtil.toUTCString(
						bqEvent.getEventDate(), "HH:mm:ss.SSS"),
					bqEvent.getEventId(), bqEvent.getCanonicalUrl(),
					bqEvent.getReferrer(), bqEvent.getTitle(), bqEvent.getURL()
				});
		}

		return rows;
	}

	private List<String[]> _getIndividualRows(
		Long channelId, @Nullable String query, @Nullable String[] sorts) {

		List<String[]> rows = new ArrayList<>();

		rows.add(
			new String[] {
				"Name", "Email", "Job Title", "Total Activities",
				"Last Activity", "Property Name"
			});

		List<Individual> individuals =
			_bqIndividualRepository.searchBQIndividuals(
				null, channelId, null, null, null,
				PageRequest.of(0, _MAX_SIZE, _getSort(sorts, Order.asc("id"))),
				query, null);

		Channel channel = _channelDog.getChannel(channelId);

		for (Individual individual : individuals) {
			Individual.Demographics demographics = individual.getDemographics();

			Map<String, List<Field>> fieldMap = demographics.getField();

			Object givenNameFiledValue = fieldMap.get(
				"givenName"
			).get(
				0
			).getValue();

			Object familyNameFieldValue = fieldMap.get(
				"familyName"
			).get(
				0
			).getValue();

			String lastActivityDateString = "";

			Date lastActivityDate = individual.getLastActivityDate();

			if (Objects.nonNull(lastActivityDate)) {
				lastActivityDateString = DateUtil.toUTCString(lastActivityDate);
			}

			rows.add(
				new String[] {
					givenNameFiledValue + " " + familyNameFieldValue,
					String.valueOf(
						fieldMap.get(
							"email"
						).get(
							0
						).getValue()),
					String.valueOf(
						fieldMap.get(
							"jobTitle"
						).get(
							0
						).getValue()),
					Objects.toString(individual.getActivitiesCount(), ""),
					lastActivityDateString, channel.getName()
				});
		}

		return rows;
	}

	private List<String[]> _getMembershipRows(
		Long channelId, Long segmentId, String[] sorts) {

		List<String[]> rows = new ArrayList<>();

		rows.add(new String[] {"Name", "Email", "First Seen"});

		List<Individual> individuals =
			_bqIndividualRepository.searchBQIndividuals(
				null, channelId, null, null, null,
				PageRequest.of(
					0, _MAX_SIZE, _getSort(sorts, Order.asc("givenName"))),
				null, segmentId);

		for (Individual individual : individuals) {
			Individual.Demographics demographics = individual.getDemographics();

			Map<String, List<Field>> fieldMap = demographics.getField();

			Object givenNameFieldValue = fieldMap.get(
				"givenName"
			).get(
				0
			).getValue();

			Object familyNameFieldValue = fieldMap.get(
				"familyName"
			).get(
				0
			).getValue();

			String firstActivityDateString = "";

			Date firstActivityDate = individual.getFirstActivityDate();

			if (Objects.nonNull(firstActivityDate)) {
				firstActivityDateString = DateUtil.toUTCString(
					firstActivityDate);
			}

			rows.add(
				new String[] {
					givenNameFieldValue + " " + familyNameFieldValue,
					String.valueOf(
						fieldMap.get(
							"email"
						).get(
							0
						).getValue()),
					firstActivityDateString
				});
		}

		return rows;
	}

	private MetricType _getMetricType(AssetType assetType) {
		if (assetType == AssetType.BLOG) {
			return PageMetricType.VIEWS;
		}
		else if (assetType == AssetType.DOCUMENT) {
			return DocumentLibraryMetricType.DOWNLOADS;
		}
		else if (assetType == AssetType.FORM) {
			return FormMetricType.VIEWS;
		}
		else if (assetType == AssetType.JOURNAL) {
			return JournalMetricType.VIEWS;
		}
		else if (assetType == AssetType.PAGE) {
			return PageMetricType.VIEWS;
		}

		return null;
	}

	private String _getMetricValueAsString(Metric metric) {
		return String.valueOf(metric.getValue());
	}

	private String[] _getSearchQueryParams() {
		Set<String> searchQueryParams = new HashSet<>();

		Preference preference = _preferenceDog.getPreference(
			"search-query-strings");

		String preferenceValue = preference.getValue();

		if (!StringUtil.isNull(preferenceValue)) {
			searchQueryParams = JSONUtil.toStringSet(
				new JSONArray(preferenceValue));
		}

		searchQueryParams.add(
			"_com_liferay_portal_search_web_portlet_SearchPortlet_keywords");
		searchQueryParams.add("q");

		return searchQueryParams.toArray(new String[0]);
	}

	private List<String[]> _getSearchTermsRows(
		long channelId, TimeRange timeRange, String timeZoneId) {

		List<String[]> rows = new ArrayList<>();

		rows.add(new String[] {"Search Query", "Searches", "% of Searches"});

		Map<String, Integer> searchTermsCounts =
			_bqEventRepository.getSearchTermsCounts(
				channelId, _getSearchQueryParams(), timeRange,
				_timeZoneDog.getTimeZoneId());

		Integer total = searchTermsCounts.get("total");

		Map<String, BigDecimal> searchTerms = _bqEventRepository.getSearchTerms(
			channelId, _getSearchQueryParams(), null, timeRange, timeZoneId);

		for (Map.Entry<String, BigDecimal> entry : searchTerms.entrySet()) {
			String searchTerm = entry.getKey();

			BigDecimal value = entry.getValue();

			BigDecimal percentage = value.multiply(
				BigDecimal.valueOf(100)
			).divide(
				new BigDecimal(total), 2, RoundingMode.HALF_UP
			);

			rows.add(
				new String[] {
					searchTerm, value.toString(), percentage.toString()
				});
		}

		return rows;
	}

	private org.springframework.data.domain.Sort _getSort(
		String[] sorts, Order defaultOrder) {

		if (ArrayUtils.isEmpty(sorts)) {
			return org.springframework.data.domain.Sort.by(defaultOrder);
		}

		List<Sort.Order> orders = new ArrayList<>();

		for (int i = 0; i < sorts.length; i++) {
			String sort = sorts[i];

			String orderString = null;

			String[] properties = sort.split(",");

			if (properties.length == 1) {
				orderString = sorts[++i];
			}
			else {
				orderString = properties[1];
			}

			Sort.Order order = null;

			if (Objects.equals(orderString, "asc")) {
				order = Sort.Order.asc(properties[0]);
			}
			else {
				order = Sort.Order.desc(properties[0]);
			}

			if (StringUtils.containsIgnoreCase(properties[0], "date")) {
				orders.add(order);
			}
			else {
				orders.add(order.ignoreCase());
			}
		}

		return Sort.by(orders);
	}

	private static final int _MAX_SIZE = 10000;

	private final Map<AssetType, AssetMetricRepository>
		_assetMetricRepositoryMap = new HashMap<>();
	private final BQEventRepository _bqEventRepository;
	private final BQIndividualRepository _bqIndividualRepository;
	private final BQMembershipRepository _bqMembershipRepository;
	private final ChannelDog _channelDog;
	private final PreferenceDog _preferenceDog;
	private final TimeZoneDog _timeZoneDog;

}