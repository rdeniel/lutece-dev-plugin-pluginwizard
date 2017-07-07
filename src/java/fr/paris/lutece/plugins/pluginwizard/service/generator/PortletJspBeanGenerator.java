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

import java.util.HashMap;
import java.util.Map;


/**
 *
 * The class generated the portlet jspbean needed to manage portlets
 *
 */
public class PortletJspBeanGenerator extends AbstractGenerator
{
    private static final String PATH = "src/java/fr/paris/lutece/plugins/{plugin_name}/web/portlet/";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );
        String strPluginName = pm.getPluginNameAsRadicalPackage();
        
        String _path = PATH.replace("{plugin_name}", pm.getPluginNameAsRadicalPath());
        
        for ( Portlet portlet : pm.getPortlets(  ) )
        {
            String strPath = getFilePath( pm, _path, portlet.getPortletClass(  ) + "JspBean.java" );

            String strSourceCode = getPortletJspBean( portlet, strPluginName , pm.isModule( ) );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
    * Gets the Portlet Jsp Bean
    * @param portlet The portlet
    * @param strPluginName The generated plugin name
    * @return The source code of the jsp
    */
    private String getPortletJspBean( Portlet portlet, String strPluginName, boolean isModule)
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( Markers.MARK_PORTLET, portlet );
        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put( Markers.MARK_IS_MODULE, isModule ) ;

        return build( model );
    }
}
