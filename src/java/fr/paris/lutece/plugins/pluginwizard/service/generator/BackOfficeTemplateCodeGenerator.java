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

import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClassHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeatureHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.SourceCodeGenerator;
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * Generates Html files used as template to construct back office UI
 *
 */
public class BackOfficeTemplateCodeGenerator implements Generator
{
    /**
     * Visits the path and verifies if Back office tempklate is relevant
     * @param plugin The plugin
     * @param pluginModel the representation of the created plugin
     * @return The map with the name of the file and its corresponding content
     */
    @Override
    public Map generate( Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );

        Collection<BusinessClass> listAllBusinessClasses = new ArrayList<BusinessClass>(  );

        String strBasePath = "plugin-{plugin_name}/webapp/WEB-INF/templates/admin/plugins/{plugin_name}/";
        strBasePath = strBasePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

        //for each feature,which business classes are attached to
        Collection<PluginFeature> listFeatures = PluginFeatureHome.findByPlugin( pluginModel.getIdPlugin(  ), plugin );
        int nPluginId = pluginModel.getIdPlugin(  );

        for ( PluginFeature feature : listFeatures )
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ),
                    nPluginId, plugin );
            listAllBusinessClasses.addAll( listBusinessClasses );
        }

        for ( BusinessClass businessClass : listAllBusinessClasses )
        {
            businessClass.setPluginName( pluginModel.getPluginName(  ) );

            for ( int i = 1; i < 4; i++ )
            {
                String strPath = strBasePath + getTemplatePrefix( i ) +
                    businessClass.getBusinessClass(  ).toLowerCase(  ) + ".html";

                String strSourceCode = SourceCodeGenerator.getCreateHtmlCode( listAllBusinessClasses, businessClass, i,
                        plugin );
                map.put( strPath, strSourceCode );
            }

            //Add the main template where all the business management interface will be accessible
            String strPath = strBasePath + "/" + getTemplatePrefix( 4 ) + pluginModel.getPluginName(  ) + "s.html";

            String strSourceCode = SourceCodeGenerator.getCreateHtmlCode( listAllBusinessClasses, businessClass, 4,
                    plugin );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * The method will return the prefix needed for naming of the template
     * @param nTemplateType The template type
     * @return The prefix needed for the naming of the template
     */
    private static String getTemplatePrefix( int nTemplateType )
    {
        String strReturn;

        switch ( nTemplateType )
        {
            case 1:
                strReturn = "create_";

                break;

            case 2:
                strReturn = "modify_";

                break;

            case 3:
                strReturn = "manage_";

                break;

            case 4:
                strReturn = "manage_";

                break;

            default:
                strReturn = "";

                break;
        }

        return strReturn;
    }
}
