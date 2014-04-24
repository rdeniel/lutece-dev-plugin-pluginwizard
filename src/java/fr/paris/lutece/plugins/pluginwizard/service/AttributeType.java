/*
 * Copyright (c) 2002-2014, Mairie de Paris
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


/**
 * AttributeType
 */
public class AttributeType
{
    // Variables declarations 
    private int _nIdAttributeType;
    private String _strDescription;
    private String _strPrefix;
    private String _strJavaType;
    private String _strConstraint;
    private int _nMaxLength;

    /**
     * Returns the IdAttributeType
     * @return The IdAttributeType
     */
    public int getIdAttributeType(  )
    {
        return _nIdAttributeType;
    }

    /**
     * Sets the IdAttributeType
     * @param nIdAttributeType The IdAttributeType
     */
    public void setIdAttributeType( int nIdAttributeType )
    {
        _nIdAttributeType = nIdAttributeType;
    }

    /**
     * Returns the Description
     * @return The Description
     */
    public String getDescription(  )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * @param strDescription The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the Prefix
     * @return The Prefix
     */
    public String getPrefix(  )
    {
        return _strPrefix;
    }

    /**
     * Sets the Prefix
     * @param strPrefix The Prefix
     */
    public void setPrefix( String strPrefix )
    {
        _strPrefix = strPrefix;
    }

    /**
     * Returns the JavaType
     * @return The JavaType
     */
    public String getJavaType(  )
    {
        return _strJavaType;
    }

    /**
     * Sets the JavaType
     * @param strJavaType The JavaType
     */
    public void setJavaType( String strJavaType )
    {
        _strJavaType = strJavaType;
    }

    /**
     * Returns the MaxLength
     * @return The MaxLength
     */
    public int getMaxLength(  )
    {
        return _nMaxLength;
    }

    /**
     * Sets the MaxLength
     * @param nMaxLength The MaxLength
     */
    public void setMaxLength( int nMaxLength )
    {
        _nMaxLength = nMaxLength;
    }

    /**
     * Returns the Constraint
     * @return The Constraint
     */
    public String getConstraint(  )
    {
        return _strConstraint;
    }

    /**
     * Sets the Constraint
     * @param strConstraint The Constraint
     */
    public void setConstraint( String strConstraint )
    {
        _strConstraint = strConstraint;
    }
}
