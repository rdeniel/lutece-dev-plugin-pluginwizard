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
package fr.paris.lutece.plugins.pluginwizard.business.model;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for PluginFeature objects
 */
public final class PluginFeatureHome
{
    // Static variable pointed at the DAO instance
    private static IPluginFeatureDAO _dao = (IPluginFeatureDAO) SpringContextService.getPluginBean( "pluginwizard",
            "pluginwizard.pluginFeatureDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private PluginFeatureHome(  )
    {
    }

    /**
     * Removes the features attached to a plugin
     * @param nIdPlugin The identifier of the plugin
     * @param plugin The plugin
     */
    public static void removePluginFeaturesByPlugin( int nIdPlugin, Plugin plugin )
    {
        _dao.deleteAllPluginFeaturesByPluginId( nIdPlugin, plugin );
    }

    /**
     * Create an instance of the pluginFeature class
     * @param pluginFeature The instance of the PluginFeature which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of pluginFeature which has been created with its primary key.
     */
    public static PluginFeature create( PluginFeature pluginFeature, Plugin plugin )
    {
        _dao.insert( pluginFeature, plugin );

        return pluginFeature;
    }

    /**
     * Update of the pluginFeature which is specified in parameter
     * @param pluginFeature The instance of the PluginFeature which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  pluginFeature which has been updated
     */
    public static PluginFeature update( PluginFeature pluginFeature, Plugin plugin )
    {
        _dao.store( pluginFeature, plugin );

        return pluginFeature;
    }

    /**
     * Remove the pluginFeature whose identifier is specified in parameter
     * @param nPluginFeatureId The pluginFeature Id
     * @param plugin the Plugin
     */
    public static void remove( int nPluginFeatureId, Plugin plugin )
    {
        _dao.delete( nPluginFeatureId, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a pluginFeature whose identifier is specified in parameter
     * @param nKey The pluginFeature primary key
     * @param plugin the Plugin
     * @return an instance of PluginFeature
     */
    public static PluginFeature findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Returns the collection of plugin features for a specific plugin
     * @param nPluginId The plugin id
     * @param plugin The plugin
     * @return The collection of the plugin features
     */
    public static Collection<PluginFeature> findByPlugin( int nPluginId, Plugin plugin )
    {
        return _dao.selectFeatureByPlugin( nPluginId, plugin );
    }

    /**
     *  Returns a reference list of features available for the plugin
     * @param nPluginId The id of the generated plugin
     * @param plugin The plugin
     * @return The reference list
     */
    public static ReferenceList getAdminFeaturesForPlugin( int nPluginId, Plugin plugin )
    {
        return _dao.selectFeatureByPluginCombo( nPluginId, plugin );
    }
}
