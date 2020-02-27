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

import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.ModelService;
import fr.paris.lutece.plugins.pluginwizard.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin Jsp Controller Generator
 */
public class AdminJspControllerGenerator extends AbstractGenerator
{
    private static final String PATH = "webapp/jsp/admin/plugins/{plugin_name}/";
    private String _strBusinessTemplate;
    private String _strFeatureTemplate;

    /**
     * Sets the business template
     * 
     * @param strTemplate
     *            The template
     */
    public void setBusinessTemplate( String strTemplate )
    {
        _strBusinessTemplate = strTemplate;
    }

    /**
     * Sets the feature template
     * 
     * @param strTemplate
     *            The template
     */
    public void setFeatureTemplate( String strTemplate )
    {
        _strFeatureTemplate = strTemplate;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm, String generationSchemeName )
    {
        HashMap map = new HashMap( );

        String strPluginName = pm.getPluginNameAsRadicalPackage( );

        for ( Feature feature : pm.getFeatures( ) )
        {
            List<BusinessClass> listBusinessClasses = ModelService.getBusinessClassesByFeature( pm, feature.getId( ) );

            for ( BusinessClass businessClass : listBusinessClasses )
            {
                String strJspFileName = "Manage" + businessClass.getBusinessClass( ) + "s.jsp";

                String strPath = getFilePath( pm, PATH, strJspFileName );

                String strSourceCode = getJspBusinessFile( businessClass, feature.getFeatureName( ), strPluginName, pm.isModule( ) );
                strSourceCode = strSourceCode.replace( "&lt;", "<" );
                strSourceCode = strSourceCode.replace( "&gt;", ">" );
                map.put( strPath, strSourceCode );
            }
        }

        return map;
    }

    /**
     * Gets the Jsp File of a business class
     * 
     * @param businessClass
     *            The business class
     * @param strFeatureName
     *            The feature name
     * @param strPluginName
     *            The generated plugin name
     * @param nJspType
     *            The type of jsp
     * @return The source code of the jsp
     */
    private String getJspBusinessFile( BusinessClass businessClass, String strFeatureName, String strPluginName, boolean bIsModule )
    {
        String strBeanName = strFeatureName.toLowerCase( ) + businessClass.getBusinessClass( );
        Map<String, Object> model = new HashMap<>( );
        model.put( Markers.MARK_BUSINESS_CLASS, businessClass );
        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put( Markers.MARK_BEAN_NAME, strBeanName );
        model.put( Markers.MARK_IS_MODULE, bIsModule );

        return build( _strBusinessTemplate, model );
    }

    /**
     * Gets the JSP feature file code
     * 
     * @param strFeatureName
     *            The feature name
     * @param strPluginName
     *            The plugin name
     * @return The source code of the JSP
     */
    private String getFeatureJspFile( String strFeatureName, String strPluginName, boolean bIsModule )
    {
        Map<String, Object> model = new HashMap<>( );
        model.put( Markers.MARK_FEATURE_NAME, strFeatureName );
        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put( Markers.MARK_IS_MODULE, bIsModule );

        return build( _strFeatureTemplate, model );
    }
}
