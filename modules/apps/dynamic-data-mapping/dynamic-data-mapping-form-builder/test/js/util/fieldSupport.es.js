/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FIELD_TYPE_FIELDSET} from '../../../src/main/resources/META-INF/resources/js/util/constants.es';
import {isFieldSetChild} from '../../../src/main/resources/META-INF/resources/js/util/fieldSupport.es';

describe('Field Support Utilities', () => {
	describe('isFieldSetChild', () => {
		it('returns true when a field is a child of a FieldSet', () => {
			const pages = [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											ddmStructureId: 123,
											fieldName: 'myFieldSet',
											nestedFields: [
												{
													fieldName: 'fieldSetChild',
												},
											],
											type: FIELD_TYPE_FIELDSET,
										},
										{
											fieldName: 'notAFieldSet',
										},
										{
											fieldName: 'otherFieldset',
											nestedFields: [
												{
													fieldName: 'sectionChild',
												},
											],
											type: FIELD_TYPE_FIELDSET,
										},
									],
								},
							],
						},
					],
				},
			];

			expect(isFieldSetChild(pages, 'fieldSetChild')).toBe(true);
			expect(isFieldSetChild(pages, 'myFieldSet')).toBe(false);
			expect(isFieldSetChild(pages, 'notAFieldSet')).toBe(false);
			expect(isFieldSetChild(pages, 'sectionChild')).toBe(false);
		});
	});
});
