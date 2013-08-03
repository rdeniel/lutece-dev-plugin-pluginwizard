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
import static fr.paris.lutece.plugins.pluginwizard.service.generator.Markers.*;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * The sql files generator
 *
 */
public class SqlCodeGenerator extends AbstractGenerator
{
    private static final String PATH = "src/sql/plugins/{plugin_name}/";
    private static final String EXT_SQL = ".sql";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );

        for ( int i = 1; i < 6; i++ )
        {
            String strSqlFile = getSqlFileName( pm.getPluginName(  ).toLowerCase(  ), i );

            String strPath = getFilePath(pm, PATH, strSqlFile);

            String strSourceCode = getSqlScript( i, pm );
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
    * @param pm The plugin Model
    * @return The corresponding sql output
    */
    private String getSqlScript( int nSqlType, PluginModel pm )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN, pm );

        model.put( MARK_LIST_FEATURES, pm.getFeatures() );
        model.put( MARK_LIST_APPLICATIONS, pm.getApplications() );
        model.put( MARK_LIST_PORTLETS, pm.getPortlets() );
        model.put( MARK_LIST_BUSINESS_CLASSES, pm.getBusinessClasses() );
        model.put( MARK_SQL_TYPE, nSqlType + "" );

        HtmlTemplate template = AppTemplateService.getTemplate( getTemplate() , Locale.getDefault(  ), model );

        return template.getHtml(  );
    }
}
