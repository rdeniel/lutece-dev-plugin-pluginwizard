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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for PluginFeature objects
 */
public final class PluginFeatureDAO implements IPluginFeatureDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_plugin_feature ) FROM pluginwizard_plugin_feature";
    private static final String SQL_QUERY_SELECT = "SELECT b.id_plugin, a.id_plugin_feature,a.plugin_feature_label, a.plugin_feature_title, a.plugin_feature_level, a.plugin_feature_url, a.plugin_feature_description FROM pluginwizard_plugin_feature as a,pluginwizard_plugin_id_feature as b  WHERE a.id_plugin_feature = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_feature ( id_plugin_feature,plugin_feature_label, plugin_feature_title, plugin_feature_level, plugin_feature_url, plugin_feature_description ) VALUES (?, ?, ?, ?, ? ,?) ";
    private static final String SQL_QUERY_INSERT_PLUGIN_FEATURE_DEPENDENCY = "INSERT INTO pluginwizard_plugin_id_feature ( id_plugin, id_plugin_feature) VALUES ( ?, ?)";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_feature WHERE id_plugin_feature = ? ";
    private static final String SQL_QUERY_DELETE_PLUGIN_FEATURE_DEPENDENCY = "DELETE FROM pluginwizard_plugin_id_feature WHERE id_plugin_feature = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_feature SET id_plugin_feature = ?, plugin_feature_label =?, plugin_feature_title = ?, plugin_feature_level = ?, plugin_feature_url = ?, plugin_feature_description = ? WHERE  id_plugin_feature = ?";
    private static final String SQL_QUERY_SELECTALL_BY_PLUGIN = "SELECT b.id_plugin,a.id_plugin_feature,a.plugin_feature_label, a.plugin_feature_title, a.plugin_feature_level, a.plugin_feature_url, a.plugin_feature_description FROM pluginwizard_plugin_feature as a , pluginwizard_plugin_id_feature as b  WHERE a.id_plugin_feature=b.id_plugin_feature AND b.id_plugin = ?";
    private static final String SQL_QUERY_SELECTALL_BY_PLUGIN_COMBO = "SELECT a.id_plugin_feature,a.plugin_feature_label FROM pluginwizard_plugin_feature as a, pluginwizard_plugin_id_feature as b WHERE a.id_plugin_feature=b.id_plugin_feature AND id_plugin= ?";
    private static final String SQL_QUERY_SELECTALL_FEATURE_ID_BY_PLUGIN = "SELECT id_plugin_feature FROM pluginwizard_plugin_id_feature where id_plugin= ?";

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
     * @param pluginFeature instance of the PluginFeature object to insert
     * @param plugin The plugin
     */
    public void insert( PluginFeature pluginFeature, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        pluginFeature.setIdPluginFeature( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, pluginFeature.getIdPluginFeature(  ) );
        daoUtil.setString( 2, pluginFeature.getPluginFeatureRight(  ) );
        daoUtil.setString( 3, pluginFeature.getPluginFeatureTitle(  ) );
        daoUtil.setString( 4, pluginFeature.getPluginFeatureLevel(  ) );
        daoUtil.setString( 5, pluginFeature.getPluginFeatureName(  ) );
        daoUtil.setString( 6, pluginFeature.getPluginFeatureDescription(  ) );
        insertDependency( pluginFeature.getIdPlugin(  ), pluginFeature.getIdPluginFeature(  ), plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Inserts the dependency between the plugin and the admin feature
     * @param nIdPlugin The identifier of the plugin
     * @param nIdPluginFeature The identifier of the pluginFeature
     * @param plugin The plugin
     */
    public void insertDependency( int nIdPlugin, int nIdPluginFeature, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PLUGIN_FEATURE_DEPENDENCY, plugin );

        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.setInt( 2, nIdPluginFeature );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the pluginFeature from the table
     * @param nIdFeature The identifier of the pluginFeature
     * @param plugin The plugin
     * @return the instance of the PluginFeature
     */
    public PluginFeature load( int nIdFeature, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nIdFeature );
        daoUtil.executeQuery(  );

        PluginFeature pluginFeature = null;

        if ( daoUtil.next(  ) )
        {
            pluginFeature = new PluginFeature(  );

            pluginFeature.setIdPlugin( daoUtil.getInt( 1 ) );
            pluginFeature.setIdPluginFeature( daoUtil.getInt( 2 ) );

            pluginFeature.setPluginFeatureRight( daoUtil.getString( 3 ) );
            pluginFeature.setPluginFeatureTitle( daoUtil.getString( 4 ) );
            pluginFeature.setPluginFeatureLevel( daoUtil.getString( 5 ) );
            pluginFeature.setPluginFeatureName( daoUtil.getString( 6 ) );
            pluginFeature.setPluginFeatureDescription( daoUtil.getString( 7 ) );
        }

        daoUtil.free(  );

        return pluginFeature;
    }

    /**
     * Delete a record from the table
     * @param nPluginFeatureId The identifier of the pluginFeature
     * @param plugin The plugin
     */
    public void delete( int nPluginFeatureId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nPluginFeatureId );
        deleteDependency( nPluginFeatureId, plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
    * Delete the dependency
    * @param nPluginFeatureId The identifier of the pluginFeature
    * @param plugin The plugin
    */
    public void deleteDependency( int nPluginFeatureId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PLUGIN_FEATURE_DEPENDENCY, plugin );
        daoUtil.setInt( 1, nPluginFeatureId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param pluginFeature The reference of the pluginFeature
     * @param plugin The plugin
     */
    public void store( PluginFeature pluginFeature, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, pluginFeature.getIdPluginFeature(  ) );
        daoUtil.setString( 2, pluginFeature.getPluginFeatureRight(  ) );
        daoUtil.setString( 3, pluginFeature.getPluginFeatureTitle(  ) );
        daoUtil.setString( 4, pluginFeature.getPluginFeatureLevel(  ) );
        daoUtil.setString( 5, pluginFeature.getPluginFeatureName(  ) );
        daoUtil.setString( 6, pluginFeature.getPluginFeatureDescription(  ) );
        daoUtil.setInt( 7, pluginFeature.getIdPluginFeature(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
    * Load the data of all the pluginFeatures and returns them as a collection
    * @param nPluginId The identifier of the plugin
    * @param plugin The plugin
    * @return The Collection which contains the data of all the pluginFeatures
    */
    public Collection<PluginFeature> selectFeatureByPlugin( int nPluginId, Plugin plugin )
    {
        Collection<PluginFeature> pluginFeatureList = new ArrayList<PluginFeature>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PluginFeature pluginFeature = new PluginFeature(  );

            pluginFeature.setIdPlugin( daoUtil.getInt( 1 ) );
            pluginFeature.setIdPluginFeature( daoUtil.getInt( 2 ) );
            pluginFeature.setPluginFeatureRight( daoUtil.getString( 3 ) );
            pluginFeature.setPluginFeatureTitle( daoUtil.getString( 4 ) );
            pluginFeature.setPluginFeatureLevel( daoUtil.getString( 5 ) );
            pluginFeature.setPluginFeatureName( daoUtil.getString( 6 ) );
            pluginFeature.setPluginFeatureDescription( daoUtil.getString( 7 ) );

            pluginFeatureList.add( pluginFeature );
        }

        daoUtil.free(  );

        return pluginFeatureList;
    }

    /**
     * Load the list of features which are available for specific plugins
     * @return The Collection of the features for the plugin
     * @param nIdPlugin The id of the plugin
     * @param plugin The plugin
     */
    public ReferenceList selectFeatureByPluginCombo( int nIdPlugin, Plugin plugin )
    {
        ReferenceList featureList = new ReferenceList(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_PLUGIN_COMBO, plugin );
        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            featureList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return featureList;
    }

    /**
     * Deletes all the features attached to a plugin
     * @param nIdPlugin The identifier of the plugin
     * @param plugin The plugin
     */
    public void deleteAllPluginFeaturesByPluginId( int nIdPlugin, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_FEATURE_ID_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            delete( daoUtil.getInt( 1 ), plugin );
        }

        daoUtil.free(  );
    }
}
