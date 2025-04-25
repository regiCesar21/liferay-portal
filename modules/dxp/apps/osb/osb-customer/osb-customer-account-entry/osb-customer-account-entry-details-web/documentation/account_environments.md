# Account Environment Configurations

### Viewing Environment Configurations

#### Basic Case

By default all of the following fields show up when a user is looking at a particular environment configuration, even if no value is present for that field:
* Environment name
* Product
* (Commerce Version)
* Liferay version
* Operating Systems
* Java Version
* Application Server
* Database
* Browser
* Portal ext properties file
* Patch info file

#### Special Cases
* _Commerce_ products will include a new _Commerce Version_ field.
  * _Commerce_ products can contain different environment configuration dropdown options than other products.
* Only product version _6.2_ and above will have the _Cloud Services_ field.
* Only _DXP_ (including _Commerce_) products will have the _Search_ field.
_Special fields are only displayed when there is value in it's associated field._
  * _Commerce_ products are limited to _DXP_ offerings (ie. 7.1 and 7.2) and currently not released individually so when selecting _Commerce_ and a _DXP_ version, the search field should appear.

---

### Add New Environment Configurations

By default the user will be able to fill in the environment _Name_ and choose a _Product_ that they have purchased. Once a _Product_ is selected, they can choose the corresponding _Liferay Version_ for that product. The options for the rest of the fields will populate according to the _Product_ and _Liferay Version_ selected.

#### Basic Case

The available fields for adding a new environment configuration are:
* Environment name
* Product
* (Commerce version)
* Liferay version
* Operating Systems
* Java Version
* Application Server
* Database
* Browser
* Portal ext properties file
* Patch info file

#### Special Cases

##### Commerce Products

When selecting any _Commerce_ product, the user will see an additional select field called _Commerce Version_. The field will only appear after a commerce product is selected, and it will show up in between the _Product_ and _Liferay Version_ fields. The _Commerce Version_ field refers to which version of the commerce product the user wants to select. Unlike the normal use case, _Liferay Version_ will be disabled until a _Commerce Version_ is selected. Once the user selects a _Commerce Version_, the _Liferay Version_ field will be enabled and the rest of the form fields should look the same. The user will still be required to fill out fields such as _Operating System_, _Java Version_, _Application Server_, etc.

##### Search Option

For any _DXP_ product, the user will see an additional multi-select field.

Based on the offering, a customer could have the _Standard Search_ option or the _Enterprise Search_ option. The multi-select field will populate different values based on the type of search the currently selected _Product_ is associated with.

This field is not required.

##### Cloud Services

For any _Liferay Version_ 6.2 and above, there will be an additional field for _Cloud Services_.

This field is a not required.

##### Other Operating Systems

`Other` is a value for the _Operating System_ select field. When `Other` is selected, a _Custom Operating System_ input text field will appear for the user to enter a value.

This field is not required.

#### Form Validation

The required fields for adding a new environment configuration are:
* Environment name
* Product
* Commerce version (if _Product_ selected is a commerce product)
* Liferay version
* Operating Systems
* Java Version
* Application Server
* Database
* Portal ext properties file
* Patch info file