/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.functional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.common.repository.executor.BigQueryQueryExecutor;
import com.liferay.osb.asah.common.spring.annotation.BigQueryColumn;
import com.liferay.osb.asah.common.util.BQSQLUtil;

import org.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Martins
 */
@Profile("dev")
@RequestMapping(produces = "application/json", value = "/functional/pagesdaily")
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.functional.BQPageDailyRestController"
)
public class BQPageDailyRestController {

	@PostMapping
	public ResponseEntity postBQPagesDaily(@RequestBody String json) {
		JSONArray jsonArray = new JSONArray(json);

		jsonArray.forEach(
			jsonObject -> _bigQueryQueryExecutor.queryExecute(
				BQSQLUtil.createInsertStatement(
					_objectMapper.convertValue(jsonObject, PageDaily.class))));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	public static class PageDaily {

		public PageDaily() {
		}

		@BigQueryColumn
		public long getBounce() {
			return _bounce;
		}

		@BigQueryColumn
		public String getBrowserName() {
			return _browserName;
		}

		@BigQueryColumn
		public String getCanonicalUrl() {
			return _canonicalUrl;
		}

		@BigQueryColumn
		public long getChannelId() {
			return _channelId;
		}

		@BigQueryColumn
		public String getCity() {
			return _city;
		}

		@BigQueryColumn
		public String getCountry() {
			return _country;
		}

		@BigQueryColumn
		public long getCtaClicks() {
			return _ctaClicks;
		}

		@BigQueryColumn
		public String getDescription() {
			return _description;
		}

		@BigQueryColumn
		public String getDeviceType() {
			return _deviceType;
		}

		@BigQueryColumn
		public long getDirectAccess() {
			return _directAccess;
		}

		@BigQueryColumn
		public long getEntrances() {
			return _entrances;
		}

		@BigQueryColumn
		public String getEventDate() {
			return _eventDate;
		}

		@BigQueryColumn
		public long getExits() {
			return _exits;
		}

		@BigQueryColumn
		public long getExperimentId() {
			return _experimentId;
		}

		@BigQueryColumn
		public long getIndirectAccess() {
			return _indirectAccess;
		}

		@BigQueryColumn
		public String getPlatformName() {
			return _platformName;
		}

		@BigQueryColumn
		public long getReads() {
			return _reads;
		}

		@BigQueryColumn
		public String getRegion() {
			return _region;
		}

		@BigQueryColumn
		public String getSessionId() {
			return _sessionId;
		}

		@BigQueryColumn
		public long getTimeOnPage() {
			return _timeOnPage;
		}

		@BigQueryColumn
		public String getTitle() {
			return _title;
		}

		@BigQueryColumn
		public String getUserId() {
			return _userId;
		}

		@BigQueryColumn
		public String getVariantId() {
			return _variantId;
		}

		@BigQueryColumn
		public long getViews() {
			return _views;
		}

		public void setBounce(long bounce) {
			_bounce = bounce;
		}

		public void setBrowserName(String browserName) {
			_browserName = browserName;
		}

		public void setCanonicalUrl(String canonicalUrl) {
			_canonicalUrl = canonicalUrl;
		}

		public void setChannelId(long channelId) {
			_channelId = channelId;
		}

		public void setCity(String city) {
			_city = city;
		}

		public void setCountry(String country) {
			_country = country;
		}

		public void setCtaClicks(long ctaClicks) {
			_ctaClicks = ctaClicks;
		}

		public void setDescription(String description) {
			_description = description;
		}

		public void setDeviceType(String deviceType) {
			_deviceType = deviceType;
		}

		public void setDirectAccess(long directAccess) {
			_directAccess = directAccess;
		}

		public void setEntrances(long entrances) {
			_entrances = entrances;
		}

		public void setEventDate(String eventDate) {
			_eventDate = eventDate;
		}

		public void setExits(long exits) {
			_exits = exits;
		}

		public void setExperimentId(long experimentId) {
			_experimentId = experimentId;
		}

		public void setIndirectAccess(long indirectAccess) {
			_indirectAccess = indirectAccess;
		}

		public void setPlatformName(String platformName) {
			_platformName = platformName;
		}

		public void setReads(long reads) {
			_reads = reads;
		}

		public void setRegion(String region) {
			_region = region;
		}

		public void setSessionId(String sessionId) {
			_sessionId = sessionId;
		}

		public void setTimeOnPage(long timeOnPage) {
			_timeOnPage = timeOnPage;
		}

		public void setTitle(String title) {
			_title = title;
		}

		public void setUserId(String userId) {
			_userId = userId;
		}

		public void setVariantId(String variantId) {
			_variantId = variantId;
		}

		public void setViews(long views) {
			_views = views;
		}

		private long _bounce;
		private String _browserName;
		private String _canonicalUrl;
		private long _channelId;
		private String _city;
		private String _country;
		private long _ctaClicks;
		private String _description;
		private String _deviceType;
		private long _directAccess;
		private long _entrances;
		private String _eventDate;
		private long _exits;
		private long _experimentId;
		private long _indirectAccess;
		private String _platformName;
		private long _reads;
		private String _region;
		private String _sessionId;
		private long _timeOnPage;
		private String _title;
		private String _userId;
		private String _variantId;
		private long _views;

	}

	@Autowired
	private BigQueryQueryExecutor _bigQueryQueryExecutor;

	@Autowired
	private ObjectMapper _objectMapper;

}