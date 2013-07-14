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
 * This is the business class for the object PluginFeature
 */
public class Feature
{
    // Variables declarations 
    private int _nId;
    private String _strPluginFeatureRight;
    private String _strPluginFeatureTitle;
    private String _strPluginFeatureLevel;
    private String _strPluginFeatureName;
    private String _strPluginFeatureDescription;
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
     * Sets the nIdPluginFeature
     * @param nId The IdPluginFeature
     */
    public void setId( int nId )
    {
        _nId = nId;
    }


    /**
     * Returns the PluginFeatureId
     * @return The PluginFeatureId
     */
    public String getPluginFeatureRight(  )
    {
        return _strPluginFeatureRight;
    }

    /**
     * Sets the PluginFeature right
     * @param strPluginFeatureRight The Plugin Feature right
     */
    public void setPluginFeatureRight( String strPluginFeatureRight )
    {
        _strPluginFeatureRight = strPluginFeatureRight;
    }

    /**
     * Returns the PluginFeatureTitle
     * @return The PluginFeatureTitle
     */
    public String getPluginFeatureTitle(  )
    {
        return _strPluginFeatureTitle;
    }

    /**
     * Sets the PluginFeatureTitle
     * @param strPluginFeatureTitle The PluginFeatureTitle
     */
    public void setPluginFeatureTitle( String strPluginFeatureTitle )
    {
        _strPluginFeatureTitle = strPluginFeatureTitle;
    }

    /**
     * Returns the PluginFeatureLevel
     * @return The PluginFeatureLevel
     */
    public String getPluginFeatureLevel(  )
    {
        return _strPluginFeatureLevel;
    }

    /**
     * Sets the PluginFeatureLevel
     * @param strPluginFeatureLevel The PluginFeatureLevel
     */
    public void setPluginFeatureLevel( String strPluginFeatureLevel )
    {
        _strPluginFeatureLevel = strPluginFeatureLevel;
    }

    /**
     * Returns the Plugin Feature name
     * @return The Plugin Feature name
     */
    public String getPluginFeatureName(  )
    {
        return _strPluginFeatureName;
    }

    /**
     * Sets the PluginFeatureName
     *
     * @param strPluginFeatureName The PluginFeature name
     */
    public void setPluginFeatureName( String strPluginFeatureName )
    {
        _strPluginFeatureName = strPluginFeatureName;
    }

    /**
     * Returns the PluginFeatureDescription
     * @return The PluginFeatureDescription
     */
    public String getPluginFeatureDescription(  )
    {
        return _strPluginFeatureDescription;
    }

    /**
     * Sets the PluginFeatureDescription
     * @param strPluginFeatureDescription The PluginFeatureDescription
     */
    public void setPluginFeatureDescription( String strPluginFeatureDescription )
    {
        _strPluginFeatureDescription = strPluginFeatureDescription;
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


}
