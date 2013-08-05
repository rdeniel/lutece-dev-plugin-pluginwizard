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

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * The portlet generator  generates all the java files needed for portlet creation
 */
public class PortletGenerator extends AbstractGenerator
{
    private static final String PATH = "src/java/fr/paris/lutece/plugins/{plugin_name}/business/portlet/";
    private static final String MARK_PORTLET_NAME = "portletName";
    private static final String EXT_JAVA = ".java";
    private static List<BusinessFileConfig> _listFiles;

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

        for ( Portlet portlet : pm.getPortlets(  ) )
        {
            for ( BusinessFileConfig file : _listFiles )
            {
                String strPortlet = portlet.getPluginPortletTypeName(  );
                int nIndex = strPortlet.lastIndexOf( "_" );
                String strPortletName = getFirstCaps( strPortlet.substring( 0, nIndex ) );
                String strPortletFile = file.getPrefix(  ) + strPortletName + "Portlet" + file.getSuffix(  ) +
                    EXT_JAVA;

                String strPath = getFilePath( pm, PATH, strPortletFile );

                String strSourceCode = getPortletFile( portlet, pm.getPluginName(  ), file.getTemplate(  ),
                        strPortletName );
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
    private String getPortletFile( Portlet portlet, String strPluginName, String strTemplate, String strPortletName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_PORTLET_NAME, strPortletName );
        AppLogService.info( portlet );

        HtmlTemplate template = AppTemplateService.getTemplate( strTemplate, Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}
