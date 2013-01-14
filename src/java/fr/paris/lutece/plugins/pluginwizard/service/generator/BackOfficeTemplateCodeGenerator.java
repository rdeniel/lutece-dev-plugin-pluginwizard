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
 * Generates Html files used as template to construct back office UI
 *
 */
public class BackOfficeTemplateCodeGenerator implements Generator
{

    private static final String TEMPLATE_HTML_BUSINESS_FILES = "/skin/plugins/pluginwizard/templates/pluginwizard_html_business_files.html";
    private static final String TEMPLATE_HTML_FEATURE_FILE = "/skin/plugins/pluginwizard/templates/pluginwizard_html_feature_file.html";
    private static String[] _template_prefix =
    {
        "create_", "modify_", "manage_"
    };

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate(Plugin plugin, PluginModel pluginModel)
    {
        HashMap map = new HashMap();

        String strBasePath = "plugin-{plugin_name}/webapp/WEB-INF/templates/admin/plugins/{plugin_name}/";
        strBasePath = strBasePath.replace("{plugin_name}", pluginModel.getPluginName());

        //for each feature,which business classes are attached to
        Collection<PluginFeature> listFeatures = PluginFeatureHome.findByPlugin(pluginModel.getIdPlugin(), plugin);
        int nPluginId = pluginModel.getIdPlugin();

        for (PluginFeature feature : listFeatures)
        {
            Collection<BusinessClass> listBusinessClasses = BusinessClassHome.getBusinessClassesByFeature(feature.getIdPluginFeature(), nPluginId, plugin);
            String strFeatureName = feature.getPluginFeatureName();

            for (BusinessClass businessClass : listBusinessClasses)
            {
                businessClass.setPluginName(pluginModel.getPluginName());

                for (int i = 0; i < _template_prefix.length; i++)
                {
                    String strPath = strBasePath + _template_prefix[i]
                            + businessClass.getBusinessClass().toLowerCase() + ".html";

                    String strSourceCode = getCreateHtmlCode(listBusinessClasses, businessClass, i + 1);
                    map.put(strPath, strSourceCode);
                }
            }
            //Add the main template where all the business management interface will be accessible
            String strPath = strBasePath + strFeatureName.toLowerCase() + ".html";

            String strSourceCode = getFeatureHtmlCode(listBusinessClasses , pluginModel.getPluginName() , feature );
            map.put(strPath, strSourceCode);
        }

        return map;
    }

    /**
     * Gets the code of a create template for a specific business object
     *
     * @param listAllBusinessClasses A list of business classes attached to
     * plugin
     * @param businessClass The instance of the business class
     * @param nTemplateType The type of template
     * @return The html code of the create template
     */
    private String getCreateHtmlCode(Collection<BusinessClass> listAllBusinessClasses,
            BusinessClass businessClass, int nTemplateType )
    {
        Map<String, Object> model = new HashMap<String, Object>();

        model.put(MARK_PLUGIN_NAME, businessClass.getBusinessPluginName());
        model.put(MARK_I18N_BRACKETS_OPEN, "@@i18n{");
        model.put(MARK_I18N_BRACKETS_CLOSE, "}");
        model.put(MARK_MACRO, "@");
        model.put(MARK_VARIABLE, "@@");
        model.put(MARK_BRACKETS_OPEN, "${");
        model.put(MARK_BRACKETS_CLOSE, "}");
        model.put(MARK_BUSINESS_CLASS, businessClass);
        model.put(MARK_LIST_BUSINESS_CLASSES, listAllBusinessClasses);

        model.put(MARK_TEMPLATE_TYPE, "" + nTemplateType);

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_HTML_BUSINESS_FILES, Locale.getDefault(), model);

        return template.getHtml().replace("@@", "#");
    }
    
    
        /**
     * Gets the code of a create template for a specific business object
     *
     * @param listAllBusinessClasses A list of business classes attached to
     * plugin
     * @return The html code of the create template
     */
    private String getFeatureHtmlCode(Collection<BusinessClass> listAllBusinessClasses , String strPluginName , PluginFeature feature )
    {
        Map<String, Object> model = new HashMap<String, Object>();

        model.put(MARK_PLUGIN_NAME, strPluginName );
        model.put(MARK_FEATURE, feature );
        model.put(MARK_I18N_BRACKETS_OPEN, "@@i18n{");
        model.put(MARK_I18N_BRACKETS_CLOSE, "}");
        model.put(MARK_MACRO, "@");
        model.put(MARK_VARIABLE, "@@");
        model.put(MARK_BRACKETS_OPEN, "${");
        model.put(MARK_BRACKETS_CLOSE, "}");
        model.put(MARK_LIST_BUSINESS_CLASSES, listAllBusinessClasses);

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_HTML_FEATURE_FILE, Locale.getDefault(), model);

        return template.getHtml().replace("@@", "#");
    }

}
