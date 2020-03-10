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

import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKey;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The Pom generator is responsible of generating a project object model used by maven
 *
 */
public class PomGenerator extends AbstractFileGenerator
{

    private static final String KEY_PREFIX = "plugin-wizard.site_property.";

    private String _strModelVersion;
    private String _strGlobalPomVersion;
    private String _strDependencyCoreVersion;
    private String _strDependencyRestVersion;

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm, String generationSchemeName )
    {
        HashMap map = new HashMap( );
        map.put( getFilePath( pm ), getCode( pm, generationSchemeName ) );

        return map;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String getCode( PluginModel pm, String generationSchemeName )
    {
        Map<String, Object> model = new HashMap<>( );
        Collection<ConfigurationKey> listKeys = ConfigurationKeyHome.getConfigurationKeysList( );

        // Fetches the actual configuration values to be replaced in the templates
        for ( ConfigurationKey key : listKeys )
        {
            model.put( key.getKeyDescription( ).trim( ), key.getKeyValue( ) );
        }

        model.put( MARK_KOTLIN, isKotlin( ) );
        model.put( Markers.MARK_PLUGIN, pm );
        model.put( Markers.MARK_GLOBAL_POM_VERSION, getGlobalPomVersion( ) );
        model.put( Markers.MARK_CORE_VERSION, getDependencyCoreVersion( ) );
        model.put( Markers.MARK_DEPENDECY_REST_VERSION, getDependencyRestVersion( ) );
        model.put( Markers.MARK_POM_MODEL_VERSION, getModelVersion( ) );

        return build( model );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String getFilename( PluginModel pm )
    {
        return "pom.xml";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getPath( )
    {
        return "";
    }

    /**
     * get pom model version
     * 
     * @return the pom model version
     */
    public String getModelVersion( )
    {
        return _strModelVersion;
    }

    /**
     * set PomModelVersion
     * 
     * @param strPomModelVersion
     */
    public void setModelVersion( String strModelVersion )
    {
        this._strModelVersion = strModelVersion;
    }

    /**
     * get global pom Version
     * 
     * @return the global Pom Version
     */
    public String getGlobalPomVersion( )
    {
        return _strGlobalPomVersion;
    }

    /**
     * set Pom Parent Version
     * 
     * @param strVersion
     */
    public void setGlobalPomVersion( String strVersion )
    {
        this._strGlobalPomVersion = strVersion;
    }

    /**
     * get Dependency Core Version
     * 
     * @return the Dependency Core Version
     */
    public String getDependencyCoreVersion( )
    {
        return _strDependencyCoreVersion;
    }

    /**
     * set Dependency Core Version
     * 
     * @param strDependencyCoreVersion
     */
    public void setDependencyCoreVersion( String strDependencyCoreVersion )
    {
        this._strDependencyCoreVersion = strDependencyCoreVersion;
    }

    /**
     * get Dependency Rest Version
     * 
     * @return the Dependency Rest Version
     */
    public String getDependencyRestVersion( )
    {
        return _strDependencyRestVersion;
    }

    /**
     * set Dependency Rest Version
     * 
     * @param strDependencyRestVersion
     */
    public void setDependencyRestVersion( String strDependencyRestVersion )
    {
        this._strDependencyRestVersion = strDependencyRestVersion;
    }
}
