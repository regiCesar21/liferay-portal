<!DOCTYPE html>

<#include init />

<html class="aui ${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<meta charset="utf-8" />
	<meta content="minimum-scale=1.0, width=device-width" name="viewport" />
	<meta content="liferay" property="fb:admins" />
	<meta content="${page.getDescription(locale)}" property="og:description" />
	<meta content="${open_graph_image}" property="og:image" />
	<meta content="${open_graph_title}" property="og:title" />
	<meta content="website" property="og:type" />
	<meta content="${open_graph_url}" property="og:url" />

	<title>${the_title} | "<@liferay.language key="liferay" /></title>

	<@liferay_util["include"] page=top_head_include />

	<script src="${javascript_folder}/ldn_faq_toggler.js" type="text/javascript"></script>

	<#if theme_settings["google-analytics-id"] != "">
		<script>
			(function(i, s, o, g, r, a, m) {
				i['GoogleAnalyticsObject'] = r;

				i[r] = i[r] || function() {
					(i[r].q = i[r].q || []).push(arguments);
				};

				i[r].l = 1 * new Date();

				a = s.createElement(o);
				m = s.getElementsByTagName(o)[0];

				a.async = 1;
				a.src = g;
				m.parentNode.insertBefore(a, m);
			})(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

			ga('create', '${theme_settings["google-analytics-id"]}', 'auto');
			ga('send', 'pageview');
		</script>
	</#if>
</head>

<body class="${css_class}" id="body">

<#if validator.isNotNull(google_tag_manager_id)>
	<noscript>
		<iframe height="0" src="//www.googletagmanager.com/ns.html?id=$google_tag_manager_id" style="display: none; visibility: hidden;" width="0"></iframe>
	</noscript>

	<script>
		(function(w, d, s, l, i) {
			w[l] = w[l] || [];

			w[l].push(
				{
					event: 'gtm.js',
					'gtm.start': new Date().getTime()
				}
			);

			var dl = l != 'dataLayer' ? '&l=' + l : '';
			var f = d.getElementsByTagName(s)[0];
			var j = d.createElement(s);

			j.async = true;
			j.src = '//www.googletagmanager.com/gtm.js?id=' + i + dl;

			f.parentNode.insertBefore(j, f);
		})(window, document, 'script', 'dataLayer', '${google_tag_manager_id}');
	</script>
</#if>

<div id="body-default">
	<a class="hide-accessible" href="#main-content" id="skip-to-content"><@liferay.language key="skip-to-content" /></a>

	<@liferay_util["include"] page=body_top_include />

	<#if can_view_control_panel>
		<@liferay.control_menu />
	</#if>

	<div class="container-fluid" id="wrapper">
		<header class="banner" id="banner" role="banner">
			<#if validator.isNotNull(liferay_help_center_banner)>
				<div class="liferay-help-center-banner">
					<@liferay.language_format
						arguments="${new_help_center_url}"
						key="you-have-accessed-the-old-customer-portal-documentation-site-which-has-been-relocated-to-the-liferay-help-center"
					/>
				</div>
			</#if>

			<#if getterUtil.getBoolean(theme_settings["custom-site-nav-logo"]) && (theme_settings["custom-site-url"] != "")>
				<div class="${logo_css_class} custom-site-nav">
					<#if theme_settings["custom-site-lang-key"] != "">
						<a class="custom-site-url" href="${theme_settings["custom-site-url"]}">
							<img alt="Liferay" height="36" src="${images_folder}/custom/liferay_customer_portal_logo.svg" />
						</a>
					</#if>
				</div>
			<#else>
				<a class="${logo_css_class}" href="${theme_settings["liferay-home-url"]}">
					<img alt="Liferay" src="${images_folder}/custom/heading.png" />
				</a>
			</#if>

			<#if has_navigation>
				<#include "${full_templates_path}/navigation.ftl" />
			</#if>
		</header>

		<div class="documentation-search ${liferay_help_center_banner}">
			<#assign redirect = paramUtil.getString(request, "redirect") />

			<#if validator.isNull(redirect)>
				<#assign redirect = paramUtil.getString(request, "_3_WAR_osbknowledgebaseportlet_redirect") />
			</#if>

			<#if validator.isNotNull(redirect)>
				<a class="results" href="${redirect}">
					<svg class="icon-monospaced lexicon-icon lexicon-icon-angle-left" role="img" viewBox="0 0 512 512">
						<path class="lexicon-icon-outline" d="M114.106 254.607c0.22 6.936 2.972 13.811 8.272 19.11l227.222 227.221c11.026 11.058 28.94 11.058 39.999 0 11.058-11.026 11.058-28.94 0-39.999l-206.333-206.333c0 0 206.333-206.333 206.333-206.333 11.058-11.059 11.058-28.973 0-39.999-11.058-11.059-28.973-11.059-39.999 0l-227.221 227.221c-5.3 5.3-8.052 12.174-8.273 19.111z"></path>
					</svg>

					<span class="responsive-hidden">
						<@liferay.language key="results" />
					</span>
				</a>
			</#if>

			<form action="/documentation/search" class="doc-search-form" method="get" name="docSearchFm">
				<input name="p_p_id" type="hidden" value="1_WAR_osbknowledgebaseportlet" />
				<input name="p_p_state" type="hidden" value="normal" />

				<#assign keywords = paramUtil.getString(request, "_1_WAR_osbknowledgebaseportlet_keywords") />

				<input class="keyword-search-input" id="_1_WAR_osbknowledgebaseportlet_keywords" name="_1_WAR_osbknowledgebaseportlet_keywords" placeholder="<@liferay.language key="search-documentation-by-keyword" />" type="search" value="${htmlUtil.escapeAttribute(keywords)}" />

				<svg class="doc-search-icon lexicon-icon lexicon-icon-search" viewBox="0 0 512 512">
					<path class="lexicon-icon-outline" d="M503.254 467.861l-133.645-133.645c27.671-35.13 44.344-79.327 44.344-127.415 0-113.784-92.578-206.362-206.362-206.362s-206.362 92.578-206.362 206.362 92.578 206.362 206.362 206.362c47.268 0 90.735-16.146 125.572-42.969l133.851 133.851c5.002 5.002 11.554 7.488 18.106 7.488s13.104-2.486 18.106-7.488c10.004-10.003 10.004-26.209 0.029-36.183zM52.446 206.801c0-85.558 69.616-155.173 155.173-155.173s155.174 69.616 155.174 155.173-69.616 155.173-155.173 155.173-155.173-69.616-155.173-155.173z"></path>
				</svg>
			</form>
		</div>

		<#if selectable>
			<@liferay_util["include"] page=content_include />
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			<@liferay_theme["wrap-portlet"] page="portlet.ftl">
				<@liferay_util["include"] page=content_include />
			</@>
		</#if>

		<footer class="col-md-12 footer">
			<div class="col-md-12 social-nav">
				<ul class="nav-menu">
					<li class="nav-item parent-item root-item">
						<a href="http://www.facebook.com/pages/Liferay/45119213107" target="_blank">
							<span class="facebook social-img"></span>

							<span class="responsive-hidden"><@liferay.language key="facebook" /></span>
						</a>
					</li>
					<li class="nav-item parent-item root-item">
						<a href="http://www.linkedin.com/company/83609?trk=NUS_CMPY_TWIT" target="_blank">
							<span class="linkedin social-img"></span>

							<span class="responsive-hidden">LinkedIn</span>
						</a>
					</li>
					<li class="nav-item parent-item root-item">
						<a href="http://www.twitter.com/liferay" target="_blank">
							<span class="social-img twitter"></span>

							<span class="responsive-hidden"><@liferay.language key="twitter" /></span>
						</a>
					</li>
				</ul>
			</div>

			<div class="responsive-only">
				<nav class="${nav_css_class} col-md-12 footer-navigation">
					<@print_navigation layout_friendly_url = mobile_footer_nav_friendly_url />
				</nav>
			</div>

			<div class="responsive-hidden">
				<nav class="${nav_css_class} col-md-12 footer-navigation">
					<@print_navigation layout_friendly_url = footer_nav_friendly_url />
				</nav>
			</div>

			<div class="responsive-hidden">
				<nav class="${nav_css_class} social-media-panel">
					<div class="facebook-buttons">
						<script type="text/javascript">
							(function(script, id) {
								function fbLazyLoad() {
									var firstScript = document.getElementsByTagName(script)[0];
									var newScript = document.createElement(script);

									if (!document.getElementById(id)) {
										newScript.id = id;

										newScript.src = '//connect.facebook.net/en_US/all.js#xfbml=1';

										firstScript.parentNode.insertBefore(newScript, firstScript);
									}
								}

								AUI.Env.add(window, 'load', fbLazyLoad, false);
							}('script', 'facebook-jssdk'));
						</script>

						<div class="fb-like" data-action="like" data-href="https://www.facebook.com/liferay" data-layout="button_count" data-show-faces="true"></div>
					</div>
				</nav>
			</div>

			<div class="col-md-12 copyright">
				&copy; ${the_year} Liferay Inc. All Rights Reserved
			</div>
		</footer>
	</div>

	<@liferay_util["include"] page=body_bottom_include />

	<@liferay_util["include"] page=bottom_include />
</div>

</body>

</html>