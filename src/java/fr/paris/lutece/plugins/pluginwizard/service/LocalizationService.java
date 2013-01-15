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
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.LocalizationKey;
import fr.paris.lutece.plugins.pluginwizard.business.LocalizationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.ResourceKey;

import java.util.HashMap;
import java.util.Map;


/**
 * LocalizationService
 */
public class LocalizationService
{
    private static Map<String, LocalizationKey> _mapLocalized = new HashMap<String, LocalizationKey>(  );
    private static boolean _bInit;

    /** Private constructor */
    private LocalizationService(  )
    {
    }

    /**
     * Initialize service
     */
    private static void init(  )
    {
        for ( LocalizationKey key : LocalizationKeyHome.getLocalizationKeysList(  ) )
        {
            _mapLocalized.put( key.getKeyName(  ), key );
        }

        _bInit = true;
    }

    /**
     * Localize a key
     * @param strFullKey The key
     * @param strPluginName  The plugin name
     * @return A localized Resource key
     */
    static ResourceKey localize( String strFullKey, String strPluginName )
    {
        ResourceKey key = new ResourceKey(  );
        key.setMarkerIdentifier( strFullKey );

        if ( !_bInit )
        {
            init(  );
        }

        String strQualifiedKey = strPluginName + "." + strFullKey;
        String strKeyname = strFullKey.substring( strFullKey.lastIndexOf( "." ) + 1 );

        key = getLocalization( strFullKey, strQualifiedKey );

        if ( key != null )
        {
            return key;
        }

        key = getLocalization( strFullKey, strFullKey );

        if ( key != null )
        {
            return key;
        }

        key = getLocalization( strFullKey, strKeyname );

        if ( key != null )
        {
            return key;
        }

        key = new ResourceKey(  );
        key.setMarkerIdentifier( strFullKey );
        strKeyname = strKeyname.replaceAll( "rowTitle", "" );
        strKeyname = strKeyname.replaceAll( "column", "" );
        strKeyname = strKeyname.replaceAll( "button", "" );
        key.setFrenchLocale( strKeyname );
        key.setEnglishLocale( strKeyname );

        return key;
    }

    /**
     * Get localization
     * @param strKey The key name
     * @param strPattern The pattern
     * @return The key
     */
    private static ResourceKey getLocalization( String strKey, String strPattern )
    {
        LocalizationKey locales = _mapLocalized.get( strPattern );

        if ( locales != null )
        {
            ResourceKey key = new ResourceKey(  );
            key.setMarkerIdentifier( strKey );
            key.setFrenchLocale( locales.getFrenchLocale(  ) );
            key.setEnglishLocale( locales.getEnglishLocale(  ) );

            return key;
        }

        return null;
    }
}
