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
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Class generates the xsl associated to every generated portlet
 *
 */
public class PortletXslGenerator extends AbstractGenerator
{
    private static final String PATH = "webapp/WEB-INF/xsl/normal/";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm, String generationSchemeName )
    {
        HashMap map = new HashMap( );

        for ( Portlet portlet : pm.getPortlets( ) )
        {
            String strPath = getFilePath( pm, PATH, "portlet_" + getFirstLower( portlet.getPortletTypeName( ) ) + ".xsl" );

            String strSourceCode = getPortletXsl( portlet, pm.getPluginName( ) );
            strSourceCode = strSourceCode.replace( "&lt;", "<" );
            strSourceCode = strSourceCode.replace( "&gt;", ">" );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * Returns the value of a string with first letter in caps
     * 
     * @param strValue
     *            The value to be transformed
     * @return The first letter is in Capital
     */
    private String getFirstLower( String strValue )
    {
        String strFirstLetter = strValue.substring( 0, 1 );
        String strLettersLeft = strValue.substring( 1 );
        String strValueCap = strFirstLetter.toLowerCase( ) + strLettersLeft.toLowerCase( );

        return strValueCap;
    }

    /**
     * Fetches the xsl corresponding to a portlet
     * 
     * @param portlet
     *            The instance of a portlet
     * @param strPluginName
     *            The plugin name
     * @return The content of the xsl file
     */
    private String getPortletXsl( Portlet portlet, String strPluginName )
    {
        Map<String, Object> model = new HashMap<>( );
        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );

        return build( model );
    }
}
