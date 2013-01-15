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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for ResourceKey objects
 */
public final class ResourceKeyDAO implements IResourceKeyDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_resource_key ) FROM pluginwizard_plugin_resource_key";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_resource_key ( id_resource_key, marker_identifier, english_locale, french_locale, id_plugin ) VALUES (  ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_SELECTALL_BY_PLUGIN = "select id_resource_key, marker_identifier, english_locale, french_locale FROM pluginwizard_plugin_resource_key WHERE id_plugin= ?";
    private static final String SQL_QUERY_DELETE_ALL_BY_PLUGIN = "DELETE FROM pluginwizard_plugin_resource_key WHERE id_plugin = ?";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * @param resourceKey instance of the ResourceKey object to insert
     * @param plugin The plugin
     */
    @Override
    public void insert( ResourceKey resourceKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        resourceKey.setIdResourceKey( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, resourceKey.getIdResourceKey(  ) );
        daoUtil.setString( 2, resourceKey.getMarkerIdentifier(  ) );
        daoUtil.setString( 3, resourceKey.getEnglishLocale(  ) );
        daoUtil.setString( 4, resourceKey.getFrenchLocale(  ) );
        daoUtil.setInt( 5, resourceKey.getIdPlugin(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
    * Delete all records from the table
    * @param nPluginId The identifier of the resourceKey
    * @param plugin The plugin
    */
    public void deleteAllByPlugin( int nPluginId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_ALL_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Returns all the resource keys related to a generated plugin
     * @param nPluginId The identifier of the plugin
     * @param plugin The plugin
     * @return A collection of resource keys
     */
    @Override
    public Collection<ResourceKey> selectResourceKeysList( int nPluginId, Plugin plugin )
    {
        Collection<ResourceKey> resourceKeyList = new ArrayList<ResourceKey>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            ResourceKey resourceKey = new ResourceKey(  );

            resourceKey.setIdResourceKey( daoUtil.getInt( 1 ) );
            resourceKey.setMarkerIdentifier( daoUtil.getString( 2 ) );
            resourceKey.setEnglishLocale( daoUtil.getString( 3 ) );
            resourceKey.setFrenchLocale( daoUtil.getString( 4 ) );

            resourceKeyList.add( resourceKey );
        }

        daoUtil.free(  );

        return resourceKeyList;
    }

    /**
     * Delete all resources related to plugin record from the table
     * @param nPluginId The identifier of the plugin
     * @param plugin The plugin
     */
    @Override
    public void deleteByPlugin( int nPluginId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_ALL_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
