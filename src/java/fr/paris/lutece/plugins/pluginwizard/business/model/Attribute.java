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

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * This is the business class for the object Attribute
 */
public class Attribute
{
    // Variables declarations
    private int _nIdAttribute;
    private int _nAttributeTypeId;
    private int _nBusinessClassId;
    private boolean _bIsPrimary;
    private boolean _bIsDescription;
    private String _strAttributeName;
    private String _strJavaType;
    private int _nMaxLength;
    private boolean _bCouldNotBeEmpty = true; //FIXME

    /**
    * Returns the IdAttribute
    * @return The IdAttribute
    */
    public int getId(  )
    {
        return _nIdAttribute;
    }

    /**
    * Sets the IdAttribute
    * @param nIdAttribute The IdAttribute
    */
    public void setId( int nIdAttribute )
    {
        _nIdAttribute = nIdAttribute;
    }

    /**
     * Returns the AttributeTypeId
     * @return The AttributeTypeId
     */
    public int getAttributeTypeId(  )
    {
        return _nAttributeTypeId;
    }

    /**
     * Sets the AttributeTypeId
     * @param nAttributeTypeId The AttributeTypeId
     */
    public void setAttributeTypeId( int nAttributeTypeId )
    {
        _nAttributeTypeId = nAttributeTypeId;
    }

    /**
     * Returns the BusinessClassId
     * @return The BusinessClassId
     */
    public int getBusinessClassId(  )
    {
        return _nBusinessClassId;
    }

    /**
     * Sets the BusinessClassId
     * @param nBusinessClassId The BusinessClassId
     */
    public void setBusinessClassId( int nBusinessClassId )
    {
        _nBusinessClassId = nBusinessClassId;
    }

    /**
     * Returns the AttributeName
     * @return The AttributeName
     */
    public String getAttributeName(  )
    {
        return _strAttributeName;
    }

    /**
     * Sets the AttributeName
     * @param strAttributeName The AttributeName
     */
    public void setAttributeName( String strAttributeName )
    {
        _strAttributeName = strAttributeName;
    }

    /**
    * Returns the IdAttribute
    * @return The IdAttribute
    */
    public boolean getIsPrimary(  )
    {
        return _bIsPrimary;
    }

    /**
    * Sets the bIsPrimary
    * @param bIsPrimary The IsPrimary
    */
    public void setIsPrimary( boolean bIsPrimary )
    {
        _bIsPrimary = bIsPrimary;
    }

    /**
    * Returns the bIsDescription
    * @return The IsDescription
    */
    public boolean getIsDescription(  )
    {
        return _bIsDescription;
    }

    /**
    * Sets the Description
    * @param bIsDescription The Description
    */
    public void setIsDescription( boolean bIsDescription )
    {
        _bIsDescription = bIsDescription;
    }

    /**
    * Returns the Type
    *
    * @return The Type
    */
    public String getType(  )
    {
        return _strJavaType;
    }

    /**
    * Sets the type of an attribute
    * @param strAttributeTypeName The attribute type name
    */
    public void setType( String strAttributeTypeName )
    {
        _strJavaType = strAttributeTypeName;
    }

    /**
    * Returns the Name
    * @return The Name
    */
    @JsonIgnore
    public String getName(  )
    {
        return getProperName( _strAttributeName );
    }

    /**
    * Returns the Name
    * @return The Name
    */
    @JsonIgnore
    public String getLabelName(  )
    {
        return _strAttributeName.substring( 0, 1 ).toUpperCase(  ) +
        _strAttributeName.substring( 1 ).replace( "_", " " );
    }

    /**
     * Returns the VariableName
     * @return  _strVariableName The VariableName
     */
    @JsonIgnore
    public String getVariableName(  )
    {
        return getPrefix( _strJavaType ) + getProperName( _strAttributeName );
    }

    /**
     * Returns the VariableName
     * @return  _strVariableName The VariableName
     */
    @JsonIgnore
    public String getParamName(  )
    {
        return _strAttributeName.toLowerCase(  );
    }

    /**
     * Returns the java name of the attribute
     * @return The java name
     */
    @JsonIgnore
    public String getJavaName(  )
    {
        return getProperName( _strAttributeName ).substring( 0, 1 ).toLowerCase(  ) +
        getProperName( _strAttributeName ).substring( 1 );
    }

    /**
    * Returns the Prefix of variable
    * @param strType the type of variable
    * @return prefix
    */
    private String getPrefix( String strType )
    {
        if ( strType.equalsIgnoreCase( "int" ) )
        {
            return "n";
        }

        if ( strType.equalsIgnoreCase( "String" ) )
        {
            return "str";
        }

        if ( strType.equalsIgnoreCase( "Date" ) )
        {
            return "date";
        }

        return "";
    }

    /**
     * Returns the Proper Name
     * @param strSource the source
     * @return source name
     */
    public static String getProperName( String strSource )
    {
        int nIndex = 0;
        boolean bUpper = true;
        StringBuilder strBuffer = new StringBuilder(  );

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
     * @return the maxLength
     */
    public int getMaxLength(  )
    {
        return _nMaxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength( int maxLength )
    {
        _nMaxLength = maxLength;
    }

    /**
     * Returns the bCouldNotBeEmpty
     * @return The CouldNotBeEmpty
     */
    public boolean getCouldNotBeEmpty(  )
    {
        return _bCouldNotBeEmpty;
    }

    /**
    * Sets the Description
    * @param bCouldNotBeEmpty The Description
    */
    public void setCouldNotBeEmpty( boolean bCouldNotBeEmpty )
    {
        _bCouldNotBeEmpty = bCouldNotBeEmpty;
    }
}
