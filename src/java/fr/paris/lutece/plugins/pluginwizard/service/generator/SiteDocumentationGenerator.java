/*
 * Copyright (c) 2002-2019, Mairie de Paris
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

import java.util.HashMap;
import java.util.Map;

/**
 * Site Documenation Generator
 */
public class SiteDocumentationGenerator extends AbstractGenerator
{
    private static final String PATH = "src/site/";
    private static String [ ] _files = {
            "site.xml", "site_fr.xml", "xdoc/index.xml", "fr/xdoc/index.xml", "resources/images/readme.txt", "fr/resources/images/readme.txt"
    };

    @Override
    public Map generate( PluginModel pm, String generationSchemeName )
    {
        HashMap map = new HashMap( );

        for ( int i = 0; i < _files.length; i++ )
        {
            String strPath = getFilePath( pm, PATH, _files [i] );

            String strSourceCode = getCode( pm, i );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * Build code
     * 
     * @param pm
     *            The plugin model
     * @param nTemplateType
     *            The template index
     * @return The code
     */
    private String getCode( PluginModel pm, int nTemplateType )
    {
        Map<String, Object> model = new HashMap<>( );

        model.put( Markers.MARK_PLUGIN_MODEL, pm );
        model.put( Markers.MARK_TEMPLATE_TYPE, nTemplateType );

        return build( model );
    }
}
