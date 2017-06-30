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
import fr.paris.lutece.plugins.pluginwizard.util.Utils;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * AbstractGenerator
 *
 */
public abstract class AbstractGenerator implements Generator
{
    private String _strTemplate;

    /**
     * Returns the Template
     *
     * @return The Template
     */
    public String getTemplate(  )
    {
        return _strTemplate;
    }

    /**
     * Sets the Template
     *
     * @param strTemplate The Template
     */
    public void setTemplate( String strTemplate )
    {
        _strTemplate = strTemplate;
    }

    /**
     * Build the file path
     * @param pm The Plugin Model
     * @param strPath The relative path
     * @param strFilename The file name
     * @return The full path
     */
    protected String getFilePath( PluginModel pm, String strPath, String strFilename )
    {
        String strBasePath = "plugin-{plugin_name}/" + strPath;
        
        if ( pm.isModule( ) ) {
            strBasePath = "module-" + pm.getPluginName(  ) + "/" 
                    + strPath.replace( "{plugin_name}", (pm.getPluginName(  ).split("-")[0]  + "/modules/" +pm.getPluginName(  ).split("-")[1]));
        }
        else
        {
            strBasePath = strBasePath.replace( "{plugin_name}", pm.getPluginName(  ) );
        }
        
        

        return strBasePath + strFilename;
    }

    /**
     * Returns the value of a string with first letter in caps
     * @param strValue The value to be transformed
     * @return The first letter is in Capital
     */
    protected String getFirstCaps( String strValue )
    {
        String strFirstLetter = strValue.substring( 0, 1 );
        String strLettersLeft = strValue.substring( 1 );
        String strValueCap = strFirstLetter.toUpperCase(  ) + strLettersLeft.toLowerCase(  );

        return strValueCap;
    }

    /**
     * Return the model filled with the plugin model
     * @param pm The plugin model
     * @return The model
     */
    protected Map<String, Object> getModel( PluginModel pm )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( Markers.MARK_PLUGIN_MODEL, pm );

        return model;
    }

    /**
     * Build the code
     * @param pm The plugin model
     * @return The code
     */
    protected String build( PluginModel pm )
    {
        return build( getTemplate(  ), getModel( pm ) );
    }

    /**
     * Build the code
     * @param strTemplate The code template
     * @param pm The plugin model
     * @return The code
     */
    protected String build( String strTemplate, PluginModel pm )
    {
        return build( strTemplate, getModel( pm ) );
    }

    /**
     * Build the code
     * @param model The model
     * @return The code
     */
    protected String build( Map<String, Object> model )
    {
        return build( getTemplate(  ), model );
    }

    /**
     * Build the code
     * @param strTemplate The code template
     * @param model The model
     * @return The code
     */
    protected String build( String strTemplate, Map<String, Object> model )
    {
        HtmlTemplate template = AppTemplateService.getTemplate( strTemplate, Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
    
}
