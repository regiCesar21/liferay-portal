<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
PortletURL manageCollaboratorsURL = PortletProviderUtil.getPortletURL(request, SharingEntry.class.getName(), PortletProvider.Action.MANAGE);

manageCollaboratorsURL.setWindowState(LiferayWindowState.POP_UP);

PortletURL sharingURL = PortletProviderUtil.getPortletURL(request, SharingEntry.class.getName(), PortletProvider.Action.EDIT);

sharingURL.setWindowState(LiferayWindowState.POP_UP);
%>

<aui:script sandbox="<%= true %>">
	function showDialog(uri, title) {
		Liferay.Util.openModal({
			id: 'sharingDialog',
			iframeBodyCssClass: 'sharing-dialog',
			height: 475,
			size: 'md',
			title: Liferay.Util.escapeHTML(title),
			url: uri,
		});
	}

	var Sharing = {
		manageCollaborators: function (classNameId, classPK) {
			var manageCollaboratorsParameters = {
				classNameId: classNameId,
				classPK: classPK,
			};

			var manageCollaboratorsURL = Liferay.Util.PortletURL.createPortletURL(
				'<%= manageCollaboratorsURL.toString() %>',
				manageCollaboratorsParameters
			);

			showDialog(
				manageCollaboratorsURL.toString(),
				'<%= LanguageUtil.get(resourceBundle, "manage-collaborators") %>'
			);
		},

		share: function (classNameId, classPK, title) {
			var sharingParameters = {
				classNameId: classNameId,
				classPK: classPK,
			};

			var sharingURL = Liferay.Util.PortletURL.createPortletURL(
				'<%= sharingURL.toString() %>',
				sharingParameters
			);

			showDialog(sharingURL.toString(), title);
		},
	};

	Liferay.Sharing = Sharing;
</aui:script>