/*
 * Copyright (c) 2002-2019, Mairie de Paris
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
package fr.paris.lutece.plugins.pluginwizard.service.generator;

/**
 * @author denielr
 *
 */
public class AdminJspBeanFileConfig
{
    // Variables declarations
    private String _strPrefix = "";
    private String _strSuffix = "";
    private String _strPath = "src";
    private String _strTemplate;
    private String _strAbstractParentBeanTemplate = "";

    /**
     * Returns the Prefix
     * 
     * @return The Prefix
     */
    public String getPrefix( )
    {
        return _strPrefix;
    }

    /**
     * Sets the Prefix
     * 
     * @param strPrefix
     *            The Prefix
     */
    public void setPrefix( String strPrefix )
    {
        _strPrefix = strPrefix;
    }

    /**
     * Returns the Suffix
     * 
     * @return The Suffix
     */
    public String getSuffix( )
    {
        return _strSuffix;
    }

    /**
     * Sets the Suffix
     * 
     * @param strSuffix
     *            The Suffix
     */
    public void setSuffix( String strSuffix )
    {
        _strSuffix = strSuffix;
    }

    /**
     * Returns the Path
     * 
     * @return The Path
     */
    public String getSourcePath( )
    {
        return _strPath;
    }

    /**
     * Sets the Path
     * 
     * @param strPath
     *            The Path
     */
    public void setSourcePath( String strPath )
    {
        _strPath = strPath;
    }

    /**
     * Returns the Template
     * 
     * @return The Template
     */
    public String getTemplate( )
    {
        return _strTemplate;
    }

    /**
     * Sets the Template
     * 
     * @param strTemplate
     *            The Template
     */
    public void setTemplate( String strTemplate )
    {
        _strTemplate = strTemplate;
    }

    /**
     * @param strAbstractParentBeanTemplate
     */
    public void setAbstractParentBeanTemplate( String strAbstractParentBeanTemplate )
    {
        _strAbstractParentBeanTemplate = strAbstractParentBeanTemplate;
    }

    /**
     * @return strAbstractParentBeanTemplate The parent Template
     */
    public String getAbstractParentBeanTemplate( )
    {
        return _strAbstractParentBeanTemplate;
    }
}
