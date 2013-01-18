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
import fr.paris.lutece.plugins.pluginwizard.business.model.Application;
import fr.paris.lutece.plugins.pluginwizard.business.model.ApplicationHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.FeatureHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.PortletHome;
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * The sql files generator
 *
 */
public class SqlCodeGenerator implements Generator
{
    private static final String TEMPLATE_DATABASE_SQL_SCRIPT = "/skin/plugins/pluginwizard/templates/pluginwizard_create_db.html";
    private static final String EXT_SQL = ".sql";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );
        int nIdPlugin = pluginModel.getIdPlugin(  );
        Collection<Feature> listFeatures = FeatureHome.findByPlugin( nIdPlugin, plugin );

        Collection<Application> listApplcations = ApplicationHome.findByPlugin( nIdPlugin, plugin );
        Collection<Portlet> listPortlets = PortletHome.findByPlugin( nIdPlugin, plugin );
        Collection<BusinessClass> listAllBusinessClasses = BusinessClassHome.getBusinessClassesByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        String strBasePath = "plugin-{plugin_name}/src/sql/plugins/{plugin_name}/";
        strBasePath = strBasePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

        for ( int i = 1; i < 6; i++ )
        {
            String strSqlFile = getSqlFileName( pluginModel.getPluginName(  ).toLowerCase(  ), i );

            String strPath = strBasePath + strSqlFile;

            String strSourceCode = getSqlScript( i, pluginModel, listAllBusinessClasses, listFeatures, listApplcations,
                    listPortlets );
            strSourceCode = strSourceCode.replace( "&lt;", "<" );
            strSourceCode = strSourceCode.replace( "&gt;", ">" );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * Returns the name of the sql file
     *
     * @param strPluginName The plugin name
     * @param nSqlType The type of sql file
     * @return the name of the sql file
     */
    private String getSqlFileName( String strPluginName, int nSqlType )
    {
        String strReturn;

        switch ( nSqlType )
        {
            case 1:
                strReturn = "plugin/create_db_" + strPluginName + EXT_SQL;

                break;

            case 2:
                strReturn = "plugin/init_db_" + strPluginName + EXT_SQL;

                break;

            case 3:
                strReturn = "plugin/init_db_" + strPluginName + "_sample" + EXT_SQL;

                break;

            case 4:
                strReturn = "core/init_core_" + strPluginName + EXT_SQL;

                break;

            case 5:
                strReturn = "core/create_" + strPluginName + "_portlet" + EXT_SQL;

                break;

            default:
                strReturn = "plugin/create_db_" + strPluginName + EXT_SQL;

                break;
        }

        return strReturn;
    }

    /**
    * Returns the necessary sql dump of creation of plugin and core
    * @param nSqlType The type of the sql
    * @param pluginModel The plugin Model
    * @param listBusinessClasses The list of business classes
    * @param listFeatures The list of admin features
    * @param listApplcations The list of XPages
    * @param listPortlets The list of portlets
    * @return The corresponding sql output
    */
    private String getSqlScript( int nSqlType, PluginModel pluginModel, Collection<BusinessClass> listBusinessClasses,
        Collection<Feature> listFeatures, Collection<Application> listApplcations,
        Collection<Portlet> listPortlets )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN, pluginModel );

        model.put( MARK_LIST_FEATURES, listFeatures );
        model.put( MARK_LIST_APPLICATIONS, listApplcations );
        model.put( MARK_LIST_PORTLETS, listPortlets );
        model.put( MARK_LIST_BUSINESS_CLASSES, listBusinessClasses );
        model.put( MARK_SQL_TYPE, nSqlType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_DATABASE_SQL_SCRIPT, Locale.getDefault(  ),
                model );

        return template.getHtml(  );
    }
}
