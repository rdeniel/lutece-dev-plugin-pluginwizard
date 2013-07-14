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
    private static final String TEST_JSON = "{\"pluginClass\":\"fr.paris.lutece.portal.service.plugin.PluginDefaultImplementation\",\"portlets\":[{\"pluginPortletClass\":\"fr.paris.lutece.plugins.monplugin.business.portlet.ExamplePortletHome\",\"pluginPortletCreationUrl\":\"plugins/monplugin/CreatePortletExample.jsp\",\"pluginPortletUpdateUrl\":\"plugins/monplugin/ModifyPortletExample.jsp\",\"idPlugin\":1,\"pluginPortletTypeName\":\"EXAMPLE_PORTLET\",\"id\":2}],\"pluginName\":\"monplugin\",\"idPlugin\":1,\"pluginDescription\":\"ma description\",\"pluginDocumentation\":\"\",\"pluginInstallation\":\"\",\"pluginChanges\":\"\",\"pluginUserGuide\":\"\",\"pluginVersion\":\"1.0.0\",\"pluginCopyright\":\"Copyright (c) 2013 Your Company\",\"pluginIconUrl\":\"images/admin/skin/plugins/monplugin/monplugin.png\",\"pluginProvider\":\"Mairie de Paris\",\"pluginProviderUrl\":\"http://your.web.site.com\",\"pluginDbPoolRequired\":\"1\",\"applications\":[{\"applicationName\":\"monplugin\",\"applicationClass\":\"MonpluginApp\",\"id\":1}],\"businessClasses\":[{\"idFeature\":1,\"businessTableName\":\"monplugin_personne\",\"classDescription\":\"nom\",\"primaryKey\":\"id_personne\",\"businessClass\":\"Personne\",\"id\":1,\"attributes\":[{\"attributeTypeId\":1,\"businessClassId\":1,\"isDescription\":false,\"isPrimary\":true,\"id\":2,\"type\":\"int\",\"attributeName\":\"id_personne\"},{\"attributeTypeId\":2,\"businessClassId\":1,\"isDescription\":true,\"isPrimary\":false,\"id\":3,\"type\":\"String\",\"attributeName\":\"nom\"}]}],\"features\":[{\"pluginFeatureTitle\":\"Ma Feature\",\"pluginFeatureLevel\":\"0\",\"pluginFeatureDescription\":\"Ma Feature description\",\"jspName\":null,\"pluginFeatureName\":\"ManageMonplugin\",\"pluginFeatureRight\":\"MONPLUGIN_MANAGEMENT\",\"id\":1}],\"locale\":null}";
        
    
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
