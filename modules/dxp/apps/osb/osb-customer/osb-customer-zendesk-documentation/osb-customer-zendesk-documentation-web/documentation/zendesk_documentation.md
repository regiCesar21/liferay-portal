# OSB Zendesk Documentation

### Documentation List

* https://github.com/liferay/liferay-docs
* https://github.com/liferay/liferay-docs-commerce
* https://github.com/liferay/liferay-docs-dxp-cloud

### OSB Documentation Admin

#### Category Menu

* Import Documentation Guide
  * Used to manually upload an Official Documentation ZIP file
* Remove
  * Only viewable if an official documentation guide has not been imported

#### Category Fields

* "Documentation Guide Zip File": the name of the ZIP file built from the official documentation
  * Save the file name without the translation language (e.g. the built ZIP file name is lp-dxp-7.2-.-user-en.zip, should save as lp-dxp-7.2-.-user.zip) as the backend will parse through different locales so long as the beginning file name matches
* "Documentation Original URL": the relative URL to the documentation guide's articles; it's used to generate links to Zendesk articles in this guide.
  * All articles within a documentation guide will share the same relative URL. For example, in the 7.2 User Admin guide all article URLs will start with _/docs/7-2/user/-/knowledge_base/u/_.
  * https://docs.google.com/spreadsheets/d/165Nio1Qtwr-Tz6k-r1dV2wsXApW6nxhUedoeG2LlE7o/edit?ts=5cb46a6f#gid=0
* "Article Labels": the labels for Zendesk to attach to each article
  * The labels should not contain any commas and each value should be on a new line. The labels are provided by Support.
* "Zendesk ID": the ID of the Zendesk Category that the articles will import into
  * Can be found as a Zendesk Admin (_Zendesk Guide Admin_ > _Arrange Content_ icon > select or create a category you want to import articles into > ID is in the URL (https://{zendesk-site}/knowledge/arrange/categories/{zendesk-category-id}?brand_id={brand-id})).
  * If you are given a link, you can view articles within the category and the category ID is in the URL (https://{zendesk-site}/hc/en-us/categories/{zendesk-category-id}).
* "Zendesk User Segment ID": the ID of the Zendesk User Segment that has permissions to view the articles
  * Can be found as a Zendesk Admin (_Zendesk Guide Admin_ > _User Permissions_ icon > select or create a User Segment > ID is in the URL (https://{zendesk-site}/knowledge/user_segments/{zendesk-user-segment-id}?brand_id={brand-id})).

### Documentation Troubleshooting

#### Broken Links

Broken links may need to be investigated with a specific check or a combination of them. An initial check can usually be done on the article itself by checking the article create date within Zendesk or seeing if the Documentation Original URL is correct. If the article create date is recent and the URL format is correct, generally a re-import should resolve the issue.

##### Documentation Build Check

1. Fork and checkout the latest repository source

1. In the terminal, navigate to the directory of the documentation you want to build. You will need to make sure you're in the same directory as the "articles" and "articles-dxp" directories.
  * Changing directory in terminal: "cd {directory-path}"
  * Checking the files and folders in current directory: "ls"

1. Run "ant dist-dxp" which will build the documentation guide into a ZIP file
  * If the build fails, you may need to notify the Documentation Team to fix the build error before you can attempt another article import
  * Check if the failure mentions specific intros, markdowns, and items that are missing

1. If the build is successful, you will find the guide's ZIP file in the "dist" folder

##### Documentation Original URL Check

1. Navigate to _Control Panel_ > _Configuration_ > _OSB Documentation Admin_

1. Find the documentation category of the reported broken link article

1. Check if the Documentation Original URL is in the correct relative URL format (may change depending on Documentation Team's updates)
  * Update the URL if needed. A new import of the articles is necessary if immediate changes are needed.

##### Documentation Source Check

1. Navigate to the correct branch of the documentation repository on GitHub

1. Find and select the article markdown that contains the broken link

1. Click on "Raw" on the GitHub page so you can see the article without any styling or formatting
  * Check if the URL is in the correct relative URL format
  * If the URL needs to be updated, notify the Documentation Team to fix the link

1. Click on "History" on the GitHub page so you can see the commit history
  * Check when the last change to the link or article was made
  * If the change was made after the last scheduled import, a new import of the articles is necessary if immediate changes are needed