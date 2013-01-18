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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;
import static org.junit.Assert.*;

/**
 *
 * @author pierre
 */
public class AttributeBusinessTest extends LuteceTestCase
{
    private final static int IDATTRIBUTE1 = 1;
    private final static int IDATTRIBUTE2 = 2;
    private final static int ATTRIBUTETYPEID1 = 1;
    private final static int ATTRIBUTETYPEID2 = 2;
    private final static String ATTRIBUTENAME1 = "AttributeName1";
    private final static String ATTRIBUTENAME2 = "AttributeName2";
    private final static boolean ISPRIMARY1 = true;
    private final static boolean ISPRIMARY2 = false;
    private final static boolean ISDESCRIPTION1 = true;
    private final static boolean ISDESCRIPTION2 = false;
    private final static int nBusinessClassId =  1;

    public void testBusiness(  )
    {
        Plugin plugin = PluginService.getPlugin( "pluginwizard" );
        
        // Initialize an object
        Attribute attribute = new Attribute();
        attribute.setIdAttribute( IDATTRIBUTE1 );
        attribute.setAttributeTypeId( ATTRIBUTETYPEID1 );
        attribute.setAttributeName( ATTRIBUTENAME1 );
        attribute.setIsPrimary( ISPRIMARY1 );
        attribute.setIsDescription( ISDESCRIPTION1 );
        attribute.setBusinessClassId( nBusinessClassId );

        // Create test
        AttributeHome.create( attribute , plugin );
        Attribute attributeStored = AttributeHome.findByPrimaryKey( attribute.getIdAttribute() , plugin );
        assertEquals( attributeStored.getIdAttribute() , attribute.getIdAttribute() );
        assertEquals( attributeStored.getAttributeTypeId() , attribute.getAttributeTypeId() );
        assertEquals( attributeStored.getAttributeName() , attribute.getAttributeName() );
        assertEquals( attributeStored.getIsPrimary() , attribute.getIsPrimary() );
        assertEquals( attributeStored.getIsDescription() , attribute.getIsDescription() );

        // Update test
        attribute.setIdAttribute( IDATTRIBUTE2 );
        attribute.setAttributeTypeId( ATTRIBUTETYPEID2 );
        attribute.setAttributeName( ATTRIBUTENAME2 );
        attribute.setIsPrimary( ISPRIMARY2 );
        attribute.setIsDescription( ISDESCRIPTION2 );
        AttributeHome.update( attribute , plugin );
        attributeStored = AttributeHome.findByPrimaryKey( attribute.getIdAttribute() , plugin );
        assertEquals( attributeStored.getIdAttribute() , attribute.getIdAttribute() );
        assertEquals( attributeStored.getAttributeTypeId() , attribute.getAttributeTypeId() );
        assertEquals( attributeStored.getAttributeName() , attribute.getAttributeName() );
        assertEquals( attributeStored.getIsPrimary() , attribute.getIsPrimary() );
        assertEquals( attributeStored.getIsDescription() , attribute.getIsDescription() );

        // List test
        AttributeHome.getAttributeListCombo( plugin );

        // Delete test
        AttributeHome.remove( attribute.getIdAttribute() , plugin );
        attributeStored = AttributeHome.findByPrimaryKey( attribute.getIdAttribute() , plugin );
        assertNull( attributeStored );
        
        AttributeHome.getAttributeTypeName( IDATTRIBUTE1, plugin );
        
    }
}
