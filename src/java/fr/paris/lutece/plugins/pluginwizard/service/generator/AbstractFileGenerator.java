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
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.MARK_BUSINESS_CLASSES;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.MARK_LIST_PORTLETS;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.MARK_PLUGIN;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Abstract Generator
 */
public abstract class AbstractFileGenerator extends AbstractGenerator
{
    // Variables declarations 
    private String _strPath;

    /**
     * Get the filename
     * @param pm The plugin model
     * @return The filename
     */
    protected abstract String getFilename( PluginModel pm );

    /**
     * Returns the Path
     *
     * @return The Path
     */
    public String getPath(  )
    {
        return _strPath;
    }

    /**
     * Sets the Path
     *
     * @param strPath The Path
     */
    public void setPath( String strPath )
    {
        _strPath = strPath;
    }

    /**
     * Produces the code
     *
     * @param pm the plugin model
     * @return the code
     */
    protected String getCode( PluginModel pm )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN, pm );
        model.put( MARK_LIST_PORTLETS, pm.getPortlets(  ) );
        model.put( MARK_BUSINESS_CLASSES, pm.getBusinessClasses(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( getTemplate(  ), Locale.getDefault(  ), model );

        return template.getHtml(  );
    }

    /**
     * Produces the code
     *
     * @param pm the plugin model
     * @return the code
     */
    protected String getFilePath( PluginModel pm )
    {
        return getFilePath( pm, getPath(  ), getFilename( pm ) );
    }

    /**
     * Produces the file code in a map
     *
     * @param pm the plugin model
     * @return the map
     */
    protected Map generateFile( PluginModel pm )
    {
        HashMap map = new HashMap(  );
        map.put( getFilePath( pm ), getCode( pm ) );

        return map;
    }
}
