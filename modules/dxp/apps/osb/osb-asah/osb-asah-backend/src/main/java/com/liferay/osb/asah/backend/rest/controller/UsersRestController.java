/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller;

import com.liferay.osb.asah.common.dog.BQUserDog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Martins
 */
@RequestMapping("/users")
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.UsersRestController"
)
public class UsersRestController {

	@GetMapping("/count")
	public long getUsersCount() {
		return _bqUserDog.getBQUsersCount();
	}

	@Autowired
	private BQUserDog _bqUserDog;

}