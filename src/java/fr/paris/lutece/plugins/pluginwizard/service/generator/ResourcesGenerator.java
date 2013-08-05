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

import fr.paris.lutece.plugins.pluginwizard.business.model.Attribute;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.text.MessageFormat;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * Class generated needed resource files for i18n implementation
 *
 */
public class ResourcesGenerator extends AbstractGenerator
{
    private static final String PATH = "src/java/fr/paris/lutece/plugins/{plugin_name}/resources/";
    private static String[] _languages = { "en", "fr" };
    private static String[] _prefix = { "create", "modify" };

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );

        for ( int i = 0; i < _languages.length; i++ )
        {
            String strPath = getFilePath( pm, PATH,
                    pm.getPluginName(  ).toLowerCase(  ) + "_messages_" + _languages[i] + ".properties" );

            String strSourceCode = getCode( pm, _languages[i] );
            map.put( strPath, strSourceCode );
        }

        return map;
    }

    /**
     * Build the code
     * @param pm The Plugin Model
     * @param strLanguage The language
     * @return The code
     */
    private String getCode( PluginModel pm, String strLanguage )
    {
        StringBuilder sb = new StringBuilder(  );
        generatePluginKeys( sb, pm );
        generateFeaturesKeys( sb, pm );
        generateBusinessClassKeys( sb, pm, strLanguage );

        return sb.toString(  );
    }

    /**
     * Writes in the buffer resources keys for the plugin
     * @param sb The buffer
     * @param pm The plugin model
     */
    private void generatePluginKeys( StringBuilder sb, PluginModel pm )
    {
        sb.append( "# Plugin's keys\n" );
        sb.append( "plugin.provider=" ).append( pm.getPluginProvider(  ) ).append( "\n" );
        sb.append( "plugin.description=" ).append( pm.getPluginDescription(  ) ).append( "\n" );
        sb.append( "\n" );
    }

    /**
     * Writes in the buffer resources keys for features
     * @param sb The buffer
     * @param pm The plugin model
     */
    private void generateFeaturesKeys( StringBuilder sb, PluginModel pm )
    {
        sb.append( "\n# Admin features keys\n\n" );

        for ( Feature feature : pm.getFeatures(  ) )
        {
            sb.append( "adminFeature." ).append( feature.getPluginFeatureName(  ) ).append( ".name=" )
              .append( feature.getPluginFeatureName(  ) ).append( "\n" );
            sb.append( "adminFeature." ).append( feature.getPluginFeatureName(  ) ).append( ".description=" )
              .append( feature.getPluginFeatureDescription(  ) ).append( "\n" );
        }

        sb.append( "\n" );
    }

    /**
     * Writes in the buffer resources keys for business classes
     * @param sb The buffer
     * @param pm The plugin model
     * @param strLanguage The language
     */
    private void generateBusinessClassKeys( StringBuilder sb, PluginModel pm, String strLanguage )
    {
        sb.append( "\n# Business classes keys\n\n" );

        for ( BusinessClass bc : pm.getBusinessClasses(  ) )
        {
            sb.append( "\n# keys for business classes keys : " ).append( bc.getBusinessClass(  ) ).append( "\n" );

            String strPrefix = "manage_" + bc.getBusinessClass(  ).toLowerCase(  ) + "s.";
            sb.append( strPrefix ).append( "pageTitle=" ).append( bc.getBusinessClass(  ) ).append( "\n" );
            sb.append( strPrefix ).append( "title=" )
              .append( getLabel( "title.manage", strLanguage, bc.getBusinessClass(  ) ) ).append( "\n" );
            sb.append( strPrefix ).append( "buttonAdd=" )
              .append( getLabel( "buttonAdd", strLanguage, bc.getBusinessClass(  ) ) ).append( "\n" );

            for ( Attribute attribute : bc.getAttributes(  ) )
            {
                sb.append( strPrefix ).append( "column" ).append( attribute.getName(  ) ).append( "=" )
                  .append( attribute.getLabelName(  ) ).append( "\n" );
            }

            for ( int i = 0; i < _prefix.length; i++ )
            {
                strPrefix = _prefix[i] + "_" + bc.getBusinessClass(  ).toLowerCase(  ) + ".";
                sb.append( strPrefix ).append( "pageTitle=" ).append( bc.getBusinessClass(  ) ).append( "\n" );
                sb.append( strPrefix ).append( "title=" )
                  .append( getLabel( "title." + _prefix[i], strLanguage, bc.getBusinessClass(  ) ) ).append( "\n" );

                for ( Attribute attribute : bc.getAttributes(  ) )
                {
                    sb.append( strPrefix ).append( "label" ).append( attribute.getName(  ) ).append( "=" )
                      .append( attribute.getLabelName(  ) ).append( "\n" );
                }
            }

            sb.append( "\nmessage.confirmRemove" ).append( bc.getBusinessClass(  ) ).append( "=" )
              .append( getLabel( "confirmRemove", strLanguage ) ).append( " ?\n" );

            sb.append( "\n# JSR 303 constraint validator messages\n" );
            strPrefix = "validation." + bc.getBusinessClass(  ).toLowerCase(  ) + ".";

            for ( Attribute attribute : bc.getAttributes(  ) )
            {
                if ( !attribute.getType(  ).equals( "int" ) )
                {
                    if ( attribute.getCouldNotBeEmpty(  ) )
                    {
                        sb.append( strPrefix ).append( attribute.getName(  ) ).append( ".notEmpty=" )
                          .append( getLabel( "validation.notEmpty", strLanguage, attribute.getLabelName(  ) ) )
                          .append( "\n" );
                    }

                    if ( attribute.getMaxLength(  ) > 0 )
                    {
                        sb.append( strPrefix ).append( attribute.getName(  ) ).append( ".size=" )
                          .append( getLabel( "validation.size", strLanguage, attribute.getLabelName(  ),
                                "" + attribute.getMaxLength(  ) ) ).append( "\n" );
                    }
                }
            }
        }
    }

    /**
     * Gets a label for a given language from the pluginwizard.properties file
     * @param strKey The key of the label
     * @param strLanguage The language
     * @return The value of the label
     */
    private String getLabel( String strKey, String strLanguage )
    {
        String strFullKey = "pluginwizard.label." + strKey + "." + strLanguage;

        return AppPropertiesService.getProperty( strFullKey, "Label not found for key " + strFullKey );
    }

    /**
     * Gets a label for a given language from the pluginwizard.properties file
     * @param strKey The key of the label
     * @param strLanguage The language
     * @param arguments arguments of the label
     * @return The value of the label
     */
    private String getLabel( String strKey, String strLanguage, String... arguments )
    {
        String strFullKey = "pluginwizard.label." + strKey + "." + strLanguage;
        String strLabel = AppPropertiesService.getProperty( strFullKey, "Label not found for key " + strFullKey );

        return MessageFormat.format( strLabel, (Object[]) arguments );
    }
}
