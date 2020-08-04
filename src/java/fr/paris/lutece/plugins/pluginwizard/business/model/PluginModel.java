/*
 * Copyright (c) 2002-2020, City of Paris
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
package fr.paris.lutece.plugins.pluginwizard.business.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is the business class for the object PluginModel
 */
public class PluginModel
{
    // Variables declarations
    private int _nIdPlugin;
    private String _strPluginName;
    private String _strPluginClass;
    @NotEmpty( message = "pluginwizard.error.plugin.description.notEmpty" )
    @Size( min = 5, max = 255, message = "pluginwizard.error.plugin.description.size" )
    private String _strPluginDescription;
    private String _strPluginDocumentation;
    private String _strType;
    private String _strPluginInstallation;
    private String _strPluginChanges;
    private String _strPluginUserGuide;
    @NotEmpty( message = "pluginwizard.error.plugin.version.notEmpty" )
    @Pattern( regexp = "[0-9].[0-9].[0-9]", message = "pluginwizard.error.plugin.version.pattern" )
    private String _strPluginVersion;
    @NotEmpty( message = "pluginwizard.error.plugin.copyright.notEmpty" )
    private String _strPluginCopyright;
    private String _strPluginIconUrl;
    @NotEmpty( message = "pluginwizard.error.plugin.provider.notEmpty" )
    @Size( min = 5, max = 255, message = "pluginwizard.error.plugin.description.size" )
    private String _strPluginProvider;
    @NotEmpty( message = "pluginwizard.error.plugin.urlProvider.notEmpty" )
    @URL( message = "pluginwizard.error.plugin.urlProvider.url" )
    private String _strPluginProviderUrl;
    private String _strPluginDbPoolRequired;
    private Locale _locale;
    private List<Application> _listPluginApplications;
    private List<Feature> _listPluginFeatures;
    private List<Portlet> _listPluginPortlets;
    private List<BusinessClass> _listBusinessClasses;
    private Rest _rest;
    private boolean _bIsModule;

    /**
     *
     */
    public PluginModel( )
    {
        _listPluginApplications = new ArrayList<>( );
        _listPluginFeatures = new ArrayList<>( );
        _listPluginPortlets = new ArrayList<>( );
        _listBusinessClasses = new ArrayList<>( );
        _rest = new Rest( );
    }

    /**
     * Returns the IdPlugin
     * 
     * @return The IdPlugin
     */
    public int getIdPlugin( )
    {
        return _nIdPlugin;
    }

    /**
     * Sets the IdPlugin
     * 
     * @param nIdPlugin
     *            The IdPlugin
     */
    public void setIdPlugin( int nIdPlugin )
    {
        _nIdPlugin = nIdPlugin;
    }

    /**
     * Returns the PluginName
     * 
     * @return The PluginName
     */
    public String getPluginName( )
    {
        return _strPluginName;
    }

    /**
     * Returns the Plugin or Module name formatted with dots as package
     * 
     * @return The PluginName
     */
    @JsonIgnore
    public String getPluginNameAsRadicalPackage( )
    {
        if ( isModule( ) )
        {
            return _strPluginName.split( "-" ) [0] + ".modules." + _strPluginName.split( "-" ) [1];
        }
        else
        {
            return _strPluginName;
        }

    }

    /**
     * Returns the Plugin or Module name formatted with slashes as Path
     *
     * @return The PluginName
     */
    @JsonIgnore
    public String getPluginNameAsRadicalPath( )
    {
        if ( isModule( ) )
        {
            return _strPluginName.split( "-" ) [0] + "/modules/" + _strPluginName.split( "-" ) [1];
        }
        else
        {
            return _strPluginName;
        }
    }

    /**
     * Returns the Plugin or Module name for i18n keys
     * 
     * @return The PluginName
     */
    @JsonIgnore
    public String getPluginNameAsPackage( )
    {
        if ( isModule( ) )
        {
            return "module." + _strPluginName.replace( "-", "." );
        }
        else
        {
            return _strPluginName;
        }

    }

    /**
     * Returns the Plugin or Module name for Ressource
     * 
     * @return The PluginName
     */
    @JsonIgnore
    public String getPluginNameForRessource( )
    {
        if ( isModule( ) )
        {
            return getModuleName( ).toLowerCase( );
        }
        else
        {
            return _strPluginName.toLowerCase( );
        }

    }

    /**
     * Returns the Plugin or Module name
     * 
     * @return The PluginName
     */
    @JsonIgnore
    public String getModuleName( )
    {
        if ( isModule( ) )
        {
            return getPluginName( ).split( "-" ) [1];
        }
        else
        {
            return "";
        }
    }

    /**
     * Sets the PluginName
     * 
     * @param strPluginName
     *            The PluginName
     */
    public void setPluginName( String strPluginName )
    {
        _strPluginName = strPluginName;
    }

    /**
     * Returns the PluginClass
     * 
     * @return The PluginClass
     */
    public String getPluginClass( )
    {
        return _strPluginClass;
    }

    /**
     * Sets the PluginClass
     * 
     * @param strPluginClass
     *            The PluginClass
     */
    public void setPluginClass( String strPluginClass )
    {
        _strPluginClass = strPluginClass;
    }

    /**
     * Returns the PluginDescription
     * 
     * @return The PluginDescription
     */
    public String getPluginDescription( )
    {
        return _strPluginDescription;
    }

    /**
     * Sets the PluginDescription
     * 
     * @param strPluginDescription
     *            The PluginDescription
     */
    public void setPluginDescription( String strPluginDescription )
    {
        _strPluginDescription = strPluginDescription;
    }

    /**
     * Returns the PluginDocumentation
     * 
     * @return The PluginDocumentation
     */
    public String getPluginDocumentation( )
    {
        return _strPluginDocumentation;
    }

    /**
     * Sets the PluginDocumentation
     * 
     * @param strPluginDocumentation
     *            The PluginDocumentation
     */
    public void setPluginDocumentation( String strPluginDocumentation )
    {
        _strPluginDocumentation = strPluginDocumentation;
    }

    /**
     * Returns the PluginInstallation
     * 
     * @return The PluginInstallation
     */
    public String getPluginInstallation( )
    {
        return _strPluginInstallation;
    }

    /**
     * Sets the PluginInstallation
     * 
     * @param strPluginInstallation
     *            The PluginInstallation
     */
    public void setPluginInstallation( String strPluginInstallation )
    {
        _strPluginInstallation = strPluginInstallation;
    }

    /**
     * Returns the PluginChanges
     * 
     * @return The PluginChanges
     */
    public String getPluginChanges( )
    {
        return _strPluginChanges;
    }

    /**
     * Sets the PluginChanges
     * 
     * @param strPluginChanges
     *            The PluginChanges
     */
    public void setPluginChanges( String strPluginChanges )
    {
        _strPluginChanges = strPluginChanges;
    }

    /**
     * Returns the PluginUserGuide
     * 
     * @return The PluginUserGuide
     */
    public String getPluginUserGuide( )
    {
        return _strPluginUserGuide;
    }

    /**
     * Sets the PluginUserGuide
     * 
     * @param strPluginUserGuide
     *            The PluginUserGuide
     */
    public void setPluginUserGuide( String strPluginUserGuide )
    {
        _strPluginUserGuide = strPluginUserGuide;
    }

    /**
     * Returns the PluginVersion
     * 
     * @return The PluginVersion
     */
    public String getPluginVersion( )
    {
        return _strPluginVersion;
    }

    /**
     * Sets the PluginVersion
     * 
     * @param strPluginVersion
     *            The PluginVersion
     */
    public void setPluginVersion( String strPluginVersion )
    {
        _strPluginVersion = strPluginVersion;
    }

    /**
     * Returns the PluginCopyright
     * 
     * @return The PluginCopyright
     */
    public String getPluginCopyright( )
    {
        return _strPluginCopyright;
    }

    /**
     * Sets the PluginCopyright
     * 
     * @param strPluginCopyright
     *            The PluginCopyright
     */
    public void setPluginCopyright( String strPluginCopyright )
    {
        _strPluginCopyright = strPluginCopyright;
    }

    /**
     * Returns the PluginIconUrl
     * 
     * @return The PluginIconUrl
     */
    public String getPluginIconUrl( )
    {
        return _strPluginIconUrl;
    }

    /**
     * Sets the PluginIconUrl
     * 
     * @param strPluginIconUrl
     *            The PluginIconUrl
     */
    public void setPluginIconUrl( String strPluginIconUrl )
    {
        _strPluginIconUrl = strPluginIconUrl;
    }

    /**
     * Returns the PluginProvider
     * 
     * @return The PluginProvider
     */
    public String getPluginProvider( )
    {
        return _strPluginProvider;
    }

    /**
     * Sets the PluginProvider
     * 
     * @param strPluginProvider
     *            The PluginProvider
     */
    public void setPluginProvider( String strPluginProvider )
    {
        _strPluginProvider = strPluginProvider;
    }

    /**
     * Returns the PluginProviderUrl
     * 
     * @return The PluginProviderUrl
     */
    public String getPluginProviderUrl( )
    {
        return _strPluginProviderUrl;
    }

    /**
     * Sets the PluginProviderUrl
     * 
     * @param strPluginProviderUrl
     *            The PluginProviderUrl
     */
    public void setPluginProviderUrl( String strPluginProviderUrl )
    {
        _strPluginProviderUrl = strPluginProviderUrl;
    }

    /**
     * Returns the PluginDbPoolRequired
     * 
     * @return The PluginDbPoolRequired
     */
    public String getPluginDbPoolRequired( )
    {
        return _strPluginDbPoolRequired;
    }

    /**
     * Sets the PluginDbPoolRequired
     * 
     * @param strPluginDbPoolRequired
     *            The PluginDbPoolRequired
     */
    public void setPluginDbPoolRequired( String strPluginDbPoolRequired )
    {
        _strPluginDbPoolRequired = strPluginDbPoolRequired;
    }

    /**
     * Gets the locale of the plugin
     * 
     * @return The Locale
     */
    public Locale getLocale( )
    {
        return _locale;
    }

    /**
     * Gets the locale of the plugin
     * 
     * @param locale
     *            The locale
     */
    public void setLocale( Locale locale )
    {
        _locale = locale;
    }

    /**
     * Sets the list of plugin applications
     * 
     * @param listPluginApplications
     *            The list of plugin applications
     */
    public void setApplications( List<Application> listPluginApplications )
    {
        if ( listPluginApplications != null )
        {
            _listPluginApplications = new ArrayList<>( listPluginApplications );
        }
        else
        {
            _listPluginApplications = null;
        }
    }

    /**
     * Returns the list of plugin applications
     * 
     * @return The collection of applications
     */
    public List<Application> getApplications( )
    {
        return new ArrayList<>( _listPluginApplications );
    }

    /**
     * Sets the rest
     * 
     * @param rest
     *            The rest
     */
    public void setRest( Rest rest )
    {
        _rest = rest;
    }

    /**
     * Returns the rest
     * 
     * @return The rest
     */
    public Rest getRest( )
    {
        return _rest;
    }

    /**
     * Sets the list of plugin features
     * 
     * @param listPluginFeatures
     *            The list of plugi features
     */
    public void setFeatures( List<Feature> listPluginFeatures )
    {
        if ( listPluginFeatures != null )
        {
            _listPluginFeatures = new ArrayList<>( listPluginFeatures );
        }
        else
        {
            _listPluginFeatures = null;
        }
    }

    /**
     * Returns the list of plugin features
     * 
     * @return The plugin features
     */
    public List<Feature> getFeatures( )
    {
        return new ArrayList<>( _listPluginFeatures );
    }

    /**
     * Sets the list of plugin portlets
     * 
     * @param listPluginPortlets
     *            The list of plugin portlets
     */
    public void setPortlets( List<Portlet> listPluginPortlets )
    {
        if ( listPluginPortlets != null )
        {
            _listPluginPortlets = new ArrayList<>( listPluginPortlets );
        }
        else
        {
            _listPluginPortlets = null;
        }
    }

    /**
     * Returns the list of plugin portlets
     * 
     * @return The list of portlets
     */
    public List<Portlet> getPortlets( )
    {
        return new ArrayList<>( _listPluginPortlets );
    }

    /**
     * Returns the list of business classes attached to the generated plugin
     * 
     * @return The list of business classes
     */
    public List<BusinessClass> getBusinessClasses( )
    {
        return new ArrayList<>( _listBusinessClasses );
    }

    public void setBusinessClasses( List<BusinessClass> listBusinessClasses )
    {
        _listBusinessClasses = new ArrayList<>( listBusinessClasses );
    }

    public List<Feature> BusinessClass( )
    {
        throw new UnsupportedOperationException( "Not supported yet." ); // To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the Type (module or plugin)
     * 
     * @return The Type
     */
    public String getType( )
    {
        return _strType;
    }

    /**
     * Sets the Type
     * 
     * @param The
     *            Type
     */
    public void setType( String strType )
    {
        _strType = strType;
    }

    /**
     * Returns the isModule boolean value
     * 
     * @return The isModule
     */
    public boolean isModule( )
    {
        return ( _bIsModule || "MODULE".equals( _strType ) );
    }

    /**
     * Returns the isModule boolean value
     * 
     * @return The isModule
     */
    @JsonIgnore
    public boolean getModule( )
    {
        return _bIsModule;
    }

    /**
     * Sets the isModule flag
     * 
     * @param _bIsModule
     *            The isModule boolean value
     */
    public void setModule( boolean bIsModule )
    {
        this._bIsModule = bIsModule;
    }

}
