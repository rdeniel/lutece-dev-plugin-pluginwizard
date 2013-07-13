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

import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKey;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * The Pom generator is responsible of generating a project object model used by maven
 *
 */
public class PomGenerator implements Generator
{
    private static final String TEMPLATE_POM_XML = "/skin/plugins/pluginwizard/templates/pluginwizard_pom_xml.html";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );
        String strBasePath = "plugin-{plugin_name}";
        strBasePath = strBasePath.replace( "{plugin_name}", pm.getPluginName(  ) );
        strBasePath = strBasePath + "/pom.xml";

        String strSourceCode = getPomXmlCode( pm );
        map.put( strBasePath, strSourceCode );

        return map;
    }

    /**
    * Produces content of the maven pom.xml file
    * @param nPluginId The id of the plugin
    * @param plugin The plugin
    * @return The content of the pom.xml
    */
    private String getPomXmlCode( PluginModel pm )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        Collection<ConfigurationKey> listKeys = ConfigurationKeyHome.getConfigurationKeysList();

        //Fetches the actual configuration values to be replaced in the templates
        for ( ConfigurationKey key : listKeys )
        {
            model.put( key.getKeyDescription(  ).trim(  ), key.getKeyValue(  ) );
        }

        model.put( MARK_PLUGIN, pm );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_POM_XML, Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}
