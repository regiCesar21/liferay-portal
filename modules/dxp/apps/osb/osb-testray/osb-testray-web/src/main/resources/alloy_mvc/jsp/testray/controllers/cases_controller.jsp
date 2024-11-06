<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/testray/controllers/init.jspf" %>

<%@ include file="/alloy_mvc/jsp/util/testray_case_checker.jspf" %>
<%@ include file="/alloy_mvc/jsp/util/testray_case_indexer.jspf" %>

<%@ include file="/alloy_mvc/jsp/util/testray_case_scheduler_message_listener.jspf" %>

<%!
public static class AlloyControllerImpl extends TestrayAlloyControllerImpl {

	public AlloyControllerImpl() {
		setAlloyServiceInvokerClass(TestrayCase.class);
		setPermissioned(true);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"cur", "delta", "description", "estimatedDuration", "name", "priority", "steps", "testrayCaseTypeId", "testrayComponentId", "testrayComponentIds", "testrayProjectId"}, parameterTypes = {Integer.class, Integer.class, String.class, Integer.class, String.class, Integer.class, String.class, Long.class, Long.class, String.class, Long.class})
	public void add() throws Exception {
		TestrayCase testrayCase = TestrayCaseLocalServiceUtil.createTestrayCase(0);

		_validateAdd();

		String counterTestrayProjectName = TestrayProject.class.getName() + StringPool.POUND + testrayProjectId;

		testrayCase.setCaseNumber(CounterLocalServiceUtil.increment(counterTestrayProjectName));

		long testrayCaseId = increment();

		testrayCase.setOriginationKey(testrayCaseId);

		updateModel(testrayCase, "testrayCaseId", testrayCaseId);

		_indexTestraySuites(testrayCase);

		if (!PortalPropsValues.TESTRAY_SIMPLIFIED_CASES) {
			_updateTestrayComponentIds(testrayCase);
		}

		if (isRespondingTo("json")) {
			respondWith(_getTestrayCaseJSONObject(testrayCase));

			return;
		}

		setPollIndexStateProperties(Field.ENTRY_CLASS_PK, testrayCase.getTestrayCaseId(), "testrayProjectId", testrayCase.getTestrayProjectId());

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		if (Validator.isNull(redirect)) {
			boolean addAnother = ParamUtil.getBoolean(request, "addAnother");

			if (addAnother) {
				redirect = TestrayUtil.getURL(this, "cases", "create", 0, "addAnother", addAnother, "description", testrayCase.getDescription(), "estimatedDuration", testrayCase.getEstimatedDuration(), "name", _getCopyName(testrayCase.getName()), "priority", testrayCase.getPriority(), "steps", testrayCase.getSteps(), "testrayCaseTypeId", testrayCase.getTestrayCaseTypeId(), "testrayComponentId", testrayCase.getTestrayComponentId(), "testrayProjectId", testrayCase.getTestrayProjectId());
			}
			else {
				redirect = TestrayUtil.getURL(this, "cases", "view", testrayCaseId);
			}
		}

		redirectTo(redirect);
	}

	public void create() throws Exception {
		renderRequest.setAttribute("testrayProjectId", testrayProjectId);

		renderRequest.setAttribute("TestrayRichTextConstants", getConstantsBean(TestrayRichTextConstants.class));

		TestrayCase testrayCase = TestrayCaseLocalServiceUtil.createTestrayCase(0);

		testrayCase.setTestrayProjectId(testrayProjectId);

		renderRequest.setAttribute("testrayCase", testrayCase);

		AlloyServiceInvoker testrayCaseTypeAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCaseType.class.getName());

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(TestrayCaseTypeModelImpl.TABLE_NAME, "name", true);

		List<TestrayCaseType> testrayCaseTypes = testrayCaseTypeAlloyServiceInvoker.executeDynamicQuery(new Object[] {"groupId", themeDisplay.getScopeGroupId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayCaseTypes", testrayCaseTypes);

		AlloyServiceInvoker testrayComponentAlloyServiceInvoker = new AlloyServiceInvoker(TestrayComponent.class.getName());

		obc = OrderByComparatorFactoryUtil.create(TestrayComponentModelImpl.TABLE_NAME, "name", true);

		List<TestrayComponent> testrayComponents = testrayComponentAlloyServiceInvoker.executeDynamicQuery(new Object[] {"testrayProjectId", testrayProjectId}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayComponents", testrayComponents);

		List<KeyValuePair> currentTestrayComponents = new ArrayList<KeyValuePair>();

		if (!PortalPropsValues.TESTRAY_SIMPLIFIED_CASES) {
			renderRequest.setAttribute("currentTestrayComponents", currentTestrayComponents);

			List<KeyValuePair> availableTestrayComponents = new ArrayList<KeyValuePair>();

			for (TestrayComponent testrayComponent : testrayComponents) {
				availableTestrayComponents.add(new KeyValuePair(String.valueOf(testrayComponent.getTestrayComponentId()), translate(testrayComponent.getName())));
			}

			availableTestrayComponents = ListUtil.sort(availableTestrayComponents, new KeyValuePairComparator(false, true));

			renderRequest.setAttribute("availableTestrayComponents", availableTestrayComponents);
		}

		long testraySuiteId = ParamUtil.getLong(request, "testraySuiteId");

		renderRequest.setAttribute("testraySuiteId", testraySuiteId);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"id"}, parameterTypes = {Long.class})
	public void delete() throws Exception {
		TestrayCase testrayCase = _fetchTestrayCase();

		_validateDelete(testrayCase);

		TestrayCaseUtil.deleteTestrayCase(testrayCase.getTestrayCaseId());

		_indexTestraySuites(testrayCase);

		TestrayCaseResultUtil.deleteTestrayCaseResults(themeDisplay.getCompanyId(), "testrayCaseId", testrayCase.getTestrayCaseId());

		if (isRespondingTo("json")) {
			respondWith(JSONFactoryUtil.getNullJSON());

			return;
		}

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void edit() throws Exception {
		TestrayCase testrayCase = _fetchTestrayCase();

		_validateEdit(testrayCase);

		renderRequest.setAttribute("TestrayRichTextConstants", getConstantsBean(TestrayRichTextConstants.class));

		renderRequest.setAttribute("testrayCase", testrayCase);

		AlloyServiceInvoker testrayCaseTypeAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCaseType.class.getName());

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(TestrayCaseTypeModelImpl.TABLE_NAME, "name", true);

		List<TestrayCaseType> testrayCaseTypes = testrayCaseTypeAlloyServiceInvoker.executeDynamicQuery(new Object[] {"groupId", themeDisplay.getScopeGroupId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayCaseTypes", testrayCaseTypes);

		AlloyServiceInvoker testrayComponentAlloyServiceInvoker = new AlloyServiceInvoker(TestrayComponent.class.getName());

		obc = OrderByComparatorFactoryUtil.create(TestrayComponentModelImpl.TABLE_NAME, "name", true);

		List<TestrayComponent> testrayComponents = testrayComponentAlloyServiceInvoker.executeDynamicQuery(new Object[] {"testrayProjectId", testrayCase.getTestrayProjectId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayComponents", testrayComponents);

		if (!PortalPropsValues.TESTRAY_SIMPLIFIED_CASES) {
			List<KeyValuePair> currentTestrayComponents = new ArrayList<KeyValuePair>();

			List<TestrayComponent> testrayCaseTestrayComponents = TestrayComponentLocalServiceUtil.getTestrayCaseTestrayComponents(testrayCase.getTestrayCaseId());

			for (TestrayComponent testrayCaseTestrayComponent : testrayCaseTestrayComponents) {
				currentTestrayComponents.add(new KeyValuePair(String.valueOf(testrayCaseTestrayComponent.getTestrayComponentId()), translate(testrayCaseTestrayComponent.getName())));
			}

			renderRequest.setAttribute("currentTestrayComponents", currentTestrayComponents);

			List<KeyValuePair> availableTestrayComponents = new ArrayList<KeyValuePair>();

			for (TestrayComponent testrayComponent : testrayComponents) {
				if (testrayCaseTestrayComponents.contains(testrayComponent)) {
					continue;
				}

				availableTestrayComponents.add(new KeyValuePair(String.valueOf(testrayComponent.getTestrayComponentId()), translate(testrayComponent.getName())));
			}

			availableTestrayComponents = ListUtil.sort(availableTestrayComponents, new KeyValuePairComparator(false, true));

			renderRequest.setAttribute("availableTestrayComponents", availableTestrayComponents);
		}

		long testraySuiteId = ParamUtil.getLong(request, "testraySuiteId");

		renderRequest.setAttribute("testraySuiteId", testraySuiteId);
	}

	public void export() throws Exception {
		long[] addTestrayCaseIds = ParamUtil.getLongValues(request, "addTestrayCaseIds");

		Set<Long> testrayCaseIds = SetUtil.fromArray(addTestrayCaseIds);

		List<TestrayCaseComposite> testrayCaseComposites = new ArrayList<>(testrayCaseIds.size());

		Map<Long, TestrayRequirementComposite> testrayRequirementsMap = new HashMap<>();

		for (long testrayCaseId : testrayCaseIds) {
			TestrayCase testrayCase = TestrayCaseLocalServiceUtil.getTestrayCase(testrayCaseId);

			testrayCaseComposites.add(new TestrayCaseComposite(testrayCase));

			for (TestrayRequirement testrayRequirement : TestrayRequirementLocalServiceUtil.getTestrayCaseTestrayRequirements(testrayCaseId)) {
				testrayRequirementsMap.putIfAbsent(testrayRequirement.getTestrayRequirementId(), new TestrayRequirementComposite(testrayRequirement));
			}
		}

		portletRequest.setAttribute("testrayCaseComposites", testrayCaseComposites);

		List<TestrayRequirementComposite> testrayRequirementComposites = new ArrayList<>();

		for (Map.Entry<Long, TestrayRequirementComposite> entry : testrayRequirementsMap.entrySet()) {
			testrayRequirementComposites.add(entry.getValue());
		}

		portletRequest.setAttribute("testrayRequirementComposites", testrayRequirementComposites);

		render("cases/export");

		return;
	}

	public void importCases() throws Exception {
		_validateImportCases();

		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		File casesFile = uploadPortletRequest.getFile("cases");

		TestrayCSVObject testrayCSVObject = new TestrayCSVObject(FileUtil.read(casesFile));

		for (int rowIndex = 1; rowIndex < testrayCSVObject.getRowsCount(); rowIndex++) {
			String name = (String)testrayCSVObject.get(rowIndex, 0);
			String priorityString = (String)testrayCSVObject.get(rowIndex, 1);
			String testrayCaseTypeName = (String)testrayCSVObject.get(rowIndex, 2);
			String testrayTeamName = (String)testrayCSVObject.get(rowIndex, 3);
			String testrayComponentName = (String)testrayCSVObject.get(rowIndex, 4);
			String estimatedDurationString = (String)testrayCSVObject.get(rowIndex, 5);
			String description = (String)testrayCSVObject.get(rowIndex, 6);
			String steps = (String)testrayCSVObject.get(rowIndex, 7);

			try {
				_validateImportCase(estimatedDurationString, name, priorityString, testrayCaseTypeName, testrayComponentName);
			}
			catch (AlloyException ae) {
				throw new AlloyException("there-is-a-problem-with-row-x-x", new Object[] {rowIndex, ae.getMessage()}, false);
			}

			TestrayCase testrayCase = TestrayCaseUtil.fetchTestrayCase(testrayProjectId, name);

			if (testrayCase == null) {
				testrayCase = TestrayCaseLocalServiceUtil.createTestrayCase(increment());

				testrayCase.setCaseNumber(CounterLocalServiceUtil.increment(TestrayProject.class.getName() + StringPool.POUND + testrayProjectId));

				testrayCase.setOriginationKey(testrayCase.getTestrayCaseId());
			}

			testrayCase.setDescription(description);
			testrayCase.setDescriptionType(TestrayRichTextConstants.TYPE_MARKDOWN);
			testrayCase.setEstimatedDuration(GetterUtil.getInteger(estimatedDurationString));
			testrayCase.setName(name);
			testrayCase.setPriority(GetterUtil.getInteger(priorityString));
			testrayCase.setSteps(steps);
			testrayCase.setStepsType(TestrayRichTextConstants.TYPE_MARKDOWN);

			TestrayCaseType testrayCaseType = TestrayCaseTypeUtil.fetchTestrayCaseType(themeDisplay.getScopeGroupId(), testrayCaseTypeName);

			testrayCase.setTestrayCaseTypeId(testrayCaseType.getTestrayCaseTypeId());

			TestrayComponent testrayComponent = _fetchOrAddTestrayComponent(testrayProjectId, testrayComponentName, testrayTeamName);

			testrayCase.setTestrayComponentId(testrayComponent.getTestrayComponentId());

			testrayCase.setTestrayProjectId(testrayProjectId);

			updateModelIgnoreRequest(testrayCase, "testrayCaseId", testrayCase.getTestrayCaseId());

			TestrayComponentLocalServiceUtil.addTestrayCaseTestrayComponent(testrayCase.getTestrayCaseId(), testrayComponent.getTestrayComponentId());
		}

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"cur", "delta", "end", "start", "testrayProjectId"}, parameterTypes = {Integer.class, Integer.class, Integer.class, Integer.class, Long.class})
	public void index() throws Exception {
		if (isRespondingTo("js")) {
			long[] testraySuiteIds = ParamUtil.getLongValues(request, "testraySuiteIds");

			Set<TestrayCase> testrayCases = new HashSet<TestrayCase>();

			if (testraySuiteIds.length > 0) {
				for (long testraySuiteId : testraySuiteIds) {
					List<TestrayCase> testraySuiteTestrayCases = TestrayCaseLocalServiceUtil.getTestraySuiteTestrayCases(testraySuiteId);

					for (TestrayCase testrayCase : testraySuiteTestrayCases) {
						testrayCases.add(testrayCase);
					}
				}
			}
			else {
				JSONArray caseParametersJSONArray = JSONFactoryUtil.createJSONArray();

				TestraySuiteUtil.addCaseParameter(request, "priority", "int", caseParametersJSONArray);
				TestraySuiteUtil.addCaseParameter(request, "subcomponentId", "long", caseParametersJSONArray);
				TestraySuiteUtil.addCaseParameter(request, "testrayCaseTypeId", "long", caseParametersJSONArray);
				TestraySuiteUtil.addCaseParameter(request, "testrayComponentId", "long", caseParametersJSONArray);
				TestraySuiteUtil.addCaseParameter(request, "testrayRequirementId", "long", caseParametersJSONArray);
				TestraySuiteUtil.addCaseParameter(request, "testrayTeamId", "long", caseParametersJSONArray);

				portletRequest.setAttribute("caseParametersString", caseParametersJSONArray.toString());

				if (caseParametersJSONArray.length() == 0) {
					testrayCases = Collections.emptySet();
				}
				else {
					long testrayProjectId = ParamUtil.getLong(request, "testrayProjectId");

					List<Long> testrayCaseIds = TestrayCaseUtil.getTestrayCaseIds(testrayProjectId, caseParametersJSONArray.toString());

					for (long testrayCaseId : testrayCaseIds) {
						testrayCases.add(TestrayCaseLocalServiceUtil.getTestrayCase(testrayCaseId));
					}
				}
			}

			JSONArray testrayCasesJSONArray = _getTestrayCasesJSONArray(testrayCases);

			portletRequest.setAttribute("testrayCasesJSONArray", testrayCasesJSONArray);

			render("cases/index_js");

			return;
		}
		else if (isRespondingTo("csv")) {
			Map<String, Serializable> attributes = new HashMap<String, Serializable>();

			boolean stepsBlankOnly = ParamUtil.getBoolean(request, "stepsBlankOnly");

			if (stepsBlankOnly) {
				attributes.put("blankSteps", true);
			}

			int[] priorities = ParamUtil.getIntegerValues(request, "priorities");

			renderRequest.setAttribute("testrayCasePriorities", ListUtil.toList(priorities));

			if (ArrayUtil.isNotEmpty(priorities)) {
				attributes.put("testrayCasePriority", StringUtil.merge(priorities));
			}

			List<TestrayCaseComposite> testrayCaseComposites = _search(attributes);

			respondWith(null, _getTestrayCasesCSV(testrayCaseComposites));

			return;
		}

		renderRequest.setAttribute("TestrayCaseConstants", getConstantsBean(TestrayCaseConstants.class));
		renderRequest.setAttribute("TestrayCaseResultConstants", getConstantsBean(TestrayCaseResultConstants.class));

		AlloyServiceInvoker testrayCaseTypeAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCaseType.class.getName());

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(TestrayCaseTypeModelImpl.TABLE_NAME, "name", true);

		List<TestrayCaseType> testrayCaseTypes = testrayCaseTypeAlloyServiceInvoker.executeDynamicQuery(new Object[] {"groupId", themeDisplay.getScopeGroupId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayCaseTypes", testrayCaseTypes);

		Map<String, Serializable> attributes = new HashMap<String, Serializable>();

		boolean stepsBlankOnly = ParamUtil.getBoolean(request, "stepsBlankOnly");

		if (stepsBlankOnly) {
			attributes.put("blankSteps", true);
		}

		int[] priorities = ParamUtil.getIntegerValues(request, "priorities");

		renderRequest.setAttribute("testrayCasePriorities", ListUtil.toList(priorities));

		if (ArrayUtil.isNotEmpty(priorities)) {
			attributes.put("testrayCasePriority", StringUtil.merge(priorities));
		}

		List<TestrayCaseComposite> testrayCaseComposites = _search(attributes);

		long testrayProjectId = ParamUtil.getLong(request, "testrayProjectId");

		List<TestrayTeam> testrayTeams = TestrayTeamUtil.getTestrayTeams(testrayProjectId);

		renderRequest.setAttribute("testrayTeams", testrayTeams);

		JSONArray testrayCasesJSONArray = _getTestrayCasesJSONArray(testrayCaseComposites);

		renderRequest.setAttribute("testrayCasesJSONArray", testrayCasesJSONArray);

		if (isRespondingTo("json")) {
			respondWith(testrayCasesJSONArray);
		}
	}

	public void requirements() throws Exception {
		TestrayCase testrayCase = _fetchTestrayCase();

		_validateRequirements(testrayCase);

		renderRequest.setAttribute("TestrayRequirementConstants", getConstantsBean(TestrayRequirementConstants.class));
		renderRequest.setAttribute("TestrayRichTextConstants", getConstantsBean(TestrayRichTextConstants.class));

		renderRequest.setAttribute("testrayCase", testrayCase);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put("testrayCaseId", testrayCase.getTestrayCaseId());
		attributes.put("testrayProjectId", testrayCase.getTestrayProjectId());

		String orderByCol = ParamUtil.getString(request, "orderByCol", "key_sortable");

		renderRequest.setAttribute("orderByCol", orderByCol);

		String orderByType = ParamUtil.getString(request, "orderByType", "asc");

		renderRequest.setAttribute("orderByType", orderByType);

		int[] startAndEnd = TestrayUtil.getStartAndEnd(request, portletRequest, portletURL);

		AlloySearchResult alloySearchResult = search(IndexerRegistryUtil.nullSafeGetIndexer(TestrayRequirement.class), new AlloyServiceInvoker(TestrayRequirement.class.getName()), request, portletRequest, attributes, null, new Sort[] {new Sort(orderByCol, orderByType.equals("desc"))}, startAndEnd[0], startAndEnd[1]);

		renderRequest.setAttribute("alloySearchResult", alloySearchResult);

		renderRequest.setAttribute("testrayRequirementComposites", TestrayCompositeUtil.getComposites(alloySearchResult.getBaseModels(), TestrayRequirementComposite.class));
	}

	public void select() throws Exception {
		renderRequest.setAttribute("TestrayCaseConstants", getConstantsBean(TestrayCaseConstants.class));

		long testrayBuildId = ParamUtil.getLong(request, "testrayBuildId", -1);
		long testrayRequirementId = ParamUtil.getLong(request, "testrayRequirementId");
		long testrayRunId = ParamUtil.getLong(request, "testrayRunId");
		long[] testrayRunIds = ParamUtil.getLongValues(request, "testrayRunIds");
		long testraySuiteId = ParamUtil.getLong(request, "testraySuiteId");

		Class<?> clazz = null;
		long classPK = 0;

		if (testrayBuildId > 0) {
			clazz = TestrayBuild.class;
			classPK = testrayBuildId;
		}
		else if (testrayRequirementId > 0) {
			clazz = TestrayRequirement.class;
			classPK = testrayRequirementId;
		}
		else if (testrayRunId > 0) {
			clazz = TestrayRun.class;
			classPK = testrayRunId;
		}
		else if (ArrayUtil.isNotEmpty(testrayRunIds)) {
			clazz = TestrayRun.class;
			classPK = testrayRunIds[0];
		}
		else {
			clazz = TestraySuite.class;
			classPK = testraySuiteId;
		}

		TestrayCaseChecker rowChecker = new TestrayCaseChecker(renderResponse, clazz, classPK);

		rowChecker.setFormName("fm2");

		renderRequest.setAttribute("rowChecker", rowChecker);

		Map<String, Serializable> attributes = new HashMap<String, Serializable>();

		boolean scopeByProject = ParamUtil.getBoolean(request, "scopeByProject");

		renderRequest.setAttribute("scopeByProject", scopeByProject);

		boolean scopeBySuite = ParamUtil.getBoolean(request, "scopeBySuite");

		renderRequest.setAttribute("scopeBySuite", scopeBySuite);

		if (scopeByProject) {
			attributes.put("testrayProjectId", testrayProjectId);
			attributes.put("testrayRunId", null);
			attributes.put("testraySuiteId", null);
		}
		else if (scopeBySuite) {
			attributes.put("testrayRunId", null);
			attributes.put("testraySuiteId", testraySuiteId);
		}
		else {
			attributes.put("testrayProjectId", TestrayProjectUtil.getTestrayProjectId(clazz.getName(), classPK));
		}

		int[] priorities = ParamUtil.getIntegerValues(request, "priorities");

		renderRequest.setAttribute("testrayCasePriorities", ListUtil.toList(priorities));

		if (ArrayUtil.isNotEmpty(priorities)) {
			attributes.put("testrayCasePriority", StringUtil.merge(priorities));
		}

		long testrayCaseTypeId = ParamUtil.getLong(request, "testrayCaseTypeId");

		TestrayCaseType testrayCaseType = TestrayCaseTypeLocalServiceUtil.fetchTestrayCaseType(testrayCaseTypeId);

		renderRequest.setAttribute("testrayCaseType", testrayCaseType);

		AlloyServiceInvoker testrayCaseTypeAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCaseType.class.getName());

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(TestrayCaseTypeModelImpl.TABLE_NAME, "name", true);

		List<TestrayCaseType> testrayCaseTypes = testrayCaseTypeAlloyServiceInvoker.executeDynamicQuery(new Object[] {"groupId", themeDisplay.getScopeGroupId()}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		renderRequest.setAttribute("testrayCaseTypes", testrayCaseTypes);

		TestrayUtil.setPortletURL(request, portletURL, "scopeByProject", "scopeBySuite");

		List<TestrayTeam> testrayTeams = TestrayTeamUtil.getTestrayTeams(testrayProjectId);

		renderRequest.setAttribute("testrayTeams", testrayTeams);

		long testrayTeamId = ParamUtil.getLong(request, "testrayTeamId");

		TestrayTeam testrayTeam = TestrayTeamLocalServiceUtil.fetchTestrayTeam(testrayTeamId);

		renderRequest.setAttribute("testrayTeam", testrayTeam);

		String redirect = ParamUtil.getString(request, "redirect");

		renderRequest.setAttribute("redirect", redirect);

		List<TestrayCaseComposite> testrayCaseComposites = _search(attributes);

		JSONArray testrayCasesJSONArray = _getTestrayCasesJSONArray(testrayCaseComposites);

		portletRequest.setAttribute("testrayCasesJSONArray", testrayCasesJSONArray);
	}

	public void selectExport() throws Exception {
		AlloyServiceInvoker testrayCaseAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCase.class.getName());

		long testrayProjectId = ParamUtil.getLong(request, "testrayProjectId");

		long testrayCasesCount = testrayCaseAlloyServiceInvoker.executeDynamicQueryCount(new Object[] {"testrayProjectId", testrayProjectId});

		renderRequest.setAttribute("testrayCasesCount", testrayCasesCount);

		String redirect = ParamUtil.getString(request, "redirect");

		renderRequest.setAttribute("redirect", redirect);

		render("cases/select_export");

		return;
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"id", "description", "estimatedDuration", "name", "priority", "steps", "testrayCaseTypeId", "testrayComponentId", "testrayComponentIds"}, parameterTypes = {String.class, String.class, Integer.class, String.class, Integer.class, String.class, Long.class, Long.class, String.class})
	public void update() throws Exception {
		TestrayCase testrayCase = _fetchTestrayCase();

		_validateUpdate(testrayCase);

		long testrayCaseId = testrayCase.getTestrayCaseId();

		updateModel(testrayCase, "testrayCaseId", testrayCaseId);

		TestrayCaseResultUtil.updateTestrayCaseTestrayCaseResults(testrayCaseId, "testrayComponentId", testrayCase.getTestrayComponentId());

		_indexTestraySuites(testrayCase);

		TestrayComponentLocalServiceUtil.clearTestrayCaseTestrayComponents(testrayCase.getTestrayCaseId());

		if (!PortalPropsValues.TESTRAY_SIMPLIFIED_CASES) {
			_updateTestrayComponentIds(testrayCase);
		}

		if (isRespondingTo("json")) {
			respondWith(_getTestrayCaseJSONObject(testrayCase));

			return;
		}

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.ACTION_PHASE, parameterNames = {"addTestrayRequirementIds", "id"}, parameterTypes = {String.class, Long.class})
	public void updateRequirements() throws Exception {
		TestrayCase testrayCase = _fetchTestrayCase();

		_validateUpdateRequirements(testrayCase);

		long[] addTestrayRequirementIds = ParamUtil.getLongValues(request, "addTestrayRequirementIds");

		if (ArrayUtil.isNotEmpty(addTestrayRequirementIds)) {
			TestrayRequirementLocalServiceUtil.addTestrayCaseTestrayRequirements(testrayCase.getTestrayCaseId(), addTestrayRequirementIds);

			for (long testrayRequirementId : addTestrayRequirementIds) {
				TestrayRequirement testrayRequirement = TestrayRequirementLocalServiceUtil.getTestrayRequirement(testrayRequirementId);

				indexModel(testrayRequirement);
			}

			indexModel(testrayCase);
		}

		if (isRespondingTo("json")) {
			respondWith(_getTestrayCaseJSONObject(testrayCase));

			return;
		}

		String redirect = ParamUtil.getString(request, "redirect");

		if (Validator.isNull(redirect)) {
			setOpenerSuccessMessage();

			render("close");
		}
		else {
			addSuccessMessage();

			redirectTo(redirect);
		}
	}

	@JSONWebServiceMethod(lifecycle = PortletRequest.RENDER_PHASE, parameterNames = {"id"}, parameterTypes = {String.class})
	public void view() throws Exception {
		TestrayCase testrayCase = _fetchTestrayCase();

		_validateView(testrayCase);

		if (isRespondingTo("json")) {
			respondWith(_getTestrayCaseJSONObject(testrayCase));

			return;
		}

		renderRequest.setAttribute("testrayCase", testrayCase);

		renderRequest.setAttribute("testrayCaseId", testrayCase.getTestrayCaseId());

		Map<String, Serializable> properties = new HashMap<String, Serializable>();

		properties.put("testrayCaseId", testrayCase.getTestrayCaseId());

		TestrayCaseResultReporter testrayCaseResultReporter = new TestrayCaseResultReporter(themeDisplay, properties);

		renderRequest.setAttribute("testrayCaseResultReporter", testrayCaseResultReporter);

		Map<String, Serializable> attributes = _getAttributes();

		renderRequest.setAttribute("testrayRoutines", TestrayRoutineUtil.getTestrayRoutines(testrayProjectId));

		List<TestrayTeam> testrayTeams = TestrayTeamUtil.getTestrayTeams(testrayProjectId);

		renderRequest.setAttribute("testrayTeams", testrayTeams);

		attributes.put("testrayCaseId", testrayCase.getTestrayCaseId());

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

		SearchContainer searchContainer = new SearchContainer<BaseModel<?>>(portletRequest, portletURL, null, null);

		int delta = ParamUtil.getInteger(request, "delta", 200);

		renderRequest.setAttribute("delta", delta);

		searchContainer.setDelta(delta);

		String orderByCol = ParamUtil.getString(request, "orderByCol", "createDate_sortable");
		String orderByType = ParamUtil.getString(request, "orderByType", "desc");

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

		if (!manualFilter) {
			AlloySearchResult alloySearchResult = search(IndexerRegistryUtil.nullSafeGetIndexer(TestrayCaseResult.class), new AlloyServiceInvoker(TestrayCaseResult.class.getName()), request, portletRequest, attributes, null, sorts, searchContainer.getStart(), searchContainer.getEnd());

			renderRequest.setAttribute("testrayCaseResultCompositesTotal", alloySearchResult.getSize());

			List<TestrayCaseResult> testrayCaseResults = TestrayUtil.getSubclassList(alloySearchResult.getBaseModels(), TestrayCaseResult.class);

			renderRequest.setAttribute("testrayCaseResultComposites", TestrayCompositeUtil.getComposites(testrayCaseResults, TestrayCaseResultComposite.class, new Class<?>[] {TestrayCaseResult.class, ThemeDisplay.class}, new Object[] {themeDisplay}));
		}
		else {
			AlloySearchResult alloySearchResult = search(IndexerRegistryUtil.nullSafeGetIndexer(TestrayCaseResult.class), new AlloyServiceInvoker(TestrayCaseResult.class.getName()), request, portletRequest, attributes, null, sorts, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			List<TestrayCaseResult> testrayCaseResults = TestrayUtil.getSubclassList(alloySearchResult.getBaseModels(), TestrayCaseResult.class);

			if (errors != null) {
				TestrayUtil.filterResultsByError(testrayCaseResults, errors);
			}

			TestrayUtil.filterTestrayCaseResultWarnings(testrayCaseResults, testrayCaseResultWarning);

			renderRequest.setAttribute("testrayCaseResultCompositesTotal", testrayCaseResults.size());

			List<TestrayCaseResultComposite> testrayCaseResultComposites = TestrayCompositeUtil.getComposites(testrayCaseResults, TestrayCaseResultComposite.class, new Class<?>[] {TestrayCaseResult.class, ThemeDisplay.class}, new Object[] {themeDisplay});

			renderRequest.setAttribute("testrayCaseResultComposites", ListUtil.subList(testrayCaseResultComposites, searchContainer.getStart(), searchContainer.getEnd()));
		}

		if (!PortalPropsValues.TESTRAY_SIMPLIFIED_CASES) {
			List<TestrayComponent> testrayCaseTestrayComponents = TestrayComponentLocalServiceUtil.getTestrayCaseTestrayComponents(testrayCase.getTestrayCaseId());

			String[] testrayCaseTestrayComponentNames = new String[testrayCaseTestrayComponents.size()];

			for (int i = 0; i < testrayCaseTestrayComponents.size(); i++) {
				TestrayComponent testrayCaseTestrayComponent = testrayCaseTestrayComponents.get(i);

				testrayCaseTestrayComponentNames[i] = testrayCaseTestrayComponent.getName();
			}

			renderRequest.setAttribute("testrayCaseTestrayComponentNames", StringUtil.merge(testrayCaseTestrayComponentNames));
		}

		TestrayCaseType testrayCaseType = TestrayCaseTypeLocalServiceUtil.fetchTestrayCaseType(testrayCase.getTestrayCaseTypeId());

		renderRequest.setAttribute("testrayCaseType", testrayCaseType);

		TestrayComponent testrayComponent = TestrayComponentLocalServiceUtil.fetchTestrayComponent(testrayCase.getTestrayComponentId());

		renderRequest.setAttribute("testrayComponent", testrayComponent);

		TestrayProject testrayProject = TestrayProjectLocalServiceUtil.fetchTestrayProject(testrayCase.getTestrayProjectId());

		renderRequest.setAttribute("testrayProject", testrayProject);

		long testraySuiteId = ParamUtil.getLong(request, "testraySuiteId");

		renderRequest.setAttribute("redirect", portletURL);

		renderRequest.setAttribute("testraySuiteId", testraySuiteId);

		renderRequest.setAttribute("users", UserLocalServiceUtil.getUsers(themeDisplay.getCompanyId(), false, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new UserFirstNameComparator(true)));
	}

	@Override
	protected Indexer buildIndexer() {
		return TestrayCaseIndexer.getInstance();
	}

	@Override
	protected MessageListener buildSchedulerMessageListener() {
		return TestrayCaseSchedulerMessageListener.getInstance(this);
	}

	@Override
	protected Trigger getSchedulerTrigger() {
		return TriggerFactoryUtil.createTrigger(getSchedulerJobName(), getMessageListenerGroupName(), PortalPropsValues.TESTRAY_CRON_TRIGGER_CASES_CONTROLLER);
	}

	private TestrayComponent _fetchOrAddTestrayComponent(long testrayProjectId, String name, String testrayTeamName) throws Exception {
		TestrayComponent testrayComponent = TestrayComponentUtil.fetchTestrayComponent(testrayProjectId, name);

		if (testrayComponent != null) {
			return testrayComponent;
		}

		testrayComponent = TestrayComponentLocalServiceUtil.createTestrayComponent(0);

		testrayComponent.setName(name);
		testrayComponent.setTestrayProjectId(testrayProjectId);

		TestrayTeam testrayTeam = TestrayTeamUtil.fetchTestrayTeam(testrayProjectId, testrayTeamName);

		if ((testrayTeam == null) && Validator.isNotNull(testrayTeamName)) {
			testrayTeam = TestrayTeamLocalServiceUtil.createTestrayTeam(0);

			testrayTeam.setName(testrayTeamName);
			testrayTeam.setTestrayProjectId(testrayProjectId);

			updateModelIgnoreRequest(testrayTeam);
		}

		if (testrayTeam != null) {
			testrayComponent.setTestrayTeamId(testrayTeam.getTestrayTeamId());
		}

		updateModelIgnoreRequest(testrayComponent);

		return testrayComponent;
	}

	private TestrayCase _fetchTestrayCase() throws Exception {
		TestrayCase testrayCase = null;

		String id = ParamUtil.getString(request, "id");

		if (Validator.isNumber(id)) {
			testrayCase = TestrayCaseLocalServiceUtil.fetchTestrayCase(GetterUtil.getLong(id));
		}
		else if (id.indexOf(StringPool.UNDERLINE) == 0) {
			long testrayProjectId = ParamUtil.getLong(request, "testrayProjectId");

			List<TestrayCase> testrayCases = alloyServiceInvoker.executeDynamicQuery(new Object[] {"testrayProjectId", testrayProjectId, "name", id.substring(1)});

			if (!testrayCases.isEmpty()) {
				testrayCase = testrayCases.get(0);
			}
		}

		return testrayCase;
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

		int[] statuses = ParamUtil.getIntegerValues(request, "statuses");

		List<Integer> statusesList = ListUtil.toList(statuses);

		renderRequest.setAttribute("statuses", statusesList);
		renderRequest.setAttribute("statusLabels", TestrayUtil.getStatusLabels(request, TestrayCaseResultConstants::getStatusLabel, statusesList));

		if (ArrayUtil.isNotEmpty(statuses)) {
			attributes.put("status", StringUtil.merge(statuses));
		}

		long[] testrayRoutineIds = ParamUtil.getLongValues(request, "testrayRoutineId");

		if (ArrayUtil.isNotEmpty(testrayRoutineIds)) {
			attributes.put("testrayRoutineId", StringUtil.merge(testrayRoutineIds));

			renderRequest.setAttribute("testrayRoutineIds", ListUtil.toList(testrayRoutineIds));
		}

		return attributes;
	}

	private String _getCopyName(String name) {
		return "Copy - " + name;
	}

	private String _getTestrayCasesCSV(List<TestrayCaseComposite> testrayCaseComposites) throws Exception {
		TestrayCSVObject testrayCSVObject = new TestrayCSVObject(8, testrayCaseComposites.size());

		testrayCSVObject.addLocalizedCells(request, "name", "priority", "case-type", "team", "component", "estimated-duration", "description", "steps");

		testrayCSVObject.startNewRow();

		for (TestrayCaseComposite testrayCaseComposite : testrayCaseComposites) {
			testrayCSVObject.addCells(testrayCaseComposite.getName(), testrayCaseComposite.getPriority(), testrayCaseComposite.getTestrayTeamName(), testrayCaseComposite.getTestrayComponentName(), testrayCaseComposite.getEstimatedDuration(), testrayCaseComposite.getDescription(), testrayCaseComposite.getSteps());

			testrayCSVObject.startNewRow();
		}

		return testrayCSVObject.toString();
	}

	private JSONObject _getTestrayCaseJSONObject(TestrayCase testrayCase) throws Exception {
		TestrayCaseComposite testrayCaseComposite = new TestrayCaseComposite(testrayCase);

		return testrayCaseComposite.getJSONObject();
	}

	private JSONArray _getTestrayCasesJSONArray(Collection<TestrayCase> testrayCases) throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (TestrayCase testrayCase : testrayCases) {
			jsonArray.put(_getTestrayCaseJSONObject(testrayCase));
		}

		return jsonArray;
	}

	private JSONArray _getTestrayCasesJSONArray(List<TestrayCaseComposite> testrayCaseComposites) throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (TestrayCaseComposite testrayCaseComposite : testrayCaseComposites) {
			jsonArray.put(testrayCaseComposite.getJSONObject());
		}

		return jsonArray;
	}

	private void _indexTestraySuites(TestrayCase testrayCase) throws Exception {
		Collection<TestraySuite> testraySuites = null;

		List<TestraySuite> databaseTestraySuites = TestraySuiteLocalServiceUtil.getTestrayCaseTestraySuites(testrayCase.getTestrayCaseId());

		Map<String, Serializable> attributes = new HashMap<String, Serializable>();

		attributes.put("testrayProjectId", testrayCase.getTestrayProjectId());

		List<TestrayCase> testrayCases = new ArrayList<TestrayCase>();

		testrayCases.add(testrayCase);

		List<Long> testrayTeamIds = TestrayTeamUtil.getTestrayTeamIds(testrayCases);

		if (!testrayTeamIds.isEmpty()) {
			attributes.put("testrayTeamId", testrayTeamIds.get(0));

			AlloySearchResult alloySearchResult = search(IndexerRegistryUtil.nullSafeGetIndexer(TestraySuite.class), new AlloyServiceInvoker(TestraySuite.class.getName()), request, portletRequest, attributes, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			List<TestraySuite> indexedTestraySuites = TestrayUtil.getSubclassList(alloySearchResult.getBaseModels(), TestraySuite.class);

			testraySuites = SetUtil.symmetricDifference(databaseTestraySuites, indexedTestraySuites);
		}
		else {
			testraySuites = databaseTestraySuites;
		}

		for (TestraySuite testraySuite : testraySuites) {
			indexModel(testraySuite);
		}
	}

	private List<TestrayCaseComposite> _search(Map<String, Serializable> attributes) throws Exception {
		String orderByCol = ParamUtil.getString(request, "orderByCol", "testrayComponentName_sortable");

		renderRequest.setAttribute("orderByCol", orderByCol);

		String orderByType = ParamUtil.getString(request, "orderByType", "asc");

		renderRequest.setAttribute("orderByType", orderByType);

		Sort[] sorts = {
			new Sort(orderByCol, orderByType.equals("desc")),
			new Sort("testrayComponentName_sortable", false),
			new Sort("priority", true),
			new Sort("name_sortable", false)
		};

		int[] startAndEnd = TestrayUtil.getStartAndEnd(request, portletRequest, portletURL);

		AlloySearchResult alloySearchResult = search(attributes, null, sorts, startAndEnd[0], startAndEnd[1]);

		renderRequest.setAttribute("alloySearchResult", alloySearchResult);

		List<TestrayCaseComposite> testrayCaseComposites = TestrayCompositeUtil.getComposites(alloySearchResult.getBaseModels(), TestrayCaseComposite.class);

		renderRequest.setAttribute("testrayCaseComposites", testrayCaseComposites);

		return testrayCaseComposites;
	}

	private void _updateTestrayComponentIds(TestrayCase testrayCase) throws Exception {
		Set<Long> testrayComponentIds = SetUtil.fromArray(ParamUtil.getLongValues(request, "testrayComponentIds"));

		testrayComponentIds.add(testrayCase.getTestrayComponentId());

		for (long testrayComponentId : testrayComponentIds) {
			TestrayComponent testrayComponent = TestrayComponentLocalServiceUtil.fetchTestrayComponent(testrayComponentId);

			if (testrayComponent == null) {
				continue;
			}

			TestrayComponentLocalServiceUtil.addTestrayCaseTestrayComponent(testrayCase.getTestrayCaseId(), testrayComponent.getTestrayComponentId());
		}
	}

	private void _validateAdd() throws Exception {
		_validateEstimatedDuration();
		_validateName();
		_validateNameRequired();
		_validatePriority();
		_validatePriorityRequired();
		_validateTestrayCaseTypeId();
		_validateTestrayCaseTypeIdRequired();
		_validateTestrayComponentId();
		_validateTestrayComponentIdRequired();
		_validateTestrayProjectId();
		_validateTestrayProjectIdRequired();
	}

	private void _validateDelete(TestrayCase testrayCase) throws Exception {
		_validateTestrayCase(testrayCase);
	}

	private void _validateEdit(TestrayCase testrayCase) throws Exception {
		_validateTestrayCase(testrayCase);
	}

	private void _validateEstimatedDuration() throws Exception {
		_validateEstimatedDuration(request.getParameter("estimatedDuration"));
	}

	private void _validateEstimatedDuration(String estimatedDurationString) throws Exception {
		if (Validator.isNull(estimatedDurationString)) {
			return;
		}

		int estimatedDuration = GetterUtil.getInteger(estimatedDurationString);

		if (estimatedDuration < 0) {
			throw new AlloyException("the-estimated-duration-is-invalid-and-must-be-an-integer", false);
		}
	}

	private void _validateImportCase(String estimatedDurationString, String name, String priorityString, String testrayCaseTypeName, String testrayComponentName) throws Exception {
		_validateEstimatedDuration(estimatedDurationString);
		_validateName(name);
		_validateNameRequired(name);
		_validatePriority(priorityString);
		_validatePriorityRequired(priorityString);
		_validateTestrayCaseTypeName(testrayCaseTypeName);
		_validateTestrayCaseTypeNameRequired(testrayCaseTypeName);
		_validateTestrayComponentNameRequired(testrayComponentName);
	}

	private void _validateImportCases() throws Exception {
		_validateTestrayProjectId();
		_validateTestrayProjectIdRequired();
	}

	private void _validateName() throws Exception {
		_validateName(request.getParameter("name"));
	}

	private void _validateName(String name) throws Exception {
		if (name == null) {
			return;
		}

		if (name.isEmpty()) {
			throw new AlloyException("the-case-name-is-invalid", false);
		}

		TestrayCase testrayCase = TestrayCaseUtil.fetchTestrayCase(testrayProjectId, name);

		if (testrayCase != null) {
			long testrayCaseId = ParamUtil.getLong(request, "id");

			if (testrayCase.getTestrayCaseId() != testrayCaseId) {
				throw new AlloyException("the-case-name-already-exists", false);
			}
		}
	}

	private void _validateNameRequired() throws Exception {
		_validateNameRequired(request.getParameter("name"));
	}

	private void _validateNameRequired(String name) throws Exception {
		if (name == null) {
			throw new AlloyException("a-name-is-required", false);
		}
	}

	private void _validatePriority() throws Exception {
		_validatePriority(request.getParameter("priority"));
	}

	private void _validatePriority(String priorityString) throws Exception {
		if (Validator.isNull(priorityString)) {
			return;
		}

		int priority = GetterUtil.getInteger(priorityString);

		if ((priority < 1) || (priority > 5)) {
			throw new AlloyException("the-priority-is-invalid", false);
		}
	}

	private void _validatePriorityRequired() throws Exception {
		_validatePriorityRequired(request.getParameter("priority"));
	}

	private void _validatePriorityRequired(String priorityString) throws Exception {
		if (priorityString == null) {
			throw new AlloyException("a-priority-is-required", false);
		}
	}

	private void _validateRequirements(TestrayCase testrayCase) throws Exception {
		_validateTestrayCase(testrayCase);
	}

	private void _validateTestrayCase(TestrayCase testrayCase) throws Exception {
		if ((testrayCase == null) || testrayCase.isNew()) {
			String id = ParamUtil.getString(request, "id");

			if (Validator.isNumber(id)) {
				throw new AlloyException(translate("the-case-with-id-x-does-not-exist", GetterUtil.getLong(id)), false);
			}
			else if (id.indexOf(StringPool.UNDERLINE) == 0) {
				long testrayProjectId = ParamUtil.getLong(request, "testrayProjectId");

				throw new AlloyException(translate("the-case-with-name-x-does-not-exist-for-the-project-with-id-x", id.substring(1), testrayProjectId), false);
			}
		}
	}

	private void _validateTestrayCaseTypeId() throws Exception {
		_validateTestrayCaseTypeId(request.getParameter("testrayCaseTypeId"));
	}

	private void _validateTestrayCaseTypeId(String testrayCaseTypeIdString) throws Exception {
		if (testrayCaseTypeIdString == null) {
			return;
		}

		long testrayCaseTypeId = GetterUtil.getLong(testrayCaseTypeIdString);

		if (testrayCaseTypeId <= 0) {
			throw new AlloyException("the-case-type-is-invalid");
		}

		TestrayCaseType testrayCaseType = TestrayCaseTypeLocalServiceUtil.fetchTestrayCaseType(testrayCaseTypeId);

		if (testrayCaseType == null) {
			throw new AlloyException(translate("the-case-type-with-id-x-does-not-exist", testrayCaseTypeId), false);
		}
	}

	private void _validateTestrayCaseTypeIdRequired() throws Exception {
		_validateTestrayCaseTypeIdRequired(request.getParameter("testrayCaseTypeId"));
	}

	private void _validateTestrayCaseTypeIdRequired(String testrayCaseTypeIdString) throws Exception {
		if (testrayCaseTypeIdString == null) {
			throw new AlloyException("a-case-type-is-required");
		}
	}

	private void _validateTestrayCaseTypeName(String testrayCaseTypeName) throws Exception {
		if (Validator.isNull(testrayCaseTypeName)) {
			return;
		}

		AlloyServiceInvoker testrayCaseTypeAlloyServiceInvoker = new AlloyServiceInvoker(TestrayCaseType.class.getName());

		long testrayCaseTypesCount = testrayCaseTypeAlloyServiceInvoker.executeDynamicQueryCount(new Object[] {"groupId", themeDisplay.getScopeGroupId(), "name", testrayCaseTypeName});

		if (testrayCaseTypesCount == 0) {
			throw new AlloyException(translate("the-case-type-with-name-x-does-not-exist", testrayCaseTypeName), false);
		}
	}

	private void _validateTestrayCaseTypeNameRequired(String testrayCaseTypeName) throws Exception {
		if (Validator.isNull(testrayCaseTypeName)) {
			throw new AlloyException("a-case-type-is-required");
		}
	}

	private void _validateTestrayComponentId() throws Exception {
		_validateTestrayComponentId(request.getParameter("testrayComponentId"));
	}

	private void _validateTestrayComponentId(long testrayComponentId) throws Exception {
		TestrayComponent testrayComponent = TestrayComponentLocalServiceUtil.fetchTestrayComponent(testrayComponentId);

		if (testrayComponent == null) {
			throw new AlloyException(translate("the-component-with-id-x-does-not-exist", testrayComponentId), false);
		}
	}

	private void _validateTestrayComponentId(String testrayComponentIdString) throws Exception {
		if (testrayComponentIdString == null) {
			return;
		}

		long testrayComponentId = GetterUtil.getLong(testrayComponentIdString);

		if (testrayComponentId <= 0) {
			throw new AlloyException("the-main-component-is-invalid", false);
		}

		_validateTestrayComponentId(testrayComponentId);
	}

	private void _validateTestrayComponentIdRequired() throws Exception {
		_validateTestrayComponentIdRequired(request.getParameter("testrayComponentId"));
	}

	private void _validateTestrayComponentIdRequired(String testrayComponentIdString) throws Exception {
		if (testrayComponentIdString == null) {
			throw new AlloyException("a-main-component-is-required", false);
		}
	}

	private void _validateTestrayComponentIds() throws Exception {
		if ((request.getParameter("testrayComponentIds") == null) || PortalPropsValues.TESTRAY_SIMPLIFIED_CASES) {
			return;
		}

		Set<Long> testrayComponentIds = SetUtil.fromArray(ParamUtil.getLongValues(request, "testrayComponentIds"));

		if (testrayComponentIds.isEmpty()) {
			throw new AlloyException("there-are-no-component-ids", false);
		}

		for (long testrayComponentId : testrayComponentIds) {
			_validateTestrayComponentId(testrayComponentId);
		}
	}

	private void _validateTestrayComponentNameRequired(String testrayComponentName) throws Exception {
		if (Validator.isNull(testrayComponentName)) {
			throw new AlloyException("a-main-component-is-required");
		}
	}

	private void _validateTestrayProjectId() throws Exception {
		_validateTestrayProjectId(request.getParameter("testrayProjectId"));
	}

	private void _validateTestrayProjectId(String testrayProjectIdString) throws Exception {
		if (testrayProjectIdString == null) {
			return;
		}

		long testrayProjectId = GetterUtil.getLong(testrayProjectIdString);

		if (testrayProjectId <= 0) {
			throw new AlloyException("the-project-id-is-invalid", false);
		}

		TestrayProject testrayProject = TestrayProjectLocalServiceUtil.fetchTestrayProject(testrayProjectId);

		if (testrayProject == null) {
			throw new AlloyException(translate("the-project-with-id-x-does-not-exist", testrayProjectId), false);
		}
	}

	private void _validateTestrayProjectIdRequired() throws Exception {
		_validateTestrayProjectIdRequired(request.getParameter("testrayProjectId"));
	}

	private void _validateTestrayProjectIdRequired(String testrayProjectIdString) throws Exception {
		if (testrayProjectIdString == null) {
			throw new AlloyException("a-project-id-is-required", false);
		}
	}

	private void _validateUpdate(TestrayCase testrayCase) throws Exception {
		_validateTestrayCase(testrayCase);

		_validateEstimatedDuration();
		_validateName();
		_validatePriority();
		_validateTestrayCaseTypeIdRequired();
		_validateTestrayComponentIdRequired();
		_validateTestrayComponentIds();
	}

	private void _validateUpdateRequirements(TestrayCase testrayCase) throws Exception {
		_validateTestrayCase(testrayCase);
	}

	private void _validateView(TestrayCase testrayCase) throws Exception {
		_validateTestrayCase(testrayCase);
	}

}
%>