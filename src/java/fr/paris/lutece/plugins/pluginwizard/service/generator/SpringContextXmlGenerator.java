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

import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClassHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.FeatureHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.PortletHome;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * Class generates the spring context file
 *
 */
public class SpringContextXmlGenerator implements Generator
{
    private static final String TEMPLATE_SPRING_CONTEXT_XML = "/skin/plugins/pluginwizard/templates/pluginwizard_spring_context_xml.html";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );

        String strBasePath = "plugin-{plugin_name}/webapp/WEB-INF/conf/plugins/";
        strBasePath = strBasePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );
        strBasePath = strBasePath + pluginModel.getPluginName(  ).toLowerCase(  ) + "_context.xml";

        String strSourceCode = getSpringContextCode( pluginModel.getIdPlugin(  ), plugin );
        map.put( strBasePath, strSourceCode );

        return map;
    }

    /**
     * Produces the spring context xml file
     *
     * @param nPluginId The id of the plugin
     * @param plugin the plugin
     * @return the content if the spring context file
     */
    private String getSpringContextCode( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN, pluginModel );

        Collection<BusinessClass> listClasses = new ArrayList<BusinessClass>(  );

        Collection<Feature> listFeaturesPlugin = FeatureHome.findByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        for ( Feature feature : listFeaturesPlugin )
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ), plugin );
            listClasses.addAll( listBusinessClasses );
        }

        Collection<Portlet> listPortlets = PortletHome.findByPlugin( nPluginId, plugin );

        model.put( MARK_LIST_PORTLETS, listPortlets );
        model.put( MARK_BUSINESS_CLASSES, listClasses );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_SPRING_CONTEXT_XML, Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}
