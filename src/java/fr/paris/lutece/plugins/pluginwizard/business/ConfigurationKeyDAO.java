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
package fr.paris.lutece.plugins.pluginwizard.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides Data Access methods for ConfigurationKey objects
 */
public final class ConfigurationKeyDAO implements IConfigurationKeyDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_key ) FROM pluginwizard_configuration_key";
    private static final String SQL_QUERY_SELECT = "SELECT id_key, key_description, key_value FROM pluginwizard_configuration_key WHERE id_key = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_configuration_key ( id_key, key_description, key_value ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_configuration_key WHERE id_key = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_configuration_key SET id_key = ?, key_description = ?, key_value = ? WHERE id_key = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_key, key_description, key_value FROM pluginwizard_configuration_key";

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
     * @param configurationKey
     *            instance of the ConfigurationKey object to insert
     * @param plugin
     *            The plugin
     */
    public void insert( ConfigurationKey configurationKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin ) )
        {

            configurationKey.setIdKey( newPrimaryKey( plugin ) );

            daoUtil.setInt( 1, configurationKey.getIdKey( ) );
            daoUtil.setString( 2, configurationKey.getKeyDescription( ) );
            daoUtil.setString( 3, configurationKey.getKeyValue( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Load the data of the configurationKey from the table
     * 
     * @param nId
     *            The identifier of the configurationKey
     * @param plugin
     *            The plugin
     * @return the instance of the ConfigurationKey
     */
    public ConfigurationKey load( int nId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nId );
            daoUtil.executeQuery( );

            ConfigurationKey configurationKey = null;

            if ( daoUtil.next( ) )
            {
                configurationKey = new ConfigurationKey( );

                configurationKey.setIdKey( daoUtil.getInt( 1 ) );
                configurationKey.setKeyDescription( daoUtil.getString( 2 ) );
                configurationKey.setKeyValue( daoUtil.getString( 3 ) );
            }

            daoUtil.free( );

            return configurationKey;
        }
    }

    /**
     * Delete a record from the table
     * 
     * @param nConfigurationKeyId
     *            The identifier of the configurationKey
     * @param plugin
     *            The plugin
     */
    public void delete( int nConfigurationKeyId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nConfigurationKeyId );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Update the record in the table
     * 
     * @param configurationKey
     *            The reference of the configurationKey
     * @param plugin
     *            The plugin
     */
    public void store( ConfigurationKey configurationKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {

            daoUtil.setInt( 1, configurationKey.getIdKey( ) );
            daoUtil.setString( 2, configurationKey.getKeyDescription( ) );
            daoUtil.setString( 3, configurationKey.getKeyValue( ) );
            daoUtil.setInt( 4, configurationKey.getIdKey( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Load the data of all the configurationKeys and returns them as a collection
     * 
     * @param plugin
     *            The plugin
     * @return The Collection which contains the data of all the configurationKeys
     */
    public Collection<ConfigurationKey> selectConfigurationKeysList( Plugin plugin )
    {
        Collection<ConfigurationKey> configurationKeyList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                ConfigurationKey configurationKey = new ConfigurationKey( );

                configurationKey.setIdKey( daoUtil.getInt( 1 ) );
                configurationKey.setKeyDescription( daoUtil.getString( 2 ) );
                configurationKey.setKeyValue( daoUtil.getString( 3 ) );

                configurationKeyList.add( configurationKey );
            }

            daoUtil.free( );

            return configurationKeyList;
        }
    }
}
