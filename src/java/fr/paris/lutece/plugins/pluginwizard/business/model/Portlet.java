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

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;


/**
 * This is the business class for the object Portlet
 */
public class Portlet
{
    // Variables declarations 
    private int _nIdPortletPlugin;
    @NotEmpty( message = "pluginwizard.error.portlet.class.notEmpty" )
    @Pattern( regexp = "[a-zA-Z.]*", message = "pluginwizard.error.portlet.class.pattern" )
    private String _strPortletClass;
    @NotEmpty( message = "pluginwizard.error.portlet.type.notEmpty" )
    @Pattern( regexp = "[A-Z]*_PORTLET", message = "pluginwizard.error.portlet.type.pattern" )
    private String _strPortletTypeName;
    private String _strPortletCreationUrl;
    private String _strPortletUpdateUrl;

    /**
     * Returns the PortletClass
     * @return The PortletClass
     */
    public String getPortletClass(  )
    {
        return _strPortletClass;
    }

    /**
     * Sets the PortletClass
     * @param strPortletClass The PortletClass
     */
    public void setPortletClass( String strPortletClass )
    {
        _strPortletClass = strPortletClass;
    }

    /**
     * Returns the PortletTypeName
     * @return The PortletTypeName
     */
    public String getPortletTypeName(  )
    {
        return _strPortletTypeName;
    }

    /**
     * Sets the PortletTypeName
     * @param strPortletTypeName The PortletTypeName
     */
    public void setPortletTypeName( String strPortletTypeName )
    {
        _strPortletTypeName = strPortletTypeName;
    }

    /**
     * Returns the PortletCreationUrl
     * @return The PortletCreationUrl
     */
    public String getPortletCreationUrl(  )
    {
        return _strPortletCreationUrl;
    }

    /**
     * Sets the PortletCreationUrl
     * @param strPortletCreationUrl The PortletCreationUrl
     */
    public void setPortletCreationUrl( String strPortletCreationUrl )
    {
        _strPortletCreationUrl = strPortletCreationUrl;
    }

    /**
     * Returns the PortletUpdateUrl
     * @return The PortletUpdateUrl
     */
    public String getPortletUpdateUrl(  )
    {
        return _strPortletUpdateUrl;
    }

    /**
     * Sets the PortletUpdateUrl
     * @param strPortletUpdateUrl The PortletUpdateUrl
     */
    public void setPortletUpdateUrl( String strPortletUpdateUrl )
    {
        _strPortletUpdateUrl = strPortletUpdateUrl;
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
