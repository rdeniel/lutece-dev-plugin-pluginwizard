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
package fr.paris.lutece.plugins.pluginwizard.web.formbean;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Description FormBean
 */
public class DescriptionFormBean implements Serializable
{
    // Variables declarations 
    private int _nIdPlugin;
    private String _strPluginName;
    private String _strPluginClass;
    @NotEmpty( message = "pluginwizard.error.plugin.description.notEmpty" )
    @Size( min = 5, max = 255, message = "pluginwizard.error.plugin.description.size" )
    private String _strPluginDescription;
    @NotEmpty( message = "pluginwizard.error.plugin.provider.notEmpty" )
    @Size( min = 3, max = 255, message = "pluginwizard.error.plugin.provider.size" )
    private String _strPluginProvider;
    @NotEmpty( message = "pluginwizard.error.plugin.version.notEmpty" )
    @Pattern( regexp = "[0-9].[0-9].[0-9]", message = "pluginwizard.error.plugin.version.pattern" )
    private String _strPluginVersion;
    @NotEmpty( message = "pluginwizard.error.plugin.copyright.notEmpty" )
    private String _strPluginCopyright;
    private String _strPluginIconUrl;
    @NotEmpty( message = "pluginwizard.error.plugin.urlProvider.notEmpty" )
    @URL( message = "pluginwizard.error.plugin.urlProvider.url" )
    private String _strPluginProviderUrl;
    private String _strPluginDbPoolRequired;
    private String _strType;
    private boolean _bIsModule;

    /**
     *
     */
    public DescriptionFormBean(  )
    {
    }

    /**
     * Returns the IdPlugin
     * @return The IdPlugin
     */
    public int getIdPlugin(  )
    {
        return _nIdPlugin;
    }

    /**
     * Sets the IdPlugin
     * @param nIdPlugin The IdPlugin
     */
    public void setIdPlugin( int nIdPlugin )
    {
        _nIdPlugin = nIdPlugin;
    }

    /**
     * Returns the PluginName
     * @return The PluginName
     */
    public String getPluginName(  )
    {
        return _strPluginName;
    }

    /**
     * Sets the PluginName
     * @param strPluginName The PluginName
     */
    public void setPluginName( String strPluginName )
    {
        _strPluginName = strPluginName;
    }

    /**
     * Returns the PluginClass
     * @return The PluginClass
     */
    public String getPluginClass(  )
    {
        return _strPluginClass;
    }

    /**
     * Sets the PluginClass
     * @param strPluginClass The PluginClass
     */
    public void setPluginClass( String strPluginClass )
    {
        _strPluginClass = strPluginClass;
    }

    /**
     * Returns the PluginDescription
     * @return The PluginDescription
     */
    public String getPluginDescription(  )
    {
        return _strPluginDescription;
    }

    /**
     * Sets the PluginDescription
     * @param strPluginDescription The PluginDescription
     */
    public void setPluginDescription( String strPluginDescription )
    {
        _strPluginDescription = strPluginDescription;
    }

    /**
     * Returns the PluginVersion
     * @return The PluginVersion
     */
    public String getPluginVersion(  )
    {
        return _strPluginVersion;
    }

    /**
     * Sets the PluginVersion
     * @param strPluginVersion The PluginVersion
     */
    public void setPluginVersion( String strPluginVersion )
    {
        _strPluginVersion = strPluginVersion;
    }

    /**
     * Returns the PluginCopyright
     * @return The PluginCopyright
     */
    public String getPluginCopyright(  )
    {
        return _strPluginCopyright;
    }

    /**
     * Sets the PluginCopyright
     * @param strPluginCopyright The PluginCopyright
     */
    public void setPluginCopyright( String strPluginCopyright )
    {
        _strPluginCopyright = strPluginCopyright;
    }

    /**
     * Returns the PluginIconUrl
     * @return The PluginIconUrl
     */
    public String getPluginIconUrl(  )
    {
        return _strPluginIconUrl;
    }

    /**
     * Sets the PluginIconUrl
     * @param strPluginIconUrl The PluginIconUrl
     */
    public void setPluginIconUrl( String strPluginIconUrl )
    {
        _strPluginIconUrl = strPluginIconUrl;
    }

    /**
     * Returns the PluginProvider
     * @return The PluginProvider
     */
    public String getPluginProvider(  )
    {
        return _strPluginProvider;
    }

    /**
     * Sets the PluginProvider
     * @param strPluginProvider The PluginProvider
     */
    public void setPluginProvider( String strPluginProvider )
    {
        _strPluginProvider = strPluginProvider;
    }

    /**
     * Returns the PluginProviderUrl
     * @return The PluginProviderUrl
     */
    public String getPluginProviderUrl(  )
    {
        return _strPluginProviderUrl;
    }

    /**
     * Sets the PluginProviderUrl
     * @param strPluginProviderUrl The PluginProviderUrl
     */
    public void setPluginProviderUrl( String strPluginProviderUrl )
    {
        _strPluginProviderUrl = strPluginProviderUrl;
    }

    /**
     * Returns the PluginDbPoolRequired
     * @return The PluginDbPoolRequired
     */
    public String getPluginDbPoolRequired(  )
    {
        return _strPluginDbPoolRequired;
    }

    /**
     * Sets the PluginDbPoolRequired
     * @param strPluginDbPoolRequired The PluginDbPoolRequired
     */
    public void setPluginDbPoolRequired( String strPluginDbPoolRequired )
    {
        _strPluginDbPoolRequired = strPluginDbPoolRequired;
    }

    /**
     * returns the type
     * 
     * @return the type
     */
    public String getType( ) 
    {
        return _strType;
    }

    /**
     * sets the type
     * 
     * @param _strType 
     */
    public void setType( String _strType ) 
    {
        this._strType = _strType;
    }
    

    /**
     * Returns if the plugin is a module
     * @return true if the plugin is a module
     */
    public boolean isModule(  )
    {
        return _bIsModule;
    }
    
    /**
     * Returns if the plugin is a module
     * @return true if the plugin is a module
     */
    public boolean getModule(  )
    {
        return _bIsModule;
    }

    /**
     * Sets the isModule flag
     * @param bIsModule true if the plugin is a module 
     */
    public void setModule( boolean _bIsModule )
    {
    	this._bIsModule = _bIsModule;
    }
}
