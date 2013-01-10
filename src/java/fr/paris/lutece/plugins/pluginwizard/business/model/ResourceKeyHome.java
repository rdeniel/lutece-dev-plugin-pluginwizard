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

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for ResourceKey objects
 */
public final class ResourceKeyHome
{
    // Static variable pointed at the DAO instance
    private static IResourceKeyDAO _dao = (IResourceKeyDAO) SpringContextService.getPluginBean( "pluginwizard",
            "pluginwizard.resourceKeyDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ResourceKeyHome(  )
    {
    }

    /**
     * Create an instance of the resourceKey class
     * @param resourceKey The instance of the ResourceKey which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of resourceKey which has been created with its primary key.
     */
    public static ResourceKey create( ResourceKey resourceKey, Plugin plugin )
    {
        _dao.insert( resourceKey, plugin );

        return resourceKey;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data of all the resourceKey objects and returns them in form of a collection
     * @param nPluginId The identifier of the generated plugin
     * @param plugin the Plugin
     * @return the collection which contains the data of all the resourceKey objects
     */
    public static Collection<ResourceKey> getResourceKeysList( int nPluginId, Plugin plugin )
    {
        return _dao.selectResourceKeysList( nPluginId, plugin );
    }

    /**
     * Deletes all the resources for a generated plugin
     * @param nPluginId The Plugin Id
     * @param plugin The Plugin
     */
    public static void deleteKeysByPlugin( int nPluginId, Plugin plugin )
    {
        _dao.deleteByPlugin( nPluginId, plugin );
    }
}
