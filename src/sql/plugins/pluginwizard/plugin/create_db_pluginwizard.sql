--
-- Table structure for table pluginwizard_configuration_key
--
DROP TABLE IF EXISTS pluginwizard_configuration_key;
CREATE TABLE pluginwizard_configuration_key
(
	id_key int NOT NULL,
	key_description varchar(255) default NULL,
	key_value varchar(255) default NULL,
	PRIMARY KEY (id_key)
);

--
-- Table structure for table pluginwizard_plugin_application
--
DROP TABLE IF EXISTS pluginwizard_plugin_application;
CREATE TABLE pluginwizard_plugin_application
(
	id_plugin_application int NOT NULL,
	application_name varchar(255) default NULL,
	application_class varchar(255) default NULL,
	PRIMARY KEY (id_plugin_application)
);

--
-- Table structure for table pluginwizard_plugin_attribute
--
DROP TABLE IF EXISTS pluginwizard_plugin_attribute;
CREATE TABLE pluginwizard_plugin_attribute
(
	id_attribute int NOT NULL,
	attribute_type_id int default NULL,
	attribute_name varchar(255) default NULL,
	is_primary_key smallint default NULL,
	is_description smallint default NULL,
	PRIMARY KEY (id_attribute)
);

--
-- Table structure for table pluginwizard_plugin_attribute_type
--
DROP TABLE IF EXISTS pluginwizard_plugin_attribute_type;
CREATE TABLE pluginwizard_plugin_attribute_type
(
	attribute_type_id int NOT NULL,
	attribute_type_name varchar(20) default NULL,
	PRIMARY KEY (attribute_type_id)
);

--
-- Table structure for table pluginwizard_plugin_business
--
DROP TABLE IF EXISTS pluginwizard_plugin_business;
CREATE TABLE pluginwizard_plugin_business
(
	id_business_class int default 0 NOT NULL,
	business_class varchar(255) default '' NOT NULL,
	business_table_name varchar(255) default NULL,
	business_class_primary_key varchar(100) default NULL,
	business_class_description varchar(100) default NULL,
	PRIMARY KEY (id_business_class)
);

--
-- Table structure for table pluginwizard_plugin_business_attribute
--
DROP TABLE IF EXISTS pluginwizard_plugin_business_attribute;
CREATE TABLE pluginwizard_plugin_business_attribute
(
	id_business_class int NOT NULL,
	id_attribute int NOT NULL,
	PRIMARY KEY (id_attribute)
);

--
-- Table structure for table pluginwizard_plugin_feature
--
DROP TABLE IF EXISTS pluginwizard_plugin_feature;
CREATE TABLE pluginwizard_plugin_feature
(
	id_plugin_feature int NOT NULL,
	plugin_feature_title varchar(255) default NULL,
	plugin_feature_level varchar(255) default NULL,
	plugin_feature_url varchar(255) default NULL,
	plugin_feature_description varchar(255) default NULL,
	plugin_feature_label varchar(255) default NULL,
	PRIMARY KEY (id_plugin_feature)
);

--
-- Table structure for table pluginwizard_plugin_feature_business
--
DROP TABLE IF EXISTS pluginwizard_plugin_feature_business;
CREATE TABLE pluginwizard_plugin_feature_business
(
	id_plugin_feature int default NULL,
	id_business_class int NOT NULL,
	PRIMARY KEY (id_business_class)
);

--
-- Table structure for table pluginwizard_plugin_id
--
DROP TABLE IF EXISTS pluginwizard_plugin_id;
CREATE TABLE pluginwizard_plugin_id
(
	id_plugin int NOT NULL,
	plugin_name varchar(255) default '' NOT NULL,
	plugin_class varchar(255) default NULL,
	plugin_description varchar(255) default NULL,
	plugin_documentation varchar(255) default NULL,
	plugin_installation varchar(255) default NULL,
	plugin_changes varchar(255) default NULL,
	plugin_user_guide varchar(255) default NULL,
	plugin_version varchar(255) default NULL,
	plugin_copyright varchar(255) default NULL,
	plugin_icon_url varchar(255) default NULL,
	plugin_provider varchar(255) default NULL,
	plugin_provider_url varchar(255) default NULL,
	plugin_db_pool_required varchar(255) default NULL,
	PRIMARY KEY (id_plugin)
);

--
-- Table structure for table pluginwizard_plugin_id_application
--
DROP TABLE IF EXISTS pluginwizard_plugin_id_application;
CREATE TABLE pluginwizard_plugin_id_application
(
	id_plugin int NOT NULL,
	id_plugin_application int NOT NULL,
	PRIMARY KEY (id_plugin, id_plugin_application)
);

--
-- Table structure for table pluginwizard_plugin_id_feature
--
DROP TABLE IF EXISTS pluginwizard_plugin_id_feature;
CREATE TABLE pluginwizard_plugin_id_feature
(
	id_plugin int NOT NULL,
	id_plugin_feature int NOT NULL,
	PRIMARY KEY (id_plugin, id_plugin_feature)
);

--
-- Table structure for table pluginwizard_plugin_id_portlet
--
DROP TABLE IF EXISTS pluginwizard_plugin_id_portlet;
CREATE TABLE pluginwizard_plugin_id_portlet
(
	id_plugin int NOT NULL,
	id_plugin_portlet int NOT NULL,
	PRIMARY KEY (id_plugin, id_plugin_portlet)
);

--
-- Table structure for table pluginwizard_plugin_id_resource_key
--
DROP TABLE IF EXISTS pluginwizard_plugin_id_resource_key;
CREATE TABLE pluginwizard_plugin_id_resource_key
(
	id_resource_key int NOT NULL,
	id_plugin int NOT NULL,
	PRIMARY KEY (id_resource_key, id_plugin)
);

--
-- Table structure for table pluginwizard_plugin_portlet
--
DROP TABLE IF EXISTS pluginwizard_plugin_portlet;
CREATE TABLE pluginwizard_plugin_portlet
(
	id_plugin_portlet int NOT NULL,
	plugin_portlet_class varchar(255) default NULL,
	plugin_portlet_type_name varchar(255) default NULL,
	plugin_portlet_creation_url varchar(255) default NULL,
	plugin_portlet_update_url varchar(255) default NULL,
	PRIMARY KEY (id_plugin_portlet)
);

--
-- Table structure for table pluginwizard_plugin_resource_key
--
DROP TABLE IF EXISTS pluginwizard_plugin_resource_key;
CREATE TABLE pluginwizard_plugin_resource_key
(
	marker_identifier varchar(255) default NULL,
	english_locale varchar(255) default NULL,
	french_locale varchar(255) default NULL,
	id_resource_key int default 0 NOT NULL,
	PRIMARY KEY (id_resource_key)
);

--
-- Table structure for table pluginwizard_localization_key
--
DROP TABLE IF EXISTS pluginwizard_localization_key;
CREATE TABLE pluginwizard_localization_key
(
	id_key int default 0 NOT NULL,
	key_name varchar(255) default NULL,
	english_locale varchar(255) default NULL,
	french_locale varchar(255) default NULL,
	PRIMARY KEY (id_key)
);
