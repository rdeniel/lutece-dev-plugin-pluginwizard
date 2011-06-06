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
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for BusinessClass objects
 */
public final class BusinessClassHome
{
    // Static variable pointed at the DAO instance
    private static IBusinessClassDAO _dao = (IBusinessClassDAO) SpringContextService.getPluginBean( "pluginwizard",
            "pluginwizard.businessClassDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private BusinessClassHome(  )
    {
    }

    /**
     * Verifies if the class has a description
     * @param nBusinessId The id of the business class
     * @param plugin The plugin
     * @return A boolean value which is true if descritpion exists
     */
    public static boolean hasAlreadyDescription( int nBusinessId, Plugin plugin )
    {
        return _dao.hasAlreadyDescription( nBusinessId, plugin );
    }

    /**
     * Verifies if business class has already a key
     * @param nBusinessId The id of the business class
     * @param plugin the plugin
     * @return A boolean value which is true when keys exists for the class
     */
    public static boolean hasAlreadyKey( int nBusinessId, Plugin plugin )
    {
        return _dao.hasAlreadyKey( nBusinessId, plugin );
    }

    /**
     * Create an instance of the businessClass class
     * @param businessClass The instance of the BusinessClass which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of businessClass which has been created with its primary key.
     */
    public static BusinessClass create( BusinessClass businessClass, Plugin plugin )
    {
        _dao.insert( businessClass, plugin );

        return businessClass;
    }

    /**
     * Verifies whether attributes are attached to the business class
     * @param nBusinessClassId The business class id
     * @param plugin The plugin
     * @return boolean value :true if at least one attribute is present
     */
    public static boolean hasAttributes( int nBusinessClassId, Plugin plugin )
    {
        return _dao.containsAttributes( nBusinessClassId, plugin );
    }

    /**
     * Update of the businessClass which is specified in parameter
     * @param businessClass The instance of the BusinessClass which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  businessClass which has been updated
     */
    public static BusinessClass update( BusinessClass businessClass, Plugin plugin )
    {
        _dao.store( businessClass, plugin );

        return businessClass;
    }

    /**
     * Remove the businessClass whose identifier is specified in parameter
     * @param nBusinessClassId The businessClass Id
     * @param plugin the Plugin
     */
    public static void remove( int nBusinessClassId, Plugin plugin )
    {
        _dao.delete( nBusinessClassId, plugin );
    }

    /**
     * Removes all the business classes attached to a plugin
     * @param nIdPlugin the id of the plugin to be generated
     * @param plugin The plugin
     */
    public static void removeBusinessClassesByPlugin( int nIdPlugin, Plugin plugin )
    {
        _dao.deleteAllClassesByPluginId( nIdPlugin, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a businessClass whose identifier is specified in parameter
     * @param nKey The businessClass primary key
     * @param plugin the Plugin
     * @return an instance of BusinessClass
     */
    public static BusinessClass findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Load the data of all the businessClass objects and returns them in form of a collection
     * @param plugin the Plugin
     * @param nIdPlugin The id of the generated plugin
     * @return the collection which contains the data of all the businessClass objects
     */
    public static Collection<BusinessClass> getBusinessClassesByPlugin( int nIdPlugin, Plugin plugin )
    {
        return _dao.selectBusinessClassesList( nIdPlugin, plugin );
    }

    /**
     * Returns a collection of business classes selected by feature
     * @param nFeatureId The id of the feature
     * @param nPluginId the id of the plugin
     * @param plugin The Plugin
     * @return A collection of business classes selected by feature
     */
    public static Collection<BusinessClass> getBusinessClassesByFeature( int nFeatureId, int nPluginId, Plugin plugin )
    {
        return _dao.selectBusinessClassesByFeature( nFeatureId, nPluginId, plugin );
    }

    /**
     *
     * @param nFeatureId The feature id
     * @param strBusinessClassName The business class name
     * @param plugin The plugin
     * @return A boolean value pointing if a key exists
     */
    public static boolean keyExists( int nFeatureId, String strBusinessClassName, Plugin plugin )
    {
        return _dao.keyExists( nFeatureId, strBusinessClassName, plugin );
    }
}
