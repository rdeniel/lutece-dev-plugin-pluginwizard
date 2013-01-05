/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.user.UserChoice;
import fr.paris.lutece.plugins.pluginwizard.service.generator.BackOfficeJspGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.BackOfficeTemplateCodeGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.BusinessClassCodeGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.JspBeanCodeGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PluginXmlGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PomGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PortletGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PortletJspBeanGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PortletJspFilesGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PortletTemplateGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PortletXslGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.PropertiesGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.ResourcesCodeGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.SpringContextXmlGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.SqlCodeGenerator;
import fr.paris.lutece.plugins.pluginwizard.service.generator.XPageGenerator;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The zip service will pack all the chosen files and create an archive
 */
public class PluginWizardZipService
{
    private static PluginWizardZipService _singleton;
    private static final String PARAM_PLUGIN_ID = "plugin_id";

    /**
     * Gets the unique instance of the PluginWizardZipService
     * @return The unique instance of the PluginWizardZipService
     */
    public static synchronized PluginWizardZipService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new PluginWizardZipService(  );
        }

        return _singleton;
    }

    /**
     * Exports the files in a byte array
     * @param request The Http request
     * @return An array of byte which is the content of the archive
     */
    public byte[] exportZip( HttpServletRequest request )
    {
        ZipOutputStream zipOutputStream = null;
        ByteArrayOutputStream fos = new ByteArrayOutputStream(  );

        //fetch the userChoice
        HttpSession session = request.getSession(  );
        UserChoice choice = (UserChoice) session.getAttribute( "userChoice" );

        byte[] buffer = new byte[1024];

        zipOutputStream = new ZipOutputStream( fos );
        zipOutputStream.setLevel( 9 );

        HashMap<String, String> globalMap = new HashMap(  );
        String strPluginId = request.getParameter( PARAM_PLUGIN_ID );
        int nPluginId = Integer.parseInt( strPluginId );
        Plugin plugin = PluginService.getPlugin( "pluginwizard" );
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        AppLogService.error( choice );

        if ( ( choice == null ) ||
                !( choice.getBusinessClasses(  ) && choice.getSqlFiles(  ) && choice.getJspBean(  ) &&
                choice.getXpages(  ) && choice.getBackOfficeTemplate(  ) && choice.getResourceFiles(  ) &&
                choice.getBackOfficeJsp(  ) && choice.getPluginPropertiesFile(  ) && choice.getMavenPomXml(  ) &&
                choice.getPluginXmlDefinition(  ) && choice.getSpringContextXml(  ) ) )
        {
            choice = new UserChoice(  );
            choice.setBusinessClasses( true );
            choice.setJspBean( true );
            choice.setSqlFiles( true );
            choice.setBackOfficeTemplate( true );
            choice.setResourceFiles( true );
            choice.setBackOfficeJsp( true );
            choice.setPluginPropertiesFile( true );
            choice.setPluginXmlDefinition( true );
            choice.setMavenPomXml( true );
            choice.setSpringContextXml( true );
            choice.setXpages( true );
        }

        try
        {
            if ( choice.getBusinessClasses(  ) )
            {
                // Add the business classes to the reference map
                String strPortletBusinessPath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/business/portlet/";
                strPortletBusinessPath = strPortletBusinessPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PortletGenerator portletGenerator = new PortletGenerator(  );
                HashMap mapPortetFiles = portletGenerator.visitPath( strPortletBusinessPath, plugin, pluginModel );
                globalMap.putAll( mapPortetFiles );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getBusinessClasses(  ) )
            {
                // Add the business classes to the reference map
                String strBusinessPath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/business/";
                strBusinessPath = strBusinessPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                BusinessClassCodeGenerator businessClassGenerator = new BusinessClassCodeGenerator(  );
                HashMap mapBusinessClasses = businessClassGenerator.visitPath( strBusinessPath, plugin, pluginModel );
                globalMap.putAll( mapBusinessClasses );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getSqlFiles(  ) )
            {
                //Add Sql Files
                String strSqlPath = "plugin-{plugin_name}/src/sql/plugins/{plugin_name}/";
                strSqlPath = strSqlPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                SqlCodeGenerator sqlCodeGenerator = new SqlCodeGenerator(  );
                HashMap mapSqlScripts = sqlCodeGenerator.visitPath( strSqlPath, plugin, pluginModel );
                globalMap.putAll( mapSqlScripts );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getJspBean(  ) )
            {
                //Add the Jsp Bean
                String strJspBeanPath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/web/";
                strJspBeanPath = strJspBeanPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                JspBeanCodeGenerator jspBeanCodeGenerator = new JspBeanCodeGenerator(  );
                HashMap mapJspBean = jspBeanCodeGenerator.visitPath( strJspBeanPath, plugin, pluginModel );
                globalMap.putAll( mapJspBean );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getXpages(  ) )
            {
                //Add the XPage
                String strXPagePath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/web/";
                strXPagePath = strXPagePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                XPageGenerator xPageGenerator = new XPageGenerator(  );
                HashMap mapXPage = xPageGenerator.visitPath( strXPagePath, plugin, pluginModel );
                globalMap.putAll( mapXPage );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getXpages(  ) )
            {
                //Add the Portlet JspBean
                String strXPagePath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/web/";
                strXPagePath = strXPagePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PortletJspBeanGenerator portletJspBean = new PortletJspBeanGenerator(  );
                HashMap mapXPage = portletJspBean.visitPath( strXPagePath, plugin, pluginModel );
                globalMap.putAll( mapXPage );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getBackOfficeTemplate(  ) )
            {
                //Add the back office templates
                String strBackTemplatesPath = "plugin-{plugin_name}/webapp/WEB-INF/templates/admin/plugins/{plugin_name}/";
                strBackTemplatesPath = strBackTemplatesPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                BackOfficeTemplateCodeGenerator backOfficeTemplateCodeGenerator = new BackOfficeTemplateCodeGenerator(  );

                HashMap mapBackTemplates = backOfficeTemplateCodeGenerator.visitPath( strBackTemplatesPath, plugin,
                        pluginModel );
                globalMap.putAll( mapBackTemplates );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getResourceFiles(  ) )
            {
                //Add the resources files
                String strResourcesPath = "plugin-{plugin_name}/src/java/fr/paris/lutece/plugins/{plugin_name}/resources/";
                strResourcesPath = strResourcesPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                ResourcesCodeGenerator resourcesCodeGenerator = new ResourcesCodeGenerator(  );
                HashMap mapResources = resourcesCodeGenerator.visitPath( strResourcesPath, plugin, pluginModel );
                globalMap.putAll( mapResources );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getBackOfficeJsp(  ) )
            {
                //Add the back office jsps
                String strBackOfficeJspPath = "plugin-{plugin_name}/webapp/jsp/admin/plugins/{plugin_name}/";
                strBackOfficeJspPath = strBackOfficeJspPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                BackOfficeJspGenerator backOfficeJspGenerator = new BackOfficeJspGenerator(  );
                HashMap mapBackOfficeJsp = backOfficeJspGenerator.visitPath( strBackOfficeJspPath, plugin, pluginModel );
                globalMap.putAll( mapBackOfficeJsp );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getBackOfficeJsp(  ) )
            {
                //Add the back office jsps
                String strBackOfficeJspPath = "plugin-{plugin_name}/webapp/jsp/admin/plugins/{plugin_name}/";
                strBackOfficeJspPath = strBackOfficeJspPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PortletJspFilesGenerator portletJspGenerator = new PortletJspFilesGenerator(  );
                HashMap mapBackOfficeJsp = portletJspGenerator.visitPath( strBackOfficeJspPath, plugin, pluginModel );
                globalMap.putAll( mapBackOfficeJsp );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getPluginPropertiesFile(  ) )
            {
                //Add the plugin properties file
                String strPropertiesPath = "plugin-{plugin_name}/webapp/WEB-INF/conf/plugins/";
                strPropertiesPath = strPropertiesPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PropertiesGenerator propertiesGenerator = new PropertiesGenerator(  );
                HashMap mapPropertiesFile = propertiesGenerator.visitPath( strPropertiesPath, plugin, pluginModel );
                globalMap.putAll( mapPropertiesFile );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getMavenPomXml(  ) )
            {
                //Add the maven goal of the plugin
                String strMavenGoalPath = "plugin-{plugin_name}";
                strMavenGoalPath = strMavenGoalPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PomGenerator pomGenerator = new PomGenerator(  );
                HashMap mapPomXml = pomGenerator.visitPath( strMavenGoalPath, plugin, pluginModel );
                globalMap.putAll( mapPomXml );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getPluginXmlDefinition(  ) )
            {
                //Add the xml definition of the plugin
                String strPluginXmlPath = "plugin-{plugin_name}/webapp/WEB-INF/plugins/";
                strPluginXmlPath = strPluginXmlPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PluginXmlGenerator pluginXmlGenerator = new PluginXmlGenerator(  );
                HashMap mapPluginXml = pluginXmlGenerator.visitPath( strPluginXmlPath, plugin, pluginModel );
                globalMap.putAll( mapPluginXml );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getSpringContextXml(  ) )
            {
                //Add the spring context of the plugin
                String strSpringContextPath = "plugin-{plugin_name}/webapp/WEB-INF/conf/plugins/";
                strSpringContextPath = strSpringContextPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                SpringContextXmlGenerator springContextGenerator = new SpringContextXmlGenerator(  );
                HashMap mapSpringContext = springContextGenerator.visitPath( strSpringContextPath, plugin, pluginModel );
                globalMap.putAll( mapSpringContext );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getBusinessClasses(  ) )
            {
                //Add the portlet template files
                String strPortletTemplatePath = "plugin-{plugin_name}/webapp/WEB-INF/templates/admin/plugins/{plugin_name}/portlet/";
                strPortletTemplatePath = strPortletTemplatePath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PortletTemplateGenerator portletTemplateGenerator = new PortletTemplateGenerator(  );
                HashMap mapSpringContext = portletTemplateGenerator.visitPath( strPortletTemplatePath, plugin,
                        pluginModel );
                globalMap.putAll( mapSpringContext );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            if ( choice.getBusinessClasses(  ) )
            {
                //Add the portlet template files
                String strPortletXslPath = "plugin-{plugin_name}/webapp/WEB-INF/xsl/normal/";
                strPortletXslPath = strPortletXslPath.replace( "{plugin_name}", pluginModel.getPluginName(  ) );

                PortletXslGenerator portletXslGenerator = new PortletXslGenerator(  );
                HashMap mapSpringContext = portletXslGenerator.visitPath( strPortletXslPath, plugin, pluginModel );
                globalMap.putAll( mapSpringContext );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        try
        {
            for ( Map.Entry<String, String> sourceFile : globalMap.entrySet(  ) )
            {
                StringBuffer sb = new StringBuffer( sourceFile.getValue(  ) );
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream( sb.toString(  ).getBytes( "UTF-8" ) );
                zipOutputStream.putNextEntry( new ZipEntry( sourceFile.getKey(  ) ) );

                int nLength;

                while ( ( nLength = byteArrayInputStream.read( buffer ) ) > 0 )
                {
                    zipOutputStream.write( buffer, 0, nLength );
                }

                zipOutputStream.closeEntry(  );
                byteArrayInputStream.close(  );
            }

            zipOutputStream.finish(  );
            zipOutputStream.close(  );
        }
        catch ( Exception e )
        {
            AppLogService.error( e );
        }

        return fos.toByteArray(  );
    }
}
