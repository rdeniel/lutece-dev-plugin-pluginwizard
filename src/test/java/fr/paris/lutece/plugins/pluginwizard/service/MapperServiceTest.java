/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author pierre
 */
public class MapperServiceTest extends TestCase
{
    private static final String VERSION = "1.0.0";


    /**
     * Test of getJson method, of class MapperService.
     */
    @Test
    public void testGetJson()
    {
        System.out.println("getJson");
        PluginModel model = new PluginModel();
        model.setIdPlugin( 1 );
        model.setPluginName( "myplugin");
        model.setPluginProvider( "provider");
        model.setPluginProviderUrl("provider url");
        model.setPluginVersion( VERSION );
        
        String result = MapperService.getJson(model);
        System.out.println( result );
    }
    
    public void testReadJson()
    {
        String strJson = "{\"locale\":null,\"idPlugin\":1,\"pluginName\":\"myplugin\",\"pluginClass\":null,\"pluginDescription\":null,\"pluginDocumentation\":null,\"pluginInstallation\":null,\"pluginChanges\":null,\"pluginUserGuide\":null,\"pluginVersion\":\"1.0.0\",\"pluginCopyright\":null,\"pluginIconUrl\":null,\"pluginProvider\":\"provider\",\"pluginProviderUrl\":\"provider url\",\"pluginDbPoolRequired\":null,\"pluginApplications\":null,\"pluginFeatures\":null,\"pluginPortlets\":null}";
        PluginModel model = MapperService.readJson( strJson );
        assertEquals(model.getPluginVersion(), VERSION );
    }
}