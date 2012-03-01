/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for ResourceKey objects
 */
public final class ResourceKeyHome
{
    // Static variable pointed at the DAO instance
    private static IResourceKeyDAO _dao = (IResourceKeyDAO) SpringContextService.getPluginBean( "pluginwizard",
            "pluginwizard.resourceKeyDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ResourceKeyHome(  )
    {
    }

    /**
     * Removes i18n ressources by plugin
     * @param nIdPlugin The id of the plugin
     * @param plugin The plugin
     */
    public static void removeResourcesByPlugin( int nIdPlugin, Plugin plugin )
    {
        throw new UnsupportedOperationException( "Not yet implemented" ); //TODO
    }

    /**
     * Create an instance of the resourceKey class
     * @param resourceKey The instance of the ResourceKey which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of resourceKey which has been created with its primary key.
     */
    public static ResourceKey create( ResourceKey resourceKey, Plugin plugin )
    {
        _dao.insert( resourceKey, plugin );

        return resourceKey;
    }

    /**
     * Update of the resourceKey which is specified in parameter
     * @param resourceKey The instance of the ResourceKey which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  resourceKey which has been updated
     */
    public static ResourceKey update( ResourceKey resourceKey, Plugin plugin )
    {
        _dao.store( resourceKey, plugin );

        return resourceKey;
    }

    /**
     * Remove the resourceKey whose identifier is specified in parameter
     * @param nResourceKeyId The resourceKey Id
     * @param plugin the Plugin
     */
    public static void remove( int nResourceKeyId, Plugin plugin )
    {
        _dao.delete( nResourceKeyId, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a resourceKey whose identifier is specified in parameter
     * @param nKey The resourceKey primary key
     * @param plugin the Plugin
     * @return an instance of ResourceKey
     */
    public static ResourceKey findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Load the data of all the resourceKey objects and returns them in form of a collection
     * @param plugin the Plugin
     * @return the collection which contains the data of all the resourceKey objects
     */
    public static Collection<ResourceKey> getResourceKeysList( Plugin plugin )
    {
        return _dao.selectResourceKeysList( plugin );
    }

    /**
     * Load the data of all the resourceKey objects and returns them in form of a collection
     * @param nPluginId The identifier of the generated plugin
     * @param plugin the Plugin
     * @return the collection which contains the data of all the resourceKey objects
     */
    public static Collection<ResourceKey> getResourceKeysList( int nPluginId, Plugin plugin )
    {
        return _dao.selectResourceKeysList( nPluginId, plugin );
    }

    /**
     * Load the data of all the resourceKey objects and returns them in form of a collection
     * @param nPluginId The identifier of the generated plugin
     * @param plugin the Plugin
     * @return the collection which contains the data of all the resourceKey objects
     */
    public static boolean isValid( int nPluginId, Plugin plugin )
    {
        return _dao.isValid( nPluginId, plugin );
    }

    /**
     * Persists the i18n keys
     * @param nPluginId The id of the plugin
     * @param listKeys The i18n keys
     * @param plugin The plugin
     */
    public static void addEmptyKeys( int nPluginId, ArrayList<String> listKeys, Plugin plugin )
    {
        _dao.addEmptyKeys( nPluginId, listKeys, plugin );
    }

    /**
     * Verifies if the keys have been generated for the plugin
     * @param nPluginId The id of the generated plugin
     * @param plugin The plugin
     * @return A boolean value
     */
    public static boolean isEmpty( int nPluginId, Plugin plugin )
    {
        return _dao.isEmpty( nPluginId, plugin );
    }

    /**
     * Deletes all the resources for a generated plugin
     * @param nPluginId The Plugin Id
     * @param plugin The Plugin
     */
    public static void deleteKeysByPlugin( int nPluginId, Plugin plugin )
    {
        _dao.deleteByPlugin( nPluginId, plugin );
    }
}
