/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import resolveEditableValue from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-content/resolveEditableValue';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			defaultLanguageId: 'en_US',
		},
	})
);

describe('resolveEditableValue', () => {
	it('return the editable value and the config for the given editable values', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
					en_US: 'value',
				},
			},
		};

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			() => {}
		);

		await expect(result).resolves.toStrictEqual(['value', {href: 'href'}]);
	});

	it('return the editable value of the provided processor', async () => {
		const editableValues = {
			[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
					en_US: 'value',
				},
			},
		};

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			() => {}
		);

		await expect(result).resolves.toStrictEqual(['value', {href: 'href'}]);
	});

	it('return the default value when the editable has no value', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
				},
			},
		};

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			() => {}
		);

		await expect(result).resolves.toStrictEqual([
			'default',
			{href: 'href'},
		]);
	});

	it('calls given function to retrieve the editable config when it is mapped', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						alt: 'alt',
						classNameId: 3,
						classPK: 2,
						fieldId: 'field',
					},
					defaultValue: 'default',
				},
			},
		};

		const getField = jest.fn(() => Promise.resolve('mapped'));

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			getField
		);

		expect(getField).toBeCalledWith(
			expect.objectContaining({
				classNameId: 3,
				classPK: 2,
				fieldId: 'field',
				languageId: 'en_US',
			})
		);

		await expect(result).resolves.toStrictEqual([
			'default',
			expect.objectContaining({alt: 'alt', href: 'mapped'}),
		]);
	});

	it('calls given function to retrieve the editable value when it is mapped', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					classNameId: 3,
					classPK: 2,
					config: {
						href: 'href',
					},
					fieldId: 'field',
				},
			},
		};

		const getField = jest.fn(() => Promise.resolve('mapped'));

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			getField
		);

		expect(getField).toBeCalledWith(
			expect.objectContaining({
				classNameId: 3,
				classPK: 2,
				fieldId: 'field',
				languageId: 'en_US',
			})
		);

		await expect(result).resolves.toStrictEqual(['mapped', {href: 'href'}]);
	});

	it('does not call given function to retrieve the editable value when it is mapped to a display page', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
					mappedField: 'mappedField',
				},
			},
		};

		const getField = jest.fn();

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			getField
		);

		expect(getField).not.toBeCalled();

		await expect(result).resolves.toStrictEqual([
			'default',
			{href: 'href'},
		]);
	});

	it('returns the editable value correctly when no segments experience is passed', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					en_US: 'value',
					mappedField: 'mappedField',
				},
			},
		};

		const getField = jest.fn();

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			null,
			getField
		);

		expect(getField).not.toBeCalled();

		await expect(result).resolves.toStrictEqual(['value', {href: 'href'}]);
	});
});
