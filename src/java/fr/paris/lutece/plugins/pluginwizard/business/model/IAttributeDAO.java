/*
 * Copyright (c) 2002-2009, Mairie de Paris
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
import fr.paris.lutece.util.ReferenceList;

import java.util.List;


/**
* IAttributeDAO Interface
*/
public interface IAttributeDAO
{
    /**
     * Deletes all the attributes related to a plugin
     * @param nIdPlugin The id of the plugin
     * @param plugin The plugin
     */
    void deleteAllAttributesByPluginId( int nIdPlugin, Plugin plugin );

    /**
     * Fetches the type name
    * @param nIdAttribute int identifier of the Attribute
    * @param plugin  the Plugin
    * @return The type name
    */
    String getTypeName( int nIdAttribute, Plugin plugin );

    /**
     * Selects the class description of the business class
     * @param nPluginId The id of the plugin
     * @param plugin The plugin
     * @return The class description
     */
    String selectClassDescription( int nPluginId, Plugin plugin );

    /**
     * Selects the primary key
     * @param nPluginId The id of the plugin
     * @param plugin The plugin
     * @return The primary key
     */
    String selectPrimaryKey( int nPluginId, Plugin plugin );

    /**
     * Stores a business class
     * @param attribute The attribute object
     * @param plugin The plugin
     */
    void store( Attribute attribute, Plugin plugin );

    /**
     * Insert a new record in the table.
     * @param attribute instance of the Attribute object to insert
     * @param nBusinessClassId The business class identifier
     * @param plugin the Plugin
     */
    void insert( int nBusinessClassId, Attribute attribute, Plugin plugin );

    /**
     * Delete a record from the table
     * @param nIdAttribute int identifier of the Attribute to delete
     * @param plugin the Plugin
     */
    void delete( int nIdAttribute, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * @param nKey The id of the attribute
     * @param plugin the Plugin
     * @return The instance of the attribute
     */
    Attribute load( int nKey, Plugin plugin );

    /**
    * Load the data of all the attribute objects and returns them as a List
    * @param plugin the Plugin
    * @return The List which contains the data of all the attribute objects
    */
    List<Attribute> selectAttributesList( Plugin plugin );

    /**
    * Load the data of all the attributes type
    * @param plugin the Plugin
    * @return The List which contains the attribute type list
    */
    ReferenceList selectAttributeListCombo( Plugin plugin );
}
