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
 * This class provides Data Access methods for PluginPortlet objects
 */
public final class PluginPortletDAO implements IPluginPortletDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_plugin_portlet ) FROM pluginwizard_plugin_portlet";
    private static final String SQL_QUERY_SELECT = "SELECT b.id_plugin, a.id_plugin_portlet, plugin_portlet_class, plugin_portlet_type_name, plugin_portlet_creation_url, plugin_portlet_update_url FROM pluginwizard_plugin_portlet as a, pluginwizard_plugin_id_portlet as b  WHERE a.id_plugin_portlet = ? AND a.id_plugin_portlet=b.id_plugin_portlet";
    private static final String SQL_QUERY_INSERT = "INSERT INTO pluginwizard_plugin_portlet ( id_plugin_portlet, plugin_portlet_class, plugin_portlet_type_name, plugin_portlet_creation_url, plugin_portlet_update_url ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM pluginwizard_plugin_portlet WHERE id_plugin_portlet = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE pluginwizard_plugin_portlet SET  id_plugin_portlet = ?, plugin_portlet_class = ?, plugin_portlet_type_name = ?, plugin_portlet_creation_url = ?, plugin_portlet_update_url = ? WHERE id_plugin_portlet = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_plugin,id_plugin_portlet ,plugin_portlet_class, plugin_portlet_type_name, plugin_portlet_creation_url, plugin_portlet_update_url FROM pluginwizard_plugin_portlet";
    private static final String SQL_QUERY_SELECT_BY_PLUGIN = "SELECT a.id_plugin_portlet, a.plugin_portlet_class, a.plugin_portlet_type_name, a.plugin_portlet_creation_url, a.plugin_portlet_update_url FROM pluginwizard_plugin_portlet as a , pluginwizard_plugin_id_portlet as b  WHERE a.id_plugin_portlet=b.id_plugin_portlet AND b.id_plugin= ?";
    private static final String SQL_QUERY_INSERT_PLUGIN_PORTLET_DEPENDENCY = "INSERT INTO pluginwizard_plugin_id_portlet ( id_plugin, id_plugin_portlet) VALUES ( ?, ?)";
    private static final String SQL_QUERY_DELETE_PLUGIN_PORTLET_DEPENDENCY = "DELETE FROM pluginwizard_plugin_id_portlet WHERE id_plugin_portlet = ?";

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
     * @param pluginPortlet instance of the PluginPortlet object to insert
     * @param plugin The plugin
     */
    public void insert( PluginPortlet pluginPortlet, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        pluginPortlet.setPluginPortletId( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, pluginPortlet.getPluginPortletId(  ) );
        daoUtil.setString( 2, pluginPortlet.getPluginPortletClass(  ) );
        daoUtil.setString( 3, pluginPortlet.getPluginPortletTypeName(  ) );
        daoUtil.setString( 4, pluginPortlet.getPluginPortletCreationUrl(  ) );
        daoUtil.setString( 5, pluginPortlet.getPluginPortletUpdateUrl(  ) );
        insertDependency( pluginPortlet.getIdPlugin(  ), pluginPortlet.getPluginPortletId(  ), plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Add the dependency
     * @param nIdPlugin The id of the plugin
     * @param nIdPortlet The id of the portlet
     * @param plugin The plugin
     */
    public void insertDependency( int nIdPlugin, int nIdPortlet, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PLUGIN_PORTLET_DEPENDENCY, plugin );

        daoUtil.setInt( 1, nIdPlugin );
        daoUtil.setInt( 2, nIdPortlet );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the pluginPortlet from the table
     * @param nId The identifier of the pluginPortlet
     * @param plugin The plugin
     * @return the instance of the PluginPortlet
     */
    public PluginPortlet load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        PluginPortlet pluginPortlet = null;

        if ( daoUtil.next(  ) )
        {
            pluginPortlet = new PluginPortlet(  );
            pluginPortlet.setIdPlugin( daoUtil.getInt( 1 ) );
            pluginPortlet.setPluginPortletId( daoUtil.getInt( 2 ) );
            pluginPortlet.setPluginPortletClass( daoUtil.getString( 3 ) );
            pluginPortlet.setPluginPortletTypeName( daoUtil.getString( 4 ) );
            pluginPortlet.setPluginPortletCreationUrl( daoUtil.getString( 5 ) );
            pluginPortlet.setPluginPortletUpdateUrl( daoUtil.getString( 6 ) );
        }

        daoUtil.free(  );

        return pluginPortlet;
    }

    /**
     * Delete a record from the table
     * @param nPluginPortletId The identifier of the pluginPortlet
     * @param plugin The plugin
     */
    public void delete( int nPluginPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nPluginPortletId );
        deleteDependency( nPluginPortletId, plugin );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete the dependency
     * @param nIdPortlet The identifier of the portlet
     * @param plugin The plugin
     */
    public void deleteDependency( int nIdPortlet, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PLUGIN_PORTLET_DEPENDENCY, plugin );
        daoUtil.setInt( 1, nIdPortlet );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param pluginPortlet The reference of the pluginPortlet
     * @param plugin The plugin
     */
    public void store( PluginPortlet pluginPortlet, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, pluginPortlet.getPluginPortletId(  ) );
        daoUtil.setString( 2, pluginPortlet.getPluginPortletClass(  ) );
        daoUtil.setString( 3, pluginPortlet.getPluginPortletTypeName(  ) );
        daoUtil.setString( 4, pluginPortlet.getPluginPortletCreationUrl(  ) );
        daoUtil.setString( 5, pluginPortlet.getPluginPortletUpdateUrl(  ) );
        daoUtil.setInt( 6, pluginPortlet.getPluginPortletId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the pluginPortlets and returns them as a collection
     * @param plugin The plugin
     * @return The Collection which contains the data of all the pluginPortlets
     */
    public Collection<PluginPortlet> selectPluginPortletsList( Plugin plugin )
    {
        Collection<PluginPortlet> pluginPortletList = new ArrayList<PluginPortlet>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PluginPortlet pluginPortlet = new PluginPortlet(  );

            pluginPortlet.setIdPlugin( daoUtil.getInt( 1 ) );
            pluginPortlet.setPluginPortletId( daoUtil.getInt( 2 ) );
            pluginPortlet.setPluginPortletClass( daoUtil.getString( 3 ) );
            pluginPortlet.setPluginPortletTypeName( daoUtil.getString( 4 ) );
            pluginPortlet.setPluginPortletCreationUrl( daoUtil.getString( 5 ) );
            pluginPortlet.setPluginPortletUpdateUrl( daoUtil.getString( 6 ) );

            pluginPortletList.add( pluginPortlet );
        }

        daoUtil.free(  );

        return pluginPortletList;
    }

    /**
     * Returns a collection of portlets associated to the generated plugin
     * @param nPluginId The identifier of the generated plugin
     * @param plugin The Plugin
     * @return A collection of plugin portlets
     */
    public Collection<PluginPortlet> selectPluginPortletsList( int nPluginId, Plugin plugin )
    {
        Collection<PluginPortlet> pluginPortletList = new ArrayList<PluginPortlet>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_PLUGIN, plugin );
        daoUtil.setInt( 1, nPluginId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PluginPortlet pluginPortlet = new PluginPortlet(  );

            pluginPortlet.setIdPlugin( nPluginId );
            pluginPortlet.setPluginPortletId( daoUtil.getInt( 1 ) );
            pluginPortlet.setPluginPortletClass( daoUtil.getString( 2 ) );
            pluginPortlet.setPluginPortletTypeName( daoUtil.getString( 3 ) );
            pluginPortlet.setPluginPortletCreationUrl( daoUtil.getString( 4 ) );
            pluginPortlet.setPluginPortletUpdateUrl( daoUtil.getString( 5 ) );

            pluginPortletList.add( pluginPortlet );
        }

        daoUtil.free(  );

        return pluginPortletList;
    }
}
