<#include "${full_templates_path}/site_wide_notification.ftl" />

<div class="container-fluid container-fluid-max-xl">
	<div class="header-inner row">
		<div class="col-sm-3 header-content">
			<a aria-label="Home" class="header-link" href="${site_logo_url}" title="Home">
				<svg alt="<@liferay.language key="liferay-help-center" />" class="header-logo">
					<use xlink:href="#liferay-waffle" />
				</svg>
			</a>

			<a href="${site_name_url}" title="${site_name}">
				<h2 class="site-name small-title">
					${site_name}
				</h2>
			</a>
		</div>

		<div class="col-sm-5 header-content header-menu-list">
			<div class="hide list-item" id="menuItemProducts">
				<h5>
					<@liferay.language key="products" />

					<svg class="lexicon-icon lexicon-icon-menu-caret">
						<use xlink:href="#menu-caret" />
					</svg>
				</h5>

				<div class="header-menu" id="megaMenuProducts"></div>
			</div>

			<div class="hide list-item" id="menuItemResources">
				<h5>
					<@liferay.language key="resources" />

					<svg class="lexicon-icon lexicon-icon-menu-caret">
						<use xlink:href="#menu-caret" />
					</svg>
				</h5>

				<div class="header-menu" id="megaMenuResources"></div>
			</div>

			<div class="hide list-item" id="menuItemSecurity">
				<h5>
					<@liferay.language key="security" />

					<svg class="lexicon-icon lexicon-icon-menu-caret">
						<use xlink:href="#menu-caret" />
					</svg>
				</h5>

				<div class="header-menu" id="megaMenuSecurity"></div>
			</div>

			<div class="hide list-item" id="menuItemSupport">
				<h5>
					<@liferay.language key="support" />

					<svg class="lexicon-icon lexicon-icon-menu-caret">
						<use xlink:href="#menu-caret" />
					</svg>
				</h5>

				<div class="header-menu" id="megaMenuSupport"></div>
			</div>
		</div>

		<div class="col-sm-4 header-content user-wrapper">
			<div class="user">
				<#if is_signed_in>
					<div class="user-personal-bar" data-toggle="dropdown">
						<img alt="${user_initials}" class="user-icon" src="${user_avatar}" />
					</div>

					<div class="dropdown-menu dropdown-menu-caret dropdown-menu-end" id="user-dropdown">
						<div id="user-menu" role="menu">
							<a class="my-activities" href="${zendesk_url}/hc/${zendesk_locale_util.convertToZendeskLocale(current_locale)}/requests"><@liferay.language key="my-activities" /></a>

							<a href="https://liferay.okta.com/login/login.htm?fromURI=%2Fenduser%2Fsettings"><@liferay.language key="account-settings" /></a>

							<#if liferay_employee>
								<a href="/group/license"><@liferay.language key="license" /></a>
							</#if>

							<#if osb_account_admin || osb_administrator || osb_support_admin>
								<a href="/group/control_panel/manage?p_p_id=1_WAR_osbportlet"><@liferay.language key="osb-admin" /></a>
							</#if>

							<a href="/c/portal/logout"><@liferay.language key="sign-out" /></a>
						</div>
					</div>
				<#else>
					<a class="btn btn-primary" href="${sign_in_url}">${sign_in_text}</a>
				</#if>
			</div>
		</div>
	</div>
</div>