/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
 *
 * Class generates the xsl associated to every generated portlet
 *
 */
public class PortletXslGenerator implements Generator
{
    /**
     * Visits the path and verifies whether portlet xsl files needed
     * @param strPath The path representing the file structure of the zip
     * @param plugin The plugin
     * @param pluginModel the representation of the created plugin
     * @return The map with the name of the file and its corresponding content
     */
    @Override
    public HashMap generate( Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );
        Collection<PluginPortlet> listPortlets = PluginPortletHome.findByPlugin( pluginModel.getIdPlugin(  ), plugin );

                        String strBasePath = "plugin-{plugin_name}/webapp/WEB-INF/xsl/normal/";
                strBasePath = strBasePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

        for ( PluginPortlet portlet : listPortlets )
        {
            String strPath = strBasePath + "portlet_" + getFirstLower( portlet.getPluginPortletTypeName(  ) ) + ".xsl";

            String strSourceCode = SourceCodeGenerator.getPortletXsl( portlet, pluginModel.getPluginName(  ) );
            strSourceCode = strSourceCode.replace( "&lt;", "<" );
            strSourceCode = strSourceCode.replace( "&gt;", ">" );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * Returns the value of a string with first letter in caps
     * @param strValue The value to be transformed
     * @return The first letter is in Capital
     */
    private String getFirstLower( String strValue )
    {
        String strFirstLetter = strValue.substring( 0, 1 );
        String strLettersLeft = strValue.substring( 1 );
        String strValueCap = strFirstLetter.toLowerCase(  ) + strLettersLeft.toLowerCase(  );

        return strValueCap;
    }
}
