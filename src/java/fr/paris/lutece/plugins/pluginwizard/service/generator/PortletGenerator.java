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

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.plugins.pluginwizard.util.Utils;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The portlet generator  generates all the java files needed for portlet creation
 */
public class PortletGenerator extends AbstractGenerator
{
    private static final String PATH = "src/java/fr/paris/lutece/plugins/{plugin_name}/business/portlet/";
    private static final String EXT_JAVA = ".java";
    private List<BusinessFileConfig> _listFiles;

    /**
     * Set the list of files config
     * @param listFiles The list of files
     */
    public void setFiles( List<BusinessFileConfig> listFiles )
    {
        _listFiles = listFiles;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );
        String strRadicalPackage = pm.getPluginNameAsRadicalPackage() ;
                
        for ( Portlet portlet : pm.getPortlets(  ) )
        {
            for ( BusinessFileConfig file : _listFiles )
            {
                String strPortletFile = file.getPrefix(  ) + portlet.getPortletClass(  ) + file.getSuffix(  ) +
                    EXT_JAVA;

                String strPath = getFilePath( pm, PATH, strPortletFile );

                String strSourceCode = getPortletFile( portlet, pm.getPluginName(  ), file.getTemplate(  ), strRadicalPackage );
                strSourceCode = strSourceCode.replace( "&lt;", "<" );
                strSourceCode = strSourceCode.replace( "&gt;", ">" );
                map.put( strPath, strSourceCode );
            }
        }

        return map;
    }

    /**
    * Produces text content of java file used to build a portlet
    * @param portlet The instance of a portlet
    * @param strPluginName The plugin name
    * @param strTemplate  The template of portlet file
    * @param strPortletName The portlet name
    * @return The content of the portlet file
    */
    private String getPortletFile( Portlet portlet, String strPluginName, String strTemplate, String strRadicalPackage )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( Markers.MARK_PORTLET, portlet );
        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put( Markers.MARK_RADICAL_PACKAGE, strRadicalPackage );
        
        AppLogService.info( portlet );

        return build( strTemplate, model );
    }
}
