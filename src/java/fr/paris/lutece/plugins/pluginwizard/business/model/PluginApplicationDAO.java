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
 * This class provides Data Access methods for PluginApplication objects
 */
public final class PluginApplicationDAO implements IPluginApplicationDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_plugin_application ) FROM pluginwizard_plugin_application";
    private static final String SQL_QUERY_SELECT = "SELECT id_plugin_application, application_name, application_class FROM pluginwizard_plugin_application WHERE id_plugin_application = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_application ( id_plugin_application, application_name, application_class ) VALUES ( ?, ?,? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_application WHERE id_plugin_application = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_application SET id_plugin_application =? ,  application_name = ?, application_class = ? WHERE id_plugin_application = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_plugin,id_plugin_application, application_name, application_class FROM pluginwizard_plugin_application";
    private static final String SQL_QUERY_SELECT_BY_PLUGIN = "SELECT a.id_plugin_application, a.application_name, a.application_class FROM pluginwizard_plugin_application as a  , pluginwizard_plugin_id_application as b WHERE a.id_plugin_application = b.id_plugin_application AND b.id_plugin = ? ";
    private static final String SQL_QUERY_INSERT_PLUGIN_APPLICATION_DEPENDENCY = "INSERT INTO pluginwizard_plugin_id_application ( id_plugin, id_plugin_application) VALUES ( ?, ?)";
    private static final String SQL_QUERY_DELETE_PLUGIN_APPLICATION_DEPENDENCY = "DELETE FROM pluginwizard_plugin_id_application WHERE id_plugin_application = ?";

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
     * @param pluginApplication instance of the PluginApplication object to insert
     * @param plugin The plugin
     */
    public void insert( PluginApplication pluginApplication, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        pluginApplication.setIdPluginApplication( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, pluginApplication.getIdPluginApplication(  ) );
        daoUtil.setString( 2, pluginApplication.getApplicationName(  ) );
        daoUtil.setString( 3, pluginApplication.getApplicationClass(  ) );
        insertDependency( pluginApplication.getIdPlugin(  ), pluginApplication.getIdPluginApplication(  ), plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Adds the dependency
     * @param nIdPlugin The id of the plugin
     * @param nPluginApplication The id of the application
     * @param plugin The plugin
     */
    public void insertDependency( int nIdPlugin, int nPluginApplication, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PLUGIN_APPLICATION_DEPENDENCY, plugin );

        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.setInt( 2, nPluginApplication );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the pluginApplication from the table
     * @param nId The identifier of the pluginApplication
     * @param plugin The plugin
     * @return the instance of the PluginApplication
     */
    public PluginApplication load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        PluginApplication pluginApplication = null;

        if ( daoUtil.next(  ) )
        {
            pluginApplication = new PluginApplication(  );

            pluginApplication.setIdPluginApplication( daoUtil.getInt( 1 ) );
            pluginApplication.setApplicationName( daoUtil.getString( 2 ) );
            pluginApplication.setApplicationClass( daoUtil.getString( 3 ) );
        }

        daoUtil.free(  );

        return pluginApplication;
    }

    /**
     * Delete a record from the table
     * @param nPluginApplicationId The identifier of the pluginApplication
     * @param plugin The plugin
     */
    public void delete( int nPluginApplicationId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nPluginApplicationId );
        deleteDependency( nPluginApplicationId, plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete the dependency
     * @param nIdApplication The identifier of the application
     * @param plugin The plugin
     */
    public void deleteDependency( int nIdApplication, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PLUGIN_APPLICATION_DEPENDENCY, plugin );
        daoUtil.setInt( 1, nIdApplication );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param pluginApplication The reference of the pluginApplication
     * @param plugin The plugin
     */
    public void store( PluginApplication pluginApplication, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, pluginApplication.getIdPluginApplication(  ) );

        daoUtil.setString( 2, pluginApplication.getApplicationName(  ) );
        daoUtil.setString( 3, pluginApplication.getApplicationClass(  ) );
        daoUtil.setInt( 4, pluginApplication.getIdPluginApplication(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the pluginApplications and returns them as a collection
     * @param plugin The plugin
     * @return The Collection which contains the data of all the pluginApplications
     */
    public Collection<PluginApplication> selectPluginApplicationsList( Plugin plugin )
    {
        Collection<PluginApplication> pluginApplicationList = new ArrayList<PluginApplication>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PluginApplication pluginApplication = new PluginApplication(  );

            pluginApplication.setIdPlugin( daoUtil.getInt( 1 ) );
            pluginApplication.setIdPluginApplication( daoUtil.getInt( 2 ) );
            pluginApplication.setApplicationName( daoUtil.getString( 3 ) );
            pluginApplication.setApplicationClass( daoUtil.getString( 4 ) );

            pluginApplicationList.add( pluginApplication );
        }

        daoUtil.free(  );

        return pluginApplicationList;
    }

    /**
     * Returns a collection of plugin application
     * @param nIdPlugin The id of the generated plugin
     * @param plugin The plugin
     * @return A list of plugin applications
     */
    public Collection<PluginApplication> selectByPlugin( int nIdPlugin, Plugin plugin )
    {
        Collection<PluginApplication> pluginApplicationList = new ArrayList<PluginApplication>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PluginApplication pluginApplication = new PluginApplication(  );

            pluginApplication.setIdPluginApplication( daoUtil.getInt( 1 ) );
            pluginApplication.setApplicationName( daoUtil.getString( 2 ) );
            pluginApplication.setApplicationClass( daoUtil.getString( 3 ) );

            pluginApplicationList.add( pluginApplication );
        }

        daoUtil.free(  );

        return pluginApplicationList;
    }
}
