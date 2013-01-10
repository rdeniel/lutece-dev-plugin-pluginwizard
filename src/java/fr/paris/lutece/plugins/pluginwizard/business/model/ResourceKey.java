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


/**
 * This is the business class for the object ResourceKey
 */
public class ResourceKey
{
    // Variables declarations 
    private int _nIdResourceKey;
    private int _nIdPlugin;
    private String _strMarkerIdentifier;
    private String _strEnglishLocale;
    private String _strFrenchLocale;

    /**
     * Returns the IdResourceKey
     * @return The IdResourceKey
     */
    public int getIdResourceKey(  )
    {
        return _nIdResourceKey;
    }

    /**
     * Sets the IdResourceKey
     * @param nIdResourceKey The IdResourceKey
     */
    public void setIdResourceKey( int nIdResourceKey )
    {
        _nIdResourceKey = nIdResourceKey;
    }

    /**
     * Returns the IdPlugin
     * @return The IdPlugin
     */
    public int getIdPlugin(  )
    {
        return _nIdPlugin;
    }

    /**
     * Sets the IdPlugin
     * @param nIdPlugin The IdPlugin
     */
    public void setIdPlugin( int nIdPlugin )
    {
        _nIdPlugin = nIdPlugin;
    }

    /**
     * Returns the MarkerIdentifier
     * @return The MarkerIdentifier
     */
    public String getMarkerIdentifier(  )
    {
        return _strMarkerIdentifier;
    }

    /**
     * Sets the MarkerIdentifier
     * @param strMarkerIdentifier The MarkerIdentifier
     */
    public void setMarkerIdentifier( String strMarkerIdentifier )
    {
        _strMarkerIdentifier = strMarkerIdentifier;
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
