/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.compat;

import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.trash.kernel.service.TrashEntryLocalService;
import com.liferay.trash.kernel.service.TrashEntryService;
import com.liferay.trash.kernel.service.TrashVersionLocalService;
import com.liferay.trash.kernel.service.persistence.TrashEntryPersistence;
import com.liferay.trash.kernel.service.persistence.TrashVersionPersistence;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author Dante Wang
 */
public class CompatBeanDefinitionRegistryPostProcessor
	implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry beanDefinitionRegistry)
		throws BeansException {

		for (Class<?> clazz : _COMPAT_CLASSES) {
			BeanDefinitionBuilder beanDefinitionBuilder =
				BeanDefinitionBuilder.genericBeanDefinition(ProxyFactory.class);

			beanDefinitionBuilder.setFactoryMethod("newDummyInstance");
			beanDefinitionBuilder.addConstructorArgValue(clazz);

			beanDefinitionRegistry.registerBeanDefinition(
				clazz.getName(), beanDefinitionBuilder.getBeanDefinition());
		}
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory configurableListableBeanFactory)
		throws BeansException {
	}

	private static final Class<?>[] _COMPAT_CLASSES = {
		TrashEntryLocalService.class, TrashEntryService.class,
		TrashEntryPersistence.class, TrashVersionLocalService.class,
		TrashVersionPersistence.class
	};

}