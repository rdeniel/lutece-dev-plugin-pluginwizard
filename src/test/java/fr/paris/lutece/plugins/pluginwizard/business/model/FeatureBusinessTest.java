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

import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.plugin.Plugin;

public class FeatureBusinessTest extends LuteceTestCase
{
    private final static String PLUGINFEATURETITLE1 = "PluginFeatureTitle1";
    private final static String PLUGINFEATURETITLE2 = "PluginFeatureTitle2";
    private final static String PLUGINFEATURELEVEL1 = "PluginFeatureLevel1";
    private final static String PLUGINFEATURELEVEL2 = "PluginFeatureLevel2";
    private final static String PLUGINFEATURENAME1 = "PluginFeatureName1";
    private final static String PLUGINFEATURENAME2 = "PluginFeatureName2";
    private final static String PLUGINFEATUREDESCRIPTION1 = "PluginFeatureDescription1";
    private final static String PLUGINFEATUREDESCRIPTION2 = "PluginFeatureDescription2";
    private final static String PLUGINFEATURERIGHT1 = "PluginFeatureRight1";
    private final static String PLUGINFEATURERIGHT2 = "PluginFeatureRight2";
    private final static int IDPLUGIN1 = 1;
    private final static int IDPLUGIN2 = 2;

    public void testBusiness(  )
    {
        Plugin plugin = PluginService.getPlugin( "pluginwizard" );
        
        // Initialize an object
        Feature pluginFeature = new Feature();
        pluginFeature.setPluginFeatureTitle( PLUGINFEATURETITLE1 );
        pluginFeature.setPluginFeatureLevel( PLUGINFEATURELEVEL1 );
        pluginFeature.setPluginFeatureName( PLUGINFEATURENAME1 );
        pluginFeature.setPluginFeatureDescription( PLUGINFEATUREDESCRIPTION1 );
        pluginFeature.setPluginFeatureRight( PLUGINFEATURERIGHT1 );
        pluginFeature.setIdPlugin( IDPLUGIN1 );

        // Create test
        FeatureHome.create( pluginFeature , plugin );
        Feature pluginFeatureStored = FeatureHome.findByPrimaryKey( pluginFeature.getIdPluginFeature() , plugin );
        assertEquals( pluginFeatureStored.getIdPluginFeature() , pluginFeature.getIdPluginFeature() );
        assertEquals( pluginFeatureStored.getPluginFeatureTitle() , pluginFeature.getPluginFeatureTitle() );
        assertEquals( pluginFeatureStored.getPluginFeatureLevel() , pluginFeature.getPluginFeatureLevel() );
        assertEquals( pluginFeatureStored.getPluginFeatureName() , pluginFeature.getPluginFeatureName() );
        assertEquals( pluginFeatureStored.getPluginFeatureDescription() , pluginFeature.getPluginFeatureDescription() );
        assertEquals( pluginFeatureStored.getPluginFeatureRight() , pluginFeature.getPluginFeatureRight() );
        assertEquals( pluginFeatureStored.getIdPlugin() , pluginFeature.getIdPlugin() );

        // Update test
        pluginFeature.setPluginFeatureTitle( PLUGINFEATURETITLE2 );
        pluginFeature.setPluginFeatureLevel( PLUGINFEATURELEVEL2 );
        pluginFeature.setPluginFeatureName( PLUGINFEATURENAME2 );
        pluginFeature.setPluginFeatureDescription( PLUGINFEATUREDESCRIPTION2 );
        pluginFeature.setPluginFeatureRight( PLUGINFEATURERIGHT2 );
        FeatureHome.update( pluginFeature , plugin );
        pluginFeatureStored = FeatureHome.findByPrimaryKey( pluginFeature.getIdPluginFeature() , plugin );
        assertEquals( pluginFeatureStored.getIdPluginFeature() , pluginFeature.getIdPluginFeature() );
        assertEquals( pluginFeatureStored.getPluginFeatureTitle() , pluginFeature.getPluginFeatureTitle() );
        assertEquals( pluginFeatureStored.getPluginFeatureLevel() , pluginFeature.getPluginFeatureLevel() );
        assertEquals( pluginFeatureStored.getPluginFeatureName() , pluginFeature.getPluginFeatureName() );
        assertEquals( pluginFeatureStored.getPluginFeatureDescription() , pluginFeature.getPluginFeatureDescription() );
        assertEquals( pluginFeatureStored.getPluginFeatureRight() , pluginFeature.getPluginFeatureRight() );

        // List test
        FeatureHome.getAdminFeaturesForPlugin( IDPLUGIN1, plugin );

        // Delete test
        FeatureHome.remove( pluginFeature.getIdPluginFeature() , plugin );
        pluginFeatureStored = FeatureHome.findByPrimaryKey( pluginFeature.getIdPluginFeature() , plugin );
        assertNull( pluginFeatureStored );
        
    }

}