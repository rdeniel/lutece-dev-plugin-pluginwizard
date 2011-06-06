/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for PluginModel objects
 */
public final class PluginModelHome
{
    // Static variable pointed at the DAO instance
    private static IPluginModelDAO _dao = (IPluginModelDAO) SpringContextService.getPluginBean( "pluginwizard",
            "pluginwizard.pluginModelDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private PluginModelHome(  )
    {
    }

    /**
     * Create an instance of the pluginModel class
     * @param pluginModel The instance of the PluginModel which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of pluginModel which has been created with its primary key.
     */
    public static PluginModel create( PluginModel pluginModel, Plugin plugin )
    {
        _dao.insert( pluginModel, plugin );

        return pluginModel;
    }

    /**
     * Update of the pluginModel which is specified in parameter
     * @param pluginModel The instance of the PluginModel which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  pluginModel which has been updated
     */
    public static PluginModel update( PluginModel pluginModel, Plugin plugin )
    {
        _dao.store( pluginModel, plugin );

        return pluginModel;
    }

    /**
     * Remove the pluginModel whose identifier is specified in parameter
     * @param nPluginModelId The pluginModel Id
     * @param plugin the Plugin
     */
    public static void remove( int nPluginModelId, Plugin plugin )
    {
        _dao.delete( nPluginModelId, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a pluginModel whose identifier is specified in parameter
     * @param nKey The pluginModel primary key
     * @param plugin the Plugin
     * @return an instance of PluginModel
     */
    public static PluginModel findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Load the data of all the pluginModel objects and returns them in form of a collection
     * @param plugin the Plugin
     * @return the collection which contains the data of all the pluginModel objects
     */
    public static Collection<PluginModel> getPluginModelsList( Plugin plugin )
    {
        return _dao.selectPluginModelsList( plugin );
    }

    /**
     * Returns the identifier of the plugin model
     * @param plugin The plugin
     * @param strName The name of the generated plugin
     * @return The identifier of the plugin model
     */
    public static int getPluginModelId( Plugin plugin, String strName )
    {
        return _dao.selectPluginModelByName( plugin, strName );
    }

    /**
     * A boolean to indicate if plugin exists
     * @param strPluginName The plugin name
     * @param plugin The plugin
     * @return A boolean value indicating if plugin exists
     */
    public static boolean pluginExists( String strPluginName, Plugin plugin )
    {
        return _dao.pluginExists( strPluginName, plugin );
    }
}
