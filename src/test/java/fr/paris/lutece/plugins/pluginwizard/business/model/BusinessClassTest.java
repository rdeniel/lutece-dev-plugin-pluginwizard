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
public class BusinessClassTest extends LuteceTestCase
{
    private final static int IDBUSINESSCLASS1 = 1;
    private final static int IDBUSINESSCLASS2 = 2;
    private final static String BUSINESSCLASS1 = "BusinessClass1";
    private final static String BUSINESSCLASS2 = "BusinessClass2";
    private final static String BUSINESSTABLENAME1 = "BusinessTableName1";
    private final static String BUSINESSTABLENAME2 = "BusinessTableName2";
    private final static int IDPLUGINFEATURE1 = 1;
    private final static int IDPLUGINFEATURE2 = 2;

    public void testBusiness(  )
    {
       Plugin plugin = PluginService.getPlugin( "pluginwizard" );
        
        // Initialize an object
        BusinessClass businessClass = new BusinessClass();
        businessClass.setIdBusinessClass( IDBUSINESSCLASS1 );
        businessClass.setBusinessClass( BUSINESSCLASS1 );
        businessClass.setBusinessTableName( BUSINESSTABLENAME1 );
        businessClass.setIdFeature( IDPLUGINFEATURE1 );

        // Create test
        BusinessClassHome.create( businessClass , plugin );
        BusinessClass businessClassStored = BusinessClassHome.findByPrimaryKey( businessClass.getIdBusinessClass() , plugin );
        assertEquals( businessClassStored.getIdBusinessClass() , businessClass.getIdBusinessClass() );
        assertEquals( businessClassStored.getBusinessClass() , businessClass.getBusinessClass() );
        assertEquals( businessClassStored.getBusinessTableName() , businessClass.getBusinessTableName() );
        assertEquals( businessClassStored.getIdFeature() , businessClass.getIdFeature() );

        // Update test
        businessClass.setIdBusinessClass( IDBUSINESSCLASS2 );
        businessClass.setBusinessClass( BUSINESSCLASS2 );
        businessClass.setBusinessTableName( BUSINESSTABLENAME2 );
        BusinessClassHome.update( businessClass , plugin );
        businessClassStored = BusinessClassHome.findByPrimaryKey( businessClass.getIdBusinessClass() , plugin );
        assertEquals( businessClassStored.getIdBusinessClass() , businessClass.getIdBusinessClass() );
        assertEquals( businessClassStored.getBusinessClass() , businessClass.getBusinessClass() );
        assertEquals( businessClassStored.getBusinessTableName() , businessClass.getBusinessTableName() );

        // List test
        BusinessClassHome.getBusinessClassesByFeature( IDPLUGINFEATURE1, plugin );

        // Delete test
        BusinessClassHome.remove( businessClass.getIdBusinessClass() , plugin );
        businessClassStored = BusinessClassHome.findByPrimaryKey( businessClass.getIdBusinessClass() , plugin );
        assertNull( businessClassStored );
        
    }
}