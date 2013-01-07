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
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.LocalizationKey;
import fr.paris.lutece.plugins.pluginwizard.business.LocalizationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.ResourceKey;
import java.util.HashMap;

/**
 * LocalizationService
 */
public class LocalizationService
{

    private static HashMap<String, LocalizationKey> _mapLocalized = new HashMap<String, LocalizationKey>();
    private static boolean _bInit = false;

    /**
     * Initialize service
     */
    private static void init()
    {
        for( LocalizationKey key : LocalizationKeyHome.getLocalizationKeysList())
        {
            _mapLocalized.put( key.getKeyName() , key );
        }
            

    }

    /**
     * Localize a key
     * @param strKey The key
     * @return A localized Resource key
     */
    static ResourceKey localize( String strKey )
    {
        ResourceKey key = new ResourceKey();
        key.setMarkerIdentifier( strKey );
        if (!_bInit)
        {
            init();
        }
        String strKeyname = strKey.substring( strKey.lastIndexOf( "." ) + 1 );

        LocalizationKey locales = _mapLocalized.get( strKey );
        if (locales != null)
        {
            key.setFrenchLocale( locales.getFrenchLocale() );
            key.setEnglishLocale( locales.getEnglishLocale() );
            return key;
        }

        locales = _mapLocalized.get( strKeyname );
        if (locales != null)
        {
            key.setFrenchLocale( locales.getFrenchLocale() );
            key.setEnglishLocale( locales.getEnglishLocale() );
            return key;
        }

        strKeyname = strKeyname.replaceAll( "rowTitle", "" );
        strKeyname = strKeyname.replaceAll( "column", "" );
        strKeyname = strKeyname.replaceAll( "button", "" );
        key.setFrenchLocale( strKeyname );
        key.setEnglishLocale( strKeyname );
        return key;
    }

}
