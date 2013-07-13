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
 * The class generated the portlet jspbean needed to manage portlets
 *
 */
public class PortletJspBeanGenerator implements Generator
{
    private static final String TEMPLATE_PORTLET_JSPBEAN_FILE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_portlet_jspbean.html";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );

        String strBasePath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/web/portlet/";
        strBasePath = strBasePath.replace( "{plugin_name}", pm.getPluginName(  ) );

        for ( Portlet portlet : pm.getPortlets() )
        {
            String strPortlet = portlet.getPluginPortletTypeName(  );
            int nIndex = strPortlet.lastIndexOf( "_" );

            String strPath = strBasePath + getFirstCaps( strPortlet.substring( 0, nIndex ).toLowerCase(  ) ) + "PortletJspBean.java";

            String strSourceCode = getPortletJspBean( portlet, pm.getPluginName(  ) );
            map.put( strPath, strSourceCode );
        }

        return map;
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
    * Gets the Portlet Jsp Bean
    * @param portlet The portlet
    * @param strPluginName The generated plugin name
    * @return The source code of the jsp
    */
    private String getPortletJspBean( Portlet portlet, String strPluginName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PORTLET, portlet );
        model.put( MARK_PLUGIN_NAME, strPluginName );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PORTLET_JSPBEAN_FILE_TEMPLATE,
                Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}
