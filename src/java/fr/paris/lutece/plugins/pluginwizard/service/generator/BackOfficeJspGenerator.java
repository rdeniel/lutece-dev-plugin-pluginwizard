/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClassHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * The generator produced the jsp for back office management
 *
 */
public class BackOfficeJspGenerator implements Generator
{
    private static final String TEMPLATE_JSP_BUSINESS_FILES = "/skin/plugins/pluginwizard/templates/pluginwizard_jsp_business_files.html";
    private static final String TEMPLATE_JSP_FEATURE_FILE = "/skin/plugins/pluginwizard/templates/pluginwizard_jsp_feature_file.html";
    private static final String EXT_JSP = ".jsp";
    private static String[] _jsp_prefix = { "Create", "DoCreate", "Remove", "DoRemove", "Manage", "Modify", "DoModify" };

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );
        String strPluginName = pluginModel.getPluginName(  );

        String strBasePath = "plugin-{plugin_name}/webapp/jsp/admin/plugins/{plugin_name}/";
        strBasePath = strBasePath.replace( "{plugin_name}", strPluginName );

        for ( Feature feature : pluginModel.getPluginFeatures(  ) )
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ), plugin );

            for ( BusinessClass businessClass : listBusinessClasses )
            {
                for ( int i = 0; i < _jsp_prefix.length; i++ )
                {
                    String strJspFileName = _jsp_prefix[i] + businessClass.getBusinessClass(  ) + EXT_JSP;

                    String strPath = strBasePath + strJspFileName;

                    String strSourceCode = getJspBusinessFile( businessClass, feature.getPluginFeatureName(  ),
                            strPluginName, i + 1 );
                    strSourceCode = strSourceCode.replace( "&lt;", "<" );
                    strSourceCode = strSourceCode.replace( "&gt;", ">" );
                    map.put( strPath, strSourceCode );
                }
            }

            String strPath = strBasePath + feature.getPluginFeatureName(  ) + EXT_JSP;

            String strSourceCode = getFeatureJspFile( feature.getPluginFeatureName(  ), strPluginName );
            strSourceCode = strSourceCode.replace( "&lt;", "<" );
            strSourceCode = strSourceCode.replace( "&gt;", ">" );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * Gets the Jsp File of a business class
     * @param businessClass The business class
     * @param strFeatureName The feature name
     * @param strPluginName The generated plugin name
     * @param nJspType The type of jsp
     * @return The source code of the jsp
     */
    private String getJspBusinessFile( BusinessClass businessClass, String strFeatureName, String strPluginName,
        int nJspType )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_FEATURE_NAME, strFeatureName );
        model.put( MARK_BUSINESS_CLASS, businessClass );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_JSP_TYPE, "" + nJspType );
        String strBeanName = strFeatureName.toLowerCase() + businessClass.getBusinessClassCapsFirst();
        model.put( MARK_BEAN_NAME , strBeanName );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_JSP_BUSINESS_FILES, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }

    /**
     * Gets the JSP feature file code
     * @param strFeatureName The feature name
     * @param strPluginName The plugin name
     * @return The source code of the JSP
     */
    private String getFeatureJspFile( String strFeatureName, String strPluginName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_FEATURE_NAME, strFeatureName );
        model.put( MARK_PLUGIN_NAME, strPluginName );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_JSP_FEATURE_FILE, new Locale( "en", "US" ),
                model );

        return template.getHtml(  );
    }
}
