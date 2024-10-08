<script>
	(function() {
		var languageToggleSelected = false;

		var menuItems = document.querySelectorAll('.header-menu-list .list-item');

		function addLocaleParamToURI(locale, urn) {
			return '${zendesk_url}/hc/${zendesk_locale_util.convertToZendeskLocale(locale)}/' + urn;
		}

		var highlightedLinks = {
			className: 'highlighted',
			configs: [
				{
					name: '<@liferay.language key="submit-a-ticket" />',
					svgId: '#submit-ticket',
					url: addLocaleParamToURI('${current_locale}', 'requests/new')
				},
				{
					name: '<@liferay.language key="contact-us" />',
					svgId: '#contact',
					url: 'https://support.liferay.com/callback-form'
				},
				{
					name: '<@liferay.language key="view-my-tickets" />',
					svgId: '#ticket',
					url: addLocaleParamToURI('${current_locale}', 'requests')
				},
				{
					name: '<@liferay.language key="see-project-details" />',
					svgId: '#project-details',
					url: '/project-details'
				},
				{
					name: '<@liferay.language key="escalate-a-ticket" />',
					svgId: '#warning',
					url: 'https://support.liferay.com/support-ticket-escalation'
				}
			]
		};

		var locale = {
			alternativeLocales: [
				<#list available_locales as locale>
					<#if locale != current_locale>
						{
							name: '${locale.getDisplayLanguage(locale)?cap_first}',
							url: '/${locale.getLanguage()}${theme_display.getURLCurrent()?remove_beginning(locale_path)}'
						},
					</#if>
				</#list>
			],
			currentLocale: {
				name: '${current_locale.getDisplayLanguage(current_locale)?cap_first}',
				url: '/${current_locale.getLanguage()}${theme_display.getURLCurrent()?remove_beginning(locale_path)}'
			}
		};

		var productConfigs = [
			{
				name: '<@liferay.language key="dxp" />',
				svgId: '#dxp-logo',
				url: addLocaleParamToURI('${current_locale}', 'categories/26332233260813')
			},
			{
				name: '<@liferay.language key="liferay-cloud-paas-and-saas" />',
				svgId: '#liferay-cloud-logo',
				url: addLocaleParamToURI('${current_locale}', 'categories/360000868032')
			},
			{
				name: '<@liferay.language key="commerce" />',
				svgId: '#commerce-logo',
				url: addLocaleParamToURI('${current_locale}', 'categories/360000867952')
			},
			{
				name: '<@liferay.language key="analytics-cloud" />',
				svgId: '#analytics-cloud-logo',
				url: addLocaleParamToURI('${current_locale}', 'categories/360000872551')
			}
		];

		var resourcesConfigs = [
			{
				description: '<@liferay.language key="products-service-packs-fix-packs-and-security-fixes" />',
				name: '<@liferay.language key="downloads" />',
				svgId: '#downloads',
				url: 'https://customer.liferay.com/downloads'
			},
			{
				description: '<@liferay.language key="articles-on-recurring-topics-issues-and-themes" />',
				name: '<@liferay.language key="knowledge-base" />',
				svgId: '#knowledge-base',
				url: addLocaleParamToURI('${current_locale}', 'categories/360000779952')
			},
			{
				description: '<@liferay.language key="product-guides-tutorials-and-reference-docs" />',
				name: '<@liferay.language key="documentation" />',
				svgId: '#documentation',
				url: addLocaleParamToURI('${current_locale}', 'categories/360000868172')
			},
			{
				description: '<@liferay.language key="reference-the-latest-changes-for-liferay-dxp" />',
				name: '<@liferay.language key="release-notes" />',
				svgId: '#fixpack',
				url: 'https://support.liferay.com/release-notes'
			},
			{
				description: '<@liferay.language key="learn-more-about-designated-contacts-for-your-project" />',
				name: '<@liferay.language key="account-support" />',
				svgId: '#account-support',
				url: '${zendesk_url}/hc/articles/360018414031'
			},
			{
				description: '<@liferay.language key="activate-your-environments-to-start-a-project" />',
				name: '<@liferay.language key="activation-keys" />',
				svgId: '#server-activation',
				url: 'https://support.liferay.com'
			}
		];

		var securityConfigs = [
			{
				description: '<@liferay.language key="how-liferay-approaches-security" />',
				name: '<@liferay.language key="security-overview" />',
				svgId: '#security-overview',
				url: addLocaleParamToURI('${current_locale}', 'articles/360016700231')
			},
			{
				description: '<@liferay.language key="information-and-fixes-for-known-vulnerabilities" />',
				name: '<@liferay.language key="security-advisories" />',
				svgId: '#security-advisories',
				url: addLocaleParamToURI('${current_locale}', 'articles/360018875952')
			},
			{
				description: '<@liferay.language key="process-for-reporting-security-vulnerabilities" />',
				name: '<@liferay.language key="report-security-issues" />',
				svgId: '#report-security-issues',
				url: addLocaleParamToURI('${current_locale}', 'articles/360016700231#Reporting-Security-Issues')
			},
			{
				description: '<@liferay.language key="policy-for-reported-security-issues-in-liferay-products" />',
				name: '<@liferay.language key="security-policies" />',
				svgId: '#security-policy',
				url: addLocaleParamToURI('${current_locale}', 'articles/360016700231#Liferay-Security-Policy')
			}
		];

		var supportConfigs = [
			{
				description: '<@liferay.language key="supported-configurations-for-liferay-products" />',
				name: '<@liferay.language key="compatibility-matrix" />',
				svgId: '#compatibility-matrix',
				url: addLocaleParamToURI('${current_locale}', 'sections/360002103292')
			},
			{
				description: '<@liferay.language key="overview-of-liferays-support-policies" />',
				name: '<@liferay.language key="support-coverage" />',
				svgId: '#support-coverage',
				url: addLocaleParamToURI('${current_locale}', 'categories/360000892912')
			},
			{
				description: '<@liferay.language key="end-of-life-policies-for-liferay-products" />',
				name: '<@liferay.language key="service-life" />',
				svgId: '#service-life',
				url: addLocaleParamToURI('${current_locale}', 'sections/360002241991')
			},
			{
				description: '<@liferay.language key="answers-to-common-support-related-questions" />',
				name: '<@liferay.language key="support-faqs" />',
				svgId: '#support-faqs',
				url: addLocaleParamToURI('${current_locale}', 'articles/360015994791')
			},
			{
				description: '<@liferay.language key="how-liferay-delivers-software-updates" />',
				name: '<@liferay.language key="fix-delivery-method" />',
				svgId: '#fix-delivery-method',
				url: addLocaleParamToURI('${current_locale}', 'articles/360016515831')
			},
			{
				description: '<@liferay.language key="benefits-included-with-each-subscription-level" />',
				name: '<@liferay.language key="service-levels" />',
				svgId: '#service-levels',
				url: addLocaleParamToURI('${current_locale}', 'articles/360016510271')
			}
		];

		function addMenuClickOutsideListener(event) {
			var body = document.querySelector('body');

			if (body) {
				body.removeEventListener('click', handleMenuClickOutside);
				body.addEventListener('click', handleMenuClickOutside);
			}
		}

		function checkClickOutside(event, nodelist) {
			var containsNode = [];

			if (nodelist) {
				Array.prototype.forEach.call(
					nodelist,
					function(node) {
						containsNode.push(node.contains(event.target));
					}
				);
			}

			return !containsNode.some(
				function(node) {
					return node === true;
				}
			);
		}

		function instantiateMegaMenu(event) {
			var currentTargetId = event.currentTarget.id;

			var configs = [];
			var id = '';
			var name = '';

			if (currentTargetId === 'menuItemProducts') {
				configs = productConfigs;
				id = 'megaMenuProducts';
				name = '<@liferay.language key="products" />';
			} else if (currentTargetId === 'menuItemResources') {
				configs = resourcesConfigs;
				id = 'megaMenuResources';
				name = '<@liferay.language key="resources" />';
			} else if (currentTargetId === 'menuItemSecurity') {
				configs = securityConfigs;
				id = 'megaMenuSecurity';
				name = '<@liferay.language key="security" />';
			} else if (currentTargetId === 'menuItemSupport') {
				configs = supportConfigs;
				id = 'megaMenuSupport';
				name = '<@liferay.language key="support" />';
			}

			renderMegaMenu(name, id, configs);

			addMenuClickOutsideListener(event);
		}

		function handleMenuClickOutside(event) {
			if (!languageToggleSelected && checkClickOutside(event, menuItems)) {
				removeActiveClasses();
			} else if (languageToggleSelected) {
				languageToggleSelected = false;
			}
		}

		function removeActiveClasses() {
			Array.prototype.forEach.call(
				menuItems,
				function(item) {
					item.classList.remove('active');
				}
			);
		}

		function renderMegaMenu(name, id, configs) {
			theme.render(
				theme.MegaMenu,
				{
					cardMenuItems: {
						className: 'card-menu',
						configs: configs
					},
					highlightedLinks: highlightedLinks,
					locale: locale,
					name: name
				},
				document.getElementById(id)
			);
		}

		function validateCurrentActiveMenu(target) {
			var targetParentNode = target.parentNode;

			var activeHeading = targetParentNode && targetParentNode.classList.contains('active');

			var activeMenuItem = target.classList.contains('active');

			return (activeHeading || activeMenuItem);
		}

		Array.prototype.forEach.call(
			menuItems,
			function(item) {
				item.classList.remove('hide');

				item.addEventListener(
					'click',
					function(event) {
						if (validateCurrentActiveMenu(event.target)) {
							item.classList.remove('active');
						} else {
							removeActiveClasses();

							item.classList.add('active');

							if (!item.querySelector('.header-menu-content')) {
								instantiateMegaMenu(event);
							}

							var languageToggle = item.querySelector('#languageToggle');

							if (languageToggle) {
								var validateLanguageToggleSelection = function() {
									languageToggleSelected = true;
								}

								languageToggle.removeEventListener('click', validateLanguageToggleSelection);
								languageToggle.addEventListener('click', validateLanguageToggleSelection);
							}
						}
					}
				);
			}
		);
	}());
</script>