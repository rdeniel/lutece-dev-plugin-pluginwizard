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


/**
 * This is the business class for the object LocalizationKey
 */
public class LocalizationKey
{
    // Variables declarations 
    private int _nIdKey;
    private String _strKeyName;
    private String _strEnglishLocale;
    private String _strFrenchLocale;

    /**
     * Returns the IdKey
     * @return The IdKey
     */
    public int getIdKey(  )
    {
        return _nIdKey;
    }

    /**
     * Sets the IdKey
     * @param nIdKey The IdKey
     */
    public void setIdKey( int nIdKey )
    {
        _nIdKey = nIdKey;
    }

    /**
     * Returns the KeyName
     * @return The KeyName
     */
    public String getKeyName(  )
    {
        return _strKeyName;
    }

    /**
     * Sets the KeyName
     * @param strKeyName The KeyName
     */
    public void setKeyName( String strKeyName )
    {
        _strKeyName = strKeyName;
    }

    /**
     * Returns the EnglishLocale
     * @return The EnglishLocale
     */
    public String getEnglishLocale(  )
    {
        return _strEnglishLocale;
    }

    /**
     * Sets the EnglishLocale
     * @param strEnglishLocale The EnglishLocale
     */
    public void setEnglishLocale( String strEnglishLocale )
    {
        _strEnglishLocale = strEnglishLocale;
    }

    /**
     * Returns the FrenchLocale
     * @return The FrenchLocale
     */
    public String getFrenchLocale(  )
    {
        return _strFrenchLocale;
    }

    /**
     * Sets the FrenchLocale
     * @param strFrenchLocale The FrenchLocale
     */
    public void setFrenchLocale( String strFrenchLocale )
    {
        _strFrenchLocale = strFrenchLocale;
    }
}
