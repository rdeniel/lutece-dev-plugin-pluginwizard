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
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * Class generated the jsp files needed to manage portlets
 *
 */
public class PortletJspGenerator implements Generator
{
    private static final String TEMPLATE_PORTLET_JSP_FILE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_jsp_files.html";
    private static final String EXT_JSP = ".jsp";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );
        String strBasePath = "plugin-{plugin_name}/webapp/jsp/admin/plugins/{plugin_name}/";
        strBasePath = strBasePath.replace( "{plugin_name}", pm.getPluginName(  ) );

        for ( Portlet portlet : pm.getPortlets() )
        {
            for ( int i = 1; i < 5; i++ )
            {
                String strPortlet = portlet.getPluginPortletTypeName(  );
                int nIndex = strPortlet.lastIndexOf( "_" );
                String strPortletFile = getPortletFileName( getFirstCaps( 
                            strPortlet.substring( 0, nIndex ).toLowerCase(  ) ), i );

                String strPath = strBasePath + strPortletFile;

                String strSourceCode = getPortletJspFile( portlet, pm.getPluginName(  ), i );
                strSourceCode = strSourceCode.replace( "&lt;", "<" );
                strSourceCode = strSourceCode.replace( "&gt;", ">" );
                map.put( strPath, strSourceCode );
            }
        }

        return map;
    }

    /**
     * Fetches the name of the portlet jsp
     *
     * @param strPortletName the name of the portlet
     * @param nPortletJspFileType The type of jsp
     * @return The name of the jsp file
     */
    private String getPortletFileName( String strPortletName, int nPortletJspFileType )
    {
        String strReturn;

        switch ( nPortletJspFileType )
        {
            case 1:
                strReturn = "ModifyPortlet" + strPortletName + EXT_JSP;

                break;

            case 2:
                strReturn = "DoModifyPortlet" + strPortletName + EXT_JSP;

                break;

            case 3:
                strReturn = "CreatePortlet" + strPortletName + EXT_JSP;

                break;

            case 4:
                strReturn = "DoCreatePortlet" + strPortletName + EXT_JSP;

                break;

            default:
                strReturn = "CreatePortlet" + strPortletName + EXT_JSP;

                break;
        }

        return strReturn;
    }

    /**
     * Returns the value of a string with first letter in caps
     *
     * @param strValue The value to be transformed
     * @return The first letter is in Capital
     */
    private String getFirstCaps( String strValue )
    {
        String strFirstLetter = strValue.substring( 0, 1 );
        String strLettersLeft = strValue.substring( 1 );
        String strValueCap = strFirstLetter.toUpperCase(  ) + strLettersLeft.toLowerCase(  );

        return strValueCap;
    }

    /**
    * Gets the portlet Jsp File
    * @param portlet The portlet
    * @param strPluginName the plugin name
    * @param nPortletJspType The type of portlet
    * @return The source code of the portlet jsp
    */
    private String getPortletJspFile( Portlet portlet, String strPluginName, int nPortletJspType )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_PORTLET_JSP_TYPE, nPortletJspType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_JSP_FILE_TEMPLATE,
                Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}