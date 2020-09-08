/*
 * Copyright (c) 2002-2020, City of Paris
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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * The sql files generator
 *
 */
public class SqlCodeGenerator extends AbstractGenerator
{
    private static final String PATH = "src/sql/plugins/{plugin_name}/";
    private static String [ ] _prefix = {
            "plugin/create_db_", "plugin/init_db_", "plugin/init_db_", "core/init_core_", "core/create_"
    };
    private static String [ ] _suffix = {
            ".sql", ".sql", "_sample.sql", ".sql", "_portlet.sql"
    };

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, String> generate( PluginModel pm, String generationSchemeName )
    {
        HashMap<String, String> map = new HashMap<>( );

        for ( int i = 0; i < _prefix.length; i++ )
        {
            String strSqlFile = getSqlFileName( pm.getPluginName( ).toLowerCase( ), i );

            String strPath = getFilePath( pm, PATH, strSqlFile );

            String strSourceCode = getSqlScript( pm, i );
            strSourceCode = strSourceCode.replace( "&lt;", "<" );
            strSourceCode = strSourceCode.replace( "&gt;", ">" );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * Returns the name of the sql file
     *
     * @param strPluginName
     *            The plugin name
     * @param nSqlType
     *            The type of sql file
     * @return the name of the sql file
     */
    private String getSqlFileName( String strPluginName, int nSqlType )
    {
        return _prefix [nSqlType] + strPluginName + _suffix [nSqlType];
    }

    /**
     * Returns the necessary sql dump of creation of plugin and core
     * 
     * @param nSqlType
     *            The type of the sql
     * @param pm
     *            The plugin Model
     * @return The corresponding sql output
     */
    private String getSqlScript( PluginModel pm, int nSqlType )
    {
        Map<String, Object> model = new HashMap<>( );

        PluginXmlGenerator.setJspName( pm );
        model.put( Markers.MARK_PLUGIN, pm );

        model.put( Markers.MARK_SQL_TYPE, nSqlType );

        return build( model );
    }
}
