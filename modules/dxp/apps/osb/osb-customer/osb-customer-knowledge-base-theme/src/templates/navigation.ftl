<div class="responsive-only site-navigation-menu-toggle toggle-menu" data-target-node="#navigationContainer" id="navigationToggle"></div>

<div class="responsive-content-height site-navigation" id="navigationContainer">
	<div class="nav">
		<#if stringUtil.equals(nav_options, "static")>
			<nav class="${nav_css_class}">
				<@print_navigation layout_friendly_url = main_nav_friendly_url />
			</nav>
		<#else>
			<nav class="${nav_css_class}">
				<ul class="nav-menu">
					<#list nav_items as nav_item>
						<#assign nav_item_css_class = "nav-item root-item" />

						<#if nav_item.isSelected()>
							<#assign nav_item_css_class = nav_item_css_class + " selected" />
						</#if>

						<#if nav_item?is_first>
							<#assign nav_item_css_class = nav_item_css_class + " first" />
						<#elseif nav_item?is_last>
							<#assign nav_item_css_class = nav_item_css_class + " last" />
						</#if>

						<li class="${nav_item_css_class}">
							<a href="${nav_item.getURL()}" onClick="_gaq.push(['_trackEvent', 'Navigation Clicks', 'Community Navigation', '${nav_item.getName()}']);" ${nav_item.getTarget()}>
								<span>${nav_item.getName()}</span>
							</a>
						</li>
					</#list>
				</ul>
			</nav>
		</#if>
	</div>

	<div class="nav-search">
		<div class="nav-search-content toggle-menu-content" id="navigationSearchContent">
			<form action="/documentation/search" class="nav-search-form" method="get" name="searchFm">
				<input name="p_p_id" type="hidden" value="1_WAR_osbknowledgebaseportlet" />
				<input name="p_p_state" type="hidden" value="normal" />

				<input class="lfr-search-input" name="_1_WAR_osbknowledgebaseportlet_keywords" size="30" title="<@liferay.language key="search" />" type="search" value="" />
			</form>
		</div>

		<div class="nav-search-toggle toggle-menu" data-target-node="#navigationSearchContent"></div>
	</div>

	<#assign main_menu_style = "" />

	<#if is_signed_in>
		<#assign main_menu_style = "style='background-image: url(" + user.getPortraitURL(theme_display) + "); background-size: cover;'" />
	</#if>

	<div class="nav-user toggle-menu" ${main_menu_style}>
		<ul class="drop-down-menu responsive-content-height toggle-menu-content">
			<#if is_signed_in>
				<li class="my-places">
					<ul>
						<li>
							<a href="https://www.liferay.com/web/${user.getScreenName()}"><@liferay.language key="account-home" /></a>
						</li>
						<li class="user-profile">
							<a href="https://www.liferay.com/web/${user.getScreenName()}/profile"><@liferay.language key="profile" /></a>
						</li>

						<#if customer || liferay_employee || partner>
							<li>
								<a href="https://www.liferay.com/group/customer"><@liferay.language key="customer-portal" /></a>
							</li>
						</#if>

						<#if liferay_employee || partner>
							<li>
								<a href="https://www.liferay.com/group/partner"><@liferay.language key="partner-portal" /></a>
							</li>
						</#if>

						<#if show_my_places>
							<#assign my_places = user.getMySiteGroups() />

							<#list my_places as place>
								<#if place.hasPublicLayouts() || place.hasPrivateLayouts()>
									<#assign
										layout_private_name = place.getDescriptiveName()
										layout_public_name = place.getDescriptiveName()

										url = place.getPathFriendlyURL(false, theme_display) + place.getFriendlyURL()
									/>

									<#if place.hasPrivateLayouts() && place.hasPublicLayouts()>
										<#assign
											layout_private_name = layout_private_name + languageUtil.get(locale, "private")
											layout_public_name = layout_public_name + languageUtil.get(locale, "public")
										/>
									</#if>

									<#if my_places?size gt 2>
										<#assign site_css_class = "site" />

										<#if place.getGroupId() == group_id>
											<#assign site_css_class = site_css_class + " selected" />
										</#if>

										<#if place.hasPublicLayouts()>
											<li class="public ${site_css_class}">
												<a href="${url}">${layout_public_name}</a>
											</li>
										</#if>

										<#if place.hasPrivateLayouts()>
											<#assign url = place.getPathFriendlyURL(true, theme_display) + place.getFriendlyURL() />

											<li class="private ${site_css_class}">
												<a href="${url}">${layout_private_name}</a>
											</li>
										</#if>
									</#if>
								</#if>
							</#list>
						</#if>
					</ul>
				</li>
			</#if>

			<li class="language-toggle parent-item toggle-menu">
				<a href="javascript:;">
					<@liferay.language key="language" />
				</a>

				<span class="children-marker"></span>

				<@liferay.languages />
			</li>

			<#if is_signed_in>
				<li class="sign-in-button" id="signOutBtn">
					<a href="${sign_out_url}?referer=${theme_display.getURLCurrent()}">${sign_out_text}</a>
				</li>
			<#else>
				<li class="sign-in-button" id="signInBtn">
					<a href="${sign_in_url}">${sign_in_text}</a>
				</li>
			</#if>
		</ul>
	</div>
</div>