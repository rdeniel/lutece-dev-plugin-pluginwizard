/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import java.util.Collection;


/**
* IBusinessClassDAO Interface
*/
public interface IBusinessClassDAO
{
    /**
     *
     * @param nBusinessClassId The identifier of the business class
     * @param plugin The plugin
     * @return a boolean value, true if class contains attributes
     */
    boolean containsAttributes( int nBusinessClassId, Plugin plugin );

    /**
     *  Deletes all the classes attached to aplugin
     * @param nIdPlugin The id of the plugin
     * @param plugin The plugin
     */
    void deleteAllClassesByPluginId( int nIdPlugin, Plugin plugin );

    /**
     * Checks whether class has a description
     * @param nBusinessId The identifier of the business class
     * @param plugin The plugin
     * @return a boolean value, true if class already has a description
     */
    boolean hasAlreadyDescription( int nBusinessId, Plugin plugin );

    /**
     * Checks whether class has already a key
     * @param nBusinessId The identifier of the business class
     * @param plugin The plugin
     * @return a boolean value, true if class already contains a key
     */
    boolean hasAlreadyKey( int nBusinessId, Plugin plugin );

    /**
     * Insert a new record in the table.
     * @param businessClass instance of the BusinessClass object to insert
     * @param plugin the Plugin
     */
    void insert( BusinessClass businessClass, Plugin plugin );

    /**
    * Update the record in the table
    * @param businessClass the reference of the BusinessClass
    * @param plugin the Plugin
    */
    void store( BusinessClass businessClass, Plugin plugin );

    /**
     * Delete a record from the table
     * @param nBusinessClassId int identifier of the BusinessClass to delete
     * @param plugin the Plugin
     */
    void delete( int nBusinessClassId, Plugin plugin );

    /**
     * Delete business classes by admin feature
     * @param nFeatureId identifier of the Admin feature
     * @param plugin the Plugin
     */
    void deleteByFeature( int nFeatureId, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * @param plugin the Plugin
     * @param nKey The id of the business class
     * @return The instance of the businessClass
     */
    BusinessClass load( int nKey, Plugin plugin );

    /**
     * Returns a collection of business classes for the plugin
     * @param nPluginId The id of the plugin
     * @param plugin The Plugin
     * @return A collection of business classes
     */
    Collection<BusinessClass> selectBusinessClassesList( int nPluginId, Plugin plugin );

    /**
     * Returns the business classes chosen by feature
     * @param nFeatureId The id of the feature
     * @param nPluginId The id of the plugin
     * @param plugin The Plugin
     * @return A collection of the business classes
     */
    Collection<BusinessClass> selectBusinessClassesByFeature( int nFeatureId, int nPluginId, Plugin plugin );

    /**
     * Verifies if key exists for business class
     * @param nFeatureId The feature of the id
     * @param strBusinessClass The business class
     * @param plugin The plugin
     * @return A boolean value which is to true if the key exists
     */
    boolean keyExists( int nFeatureId, String strBusinessClass, Plugin plugin );
}
