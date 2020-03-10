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

import fr.paris.lutece.util.ReferenceList;

import java.util.ArrayList;
import java.util.List;

/**
 * Attribute Service
 */
public class AttributeService
{
    private List<AttributeType> _listAttributeTypes;

    /**
     * Sets the list of attribute types
     * 
     * @param list
     *            The list
     */
    public void setAttributeTypesList( List<AttributeType> list )
    {
        if( !list.equals( null ) ) 
        {
            _listAttributeTypes = ( List<AttributeType> )( ( ( ArrayList<AttributeType> )list ).clone( ) );
        }
        else
        {
            _listAttributeTypes = list;
        }
    }

    /**
     * Returns a Reference list with all attribute types available
     * 
     * @return The list
     */
    public ReferenceList getAttributeTypes( )
    {
        ReferenceList list = new ReferenceList( );

        for ( AttributeType type : _listAttributeTypes )
        {
            list.addItem( type.getIdAttributeType( ), type.getDescription( ) );
        }

        return list;
    }

    /**
     * Get the type object from its ID
     * 
     * @param nAttributeTypeId
     *            The ID
     * @return The object
     */
    public AttributeType getType( int nAttributeTypeId )
    {
        for ( AttributeType type : _listAttributeTypes )
        {
            if ( type.getIdAttributeType( ) == nAttributeTypeId )
            {
                return type;
            }
        }

        return null;
    }
}
