/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.service.generator;

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.MapperService;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author pierre
 */
public class GeneratorUtils
{
    private static final String TEST_JSON = "{\"locale\":\"fr\",\"idPlugin\":1,\"pluginName\":\"myplugin\",\"pluginClass\":\"\",\"pluginDescription\":\"description\",\"pluginDocumentation\":\"\",\"pluginInstallation\":\"\",\"pluginChanges\":\"\",\"pluginUserGuide\":\"\",\"pluginVersion\":\"1.0.0\",\"pluginCopyright\":\"\",\"pluginIconUrl\":\"\",\"pluginProvider\":\"provider\",\"pluginProviderUrl\":\"provider url\",\"pluginDbPoolRequired\":1,\"pluginApplications\":[],\"pluginFeatures\":[],\"pluginPortlets\":[]}";
        
    
    public static PluginModel getTestModel()
    {
        return MapperService.readJson( TEST_JSON );
    }

    static void outputMap(Map map )
    {
        for (Iterator it = map.keySet().iterator(); it.hasNext();)
        {
            String strKey = (String) it.next();
            System.out.println( "######################### file : " + strKey + "#########################");
            System.out.println( map.get( strKey ));
        }
    }

}
