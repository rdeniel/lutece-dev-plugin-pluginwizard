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
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.LocalizationKey;
import fr.paris.lutece.plugins.pluginwizard.business.LocalizationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClassHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.FeatureHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.PortletHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.ResourceKey;
import fr.paris.lutece.plugins.pluginwizard.business.model.ResourceKeyHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;


/**
 *
 * This class is used for source code generation( sql scripts, resource files,
 * etc)
 *
 */
public final class ResourceKeyService
{
    private static final String TEMPLATE_PROPERTIES_KEYS = "/skin/plugins/pluginwizard/templates/pluginwizard_properties_keys.html";
    private static final String MARK_LIST_BUSINESS_CLASS = "list_business_class";
    private static final String MARK_PLUGIN_NAME = "plugin_name";
    private static final String MARK_LIST_FEATURES = "features";
    private static final String MARK_LIST_PORTLETS = "portlets";

    /**
     * Constructor is private
     */
    private ResourceKeyService(  )
    {
    }

    /**
     * The method will store all relevant i18n keys in the database
     *
     * @param nPluginId The id of the generated plugin
     * @param plugin The Plugin
     */
    public static void storeKeys( int nPluginId, Plugin plugin )
    {
        PluginModel pluginModel = PluginModelHome.findByPrimaryKey( nPluginId, plugin );
        ArrayList<BusinessClass> listBusinessClasses = new ArrayList<BusinessClass>(  );
        Collection<Feature> listFeatures = FeatureHome.findByPlugin( nPluginId, plugin );
        String strPluginName = pluginModel.getPluginName(  );
        localizePlugin( pluginModel );

        for ( Feature feature : listFeatures )
        {
            Collection<BusinessClass> listClassesFeature = BusinessClassHome.getBusinessClassesByFeature( feature.getIdPluginFeature(  ), plugin );
            listBusinessClasses.addAll( listClassesFeature );

            localizeFeature( strPluginName, feature );
        }

        List<String> listKeys = findResourceKeys( listBusinessClasses, strPluginName, nPluginId, plugin );

        //Method will add all the keys for the generated plugin in the database
        // ResourceKeyHome.addEmptyKeys( pluginModel.getIdPlugin(  ), listKeys, plugin );
        storeKeyList( nPluginId, pluginModel.getPluginName(  ), plugin, listKeys );
    }

    private static void storeKeyList( int nPluginId, String strPluginName, Plugin plugin, List<String> listKeys )
    {
        for ( String strKey : listKeys )
        {
            ResourceKey key = LocalizationService.localize( strKey.trim(  ), strPluginName );
            key.setIdPlugin( nPluginId );
            ResourceKeyHome.create( key, plugin );
            AppLogService.debug( key.getMarkerIdentifier(  ) + " " + key.getFrenchLocale(  ) + " " +
                key.getEnglishLocale(  ) );
        }
    }

    /**
     * Fetches all the resource keys
     *
     * @param listBusinessClasses The list of business classes
     * @param strPluginName The plugin name
     * @param plugin The plugin
     * @param nPluginId The id of the plugin to be generated
     * @return A list of the resource keys
     */
    private static List<String> findResourceKeys( List<BusinessClass> listBusinessClasses, String strPluginName,
        int nPluginId, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        Collection<Portlet> listPortlets = PortletHome.findByPlugin( nPluginId, plugin );
        Collection<Feature> listFeatures = FeatureHome.findByPlugin( nPluginId, plugin );

        model.put( MARK_LIST_PORTLETS, listPortlets );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_LIST_BUSINESS_CLASS, listBusinessClasses );
        model.put( MARK_LIST_FEATURES, listFeatures );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PROPERTIES_KEYS, Locale.getDefault(  ), model );

        StringTokenizer st = new StringTokenizer( template.getHtml(  ), "\n" );
        List<String> listKeys = new ArrayList<String>(  );

        while ( st.hasMoreTokens(  ) )
        {
            String strKey = (String) st.nextElement(  );

            if ( strKey.trim(  ).length(  ) > 2 )
            {
                listKeys.add( strKey );
            }
        }

        return listKeys;
    }

    /**
     * Creates localized keys for Plugin (description, provider)
     * @param pluginModel
     */
    private static void localizePlugin( PluginModel pluginModel )
    {
        String strKeyNameDescription = pluginModel.getPluginName(  ) + ".plugin.description";
        LocalizationKey keyDescription = new LocalizationKey(  );
        keyDescription.setKeyName( strKeyNameDescription );
        keyDescription.setEnglishLocale( pluginModel.getPluginDescription(  ) );
        keyDescription.setFrenchLocale( pluginModel.getPluginDescription(  ) );

        LocalizationKeyHome.create( keyDescription );

        String strKeyNameProvider = pluginModel.getPluginName(  ) + ".plugin.provider";
        LocalizationKey keyProvider = new LocalizationKey(  );
        keyProvider.setKeyName( strKeyNameProvider );
        keyProvider.setEnglishLocale( pluginModel.getPluginProvider(  ) );
        keyProvider.setFrenchLocale( pluginModel.getPluginProvider(  ) );
        LocalizationKeyHome.create( keyProvider );
    }

    /**
     * Creates localized keys for Admin Feature (title and description)
     * @param strPluginName The plugin's name
     * @param feature The feature
     */
    private static void localizeFeature( String strPluginName, Feature feature )
    {
        String strKeyNameTitle = strPluginName + ".adminFeature." + feature.getPluginFeatureName(  ).toLowerCase(  ) +
            ".name";
        LocalizationKey keyTitle = new LocalizationKey(  );
        keyTitle.setKeyName( strKeyNameTitle );
        keyTitle.setEnglishLocale( feature.getPluginFeatureTitle(  ) );
        keyTitle.setFrenchLocale( feature.getPluginFeatureTitle(  ) );
        LocalizationKeyHome.create( keyTitle );

        String strKeyNameDescription = strPluginName + ".adminFeature." +
            feature.getPluginFeatureName(  ).toLowerCase(  ) + ".description";
        LocalizationKey keyDescription = new LocalizationKey(  );
        keyDescription.setKeyName( strKeyNameDescription );
        keyDescription.setEnglishLocale( feature.getPluginFeatureDescription(  ) );
        keyDescription.setFrenchLocale( feature.getPluginFeatureDescription(  ) );
        LocalizationKeyHome.create( keyDescription );
    }
}
