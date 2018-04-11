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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Class generating the Xpages
 *
 */
public class XPageGenerator extends AbstractGenerator
{
    private static final String PATH_JAVA = "src/java/fr/paris/lutece/plugins/{plugin_name}/web/";
    private static final String PATH_KOTLIN = "src/kotlin/fr/paris/lutece/plugins/{plugin_name}/web/";
    private static final String SUFFIX_JAVA_XPAGE_CLASS = "XPage.java";
    private static final String SUFFIX_KOTLIN_XPAGE_CLASS = "XPage.kt";
    private static final String SUFFIX_JAVA_XPAGE = ".java";
    private static final String SUFFIX_KOTLIN_XPAGE = ".kt";

    /**
     * {@inheritDoc }
     * 
     * @param pm
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap( );
        String strFilesPath = ( isKotlin( ) ) ? PATH_KOTLIN : PATH_JAVA;

        for ( Application application : pm.getApplications( ) )
        {
            Collection<BusinessClass> listBusinessClasses = ModelService.getBusinessClassesByApplication( pm, application.getId( ) );

            if ( listBusinessClasses.isEmpty( ) )
            {
                String strSuffix = ( isKotlin( ) ) ? SUFFIX_KOTLIN_XPAGE : SUFFIX_JAVA_XPAGE;
                String strPath = getFilePath( pm, strFilesPath, application.getApplicationClass( ) + strSuffix );
                String strSourceCode = getXPageCode( pm, application.getApplicationName( ), application.getId( ), application );
                map.put( strPath, strSourceCode );
            }
            else
            {
                for ( BusinessClass businessClass : listBusinessClasses )
                {
                    String strSuffix = ( isKotlin( ) ) ? SUFFIX_KOTLIN_XPAGE_CLASS : SUFFIX_JAVA_XPAGE_CLASS;
                    String strFilename = businessClass.getBusinessClassCapsFirst( ) + strSuffix;
                    String strPath = getFilePath( pm, strFilesPath, strFilename );
                    String strSourceCode = getXPageCode( pm, application.getApplicationName( ), application.getId( ), application, businessClass );
                    map.put( strPath, strSourceCode );
                }
            }
        }

        return map;
    }

    /**
     * Generates the XPage code
     * 
     * @param pm
     *            The plugin model
     * @param nApplicationId
     *            id of the plugin application
     * @param strApplicationName
     *            the name of the appliaction
     * @param application
     *            the application
     * @param businessClass
     *            the business class
     * @return The code of the XPage generated
     */
    private String getXPageCode( PluginModel pm, String strApplicationName, int nApplicationId, Application application, BusinessClass businessClass )
    {
        Map<String, Object> model = getModel( pm );
        model.put( Markers.MARK_PLUGIN, pm );
        model.put( Markers.MARK_PLUGIN_APPLICATION, ModelService.getApplication( pm, nApplicationId ) );
        model.put( Markers.MARK_APPLICATION, application );
        model.put( Markers.MARK_APPLICATION_NAME, strApplicationName );
        model.put( Markers.MARK_BUSINESS_CLASS, businessClass );

        return build( model );
    }

    /**
     * Generates the XPage code
     * 
     * @param pm
     *            The plugin model
     * @param nApplicationId
     *            id of the plugin application
     * @param strApplicationName
     *            the name of the appliaction
     * @param application
     *            the application
     * @return The code of the XPage generated
     */
    private String getXPageCode( PluginModel pm, String strApplicationName, int nApplicationId, Application application )
    {
        Map<String, Object> model = getModel( pm );
        model.put( Markers.MARK_PLUGIN, pm );
        model.put( Markers.MARK_PLUGIN_APPLICATION, ModelService.getApplication( pm, nApplicationId ) );
        model.put( Markers.MARK_APPLICATION, application );
        model.put( Markers.MARK_APPLICATION_NAME, strApplicationName );

        return build( model );
    }
}
