# OSB Downloads Display

### Set up

#### OSB Downloads Display Portlet

Place the _OSB Downloads Display_ portlet on a page (not to be confused with _OSB Downloads_ portet). Go to the product menu and click on the ellipsis next to the current page. Choose _Configure Page_ and select _OSB Downloads Display Layout_ as the page's layout.

Click on the ellipsis icon inside the portlet's topbar, and open _Configuration_.

In the _Setup_ tab, use the _DDM Structure Key_ dropdown and select either _OSB-CUSTOMER-THEME--DOWNLOAD_ or _OSB-CUSTOMER-THEME--TRIAL-DOWNLOAD_. Save the configuration.

#### Downloads Portlet

There is separate portlet named _Downloads_. Place it on the same page as the _OSB Downloads Display_ portlet and follow the same instructions to open _Configuration_.

Inside the _Setup_ tab, do the following in the _ESA_, _Evaluation EULA_, and _Studio EULA_ tabs:

1. Leave _Language_ as _English (United States)_ unless told otherwise.

1. _URL_ field will be an address to the EULA's agreement content. For testing purposes, any url could work (e.g. _http://localhost:8080?test=test_).

1. _Version Displayed_ should be 1.0.

1. _Required to Accept_ should be 1.0.

#### Custom Fields

Go to _Control Panel_ > _Configuration_ > _Custom Fields_ > _User_. Use the _+_ button on the bottom right to add __three__ custom fields:

Each custom field entry contains two fields: _Key_ and _Type_.

Make a custom field for each of the following:
- _osbCustomerESA_
- _osbCustomerEvaulationEULA_
- _osbCustomerStudioEULA_

All three custom fields will have type _Group of Text Values_.

Setup is complete after this step.

Any _Journal Article_ created with the specified structure will appear in the _OSB Downloads Display_ portlet. See [__Add New Journal Article Downloads__](#newArticles) on how to create _Journal Articles_.

---

### Viewing Journal Article Downloads

#### Multiple Downloads View

By default the _OSB Downloads Display_ portlet will show two filters, _Product_ and _File Type_ which a user can use to filter through the entire list of _Journal Articles_. If there are no existing _Journal Articles_, the results area will show an empty message.

If there are existing _Journal Articles_, the results section will render a table with two columns: _Released_ and _Name_.
* _Released_ corresponds to the _Release Date_ field of the _OSB Customer Theme - Download_ structure.
* _Name_ includes the following information:
	* Title
	* Link (repeatable)
	* Alert
	* Additional Notes
	* Download Dropdown (if more than one download is available)
	* Download Button
	* Download Details

#### Testing Single Download View

A user will typically be taken to the _Journal Article_ housing the download for a single product via Zendesk. However, to test locally, append the following to the current URL in the browser's address bar:

`?p_p_id=com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet_mvcRenderCommandName=%2Fview&_com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet_journalArticleResourcePrimKey=[primkey]`

where __[primkey]__ is the _Journal Article's_ `primarykey`. To obtain the primarykey, simply inspect the target _Journal Article_ and locate the _<div>_ with class `.downloads`. On the id attribute, copy the number after the `namespace`.

---

#### <a name="newArticles">Add New Journal Article Downloads</a>

Go to _Control Panel_ > _Content_ > _Web Content_, select either _OSB Customer Theme - Download_ structure or _OSB Customer Theme - Trial Download_ structure. For both these structures, the available fields are:

* Title
* Summary
* Product
* File Type
* Release Date
* Download Number
* Link (repeatable)
	* Link URL
* Alert Message
* Additional Notes
* Download Group (repeatable; determines number of download groups in dropdown)
	* Download Name (repeatable; determines number of downloads in each download group)
		* Download URL
		* Download Detail (repeatable; each download usually includes 2 Label/Value pairs)
			* Detail Label (e.g., File Size)
			* Detail Value (e.g., MD5)
* Required Agreement

After creating a _Journal Article_, the user should be able to see it in the _OSB Downloads Display_ portlet.

---

### EULA

The _Required Agreement_ field will determine whether a user will need to accept a particular set of terms and agreements before downloading. The options are:

* None
* ESA
* Evaluation EULA
* Studio EULA

If any option other than _None_ is chosen, a modal will be triggered when the blue _Download_ button is clicked. The modal will ask the user to read and agree to accept the terms and conditions.

After accepting the terms and conditions, the _Download_ button inside the modal will change from disabled to enabled to download to start. The user will only need to accept the specified terms and conditions once.

#### Testing EULA

Accepting EULA once will prevent the modal from showing up again. If more testing is needed, go to:

_Control Panel_ > _Users_ > _Users and Organizations_ > find current user > _Miscellaneous_ tab and make sure the following boxes are empty:

1. _osbCustomerESA_

1. _osbCustomerEvaulationEULA_

1. _osbCustomerStudioEULA_

After saving, terms and conditions for _ESA_, _Evaluation EULA_, and _Studio EULA_ will have to be accepted again in order to proceed to download.