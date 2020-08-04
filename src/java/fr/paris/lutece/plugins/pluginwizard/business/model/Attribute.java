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

import fr.paris.lutece.plugins.pluginwizard.service.ModelService;
import fr.paris.lutece.plugins.pluginwizard.util.Utils;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

/**
 * This is the business class for the object Attribute
 */
public class Attribute implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Variables declarations
    private int _nIdAttribute;
    private int _nAttributeTypeId;
    @NotEmpty( message = "pluginwizard.error.attribute.name.notEmpty" )
    @Pattern( regexp = "[a-z_]*", message = "pluginwizard.error.attribute.name.pattern" )
    private String _strAttributeName;
    private int _nMaxLength;
    private boolean _bNotNull;

    /**
     * Returns the IdAttribute
     * 
     * @return The IdAttribute
     */
    public int getId( )
    {
        return _nIdAttribute;
    }

    /**
     * Sets the IdAttribute
     * 
     * @param nIdAttribute
     *            The IdAttribute
     */
    public void setId( int nIdAttribute )
    {
        _nIdAttribute = nIdAttribute;
    }

    /**
     * Returns the AttributeTypeId
     * 
     * @return The AttributeTypeId
     */
    public int getAttributeTypeId( )
    {
        return _nAttributeTypeId;
    }

    /**
     * Sets the AttributeTypeId
     * 
     * @param nAttributeTypeId
     *            The AttributeTypeId
     */
    public void setAttributeTypeId( int nAttributeTypeId )
    {
        _nAttributeTypeId = nAttributeTypeId;
    }

    /**
     * Returns the AttributeName
     * 
     * @return The AttributeName
     */
    public String getAttributeName( )
    {
        return _strAttributeName;
    }

    /**
     * Sets the AttributeName
     * 
     * @param strAttributeName
     *            The AttributeName
     */
    public void setAttributeName( String strAttributeName )
    {
        _strAttributeName = strAttributeName;
    }

    /**
     * Returns the Type
     *
     * @return The Type
     */
    @JsonIgnore
    public String getType( )
    {
        return ModelService.getAttributeType( _nAttributeTypeId );
    }

    /**
     * Returns the Name
     * 
     * @return The Name
     */
    @JsonIgnore
    public String getName( )
    {
        return Utils.getProperName( _strAttributeName );
    }

    /**
     * Returns the Name
     * 
     * @return The Name
     */
    @JsonIgnore
    public String getLabelName( )
    {
        return _strAttributeName.substring( 0, 1 ).toUpperCase( ) + _strAttributeName.substring( 1 ).replace( "_", " " );
    }

    /**
     * Returns the VariableName
     * 
     * @return The VariableName
     */
    @JsonIgnore
    public String getVariableName( )
    {
        return ModelService.getAttributePrefix( _nAttributeTypeId ) + Utils.getProperName( _strAttributeName );
    }

    /**
     * Returns the type description
     * 
     * @return The type description
     */
    @JsonIgnore
    public String getTypeDescription( )
    {
        return ModelService.getAttributeTypeDescription( _nAttributeTypeId );
    }

    /**
     * Returns the Param Name
     * 
     * @return The Param VariableName
     */
    @JsonIgnore
    public String getParamName( )
    {
        return _strAttributeName.toLowerCase( );
    }

    /**
     * Returns the java name of the attribute
     * 
     * @return The java name
     */
    @JsonIgnore
    public String getJavaName( )
    {
        return Utils.getProperName( _strAttributeName ).substring( 0, 1 ).toLowerCase( ) + Utils.getProperName( _strAttributeName ).substring( 1 );
    }

    /**
     * @return the maxLength
     */
    public int getMaxLength( )
    {
        return _nMaxLength;
    }

    /**
     * @param maxLength
     *            the maxLength to set
     */
    public void setMaxLength( int maxLength )
    {
        _nMaxLength = maxLength;
    }

    /**
     * Returns the bNotNull
     * 
     * @return The NotNull
     */
    public boolean getNotNull( )
    {
        return _bNotNull;
    }

    /**
     * Sets the Description
     * 
     * @param bNotNull
     *            The Description
     */
    public void setNotNull( boolean bNotNull )
    {
        _bNotNull = bNotNull;
    }

    /**
     * Returns the Constraint
     *
     * @return The Constraint
     */
    @JsonIgnore
    public String getConstraint( )
    {
        return ModelService.getAttributeConstraint( _nAttributeTypeId );
    }
}
