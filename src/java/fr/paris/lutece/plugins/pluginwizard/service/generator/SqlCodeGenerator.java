/*
 * Copyright (c) 2002-2009, Mairie de Paris
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
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplication;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplicationHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeatureHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortletHome;
import fr.paris.lutece.plugins.pluginwizard.service.SourceCodeGenerator;
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;


/**
 *
 * The sql files generator
 *
 */
public class SqlCodeGenerator implements Visitor
{
    /**
     * Visit the path and verifies if sql files are relevant to be generated
     * @param strPath The path representing the file structure of the zip
     * @param plugin The plugin
     * @param pluginModel the representation of the created plugin
     * @return The map with the name of the file and its corresponding content
     */
    public HashMap visitPath( String strPath, Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );
        int nIdPlugin = pluginModel.getIdPlugin(  );
        Collection<PluginFeature> listFeatures = PluginFeatureHome.findByPlugin( nIdPlugin, plugin );

        Collection<PluginApplication> listApplcations = PluginApplicationHome.findByPlugin( nIdPlugin, plugin );
        Collection<PluginPortlet> listPortlets = PluginPortletHome.findByPlugin( nIdPlugin, plugin );
        Collection<BusinessClass> listAllBusinessClasses = BusinessClassHome.getBusinessClassesByPlugin( pluginModel.getIdPlugin(  ),
                plugin );
        String strOldPath = new String( strPath );
        String strBasePath = new String( strPath );

        for ( int i = 1; i < 6; i++ )
        {
            String strSqlFile = getSqlFileName( pluginModel.getPluginName(  ).toLowerCase(  ), i );

            strBasePath = strBasePath + strSqlFile;

            String strSourceCode = SourceCodeGenerator.getSqlScript( i, pluginModel, listAllBusinessClasses,
                    listFeatures, listApplcations, listPortlets );
            strSourceCode = strSourceCode.replace( "&lt;", "<" );
            strSourceCode = strSourceCode.replace( "&gt;", ">" );
            map.put( strBasePath, strSourceCode );
            strBasePath = strOldPath;
        }

        return map;
    }

    /**
    * Returns the name of the sql file
    * @param strPluginName The plugin name
    * @param nSqlType The type of sql file
    * @return the name of the sql file
    */
    private String getSqlFileName( String strPluginName, int nSqlType )
    {
        String strReturn = "";

        switch ( nSqlType )
        {
            case 1:
                strReturn = "plugin\\create_db_" + strPluginName + ".sql";

                break;

            case 2:
                strReturn = "plugin\\init_db_" + strPluginName + ".sql";

                break;

            case 3:
                strReturn = "plugin\\init_db_" + strPluginName + "_sample.sql";

                break;

            case 4:
                strReturn = "core\\init_core_" + strPluginName + ".sql";

                break;

            case 5:
                strReturn = "core\\create_" + strPluginName + "_portlet.sql";

                break;

            default:
                strReturn = "plugin\\create_db_" + strPluginName + ".sql";

                break;
        }

        return strReturn;
    }
}
