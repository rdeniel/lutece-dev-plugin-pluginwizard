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

import fr.paris.lutece.plugins.pluginwizard.business.model.Application;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.ModelService;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * Class generating the Xpages
 *
 */
public class XPageGenerator extends AbstractGenerator
{
    private static final String PATH = "src/java/fr/paris/lutece/plugins/{plugin_name}/web/";

    /**
     * {@inheritDoc }
     * @param pm
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );

        for ( Application xpage : pm.getApplications(  ) )
        {
            String strPath = getFilePath( pm, PATH, xpage.getApplicationClass(  ) + ".java" );

            String strSourceCode = getXPageCode( pm, xpage.getId(  ) );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
    * Generates the XPage source code
    * @param pm The plugin model
    * @param nApplicationId id of the plugin application
    * @return The code of the XPage generated
    */
    private String getXPageCode( PluginModel pm, int nApplicationId )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN, pm );

        model.put( MARK_PLUGIN_MODEL, pm );
        model.put( MARK_PLUGIN_APPLICATION, ModelService.getApplication( pm, nApplicationId ) );

        HtmlTemplate template = AppTemplateService.getTemplate( getTemplate(  ), Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}
