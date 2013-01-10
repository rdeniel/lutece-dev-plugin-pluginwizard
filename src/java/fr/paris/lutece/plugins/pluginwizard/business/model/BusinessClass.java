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

import java.util.Collection;


/**
 * This is the business class for the object BusinessClass
 */
public class BusinessClass
{
    // Variables declarations 
    private int _nIdFeature;
    private int _nIdBusinessClass;
    private String _strBusinessClass;
    private String _strBusinessTableName;
    private String _strPluginName;
    private Collection<Attribute> _listAttributes;
    private String _strPrimaryAttributeName;
    private String _strDescriptionAttributeName;
    private String _strPackageName;

    /**
    * Returns the IdFeature
    * @return The IdFeature
    */
    public int getIdFeature(  )
    {
        return _nIdFeature;
    }

    /**
     * Sets the IdFeature
     * @param nIdFeature The IdPlugin
     */
    public void setIdFeature( int nIdFeature )
    {
        _nIdFeature = nIdFeature;
    }

    /**
    * Returns the nIdBusinessClass
    * @return The nIdBusinessClass
    */
    public int getIdBusinessClass(  )
    {
        return _nIdBusinessClass;
    }

    /**
     * Sets the nIdBusinessClass
     * @param nIdBusinessClass The IdPlugin
     */
    public void setIdBusinessClass( int nIdBusinessClass )
    {
        _nIdBusinessClass = nIdBusinessClass;
    }

    /**
     * Returns the BusinessClass
     * @return The BusinessClass
     */
    public String getBusinessClass(  )
    {
        return _strBusinessClass;
    }

    /**
     * Sets the BusinessClass
     * @param strBusinessClass The BusinessClass
     */
    public void setBusinessClass( String strBusinessClass )
    {
        _strBusinessClass = strBusinessClass;
    }

    /**
     * Returns the BusinessTableName
     * @return The BusinessTableName
     */
    public String getBusinessTableName(  )
    {
        return _strBusinessTableName;
    }

    /**
     * Sets the BusinessTableName
     * @param strBusinessTableName The BusinessTableName
     */
    public void setBusinessTableName( String strBusinessTableName )
    {
        _strBusinessTableName = strBusinessTableName;
    }

    /**
     * Returns the Plugin Name of the generated plugin
     * @return The Plugin Name
     */
    public String getBusinessPluginName(  )
    {
        return _strPluginName;
    }

    /**
     * Sets the Plugin Name of the generated plugin
     * @param strPluginName The Plugin name being generated
     */
    public void setBusinessPluginName( String strPluginName )
    {
        _strPluginName = strPluginName;
    }

    /////////////////////////////////////////////////////////////
    /**
     * Returns the BusinessClass
     * @return The BusinessClass
     */
    public String getBusinessClassUpperCase(  )
    {
        return _strBusinessClass.toUpperCase(  );
    }

    /**
     * Returns the BusinessClass
     * @return The BusinessClass
     */
    public String getBusinessClassLowerCase(  )
    {
        return _strBusinessClass.toLowerCase(  );
    }

    /**
     * Returns the BusinessClass
     * @return The BusinessClass
     */
    public String getBusinessClassCapsFirst(  )
    {
        char[] characters = _strBusinessClass.toCharArray(  );
        characters[0] = Character.toTitleCase( characters[0] );

        return String.valueOf( characters );
    }

    //////////////////////////////////////////////////////////////
    // The methods below are meant to be used when generating the artifacts of the plugin

    /**
     * The business class import
     * @return The business class import
     */
    public String getBusinessClassImport(  )
    {
        return getBusinessClass(  );
    }

    /**
     * Fetches the business class naming
     * @return The business class Naming
     */
    public String getBusinessClassNaming(  )
    {
        return getBusinessClassCapsFirst(  );
    }

    /**
     *
     * @return the business class right declaration
     */
    public String getBusinessClassRightDeclaration(  )
    {
        return getBusinessClassCapsFirst(  );
    }

    /**
     * The business class as a parameter
     * @return parameter formatting
     */
    public String getBusinessClassParamDeclaration(  )
    {
        return getBusinessClassUpperCase(  );
    }

    /**
     * The business class expressed as a class parameter
     * @return class param formatting
     */
    public String getBusinessClassParam(  )
    {
        return getBusinessClassLowerCase(  );
    }

    /**
     * The business class variable in declaration of a template
     * @return the business class declared in a template
     */
    public String getBusinessClassTemplateDeclaration(  )
    {
        return getBusinessClassUpperCase(  );
    }

    /**
     * The business class declared as a template
     * @return The business class template formatting
     */
    public String getBusinessClassTemplate(  )
    {
        return getBusinessClassLowerCase(  );
    }

    /**
     * The business class declared in a property
     * @return The business class declaring a property
     */
    public String getBusinessClassPropertyDeclaration(  )
    {
        return getBusinessClassUpperCase(  );
    }

    /**
     * The business class in a property
     * @return the correponding formatting in a property
     */
    public String getBusinessClassProperty(  )
    {
        return getBusinessClassLowerCase(  );
    }

    /**
     * The business class in making a marker declaration
     * @return The formatting in declaring a marker used by freemarker
     */
    public String getBusinessClassMarkerDeclaration(  )
    {
        return getBusinessClassUpperCase(  );
    }

    /**
     * The business class expressed as a marker
     * @return The formatting of a freemarker bookmark
     */
    public String getBusinessClassmarker(  )
    {
        return getBusinessClassLowerCase(  );
    }

    /**
     * The business class Jsp declaration
     * @return The formatting in declaration of a Jsp
     */
    public String getBusinessClassJspDeclaration(  )
    {
        return getBusinessClassUpperCase(  );
    }

    /**
     * The business class to  name a Jsp
     * @return The correponding formatting to name a Jsp
     */
    public String getBusinessClassJsp(  )
    {
        return getBusinessClassCapsFirst(  );
    }

    /**
     * Returns the class name for the message declaration
     * @return The business class for the message declaration
     */
    public String getBusinessClassMessageDeclaration(  )
    {
        return getBusinessClassUpperCase(  );
    }

    /**
     * Returns the class name used for the message
     * @return the business class for Message
     */
    public String getBusinessClassMessage(  )
    {
        return getBusinessClassCapsFirst(  );
    }

    /**
     * Returns the Proper Name
     * @param strSource the source
     * @return source
     */
    public static String getProperName( String strSource )
    {
        int nIndex = 0;
        boolean bUpper = true;
        StringBuffer strBuffer = new StringBuffer(  );

        while ( nIndex < strSource.length(  ) )
        {
            char c = strSource.charAt( nIndex );

            if ( c == '_' )
            {
                // skip by reading the next char
                nIndex++;
                bUpper = true;
            }

            if ( bUpper )
            {
                String strChar = strSource.substring( nIndex, nIndex + 1 );
                c = strChar.toUpperCase(  ).charAt( 0 );
                bUpper = false;
            }

            strBuffer.append( c );
            nIndex++;
        }

        return strBuffer.toString(  );
    }

    /**
     * Sets the list of attributes associated to business class
     * @param listAttributes The collection of attributes associated to the class
     */
    public void setAttributes( Collection<Attribute> listAttributes )
    {
        _listAttributes = listAttributes;
    }

    /**
     * Returns the collection of attributes
     * @return the collection of child attributes
     */
    public Collection<Attribute> getAttributes(  )
    {
        return _listAttributes;
    }

    /**
     * Sets the class description and it is given by an attribute
     * @param strDescriptionAttributeName The name of the attribute which the description
     */
    public void setClassDescription( String strDescriptionAttributeName )
    {
        _strDescriptionAttributeName = strDescriptionAttributeName;
    }

    /**
     * Fetches the description of a business class
     * @return The description of the class
     */
    public String getClassDescription(  )
    {
        return _strDescriptionAttributeName;
    }

    /**
     * Sets the primary key of the class
     * @param strPrimaryAttributeName The key attribute name
     */
    public void setPrimaryKey( String strPrimaryAttributeName )
    {
        _strPrimaryAttributeName = strPrimaryAttributeName;
    }

    /**
     * Fetches the attributes which represents the identifier of the business class
     * @return The key
     */
    public String getPrimaryKey(  )
    {
        return _strPrimaryAttributeName;
    }

    /**
     * Fetches the primary key
     * @return The name of the key
     */
    public String getPrimaryKeyName(  )
    {
        return getProperName( _strPrimaryAttributeName );
    }

    /**
    * Sets the Plugin Name
    * @param strPluginName The BusinessClass
    */
    public void setPluginName( String strPluginName )
    {
        _strPluginName = strPluginName;
    }

    /**
    * Returns the InstanceName
    * @return The InstanceName
    */
    public String getInstanceName(  )
    {
        String strInstanceName = _strBusinessClass.substring( 0, 1 ).toLowerCase(  ) +
            _strBusinessClass.substring( 1, _strBusinessClass.length(  ) );

        return strInstanceName;
    }

    /**
     * Sets the package name
     * @param strPluginName The plugin name
     */
    public void setPackageName( String strPluginName )
    {
        _strPackageName = "fr.paris.lutece.plugins." + strPluginName.toLowerCase(  ) + ".business";
    }

    /**
     * Returns the PackageName
     *
     * @return The PackageName
     */
    public String getPackageName(  )
    {
        return _strPackageName;
    }
}
