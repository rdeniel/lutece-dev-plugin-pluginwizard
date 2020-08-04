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
import java.util.List;

/**
 * This class provides Data Access methods for Model objects
 */
public final class ModelDAO implements IModelDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_plugin ) FROM pluginwizard_plugin_model";
    private static final String SQL_QUERY_SELECT = "SELECT id_plugin, name, model_json FROM pluginwizard_plugin_model WHERE id_plugin = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_model ( id_plugin, name, model_json ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_model WHERE id_plugin = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_model SET id_plugin = ?, name = ?, model_json = ? WHERE id_plugin = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_plugin, name, model_json FROM pluginwizard_plugin_model";

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

            daoUtil.next( );
            nKey = daoUtil.getInt( 1 ) + 1;
            daoUtil.free( );

            return nKey;
        }
    }

    /**
     * Insert a new record in the table.
     *
     * @param model
     *            instance of the Model object to insert
     * @param plugin
     *            The plugin
     */
    @Override
    public void insert( Model model, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin ) )
        {

            model.setIdPlugin( newPrimaryKey( plugin ) );

            daoUtil.setInt( 1, model.getIdPlugin( ) );
            daoUtil.setString( 2, model.getName( ) );
            daoUtil.setString( 3, model.getModelJson( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Load the data of the model from the table
     *
     * @param nId
     *            The identifier of the model
     * @param plugin
     *            The plugin
     * @return the instance of the Model
     */
    @Override
    public Model load( int nId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nId );
            daoUtil.executeQuery( );

            Model model = null;

            if ( daoUtil.next( ) )
            {
                model = new Model( );

                model.setIdPlugin( daoUtil.getInt( 1 ) );
                model.setName( daoUtil.getString( 2 ) );
                model.setModelJson( daoUtil.getString( 3 ) );
            }

            daoUtil.free( );

            return model;
        }
    }

    /**
     * Delete a record from the table
     *
     * @param nModelId
     *            The identifier of the model
     * @param plugin
     *            The plugin
     */
    @Override
    public void delete( int nModelId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nModelId );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Update the record in the table
     *
     * @param model
     *            The reference of the model
     * @param plugin
     *            The plugin
     */
    @Override
    public void store( Model model, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {

            daoUtil.setInt( 1, model.getIdPlugin( ) );
            daoUtil.setString( 2, model.getName( ) );
            daoUtil.setString( 3, model.getModelJson( ) );
            daoUtil.setInt( 4, model.getIdPlugin( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * Load the data of all the models and returns them as a List
     *
     * @param plugin
     *            The plugin
     * @return The List which contains the data of all the models
     */
    @Override
    public List<Model> selectModelsList( Plugin plugin )
    {
        List<Model> modelList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                Model model = new Model( );

                model.setIdPlugin( daoUtil.getInt( 1 ) );
                model.setName( daoUtil.getString( 2 ) );
                model.setModelJson( daoUtil.getString( 3 ) );

                modelList.add( model );
            }

            daoUtil.free( );

            return modelList;
        }
    }
}
