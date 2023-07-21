/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// Address API

// Aop API

export {default as AOP} from './liferay/aop/AOP.es';

// AutoSize API

export {default as autoSize} from './liferay/autosize/autosize.es';

// Debounce API

export {cancelDebounce, debounce} from './liferay/debounce/debounce.es';

// Delegate API

export {default as delegate} from './liferay/delegate/delegate.es';

// Form API

export {default as objectToFormData} from './liferay/util/form/object_to_form_data.es';

// Liferay API

export {default as BREAKPOINTS} from './liferay/breakpoints';

export {default as CompatibilityEventProxy} from './liferay/CompatibilityEventProxy.es';

export {default as DefaultEventHandler} from './liferay/DefaultEventHandler.es';
export {default as ItemSelectorDialog} from './liferay/ItemSelectorDialog.es';
export {default as PortletBase} from './liferay/PortletBase.es';

// Modal API

export {openModal, openSelectionModal} from './liferay/modal/Modal';

export {default as openSimpleInputModal} from './liferay/modal/commands/OpenSimpleInputModal.es';

// PortletURL API

export {default as createActionURL} from './liferay/util/portlet_url/create_action_url.es';

export {default as createPortletURL} from './liferay/util/portlet_url/create_portlet_url.es';

export {default as createRenderURL} from './liferay/util/portlet_url/create_render_url.es';

export {default as createResourceURL} from './liferay/util/portlet_url/create_resource_url.es';

// Session API

export {getSessionValue, setSessionValue} from './liferay/util/session.es';

// Toast API

export {openToast} from './liferay/toast/commands/OpenToast.es';

// Throttle API

export {default as throttle} from './liferay/throttle.es';

// Util API

export {default as addParams} from './liferay/util/add_params';
export {default as fetch} from './liferay/util/fetch.es';
export {default as focusFormField} from './liferay/util/focus_form_field';
export {default as getPortletId} from './liferay/util/get_portlet_id';
export {default as getPortletNamespace} from './liferay/util/get_portlet_namespace.es';
export {default as inBrowserView} from './liferay/util/in_browser_view';
export {default as isPhone} from './liferay/util/is_phone';
export {default as isTablet} from './liferay/util/is_tablet';
export {default as navigate} from './liferay/util/navigate.es';
export {default as normalizeFriendlyURL} from './liferay/util/normalize_friendly_url';
export {default as toggleDisabled} from './liferay/util/toggle_disabled';
