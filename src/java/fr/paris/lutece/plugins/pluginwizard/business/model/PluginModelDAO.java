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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides Data Access methods for PluginModel objects
 */
public final class PluginModelDAO implements IPluginModelDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_plugin ) FROM pluginwizard_plugin_id";
    private static final String SQL_QUERY_SELECT = "SELECT id_plugin, plugin_name, plugin_class, plugin_description, plugin_documentation, plugin_installation, plugin_changes, plugin_user_guide, plugin_version, plugin_copyright, plugin_icon_url, plugin_provider, plugin_provider_url, plugin_db_pool_required FROM pluginwizard_plugin_id WHERE id_plugin = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_id ( id_plugin, plugin_name, plugin_class, plugin_description, plugin_documentation, plugin_installation, plugin_changes, plugin_user_guide, plugin_version, plugin_copyright, plugin_icon_url, plugin_provider, plugin_provider_url, plugin_db_pool_required ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_id WHERE id_plugin = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_id SET id_plugin = ?, plugin_name = ?, plugin_class = ?, plugin_description = ?, plugin_documentation = ?, plugin_installation = ?, plugin_changes = ?, plugin_user_guide = ?, plugin_version = ?, plugin_copyright = ?, plugin_icon_url = ?, plugin_provider = ?, plugin_provider_url = ?, plugin_db_pool_required = ? WHERE id_plugin = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_plugin, plugin_name, plugin_class, plugin_description, plugin_documentation, plugin_installation, plugin_changes, plugin_user_guide, plugin_version, plugin_copyright, plugin_icon_url, plugin_provider, plugin_provider_url, plugin_db_pool_required FROM pluginwizard_plugin_id";
    private static final String SQL_QUERY_FIND_BY_NAME = "SELECT id_plugin FROM pluginwizard_plugin_id WHERE plugin_name= ?";
    private static final String SQL_QUERY_COUNT_FIND_BY_NAME = "SELECT COUNT(id_plugin) FROM pluginwizard_plugin_id WHERE plugin_name= ?";

    /**
     * Generates a new primary key
     * 
     * @param plugin
     *            The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin ) )
        {
            daoUtil.executeQuery( );

            int nKey;

            if ( !daoUtil.next( ) )
            {
                // if the table is empty
                nKey = 1;
            }

            nKey = daoUtil.getInt( 1 ) + 1;
            daoUtil.free( );

            return nKey;
        }
    }

    /**
     * Insert a new record in the table.
     * 
     * @param pluginModel
     *            instance of the PluginModel object to insert
     * @param plugin
     *            The plugin
     */
    public void insert( PluginModel pluginModel, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin ) )
        {

            pluginModel.setIdPlugin( newPrimaryKey( plugin ) );

            daoUtil.setInt( 1, pluginModel.getIdPlugin( ) );
            daoUtil.setString( 2, pluginModel.getPluginName( ) );
            daoUtil.setString( 3, pluginModel.getPluginClass( ) );
            daoUtil.setString( 4, pluginModel.getPluginDescription( ) );
            daoUtil.setString( 5, pluginModel.getPluginDocumentation( ) );
            daoUtil.setString( 6, pluginModel.getPluginInstallation( ) );
            daoUtil.setString( 7, pluginModel.getPluginChanges( ) );
            daoUtil.setString( 8, pluginModel.getPluginUserGuide( ) );
            daoUtil.setString( 9, pluginModel.getPluginVersion( ) );
            daoUtil.setString( 10, pluginModel.getPluginCopyright( ) );
            daoUtil.setString( 11, pluginModel.getPluginIconUrl( ) );
            daoUtil.setString( 12, pluginModel.getPluginProvider( ) );
            daoUtil.setString( 13, pluginModel.getPluginProviderUrl( ) );
            daoUtil.setString( 14, pluginModel.getPluginDbPoolRequired( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Load the data of the pluginModel from the table
     * 
     * @param nId
     *            The identifier of the pluginModel
     * @param plugin
     *            The plugin
     * @return the instance of the PluginModel
     */
    public PluginModel load( int nId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nId );
            daoUtil.executeQuery( );

            PluginModel pluginModel = null;

            if ( daoUtil.next( ) )
            {
                pluginModel = new PluginModel( );

                pluginModel.setIdPlugin( daoUtil.getInt( 1 ) );
                pluginModel.setPluginName( daoUtil.getString( 2 ) );
                pluginModel.setPluginClass( daoUtil.getString( 3 ) );
                pluginModel.setPluginDescription( daoUtil.getString( 4 ) );
                pluginModel.setPluginDocumentation( daoUtil.getString( 5 ) );
                pluginModel.setPluginInstallation( daoUtil.getString( 6 ) );
                pluginModel.setPluginChanges( daoUtil.getString( 7 ) );
                pluginModel.setPluginUserGuide( daoUtil.getString( 8 ) );
                pluginModel.setPluginVersion( daoUtil.getString( 9 ) );
                pluginModel.setPluginCopyright( daoUtil.getString( 10 ) );
                pluginModel.setPluginIconUrl( daoUtil.getString( 11 ) );
                pluginModel.setPluginProvider( daoUtil.getString( 12 ) );
                pluginModel.setPluginProviderUrl( daoUtil.getString( 13 ) );
                pluginModel.setPluginDbPoolRequired( daoUtil.getString( 14 ) );

                // //TODO Portlets pluginModel.setPluginPortlets( PluginPortletHome.findByPlugin( nId, plugin ) );
            }

            daoUtil.free( );

            return pluginModel;
        }
    }

    /**
     * Delete a record from the table
     * 
     * @param nPluginModelId
     *            The identifier of the pluginModel
     * @param plugin
     *            The plugin
     */
    public void delete( int nPluginModelId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nPluginModelId );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Update the record in the table
     * 
     * @param pluginModel
     *            The reference of the pluginModel
     * @param plugin
     *            The plugin
     */
    public void store( PluginModel pluginModel, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {

            daoUtil.setInt( 1, pluginModel.getIdPlugin( ) );
            daoUtil.setString( 2, pluginModel.getPluginName( ) );
            daoUtil.setString( 3, pluginModel.getPluginClass( ) );
            daoUtil.setString( 4, pluginModel.getPluginDescription( ) );
            daoUtil.setString( 5, pluginModel.getPluginDocumentation( ) );
            daoUtil.setString( 6, pluginModel.getPluginInstallation( ) );
            daoUtil.setString( 7, pluginModel.getPluginChanges( ) );
            daoUtil.setString( 8, pluginModel.getPluginUserGuide( ) );
            daoUtil.setString( 9, pluginModel.getPluginVersion( ) );
            daoUtil.setString( 10, pluginModel.getPluginCopyright( ) );
            daoUtil.setString( 11, pluginModel.getPluginIconUrl( ) );
            daoUtil.setString( 12, pluginModel.getPluginProvider( ) );
            daoUtil.setString( 13, pluginModel.getPluginProviderUrl( ) );
            daoUtil.setString( 14, pluginModel.getPluginDbPoolRequired( ) );
            daoUtil.setInt( 15, pluginModel.getIdPlugin( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Load the data of all the pluginModels and returns them as a collection
     * 
     * @param plugin
     *            The plugin
     * @return The Collection which contains the data of all the pluginModels
     */
    public Collection<PluginModel> selectPluginModelsList( Plugin plugin )
    {
        Collection<PluginModel> pluginModelList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                PluginModel pluginModel = new PluginModel( );

                pluginModel.setIdPlugin( daoUtil.getInt( 1 ) );
                pluginModel.setPluginName( daoUtil.getString( 2 ) );
                pluginModel.setPluginClass( daoUtil.getString( 3 ) );
                pluginModel.setPluginDescription( daoUtil.getString( 4 ) );
                pluginModel.setPluginDocumentation( daoUtil.getString( 5 ) );
                pluginModel.setPluginInstallation( daoUtil.getString( 6 ) );
                pluginModel.setPluginChanges( daoUtil.getString( 7 ) );
                pluginModel.setPluginUserGuide( daoUtil.getString( 8 ) );
                pluginModel.setPluginVersion( daoUtil.getString( 9 ) );
                pluginModel.setPluginCopyright( daoUtil.getString( 10 ) );
                pluginModel.setPluginIconUrl( daoUtil.getString( 11 ) );
                pluginModel.setPluginProvider( daoUtil.getString( 12 ) );
                pluginModel.setPluginProviderUrl( daoUtil.getString( 13 ) );
                pluginModel.setPluginDbPoolRequired( daoUtil.getString( 14 ) );

                pluginModelList.add( pluginModel );
            }

            daoUtil.free( );

            return pluginModelList;
        }
    }

    /**
     * Returns the identifier of the generated plugin
     * 
     * @param plugin
     *            The Plugin
     * @param strPluginName
     *            The name of the generated plugin
     * @return the identifier of the generated plugin
     */
    public int selectPluginModelByName( Plugin plugin, String strPluginName )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_NAME, plugin ) )
        {
            daoUtil.setString( 1, strPluginName );
            daoUtil.executeQuery( );

            int nPluginId = 0;

            if ( daoUtil.next( ) )
            {
                nPluginId = daoUtil.getInt( 1 );
            }

            daoUtil.free( );

            return nPluginId;
        }
    }

    /**
     * Verifies whether the plugin exists
     * 
     * @param strPluginName
     *            The name given to the plugin
     * @param plugin
     *            The plugin
     * @return A boolean value telling whether a plugin with this name exists
     */
    public boolean pluginExists( String strPluginName, Plugin plugin )
    {
        boolean bValue = false;
        int nCount = 0;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_COUNT_FIND_BY_NAME, plugin ) )
        {
            daoUtil.setString( 1, strPluginName );
            daoUtil.executeQuery( );

            if ( daoUtil.next( ) )
            {
                nCount = daoUtil.getInt( 1 );
            }

            daoUtil.free( );

            if ( nCount > 0 )
            {
                bValue = true;
            }

            return bValue;
        }
    }
}
