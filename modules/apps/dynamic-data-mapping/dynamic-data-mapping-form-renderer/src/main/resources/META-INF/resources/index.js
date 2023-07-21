/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// Utils

export {default as compose} from './js/util/compose.es';
export {normalizeFieldName} from './js/util/fields.es';
export {
	getRepeatedIndex,
	generateName,
	generateInstanceId,
	parseName,
} from './js/util/repeatable.es';
export {PagesVisitor, RulesVisitor} from './js/util/visitors.es';
export * as FormSupport from './js/util/FormSupport.es';
export {getConnectedReactComponentAdapter} from './js/util/ReactComponentAdapter.es';

// Composing Form

export {default as Pages} from './js/components/Pages.es';
export {EVENT_TYPES} from './js/actions/eventTypes.es';
export {PageProvider, usePage} from './js/hooks/usePage.es';
export {FormProvider, FormNoopProvider, useForm} from './js/hooks/useForm.es';
export {Layout} from './js/components/PageRenderer/Layout.es';
export * as DefaultVariant from './js/components/PageRenderer/DefaultVariant.es';

// Containers

export {default as Form} from './js/containers/Form.es';
export {FormNoop} from './js/containers/FormNoop.es';
