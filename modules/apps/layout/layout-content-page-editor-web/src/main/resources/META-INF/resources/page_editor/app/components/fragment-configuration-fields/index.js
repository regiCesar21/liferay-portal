/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CheckboxField} from './CheckboxField';
import {CollectionSelectorField} from './CollectionSelectorField';
import {ColorPaletteField} from './ColorPaletteField';
import {ColorPickerField} from './ColorPickerField';
import {ImageSelectorField} from './ImageSelectorField';
import {ItemSelectorField} from './ItemSelectorField';
import {SelectField} from './SelectField';
import {TextField} from './TextField';

export const FRAGMENT_CONFIGURATION_FIELDS = {
	checkbox: CheckboxField,
	collectionSelector: CollectionSelectorField,
	colorPalette: ColorPaletteField,
	colorPicker: ColorPickerField,
	imageSelector: ImageSelectorField,
	itemSelector: ItemSelectorField,
	select: SelectField,
	text: TextField,
};
