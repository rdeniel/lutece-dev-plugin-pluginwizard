/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import java.util.ArrayList;
import java.util.Collection;


/**
* IResourceKeyDAO Interface
*/
public interface IResourceKeyDAO
{
    /**
     * Insert a new record in the table.
     * @param resourceKey instance of the ResourceKey object to inssert
     * @param plugin the Plugin
     */
    void insert( ResourceKey resourceKey, Plugin plugin );

    /**
    * Update the record in the table
    * @param resourceKey the reference of the ResourceKey
    * @param plugin the Plugin
    */
    void store( ResourceKey resourceKey, Plugin plugin );

    /**
     * Delete a record from the table
     * @param nIdResourceKey int identifier of the ResourceKey to delete
     * @param plugin the Plugin
     */
    void delete( int nIdResourceKey, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * @param nKey The identifier of the ressource key
     * @param plugin the Plugin
     * @return The instance of the resourceKey
     */
    ResourceKey load( int nKey, Plugin plugin );

    /**
    * Load the data of all the resourceKey objects and returns them as a collection
    * @param plugin the Plugin
    * @return The collection which contains the data of all the resourceKey objects
    */
    Collection<ResourceKey> selectResourceKeysList( Plugin plugin );

    /**
     * Select a list of resource keys
     * @param nPluginId The id of the plugin
     * @param plugin The Plugin
     * @return A collection of all the resource keys related to a generated plugin
     */
    Collection<ResourceKey> selectResourceKeysList( int nPluginId, Plugin plugin );

    /**
     * Returns true if resource key is valid
     * @param nPluginId The id of the generated plugin
     * @param plugin The Plugin
     * @return A boolean value indicating the validity of the resource key
     */
    boolean isValid( int nPluginId, Plugin plugin );

    /**
    * Persists the i18n keys
    * @param nPluginId The plugin id
    * @param listKeys The list of i18n keys
    * @param plugin The plugin
    */
    void addEmptyKeys( int nPluginId, ArrayList<String> listKeys, Plugin plugin );

    /**
     * Verifies if the keys have been generated for the plugin
     * @param nPluginId The id of the generated plugin
     * @param plugin The plugin
     * @return A boolean value
     */
    boolean isEmpty( int nPluginId, Plugin plugin );

    /**
     * Deletes all the resources from a Plugin
     * @param nPluginId The generated plugin id
     * @param plugin The plugin
     */
    void deleteByPlugin( int nPluginId, Plugin plugin );
}
