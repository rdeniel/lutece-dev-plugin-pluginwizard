/*
 * Copyright (c) 2002-2009, Mairie de Paris
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
    private static final String SQL_QUERY_SELECT = "SELECT id_resource_key, id_plugin, marker_identifier, english_locale, french_locale FROM pluginwizard_plugin_resource_key WHERE id_resource_key = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_resource_key ( id_resource_key, marker_identifier, english_locale, french_locale ) VALUES (  ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_id_resource_key WHERE id_resource_key = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_resource_key SET id_resource_key = ?, id_plugin = ?, marker_identifier = ?, english_locale = ?, french_locale = ? WHERE id_resource_key = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_resource_key, id_plugin, marker_identifier, english_locale, french_locale FROM pluginwizard_plugin_resource_key";
    private static final String SQL_QUERY_SELECTALL_ID_BY_PLUGIN = "select a.id_resource_key from pluginwizard_plugin_resource_key as a, pluginwizard_plugin_id_resource_key as b where a.id_resource_key=b.id_resource_key AND b.id_plugin= ?";
    private static final String SQL_QUERY_SELECTALL_BY_PLUGIN = "select a.id_resource_key, a.marker_identifier, a.english_locale, a.french_locale from pluginwizard_plugin_resource_key as a, pluginwizard_plugin_id_resource_key as b where a.id_resource_key=b.id_resource_key AND b.id_plugin= ?";
    private static final String SQL_QUERY_COUNT_RESOURCE = "SELECT COUNT(id_resource_key) FROM pluginwizard_plugin_id_resource_key WHERE id_plugin = ?";
    private static final String SQL_QUERY_DELETE_RESOURCE_BY_PLUGIN = "DELETE FROM pluginwizard_plugin_resource_key WHERE id_plugin = ?";
    private static final String SQL_QUERY_DELETE_RESOURCE_DEPENDENCY = "DELETE FROM pluginwizard_plugin_id_resource_key WHERE id_resource_key = ? ";
    private static final String SQL_QUERY_DELETE_ALL_BY_PLUGIN = "";
    private static final String SQL_QUERY_INSERT_PLUGIN_RESOURCE_DEPENDENCY = "INSERT INTO pluginwizard_plugin_id_resource_key ( id_plugin , id_resource_key ) VALUES ( ? , ? )";

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
    public void insert( ResourceKey resourceKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        resourceKey.setIdResourceKey( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, resourceKey.getIdResourceKey(  ) );
        daoUtil.setString( 2, resourceKey.getMarkerIdentifier(  ) );
        daoUtil.setString( 3, resourceKey.getEnglishLocale(  ) );
        daoUtil.setString( 4, resourceKey.getFrenchLocale(  ) );
        insertDependency( resourceKey.getIdPlugin(  ), resourceKey.getIdResourceKey(  ), plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Inserts the dependency between the plugin and its resource keys
     * @param nIdPlugin The id of the plugin
     * @param nIdResourceKey The id of the resource key
     * @param plugin The plugin
     */
    public void insertDependency( int nIdPlugin, int nIdResourceKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PLUGIN_RESOURCE_DEPENDENCY, plugin );

        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.setInt( 2, nIdResourceKey );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the resourceKey from the table
     * @param nId The identifier of the resourceKey
     * @param plugin The plugin
     * @return the instance of the ResourceKey
     */
    public ResourceKey load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        ResourceKey resourceKey = null;

        if ( daoUtil.next(  ) )
        {
            resourceKey = new ResourceKey(  );

            resourceKey.setIdResourceKey( daoUtil.getInt( 1 ) );
            resourceKey.setIdPlugin( daoUtil.getInt( 2 ) );
            resourceKey.setMarkerIdentifier( daoUtil.getString( 3 ) );
            resourceKey.setEnglishLocale( daoUtil.getString( 4 ) );
            resourceKey.setFrenchLocale( daoUtil.getString( 5 ) );
        }

        daoUtil.free(  );

        return resourceKey;
    }

    /**
     * Delete a record from the table
     * @param nResourceKeyId The identifier of the resourceKey
     * @param plugin The plugin
     */
    public void delete( int nResourceKeyId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nResourceKeyId );
        daoUtil.executeUpdate(  );
        deleteDependency( nResourceKeyId, plugin );
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
        deleteDependency( nPluginId, plugin );
        daoUtil.free(  );
    }

    /**
     * Delete the dependency
     * @param nResourceKeyId The identifier of the ressource
     * @param plugin The plugin
     */
    public void deleteDependency( int nResourceKeyId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_RESOURCE_DEPENDENCY, plugin );
        daoUtil.setInt( 1, nResourceKeyId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param resourceKey The reference of the resourceKey
     * @param plugin The plugin
     */
    public void store( ResourceKey resourceKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, resourceKey.getIdResourceKey(  ) );
        daoUtil.setInt( 2, resourceKey.getIdPlugin(  ) );
        daoUtil.setString( 3, resourceKey.getMarkerIdentifier(  ) );
        daoUtil.setString( 4, resourceKey.getEnglishLocale(  ) );
        daoUtil.setString( 5, resourceKey.getFrenchLocale(  ) );
        daoUtil.setInt( 6, resourceKey.getIdResourceKey(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the resourceKeys and returns them as a collection
     * @param plugin The plugin
     * @return The Collection which contains the data of all the resourceKeys
     */
    public Collection<ResourceKey> selectResourceKeysList( Plugin plugin )
    {
        Collection<ResourceKey> resourceKeyList = new ArrayList<ResourceKey>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            ResourceKey resourceKey = new ResourceKey(  );

            resourceKey.setIdResourceKey( daoUtil.getInt( 1 ) );
            resourceKey.setIdPlugin( daoUtil.getInt( 2 ) );
            resourceKey.setMarkerIdentifier( daoUtil.getString( 3 ) );
            resourceKey.setEnglishLocale( daoUtil.getString( 4 ) );
            resourceKey.setFrenchLocale( daoUtil.getString( 5 ) );

            resourceKeyList.add( resourceKey );
        }

        daoUtil.free(  );

        return resourceKeyList;
    }

    /**
     * Returns all the resource keys related to a generated plugin
     * @param nPluginId The identifier of the plugin
     * @param plugin The plugin
     * @return A collection of resource keys
     */
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
     * Fetches all the resource keys by plugin
     * @param nPluginId The identifier of the generated plugin
     * @param plugin The plugin
     * @return A collection of resource keys
     */
    public Collection<ResourceKey> getAllResourceKeys( int nPluginId, Plugin plugin )
    {
        Collection<ResourceKey> listResourceKey = new ArrayList<ResourceKey>(  );
        Collection<ResourceKey> resourceKeyUserAdded = selectResourceKeysList( nPluginId, plugin );
        listResourceKey.addAll( resourceKeyUserAdded );

        Collection<PluginFeature> listPluginFeature = PluginFeatureHome.findByPlugin( nPluginId, plugin );

        for ( PluginFeature pluginFeature : listPluginFeature )
        {
            ResourceKey key = new ResourceKey(  );
            key.setMarkerIdentifier( pluginFeature.getPluginFeatureTitle(  ) );
            key.setMarkerIdentifier( pluginFeature.getPluginFeatureDescription(  ) );
            //TODO Add the create,modify and delete keys
            listResourceKey.add( key );
        }

        Collection<PluginPortlet> listPluginPortlet = PluginPortletHome.findByPlugin( nPluginId, plugin );

        for ( PluginPortlet pluginPortlet : listPluginPortlet )
        {
            ResourceKey key = new ResourceKey(  );
            key.setMarkerIdentifier( pluginPortlet.getPluginPortletTypeName(  ) );
            listResourceKey.add( key );
        }

        return listResourceKey;
    }

    /**
     * Returns whether all the resource keys are filled before generation
     * @param nPluginId The identifier of the plugin
     * @param plugin The plugin
     * @return A boolean value indicating whether the plugin is valid
     */
    public boolean isValid( int nPluginId, Plugin plugin )
    {
        //TODO Not implemented and
        return false;
    }

    /**
     * Adds the list of required i18n keys
     * @param nPluginId The id  of the plugin
     * @param listKeys The markers identifier
     * @param plugin The plugin
     */
    public void addEmptyKeys( int nPluginId, ArrayList<String> listKeys, Plugin plugin )
    {
        for ( String strKey : listKeys )
        {
            ResourceKey resourceKey = new ResourceKey(  );

            resourceKey.setIdPlugin( nPluginId );
            resourceKey.setMarkerIdentifier( strKey );
            resourceKey.setEnglishLocale( strKey.substring( strKey.lastIndexOf( '.' ) + 1 ) );
            resourceKey.setFrenchLocale( strKey.substring( strKey.lastIndexOf( '.' ) + 1 ) );
            insert( resourceKey, plugin );
        }
    }

    /**
     * Verifies whether no resource is present for a plugin
     * @param nPluginId The identifier of the plugin
     * @param plugin The plugin
     * @return A boolean value which is true when no resource is associated to the plugin
     */
    public boolean isEmpty( int nPluginId, Plugin plugin )
    {
        boolean bValue = true;
        int nCount = 0;
        Collection<ResourceKey> resourceKeyList = new ArrayList<ResourceKey>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_COUNT_RESOURCE, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            nCount = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        if ( nCount > 0 )
        {
            bValue = false;
        }

        return bValue;
    }

    /**
     * Delete all resources related to plugin record from the table
     * @param nPluginId The identifier of the plugin
     * @param plugin The plugin
     */
    public void deleteByPlugin( int nPluginId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            int nResourceId = daoUtil.getInt( 1 );

            delete( nResourceId, plugin );
        }

        daoUtil.free(  );
    }
}
