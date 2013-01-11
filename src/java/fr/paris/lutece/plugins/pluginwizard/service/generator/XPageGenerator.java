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

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplication;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplicationHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModelHome;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * Class generating the Xpages
 *
 */
public class XPageGenerator implements Generator
{
    private static final String TEMPLATE_XPAGE_TEMPLATE = "/skin/plugins/pluginwizard/templates/pluginwizard_xpage_template.html";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );
        Collection<PluginApplication> listPluginApplications = PluginApplicationHome.findByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        String strBasePath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/web/";
        strBasePath = strBasePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

        for ( PluginApplication xpage : listPluginApplications )
        {
            String strPath = strBasePath + xpage.getApplicationClass(  ) + ".java";

            String strSourceCode = getXPageCode( pluginModel.getIdPlugin(  ), plugin, xpage.getIdPluginApplication(  ) );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
    * Generates the XPage source code
    * @param nPluginId The id of the plugin
    * @param plugin The plugin
    * @param nIdPluginApplication id of the plugin application
    * @return The code of the XPage generated
    */
    private String getXPageCode( int nPluginId, Plugin plugin, int nIdPluginApplication )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN, pluginModel );

        model.put( MARK_PLUGIN_MODEL, pluginModel );
        model.put( MARK_PLUGIN_APPLICATION, PluginApplicationHome.findByPrimaryKey( nIdPluginApplication, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_XPAGE_TEMPLATE, Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}
