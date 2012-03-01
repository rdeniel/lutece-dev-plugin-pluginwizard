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
package fr.paris.lutece.plugins.pluginwizard.business.model.user;

import java.io.Serializable;


/**
 *
 * The class will determine the choices of the user during the navigation process
 *
 */
public class UserChoice implements Serializable
{
    private static final long serialVersionUID = 5783514241945024840L;
    private boolean _bBusinessClasses;
    private boolean _bJspBean;
    private boolean _bSqlFiles;
    private boolean _bBackOfficeTemplate;
    private boolean _bResourceFiles;
    private boolean _bBackOfficeJsp;
    private boolean _bPluginPropertiesFile;
    private boolean _bPluginXmlDefinition;
    private boolean _bMavenPomXml;
    private boolean _bSpringContextXml;
    private boolean _bXpages;

    /**
     * Returns boolean value whether to generate business classes
     * @return if true business classes are generated
     */
    public boolean getBusinessClasses(  )
    {
        return _bBusinessClasses;
    }

    /**
     * Sets the business class choice
     * @param bBusinessClasses The boolean value to generate business class
     */
    public void setBusinessClasses( boolean bBusinessClasses )
    {
        _bBusinessClasses = bBusinessClasses;
    }

    /**
     * Fetches the JspBean choice
     * @return if true jspbean are generated
     */
    public boolean getJspBean(  )
    {
        return _bJspBean;
    }

    /**
     * Sets the bJspBeanChoice
     * @param bJspBeanChoice  The boolean value to generate business class
     */
    public void setJspBean( boolean bJspBeanChoice )
    {
        _bJspBean = bJspBeanChoice;
    }

    /**
     * Fetches the sql choice
     * @return if true sql files are generated
     */
    public boolean getSqlFiles(  )
    {
        return _bSqlFiles;
    }

    /**
     * Sets the sql files generation
     * @param bSqlFiles The boolean value to generate sql files
     */
    public void setSqlFiles( boolean bSqlFiles )
    {
        _bSqlFiles = bSqlFiles;
    }

    /**
     * Fetches the back office templates choice
     * @return if true back office templates are generated
     */
    public boolean getBackOfficeTemplate(  )
    {
        return _bBackOfficeTemplate;
    }

    /**
     * Sets the back office templates generation  choice
     * @param bBackOfficeTemplate The boolean value
     */
    public void setBackOfficeTemplate( boolean bBackOfficeTemplate )
    {
        _bBackOfficeTemplate = bBackOfficeTemplate;
    }

    /**
     * Fetches resource files choice
     * @return if true resource files are generated
     */
    public boolean getResourceFiles(  )
    {
        return _bResourceFiles;
    }

    /**
     * Sets the resource files generation  choice
     * @param bResourceFiles The boolean value
     */
    public void setResourceFiles( boolean bResourceFiles )
    {
        _bResourceFiles = bResourceFiles;
    }

    /**
     * Fetches back office jsp  files choice
     * @return if true back office jsp are generated
     */
    public boolean getBackOfficeJsp(  )
    {
        return _bBackOfficeJsp;
    }

    /**
     * Sets the back office jsp choice
     * @param bBackOfficeJsp The boolean value
     */
    public void setBackOfficeJsp( boolean bBackOfficeJsp )
    {
        _bBackOfficeJsp = bBackOfficeJsp;
    }

    /**
     *  Fetches plugin properties  files choice
     * @return if true plugin properties file are generated
     */
    public boolean getPluginPropertiesFile(  )
    {
        return _bPluginPropertiesFile;
    }

    /**
     * Sets plugin properties choice
     * @param bPluginPropertiesFile The boolean value
     */
    public void setPluginPropertiesFile( boolean bPluginPropertiesFile )
    {
        _bPluginPropertiesFile = bPluginPropertiesFile;
    }

    /**
     * Fetches xml describing the plugin choice
     * @return if true the xml describing the plugin is generated
     */
    public boolean getPluginXmlDefinition(  )
    {
        return _bPluginXmlDefinition;
    }

    /**
     * Sets the xml plugin choice
     * @param bPluginXmlDefinition The boolean value
     */
    public void setPluginXmlDefinition( boolean bPluginXmlDefinition )
    {
        _bPluginXmlDefinition = bPluginXmlDefinition;
    }

    /**
     * Fetches pom.xml choice
     * @return if true the pom.xml of the plugin is generated
     */
    public boolean getMavenPomXml(  )
    {
        return _bMavenPomXml;
    }

    /**
     * Sets the maven pom choice
     * @param bMavenPomXml The boolean value
     */
    public void setMavenPomXml( boolean bMavenPomXml )
    {
        _bMavenPomXml = bMavenPomXml;
    }

    /**
     * Fetches spring context file choice
     * @return if true the context file is generated
     */
    public boolean getSpringContextXml(  )
    {
        return _bSpringContextXml;
    }

    /**
     * Sets the context file used by spring choice
     * @param bSpringContextXml The boolean value
     */
    public void setSpringContextXml( boolean bSpringContextXml )
    {
        _bSpringContextXml = bSpringContextXml;
    }

    /**
     * Fetches Xpagese choice
     * @return if true the Xpages are generated
     */
    public boolean getXpages(  )
    {
        return _bXpages;
    }

    /**
     * Sets the XPages choice
     * @param bXpages The boolean value
     */
    public void setXpages( boolean bXpages )
    {
        _bXpages = bXpages;
    }
}
