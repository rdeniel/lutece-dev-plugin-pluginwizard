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
 * This is the business class for the object PluginPortlet
 */
public class Portlet
{
    // Variables declarations 
    private int _nIdPortletPlugin;
    private String _strPluginPortletClass;
    private String _strPluginPortletTypeName;
    private String _strPluginPortletCreationUrl;
    private String _strPluginPortletUpdateUrl;

    /**
     * Returns the PluginPortletClass
     * @return The PluginPortletClass
     */
    public String getPluginPortletClass(  )
    {
        return _strPluginPortletClass;
    }

    /**
     * Sets the PluginPortletClass
     * @param strPluginPortletClass The PluginPortletClass
     */
    public void setPluginPortletClass( String strPluginPortletClass )
    {
        _strPluginPortletClass = strPluginPortletClass;
    }

    /**
     * Returns the PluginPortletTypeName
     * @return The PluginPortletTypeName
     */
    public String getPluginPortletTypeName(  )
    {
        return _strPluginPortletTypeName;
    }

    /**
     * Sets the PluginPortletTypeName
     * @param strPluginPortletTypeName The PluginPortletTypeName
     */
    public void setPluginPortletTypeName( String strPluginPortletTypeName )
    {
        _strPluginPortletTypeName = strPluginPortletTypeName;
    }

    /**
     * Returns the PluginPortletCreationUrl
     * @return The PluginPortletCreationUrl
     */
    public String getPluginPortletCreationUrl(  )
    {
        return _strPluginPortletCreationUrl;
    }

    /**
     * Sets the PluginPortletCreationUrl
     * @param strPluginPortletCreationUrl The PluginPortletCreationUrl
     */
    public void setPluginPortletCreationUrl( String strPluginPortletCreationUrl )
    {
        _strPluginPortletCreationUrl = strPluginPortletCreationUrl;
    }

    /**
     * Returns the PluginPortletUpdateUrl
     * @return The PluginPortletUpdateUrl
     */
    public String getPluginPortletUpdateUrl(  )
    {
        return _strPluginPortletUpdateUrl;
    }

    /**
     * Sets the PluginPortletUpdateUrl
     * @param strPluginPortletUpdateUrl The PluginPortletUpdateUrl
     */
    public void setPluginPortletUpdateUrl( String strPluginPortletUpdateUrl )
    {
        _strPluginPortletUpdateUrl = strPluginPortletUpdateUrl;
    }

    /**
     * Sets the IdPortletPlugin
     * @param nIdPortletPlugin The IdPlugin
     */
    public void setId( int nIdPortletPlugin )
    {
        _nIdPortletPlugin = nIdPortletPlugin;
    }

    /**
     * Returns the IdPortletPlugin
     * @return The IdPortletPlugin
     */
    public int getId(  )
    {
        return _nIdPortletPlugin;
    }
}
