# OSB Release Tool

### Set up

#### OSB Release Tool Portlet

Place the _OSB Release Tool_ portlet on a page (not to be confused with _Release Notes_ portet).

---

#### Creating Release Vocabulary

When logged in as an administrator, go to _Control Panel_ and click _Go to Other Site_. Navigate to the _Global_ site. Navigate to _Content_ > _Categories_ > _Releases_.

Click on the dotted menu to _Edit_. Under _Associated Asset Types_, add a _Web Content Article_ asset type and select _OSB Customer Theme - Fix Pack_. Make sure to toggle the _Required_ button and hit _Save_.

Under the _Releases_ vocabulary, its categories are organized by _Liferay Versions_, whose subcategories are fix packs and GA versions listed by name (e.g., _Fix Pack 45_).

When creating a new subcategory under product, the naming convention should begin with 'Fix Pack ' followed by a number (e.g., _Fix Pack 45_). Note, the names can be localized in the user interface by clicking on the country's flags. The _Properties_ field should follow such that _Key_ should be 'version' and _Value_ should be the number of the _Fix Pack_ (for example, '45.0', make sure to include the decimal point). Save the subcategory and proceed with [_Creating Release Web Content_](#creating-release-web-content).

Each category and subcategories can be edited by clicking on the dotted menu and select _Edit_.

---

#### Creating Release Web Content

Go to _Control Panel_ > _Content_ > _Web Content_ on the site where the _OSB Release Tool Portlet_ is located. Create a web content using the _OSB Customer Theme - Fix Pack_ structure. The available fields are:

* Title (should be the name of the release/fix pack)
* Release Date
* Highlights (a container, not a field)
	* Key Highlights
	* Important Changes
	* Known Issues
	* Security

In addition to these fields, there is an option to select a _Fix Pack Subcategory_. Please look at the [_Creating Release Vocabulary_](#creating-release-vocabulary) section of the documentation before proceeding. Under the _Metadata_ section (the section may need to be expanded), click on _Select_ under _Releases (Global)_. This will trigger a popup that shows a folder structure. Expand the folder of whichever _Liferay Version_ the _Fix Pack_ is meant to be associated with, then select the _Fix Pack Version_ (which should match the _Title_ of the web content).

After creating a _Fix Pack_, it should show up in _OSB Release Tool_.

---

#### Create Tab Description

Release Tools has three tabs:
* Highlights
* Changelog
* Module Change

Each tab has a description of varying lengths to provide context to the user. To allow flexibility of updating content and providing translations, these descriptions are web content articles that are rendered under the heading of each tab.

To configure, create a web content article and place the desired copy there. Go into the _Release Tools_ portlet topper and click on the dotted menu. Select _Configuration_. Under the _Setup_ tab are three fields for inputting the _Tab Description Journal Article Ids_. Add the web content article ID in the appropriate field for the tab it's to display under.

---

#### Link Fix Pack Downloads

At the top of _Release Tool_ portlet, there is a filter that allows the user to select a range of products and its associated releases. When a valid range is selected, a download button will appear to allow the user to go to _Downloads Display_ portlet and download the fix pack indicated in the upper range.

In order to display the download link, the fix pack needs to be linked with download web content.

To do that, go to _Web Content_ > _Downloads_ > Select a product of choice, for example _DXP 7.0_ > _Fix Packs_ > Select a fix pack of choice, for example _Fix Pack 1_.

From there, go to the _Metadata_ section of the web content, click on _Select_ under _Releases (Global)_. Select the correct _Fix Pack_ version similar to the steps described in [_Creating Release Web Content_](#creating-release-web-content).