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
package fr.paris.lutece.plugins.pluginwizard.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for LocalizationKey objects
 */
public final class LocalizationKeyHome
{
    // Static variable pointed at the DAO instance
    private static ILocalizationKeyDAO _dao = (ILocalizationKeyDAO) SpringContextService.getBean( 
            "pluginwizard.localizationKeyDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "pluginwizard" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private LocalizationKeyHome(  )
    {
    }

    /**
     * Create an instance of the localizationKey class
     * @param localizationKey The instance of the LocalizationKey which contains the informations to store
     * @return The  instance of localizationKey which has been created with its primary key.
     */
    public static LocalizationKey create( LocalizationKey localizationKey )
    {
        _dao.insert( localizationKey, _plugin );

        return localizationKey;
    }

    /**
     * Update of the localizationKey which is specified in parameter
     * @param localizationKey The instance of the LocalizationKey which contains the data to store
     * @return The instance of the  localizationKey which has been updated
     */
    public static LocalizationKey update( LocalizationKey localizationKey )
    {
        _dao.store( localizationKey, _plugin );

        return localizationKey;
    }

    /**
     * Remove the localizationKey whose identifier is specified in parameter
     * @param nLocalizationKeyId The localizationKey Id
     */
    public static void remove( int nLocalizationKeyId )
    {
        _dao.delete( nLocalizationKeyId, _plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a localizationKey whose identifier is specified in parameter
     * @param nKey The localizationKey primary key
     * @return an instance of LocalizationKey
     */
    public static LocalizationKey findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the localizationKey objects and returns them in form of a collection
     * @return the collection which contains the data of all the localizationKey objects
     */
    public static Collection<LocalizationKey> getLocalizationKeysList(  )
    {
        return _dao.selectLocalizationKeysList( _plugin );
    }
}
