<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long licenseKeySetId = ParamUtil.getLong(request, "licenseKeySetId");

LicenseKeySet licenseKeySet = LicenseKeySetLocalServiceUtil.fetchLicenseKeySet(licenseKeySetId);

String koroneikiAccountKey = BeanParamUtil.getString(licenseKeySet, request, "koroneikiAccountKey");

Account koroneikiAccount = null;

boolean addLicensePermission = false;

if (Validator.isNotNull(koroneikiAccountKey)) {
	koroneikiAccount = accountWebService.getAccount(koroneikiAccountKey);

	addLicensePermission = accountPermission.contains(permissionChecker, koroneikiAccount, OSBActionKeys.ADD_LICENSE);
}

long productEntryId = ParamUtil.getLong(request, "productEntryId");

ProductEntry productEntry = ProductEntryLocalServiceUtil.fetchProductEntry(productEntryId);

int productVersion = ParamUtil.getInteger(request, "productVersion");

long licenseEntryId = ParamUtil.getLong(request, "licenseEntryId");

LicenseEntry licenseEntry = LicenseEntryLocalServiceUtil.fetchLicenseEntry(licenseEntryId);

boolean hasUpdateAdmin = false;

if (RoleLocalServiceUtil.hasUserRole(user.getUserId(), OSBCustomerConstants.ROLE_OSB_ACCOUNT_ADMIN_ID) || RoleLocalServiceUtil.hasUserRole(user.getUserId(), OSBCustomerConstants.ROLE_OSB_ADMINISTRATOR_ID)) {
	hasUpdateAdmin = true;
}
%>

<div class="container-fluid-1280">
	<aui:row>
		<h1>
			<liferay-ui:message key="generate-new-license" />
		</h1>

		<portlet:renderURL var="updateLicenseKeyURL">
			<portlet:param name="mvcPath" value="/license/edit_license_key.jsp" />
		</portlet:renderURL>

		<aui:form action="<%= updateLicenseKeyURL %>" cssClass="col-md-12" method="post">
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
			<aui:input name="licenseKeySetId" type="hidden" value="<%= licenseKeySetId %>" />
			<aui:input name="koroneikiProductKey" type="hidden" value="" />
			<aui:input name="koroneikiProductPurchaseKey" type="hidden" value="" />

			<aui:button-row cssClass="pull-right">
				<aui:button cssClass="btn-sm" onClick="<%= backURL %>" value="back-to-previous-page" />
			</aui:button-row>

			<aui:col md="12">
				<h2 class="control-label">
					<liferay-ui:message key="account" />
				</h2>

				<c:choose>
					<c:when test="<%= licenseKeySet != null %>">
						<span class="semi-bold"><%= HtmlUtil.escape(koroneikiAccount.getName()) %></span>

						<aui:input label="" name="koroneikiAccountKey" type="hidden" value="<%= koroneikiAccount.getKey() %>" />
					</c:when>
					<c:when test="<%= hasUpdateAdmin %>">
						<span class="account-entry-name semi-bold" id="<portlet:namespace />koroneikiAccountName"><%= (koroneikiAccount != null) ? HtmlUtil.escape(koroneikiAccount.getName()) : "" %></span>

						<input class="btn btn-default select-account-entry" onClick="var accountWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/license/select_account.jsp" /><portlet:param name="callback" value="selectAccount" /></portlet:renderURL>', 'account', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); accountWindow.focus();" type="button" value="<liferay-ui:message key="select" />" />

						<aui:input name="koroneikiAccountKey" type="hidden" value='<%= (koroneikiAccount != null) ? koroneikiAccount.getKey() : "" %>' />
					</c:when>
					<c:otherwise>

						<%
						StringBundler sb = new StringBundler(5);

						sb.append("status eq '");
						sb.append(Account.Status.ACTIVE);
						sb.append("' and workerContactUuids/any(s:s eq '");
						sb.append(user.getUuid());
						sb.append("')");

						List<Account> accounts = accountWebService.search(StringPool.BLANK, sb.toString(), 1, 1000, StringPool.BLANK);
						%>

						<c:choose>
							<c:when test="<%= accounts.size() == 1 %>">

								<%
								koroneikiAccount = accounts.get(0);

								koroneikiAccountKey = koroneikiAccount.getKey();

								addLicensePermission = accountPermission.contains(permissionChecker, koroneikiAccount, OSBActionKeys.ADD_LICENSE);
								%>

								<span class="semi-bold"><%= HtmlUtil.escape(koroneikiAccount.getName()) %></span>

								<aui:input label="" name="koroneikiAccountKey" type="hidden" value="<%= koroneikiAccountKey %>" />
							</c:when>
							<c:otherwise>

								<%
								String taglibOnChange = renderResponse.getNamespace() + "updateLicenseKey('', '', '');";
								%>

								<aui:select label="" name="koroneikiAccountKey" onChange="<%= taglibOnChange %>">
									<aui:option value="" />

									<%
									for (Account curKoroneikiAccount : accounts) {
									%>

										<aui:option label="<%= curKoroneikiAccount.getName() %>" selected="<%= koroneikiAccountKey.equals(curKoroneikiAccount.getKey()) %>" value="<%= curKoroneikiAccount.getKey() %>" />

									<%
									}
									%>

								</aui:select>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</aui:col>

			<aui:col md="12">
				<h2 class="control-label">
					<liferay-ui:message key="product" />
				</h2>

				<%
				String taglibOnChange = renderResponse.getNamespace() + "updateLicenseKey(this.value, '', '');";
				%>

				<aui:select label="" name="productEntryId" onChange="<%= taglibOnChange %>">
					<aui:option value="" />

					<c:if test="<%= koroneikiAccount != null %>">

						<%
						List<ProductEntry> productEntries = ProductEntryLocalServiceUtil.getProductEntries(true);

						List<String> koroneikiProductKeys = ListUtil.toList(productEntries, ProductEntry.KORONEIKI_PRODUCT_KEY_ACCESSOR);

						String filter = "accountKey eq '" + koroneikiAccount.getKey() + "' and (productKey eq '" + StringUtil.merge(koroneikiProductKeys, "' or productKey eq '") + "')";

						List<ProductPurchaseView> productPurchaseViews = productPurchaseViewWebService.getProductPurchaseViews(StringPool.BLANK, filter, 1, 1000, StringPool.BLANK);

						List<ProductEntry> purchasedProductEntries = new ArrayList<ProductEntry>();
						List<ProductEntry> otherProductEntries = new ArrayList<ProductEntry>();

						for (ProductEntry curProductEntry : productEntries) {
							boolean purchased = false;

							String koroneikiProductKey = curProductEntry.getKoroneikiProductKey();

							for (ProductPurchaseView productPurchaseView : productPurchaseViews) {
								Product product = productPurchaseView.getProduct();

								if (koroneikiProductKey.equals(product.getKey()) && ArrayUtil.isNotEmpty(productPurchaseView.getProductPurchases())) {
									purchased = true;

									break;
								}
							}

							if (purchased) {
								purchasedProductEntries.add(curProductEntry);
							}
							else if (hasUpdateAdmin) {
								otherProductEntries.add(curProductEntry);
							}
						}
						%>

						<optgroup label="<liferay-ui:message key="purchased" />">
							<c:if test="<%= !purchasedProductEntries.isEmpty() %>">

								<%
								for (ProductEntry curProductEntry : purchasedProductEntries) {
								%>

									<aui:option label="<%= curProductEntry.getName() %>" selected="<%= curProductEntry.getProductEntryId() == productEntryId %>" value="<%= curProductEntry.getProductEntryId() %>" />

								<%
								}
								%>

							</c:if>
						</optgroup>

						<c:if test="<%= !otherProductEntries.isEmpty() %>">
							<optgroup label="<liferay-ui:message key="other" />">

								<%
								for (ProductEntry curProductEntry : otherProductEntries) {
								%>

									<aui:option label="<%= curProductEntry.getName() %>" selected="<%= curProductEntry.getProductEntryId() == productEntryId %>" value="<%= curProductEntry.getProductEntryId() %>" />

								<%
								}
								%>

							</optgroup>
						</c:if>
					</c:if>
				</aui:select>
			</aui:col>

			<aui:col md="6">
				<h2 class="control-label">
					<liferay-ui:message key="choose-version" />
				</h2>

				<%
				String taglibOnChange = renderResponse.getNamespace() + "updateLicenseKey(document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "productEntryId.value, this.value, '');";
				%>

				<aui:select label="" name="productVersion" onChange="<%= taglibOnChange %>">
					<aui:option value="" />

					<c:if test="<%= productEntry != null %>">

						<%
						List<ListType> productVersionTypes = productEntry.getAllLicenseVersionsListTypes();

						String previousNamePrefix = StringPool.BLANK;

						for (ListType productVersionType : productVersionTypes) {
							if ((productVersionType.getListTypeId() == ProductEntryConstants.PORTAL_VERSION_4_4_0) || (productVersionType.getListTypeId() == ProductEntryConstants.PORTAL_VERSION_OTHER)) {
								continue;
							}

							String name = productVersionType.getName();

							String namePrefix = StringPool.BLANK;

							if (name.length() >= 3) {
								namePrefix = name.substring(0, 3);
							}
						%>

							<c:if test="<%= Validator.isNotNull(previousNamePrefix) && !previousNamePrefix.equals(namePrefix) %>">
								<aui:option disabled="<%= true %>">--------</aui:option>
							</c:if>

							<aui:option label="<%= productVersionType.getName() %>" selected="<%= productVersionType.getListTypeId() == productVersion %>" value="<%= productVersionType.getListTypeId() %>" />

						<%
							previousNamePrefix = namePrefix;
						}
						%>

					</c:if>
				</aui:select>
			</aui:col>

			<aui:col md="6">
				<h2 class="control-label">
					<liferay-ui:message key="type" />
				</h2>

				<aui:select label="" name="licenseEntryId" onChange='<%= renderResponse.getNamespace() + "updateLicenseKey(document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "productEntryId.value, document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "productVersion.value, this.value);" %>'>
					<aui:option value="" />

					<c:if test="<%= productVersion > 0 %>">

						<%
						List<LicenseEntry> licenseEntries = LicenseEntryLocalServiceUtil.getLicenseEntries(productEntryId, productVersion);

						for (LicenseEntry curLicenseEntry : licenseEntries) {
						%>

							<aui:option selected="<%= curLicenseEntry.getLicenseEntryId() == licenseEntryId %>" value="<%= curLicenseEntry.getLicenseEntryId() %>"><%= curLicenseEntry.getName() %> (<%= LanguageUtil.get(request, curLicenseEntry.getType()) %>)</aui:option>

						<%
						}
						%>

					</c:if>
				</aui:select>
			</aui:col>

			<aui:col md="12">
				<h2 class="control-label">
					<liferay-ui:message key="choose-purchase" />
				</h2>

				<c:if test="<%= Validator.isNotNull(koroneikiAccountKey) && (productEntry != null) && (productVersion > 0) && (licenseEntry != null) %>">

					<%
					List<Object> productPurchaseDisplays = new ArrayList<Object>();

					ProductPurchaseView productPurchaseView = productPurchaseViewWebService.fetchProductPurchaseView(koroneikiAccountKey, productEntry.getKoroneikiProductKey());

					Map<String, List<ProductConsumption>> productConsumptionsMap = new HashMap<String, List<ProductConsumption>>();

					if (productPurchaseView != null) {
						if (productPurchaseView.getProductConsumptions() != null) {
							for (ProductConsumption productConsumption : productPurchaseView.getProductConsumptions()) {
								List<ProductConsumption> curProductConsumptions = productConsumptionsMap.get(productConsumption.getProductPurchaseKey());

								if (curProductConsumptions == null) {
									curProductConsumptions = new ArrayList<ProductConsumption>();

									productConsumptionsMap.put(productConsumption.getProductPurchaseKey(), curProductConsumptions);
								}

								curProductConsumptions.add(productConsumption);
							}
						}

						if (productPurchaseView.getProductPurchases() != null) {
							for (ProductPurchase productPurchase : productPurchaseView.getProductPurchases()) {
								productPurchaseDisplays.add(new ProductPurchaseDisplay(request, productPurchase, productConsumptionsMap.get(productPurchase.getKey())));
							}
						}
					}

					productPurchaseDisplays.add((Object)null);
					%>

					<liferay-ui:search-container
						headerNames="start-date,expiration-date,instance-size,license-keys-generated,,"
						total="<%= productPurchaseDisplays.size() %>"
					>
						<liferay-ui:search-container-results
							results="<%= productPurchaseDisplays %>"
						/>

						<liferay-ui:search-container-row
							className="java.lang.Object"
							modelVar="resultRow"
						>

							<%
							ProductPurchaseDisplay productPurchaseDisplay = null;

							if (resultRow instanceof ProductPurchaseDisplay) {
								productPurchaseDisplay = (ProductPurchaseDisplay)resultRow;
							}

							String productPurchaseKey = StringPool.BLANK;

							if (productPurchaseDisplay != null) {
								productPurchaseKey = productPurchaseDisplay.getKey();
							}

							Calendar startDateCal = Calendar.getInstance(timeZone, locale);

							if ((productPurchaseDisplay != null) && (productPurchaseDisplay.getStartDate() != null)) {
								startDateCal.setTime(productPurchaseDisplay.getStartDate());
							}

							Calendar expirationDateCal = Calendar.getInstance(timeZone, locale);

							String licenseEntryType = licenseEntry.getType();

							if (licenseEntryType.equals(LicenseEntryConstants.TYPE_DEVELOPER) && (productPurchaseDisplay != null) && (productPurchaseDisplay.getEndDate() != null)) {
								expirationDateCal.setTime(productPurchaseDisplay.getEndDate());
							}
							else if (productPurchaseDisplay != null) {
								expirationDateCal.add(Calendar.YEAR, 100);
							}
							else {
								expirationDateCal.add(Calendar.YEAR, 1);
							}

							String rowHREF = null;

							if (addLicensePermission) {
								StringBundler sb = new StringBundler(5);

								sb.append("javascript:");
								sb.append(renderResponse.getNamespace());
								sb.append("selectProductPurchase('");
								sb.append(productPurchaseKey);
								sb.append("');");

								rowHREF = sb.toString();
							}
							%>

							<liferay-ui:search-container-column-text
								name="start-date"
							>
								<c:choose>
									<c:when test="<%= hasUpdateAdmin %>">
										<liferay-ui:input-date
											dayParam='<%= productPurchaseKey + "startDateDay" %>'
											dayValue="<%= startDateCal.get(Calendar.DAY_OF_MONTH) %>"
											disabled="<%= false %>"
											firstDayOfWeek="<%= startDateCal.getFirstDayOfWeek() %>"
											formName="fm"
											monthParam='<%= productPurchaseKey + "startDateMonth" %>'
											monthValue="<%= startDateCal.get(Calendar.MONTH) %>"
											yearParam='<%= productPurchaseKey + "startDateYear" %>'
											yearValue="<%= startDateCal.get(Calendar.YEAR) %>"
										/>
									</c:when>
									<c:otherwise>
										<%= longDateFormatDate.format(startDateCal.getTime()) %>

										<aui:input name='<%= productPurchaseKey + "startDateDay" %>' type="hidden" value="<%= startDateCal.get(Calendar.DAY_OF_MONTH) %>" />
										<aui:input name='<%= productPurchaseKey + "startDateMonth" %>' type="hidden" value="<%= startDateCal.get(Calendar.MONTH) %>" />
										<aui:input name='<%= productPurchaseKey + "startDateYear" %>' type="hidden" value="<%= startDateCal.get(Calendar.YEAR) %>" />
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								name="expiration-date"
							>
								<c:choose>
									<c:when test="<%= hasUpdateAdmin %>">
										<liferay-ui:input-date
											dayParam='<%= productPurchaseKey + "expirationDateDay" %>'
											dayValue="<%= expirationDateCal.get(Calendar.DAY_OF_MONTH) %>"
											disabled="<%= false %>"
											firstDayOfWeek="<%= expirationDateCal.getFirstDayOfWeek() %>"
											formName="fm"
											monthParam='<%= productPurchaseKey + "expirationDateMonth" %>'
											monthValue="<%= expirationDateCal.get(Calendar.MONTH) %>"
											yearParam='<%= productPurchaseKey + "expirationDateYear" %>'
											yearValue="<%= expirationDateCal.get(Calendar.YEAR) %>"
										/>
									</c:when>
									<c:otherwise>
										<%= longDateFormatDate.format(expirationDateCal.getTime()) %>

										<aui:input name='<%= productPurchaseKey + "expirationDateDay" %>' type="hidden" value="<%= expirationDateCal.get(Calendar.DAY_OF_MONTH) %>" />
										<aui:input name='<%= productPurchaseKey + "expirationDateMonth" %>' type="hidden" value="<%= expirationDateCal.get(Calendar.MONTH) %>" />
										<aui:input name='<%= productPurchaseKey + "expirationDateYear" %>' type="hidden" value="<%= expirationDateCal.get(Calendar.YEAR) %>" />
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								name="instance-size"
							>
								<c:choose>
									<c:when test="<%= productPurchaseDisplay != null %>">
										<%= productPurchaseDisplay.getSizing() %>

										<aui:input name='<%= productPurchaseDisplay.getKey() + "sizing" %>' type="hidden" value="<%= productPurchaseDisplay.getSizing() %>" />
									</c:when>
									<c:otherwise>
										<aui:select label="" name="sizing">
											<aui:option label="1" value="1" />
											<aui:option label="2" value="2" />
											<aui:option label="3" value="3" />
											<aui:option label="4" value="4" />
										</aui:select>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								name="license-keys-generated"
							>
								<c:choose>
									<c:when test="<%= productPurchaseDisplay != null %>">
										<%= productPurchaseDisplay.getLicenseKeysGenerated() %>
									</c:when>
									<c:otherwise>
										<%= productConsumptionWebService.searchCount("accountKey eq '" + koroneikiAccountKey + "' and productKey eq '" + productEntry.getKoroneikiProductKey() + "' and productPurchaseKey eq null") %>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text>
								<c:if test="<%= addLicensePermission %>">
									<aui:button onClick="<%= rowHREF %>" value="choose" />
								</c:if>
							</liferay-ui:search-container-column-text>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							markupView="lexicon"
							paginate="<%= false %>"
							resultRowSplitter="<%= new ProductPurchaseResultRowSplitter() %>"
						/>
					</liferay-ui:search-container>
				</c:if>
			</aui:col>
		</aui:form>
	</aui:row>
</div>

<aui:script>
	function <portlet:namespace />selectAccount(koroneikiAccountKey, koroneikiAccountName) {
		document.<portlet:namespace />fm.<portlet:namespace />koroneikiAccountKey.value = koroneikiAccountKey;

		document.getElementById('<portlet:namespace />koroneikiAccountName').innerHTML = koroneikiAccountName;

		<portlet:namespace />updateLicenseKey('', '', '');
	}

	function <portlet:namespace />selectProductPurchase(koroneikiProductPurchaseKey) {
		document.<portlet:namespace />fm.<portlet:namespace />koroneikiProductPurchaseKey.value = koroneikiProductPurchaseKey;

		submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="mvcPath" value="/license/edit_license_key_details.jsp" /></portlet:renderURL>');
	}

	function <portlet:namespace />updateLicenseKey(productEntryId, productVersion, licenseEntryId) {
		var A = AUI();

		A.one('#<portlet:namespace />productEntryId').val(productEntryId);
		A.one('#<portlet:namespace />productVersion').val(productVersion);
		A.one('#<portlet:namespace />licenseEntryId').val(licenseEntryId);

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>