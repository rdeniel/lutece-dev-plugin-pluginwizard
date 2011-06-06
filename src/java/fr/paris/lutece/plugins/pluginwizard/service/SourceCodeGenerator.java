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
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKey;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClassHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplication;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplicationHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeatureHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortletHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.ResourceKey;
import fr.paris.lutece.plugins.pluginwizard.business.model.ResourceKeyHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;


/**
 *
 * This class is used for source code generation( sql scripts, resource files, etc)
 *
 */
public final class SourceCodeGenerator
{
    private static final String PROPERTY_GENERATOR = "pluginwizard.generator";
    private static final String TEMPLATE_DATABASE_SQL_SCRIPT = "/skin/plugins/pluginwizard/templates/pluginwizard_create_db.html";
    private static final String TEMPLATE_JSPBEAN_CODE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_jspbean_template.html";
    private static final String TEMPLATE_PROPERTIES_KEYS = "/skin/plugins/pluginwizard/templates/pluginwizard_properties_keys.html";
    private static final String TEMPLATE_PROPERTIES_KEYS_GENERATED = "/skin/plugins/pluginwizard/templates/pluginwizard_properties_keys_generated.html";
    private static final String TEMPLATE_JSP_FILE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_jsp_files.html";
    private static final String TEMPLATE_PORTLET_JSP_FILE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_jsp_files.html";
    private static final String TEMPLATE_PORTLET_HTML_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_template_files.html";
    private static final String TEMPLATE_PORTLET_XSL_FILE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_xsl_files.html";
    private static final String TEMPLATE_PORTLET_JSPBEAN_FILE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_jspbean.html";
    private static final String TEMPLATE_PORTLET_FILE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_files.html";
    private static final String TEMPLATE_HTML_TEMPLATE_MODEL = "/skin/plugins/pluginwizard/templates/pluginwizard_html_template_model.html";
    private static final String TEMPLATE_PROPERTIES_FILE = "/skin/plugins/pluginwizard/templates/pluginwizard_properties_file.html";
    private static final String TEMPLATE_POM_XML = "/skin/plugins/pluginwizard/templates/pluginwizard_pom_xml.html";
    private static final String TEMPLATE_PORTLET_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_template.html";
    private static final String TEMPLATE_XPAGE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_xpage_template.html";
    private static final String TEMPLATE_SPRING_CONTEXT_XML = "/skin/plugins/pluginwizard/templates/pluginwizard_spring_context_xml.html";
    private static final String TEMPLATE_PLUGIN_XML_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_xml_template.html";
    private static final String MARK_LIST_BUSINESS_CLASS = "list_business_class";
    private static final String MARK_PLUGIN_APPLICATION = "plugin_application";
    private static final String MARK_PLUGIN_MODEL = "plugin_model";
    private static final String MARK_PLUGIN_NAME = "plugin_name";
    private static final String MARK_BUSINESS_CLASS = "business_class";
    private static final String MARK_BUSINESS_CLASSES = "business_classes";
    private static final String MARK_JSP_TYPE = "jsp_type";
    private static final String MARK_PORTLET_TEMPLATE_TYPE = "template_type";
    private static final String MARK_PORTLET_JSP_TYPE = "portlet_jsp_type";
    private static final String MARK_PORTLET = "portlet";
    private static final String MARK_PORTLET_FILE_TYPE = "portlet_file_type";
    private static final String MARK_TEMPLATE_TYPE = "template_type";
    private static final String MARK_SQL_TYPE = "sql_type";
    private static final String MARK_LANGUAGE = "language";
    private static final String MARK_I18N_BRACKETS_OPEN = "i18n_open";
    private static final String MARK_I18N_BRACKETS_CLOSE = "i18n_close";
    private static final String MARK_VARIABLE = "variable";
    private static final String MARK_BRACKETS_OPEN = "bra_open";
    private static final String MARK_BRACKETS_CLOSE = "bra_close";
    private static final String MARK_MACRO = "macro";
    private static final String MARK_PLUGIN = "plugin";
    private static final String MARK_LIST_BUSINESS_CLASSES = "business_classes";
    private static final String MARK_RESOURCE_KEY_LIST = "list_resource";
    private static final String MARK_LIST_FEATURES = "features";
    private static final String MARK_LIST_APPLICATIONS = "applications";
    private static final String MARK_LIST_PORTLETS = "portlets";

    /**
     * Constructor is private
     */
    private SourceCodeGenerator(  )
    {
    }

    /**
     * Returns the source code of a business object
     * @param businessClass The business class
     * @param nGenerationType The type of generation(DAO,Home,etc)
     * @return The java source code of the business object
     */
    public static String getSourceCode( BusinessClass businessClass, int nGenerationType )
    {
        String strPage = "";

        Generator generator = new Generator(  );
        generator.setTemplate( getTemplate( nGenerationType ) );
        strPage = generator.generate( businessClass );

        return strPage;
    }

    /**
     * Gets the Jsp File of a business class
     * @param businessClass The business class
     * @param strPluginName The generated plugin name
     * @param nJspType The type of jsp
     * @return The source code of the jsp
     */
    public static String getJspFile( BusinessClass businessClass, String strPluginName, int nJspType )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_BUSINESS_CLASS, businessClass );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_JSP_TYPE, nJspType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_JSP_FILE_TEMPLATE, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }

    /**
     * Gets the portlet Jsp File
     * @param portlet The portlet
     * @param strPluginName the plugin name
     * @param nPortletJspType The type of portlet
     * @return The source code of the portlet jsp
     */
    public static String getPortletJspFile( PluginPortlet portlet, String strPluginName, int nPortletJspType )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_PORTLET_JSP_TYPE, nPortletJspType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_JSP_FILE_TEMPLATE,
                new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * Gets the Portlet Jsp Bean
     * @param portlet The portlet
     * @param strPluginName The generated plugin name
     * @return The source code of the jsp
     */
    public static String getPortletJspBean( PluginPortlet portlet, String strPluginName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_JSPBEAN_FILE_TEMPLATE,
                new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * Returns the necessary sql dump of creation of plugin and core
     * @param nSqlType The type of the sql
     * @param pluginModel The plugin Model
     * @param listBusinessClasses The list of business classes
     * @param listFeatures The list of admin features
     * @param listApplcations The list of XPages
     * @param listPortlets The list of portlets
     * @return The corresponding sql output
     */
    public static String getSqlScript( int nSqlType, PluginModel pluginModel,
        Collection<BusinessClass> listBusinessClasses, Collection<PluginFeature> listFeatures,
        Collection<PluginApplication> listApplcations, Collection<PluginPortlet> listPortlets )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PLUGIN, pluginModel );

        model.put( MARK_LIST_FEATURES, listFeatures );
        model.put( MARK_LIST_APPLICATIONS, listApplcations );
        model.put( MARK_LIST_PORTLETS, listPortlets );
        model.put( MARK_LIST_BUSINESS_CLASSES, listBusinessClasses );
        model.put( MARK_SQL_TYPE, nSqlType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_DATABASE_SQL_SCRIPT, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }

    /**
     * Return template of generation
     * @param nIndex the index
     * @return the template
     */
    private static String getTemplate( int nIndex )
    {
        String strTemplate = AppPropertiesService.getProperty( PROPERTY_GENERATOR + nIndex + ".template" );

        return strTemplate;
    }

    /**
     * The properties file content
     * @param nPluginId The id of the plugin
     * @param plugin The plugin
     * @return The content of configuration properties file
     */
    public static String getPropertiesFileCode( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PLUGIN, pluginModel );

        Collection<BusinessClass> listClasses = new ArrayList<BusinessClass>(  );

        Collection<PluginFeature> listFeaturesPlugin = PluginFeatureHome.findByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        for ( PluginFeature feature : listFeaturesPlugin )
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ),
                    nPluginId, plugin );
            listClasses.addAll( listBusinessClasses );
        }

        model.put( MARK_BUSINESS_CLASSES, listClasses );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PROPERTIES_FILE, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }

    /**
     * Produces content of the maven pom.xml file
     * @param nPluginId The id of the plugin
     * @param plugin The plugin
     * @return The content of the pom.xml
     */
    public static String getPomXmlCode( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );;
        Collection<ConfigurationKey> listKeys = ConfigurationKeyHome.getConfigurationKeysList( plugin );

        //Fetches the actual configuration values to be replaced in the templates
        for ( ConfigurationKey key : listKeys )
        {
            model.put( key.getKeyDescription(  ).trim(  ), key.getKeyValue(  ) );
        }

        model.put( MARK_PLUGIN, pluginModel );

        Collection<BusinessClass> listClasses = new ArrayList<BusinessClass>(  );

        Collection<PluginFeature> listFeaturesPlugin = PluginFeatureHome.findByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        for ( PluginFeature feature : listFeaturesPlugin )
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ),
                    nPluginId, plugin );
            listClasses.addAll( listBusinessClasses );
        }

        model.put( MARK_BUSINESS_CLASSES, listClasses );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_POM_XML, new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * Produces the java code of the portlet file
     * @param nPluginId The id of the plugin
     * @param plugin The plugin
     * @return The portlet java code
     */
    public static String getPortletCode( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PLUGIN, pluginModel );

        Collection<BusinessClass> listClasses = new ArrayList<BusinessClass>(  );

        Collection<PluginFeature> listFeaturesPlugin = PluginFeatureHome.findByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        for ( PluginFeature feature : listFeaturesPlugin )
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ),
                    nPluginId, plugin );
            listClasses.addAll( listBusinessClasses );
        }

        model.put( MARK_BUSINESS_CLASSES, listClasses );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_TEMPLATE, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }

    /**
     * Generates the XPage source code
     * @param nPluginId The id of the plugin
     * @param plugin The plugin
     * @param nIdPluginApplication id of the plugin application
     * @return The code of the XPage generated
     */
    public static String getXPageCode( int nPluginId, Plugin plugin, int nIdPluginApplication )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PLUGIN, pluginModel );

        model.put( MARK_PLUGIN_MODEL, pluginModel );
        model.put( MARK_PLUGIN_APPLICATION, PluginApplicationHome.findByPrimaryKey( nIdPluginApplication, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_XPAGE_TEMPLATE, new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * Produces the spring context xml file
     * @param nPluginId The id of the plugin
     * @param plugin the plugin
     * @return the content if the spring context file
     */
    public static String getSpringContextCode( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PLUGIN, pluginModel );

        Collection<BusinessClass> listClasses = new ArrayList<BusinessClass>(  );

        Collection<PluginFeature> listFeaturesPlugin = PluginFeatureHome.findByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        for ( PluginFeature feature : listFeaturesPlugin )
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ),
                    nPluginId, plugin );
            listClasses.addAll( listBusinessClasses );
        }

        Collection<PluginPortlet> listPortlets = PluginPortletHome.findByPlugin( nPluginId, plugin );

        model.put( MARK_LIST_PORTLETS, listPortlets );
        model.put( MARK_BUSINESS_CLASSES, listClasses );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_SPRING_CONTEXT_XML, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }

    /**
     * Returns the text content of the plugin xml file
     * @param nPluginId The id of the plugin
     * @param plugin The plugin
     * @return The plugin xml file content of the plugin
     */
    public static String getPluginXmlCode( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PLUGIN, pluginModel );

        int nIdPlugin = pluginModel.getIdPlugin(  );
        Collection<PluginFeature> listFeatures = PluginFeatureHome.findByPlugin( nIdPlugin, plugin );

        Collection<PluginApplication> listApplcations = PluginApplicationHome.findByPlugin( nIdPlugin, plugin );
        Collection<PluginPortlet> listPortlets = PluginPortletHome.findByPlugin( nPluginId, plugin );
        model.put( MARK_LIST_FEATURES, listFeatures );
        model.put( MARK_LIST_APPLICATIONS, listApplcations );
        model.put( MARK_LIST_PORTLETS, listPortlets );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PLUGIN_XML_TEMPLATE, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }

    /**
     * Return JspBean code
     * @param pluginModel The plugin model
     * @param listBusinessClasses The list of business classes
     * @return the template The source code of the Jsp Bean
     */
    public static String getJspBeanCode( PluginModel pluginModel, Collection<BusinessClass> listBusinessClasses )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;

        model.put( MARK_LIST_BUSINESS_CLASS, listBusinessClasses );
        model.put( MARK_PLUGIN_MODEL, pluginModel );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_JSPBEAN_CODE_TEMPLATE,
                new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * Fetches the locale keys needed by front and back office
     * @param nPluginId The id of the plugin
     * @param strLanguage The language needed
     * @param plugin The plugin
     * @return The Locale keys
     */
    public static String getLocalePropertiesKeys( int nPluginId, String strLanguage, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        Collection<ResourceKey> listResourceKey = ResourceKeyHome.getResourceKeysList( nPluginId, plugin );
        model.put( MARK_RESOURCE_KEY_LIST, listResourceKey );
        model.put( MARK_LANGUAGE, strLanguage );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PROPERTIES_KEYS_GENERATED,
                new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * The method will store all relevant i18n keys in the database
     * @param nPluginId The id of the generated plugin
     * @param plugin The Plugin
     */
    public static void storeKeys( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        ArrayList<BusinessClass> listBusinessClasses = new ArrayList<BusinessClass>(  );
        Collection<PluginFeature> listFeatures = PluginFeatureHome.findByPlugin( nPluginId, plugin );

        for ( PluginFeature feature : listFeatures )
        {
            Collection<BusinessClass> listClassesFeature = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ),
                    nPluginId, plugin );
            listBusinessClasses.addAll( listClassesFeature );
        }

        String strPluginName = pluginModel.getPluginName(  );
        ArrayList<String> listKeys = findResourceKeys( listBusinessClasses, strPluginName, nPluginId, plugin );
        //Method will add all the keys for the generated plugin in the database
        ResourceKeyHome.addEmptyKeys( pluginModel.getIdPlugin(  ), listKeys, plugin );
    }

    /**
     * Fetches all the resource keys
     * @param listBusinessClasses The list of business classes
     * @param strPluginName The plugin name
     * @param plugin The plugin
     * @param nPluginId The id of the plugin to be generated
     * @return A list of the resource keys
     */
    public static ArrayList<String> findResourceKeys( List<BusinessClass> listBusinessClasses, String strPluginName,
        int nPluginId, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        Collection<PluginPortlet> listPortlets = PluginPortletHome.findByPlugin( nPluginId, plugin );

        model.put( MARK_LIST_PORTLETS, listPortlets );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_LIST_BUSINESS_CLASS, listBusinessClasses );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PROPERTIES_KEYS, new Locale( "en", "US" ),
                model );

        StringTokenizer st = new StringTokenizer( template.getHtml(  ), "\n" );
        ArrayList<String> listKeys = new ArrayList<String>(  );

        while ( st.hasMoreTokens(  ) )
        {
            listKeys.add( (String) st.nextElement(  ) );
        }

        return listKeys;
    }

    /**
     * Gets the code of a create template for a specific business object
     * @param listAllBusinessClasses A list of business classes attached to plugin
     * @param businessClass The instance of the business class
     * @param nTemplateType The type of template
     * @param plugin The plugin
     * @return The html code of the create template
     */
    public static String getCreateHtmlCode( Collection<BusinessClass> listAllBusinessClasses,
        BusinessClass businessClass, int nTemplateType, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;

        model.put( MARK_PLUGIN_NAME, businessClass.getBusinessPluginName(  ) );
        model.put( MARK_I18N_BRACKETS_OPEN, "@@i18n{" );
        model.put( MARK_I18N_BRACKETS_CLOSE, "}" );
        model.put( MARK_VARIABLE, "@@" );
        model.put( MARK_BRACKETS_OPEN, "${" );
        model.put( MARK_BRACKETS_CLOSE, "}" );
        model.put( MARK_BUSINESS_CLASS, businessClass );
        model.put( MARK_LIST_BUSINESS_CLASSES, listAllBusinessClasses );

        model.put( MARK_TEMPLATE_TYPE, nTemplateType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_HTML_TEMPLATE_MODEL, new Locale( "en", "US" ),
                model );

        return template.getHtml(  ).replace( "@@", "#" );
    }

    /**
     * Produces text content of java file used to build a portlet
     * @param portlet The instance of a portlet
     * @param strPluginName The plugin name
     * @param nPortletFileType The type of portlet file
     * @return The content of the portlet file
     */
    public static String getPortletFile( PluginPortlet portlet, String strPluginName, int nPortletFileType )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;

        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_PORTLET_FILE_TYPE, nPortletFileType + "" );
        AppLogService.info( portlet );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_FILE_TEMPLATE,
                new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * Produces text content of the html template for a portlet
     * @param portlet The instance of a portlet
     * @param strPluginName The plugin name
     * @param nPortletTemplateType The type of portlet
     * @return The content of the html template
     */
    public static String getPortletHtmlTemplate( PluginPortlet portlet, String strPluginName, int nPortletTemplateType )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_I18N_BRACKETS_OPEN, "@@i18n{" );
        model.put( MARK_I18N_BRACKETS_CLOSE, "}" );
        model.put( MARK_BRACKETS_OPEN, "${" );
        model.put( MARK_BRACKETS_CLOSE, "}" );
        model.put( MARK_MACRO, "@" );
        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_PORTLET_TEMPLATE_TYPE, nPortletTemplateType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_HTML_TEMPLATE,
                new Locale( "en", "US" ), model );

        return template.getHtml(  );
    }

    /**
     * Fetches the xsl corresponding to a portlet
     * @param portlet The instance of a portlet
     * @param strPluginName The plugin name
     * @return The content of the xsl file
     */
    public static String getPortletXsl( PluginPortlet portlet, String strPluginName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );;
        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_XSL_FILE, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }
}
