<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long[] digitalEnterpriseProductMinorVersions = StringUtil.split(PrefsParamUtil.getString(portletPreferences, request, "digitalEnterprise_productMinorVersions"), 0L);
long[] portalProductMinorVersions = StringUtil.split(PrefsParamUtil.getString(portletPreferences, request, "portal_productMinorVersions"), 0L);
%>

<c:choose>
	<c:when test="<%= ArrayUtil.isEmpty(digitalEnterpriseProductMinorVersions) || ArrayUtil.isEmpty(portalProductMinorVersions) %>">

		<%
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		%>

		<div class="portlet-msg-info">
			<liferay-ui:message key="please-configure-this-portlet-to-make-it-visible-to-all-users" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		TreeSet<AccountEntry> accountEntries = new TreeSet<AccountEntry>(new AccountEntryNameComparator(true));
		Map<String, Set<ProductEntry>> accountEntryProductEntriesMap = new HashMap<String, Set<ProductEntry>>();
		Set<String> partnerAccountKeys = new HashSet<String>();

		StringBundler sb = new StringBundler(9);

		sb.append("customerContactUuids/any(s:s eq '");
		sb.append(user.getUuid());
		sb.append("') and state eq 'active' and (property_type eq 'primary' ");
		sb.append("or contains(name, 'Commerce for DXP Cloud') ");
		sb.append("or contains(name, 'Commerce for Liferay PaaS') ");
		sb.append("or contains(name, 'Commerce Subscription') ");
		sb.append("or contains(name, 'DXP Cloud Subscription') ");
		sb.append("or contains(name, 'Liferay PaaS Subscription') ");
		sb.append("or contains(name, 'Partnership'))");

		List<ProductPurchaseView> productPurchaseViews = productPurchaseViewWebService.getProductPurchaseViews(StringPool.BLANK, sb.toString(), 1, 1000, StringPool.BLANK);

		for (ProductPurchaseView productPurchaseView : productPurchaseViews) {
			ProductPurchase[] productPurchases = productPurchaseView.getProductPurchases();

			if (ArrayUtil.isEmpty(productPurchases)) {
				continue;
			}

			ProductPurchase productPurchase = productPurchases[0];

			if (partnerAccountKeys.contains(productPurchase.getAccountKey())) {
				continue;
			}

			AccountEntry accountEntry = AccountEntryLocalServiceUtil.fetchKoroneikiAccountEntry(productPurchase.getAccountKey());

			if (accountEntry == null) {
				continue;
			}

			Product product = productPurchaseView.getProduct();

			if (ArrayUtil.contains(ProductConstants.NAMES_PARTNERSHIP, product.getName())) {
				accountEntries.add(accountEntry);

				accountEntryProductEntriesMap.put(productPurchase.getAccountKey(), new HashSet<>(ProductEntryLocalServiceUtil.getProductEntriesByEnvironment(ProductEntryConstants.ENVIRONMENT_DEVELOPMENT)));

				partnerAccountKeys.add(productPurchase.getAccountKey());

				continue;
			}

			ProductEntry productEntry = ProductEntryLocalServiceUtil.fetchProductEntryByKoroneikiKey(productPurchase.getProductKey());

			if (productEntry == null) {
				continue;
			}

			ProductEntry developerProductEntry = ProductEntryLocalServiceUtil.getDeveloperProductEntry(productEntry.getProductEntryId());

			if (developerProductEntry == null) {
				continue;
			}

			Set<ProductEntry> productEntries = accountEntryProductEntriesMap.get(productPurchase.getAccountKey());

			if (productEntries == null) {
				accountEntries.add(accountEntry);

				productEntries = new TreeSet<>(new ProductEntryNameComparator(true));

				accountEntryProductEntriesMap.put(productPurchase.getAccountKey(), productEntries);
			}

			productEntries.add(developerProductEntry);
		}
		%>

		<div class="container-fluid-1280">
			<aui:row>
				<aui:col width="<%= 30 %>">
					<aui:select label="" name="accountEntryId" onChange='<%= renderResponse.getNamespace() + "selectAccountEntry(this.value);" %>'>
						<aui:option disabled="<%= true %>" label="project" selected="<%= true %>" />

						<%
						for (AccountEntry accountEntry : accountEntries) {
						%>

							<aui:option label="<%= accountEntry.getName() %>" value="<%= accountEntry.getAccountEntryId() %>" />

						<%
						}
						%>

					</aui:select>
				</aui:col>

				<aui:col width="<%= 30 %>">
					<aui:select label="" name="productEntryId" onChange='<%= renderResponse.getNamespace() + "selectProduct();" %>'>
						<aui:option disabled="<%= true %>" label="product" selected="<%= true %>" value="" />
					</aui:select>
				</aui:col>

				<aui:col width="<%= 20 %>">
					<aui:select label="" name="productMinorVersion">
						<aui:option disabled="<%= true %>" label="version" selected="<%= true %>" value="" />
					</aui:select>
				</aui:col>

				<aui:col width="<%= 20 %>">
					<aui:button id="activationKeyDownloadButton" onClick='<%= renderResponse.getNamespace() + "generateLicenseKey();" %>' primary="<%= true %>" value="download" />
				</aui:col>
			</aui:row>
		</div>

		<aui:script>
			Liferay.provide(
				window,
				'<portlet:namespace />generateLicenseKey',
				function() {
					var A = AUI();

					var accountEntryId = A.one('#<portlet:namespace />accountEntryId');
					var productEntryId = A.one('#<portlet:namespace />productEntryId');
					var productMinorVersion = A.one('#<portlet:namespace />productMinorVersion');

					if (accountEntryId && productEntryId && productMinorVersion) {
						if (!accountEntryId.val() || !productEntryId || !productMinorVersion.val()) {
							alert('<liferay-ui:message key="please-fill-out-all-required-fields" />');

							return;
						}

						<portlet:resourceURL id="generateLicenseKey" var="generateLicenseKeyURL" />

						window.location.href = '<%= generateLicenseKeyURL.toString() %>&<portlet:namespace />accountEntryId=' + accountEntryId.val() + '&<portlet:namespace />productEntryId=' + productEntryId.val() + '&<portlet:namespace />productMinorVersion=' + productMinorVersion.val();
					}
				},
				['aui-base']
			);

			Liferay.provide(
				window,
				'<portlet:namespace />init',
				function(accountEntryId) {
					var A = AUI();

					var accountEntryIdSelect = A.one('#<portlet:namespace />accountEntryId');

					accountEntryIdSelect.val(accountEntryId);

					<portlet:namespace />selectAccountEntry(accountEntryId);
				},
				['aui-base']
			);

			Liferay.provide(
				window,
				'<portlet:namespace />selectAccountEntry',
				function(accountEntryId) {
					var A = AUI();

					var productEntryIdSelect = A.one('#<portlet:namespace />productEntryId');

					if (productEntryIdSelect) {
						productEntryIdSelect.empty();

						var productEntryIdOptions = [];

						productEntryIdOptions.push('<option disabled><liferay-ui:message key="product" /></option>');

						var selectedOption = '';

						<%
						for (AccountEntry accountEntry : accountEntries) {
						%>

							if (accountEntryId == '<%= accountEntry.getAccountEntryId() %>') {

								<%
								Set<ProductEntry> productEntries = accountEntryProductEntriesMap.get(accountEntry.getKoroneikiAccountKey());

								for (ProductEntry productEntry : productEntries) {
									String productEntryRootName = null;

									if (productEntry.isCommerce()) {
										productEntryRootName = ProductEntryConstants.ROOT_COMMERCE_SUBSCRIPTION;
									}

									if (productEntry.isDXP() || productEntry.isDXPCloud() || productEntry.isLiferayPaas()) {
										productEntryRootName = ProductEntryConstants.ROOT_NAME_DXP;
									}

									if (productEntry.isPortal()) {
										productEntryRootName = ProductEntryConstants.ROOT_NAME_PORTAL;
									}
								%>

									if (selectedOption == '') {
										selectedOption = '<%= productEntry.getProductEntryId() %>';
									}

									productEntryIdOptions.push('<option value="<%= productEntry.getProductEntryId() %>"><%= productEntryRootName %></option>');

								<%
								}
								%>

							}

						<%
						}
						%>

						productEntryIdSelect.append(productEntryIdOptions.join(''));

						if (selectedOption != '') {
							productEntryIdSelect.val(selectedOption);

							<portlet:namespace />selectProduct();
						}
					}
				},
				['aui-base']
			);

			Liferay.provide(
				window,
				'<portlet:namespace />selectProduct',
				function() {
					var A = AUI();

					var productEntryIdSelect = document.getElementById('<portlet:namespace />productEntryId');

					var selectedOption = productEntryIdSelect.options[productEntryIdSelect.selectedIndex];

					var productEntryRootName = selectedOption.text;

					var productMinorVersionSelect = A.one('#<portlet:namespace />productMinorVersion');

					if (productEntryRootName == '<%= ProductEntryConstants.ROOT_COMMERCE_SUBSCRIPTION %>') {
						productMinorVersionSelect.attr('disabled', true);
					}
					else {
						productMinorVersionSelect.attr('disabled', false);
					}

					if (productMinorVersionSelect) {
						productMinorVersionSelect.empty();

						var productMinorVersionOptions = [];

						productMinorVersionOptions.push('<option disabled><liferay-ui:message key="version" /></option>');

						if (productEntryRootName == '<%= ProductEntryConstants.ROOT_COMMERCE_SUBSCRIPTION %>') {

							<%
							for (ListType productMinorVersionType : ListTypeServiceUtil.getListTypes(ProductEntryConstants.LIST_TYPE_COMMERCE_LICENSE_VERSIONS)) {
							%>

								productMinorVersionOptions.push('<option value="<%= productMinorVersionType.getListTypeId() %>"><%= LanguageUtil.get(request, productMinorVersionType.getName()) %></option>');

							<%
							}
							%>

						}
						else if (productEntryRootName == '<%= ProductEntryConstants.ROOT_NAME_DXP %>') {

							<%
							for (long productMinorVersion : digitalEnterpriseProductMinorVersions) {
								ListType productMinorVersionType = ListTypeServiceUtil.getListType(productMinorVersion);
							%>

								productMinorVersionOptions.push('<option value="<%= productMinorVersion %>"><%= LanguageUtil.get(request, productMinorVersionType.getName()) %></option>');

							<%
							}
							%>

						}
						else if (productEntryRootName == '<%= ProductEntryConstants.ROOT_NAME_PORTAL %>') {

							<%
							for (long productMinorVersion : portalProductMinorVersions) {
								ListType productMinorVersionType = ListTypeServiceUtil.getListType(productMinorVersion);
							%>

								productMinorVersionOptions.push('<option value="<%= productMinorVersion %>"><%= LanguageUtil.get(request, productMinorVersionType.getName()) %></option>');

							<%
							}
							%>

						}

						productMinorVersionSelect.append(productMinorVersionOptions.join(''));
					}
				},
				['aui-base']
			);

			<c:if test="<%= accountEntries.size() == 1 %>">

				<%
				AccountEntry accountEntry = accountEntries.first();
				%>

				<portlet:namespace />init(<%= accountEntry.getAccountEntryId() %>);
			</c:if>
		</aui:script>
	</c:otherwise>
</c:choose>