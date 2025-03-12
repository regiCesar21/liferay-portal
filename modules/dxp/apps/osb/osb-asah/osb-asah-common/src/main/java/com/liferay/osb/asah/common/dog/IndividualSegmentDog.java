/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.dog;

import com.liferay.osb.asah.common.repository.IndividualSegmentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class IndividualSegmentDog {

	public List<Long> getIndividualSegmentIds(String individualId) {
		return _individualSegmentRepository.findSegmentIdByIndividualId(
			individualId);
	}

	@Autowired
	private IndividualSegmentRepository _individualSegmentRepository;

}