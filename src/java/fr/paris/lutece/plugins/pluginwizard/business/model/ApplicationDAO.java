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
public final class ApplicationDAO implements IApplicationDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_plugin_application ) FROM pluginwizard_plugin_application";
    private static final String SQL_QUERY_SELECT = "SELECT id_plugin_application, application_name, application_class , id_plugin FROM pluginwizard_plugin_application WHERE id_plugin_application = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_application ( id_plugin_application, application_name, application_class, id_plugin ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_application WHERE id_plugin_application = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_application SET id_plugin_application =? ,  application_name = ?, application_class = ? WHERE id_plugin_application = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_plugin,id_plugin_application, application_name, application_class, id_plugin FROM pluginwizard_plugin_application";
    private static final String SQL_QUERY_SELECT_BY_PLUGIN = "SELECT id_plugin_application, application_name, application_class, id_plugin FROM pluginwizard_plugin_application WHERE id_plugin = ? ";
    private static final String SQL_QUERY_DELETE_BY_PLUGIN = "DELETE FROM pluginwizard_plugin_application WHERE id_plugin = ?";

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
    public void insert( Application pluginApplication, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        pluginApplication.setIdPluginApplication( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, pluginApplication.getIdPluginApplication(  ) );
        daoUtil.setString( 2, pluginApplication.getApplicationName(  ) );
        daoUtil.setString( 3, pluginApplication.getApplicationClass(  ) );
        daoUtil.setInt( 4, pluginApplication.getIdPlugin() );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the pluginApplication from the table
     * @param nId The identifier of the pluginApplication
     * @param plugin The plugin
     * @return the instance of the PluginApplication
     */
    public Application load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        Application pluginApplication = null;

        if ( daoUtil.next(  ) )
        {
            pluginApplication = new Application(  );

            pluginApplication.setIdPluginApplication( daoUtil.getInt( 1 ) );
            pluginApplication.setApplicationName( daoUtil.getString( 2 ) );
            pluginApplication.setApplicationClass( daoUtil.getString( 3 ) );
            pluginApplication.setIdPlugin( daoUtil.getInt( 4 ) );
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
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete applications of a given plugin
     * @param nIdPlugin The identifier of the plugin
     * @param plugin The plugin
     */
    public void deleteByPlugin( int nIdPlugin, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nIdPlugin );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param pluginApplication The reference of the pluginApplication
     * @param plugin The plugin
     */
    public void store( Application pluginApplication, Plugin plugin )
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
    public Collection<Application> selectPluginApplicationsList( Plugin plugin )
    {
        Collection<Application> pluginApplicationList = new ArrayList<Application>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Application pluginApplication = new Application(  );

            pluginApplication.setIdPlugin( daoUtil.getInt( 1 ) );
            pluginApplication.setIdPluginApplication( daoUtil.getInt( 2 ) );
            pluginApplication.setApplicationName( daoUtil.getString( 3 ) );
            pluginApplication.setApplicationClass( daoUtil.getString( 4 ) );
            pluginApplication.setIdPlugin( daoUtil.getInt( 5 ) );

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
    public Collection<Application> selectByPlugin( int nIdPlugin, Plugin plugin )
    {
        Collection<Application> pluginApplicationList = new ArrayList<Application>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Application pluginApplication = new Application(  );

            pluginApplication.setIdPluginApplication( daoUtil.getInt( 1 ) );
            pluginApplication.setApplicationName( daoUtil.getString( 2 ) );
            pluginApplication.setApplicationClass( daoUtil.getString( 3 ) );
            pluginApplication.setIdPlugin( daoUtil.getInt( 4 ) );

            pluginApplicationList.add( pluginApplication );
        }

        daoUtil.free(  );

        return pluginApplicationList;
    }
}