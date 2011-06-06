/*
 * Copyright (c) 2002-2011, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.pluginwizard.web;


/**
 *
 * The class which contains the property keys and other constants
 *
 */
public final class Constants
{
    // Properties for page titles
    public static final String PROPERTY_PAGE_TITLE_CREATE_PLUGIN_DESCRIPTION = "wizard.wizard_plugin_description.pageTitle";

    //Trigger Names
    public static final String PROPERTY_DATABASE_DIRECTORY_TRIGGER = "DatabaseCreationDummy";
    public static final String PROPERTY_JSP_BEAN_DIRECTORY_TRIGGER = "JspBeanTemplateDummy";
    public static final String PROPERTY_BUSINESS_CLASS_DIRECTORY_TRIGGER = "BusinessClassDummy";
    public static final String PROPERTY_BACK_OFFICE_TEMPLATES_DIRECTORY_TRIGGER = "HtmlTemplateBackDummy";
    public static final String PROPERTY_BACK_OFFICE_JSP_DIRECTORY_TRIGGER = "BackOfficeJspDummy";
    public static final String PROPERTY_PROPERTIES_RESOURCES_DIRECTORY_TRIGGER = "PropertiesDummy";

    //File Naming
    public static final String PROPERTY_DATABASE_PREFIX = "create_db_";
    public static final String PROPERTY_JSP_BEAN_SUFFIX = "JspBean";
    public static final String PROPERTY_XPAGE_SUFFIX = "App";

    //The parameters for handling conditional generation
    public static final String PARAM_ADD_BUSINESS_CLASSES = "add_business_classes";
    public static final String PARAM_ADD_SQL_FILES = "add_sql_files";
    public static final String PARAM_ADD_JSP_BEAN = "add_jsp_bean";
    public static final String PARAM_ADD_BACK_OFFICE_TEMPLATE = "add_back_office_template";
    public static final String PARAM_ADD_RESOURCE_FILES = "add_resource_files";
    public static final String PARAM_ADD_BACK_OFFICE_JSP = "add_back_office_jsp";
    public static final String PARAM_ADD_PLUGIN_PROPERTIES_FILE = "add_plugin_properties_file";
    public static final String PARAM_ADD_MAVEN_POM_XML = "add_maven_pom_xml";
    public static final String PARAM_ADD_PLUGIN_XML_DEFINITION = "plugin_xml_definition";
    public static final String PARAM_ADD_SPRING_CONTEXT_XML = "add_spring_context_xml";

    /**
     * Private constructor
     */
    private Constants(  )
    {
    }
}
