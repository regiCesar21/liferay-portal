/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.functional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.common.repository.executor.BigQueryQueryExecutor;
import com.liferay.osb.asah.common.spring.annotation.BigQueryColumn;
import com.liferay.osb.asah.common.util.BQSQLUtil;

import java.util.Date;

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
@RequestMapping(produces = "application/json", value = "/functional/blogsdaily")
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.functional.BQBlogDailyRestController"
)
public class BQBlogDailyRestController {

	@PostMapping
	public ResponseEntity postBQBlogsDaily(@RequestBody String json) {
		JSONArray jsonArray = new JSONArray(json);

		jsonArray.forEach(
			jsonObject -> _bigQueryQueryExecutor.queryExecute(
				BQSQLUtil.createInsertStatement(
					_objectMapper.convertValue(jsonObject, BlogDaily.class))));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	public static class BlogDaily {

		public BlogDaily() {
		}

		@BigQueryColumn
		public String getAssetId() {
			return _assetId;
		}

		@BigQueryColumn
		public String getAssetTitle() {
			return _assetTitle;
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
		public long getClicks() {
			return _clicks;
		}

		@BigQueryColumn
		public long getComments() {
			return _comments;
		}

		@BigQueryColumn
		public String getCountry() {
			return _country;
		}

		@BigQueryColumn
		public String getDeviceType() {
			return _deviceType;
		}

		@BigQueryColumn
		public Date getEventDate() {
			if (_eventDate == null) {
				return null;
			}

			return new Date(_eventDate.getTime());
		}

		@BigQueryColumn
		public String getPageTitle() {
			return _pageTitle;
		}

		@BigQueryColumn
		public String getPlatformName() {
			return _platformName;
		}

		@BigQueryColumn
		public long getRatings() {
			return _ratings;
		}

		@BigQueryColumn
		public double getRatingsScore() {
			return _ratingsScore;
		}

		@BigQueryColumn
		public long getReadTime() {
			return _readTime;
		}

		@BigQueryColumn
		public String getRegion() {
			return _region;
		}

		@BigQueryColumn
		public long getSessions() {
			return _sessions;
		}

		@BigQueryColumn
		public String getUserId() {
			return _userId;
		}

		@BigQueryColumn
		public long getViews() {
			return _views;
		}

		public void setAssetId(String assetId) {
			_assetId = assetId;
		}

		public void setAssetTitle(String assetTitle) {
			_assetTitle = assetTitle;
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

		public void setClicks(long clicks) {
			_clicks = clicks;
		}

		public void setComments(long comments) {
			_comments = comments;
		}

		public void setCountry(String country) {
			_country = country;
		}

		public void setDeviceType(String deviceType) {
			_deviceType = deviceType;
		}

		public void setEventDate(Date eventDate) {
			if (eventDate != null) {
				_eventDate = new Date(eventDate.getTime());
			}
		}

		public void setPageTitle(String pageTitle) {
			_pageTitle = pageTitle;
		}

		public void setPlatformName(String platformName) {
			_platformName = platformName;
		}

		public void setRatings(long ratings) {
			_ratings = ratings;
		}

		public void setRatingsScore(double ratingsScore) {
			_ratingsScore = ratingsScore;
		}

		public void setReadTime(long readTime) {
			_readTime = readTime;
		}

		public void setRegion(String region) {
			_region = region;
		}

		public void setSessions(long sessions) {
			_sessions = sessions;
		}

		public void setUserId(String userId) {
			_userId = userId;
		}

		public void setViews(long views) {
			_views = views;
		}

		private String _assetId;
		private String _assetTitle;
		private String _browserName;
		private String _canonicalUrl;
		private long _channelId;
		private String _city;
		private long _clicks;
		private long _comments;
		private String _country;
		private String _deviceType;
		private Date _eventDate;
		private String _pageTitle;
		private String _platformName;
		private long _ratings;
		private double _ratingsScore;
		private long _readTime;
		private String _region;
		private long _sessions;
		private String _userId;
		private long _views;

	}

	@Autowired
	private BigQueryQueryExecutor _bigQueryQueryExecutor;

	@Autowired
	private ObjectMapper _objectMapper;

}