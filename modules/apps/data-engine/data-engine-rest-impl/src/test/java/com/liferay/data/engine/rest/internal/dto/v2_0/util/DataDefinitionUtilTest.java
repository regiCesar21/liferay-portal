/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mateus Santana
 */
@PrepareForTest(LocaleUtil.class)
@RunWith(PowerMockRunner.class)
public class DataDefinitionUtilTest extends PowerMockito {

	@Before
	public void setUp() {
		_setUpLocaleUtil();
	}

	@Test
	public void testToDDMFormEquals() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US}),
			LocaleUtil.US);

		Locale defaultLocale = ddmForm.getDefaultLocale();

		ddmForm.addDDMFormField(
			new DDMFormField() {
				{
					setIndexType("text");
					setLabel(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "label1"
							).put(
								"pt_BR", "rótulo1"
							).build(),
							defaultLocale));
					setLocalizable(true);
					setName("name1");
					setPredefinedValue(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "enter a text"
							).put(
								"pt_BR", "insira um texto"
							).build(),
							defaultLocale));
					setReadOnly(true);
					setRepeatable(true);
					setRequired(true);
					setShowLabel(true);
					setTip(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "tip1"
							).put(
								"pt_BR", "ajuda1"
							).build(),
							defaultLocale));
					setType("text");
				}
			});
		ddmForm.addDDMFormField(
			new DDMFormField() {
				{
					setIndexType("keyword");
					setLabel(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "label2"
							).put(
								"pt_BR", "rótulo2"
							).build(),
							defaultLocale));
					setLocalizable(false);
					setName("name2");
					setPredefinedValue(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "select an option"
							).put(
								"pt_BR", "selecione uma opção"
							).build(),
							defaultLocale));
					setReadOnly(false);
					setRepeatable(false);
					setRequired(false);
					setShowLabel(false);
					setTip(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "tip2"
							).put(
								"pt_BR", "ajuda2"
							).build(),
							defaultLocale));
					setType("select");
				}
			});

		Assert.assertEquals(
			ddmForm,
			DataDefinitionUtil.toDDMForm(
				new DataDefinition() {
					{
						availableLanguageIds = new String[] {"en_US", "pt_BR"};
						dataDefinitionFields = new DataDefinitionField[] {
							new DataDefinitionField() {
								{
									defaultValue =
										HashMapBuilder.<String, Object>put(
											"en_US", "enter a text"
										).put(
											"pt_BR", "insira um texto"
										).build();
									fieldType = "text";
									indexType = IndexType.TEXT;
									label = HashMapBuilder.<String, Object>put(
										"en_US", "label1"
									).put(
										"pt_BR", "rótulo1"
									).build();
									localizable = true;
									name = "name1";
									readOnly = true;
									repeatable = true;
									required = true;
									showLabel = true;
									tip = HashMapBuilder.<String, Object>put(
										"en_US", "tip1"
									).put(
										"pt_BR", "ajuda1"
									).build();
								}
							},
							new DataDefinitionField() {
								{
									defaultValue =
										HashMapBuilder.<String, Object>put(
											"en_US", "select an option"
										).put(
											"pt_BR", "selecione uma opção"
										).build();
									fieldType = "select";
									indexType = IndexType.KEYWORD;
									label = HashMapBuilder.<String, Object>put(
										"en_US", "label2"
									).put(
										"pt_BR", "rótulo2"
									).build();
									localizable = false;
									name = "name2";
									readOnly = false;
									repeatable = false;
									required = false;
									showLabel = false;
									tip = HashMapBuilder.<String, Object>put(
										"en_US", "tip2"
									).put(
										"pt_BR", "ajuda2"
									).build();
								}
							}
						};
						defaultLanguageId = "en_US";
					}
				},
				_ddmFormFieldTypeServicesTracker));
	}

	@Test
	public void testToDDMFormWithEmptyDataDefinition() {
		DDMForm ddmForm = DataDefinitionUtil.toDDMForm(
			new DataDefinition(), _ddmFormFieldTypeServicesTracker);

		Assert.assertTrue(SetUtil.isEmpty(ddmForm.getAvailableLocales()));
		Assert.assertTrue(ListUtil.isEmpty(ddmForm.getDDMFormFields()));
		Assert.assertNull(LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()));
	}

	@Test
	public void testToDDMFormWithNullDataDefinition() {
		Assert.assertEquals(
			new DDMForm(),
			DataDefinitionUtil.toDDMForm(
				null, _ddmFormFieldTypeServicesTracker));
	}

	private void _setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);
	}

	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

}