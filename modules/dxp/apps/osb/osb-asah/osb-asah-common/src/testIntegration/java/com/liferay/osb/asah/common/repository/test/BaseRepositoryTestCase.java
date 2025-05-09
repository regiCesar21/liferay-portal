/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.test;

import com.liferay.osb.asah.common.OSBAsahCommonSpringTestContext;
import com.liferay.osb.asah.common.repository.Repository;
import com.liferay.osb.asah.common.util.ListUtil;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;

/**
 * @author Inácio Nery
 */
public abstract class BaseRepositoryTestCase<T extends Persistable<ID>, ID>
	implements OSBAsahCommonSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@AfterEach
	public void tearDown() {
		Repository<T, ID> repository = getRepository();

		repository.deleteAll();
	}

	@Test
	public void testCount() {
		Repository<T, ID> repository = getRepository();

		Assertions.assertEquals(entityModels.size(), repository.count());
	}

	@Test
	public void testDelete() {
		Repository<T, ID> repository = getRepository();

		repository.delete(entityModels.get(0));

		Assertions.assertEquals(entityModels.size() - 1, repository.count());
	}

	@Test
	public void testDeleteAll1() {
		Repository<T, ID> repository = getRepository();

		repository.deleteAll();

		Assertions.assertEquals(0, repository.count());
	}

	@Test
	public void testDeleteAll2() {
		Repository<T, ?> repository = getRepository();

		repository.deleteAll(entityModels);

		Assertions.assertEquals(0, repository.count());
	}

	@Test
	public void testDeleteById() {
		T model = entityModels.get(0);

		ID id = model.getId();

		Assertions.assertNotNull(id);

		Repository<T, ID> repository = getRepository();

		repository.deleteById(id);

		Assertions.assertEquals(entityModels.size() - 1, repository.count());
	}

	@Test
	public void testExistsById() {
		T model = entityModels.get(0);

		ID id = model.getId();

		Assertions.assertNotNull(id);

		Repository<T, ID> repository = getRepository();

		Assertions.assertTrue(repository.existsById(id));
	}

	@Test
	public void testFindAll1() {
		Repository<T, ID> repository = getRepository();

		Assertions.assertEquals(entityModels, repository.findAll());
	}

	@Test
	public void testFindAll2() {
		Repository<T, ID> repository = getRepository();

		Page<T> page = repository.findAll(
			PageRequest.of(0, entityModels.size(), Sort.by("id")));

		Assertions.assertEquals(entityModels, page.getContent());
	}

	@Test
	public void testFindAll3() {
		Repository<T, ID> repository = getRepository();

		Assertions.assertEquals(
			entityModels, repository.findAll(Sort.by("id")));
	}

	@Test
	public void testFindAllById() {
		Repository<T, ID> repository = getRepository();

		Assertions.assertEquals(
			entityModels,
			repository.findAllById(ListUtil.map(entityModels, T::getId)));
	}

	@Test
	public void testFindById() {
		T model = entityModels.get(0);

		ID id = model.getId();

		Assertions.assertNotNull(id);

		Repository<T, ID> repository = getRepository();

		Optional<T> modelOptional = repository.findById(id);

		Assertions.assertTrue(modelOptional.isPresent());
	}

	@Test
	public void testSave() {
		Repository<T, ID> repository = getRepository();

		Assertions.assertEquals(
			entityModels.get(0), repository.save(entityModels.get(0)));
	}

	@Test
	public void testSaveAll() {
		Repository<T, ID> repository = getRepository();

		Assertions.assertEquals(entityModels, repository.saveAll(entityModels));
	}

	protected abstract Repository<T, ID> getRepository();

	protected void setUpRepository(T... entityModels) {
		Repository<T, ID> repository = getRepository();

		this.entityModels = IterableUtils.toList(
			repository.saveAll(Arrays.asList(entityModels)));
	}

	protected List<T> entityModels;

}