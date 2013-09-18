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
package fr.paris.lutece.plugins.pluginwizard.web.formbean;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * BusinessClass formbean
 */
public class BusinessClassFormBean
{
    // Variables declarations 
    private int _nIdFeature;
    private int _nIdBusinessClass;
    @NotEmpty( message = "pluginwizard.error.businessClass.class.notEmpty" )
    @Pattern( regexp = "[A-Z][a-zA-Z]*", message = "pluginwizard.error.businessClass.class.pattern" )
    private String _strBusinessClass;
    @NotEmpty( message = "pluginwizard.error.businessClass.tableName.notEmpty" )
    @Pattern( regexp = "[a-z][a-z_]*", message = "pluginwizard.error.businessClass.tableName.pattern" )
    private String _strBusinessTableName;
    private String _strPrimaryAttributeName;
    private String _strDescriptionAttributeName;

    /**
     * Returns the nIdBusinessClass
     *
     * @return The nIdBusinessClass
     */
    public int getId(  )
    {
        return _nIdBusinessClass;
    }

    /**
     * Sets the nIdBusinessClass
     *
     * @param nIdBusinessClass The IdPlugin
     */
    public void setId( int nIdBusinessClass )
    {
        _nIdBusinessClass = nIdBusinessClass;
    }

    /**
     * Returns the IdFeature
     *
     * @return The IdFeature
     */
    public int getIdFeature(  )
    {
        return _nIdFeature;
    }

    /**
     * Sets the IdFeature
     *
     * @param nIdFeature The IdPlugin
     */
    public void setIdFeature( int nIdFeature )
    {
        _nIdFeature = nIdFeature;
    }

    /**
     * Returns the BusinessClass
     *
     * @return The BusinessClass
     */
    public String getBusinessClass(  )
    {
        return _strBusinessClass;
    }

    /**
     * Sets the BusinessClass
     *
     * @param strBusinessClass The BusinessClass
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
    public String getBusinessTableName(  )
    {
        return _strBusinessTableName;
    }

    /**
     * Sets the BusinessTableName
     *
     * @param strBusinessTableName The BusinessTableName
     */
    public void setBusinessTableName( String strBusinessTableName )
    {
        _strBusinessTableName = strBusinessTableName;
    }

    /**
     * Sets the class description and it is given by an attribute
     *
     * @param strDescriptionAttributeName The name of the attribute which the
     * description
     */
    public void setClassDescription( String strDescriptionAttributeName )
    {
        _strDescriptionAttributeName = strDescriptionAttributeName;
    }

    /**
     * Fetches the description of a business class
     *
     * @return The description of the class
     */
    public String getClassDescription(  )
    {
        return _strDescriptionAttributeName;
    }

    /**
     * Sets the primary key of the class
     *
     * @param strPrimaryAttributeName The key attribute name
     */
    public void setPrimaryKey( String strPrimaryAttributeName )
    {
        _strPrimaryAttributeName = strPrimaryAttributeName;
    }

    /**
     * Fetches the attributes which represents the identifier of the business
     * class
     *
     * @return The key
     */
    public String getPrimaryKey(  )
    {
        return _strPrimaryAttributeName;
    }

}
