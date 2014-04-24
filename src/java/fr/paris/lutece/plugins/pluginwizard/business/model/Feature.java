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
package fr.paris.lutece.plugins.pluginwizard.business.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * This is the business class for the object Feature
 */
public class Feature implements Serializable
{
    // Variables declarations
    private List<Integer> _nIdBusinessClasses;
    private int _nId;
    @NotEmpty( message = "pluginwizard.error.feature.right.notEmpty" )
    @Pattern( regexp = "[A-Z][A-Z_]*", message = "pluginwizard.error.feature.right.pattern" )
    private String _strFeatureRight;
    @NotEmpty( message = "pluginwizard.error.feature.title.notEmpty" )
    @Size( min = 6, max = 80, message = "pluginwizard.error.feature.title.size" )
    private String _strFeatureTitle;
    private String _strFeatureLevel;
    @NotEmpty( message = "pluginwizard.error.feature.name.notEmpty" )
    @Pattern( regexp = "[A-Z][a-zA-Z]*", message = "pluginwizard.error.feature.name.pattern" )
    private String _strFeatureName;
    @NotEmpty( message = "pluginwizard.error.feature.description.notEmpty" )
    @Size( min = 6, max = 255, message = "pluginwizard.error.feature.description.size" )
    private String _strFeatureDescription;
    private String _strJspName;

    /**
     * Constructor initializing a collection of business class
     */

    /**
     * Returns the Id
     * @return The Id
     */
    public int getId(  )
    {
        return _nId;
    }

    /**
     * Sets the nIdFeature
     * @param nId The IdFeature
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the FeatureId
     * @return The FeatureId
     */
    public String getFeatureRight(  )
    {
        return _strFeatureRight;
    }

    /**
     * Sets the Feature right
     * @param strFeatureRight The  Feature right
     */
    public void setFeatureRight( String strFeatureRight )
    {
        _strFeatureRight = strFeatureRight;
    }

    /**
     * Returns the FeatureTitle
     * @return The FeatureTitle
     */
    public String getFeatureTitle(  )
    {
        return _strFeatureTitle;
    }

    /**
     * Sets the FeatureTitle
     * @param strFeatureTitle The FeatureTitle
     */
    public void setFeatureTitle( String strFeatureTitle )
    {
        _strFeatureTitle = strFeatureTitle;
    }

    /**
     * Returns the FeatureLevel
     * @return The FeatureLevel
     */
    public String getFeatureLevel(  )
    {
        return _strFeatureLevel;
    }

    /**
     * Sets the FeatureLevel
     * @param strFeatureLevel The FeatureLevel
     */
    public void setFeatureLevel( String strFeatureLevel )
    {
        _strFeatureLevel = strFeatureLevel;
    }

    /**
     * Returns the  Feature name
     * @return The  Feature name
     */
    public String getFeatureName(  )
    {
        return _strFeatureName;
    }

    /**
     * Sets the FeatureName
     *
     * @param strFeatureName The Feature name
     */
    public void setFeatureName( String strFeatureName )
    {
        _strFeatureName = strFeatureName;
    }

    /**
     * Returns the FeatureDescription
     * @return The FeatureDescription
     */
    public String getFeatureDescription(  )
    {
        return _strFeatureDescription;
    }

    /**
     * Sets the FeatureDescription
     * @param strFeatureDescription The FeatureDescription
     */
    public void setFeatureDescription( String strFeatureDescription )
    {
        _strFeatureDescription = strFeatureDescription;
    }

    /**
     * Returns the JspName
     * @return The JspName
     */
    public String getJspName(  )
    {
        return _strJspName;
    }

    /**
     * Sets the JspName
     * @param strJspName The JspName
     */
    public void setJspName( String strJspName )
    {
        _strJspName = strJspName;
    }

    /**
     * Returns the list of IdBusinessClasses
     * @return the collection of IdBusinessClasses
     */
    public List<Integer> getIdBusinessClasses(  )
    {
        return _nIdBusinessClasses;
    }

    /**
     * Sets the list of IdBusinessClasses
     * @param nIdBusinessClasses The collection of IdBusinessClasses
     */
    public void setIdBusinessClasses( List<Integer> nIdBusinessClasses )
    {
        _nIdBusinessClasses = nIdBusinessClasses;
    }
}
