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
import fr.paris.lutece.util.ReferenceList;



/**
 * This class provides instances management methods (create, find, ...) for Attribute objects
 */
public final class AttributeHome
{
    // Static variable pointed at the DAO instance
    private static IAttributeDAO _dao = (IAttributeDAO) SpringContextService.getBean( "pluginwizard.attributeDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private AttributeHome(  )
    {
    }

    /**
     * Finds the class description
     * @param nBusinessId The identifier of the business class
     * @param plugin The plugin
     * @return The class description
     */
    public static String findClassDescription( int nBusinessId, Plugin plugin )
    {
        return _dao.selectClassDescription( nBusinessId, plugin );
    }

    /**
     * Fetches the key of the business class
     * @param nBusinessId The business class identifier
     * @param plugin The plugin
     * @return The primary key of the business class
     */
    public static String findPrimaryKey( int nBusinessId, Plugin plugin )
    {
        return _dao.selectPrimaryKey( nBusinessId, plugin );
    }

    /**
     * Create an instance of the attribute class
     * @param attribute The instance of the Attribute which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of attribute which has been created with its primary key.
     */
    public static Attribute create( Attribute attribute, Plugin plugin )
    {
        _dao.insert( attribute, plugin );

        return attribute;
    }

    /**
     * Update of the attribute which is specified in parameter
     * @param attribute The instance of the Attribute which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  attribute which has been updated
     */
    public static Attribute update( Attribute attribute, Plugin plugin )
    {
        _dao.store( attribute, plugin );

        return attribute;
    }

    /**
     * Remove the attribute whose identifier is specified in parameter
     * @param nAttributeId The attribute Id
     * @param plugin the Plugin
     */
    public static void remove( int nAttributeId, Plugin plugin )
    {
        _dao.delete( nAttributeId, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a attribute whose identifier is specified in parameter
     * @param nKey The attribute primary key
     * @param plugin the Plugin
     * @return an instance of Attribute
     */
    public static Attribute findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
    * Returns an instance of a attribute whose identifier is specified in parameter
    * @param nAttributeId The attribute primary key
    * @param plugin the Plugin
    * @return A representation of the type
    */
    public static String getAttributeTypeName( int nAttributeId, Plugin plugin )
    {
        return _dao.getTypeName( nAttributeId, plugin );
    }

    /**
    *  Returns a reference list of attribute list
    * @param plugin The plugin
    * @return The reference list
    */
    public static ReferenceList getAttributeListCombo( Plugin plugin )
    {
        return _dao.selectAttributeListCombo( plugin );
    }
}
