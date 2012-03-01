/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
import java.util.Collection;


/**
 * This is the business class for the object PluginFeature
 */
public class PluginFeature
{
    // Variables declarations 
    private int _nIdPlugin;
    private int _nIdPluginFeature;
    private String _strPluginFeatureLabel;
    private String _strPluginFeatureTitle;
    private String _strPluginFeatureLevel;
    private String _strPluginFeatureUrl;
    private String _strPluginFeatureDescription;
    private Collection<BusinessClass> _listBusinessClasses;

    /**
     * Constructor initializing a collection of business class
     */
    public PluginFeature(  )
    {
        _listBusinessClasses = new ArrayList<BusinessClass>(  );
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
     * Returns the IdPlugin
     * @return The IdPlugin
     */
    public int getIdPluginFeature(  )
    {
        return _nIdPluginFeature;
    }

    /**
     * Sets the nIdPluginFeature
     * @param nIdPluginFeature The IdPluginFeature
     */
    public void setIdPluginFeature( int nIdPluginFeature )
    {
        _nIdPluginFeature = nIdPluginFeature;
    }

    /**
     * Returns the PluginFeatureId
     * @return The PluginFeatureId
     */
    public String getPluginFeatureLabel(  )
    {
        return _strPluginFeatureLabel;
    }

    /**
     * Sets the PluginFeatureLabel
     * @param strPluginFeatureLabel The PluginFeatureLabel
     */
    public void setPluginFeatureLabel( String strPluginFeatureLabel )
    {
        _strPluginFeatureLabel = strPluginFeatureLabel;
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
     * Returns the PluginFeatureUrl
     * @return The PluginFeatureUrl
     */
    public String getPluginFeatureUrl(  )
    {
        return _strPluginFeatureUrl;
    }

    /**
     * Sets the PluginFeatureUrl
     * @param strPluginFeatureUrl The PluginFeatureUrl
     */
    public void setPluginFeatureUrl( String strPluginFeatureUrl )
    {
        _strPluginFeatureUrl = strPluginFeatureUrl;
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
     * Adds a business class to the plugin feature
     * @param businessClass The business class
     */
    public void addBusinessClass( BusinessClass businessClass )
    {
        _listBusinessClasses.add( businessClass );
    }

    /**
     *  Fetches the list of business classes
     * @return The collection of business class attached to the feature
     */
    public Collection<BusinessClass> getBusinessClasses(  )
    {
        return _listBusinessClasses;
    }
}
