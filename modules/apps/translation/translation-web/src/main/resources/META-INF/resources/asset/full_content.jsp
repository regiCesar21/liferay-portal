<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewTranslationDisplayContext viewTranslationDisplayContext = (ViewTranslationDisplayContext)request.getAttribute(ViewTranslationDisplayContext.class.getName());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<clay:container-fluid
	cssClass="container-view"
>
	<div class="sheet translate-body-form">
		<clay:row>
			<clay:col
				md="6"
			>

				<%
				String sourceLanguageIdTitle = viewTranslationDisplayContext.getLanguageIdTitle(viewTranslationDisplayContext.getSourceLanguageId());
				%>

				<clay:icon
					symbol="<%= StringUtil.toLowerCase(sourceLanguageIdTitle) %>"
				/>

				<span class="ml-1"> <%= sourceLanguageIdTitle %> </span>

				<div class="separator"><!-- --></div>
			</clay:col>

			<clay:col
				md="6"
			>

				<%
				String targetLanguageIdTitle = viewTranslationDisplayContext.getLanguageIdTitle(viewTranslationDisplayContext.getTargetLanguageId());
				%>

				<clay:icon
					symbol="<%= StringUtil.toLowerCase(targetLanguageIdTitle) %>"
				/>

				<span class="ml-1"> <%= targetLanguageIdTitle %> </span>

				<div class="separator"><!-- --></div>
			</clay:col>
		</clay:row>

		<%
		for (InfoFieldSetEntry infoFieldSetEntry : viewTranslationDisplayContext.getInfoFieldSetEntries()) {
			List<InfoField> infoFields = viewTranslationDisplayContext.getInfoFields(infoFieldSetEntry);

			if (ListUtil.isEmpty(infoFields)) {
				continue;
			}

			String infoFieldSetLabel = viewTranslationDisplayContext.getInfoFieldSetLabel(infoFieldSetEntry, locale);
		%>

			<c:if test="<%= Validator.isNotNull(infoFieldSetLabel) %>">
				<clay:row>
					<clay:col
						md="6"
					>
						<div class="fieldset-title">
							<%= infoFieldSetLabel %>
						</div>
					</clay:col>

					<clay:col
						md="6"
					>
						<div class="fieldset-title">
							<%= infoFieldSetLabel %>
						</div>
					</clay:col>
				</clay:row>
			</c:if>

			<%
			for (InfoField<TextInfoFieldType> infoField : infoFields) {
				boolean html = viewTranslationDisplayContext.getBooleanValue(infoField, TextInfoFieldType.HTML);
				String label = viewTranslationDisplayContext.getInfoFieldLabel(infoField);
				boolean multiline = viewTranslationDisplayContext.getBooleanValue(infoField, TextInfoFieldType.MULTILINE);
			%>

				<clay:row>
					<clay:col
						md="6"
					>

						<%
						String sourceContent = viewTranslationDisplayContext.getStringValue(infoField, viewTranslationDisplayContext.getSourceLocale());
						String sourceContentDir = LanguageUtil.get(viewTranslationDisplayContext.getSourceLocale(), "lang.dir");
						%>

						<c:choose>
							<c:when test="<%= html %>">
								<label class="control-label">
									<%= label %>
								</label>

								<div class="translate-editor-preview" dir="<%= sourceContentDir %>">
									<%= sourceContent %>
								</div>
							</c:when>
							<c:otherwise>
								<aui:input dir="<%= sourceContentDir %>" label="<%= label %>" name="<%= label %>" readonly="true" tabIndex="-1" type='<%= multiline ? "textarea" : "text" %>' value="<%= sourceContent %>" />
							</c:otherwise>
						</c:choose>
					</clay:col>

					<clay:col
						md="6"
					>

						<%
						String targetContent = viewTranslationDisplayContext.getStringValue(infoField, viewTranslationDisplayContext.getTargetLocale());
						%>

						<c:choose>
							<c:when test="<%= html %>">
								<label class="control-label">
									<%= label %>
								</label>

								<div class="translate-editor-preview" dir="<%= LanguageUtil.get(viewTranslationDisplayContext.getTargetLocale(), "lang.dir") %>">
									<%= targetContent %>
								</div>
							</c:when>
							<c:otherwise>
								<aui:input dir='<%= LanguageUtil.get(viewTranslationDisplayContext.getTargetLocale(), "lang.dir") %>' label="<%= label %>" name="<%= label %>" readonly="true" tabIndex="-1" type='<%= multiline ? "textarea" : "text" %>' value="<%= targetContent %>" />
							</c:otherwise>
						</c:choose>
					</clay:col>
				</clay:row>

		<%
			}
		}
		%>

	</div>
</clay:container-fluid>