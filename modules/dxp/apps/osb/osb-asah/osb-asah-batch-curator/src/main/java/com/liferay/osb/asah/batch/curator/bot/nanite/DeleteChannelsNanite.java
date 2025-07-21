/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.batch.curator.bot.nanite;

import com.liferay.osb.asah.common.dog.ChannelDog;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.model.Author;
import com.liferay.osb.asah.common.util.AuthorThreadLocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
public class DeleteChannelsNanite extends BaseNanite {

	@Override
	public boolean isLogRunEnabled() {
		return true;
	}

	@Override
	public void run(JSONObject contextJSONObject) throws Exception {
		AuthorThreadLocal.forAuthor(
			new Author(
				contextJSONObject.getLong("userId"),
				contextJSONObject.getString("userName")),
			() -> _channelDog.deleteChannels(
				JSONUtil.toLongSet(
					contextJSONObject.getJSONArray("channelIds")),
				String.valueOf(contextJSONObject.get("createDate"))));
	}

	@Override
	protected Log getLog() {
		return LogFactory.getLog(DeleteChannelsNanite.class);
	}

	@Autowired
	private ChannelDog _channelDog;

}