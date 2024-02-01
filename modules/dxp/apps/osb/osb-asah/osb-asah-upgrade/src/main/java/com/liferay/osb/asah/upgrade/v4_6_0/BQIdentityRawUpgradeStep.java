/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_6_0;

import com.liferay.osb.asah.common.entity.Suppression;
import com.liferay.osb.asah.common.repository.SuppressionRepository;
import com.liferay.osb.asah.common.repository.executor.BigQueryQueryExecutor;
import com.liferay.osb.asah.common.util.ListUtil;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
public class BQIdentityRawUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) {
		List<String> emailAddresses = ListUtil.map(
			_suppressionRepository.findAll(), Suppression::getEmailAddress);

		Stream<String> emailAddressStream = emailAddresses.stream();

		_bigQueryQueryExecutor.queryExecute(
			String.format(
				"UPDATE BQIdentity_Raw SET individualId = NULL WHERE " +
					"individualId IN (%s);",
				emailAddressStream.map(
					emailAddress ->
						"'" + DigestUtils.sha256Hex(emailAddress) + "'"
				).collect(
					Collectors.joining(",")
				)));

		if (_log.isInfoEnabled()) {
			_log.info("BQIdentity_Raw has successfully upgraded");
		}
	}

	private static final Log _log = LogFactory.getLog(
		BQIdentityRawUpgradeStep.class);

	@Autowired
	private BigQueryQueryExecutor _bigQueryQueryExecutor;

	@Autowired
	private SuppressionRepository _suppressionRepository;

}