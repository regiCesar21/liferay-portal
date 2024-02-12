<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String userSQL = (String)request.getAttribute("userSQL");

String userParam = ParamUtil.getString(request, "userParam");
String firstName = ParamUtil.getString(request, "firstName");
String middleName = ParamUtil.getString(request, "middleName");
String lastName = ParamUtil.getString(request, "lastName");
String screenName = ParamUtil.getString(request, "screenName");
String emailAddress = ParamUtil.getString(request, "emailAddress");

String callback = ParamUtil.getString(request, "callback");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/license/select_user");
portletURL.setParameter("userParam", userParam);
portletURL.setParameter("callback", callback);
%>

<c:if test="<%= OrganizationLocalServiceUtil.hasUserOrganization(user.getUserId(), OSBCustomerConstants.ORGANIZATION_LIFERAY_INC_ID) %>">
	<aui:form action="<%= portletURL %>" method="post" name="selectUserFm">
		<div class="unit">
			<div class="unit-content">
				<liferay-ui:tabs
					names="users"
				/>

				<%@ include file="/common/user_search_inputs.jspf" %>

				<%
				LinkedHashMap<String, Object> userParams = new LinkedHashMap();

				userParams.put(userParam, new CustomSQLParam(userSQL, StringPool.BLANK));
				%>

				<liferay-ui:search-container
					emptyResultsMessage="no-users-were-found"
					id="usersSearchContainer"
					iteratorURL="<%= portletURL %>"
					searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
				>

					<%
					UserDisplayTerms searchTerms = (UserDisplayTerms)searchContainer.getSearchTerms();

					if (!searchTerms.isAdvancedSearch()) {
						searchContainer.setResults(UserLocalServiceUtil.search(themeDisplay.getCompanyId(), searchTerms.getKeywords(), WorkflowConstants.STATUS_ANY, userParams, searchContainer.getStart(), searchContainer.getEnd(), new UserFirstNameComparator(true)));
						searchContainer.setTotal(UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), searchTerms.getKeywords(), WorkflowConstants.STATUS_ANY, userParams));
					}
					else {
						searchContainer.setResults(UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName, middleName, lastName, screenName, emailAddress, WorkflowConstants.STATUS_ANY, userParams, true, searchContainer.getStart(), searchContainer.getEnd(), new UserFirstNameComparator(true)));
						searchContainer.setTotal(UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), firstName, middleName, lastName, screenName, emailAddress, WorkflowConstants.STATUS_ANY, userParams, true));
					}
					%>

					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.User"
						keyProperty="userId"
						modelVar="curUser"
					>

						<%
						StringBundler sb = new StringBundler(8);

						sb.append("javascript:opener.");
						sb.append(renderResponse.getNamespace());
						sb.append(callback);
						sb.append("('");
						sb.append(curUser.getUserId());
						sb.append("', '");
						sb.append(UnicodeFormatter.toString(curUser.getFullName()));
						sb.append("'); window.close();");

						String rowHREF = sb.toString();
						%>

						<liferay-ui:search-container-column-text
							href="<%= rowHREF %>"
							name="name"
							value="<%= HtmlUtil.escape(curUser.getFullName()) %>"
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowHREF %>"
							name="screen-name"
							value="<%= HtmlUtil.escape(curUser.getScreenName()) %>"
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowHREF %>"
							name="email-address"
							property="emailAddress"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>
		</div>
	</aui:form>
</c:if>