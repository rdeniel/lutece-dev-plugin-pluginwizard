/*
 * Copyright (c) 2002-2009, Mairie de Paris
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
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortletHome;
import fr.paris.lutece.plugins.pluginwizard.service.SourceCodeGenerator;
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;


/**
 * The generator produced the templates necessary for the handling of portlets
 */
public class PortletTemplateGenerator implements Visitor
{
    /**
     * Visit the path and verifies if Portlet templates is relevant to be generated
     * @param strPath The path representing the file structure of the zip
     * @param plugin The plugin
     * @param pluginModel the representation of the created plugin
     * @return The map with the name of the file and its corresponding content
     */
    public HashMap visitPath( String strPath, Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );
        Collection<PluginPortlet> listPortlets = PluginPortletHome.findByPlugin( pluginModel.getIdPlugin(  ), plugin );

        for ( PluginPortlet portlet : listPortlets )
        {
            String strOldPath = new String( strPath );
            String strBasePath = new String( strPath );

            for ( int i = 1; i < 4; i++ )
            {
                String strPortlet = portlet.getPluginPortletTypeName(  );
                int nIndex = strPortlet.lastIndexOf( "_" );

                String strPortletFile = getPortletTemplateName( strPortlet.substring( 0, nIndex ).toLowerCase(  ),
                        pluginModel.getPluginName(  ), i );

                strBasePath = strBasePath + "\\" + strPortletFile;

                String strSourceCode = SourceCodeGenerator.getPortletHtmlTemplate( portlet,
                        pluginModel.getPluginName(  ), i );
                strSourceCode = strSourceCode.replace( "&lt;", "<" );
                strSourceCode = strSourceCode.replace( "&gt;", ">" );
                strSourceCode = strSourceCode.replace( "@@", "#" );
                map.put( strBasePath, strSourceCode );
                strBasePath = strOldPath;
            }
        }

        return map;
    }

    /**
    * Chooses the name of the template
    * @param strPortletName The name of the portlet
    * @param strPluginName The plugin name
    * @param nTemplate The type of template
    * @return The name of the tempate
    */
    private String getPortletTemplateName( String strPortletName, String strPluginName, int nTemplate )
    {
        String strReturn = "";

        switch ( nTemplate )
        {
            case 1:
                strReturn = "combo_feed_" + strPortletName + ".html";

                break;

            case 2:
                strReturn = "modify_portlet_" + strPortletName + ".html";

                break;

            case 3:
                strReturn = "create_portlet_" + strPortletName + ".html";

                break;

            default:
                strReturn = "combo_feed_" + strPortletName + ".html";

                break;
        }

        return strReturn;
    }
}
