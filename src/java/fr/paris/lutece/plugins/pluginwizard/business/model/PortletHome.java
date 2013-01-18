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
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for PluginPortlet objects
 */
public final class PortletHome
{
    // Static variable pointed at the DAO instance
    private static IPortletDAO _dao = (IPortletDAO) SpringContextService.getPluginBean( "pluginwizard",
            "pluginwizard.pluginPortletDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private PortletHome(  )
    {
    }

    /**
     * Removes all the portlets related to the plugin
     * @param nIdPlugin The id of the generated plugin
     * @param plugin The plugin
     */
    public static void removePortletsByPlugin( int nIdPlugin, Plugin plugin )
    {
        _dao.deleteByPlugin( nIdPlugin, plugin );
    }

    /**
     * Create an instance of the pluginPortlet class
     * @param pluginPortlet The instance of the PluginPortlet which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of pluginPortlet which has been created with its primary key.
     */
    public static Portlet create( Portlet pluginPortlet, Plugin plugin )
    {
        _dao.insert( pluginPortlet, plugin );

        return pluginPortlet;
    }

    /**
     * Update of the pluginPortlet which is specified in parameter
     * @param pluginPortlet The instance of the PluginPortlet which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  pluginPortlet which has been updated
     */
    public static Portlet update( Portlet pluginPortlet, Plugin plugin )
    {
        _dao.store( pluginPortlet, plugin );

        return pluginPortlet;
    }

    /**
     * Remove the pluginPortlet whose identifier is specified in parameter
     * @param nPluginPortletId The pluginPortlet Id
     * @param plugin the Plugin
     */
    public static void remove( int nPluginPortletId, Plugin plugin )
    {
        _dao.delete( nPluginPortletId, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a pluginPortlet whose identifier is specified in parameter
     * @param nKey The pluginPortlet primary key
     * @param plugin the Plugin
     * @return an instance of PluginPortlet
     */
    public static Portlet findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Returns an instance of a pluginPortlet whose identifier is specified in parameter
     * @param nKey The pluginPortlet primary key
     * @param plugin the Plugin
     * @return an instance of PluginPortlet
     */
    public static Collection<Portlet> findByPlugin( int nKey, Plugin plugin )
    {
        return _dao.selectPluginPortletsList( nKey, plugin );
    }

    /**
     * Load the data of all the pluginPortlet objects and returns them in form of a collection
     * @param plugin the Plugin
     * @return the collection which contains the data of all the pluginPortlet objects
     */
    public static Collection<Portlet> getPluginPortletsList( Plugin plugin )
    {
        return _dao.selectPluginPortletsList( plugin );
    }
}
