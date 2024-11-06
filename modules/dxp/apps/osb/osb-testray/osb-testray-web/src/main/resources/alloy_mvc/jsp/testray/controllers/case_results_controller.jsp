<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/testray/controllers/init.jspf" %>
<%@ include file="/alloy_mvc/jsp/util/testray_case_result_indexer.jspf" %>
<%@ include file="/alloy_mvc/jsp/util/testray_case_result_scheduler_message_listener.jspf" %>

<%!
public static class AlloyControllerImpl extends TestrayAlloyControllerImpl {

	public AlloyControllerImpl() {
		setAlloyServiceInvokerClass(TestrayCaseResult.class);
		setPermissioned(true);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"assignedUserId", "attachments", "errors", "issues", "statusLabel", "testrayCaseId", "testrayRunId", "warnings"}, parameterTypes = {Long.class, String.class, String.class, String.class, Integer.class, Long.class, Long.class, String.class})
	public void add() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateAdd();

		TestrayCaseResult testrayCaseResult = TestrayCaseResultLocalServiceUtil.createTestrayCaseResult(0);

		long assignedUserId = ParamUtil.getLong(request, "assignedUserId");

		testrayCaseResult.setAssignedUserId(assignedUserId);

		String errors = ParamUtil.getString(request, "errors");

		testrayCaseResult.setErrors(errors);

		String statusLabel = ParamUtil.getString(request, "statusLabel");

		testrayCaseResult.setStatus(TestrayCaseResultConstants.getLabelStatus(statusLabel));

		long testrayRunId = ParamUtil.getLong(request, "testrayRunId");

		TestrayRun testrayRun = TestrayRunLocalServiceUtil.getTestrayRun(testrayRunId);

		testrayCaseResult.setTestrayBuildId(testrayRun.getTestrayBuildId());

		long testrayCaseId = ParamUtil.getLong(request, "testrayCaseId");

		testrayCaseResult.setTestrayCaseId(testrayCaseId);

		testrayCaseResult.setTestrayRunId(testrayRunId);

		updateModelIgnoreRequest(testrayCaseResult);

		String attachments = ParamUtil.getString(request, "attachments");

		TestrayCaseResultUtil.putTestrayAttachments(this, testrayCaseResult, TestrayCaseResultUtil.getTestrayCaseAttachments(attachments));

		String issues = ParamUtil.getString(request, "issues");

		TestrayCaseResultUtil.addTestrayCaseResultTestrayIssue(this, testrayCaseResult.getTestrayCaseResultId(), issues);

		String warnings = ParamUtil.getString(request, "warnings");

		TestrayCaseResultUtil.addTestrayCaseResultWarnings(this, testrayCaseResult.getTestrayCaseResultId(), ListUtil.fromString(warnings, StringPool.COMMA));

		respondWith(_getTestrayCaseResultJSONObject(testrayCaseResult));
	}

	public void addTestrayCaseResults() throws Exception {
		List<Long> testrayRunIds = new ArrayList();

		long[] testrayRunIdsArray = ParamUtil.getLongValues(request, "testrayRunIds");

		if (ArrayUtil.isNotEmpty(testrayRunIdsArray)) {
			testrayRunIds = ListUtil.toList(testrayRunIdsArray);
		}
		else {
			testrayRunIds.add(ParamUtil.getLong(request, "testrayRunId"));
		}

		long[] addTestrayCaseIds = ParamUtil.getLongValues(request, "addTestrayCaseIds");

		Set<Long> testrayCaseIds = SetUtil.fromArray(addTestrayCaseIds);

		long[] addTestraySuiteIds = ParamUtil.getLongValues(request, "addTestraySuiteIds");

		for (long testrayRunId : testrayRunIds) {
			for (long testraySuiteId : addTestraySuiteIds) {
				List<TestrayCase> testrayCases = TestrayCaseLocalServiceUtil.getTestraySuiteTestrayCases(testraySuiteId);

				for (TestrayCase testrayCase : testrayCases) {
					testrayCaseIds.add(testrayCase.getTestrayCaseId());
				}
			}

			for (long testrayCaseId : testrayCaseIds) {
				try {
					TestrayCaseResultUtil.addTestrayCaseResult(this, testrayRunId, testrayCaseId);
				}
				catch (Exception e) {
					throw new AlloyException(e.getMessage());
				}
			}

			_indexTestrayBuild(testrayRunId);
		}

		WindowState windowState = actionRequest.getWindowState();

		if (windowState.equals(LiferayWindowState.POP_UP)) {
			setOpenerSuccessMessage();

			render("close");
		}
		else {
			addSuccessMessage();

			String redirect = ParamUtil.getString(request, "redirect");

			redirectTo(redirect);
		}
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"archived", "cur", "delta", "testrayRoutineId"}, parameterTypes = {Integer.class, Integer.class, Boolean.class, Long.class})
	public void buildMetrics() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateBuildMetrics();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Map<String, Serializable> attributes = new HashMap<String, Serializable>();

		attributes.put("template", false);

		long testrayRoutineId = ParamUtil.getLong(request, "testrayRoutineId");

		TestrayRoutine testrayRoutine = TestrayRoutineLocalServiceUtil.getTestrayRoutine(testrayRoutineId);

		attributes.put("testrayProjectId", testrayRoutine.getTestrayProjectId());

		Sort[] sorts = {
			new Sort("createDate_sortable", true)
		};

		AlloySearchResult alloySearchResult = search(IndexerRegistryUtil.nullSafeGetIndexer(TestrayBuild.class), new AlloyServiceInvoker(TestrayBuild.class.getName()), request, portletRequest, null, attributes, null, sorts);

		Map<String, Serializable> testrayCaseResultProperties = new HashMap<String, Serializable>();

		int[] priorities = ParamUtil.getIntegerValues(request, "priorities");

		testrayCaseResultProperties.put("testrayCasePriority", StringUtil.merge(priorities));

		long[] testrayCaseTypeIds = ParamUtil.getLongValues(request, "testrayCaseTypeId");

		testrayCaseResultProperties.put("testrayCaseTypeId", StringUtil.merge(testrayCaseTypeIds));

		long testrayTeamId = ParamUtil.getLong(request, "testrayTeamId");

		testrayCaseResultProperties.put("testrayTeamId", testrayTeamId);

		List<TestrayBuild> testrayBuilds = TestrayUtil.getSubclassList(alloySearchResult.getBaseModels(), TestrayBuild.class);

		for (TestrayBuild testrayBuild : testrayBuilds) {
			TestrayBuildComposite testrayBuildComposite = new TestrayBuildComposite(testrayBuild, themeDisplay, testrayCaseResultProperties);

			jsonArray.put(testrayBuildComposite.getBuildMetricsJSONObject());
		}

		respondWith(jsonArray);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"caseResultsJSONArrayString"}, parameterTypes = {String.class})
	@RequireIssueEngineAuthorization
	public void bulk() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateBulk();

		JSONArray caseResultsOutputJSONArray = JSONFactoryUtil.createJSONArray();

		String caseResultsJSONArrayString = ParamUtil.getString(request, "caseResultsJSONArrayString");

		JSONArray caseResultsJSONArray = JSONFactoryUtil.createJSONArray(caseResultsJSONArrayString);

		for (int i = 0; i < caseResultsJSONArray.length(); i++) {
			JSONObject caseResultJSONObject = caseResultsJSONArray.getJSONObject(i);

			TestrayCaseResult testrayCaseResult = TestrayCaseResultUtil.fetchOrCreateTestrayCaseResult(caseResultJSONObject.getLong("testrayRunId"), caseResultJSONObject.getLong("testrayCaseId"));

			User assignedUser = UserLocalServiceUtil.fetchUser(caseResultJSONObject.getLong("assignedUserId"));

			if (assignedUser != null) {
				testrayCaseResult.setAssignedUserId(assignedUser.getUserId());
			}

			testrayCaseResult.setStatus(caseResultJSONObject.getInt("status"));

			testrayCaseResult.setErrors(caseResultJSONObject.getString("errors"));

			updateModelIgnoreRequest(testrayCaseResult);

			TestrayCaseResultUtil.putTestrayAttachments(this, testrayCaseResult, TestrayCaseResultUtil.getTestrayCaseAttachments(caseResultJSONObject.getJSONObject("attachments")));
			TestrayCaseResultUtil.addTestrayCaseResultTestrayIssue(this, testrayCaseResult.getTestrayCaseResultId(), caseResultJSONObject.getString("issues"));

			List<String> warnings = ListUtil.fromString(caseResultJSONObject.getString("warnings"), StringPool.COMMA);

			TestrayCaseResultUtil.addTestrayCaseResultWarnings(this, testrayCaseResult.getTestrayCaseResultId(), warnings);

			caseResultsOutputJSONArray.put(_getTestrayCaseResultJSONObject(testrayCaseResult));
		}

		respondWith(caseResultsOutputJSONArray);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"testrayBuildId", "testrayRunId", "testrayTeamId"}, parameterTypes = {Long.class, Long.class, Long.class})
	public void componentMetrics() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateComponentMetrics();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<TestrayComponent> testrayComponents = Collections.emptyList();

		long testrayTeamId = ParamUtil.getLong(request, "testrayTeamId");

		Map<String, Serializable> testrayCaseResultProperties = new HashMap<String, Serializable>();

		TestrayBuild testrayBuild = TestrayBuildUtil.fetchTestrayBuild(request);

		if (testrayBuild != null) {
			testrayComponents = TestrayComponentUtil.getTestrayComponents(testrayTeamId, "testrayBuildId", testrayBuild.getTestrayBuildId());

			testrayCaseResultProperties.put("testrayBuildId", testrayBuild.getTestrayBuildId());
		}

		long testrayRunId = ParamUtil.getLong(request, "testrayRunId");

		if (testrayRunId > 0) {
			testrayComponents = TestrayComponentUtil.getTestrayComponents(testrayTeamId, "testrayRunId", testrayRunId);

			testrayCaseResultProperties.put("testrayRunId", testrayRunId);
		}

		for (TestrayComponent testrayComponent : testrayComponents) {
			TestrayComponentComposite testrayComponentComposite = new TestrayComponentComposite(testrayComponent, themeDisplay, testrayCaseResultProperties);

			jsonArray.put(testrayComponentComposite.getComponentMetricsJSONObject());
		}

		respondWith(jsonArray);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"id"}, parameterTypes = {Long.class})
	public void delete() throws Exception {
		TestrayCaseResult testrayCaseResult = _fetchTestrayCaseResult();

		_validateDelete(testrayCaseResult);

		TestrayCaseResultUtil.deleteTestrayCaseResult(testrayCaseResult);

		if (isRespondingTo("json")) {
			respondWith(JSONFactoryUtil.getNullJSON());

			return;
		}

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void edit() throws Exception {
		TestrayCaseResult testrayCaseResult = _fetchTestrayCaseResult();

		_validateEdit(testrayCaseResult);

		renderRequest.setAttribute("TestrayCaseResultConstants", getConstantsBean(TestrayCaseResultConstants.class));

		TestrayCaseResultComposite testrayCaseResultComposite = new TestrayCaseResultComposite(testrayCaseResult, themeDisplay);

		renderRequest.setAttribute("testrayCaseResultComposite", testrayCaseResultComposite);

		String redirect = ParamUtil.getString(request, "redirect");

		renderRequest.setAttribute("redirect", redirect);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"id"}, parameterTypes = {Long.class})
	public void history() throws Exception {
		TestrayCaseResult testrayCaseResult = TestrayCaseResultUtil.getTestrayCaseResult(request);

		_validateHistory(testrayCaseResult);

		_setAttributes(testrayCaseResult);

		Map<String, Serializable> attributes = _getAttributes();

		if (!attributes.containsKey("environmentHash")) {
			TestrayRun testrayRun = TestrayRunLocalServiceUtil.getTestrayRun(testrayCaseResult.getTestrayRunId());

			attributes.put("environmentHash", GetterUtil.getInteger(testrayRun.getEnvironmentHash()));
		}

		if (!attributes.containsKey("testrayRoutineId")) {
			TestrayBuild testrayBuild = TestrayBuildLocalServiceUtil.getTestrayBuild(testrayCaseResult.getTestrayBuildId());

			attributes.put("testrayRoutineId", testrayBuild.getTestrayRoutineId());
		}

		attributes.put("testrayCaseId", testrayCaseResult.getTestrayCaseId());

		String orderByCol = ParamUtil.getString(request, "orderByCol", "createDate_sortable");
		String orderByType = ParamUtil.getString(request, "orderByType", "desc");

		List<TestrayCaseResultComposite> testrayCaseResultComposites = _search(attributes, orderByCol, orderByType);

		if (isRespondingTo("json")) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (TestrayCaseResultComposite testrayCaseResultComposite : testrayCaseResultComposites) {
				jsonArray.put(testrayCaseResultComposite.getJSONObject());
			}

			respondWith(jsonArray);

			return;
		}

		renderRequest.setAttribute("testrayCaseResultComposites", testrayCaseResultComposites);

		renderRequest.setAttribute("testrayRoutines", TestrayRoutineUtil.getTestrayRoutines(testrayProjectId));

		List<TestrayTeam> testrayTeams = TestrayTeamUtil.getTestrayTeams(testrayProjectId);

		renderRequest.setAttribute("testrayTeams", testrayTeams);

		List<User> users = UserLocalServiceUtil.getUsers(themeDisplay.getCompanyId(), false, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new UserFirstNameComparator(true));

		renderRequest.setAttribute("users", users);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"results", "testrayRunId", "type"}, parameterTypes = {String.class, Long.class, String.class})
	public void importResults() throws Exception {
		List<TestrayCaseResult> testrayCaseResults = null;

		String[] resultsArray = {ParamUtil.getString(request, "results")};

		if (Validator.isNull(resultsArray[0])) {
			UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);

			File[] resultsFiles = uploadPortletRequest.getFiles("results");

			resultsArray = new String[resultsFiles.length];

			for (int i = 0; i < resultsFiles.length; i++) {
				resultsArray[i] = FileUtil.read(resultsFiles[i]);
			}
		}

		_validateImportResults(resultsArray);

		int type = ParamUtil.getInteger(request, "type");

		if (type <= 0) {
			String typeLabel = ParamUtil.getString(request, "type");

			type = TestrayAutomationConstants.getLabelType(typeLabel);
		}

		if (type == TestrayAutomationConstants.EXTERNAL_REFERENCE_TYPE_CUCUMBER) {
			JSONArray resultsJSONArray = TestrayCucumberUtil.getResultsJSONArray(resultsArray);

			long[] testrayRunIds = ParamUtil.getLongValues(request, "testrayRunIds");

			for (long testrayRunId : testrayRunIds) {
				try {
					testrayCaseResults = TestrayCucumberUtil.processImportResults(this, resultsJSONArray, testrayProjectId, TestrayRunLocalServiceUtil.getTestrayRun(testrayRunId));
				}
				catch (Exception e) {
					log.error(e, e);

					throw new AlloyException(e.getMessage(), false);
				}
			}
		}
		else if (type == TestrayAutomationConstants.EXTERNAL_REFERENCE_TYPE_POSHI) {
			testrayCaseResults = new ArrayList<TestrayCaseResult>();

			try {
				for (String resultsString : resultsArray) {
					testrayCaseResults.addAll(TestrayAutomationUtil.processAutomationResult(this, resultsString, null));
				}
			}
			catch (Exception e) {
				log.error(e, e);

				throw new AlloyException(e.getMessage(), false);
			}
		}

		if (isRespondingTo("json")) {
			respondWith(_getTestrayCaseResultsJSONArray(testrayCaseResults));

			return;
		}

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"cur", "delta", "end", "start", "testrayBuildId", "testrayCaseId", "testrayRunId", "testraySubtaskId"}, parameterTypes = {Integer.class, Integer.class, Integer.class, Integer.class, Long.class, Long.class, Long.class, String.class})
	public void index() throws Exception {
		if (isRespondingTo("json")) {
			long testrayBuildId = ParamUtil.getLong(request, "testrayBuildId");
			String testrayBuildName = ParamUtil.getString(request, "testrayBuildName");
			long testrayCaseId = ParamUtil.getLong(request, "testrayCaseId", -1);
			long testrayRunId = ParamUtil.getLong(request, "testrayRunId", -1);
			String testraySubtaskIdentifier = ParamUtil.getString(request, "testraySubtaskId");

			if (testrayCaseId >= 0) {
				TestrayCase testrayCase = TestrayCaseLocalServiceUtil.fetchTestrayCase(testrayCaseId);

				TestrayValidator.validateBaseModel(request, testrayCase, "the-case-with-id-x-does-not-exist", "testrayCaseId");

				respondWith(_getTestrayCaseResultsJSONArray("testrayCaseId", testrayCaseId));
			}
			else if (testrayBuildId > 0) {
				respondWith(_getTestrayCaseResultsJSONArray("testrayBuildId", testrayBuildId));
			}
			else if (Validator.isNotNull(testrayBuildName)) {
				TestrayBuild testrayBuild = TestrayBuildUtil.getTestrayBuild(request);

				respondWith(_getTestrayCaseResultsJSONArray("testrayBuildId", testrayBuild.getTestrayBuildId()));
			}
			else if (testrayRunId >= 0) {
				TestrayRun testrayRun = TestrayRunLocalServiceUtil.fetchTestrayRun(testrayRunId);

				TestrayValidator.validateBaseModel(request, testrayRun, "the-run-with-id-x-does-not-exist", "testrayRunId");

				respondWith(_getTestrayCaseResultsJSONArray("testrayRunId", testrayRunId));
			}
			else if (Validator.isNotNull(testraySubtaskIdentifier)) {
				TestraySubtask testraySubtask = TestraySubtaskUtil.fetchTestraySubtask(request, "testraySubtaskId");

				TestrayValidator.validateBaseModel(request, testraySubtask, "the-subtask-with-id-x-does-not-exist", "testraySubtaskId");

				int[] startAndEnd = TestrayUtil.getStartAndEnd(request, portletRequest, portletURL);

				respondWith(_getTestrayCaseResultsJSONArray(TestrayCaseResultLocalServiceUtil.getTestraySubtaskTestrayCaseResults(testraySubtask.getTestraySubtaskId(), startAndEnd[0], startAndEnd[1])));
			}

			return;
		}
		else if (isRespondingTo("csv")) {
			Map<String, Serializable> attributes = _getAttributes();

			_validateAndAddIfPresent(attributes, TestrayBuild.class, "testrayBuildId", "the-build-with-id-x-does-not-exist");
			_validateAndAddIfPresent(attributes, TestrayCase.class, "testrayCaseId", "the-case-with-id-x-does-not-exist");
			_validateAndAddIfPresent(attributes, TestrayCaseType.class, "testrayCaseTypeId", "the-case-type-with-id-x-does-not-exist");
			_validateAndAddIfPresent(attributes, TestrayComponent.class, "testrayComponentId", "the-component-with-id-x-does-not-exist");
			_validateAndAddIfPresent(attributes, TestrayProject.class, "testrayProjectId", "the-project-with-id-x-does-not-exist");
			_validateAndAddIfPresent(attributes, TestrayRoutine.class, "testrayRoutineId", "the-routine-with-id-x-does-not-exist");
			_validateAndAddIfPresent(attributes, TestrayRun.class, "testrayRunId", "the-run-with-id-x-does-not-exist");
			_validateAndAddIfPresent(attributes, TestrayTeam.class, "testrayTeamId", "the-team-with-id-x-does-not-exist");

			List<TestrayCaseResultComposite> testrayCaseResultComposites = _search(attributes, "status_sortable", "asc");

			respondWith(null, _getTestrayCaseResultsCSV(testrayCaseResultComposites));

			return;
		}

		TestrayBuild testrayBuild = TestrayBuildUtil.fetchTestrayBuild(request);

		TestrayValidator.validateTestrayBuild(request, testrayBuild);

		renderRequest.setAttribute("TestrayCaseConstants", getConstantsBean(TestrayCaseConstants.class));
		renderRequest.setAttribute("TestrayCaseResultConstants", getConstantsBean(TestrayCaseResultConstants.class));

		Map<String, Serializable> testrayCaseResultProperties = new HashMap<String, Serializable>();

		long testrayRunId = ParamUtil.getLong(request, "testrayRunId");

		if (testrayRunId > 0) {
			testrayCaseResultProperties.put("testrayRunId", testrayRunId);

			TestrayRun testrayRun = TestrayRunLocalServiceUtil.fetchTestrayRun(testrayRunId);

			renderRequest.setAttribute("testrayRunComposite", new TestrayRunComposite(testrayRun, themeDisplay, testrayCaseResultProperties));
		}

		TestrayBuildComposite testrayBuildComposite = new TestrayBuildComposite(testrayBuild, themeDisplay, testrayCaseResultProperties);

		renderRequest.setAttribute("testrayBuildComposite", testrayBuildComposite);
		renderRequest.setAttribute("testrayCaseResultReporter", testrayBuildComposite.getTestrayCaseResultReporter());

		Map<String, Serializable> attributes = _getAttributes();

		if (testrayRunId > 0) {
			attributes.put("testrayRunId", testrayRunId);
		}

		String orderByCol = ParamUtil.getString(request, "orderByCol", "status_sortable");
		String orderByType = ParamUtil.getString(request, "orderByType", "asc");

		List<TestrayCaseResultComposite> testrayCaseResultComposites = _search(attributes, orderByCol, orderByType);

		renderRequest.setAttribute("testrayCaseResultComposites", testrayCaseResultComposites);

		List<TestrayComponent> testrayComponents = TestrayComponentUtil.getTestrayComponents(0, "testrayBuildId", testrayBuild.getTestrayBuildId());

		renderRequest.setAttribute("testrayComponents", testrayComponents);

		AlloyServiceInvoker testrayCaseTypeAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCaseType.class.getName());

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(TestrayCaseTypeModelImpl.TABLE_NAME, "name", true);

		List<TestrayCaseType> testrayCaseTypes = testrayCaseTypeAlloyServiceInvoker.executeDynamicQuery(new Object[] {"groupId", themeDisplay.getScopeGroupId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayCaseTypes", testrayCaseTypes);

		AlloyServiceInvoker testrayRunAlloyServiceInvoker = new AlloyServiceInvoker(TestrayRun.class.getName());

		obc = OrderByComparatorFactoryUtil.create(TestrayRunModelImpl.TABLE_NAME, "number", true);

		List<TestrayRun> testrayRuns = testrayRunAlloyServiceInvoker.executeDynamicQuery(new Object[] {"testrayBuildId", testrayBuild.getTestrayBuildId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayRuns", testrayRuns);

		List<TestrayTeam> testrayTeams = TestrayTeamUtil.getTestrayTeams(testrayProjectId);

		renderRequest.setAttribute("testrayTeams", testrayTeams);

		List<User> users = UserLocalServiceUtil.getUsers(themeDisplay.getCompanyId(), false, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new UserFirstNameComparator(true));

		renderRequest.setAttribute("users", users);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"testrayBuildId", "testrayComponentId", "testrayRoutineId", "testrayRunId", "testrayTeamId"}, parameterTypes = {String.class, Long.class, Long.class, Long.class, Long.class})
	public void issues() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateIssues();

		JSONArray testrayIssuesJSONArray = JSONFactoryUtil.createJSONArray();

		Set<String> issues = new TreeSet<String>();

		Map<String, Serializable> attributes = new HashMap<String, Serializable>();

		TestrayBuild testrayBuild = TestrayBuildUtil.fetchTestrayBuild(request);

		if (testrayBuild != null) {
			attributes.put("testrayBuildId", testrayBuild.getTestrayBuildId());
		}

		AlloySearchResult alloySearchResult = search(attributes, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<TestrayCaseResult> testrayCaseResults = TestrayUtil.getSubclassList(alloySearchResult.getBaseModels(), TestrayCaseResult.class);

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			List<TestrayIssue> testrayIssues = TestrayIssueLocalServiceUtil.getTestrayCaseResultTestrayIssues(testrayCaseResult.getTestrayCaseResultId());

			for (TestrayIssue testrayIssue : testrayIssues) {
				issues.add(testrayIssue.getName());
			}
		}

		for (String issue : issues) {
			testrayIssuesJSONArray.put(issue);
		}

		respondWith(testrayIssuesJSONArray);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"testrayBuildId", "testrayCaseId", "testrayCaseName", "testrayComponentId", "testrayProjectId", "testrayProjectName", "testrayRoutineId", "testrayRunId", "testrayRunNumber", "testrayTeamId"}, parameterTypes = {String.class, Long.class, String.class, Long.class, Long.class, String.class, Long.class, Long.class, Integer.class, Long.class})
	public void metrics() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateMetrics();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Serializable> attributes = _getAttributes();

		TestrayBuild testrayBuild = TestrayBuildUtil.fetchTestrayBuild(request);

		if (testrayBuild != null) {
			attributes.put("testrayBuildId", testrayBuild.getTestrayBuildId());
		}

		long testrayCaseId = ParamUtil.getLong(request, "testrayCaseId");
		String testrayCaseName = ParamUtil.getString(request, "testrayCaseName");

		if ((testrayCaseId > 0) || Validator.isNotNull(testrayCaseName)) {
			TestrayCase testrayCase = TestrayCaseUtil.getTestrayCase(request);

			attributes.put("testrayCaseId", testrayCase.getTestrayCaseId());
		}

		long testrayCaseTypeId = ParamUtil.getLong(request, "testrayCaseTypeId");

		if (testrayCaseTypeId > 0) {
			attributes.put("testrayCaseTypeId", testrayCaseTypeId);
		}

		long testrayComponentId = ParamUtil.getLong(request, "testrayComponentId");

		if (testrayComponentId > 0) {
			attributes.put("testrayComponentId", testrayComponentId);
		}

		long testrayRunId = ParamUtil.getLong(request, "testrayRunId");
		int testrayRunNumber = ParamUtil.getInteger(request, "testrayRunNumber");

		if ((testrayRunId > 0) || (testrayRunNumber > 0)) {
			TestrayRun testrayRun = TestrayRunUtil.getTestrayRun(request);

			attributes.put("testrayRunId", testrayRun.getTestrayRunId());
		}

		long testrayTeamId = ParamUtil.getLong(request, "testrayTeamId");

		if (testrayTeamId > 0) {
			attributes.put("testrayTeamId", testrayTeamId);
		}

		TestrayCaseResultReporter testrayCaseResultReporter = new TestrayCaseResultReporter(themeDisplay, attributes);

		String metricsJSONString = JSONFactoryUtil.looseSerialize(testrayCaseResultReporter.getTestrayCaseResultStatusCounts());

		jsonObject.put("metrics", JSONFactoryUtil.createJSONObject(metricsJSONString));

		respondWith(jsonObject);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"testrayBuildId", "testrayRoutineId"}, parameterTypes = {String.class, Long.class})
	public void runMetrics() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateRunMetrics();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		AlloyServiceInvoker testrayRunAlloyServiceInvoker = new AlloyServiceInvoker(TestrayRun.class.getName());

		TestrayBuild testrayBuild = TestrayBuildUtil.fetchTestrayBuild(request);

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(TestrayRunModelImpl.TABLE_NAME, "number", true);

		List<TestrayRun> testrayRuns = testrayRunAlloyServiceInvoker.executeDynamicQuery(new Object[] {"testrayBuildId", testrayBuild.getTestrayBuildId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		for (TestrayRun testrayRun : testrayRuns) {
			TestrayRunComposite testrayRunComposite = new TestrayRunComposite(testrayRun, themeDisplay);

			jsonArray.put(testrayRunComposite.getRunMetricsJSONObject());
		}

		respondWith(jsonArray);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"testrayBuildId", "testrayRoutineId", "testrayRunId"}, parameterTypes = {String.class, Long.class, Long.class})
	public void teamMetrics() throws Exception {
		if (!isRespondingTo("json")) {
			return;
		}

		_validateTeamMetrics();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Long> testrayTeamIds = new ArrayList<Long>();

		Map<String, Serializable> testrayCaseResultProperties = new HashMap<String, Serializable>();

		TestrayBuild testrayBuild = TestrayBuildUtil.fetchTestrayBuild(request);

		if (testrayBuild != null) {
			testrayTeamIds = TestrayTeamUtil.getTestrayTeamIds("testrayBuildId", testrayBuild.getTestrayBuildId());

			testrayCaseResultProperties.put("testrayBuildId", testrayBuild.getTestrayBuildId());
		}

		long testrayRunId = ParamUtil.getLong(request, "testrayRunId");

		if (testrayRunId > 0) {
			testrayTeamIds = TestrayTeamUtil.getTestrayTeamIds("testrayRunId", testrayRunId);

			testrayCaseResultProperties.put("testrayRunId", testrayRunId);
		}

		List<TestrayTeamComposite> testrayTeamComposites = TestrayCompositeUtil.getComposites(testrayTeamIds, TestrayTeamComposite.class, new Class<?>[] {long.class, ThemeDisplay.class, Map.class}, new Object[] {themeDisplay, testrayCaseResultProperties});

		TestrayCompositeComparator testrayCompositeComparator = new TestrayCompositeComparator("name", true);

		Collections.sort(testrayTeamComposites, testrayCompositeComparator);

		for (TestrayTeamComposite testrayTeamComposite : testrayTeamComposites) {
			jsonArray.put(testrayTeamComposite.getTeamMetricsJSONObject());
		}

		respondWith(jsonArray);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"assignedUserId", "attachments", "comment", "id", "issues", "status"}, parameterTypes = {Long.class, String.class, String.class, Long.class, String.class, Integer.class})
	@RequireIssueEngineAuthorization
	public void update() throws Exception {
		TestrayCaseResult testrayCaseResult = _fetchTestrayCaseResult();

		_validateUpdate(testrayCaseResult);

		int status = ParamUtil.getInteger(request, "status", -1);

		if ((status != testrayCaseResult.getStatus()) && (status >= 0)) {
			TestrayCase testrayCase = TestrayCaseLocalServiceUtil.getTestrayCase(testrayCaseResult.getTestrayCaseId());
		}

		if (isRespondingTo("json")) {
			String attachments = ParamUtil.getString(request, "attachments");

			TestrayCaseResultUtil.putTestrayAttachments(this, testrayCaseResult, TestrayCaseResultUtil.getTestrayCaseAttachments(attachments));

			String body = ParamUtil.getString(request, "comment", null);

			if (body != null) {
				testrayCaseResult.setAssignedUserId(user.getUserId());

				long commentMBMessageId = TestrayUtil.updateMBMessage(themeDisplay, testrayCaseResult.getCommentMBMessageId(), TestrayCase.class.getName(), testrayCaseResult.getTestrayCaseId(), body, portletRequest);

				testrayCaseResult.setCommentMBMessageId(commentMBMessageId);
			}

			String issues = ParamUtil.getString(request, "issues", null);

			if (issues != null) {
				testrayCaseResult.setAssignedUserId(user.getUserId());

				TestrayCaseResultUtil.updateTestrayCaseResultsTestrayIssues(this, StringUtil.split(issues), testrayCaseResult.getTestrayCaseResultId(), user);
			}

			if (status >= 0) {
				testrayCaseResult.setAssignedUserId(user.getUserId());

				testrayCaseResult.setStatus(status);
			}

			long assignedUserId = ParamUtil.getLong(request, "assignedUserId", -1);

			if (assignedUserId >= 0) {
				testrayCaseResult.setAssignedUserId(assignedUserId);
			}

			updateModelIgnoreRequest(testrayCaseResult);

			respondWith(_getTestrayCaseResultJSONObject(testrayCaseResult));

			return;
		}

		String issues = ParamUtil.getString(request, "issues", null);

		TestrayCaseResultUtil.updateTestrayCaseResultsTestrayIssues(this, StringUtil.split(issues), testrayCaseResult.getTestrayCaseResultId(), user);

		String body = ParamUtil.getString(request, "comment", null);

		long commentMBMessageId = TestrayUtil.updateMBMessage(themeDisplay, testrayCaseResult.getCommentMBMessageId(), TestrayCase.class.getName(), testrayCaseResult.getTestrayCaseId(), body, portletRequest);

		updateModel(testrayCaseResult, "commentMBMessageId", commentMBMessageId, "assignedUserId", user.getUserId());

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	@RequireIssueEngineAuthorization
	public void updateStatus() throws Exception {
		TestrayCaseResult testrayCaseResult = _fetchTestrayCaseResult();

		_validateUpdateStatus(testrayCaseResult);

		int status = ParamUtil.getInteger(request, "status", -1);

		if ((status != testrayCaseResult.getStatus()) && (status >= 0)) {
			TestrayCase testrayCase = TestrayCaseLocalServiceUtil.getTestrayCase(testrayCaseResult.getTestrayCaseId());
		}

		testrayCaseResult.setStatus(status);

		if (status == TestrayCaseResultConstants.STATUS_IN_PROGRESS) {
			testrayCaseResult.setStartDate(new Date());
			testrayCaseResult.setClosedDate(null);
		}
		else if (status != TestrayCaseResultConstants.STATUS_UNTESTED) {
			testrayCaseResult.setClosedDate(new Date());
		}

		String body = ParamUtil.getString(request, "comment", null);

		if (body != null) {
			long commentMBMessageId = TestrayUtil.updateMBMessage(themeDisplay, testrayCaseResult.getCommentMBMessageId(), TestrayCase.class.getName(), testrayCaseResult.getTestrayCaseId(), body, portletRequest);

			testrayCaseResult.setCommentMBMessageId(commentMBMessageId);
		}

		String caseResultIssues = ParamUtil.getString(request, "caseResultIssues", null);

		if (caseResultIssues != null) {
			TestrayCaseResultUtil.updateTestrayCaseResultsTestrayIssues(this, StringUtil.split(caseResultIssues), testrayCaseResult.getTestrayCaseResultId(), user);
		}

		updateModelIgnoreRequest(testrayCaseResult);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void updateUser() throws Exception {
		TestrayCaseResult testrayCaseResult = _fetchTestrayCaseResult();

		_validateUpdateUser(testrayCaseResult);

		long assignedUserId = ParamUtil.getLong(request, "assignedUserId");

		testrayCaseResult.setAssignedUserId(assignedUserId);

		if ((assignedUserId == 0) && (testrayCaseResult.getStatus() == TestrayCaseResultConstants.STATUS_IN_PROGRESS)) {
			testrayCaseResult.setStartDate(null);
			testrayCaseResult.setStatus(TestrayCaseResultConstants.STATUS_UNTESTED);
		}
		else if ((assignedUserId != 0) && (testrayCaseResult.getStatus() == TestrayCaseResultConstants.STATUS_UNTESTED) && (assignedUserId == themeDisplay.getUserId())) {
			testrayCaseResult.setStartDate(new Date());
			testrayCaseResult.setClosedDate(null);
			testrayCaseResult.setStatus(TestrayCaseResultConstants.STATUS_IN_PROGRESS);
		}

		updateModelIgnoreRequest(testrayCaseResult);

		WindowState windowState = actionRequest.getWindowState();

		if (windowState.equals(LiferayWindowState.POP_UP)) {
			setOpenerSuccessMessage();

			render("close");
		}
		else {
			addSuccessMessage();

			String redirect = ParamUtil.getString(request, "redirect");

			redirectTo(redirect);
		}
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"id"}, parameterTypes = {Long.class})
	public void view() throws Exception {
		TestrayCaseResult testrayCaseResult = TestrayCaseResultUtil.getTestrayCaseResult(request);

		if (isRespondingTo("json")) {
			respondWith(_getTestrayCaseResultJSONObject(testrayCaseResult));

			return;
		}

		_setAttributes(testrayCaseResult);
	}

	@Override
	protected Indexer buildIndexer() {
		return TestrayCaseResultIndexer.getInstance();
	}

	@Override
	protected MessageListener buildSchedulerMessageListener() {
		return TestrayCaseResultSchedulerMessageListener.getInstance(this);
	}

	@Override
	protected Trigger getSchedulerTrigger() {
		return TriggerFactoryUtil.createTrigger(getSchedulerJobName(), getMessageListenerGroupName(), PortalPropsValues.TESTRAY_CRON_TRIGGER_CASE_RESULTS_CONTROLLER);
	}

	private TestrayCaseResult _fetchTestrayCaseResult() throws Exception {
		TestrayCaseResult testrayCaseResult = null;

		String id = ParamUtil.getString(request, "id");

		if (Validator.isNumber(id)) {
			testrayCaseResult = TestrayCaseResultLocalServiceUtil.fetchTestrayCaseResult(GetterUtil.getLong(id));
		}
		else if (id.indexOf(StringPool.UNDERLINE) == 0) {
			long testrayRunId = ParamUtil.getLong(request, "testrayRunId");

			Property testrayCaseIdProperty = PropertyFactoryUtil.forName("testrayCaseId");

			DynamicQuery testrayCaseResultDynamicQuery = alloyServiceInvoker.buildDynamicQuery(new Object[] {"testrayRunId", testrayRunId});

			AlloyServiceInvoker testrayCaseAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCase.class.getName());

			DynamicQuery testrayCaseDynamicQuery = testrayCaseAlloyServiceInvoker.buildDynamicQuery(new Object[] {"groupId", themeDisplay.getScopeGroupId(), "name", id.substring(1)});

			Projection testrayCaseIdProjection = ProjectionFactoryUtil.property("testrayCaseId");

			testrayCaseDynamicQuery.setProjection(testrayCaseIdProjection);

			testrayCaseResultDynamicQuery.add(testrayCaseIdProperty.in(testrayCaseDynamicQuery));

			List<TestrayCaseResult> testrayCaseResults = alloyServiceInvoker.executeDynamicQuery(testrayCaseResultDynamicQuery);

			if (!testrayCaseResults.isEmpty()) {
				testrayCaseResult = testrayCaseResults.get(0);
			}
		}

		return testrayCaseResult;
	}

	private TestrayRun _fetchTestrayRun() throws Exception {
		long testrayRunId = ParamUtil.getLong(request, "testrayRunId");

		return TestrayRunLocalServiceUtil.fetchTestrayRun(testrayRunId);
	}

	private Map<String, Serializable> _getAttributes() throws Exception {
		Map<String, Serializable> attributes = new HashMap<String, Serializable>();

		long assignedUserId = ParamUtil.getLong(request, "assignedUserId", -1);

		User assignedUser = UserLocalServiceUtil.fetchUser(assignedUserId);

		renderRequest.setAttribute("assignedUser", assignedUser);

		String comments = ParamUtil.getString(request, "comments");
		boolean commentsBlankOnly = ParamUtil.getBoolean(request, "commentsBlankOnly");

		if (commentsBlankOnly) {
			attributes.put("blankCommentMBMessage", true);
		}
		else if (Validator.isNotNull(comments)) {
			attributes.put("commentMBMessageBody", comments);
		}

		boolean errorsBlankOnly = ParamUtil.getBoolean(request, "errorsBlankOnly");

		if (errorsBlankOnly) {
			attributes.put("blankErrors", true);
			attributes.put("errors", null);
		}

		boolean issuesBlankOnly = ParamUtil.getBoolean(request, "issuesBlankOnly");

		if (issuesBlankOnly) {
			attributes.put("blankIssues", true);
			attributes.put("issues", null);
		}

		String maxCreateDateString = ParamUtil.getString(request, "maxCreateDateString");

		if (Validator.isNotNull(maxCreateDateString)) {
			Date maxCreateDate = TestrayUtil.fetchDate(maxCreateDateString);

			attributes.put("maxCreateDate", maxCreateDate.getTime());
		}

		String minCreateDateString = ParamUtil.getString(request, "minCreateDateString");

		if (Validator.isNotNull(minCreateDateString)) {
			Date minCreateDate = TestrayUtil.fetchDate(minCreateDateString);

			attributes.put("minCreateDate", minCreateDate.getTime());
		}

		String name = ParamUtil.getString(request, "name");

		if (Validator.isNotNull(name)) {
			attributes.put("testrayCaseName_sortable", name);
		}

		int[] priorities = ParamUtil.getIntegerValues(request, "priorities");

		renderRequest.setAttribute("testrayCasePriorities", ListUtil.toList(priorities));

		if (ArrayUtil.isNotEmpty(priorities)) {
			attributes.put("testrayCasePriority", StringUtil.merge(priorities));
		}

		long[] testrayCaseTypeIds = ParamUtil.getLongValues(request, "testrayCaseTypeId");

		if (ArrayUtil.isNotEmpty(testrayCaseTypeIds)) {
			attributes.put("testrayCaseTypeId", StringUtil.merge(testrayCaseTypeIds));

			renderRequest.setAttribute("testrayCaseTypeIds", ListUtil.toList(testrayCaseTypeIds));
		}

		int[] statuses = ParamUtil.getIntegerValues(request, "statuses");

		List<Integer> statusesList = ListUtil.toList(statuses);

		renderRequest.setAttribute("statuses", statusesList);
		renderRequest.setAttribute("statusLabels", TestrayUtil.getStatusLabels(request, TestrayCaseResultConstants::getStatusLabel, statusesList));

		if (ArrayUtil.isNotEmpty(statuses)) {
			attributes.put("status", StringUtil.merge(statuses));
		}

		long testrayComponentId = ParamUtil.getLong(request, "testrayComponentId");

		TestrayComponent testrayComponent = TestrayComponentLocalServiceUtil.fetchTestrayComponent(testrayComponentId);

		renderRequest.setAttribute("testrayComponent", testrayComponent);

		long[] testrayRoutineIds = ParamUtil.getLongValues(request, "testrayRoutineId");

		if (ArrayUtil.isNotEmpty(testrayRoutineIds)) {
			attributes.put("testrayRoutineId", StringUtil.merge(testrayRoutineIds));

			renderRequest.setAttribute("testrayRoutineIds", ListUtil.toList(testrayRoutineIds));
		}

		return attributes;
	}

	private JSONObject _getTestrayCaseResultJSONObject(TestrayCaseResult testrayCaseResult) throws Exception {
		return _getTestrayCaseResultJSONObject(new TestrayCaseResultComposite(testrayCaseResult, themeDisplay));
	}

	private JSONObject _getTestrayCaseResultJSONObject(TestrayCaseResultComposite testrayCaseResultComposite) throws Exception {
		testrayCaseResultComposite.setHtmlURL(TestrayUtil.getURL(this, "case_results", null, testrayCaseResultComposite.getTestrayCaseResultId()));

		return testrayCaseResultComposite.getJSONObject();
	}

	private String _getTestrayCaseResultsCSV(List<TestrayCaseResultComposite> testrayCaseResultComposites) throws Exception {
		AlloyServiceInvoker testrayFactorCategoryAlloyServiceInvoker = new AlloyServiceInvoker(TestrayFactorCategory.class.getName());

		DynamicQuery testrayFactorCategoriesDynamicQuery = testrayFactorCategoryAlloyServiceInvoker.buildDynamicQuery();

		Property testrayFactorCategoryIdProperty = PropertyFactoryUtil.forName("testrayFactorCategoryId");

		Set<Long> testrayFactorCategoryIds = new HashSet<>();

		Map<Long, List<TestrayFactor>> testrayRunTestrayFactors = new HashMap<>();

		for (TestrayCaseResultComposite testrayCaseResultComposite : testrayCaseResultComposites) {
			if (!testrayRunTestrayFactors.containsKey(testrayCaseResultComposite.getTestrayRunId())) {
				List<TestrayFactor> testrayFactors = testrayCaseResultComposite.getTestrayFactors();

				testrayRunTestrayFactors.put(testrayCaseResultComposite.getTestrayRunId(), testrayFactors);

				for (TestrayFactor testrayFactor : testrayFactors) {
					testrayFactorCategoryIds.add(testrayFactor.getTestrayFactorCategoryId());
				}
			}
		}

		if (!testrayFactorCategoryIds.isEmpty()) {
			testrayFactorCategoriesDynamicQuery.add(testrayFactorCategoryIdProperty.in(testrayFactorCategoryIds));
		}
		else {
			testrayFactorCategoriesDynamicQuery.add(testrayFactorCategoryIdProperty.eq(0L));
		}

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(TestrayFactorCategoryModelImpl.TABLE_NAME, "name", true);

		List<TestrayFactorCategory> testrayFactorCategories = testrayFactorCategoryAlloyServiceInvoker.executeDynamicQuery(testrayFactorCategoriesDynamicQuery, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		TestrayCSVObject testrayCSVObject = new TestrayCSVObject(14 + testrayFactorCategories.size(), testrayCaseResultComposites.size());

		testrayCSVObject.addLocalizedCells(request, "case-type", "priority", "team", "component", "run-number");

		for (TestrayFactorCategory testrayFactorCategory : testrayFactorCategories) {
			testrayCSVObject.addCell(testrayFactorCategory.getName());
		}

		testrayCSVObject.addLocalizedCells(request, "case-name", "assignee", "status", "issues", "errors", "comments", "case-result-url", "create-date", "last-updated-date");

		testrayCSVObject.startNewRow();

		for (TestrayCaseResultComposite testrayCaseResultComposite : testrayCaseResultComposites) {
			testrayCSVObject.addCells(testrayCaseResultComposite.getTestrayCaseTypeName(), testrayCaseResultComposite.getPriority(), testrayCaseResultComposite.getTestrayTeamName(), testrayCaseResultComposite.getTestrayComponentName(), testrayCaseResultComposite.getTestrayRunNumber());

			List<TestrayFactor> testrayFactors = testrayRunTestrayFactors.get(testrayCaseResultComposite.getTestrayRunId());

			for (TestrayFactorCategory testrayFactorCategory : testrayFactorCategories) {
				boolean cellFilled = false;

				for (TestrayFactor testrayFactor : testrayFactors) {
					if (testrayFactor.getTestrayFactorCategoryId() == testrayFactorCategory.getTestrayFactorCategoryId()) {
						testrayCSVObject.addCell(testrayFactor.getTestrayFactorOptionName());

						cellFilled = true;

						break;
					}
				}

				if (!cellFilled) {
					testrayCSVObject.addCell(StringPool.BLANK);
				}
			}

			testrayCSVObject.addCells(testrayCaseResultComposite.getTestrayCaseName(), testrayCaseResultComposite.getAssignedUserFullName());

			testrayCSVObject.addLocalizedCell(request, testrayCaseResultComposite.getStatusLabel());

			testrayCSVObject.addCells(testrayCaseResultComposite.getIssues(), testrayCaseResultComposite.getErrors(), testrayCaseResultComposite.getComment(), TestrayUtil.getURL(this, "case_results", null, testrayCaseResultComposite.getTestrayCaseResultId()), testrayCaseResultComposite.getCreateDate(), testrayCaseResultComposite.getModifiedDate());

			testrayCSVObject.startNewRow();
		}

		return testrayCSVObject.toString();
	}

	private JSONArray _getTestrayCaseResultsJSONArray(List<TestrayCaseResult> testrayCaseResults) throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			jsonArray.put(_getTestrayCaseResultJSONObject(testrayCaseResult));
		}

		return jsonArray;
	}

	private JSONArray _getTestrayCaseResultsJSONArray(String fieldName, long fieldValue) throws Exception {
		Map<String, Serializable> attributes = _getAttributes();

		if (Validator.isNotNull(fieldName)) {
			attributes.put(fieldName, fieldValue);
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<TestrayCaseResultComposite> testrayCaseResultComposites = _search(attributes, "status_sortable", "asc");

		for (TestrayCaseResultComposite testrayCaseResultComposite : testrayCaseResultComposites) {
			jsonArray.put(_getTestrayCaseResultJSONObject(testrayCaseResultComposite));
		}

		return jsonArray;
	}

	private void _indexTestrayBuild(long testrayRunId) throws Exception {
		TestrayRun testrayRun = TestrayRunLocalServiceUtil.getTestrayRun(testrayRunId);

		TestrayBuild testrayBuild = TestrayBuildLocalServiceUtil.getTestrayBuild(testrayRun.getTestrayBuildId());

		indexModel(testrayBuild);
	}

	private List<TestrayCaseResultComposite> _search(Map<String, Serializable> attributes, String orderByCol, String orderByType) throws Exception {
		String errors = null;
		String testrayCaseResultWarning = null;
		boolean manualFilter = false;

		if (attributes.containsKey("errors")) {
			errors = (String)attributes.remove("errors");
			manualFilter = true;
		}
		else if (attributes.containsKey("testrayCaseResultWarning")) {
			testrayCaseResultWarning = (String)attributes.remove("testrayCaseResultWarning");
			manualFilter = true;
		}

		renderRequest.setAttribute("orderByCol", orderByCol);
		renderRequest.setAttribute("orderByType", orderByType);

		Sort[] sorts = {
			new Sort(orderByCol, orderByType.equals("desc")),
			new Sort("status_sortable", true),
			new Sort("errors_sortable", true),
			new Sort("testrayCasePriority_sortable", true),
			new Sort("testrayComponentName_sortable", true),
			new Sort("testrayCaseName_sortable", true)
		};

		int delta = ParamUtil.getInteger(request, "delta", 200);

		renderRequest.setAttribute("delta", delta);

		int[] startAndEnd = TestrayUtil.getStartAndEnd(request, portletRequest, portletURL, 200);

		if (!manualFilter) {
			AlloySearchResult alloySearchResult = search(attributes, null, sorts, startAndEnd[0], startAndEnd[1]);

			renderRequest.setAttribute("testrayCaseResultCompositesTotal", alloySearchResult.getSize());

			List<TestrayCaseResult> testrayCaseResults = TestrayUtil.getSubclassList(alloySearchResult.getBaseModels(), TestrayCaseResult.class);

			return TestrayCompositeUtil.getComposites(testrayCaseResults, TestrayCaseResultComposite.class, new Class<?>[] {TestrayCaseResult.class, ThemeDisplay.class}, new Object[] {themeDisplay});
		}
		else {
			AlloySearchResult alloySearchResult = search(attributes, null, sorts, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			List<TestrayCaseResult> testrayCaseResults = TestrayUtil.getSubclassList(alloySearchResult.getBaseModels(), TestrayCaseResult.class);

			if (errors != null) {
				TestrayUtil.filterResultsByError(testrayCaseResults, errors);
			}

			TestrayUtil.filterTestrayCaseResultWarnings(testrayCaseResults, testrayCaseResultWarning);

			renderRequest.setAttribute("testrayCaseResultCompositesTotal", testrayCaseResults.size());

			List<TestrayCaseResultComposite> testrayCaseResultComposites = TestrayCompositeUtil.getComposites(testrayCaseResults, TestrayCaseResultComposite.class, new Class<?>[] {TestrayCaseResult.class, ThemeDisplay.class}, new Object[] {themeDisplay});

			return ListUtil.subList(testrayCaseResultComposites, startAndEnd[0], startAndEnd[1]);
		}
	}

	private void _setAttributes(TestrayCaseResult testrayCaseResult) throws Exception {
		renderRequest.setAttribute("TestrayCaseResultConstants", getConstantsBean(TestrayCaseResultConstants.class));

		TestrayCaseResultComposite testrayCaseResultComposite = new TestrayCaseResultComposite(testrayCaseResult, themeDisplay);

		renderRequest.setAttribute("testrayCaseResultComposite", testrayCaseResultComposite);

		String redirect = ParamUtil.getString(request, "redirect");

		renderRequest.setAttribute("redirect", redirect);

		renderRequest.setAttribute("testrayCaseResultWarningsCount", testrayCaseResultComposite.getTestrayCaseResultWarningsCount());
	}

	private void _validateAdd() throws Exception {
		long assignedUserId = ParamUtil.getLong(request, "assignedUserId");

		if (assignedUserId > 0) {
			TestrayValidator.validateClassIdentifier(request, User.class, "the-user-with-id-x-does-not-exist", assignedUserId);
		}

		TestrayValidator.validateClassIdentifier(request, TestrayCase.class, "testrayCaseId", "the-case-with-id-x-does-not-exist");
		TestrayValidator.validateClassIdentifier(request, TestrayRun.class, "testrayRunId", "the-run-with-id-x-does-not-exist");
	}

	private void _validateAndAddIfPresent(Map<String, Serializable> attributes, Class<?> clazz, String classPKFieldName, String errorMessage) throws Exception {
		long classPK = ParamUtil.getLong(request, classPKFieldName);

		if (classPK > 0) {
			TestrayValidator.validateClassIdentifier(request, clazz, errorMessage, classPK);

			attributes.put(classPKFieldName, classPK);
		}
	}

	private void _validateBuildMetrics() throws Exception {
		TestrayValidator.validateClassIdentifier(request, TestrayRoutine.class, "testrayRoutineId", "the-routine-with-id-x-does-not-exist");
	}

	private void _validateBulk() throws Exception {
		TestrayValidator.validateTestrayCaseResultsJSONArray(request);
	}

	private void _validateComponentMetrics() throws Exception {
		_validateMetrics();
	}

	private void _validateDelete(TestrayCaseResult testrayCaseResult) throws Exception {
		TestrayValidator.validateBaseModel(request, testrayCaseResult, "the-case-result-with-id-x-does-not-exist", "id");
	}

	private void _validateEdit(TestrayCaseResult testrayCaseResult) throws Exception {
		TestrayValidator.validateBaseModel(request, testrayCaseResult, "the-case-result-with-id-x-does-not-exist", "id");
	}

	private void _validateHistory(TestrayCaseResult testrayCaseResult) throws Exception {
		TestrayValidator.validateBaseModel(request, testrayCaseResult, "the-case-result-with-id-x-does-not-exist", "id");
	}

	private void _validateImportResults(String[] resultsArray) throws Exception {
		if (ArrayUtil.isEmpty(resultsArray)) {
			throw new AlloyException("you-must-provide-results", false);
		}
	}

	private void _validateIssues() throws Exception {
		TestrayValidator.validateTestrayBuildOrRunId(request);

		if (request.getParameter("testrayComponentId") != null) {
			TestrayValidator.validateClassIdentifier(request, TestrayComponent.class, "testrayComponentId", "the-component-with-id-x-does-not-exist");
		}
		else if (request.getParameter("testrayTeamId") != null) {
			TestrayValidator.validateClassIdentifier(request, TestrayTeam.class, "testrayTeamId", "the-team-with-id-x-does-not-exist");
		}
	}

	private void _validateMetrics() throws Exception {
		TestrayValidator.validateTestrayBuildOrRunId(request);

		if (request.getParameter("testrayComponentId") != null) {
			TestrayValidator.validateClassIdentifier(request, TestrayComponent.class, "testrayComponentId", "the-component-with-id-x-does-not-exist");
		}
		else if (request.getParameter("testrayTeamId") != null) {
			TestrayValidator.validateClassIdentifier(request, TestrayTeam.class, "testrayTeamId", "the-team-with-id-x-does-not-exist");
		}
	}

	private void _validateRunMetrics() throws Exception {
		String testrayBuildIdentifier = ParamUtil.getString(request, "testrayBuildId");

		TestrayBuild testrayBuild = TestrayBuildUtil.fetchTestrayBuild(request, testrayBuildIdentifier);

		if (testrayBuild == null) {
			TestrayValidator.validateTestrayBuildIdentifier(request, testrayBuildIdentifier);
		}
	}

	private void _validateTeamMetrics() throws Exception {
		_validateMetrics();
	}

	private void _validateUpdate(TestrayCaseResult testrayCaseResult) throws Exception {
		TestrayValidator.validateBaseModel(request, testrayCaseResult, "the-case-result-with-id-x-does-not-exist", "id");

		long assignedUserId = ParamUtil.getLong(request, "assignedUserId");

		if (assignedUserId > 0) {
			TestrayValidator.validateUser(request);
		}

		if (request.getParameter("status") != null) {
			TestrayValidator.validateStatus(request);

			_validateUpdateStatus(testrayCaseResult);
		}
	}

	private void _validateUpdateStatus(TestrayCaseResult testrayCaseResult) throws Exception {
		TestrayValidator.validateBaseModel(request, testrayCaseResult, "the-case-result-with-id-x-does-not-exist", "id");

		int status = ParamUtil.getInteger(request, "status");

		if ((testrayCaseResult.getStatus() == TestrayCaseResultConstants.STATUS_UNTESTED) && (status != TestrayCaseResultConstants.STATUS_IN_PROGRESS)) {
			throw new AlloyException(translate("progress-has-not-been-started-for-the-case-result-with-id-x", String.valueOf(testrayCaseResult.getTestrayCaseResultId())), false);
		}
	}

	private void _validateUpdateUser(TestrayCaseResult testrayCaseResult) throws Exception {
		TestrayValidator.validateBaseModel(request, testrayCaseResult, "the-case-result-with-id-x-does-not-exist", "id");

		long newAssignedUserId = ParamUtil.getLong(request, "assignedUserId");

		if ((newAssignedUserId > 0) && (testrayCaseResult.getAssignedUserId() > 0) && (newAssignedUserId != testrayCaseResult.getAssignedUserId()) && !TestrayPermission.contains(themeDisplay, testrayCaseResult, "delete")) {
			throw new AlloyException("the-case-result-has-already-been-assigned-to-another-user", false);
		}
	}

}
%>