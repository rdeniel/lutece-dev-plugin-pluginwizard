/*
 * Copyright (c) 2002-2020, City of Paris
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

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.paris.lutece.plugins.pluginwizard.util.Utils;

/**
 * This is the business class for the object BusinessClass
 */
public class BusinessClass
{
    // Variables declarations
    private int _nIdBusinessClass;
    @NotEmpty( message = "pluginwizard.error.businessClass.class.notEmpty" )
    @Pattern( regexp = "[A-Z][a-zA-Z]*", message = "pluginwizard.error.businessClass.class.pattern" )
    private String _strBusinessClass;
    @NotEmpty( message = "pluginwizard.error.businessClass.tableName.notEmpty" )
    @Pattern( regexp = "[a-z][a-z_]*", message = "pluginwizard.error.businessClass.tableName.pattern" )
    private String _strBusinessTableName;
    private List<Attribute> _listAttributes;
    private String _strPrimaryAttributeName;

    /**
     *
     */
    public BusinessClass( )
    {
        _listAttributes = new ArrayList<>( );
    }

    /**
     * Returns the nIdBusinessClass
     *
     * @return The nIdBusinessClass
     */
    public int getId( )
    {
        return _nIdBusinessClass;
    }

    /**
     * Sets the nIdBusinessClass
     *
     * @param nIdBusinessClass
     *            The IdPlugin
     */
    public void setId( int nIdBusinessClass )
    {
        _nIdBusinessClass = nIdBusinessClass;
    }

    /**
     * Returns the BusinessClass
     *
     * @return The BusinessClass
     */
    public String getBusinessClass( )
    {
        return _strBusinessClass;
    }

    /**
     * Sets the BusinessClass
     *
     * @param strBusinessClass
     *            The BusinessClass
     */
    public void setBusinessClass( String strBusinessClass )
    {
        _strBusinessClass = strBusinessClass;
    }

    /**
     * Returns the BusinessTableName
     *
     * @return The BusinessTableName
     */
    public String getBusinessTableName( )
    {
        return _strBusinessTableName;
    }

    /**
     * Sets the BusinessTableName
     *
     * @param strBusinessTableName
     *            The BusinessTableName
     */
    public void setBusinessTableName( String strBusinessTableName )
    {
        _strBusinessTableName = strBusinessTableName;
    }

    // ///////////////////////////////////////////////////////////
    /**
     * Sets the list of attributes associated to business class
     *
     * @param listAttributes
     *            The collection of attributes associated to the class
     */
    public void setAttributes( List<Attribute> listAttributes )
    {
        if ( listAttributes != null )
        {
            _listAttributes = new ArrayList<>( listAttributes );
        }
        else
        {
            _listAttributes = null;
        }
    }

    /**
     * Returns the collection of attributes
     *
     * @return the collection of child attributes
     */
    public List<Attribute> getAttributes( )
    {
        if ( _listAttributes != null )
        {
            return new ArrayList<>( _listAttributes );
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the primary key of the class
     *
     * @param strPrimaryAttributeName
     *            The key attribute name
     */
    public void setPrimaryKey( String strPrimaryAttributeName )
    {
        _strPrimaryAttributeName = strPrimaryAttributeName;
    }

    /**
     * Fetches the attributes which represents the identifier of the business class
     *
     * @return The key
     */
    public String getPrimaryKey( )
    {
        return _strPrimaryAttributeName;
    }

    // ////////////////////////////////////////////////////////////
    // The methods below are meant to be used when generating the artifacts of the plugin

    /**
     * Fetches the primary key
     *
     * @return The name of the key
     */
    @JsonIgnore
    public String getPrimaryKeyName( )
    {
        return Utils.getProperName( _strPrimaryAttributeName );
    }

    /**
     * Returns the BusinessClass
     *
     * @return The BusinessClass
     */
    @JsonIgnore
    public String getBusinessClassCapsFirst( )
    {
        char [ ] characters = _strBusinessClass.toCharArray( );
        characters [0] = Character.toTitleCase( characters [0] );

        return String.valueOf( characters );
    }

    /**
     * Returns the InstanceName
     *
     * @return The InstanceName
     */
    @JsonIgnore
    public String getInstanceName( )
    {
        return Utils.firstLowerCase( _strBusinessClass );
    }

}
