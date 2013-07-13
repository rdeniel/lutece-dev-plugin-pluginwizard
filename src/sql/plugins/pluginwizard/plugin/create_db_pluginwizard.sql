--
-- Table structure for table pluginwizard_plugin_model
--
DROP TABLE IF EXISTS pluginwizard_plugin_model;
CREATE TABLE pluginwizard_plugin_model
(

    id_plugin int NOT NULL,
    name varchar(255) default NULL,
    model_json long varchar default NULL,
    PRIMARY KEY (id_plugin)
);

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
-- Table structure for table pluginwizard_plugin_resource_key
--
DROP TABLE IF EXISTS pluginwizard_plugin_resource_key;
CREATE TABLE pluginwizard_plugin_resource_key
(
	id_resource_key int default 0 NOT NULL,
	marker_identifier varchar(255) default NULL,
	english_locale varchar(255) default NULL,
	french_locale varchar(255) default NULL,
        id_plugin int default 0 NOT NULL,
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


DROP TABLE IF EXISTS pluginwizard_plugin_application;
DROP TABLE IF EXISTS pluginwizard_plugin_attribute;
DROP TABLE IF EXISTS pluginwizard_plugin_attribute_type;
DROP TABLE IF EXISTS pluginwizard_plugin_business;
DROP TABLE IF EXISTS pluginwizard_plugin_feature;
DROP TABLE IF EXISTS pluginwizard_plugin_id;
DROP TABLE IF EXISTS pluginwizard_plugin_portlet;
