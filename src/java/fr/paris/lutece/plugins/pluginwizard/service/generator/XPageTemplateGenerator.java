/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.pluginwizard.service.generator;

import fr.paris.lutece.plugins.pluginwizard.business.model.Application;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.ModelService;
import fr.paris.lutece.plugins.pluginwizard.util.Utils;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * Generates Html files used as template to construct front office
 *
 */
public class XPageTemplateGenerator extends AbstractGenerator
{
    private static final String PATH = "webapp/WEB-INF/templates/skin/plugins/{plugin_name}/";
    private static String[] _template_prefix = { "create_", "modify_", "manage_" };
    private String _strTabsTemplate;

    /**
     * {@inheritDoc }
     * @param pm
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );
        String strPluginName = pm.getPluginNameAsPackage();
        
        //for each application,which business classes are attached to
        Collection<Application> listApplication = pm.getApplications(  );
                
        for ( Application application : listApplication )
        {
            Collection<BusinessClass> listBusinessClasses = ModelService.getBusinessClassesByApplication( pm,
                    application.getId(  ) );

            if ( listBusinessClasses.isEmpty(  ) )
            {
                String strPath = getFilePath( pm, PATH, application.getApplicationName(  ) + ".html" );
                String strSourceCode = getCreateHtmlCode( application, strPluginName );
                map.put( strPath, strSourceCode );
            }
            else
            {
                for ( BusinessClass businessClass : listBusinessClasses )
                {
                    for ( int i = 0; i < _template_prefix.length; i++ )
                    {
                        String strSuffix = ( i == 2 ) ? "s.html" : ".html";
                        String strPath = getFilePath( pm, PATH,
                                _template_prefix[i] + businessClass.getBusinessClass(  ).toLowerCase(  ) + strSuffix );

                        String strSourceCode = getCreateHtmlCode( listBusinessClasses, application, businessClass, i,
                        		strPluginName );
                        map.put( strPath, strSourceCode );
                    }
                }

                //Add the main template where all the business management interface will be accessible
                String strPath = getFilePath( pm, PATH,
                        application.getApplicationName(  ).toLowerCase(  ) + "_tabs.html" );
               
                String strSourceCode = getTabsHtmlCode( listBusinessClasses, strPluginName, application );
                map.put( strPath, strSourceCode );
            }
        }

        return map;
    }

    /**
     * Gets the code of a create template for a specific business object
     *
     * @param listAllBusinessClasses A list of business classes attached to
     * plugin
     * @param businessClass The instance of the business class
     * @param nTemplateType The type of template
     * @param application The application
     * @param strPluginName The plugin name
     * @return The html code of the create template
     */
    private String getCreateHtmlCode( Collection<BusinessClass> listAllBusinessClasses, Application application,
        BusinessClass businessClass, int nTemplateType, String strPluginName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put( Markers.MARK_I18N_BRACKETS_OPEN, "@@i18n{" );
        model.put( Markers.MARK_I18N_BRACKETS_CLOSE, "}" );
        model.put( Markers.MARK_MACRO, "@" );
        model.put( Markers.MARK_VARIABLE, "@@" );
        model.put( Markers.MARK_BRACKETS_OPEN, "${" );
        model.put( Markers.MARK_BRACKETS_CLOSE, "}" );
        model.put( Markers.MARK_BUSINESS_CLASS, businessClass );
        model.put( Markers.MARK_LIST_BUSINESS_CLASSES, listAllBusinessClasses );
        model.put( Markers.MARK_APPLICATION, application );
        model.put( Markers.MARK_INCLUDE, "@@include" );

        model.put( Markers.MARK_TEMPLATE_TYPE, nTemplateType );

        HtmlTemplate template = AppTemplateService.getTemplate( getTemplate(  ), Locale.getDefault(  ), model );

        return template.getHtml(  ).replace( "@@", "#" );
    }

    /**
    * Gets the code of a tab template for a specific business object
    *
    * @param listAllBusinessClasses A list of business classes attached to plugin
    * @param strPluginName  The plugin name
    * @param application The application
    * @return The html code of the tab template
    */
    private String getTabsHtmlCode( Collection<BusinessClass> listAllBusinessClasses, String strPluginName,
        Application application )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put( Markers.MARK_APPLICATION, application );
        model.put( Markers.MARK_I18N_BRACKETS_OPEN, "@@i18n{" );
        model.put( Markers.MARK_I18N_BRACKETS_CLOSE, "}" );
        model.put( Markers.MARK_MACRO, "@" );
        model.put( Markers.MARK_VARIABLE, "@@" );
        model.put( Markers.MARK_BRACKETS_OPEN, "${" );
        model.put( Markers.MARK_BRACKETS_CLOSE, "}" );
        model.put( Markers.MARK_MACRO_DEF, "@@macro" );
        model.put( Markers.MARK_LIST_BUSINESS_CLASSES, listAllBusinessClasses );

        HtmlTemplate template = AppTemplateService.getTemplate( _strTabsTemplate, Locale.getDefault(  ), model );

        return template.getHtml(  ).replace( "@@", "#" );
    }

    /**
     * Gets the code of a create template for a specific business object
     *
     * @param application The application
     * @param strPluginName The plugin name
     * @return The html code of the create template
     */
    private String getCreateHtmlCode( Application application, String strPluginName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put( Markers.MARK_I18N_BRACKETS_OPEN, "@@i18n{" );
        model.put( Markers.MARK_I18N_BRACKETS_CLOSE, "}" );
        model.put( Markers.MARK_MACRO, "@" );
        model.put( Markers.MARK_VARIABLE, "@@" );
        model.put( Markers.MARK_BRACKETS_OPEN, "${" );
        model.put( Markers.MARK_BRACKETS_CLOSE, "}" );
        model.put( Markers.MARK_APPLICATION, application );
        model.put( Markers.MARK_INCLUDE, "@@include" );

        HtmlTemplate template = AppTemplateService.getTemplate( getTemplate(  ), Locale.getDefault(  ), model );

        return template.getHtml(  ).replace( "@@", "#" );
    }

    /**
     * @param tabsTemplate the tabsTemplate to set
     */
    public void setTabsTemplate( String tabsTemplate )
    {
        _strTabsTemplate = tabsTemplate;
    }
}
