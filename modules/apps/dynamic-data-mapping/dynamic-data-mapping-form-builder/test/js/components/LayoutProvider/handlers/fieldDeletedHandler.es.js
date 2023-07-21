/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormSupport} from 'dynamic-data-mapping-form-renderer';

import * as fieldDeletedHandler from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldDeletedHandler.es';
import RulesSupport from '../../../../../src/main/resources/META-INF/resources/js/components/RuleBuilder/RulesSupport.es';
import mockPages from '../../../__mock__/mockPages.es';

describe('LayoutProvider/handlers/fieldDeletedHandler', () => {
	describe('handleFieldDeleted(state, event)', () => {
		it('calls removeEmptyRows() when row is left with no fields after delete operation', () => {
			const event = {
				activePage: 0,
				fieldName: 'radio',
				removeEmptyRows: true,
			};
			const state = {
				pages: mockPages,
				rules: [],
			};

			const removeEmptyRowsSpy = jest.spyOn(
				FormSupport,
				'removeEmptyRows'
			);

			removeEmptyRowsSpy.mockImplementation(() => []);

			fieldDeletedHandler.handleFieldDeleted({}, state, event);

			expect(removeEmptyRowsSpy).toHaveBeenCalled();

			removeEmptyRowsSpy.mockRestore();
		});

		it('do not call formatRules when moving a field', () => {
			const event = {
				activePage: 0,
				editRule: false,
				fieldName: 'text1',
				removeEmptyRows: true,
			};
			const state = {
				pages: mockPages,
				rules: [
					{
						actions: [],
						conditions: [
							{
								operands: [
									{
										value: 'text1',
									},
								],
							},
						],
					},
				],
			};

			const formatRulesSpy = jest.spyOn(RulesSupport, 'formatRules');

			formatRulesSpy.mockImplementation(() => []);

			fieldDeletedHandler.handleFieldDeleted({}, state, event);

			expect(formatRulesSpy).not.toHaveBeenCalled();

			formatRulesSpy.mockRestore();
		});
	});
});
