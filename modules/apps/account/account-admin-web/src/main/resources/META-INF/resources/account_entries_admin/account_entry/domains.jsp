<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

List<String> domains = accountEntryDisplay.getDomains();
%>

<liferay-ui:error exception="<%= AccountEntryDomainsException.class %>" message="please-enter-a-valid-mail-domain" />

<liferay-util:buffer
	var="removeDomainIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<clay:sheet-section>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="valid-domains" /></span>
		</clay:content-col>

		<clay:content-col
			containerElement="span"
		>
			<span class="heading-end">
				<liferay-ui:icon
					cssClass="modify-link"
					id="addDomains"
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message="add"
					method="get"
					url="javascript:;"
				/>
			</span>
		</clay:content-col>
	</clay:content-row>

	<aui:input name="domains" type="hidden" value="<%= StringUtil.merge(domains) %>" />

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		emptyResultsMessage="this-account-does-not-have-a-valid-domain"
		headerNames="title,null"
		id="accountDomainsSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= domains.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= domains.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="java.lang.String"
			modelVar="domain"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= domain %>"
			/>

			<liferay-ui:search-container-column-text>
				<a class="float-right modify-link" data-rowId="<%= domain %>" href="javascript:;"><%= removeDomainIcon %></a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:sheet-section>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />accountDomainsSearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	var domainsInput =
		document.<portlet:namespace />fm.<portlet:namespace />domains;

	var domains = domainsInput.value.split(',').filter(Boolean);

	searchContainerContentBox.delegate(
		'click',
		function (event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, rowId);

			A.Array.removeItem(domains, rowId);

			domainsInput.value = domains.join(',');
		},
		'.modify-link'
	);

	var addDomainsIcon = document.getElementById('<portlet:namespace />addDomains');

	if (addDomainsIcon) {
		addDomainsIcon.addEventListener('click', function (event) {
			event.preventDefault();

			Liferay.Util.openModal({
				customEvents: [
					{
						name:
							'<%= liferayPortletResponse.getNamespace() + "addDomains" %>',
						onEvent: function (event) {
							var newDomains = event.data.split(',');

							newDomains.forEach(function (domain) {
								domain = domain.trim();

								if (!domains.includes(domain)) {
									var rowColumns = [];

									rowColumns.push(Liferay.Util.escape(domain));
									rowColumns.push(
										'<a class="float-right modify-link" data-rowId="' +
											domain +
											'" href="javascript:;"><%= UnicodeFormatter.toString(removeDomainIcon) %></a>'
									);

									searchContainer.addRow(rowColumns, domain);

									domains.push(domain);
								}
							});

							searchContainer.updateDataStore();

							domainsInput.value = domains.join(',');
						},
					},
				],
				id: '<%= liferayPortletResponse.getNamespace() + "addDomains" %>',
				title: '<liferay-ui:message key="add-domain" />',

				<%
				PortletURL addDomainsURL = renderResponse.createRenderURL();

				addDomainsURL.setParameter("mvcPath", "/account_entries_admin/account_entry/add_domains.jsp");
				addDomainsURL.setWindowState(LiferayWindowState.POP_UP);
				%>

				url: '<%= addDomainsURL.toString() %>',
			});
		});
	}
</aui:script>