/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.util.Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * The business classes representing the business layer of the plugin is generated
 *
 */
public class BusinessClassGenerator extends AbstractGenerator
{
    private static final String PATH = "SOURCE/java/fr/paris/lutece/plugins/";
    private static final String PATH_SUFFIX = "/business/";
    private List<BusinessFileConfig> _listFiles;

    /**
     * Set the list of files config
     * @param listFiles The list of files
     */
    public void setFiles( List<BusinessFileConfig> listFiles )
    {
        _listFiles = listFiles;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, String> generate( PluginModel pm )
    {
        HashMap<String, String> map = new HashMap<String, String>(  );
        Collection<BusinessClass> listAllBusinessClasses = pm.getBusinessClasses(  );
        
        String strPluginName = pm.getPluginNameAsPackage( ) ;
        String strRadicalPackage = pm.getPluginNameAsRadicalPackage( ) ;
        String strRadicalPath = pm.getPluginNameAsRadicalPath();
        
        
        for ( BusinessClass businessClass : listAllBusinessClasses )
        {
            for ( BusinessFileConfig file : _listFiles )
            {
                String strClassName = file.getPrefix(  ) + businessClass.getBusinessClass(  ) + file.getSuffix(  );
                String strFilename = strClassName + ".java";
                String strSourceCode = getSourceCode( strPluginName, businessClass, file.getTemplate(  ), strRadicalPackage, pm.getPluginName( ) );
                strSourceCode = strSourceCode.replace( "&lt;", "<" );
                strSourceCode = strSourceCode.replace( "&gt;", ">" );
                strSourceCode = strSourceCode.replace( "@i18n", "#i18n" );

                String strPath = PATH.replace( "SOURCE", file.getSourcePath(  ) ) + strRadicalPath + PATH_SUFFIX;
                
                map.put( getFilePath( pm, strPath, strFilename ), strSourceCode );
            }
        }

        return map;
    }

    /**
     * Returns the source code of a business object
     * @param strPluginName  The plugin name
     * @param businessClass The business class
     * @param strTemplate The type of generation(DAO,Home,etc)
     * @return The java source code of the business object
     */
    private String getSourceCode( String strPluginName, BusinessClass businessClass, String strTemplate, String strRadicalPackage, String strBeanName )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( Markers.MARK_BUSINESS_CLASS, businessClass );
        model.put( Markers.MARK_PLUGIN_NAME, strPluginName );
        model.put(Markers.MARK_RADICAL_PACKAGE, strRadicalPackage);
        model.put(Markers.MARK_BEAN_NAME, strBeanName);

        return build( strTemplate, model );
    }
}
