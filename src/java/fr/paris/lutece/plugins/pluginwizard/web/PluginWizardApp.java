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
package fr.paris.lutece.plugins.pluginwizard.web;

import fr.paris.lutece.plugins.mvc.MVCApplication;
import fr.paris.lutece.plugins.mvc.annotations.Action;
import fr.paris.lutece.plugins.mvc.annotations.Controller;
import fr.paris.lutece.plugins.mvc.annotations.View;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKey;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.ModelHome;
import fr.paris.lutece.plugins.pluginwizard.web.formbean.FormName;
import fr.paris.lutece.plugins.pluginwizard.business.model.Application;
import fr.paris.lutece.plugins.pluginwizard.business.model.Attribute;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.plugins.pluginwizard.service.ModelService;
import fr.paris.lutece.plugins.pluginwizard.service.generator.GeneratorService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * The class manage pluginwizard Page
 */
@Controller(xpageName = "pluginwizard", pagePath = "pluginwizard.pagePath", pageTitle = "pluginwizard.pageTitle")
public class PluginWizardApp extends MVCApplication
{
    //Constants

    private static final String MARK_PLUGIN_ID = "plugin_id";
    private static final String MARK_PLUGIN_NAME = "plugin_name";
    private static final String MARK_PLUGIN_MODEL = "plugin_model";
    //Management Bookmarks
    private static final String MARK_PLUGIN_PORTLETS = "plugin_portlets";
    private static final String MARK_PLUGIN_APPLICATIONS = "plugin_applications";
    private static final String MARK_ADMIN_FEATURES = "admin_features";
    private static final String MARK_BUSINESS_CLASSES = "business_classes";
    private static final String MARK_BUSINESS_CLASS = "business_class";
    private static final String MARK_BUSINESS_CLASS_ID = "business_class_id";
    private static final String MARK_ADMIN_FEATURES_COMBO = "combo_admin_features";
    private static final String MARK_ATTRIBUTE_TYPE_COMBO = "combo_attribute_type";
    private static final String MARK_SCHEMES_COMBO = "combo_schemes";
    //Modification bookmarks
    private static final String MARK_FEATURE = "feature";
    private static final String MARK_ATTRIBUTE = "attribute";
    private static final String MARK_APPLICATION = "application";
    private static final String MARK_PLUGIN_PORTLET = "portlet";
    private static final String TEMPLATE_CREATE_PLUGIN = "/skin/plugins/pluginwizard/pluginwizard_create_plugin.html";
    private static final String TEMPLATE_PLUGIN_EXISTS = "/skin/plugins/pluginwizard/pluginwizard_plugin_exists.html";
    private static final String TEMPLATE_CREATE_PLUGIN_DESCRIPTION = "/skin/plugins/pluginwizard/pluginwizard_create_plugin_description.html";
    private static final String TEMPLATE_MODIFY_PLUGIN_DESCRIPTION = "/skin/plugins/pluginwizard/pluginwizard_modify_plugin_description.html";
    private static final String TEMPLATE_MODIFY_PLUGIN = "/skin/plugins/pluginwizard/pluginwizard_modify_plugin.html";
    private static final String TEMPLATE_MODIFY_BUSINESS_CLASS = "/skin/plugins/pluginwizard/pluginwizard_modify_business_class.html";
    private static final String TEMPLATE_MANAGE_ADMIN_FEATURES = "/skin/plugins/pluginwizard/pluginwizard_manage_admin_features.html";
    private static final String TEMPLATE_MANAGE_PLUGIN_PORTLETS = "/skin/plugins/pluginwizard/pluginwizard_manage_portlets.html";
    private static final String TEMPLATE_MANAGE_PLUGIN_APPLICATIONS = "/skin/plugins/pluginwizard/pluginwizard_manage_applications.html";
    private static final String TEMPLATE_MANAGE_BUSINESS_CLASSES = "/skin/plugins/pluginwizard/pluginwizard_manage_business_classes.html";
    private static final String TEMPLATE_GET_RECAPITULATE = "/skin/plugins/pluginwizard/pluginwizard_plugin_recapitulate.html";
    //CREATE
    private static final String TEMPLATE_CREATE_ADMIN_FEATURE = "/skin/plugins/pluginwizard/pluginwizard_create_admin_feature.html";
    private static final String TEMPLATE_CREATE_PLUGIN_PORTLET = "/skin/plugins/pluginwizard/pluginwizard_create_portlet.html";
    private static final String TEMPLATE_CREATE_PLUGIN_APPLICATION = "/skin/plugins/pluginwizard/pluginwizard_create_application.html";
    private static final String TEMPLATE_CREATE_BUSINESS_CLASS = "/skin/plugins/pluginwizard/pluginwizard_create_business_class.html";
    private static final String TEMPLATE_CREATE_ATTRIBUTE = "/skin/plugins/pluginwizard/pluginwizard_create_attribute.html";
    //MODIFY
    private static final String TEMPLATE_MODIFY_ATTRIBUTE = "/skin/plugins/pluginwizard/pluginwizard_modify_attribute.html";
    private static final String TEMPLATE_MODIFY_ADMIN_FEATURE = "/skin/plugins/pluginwizard/pluginwizard_modify_admin_feature.html";
    private static final String TEMPLATE_MODIFY_PLUGIN_PORTLET = "/skin/plugins/pluginwizard/pluginwizard_modify_portlet.html";
    private static final String TEMPLATE_MODIFY_PLUGIN_APPLICATION = "/skin/plugins/pluginwizard/pluginwizard_modify_application.html";
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_PLUGIN_COPYRIGHT = "plugin_copyright";
    private static final String PARAM_BUSINESS_CLASS_ID = "business_class_id";
    private static final String PARAM_ATTRIBUTE_ID = "attribute_id";
    //Admin feature
    private static final String PARAM_FEATURE_ID = "feature_id";
    //Application
    private static final String PARAM_APPLICATION_ID = "application_id";
    //Portlet
    private static final String PARAM_PORTLET_ID = "portlet_id";
    // Views
    private static final String VIEW_CREATE_PLUGIN = "createPlugin";
    private static final String VIEW_MODIFY_PLUGIN = "modifyPlugin";
    private static final String VIEW_PLUGIN_EXISTS = "pluginExists";
    private static final String VIEW_CREATE_DESCRIPTION = "createDescription";
    private static final String VIEW_MODIFY_DESCRIPTION = "modifyDescription";
    private static final String VIEW_CREATE_ATTRIBUTE = "createAttribute";
    private static final String VIEW_MODIFY_ATTRIBUTE = "modifyAttribute";
    private static final String VIEW_MANAGE_ADMIN_FEATURES = "manageAdminFeatures";
    private static final String VIEW_CREATE_ADMIN_FEATURE = "createAdminFeature";
    private static final String VIEW_MODIFY_ADMIN_FEATURE = "modifyAdminFeature";
    private static final String VIEW_MANAGE_BUSINESS_CLASSES = "manageBusinessClasses";
    private static final String VIEW_CREATE_BUSINESS_CLASS = "createBusinessClass";
    private static final String VIEW_MODIFY_BUSINESS_CLASS = "modifyBusinessClass";
    private static final String VIEW_MANAGE_APPLICATIONS = "manageApplications";
    private static final String VIEW_CREATE_APPLICATION = "createApplication";
    private static final String VIEW_MODIFY_APPLICATION = "modifyApplication";
    private static final String VIEW_MANAGE_PORTLETS = "managePortlets";
    private static final String VIEW_CREATE_PORTLET = "createPortlet";
    private static final String VIEW_MODIFY_PORTLET = "modifyPortlet";
    private static final String VIEW_RECAPITULATE = "recapitulate";

    //Plugin
    private static final String ACTION_CREATE_PLUGIN = "createPlugin";
    private static final String ACTION_DESCRIPTION_PREVIOUS = "descriptionPrevious";
    private static final String ACTION_DESCRIPTION_NEXT = "descriptionNext";
    private static final String ACTION_CREATE_ATTRIBUTE = "create_attribute";
    private static final String ACTION_MODIFY_ATTRIBUTE = "modify_attribute";
    private static final String ACTION_RESET_DATA = "resetData";
    
    //CREATE ACTIONS
    private static final String ACTION_CREATE_ADMIN_FEATURE = "create_admin_feature";
    private static final String ACTION_CREATE_PORTLET = "create_plugin_portlet";
    private static final String ACTION_CREATE_APPLICATION = "create_plugin_application";
    private static final String ACTION_CREATE_BUSINESS_CLASS = "create_business_class";
    //MODIFY_ACTIONS
    private static final String ACTION_MODIFY_ADMIN_FEATURE = "modify_admin_feature";
    private static final String ACTION_MODIFY_PORTLET = "modify_portlet";
    private static final String ACTION_MODIFY_APPLICATION = "modify_plugin_application";
    private static final String ACTION_MODIFY_BUSINESS_CLASS = "modify_business_class";
    //REMOVE_ACTIONS
    private static final String ACTION_REMOVE_ADMIN_FEATURE = "remove_admin_feature";
    private static final String ACTION_REMOVE_BUSINESS_ATTRIBUTE = "remove_attribute";
    private static final String ACTION_REMOVE_PLUGIN_PORTLET = "remove_plugin_portlet";
    private static final String ACTION_REMOVE_APPLICATION = "remove_plugin_application";
    private static final String ACTION_REMOVE_BUSINESS_CLASS = "remove_business_class";
    //REMOVE ACTION
    private static final String ACTION_DO_REMOVE_FEATURE = "do_remove_feature";
    private static final String ACTION_DO_REMOVE_PLUGIN_APPLICATION = "do_remove_application";
    private static final String ACTION_DO_REMOVE_PLUGIN_PORTLET = "do_remove_portlet";
    private static final String ACTION_DO_REMOVE_BUSINESS_CLASS = "do_remove_class";
    private static final String ACTION_DO_REMOVE_BUSINESS_ATTRIBUTE = "do_remove_attribute";
    //Recapitulate
    private static final String PROPERTY_PAGE_TITLE = "pluginwizard.pageTitle";
    private static final String PROPERTY_PAGE_PATH_LABEL = "pluginwizard.pagePathLabel";
    private static final String JSP_PAGE_PORTAL = "jsp/site/Portal.jsp";
    //Front Messages
    private static final String PROPERTY_CONFIRM_REMOVE_FEATURE_TITLE_MESSAGE = "pluginwizard.siteMessage.confirmRemoveFeature.title";
    private static final String PROPERTY_CONFIRM_REMOVE_FEATURE_ALERT_MESSAGE = "pluginwizard.siteMessage.confirmRemoveFeature.alertMessage";
    private static final String PROPERTY_CONFIRM_REMOVE_PORTLET_TITLE_MESSAGE = "pluginwizard.siteMessage.confirmRemovePortlet.title";
    private static final String PROPERTY_CONFIRM_REMOVE_PORTLET_ALERT_MESSAGE = "pluginwizard.siteMessage.confirmRemovePortlet.alertMessage";
    private static final String PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_ALERT_MESSAGE = "pluginwizard.siteMessage.confirmRemoveAttribute.alertMessage";
    private static final String PROPERTY_CONFIRM_REMOVE_APPLICATION_ALERT_MESSAGE = "pluginwizard.siteMessage.confirmRemoveApplication.title";
    private static final String PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_ALERT_MESSAGE = "pluginwizard.siteMessage.confirmRemoveBusinessClass.title";
    private static final String PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_TITLE_MESSAGE = "pluginwizard.siteMessage.confirmRemoveBusinessAttribute.title";
    private static final String PROPERTY_CONFIRM_REMOVE_APPLICATION_TITLE_MESSAGE = "pluginwizard.siteMessage.confirmRemoveApplication.alertMessage";
    private static final String PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_TITLE_MESSAGE = "pluginwizard.siteMessage.confirmRemoveBusinessClass.alertMessage";
    private static final String PROPERTY_PLUGIN_NAME = "pluginwizard";
    private static final String PROPERTY_FEATURE_NOT_DEFINE_TITLE_MESSAGE = "pluginwizard.siteMessage.featureNotExists.alertMessage";
    private static final String PROPERTY_CLASS_NOT_DEFINED_TITLE_MESSAGE = "pluginwizard.siteMessage.classNotDefined.alertMessage";
    private static final String PROPERTY_CLASS_NOT_BEGIN_CAPITAL_TITLE_MESSAGE = "pluginwizard.siteMessage.classNotBeginCapital.alertMessage";
    private static final String PROPERTY_ATTRIBUTE_NOT_DEFINED_TITLE_MESSAGE = "pluginwizard.siteMessage.attributeNotDefined.alertMessage";
    private static final String PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE = "pluginwizard.siteMessage.mandatoryFields.alertMessage";
    private static final String PROPERTY_BUSINESS_PRIMARY_KEY_MUST_BE_INT = "pluginwizard.siteMessage.keyMustBeInt.alertMessage";
    private static final String PROPERTY_BUSINESS_DESCRIPTION_MUST_BE_STRING = "pluginwizard.siteMessage.descriptionIsString.alertMessage";
    //Input Fields Verification
    private static final String PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_DB_POOL_REQUIRED = "pluginwizard.regex.do_modify_plugin.plugin_db_pool_required";
    private static final String PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_PROVIDER_URL = "pluginwizard.regex.do_modify_plugin.plugin_provider_url";
    private static final String PROPERTY_DO_CREATE_PLUGIN_PARAM_PLUGIN_NAME = "pluginwizard.regex.do_create_plugin.plugin_name";
    //Regex property for creation of a portlet
    private static final String PROPERTY_DO_CREATE_PORTLET_PARAM_PORTLET_TYPE_NAME = "pluginwizard.regex.do_create_portlet.portlet_type_name";
    private static final String PROPERTY_DO_MODIFY_PORTLET_PARAM_PORTLET_TYPE_NAME = "pluginwizard.regex.do_modify_portlet.portlet_type_name";
    //Error Messages
    private static final String PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_PROVIDER_URL_MESSAGE = "pluginwizard.siteMessage.regex.do_modify_plugin.plugin_provider_url";
    private static final String PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_DB_POOL_REQUIRED_MESSAGE = "pluginwizard.siteMessage.regex.do_modify_plugin.plugin_db_pool_required";
    private static final String PROPERTY_DO_CREATE_PLUGIN_PARAM_PLUGIN_NAME_MESSAGE = "pluginwizard.siteMessage.regex.do_create_plugin.plugin_name";
    private static final String PROPERTY_DO_CREATE_PORTLET_PARAM_PORTLET_TYPE_NAME_MESSAGE = "pluginwizard.siteMessage.regex.do_create_portlet.portlet_type_name";
    private static final String PROPERTY_DO_MODIFY_PORTLET_PARAM_PORTLET_TYPE_NAME_MESSAGE = "pluginwizard.siteMessage.regex.do_create_portlet.portlet_type_name";
    private static final String PROPERTY_CLASS_TWO_ATTRIBUTES_MINIMUM = "pluginwizard.siteMessage.classTwoAttributes.alertMessage";
    // Properties
    private static final String DEFAULT_PLUGIN_CLASS = "fr.paris.lutece.portal.service.plugin.PluginDefaultImplementation";
    int _nPluginId;
    String _strPluginName;

    ////////////////////////////////////////////////////////////////////////////
    // VIEWS
    /**
     * The Creation form of the plugin
     *
     * @param request The Http Request
     * @return The html code of the plugin
     */
    @View(value = VIEW_CREATE_PLUGIN, defaultView = true)
    public XPage getCreatePlugin(HttpServletRequest request)
    {
        return getXPage(TEMPLATE_CREATE_PLUGIN, request.getLocale());
    }

    /**
     * The modification form of the plugin
     *
     * @param request The Http Request
     * @return The html code of the creation of plugin description
     */
    @View(VIEW_MODIFY_PLUGIN)
    public XPage getModifyPlugin(HttpServletRequest request)
    {
        return getXPage( TEMPLATE_MODIFY_PLUGIN, request.getLocale(), getPluginModel() );
    }

    /**
     * The creation form of the plugin description
     *
     * @param request The Http Request
     * @param strPluginName The Plugin Name
     * @return The creation form of the plugin description
     */
    @View(VIEW_PLUGIN_EXISTS)
    public XPage getPluginExists(HttpServletRequest request)
    {
        Map<String, Object> model = getModel();
        model.put(MARK_PLUGIN_NAME, _strPluginName);
        model.put(MARK_PLUGIN_ID, _nPluginId);

        return getXPage(TEMPLATE_PLUGIN_EXISTS, request.getLocale(), model);
    }

    /**
     * Gets the create plugin description page
     *
     * @param request The HTTP request
     * @param nPluginId The plugin ID
     * @return The page
     */
    @View(VIEW_CREATE_DESCRIPTION)
    public XPage getCreatePluginDescription(HttpServletRequest request)
    {
        Map<String, Object> model = getPluginModel();

        Collection<ConfigurationKey> listKeys = ConfigurationKeyHome.getConfigurationKeysList();

        for (ConfigurationKey key : listKeys)
        {
            model.put(key.getKeyDescription().trim(), key.getKeyValue());
        }

        return getXPage(TEMPLATE_CREATE_PLUGIN_DESCRIPTION, request.getLocale(), model);

    }

    /**
     * The modification form of the plugin description
     *
     * @param request The Http Request
     * @return The html code of the creation of plugin description
     */
    @View(VIEW_MODIFY_DESCRIPTION)
    public XPage getModifyPluginDescription(HttpServletRequest request)
    {
        return getXPage(TEMPLATE_MODIFY_PLUGIN_DESCRIPTION, request.getLocale(), getPluginModel() );
    }

     /**
     * The management screen of the admin features
     *
     * @param request The Http Request
     * @return The html code of the admin features
     */
    @View(VIEW_MANAGE_ADMIN_FEATURES)
    public XPage getManageAdminFeatures(HttpServletRequest request)
    {
        PluginModel pm = ModelService.getPluginModel(_nPluginId);

        Map<String, Object> model = getModel();

        model.put(MARK_PLUGIN_ID, Integer.toString(_nPluginId));
        model.put(MARK_ADMIN_FEATURES, pm.getFeatures());

        return getXPage(TEMPLATE_MANAGE_ADMIN_FEATURES, request.getLocale(), model);

    }

    /**
     * The creation form of the admin feature
     *
     * @param request The Http Request
     * @return The html code of the admin feature
     */
    @View(VIEW_CREATE_ADMIN_FEATURE)
    public XPage getCreateAdminFeature(HttpServletRequest request)
    {
        return getXPage( TEMPLATE_CREATE_ADMIN_FEATURE, request.getLocale(), getPluginModel() );
    }

    /**
     * The modification screen of the admin feature
     *
     * @param request The Http Request
     * @return The html code of the admin feature
     */
    @View(VIEW_MODIFY_ADMIN_FEATURE)
    public XPage getModifyAdminFeature(HttpServletRequest request)
    {
        int nFeatureId = Integer.parseInt(request.getParameter(PARAM_FEATURE_ID));
        Feature feature = ModelService.getFeature(_nPluginId, nFeatureId);

        Map<String, Object> model = getModel();
        model.put(MARK_FEATURE, feature);
        model.put(MARK_PLUGIN_ID, _nPluginId);

        return getXPage(TEMPLATE_MODIFY_ADMIN_FEATURE, request.getLocale(), model);
    }

    /**
     * The management screen of business classes associated to the plugin which
     * is generated
     *
     * @param request The Http Request
     * @return The html code of the management screen of the business classes
     */
    @View(VIEW_MANAGE_BUSINESS_CLASSES)
    public XPage getManageBusinessClasses(HttpServletRequest request)
    {
        Map<String, Object> model = getModel();
        model.put(MARK_PLUGIN_ID, Integer.toString(_nPluginId));
        model.put(MARK_BUSINESS_CLASSES, ModelService.getPluginModel(_nPluginId).getBusinessClasses());

        return getXPage(TEMPLATE_MANAGE_BUSINESS_CLASSES, request.getLocale(), model);

    }

    /**
     * The creation form of a business class
     *
     * @param request The Http Request
     * @return The html code of the creation of a business class
      */
    @View( VIEW_CREATE_BUSINESS_CLASS )
    public XPage getCreateBusinessClass(HttpServletRequest request)
    {
        Map<String, Object> model = getPluginModel();
        model.put(MARK_ADMIN_FEATURES_COMBO, ModelService.getComboFeatures(_nPluginId));

        return getXPage(TEMPLATE_CREATE_BUSINESS_CLASS, request.getLocale(), model);

    }

    /**
     * Gets the modify business class page
     *
     * @param nBusinessClassId The business class id
     * @param request The HTTP request
     * @return The page
     */
    @View(VIEW_MODIFY_BUSINESS_CLASS)
    public XPage getModifyBusinessClass(HttpServletRequest request)
    {
        int nBusinessClassId = Integer.parseInt(request.getParameter(PARAM_BUSINESS_CLASS_ID));
        BusinessClass bClass = ModelService.getBusinessClass(_nPluginId, nBusinessClassId);
        Map<String, Object> model = getPluginModel();
        model.put(MARK_BUSINESS_CLASS, bClass);
        model.put(MARK_ADMIN_FEATURES_COMBO, ModelService.getComboFeatures(_nPluginId));

        return getXPage(TEMPLATE_MODIFY_BUSINESS_CLASS, request.getLocale(), model);
    }

    /**
     * The creation form of the attribute associated to a business class
     *
     * @param request The Http Request
     * @return The html code of the admin feature
     */
    @View(VIEW_CREATE_ATTRIBUTE)
    public XPage getCreateAttribute(HttpServletRequest request)
    {
        String strBusinessClassId = request.getParameter(PARAM_BUSINESS_CLASS_ID);
        Map<String, Object> model = getModel();
        model.put(MARK_BUSINESS_CLASS_ID, strBusinessClassId);
        model.put(MARK_PLUGIN_ID, _nPluginId);
        model.put(MARK_ATTRIBUTE_TYPE_COMBO, ModelService.getAttributeTypes());

        return getXPage(TEMPLATE_CREATE_ATTRIBUTE, request.getLocale(), model);
    }

    /**
     * The modification form of an attribute
     *
     * @param request The Http Request
     * @return The html code of the creation of attribute description
     */
    @View(VIEW_MODIFY_ATTRIBUTE)
    public XPage getModifyAttribute(HttpServletRequest request)
    {
        Map<String, Object> model = getModel();
        int nIdBusinessClass = Integer.parseInt(request.getParameter(PARAM_BUSINESS_CLASS_ID));
        int nIdAttribute = Integer.parseInt(request.getParameter(PARAM_ATTRIBUTE_ID));
        Attribute attribute = ModelService.getAttribute(_nPluginId, nIdBusinessClass, nIdAttribute);

        model.put(MARK_PLUGIN_ID, _nPluginId);
        model.put(MARK_BUSINESS_CLASS_ID, nIdBusinessClass);
        model.put(MARK_ATTRIBUTE_TYPE_COMBO, ModelService.getAttributeTypes());
        model.put(MARK_ATTRIBUTE, attribute);

        return getXPage(TEMPLATE_MODIFY_ATTRIBUTE, request.getLocale(), model);
    }

    /**
     * The management of the plugin applications associated to the generated
     * plugin
     *
     * @param request The Http Request
     * @return The html code of the management screen of the applications
     */
    @View(VIEW_MANAGE_APPLICATIONS)
    public XPage getManageApplications(HttpServletRequest request)
    {
        Collection<BusinessClass> listBusinessClass = ModelService.getPluginModel(_nPluginId).getBusinessClasses();

        for (BusinessClass businessClass : listBusinessClass)
        {
            if (businessClass.getAttributes().size() < 2)
            {
                // TODO add Error
                return redirectView(request, VIEW_MANAGE_APPLICATIONS);
            }
        }

        Map<String, Object> model = getModel();
        model.put(MARK_PLUGIN_ID, Integer.toString(_nPluginId));
        model.put(MARK_PLUGIN_APPLICATIONS, ModelService.getPluginModel(_nPluginId).getApplications());

        return getXPage(TEMPLATE_MANAGE_PLUGIN_APPLICATIONS, request.getLocale(), model);
    }

    /**
     * The creation screen of a plugin application
     *
     * @param request The Http Request
     * @return The html code of a plugin application
     */
    @View( VIEW_CREATE_APPLICATION )
    public XPage getCreateApplication(HttpServletRequest request)
    {
        return getXPage( TEMPLATE_CREATE_PLUGIN_APPLICATION, request.getLocale(), getPluginModel() );
    }

    /**
     * The modification screen of a plugin application
     *
     * @param request The Http Request
     * @return The html code of the modification of an application associated to
     * the generated plugin
     */
    @View(VIEW_MODIFY_APPLICATION)
    public XPage getModifyApplication(HttpServletRequest request)
    {
        int nPluginApplicationId = Integer.parseInt(request.getParameter(PARAM_APPLICATION_ID));

        Application application = ModelService.getApplication(_nPluginId, nPluginApplicationId);
        Map<String, Object> model = getPluginModel();
        model.put(MARK_APPLICATION, application);
        model.put(MARK_PLUGIN_ID, Integer.toString(_nPluginId));

        return getXPage(TEMPLATE_MODIFY_PLUGIN_APPLICATION, request.getLocale(), model);
    }

    /**
     * The screen for management of portlets associated to the generated plugin
     *
     * @param request The Http Request
     * @return The main management screen of portlets
     */
    @View( VIEW_MANAGE_PORTLETS )
    public XPage getManagePortlets(HttpServletRequest request)
    {
        Map<String, Object> model = getModel();
        model.put(MARK_PLUGIN_ID, Integer.toString(_nPluginId));
        model.put(MARK_PLUGIN_PORTLETS, ModelService.getPluginModel(_nPluginId).getPortlets());

        return getXPage(TEMPLATE_MANAGE_PLUGIN_PORTLETS, request.getLocale(), model );
    }

    /**
     * The creation screen of a portlet
     *
     * @param request The Http Request
     * @return The html code of the creation of a portlet
     */
    @View( VIEW_CREATE_PORTLET )
    public XPage getCreatePortlet(HttpServletRequest request)
    {
        return getXPage( TEMPLATE_CREATE_PLUGIN_PORTLET, request.getLocale(), getPluginModel());
    }

    /**
     * The modification page of the portlet
     *
     * @param request The Http Request
     * @return The html code of the modification of the portlet
     */
    @View( VIEW_MODIFY_PORTLET )
    public XPage getModifyPortlet(HttpServletRequest request)
    {
        int nPluginPortletId = Integer.parseInt(request.getParameter(PARAM_PORTLET_ID));
        Map<String, Object> model = getModel();
        model.put(MARK_PLUGIN_PORTLET, ModelService.getPortlet(_nPluginId, nPluginPortletId));

        return getXPage(TEMPLATE_MODIFY_PLUGIN_PORTLET, request.getLocale(), model);

    }

    ////////////////////////////////////////////////////////////////////////////
    // ACTIONS
    /**
     * The modification action of the plugin
     *
     * @param request The Http Request
     * @param strPluginName The Plugin name
     * @return The plugin id
     */
    @Action(ACTION_CREATE_PLUGIN)
    public XPage doCreatePlugin(HttpServletRequest request)
    {
        FormName form = new FormName();
        populate(form, request);
        if (!validateBean(form))
        {
            return redirectView(request, VIEW_CREATE_PLUGIN);
        }
        _strPluginName = form.getName();
        _nPluginId = ModelHome.exists(form.getName());

        if (_nPluginId == -1)
        {
            // The plugin doesn't exists
            _nPluginId = ModelService.createModel(form.getName());
            return redirectView(request, VIEW_CREATE_DESCRIPTION);
        }
        return redirectView(request, VIEW_PLUGIN_EXISTS);
    }

    @Action( ACTION_RESET_DATA )
    public XPage doResetData( HttpServletRequest request )
    {
        ModelService.removeAll(_nPluginId);
        return redirectView( request, VIEW_CREATE_DESCRIPTION );
    }
    
    /**
     * The modification action of the plugin
     *
     * @param request The Http Request
     */
    @Action(ACTION_DESCRIPTION_PREVIOUS)
    public XPage doDescritionPrevious(HttpServletRequest request)
    {
        return doModifyPlugin(request, VIEW_MODIFY_PLUGIN);
    }

    @Action(ACTION_DESCRIPTION_NEXT)
    public XPage doDescritionNext(HttpServletRequest request)
    {
        return doModifyPlugin(request, VIEW_MANAGE_ADMIN_FEATURES);
    }

    private XPage doModifyPlugin(HttpServletRequest request, String strView)
    {
        PluginModel model = ModelService.getPluginModel(_nPluginId);

        populate(model, request);

        if (!validateBean(model))
        {
            return redirectView(request, VIEW_MODIFY_PLUGIN);
        }
        model.setPluginIconUrl("images/admin/skin/plugins/" + _strPluginName + "/" + _strPluginName + ".png");
        model.setPluginCopyright(request.getParameter(PARAM_PLUGIN_COPYRIGHT));


        ModelService.savePluginModel(model);
        return redirectView(request, strView);
    }

    /**
     * The creation of an admin feature
     *
     * @param request The Http Request
     */
    @Action(ACTION_CREATE_ADMIN_FEATURE)
    public XPage doCreateAdminFeature(HttpServletRequest request)
    {
        Feature feature = new Feature();
        populate( feature , request);
        if( ! validateBean(this))
        {
            return redirectView(request, VIEW_CREATE_ADMIN_FEATURE );
        }
        ModelService.addFeature(_nPluginId, feature);

        return redirectView(request, VIEW_MANAGE_ADMIN_FEATURES);
    }

    /**
     * The modification action of an admin feature
     *
     * @param request The Http Request
     */
    @Action(ACTION_MODIFY_ADMIN_FEATURE)
    public XPage doModifyAdminFeature(HttpServletRequest request)
    {
        Feature feature = new Feature();
        populate( feature , request);
        if( ! validateBean(this))
        {
            return redirectView(request, VIEW_MODIFY_ADMIN_FEATURE );
        }
        ModelService.updateFeature(_nPluginId, feature);

        return redirectView(request, VIEW_MANAGE_ADMIN_FEATURES);
    }
    
 
    /**
     * The confirmation of the removal of an admin feature
     *
     * @param request The Http Request
     */
    private void getConfirmRemoveAdminFeature(HttpServletRequest request)
    {
        UrlItem url = new UrlItem(JSP_PAGE_PORTAL);

        url.addParameter(PARAM_PAGE, PROPERTY_PLUGIN_NAME);
        url.addParameter(PARAM_ACTION, ACTION_DO_REMOVE_FEATURE);
        url.addParameter(PARAM_FEATURE_ID, request.getParameter(PARAM_FEATURE_ID));

 //       SiteMessageService.setMessage(request, PROPERTY_CONFIRM_REMOVE_FEATURE_ALERT_MESSAGE, null,
//                PROPERTY_CONFIRM_REMOVE_FEATURE_TITLE_MESSAGE, url.getUrl(), null, SiteMessage.TYPE_CONFIRMATION);
    }

    /**
     * The removal screen of an admin feature
     *
     * @param request The Http Request
     */
    private void doRemoveAdminFeature(HttpServletRequest request)
    {
        int nFeatureId = Integer.parseInt(request.getParameter(PARAM_FEATURE_ID));
        ModelService.removeFeature( _nPluginId, nFeatureId);
    }

    /**
     * The creation action of the plugin application
     *
     * @param request The Http Request
     */
    @Action( ACTION_CREATE_APPLICATION )
    public XPage doCreateApplication(HttpServletRequest request)
    {
        Application application = new Application();
        populate( application , request );
        if( !validateBean( application ))
        {
            return redirectView(request, VIEW_CREATE_APPLICATION );
        }
        ModelService.addApplication(_nPluginId, application);
        return redirectView(request, VIEW_MANAGE_APPLICATIONS );
    }
    
    /**
     * The modification action of the plugin application
     *
     * @param request The Http Request
     */
    @Action(ACTION_MODIFY_APPLICATION)
    public XPage doModifyApplication(HttpServletRequest request)
    {
        Application application = new Application();
        populate(application, request);
        if (!validateBean(application))
        {
            return redirectView(request, VIEW_MODIFY_APPLICATION);
        }

        ModelService.updateApplication(_nPluginId, application);
        return redirectView(request, VIEW_MANAGE_APPLICATIONS);
    }

    /**
     * The confirmation of an application removal
     *
     * @param request The Http Request
     */
    private void getConfirmRemovePluginApplication(HttpServletRequest request)
    {
        UrlItem url = new UrlItem(JSP_PAGE_PORTAL);
        url.addParameter(PARAM_PAGE, PROPERTY_PLUGIN_NAME);
        url.addParameter(PARAM_ACTION, ACTION_DO_REMOVE_PLUGIN_APPLICATION);
        url.addParameter(PARAM_APPLICATION_ID, request.getParameter(PARAM_APPLICATION_ID));
 //       SiteMessageService.setMessage(request, PROPERTY_CONFIRM_REMOVE_APPLICATION_ALERT_MESSAGE, null,
 //               PROPERTY_CONFIRM_REMOVE_APPLICATION_TITLE_MESSAGE, url.getUrl(), null, SiteMessage.TYPE_CONFIRMATION);
    }

    /**
     * The confirmation of a business class removal
     *
     * @param request The Http Request
     * @throws SiteMessageException The front office exception
     */
    private void getConfirmRemoveBusinessClass(HttpServletRequest request)
            throws SiteMessageException
    {
        String strBusinessClassId = request.getParameter(PARAM_BUSINESS_CLASS_ID);
        UrlItem url = new UrlItem(JSP_PAGE_PORTAL);
        url.addParameter(PARAM_PAGE, PROPERTY_PLUGIN_NAME);
        url.addParameter(PARAM_ACTION, ACTION_DO_REMOVE_BUSINESS_CLASS);
        url.addParameter(PARAM_BUSINESS_CLASS_ID, strBusinessClassId);
        url.addParameter(PARAM_FEATURE_ID, request.getParameter(PARAM_FEATURE_ID));

        SiteMessageService.setMessage(request, PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_ALERT_MESSAGE, null,
                PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_TITLE_MESSAGE, url.getUrl(), null, SiteMessage.TYPE_CONFIRMATION);
    }

    /**
     * The removal action of a plugin application
     *
     * @param request The Http Request
     */
    @Action( ACTION_REMOVE_APPLICATION )
    public XPage doRemoveApplication(HttpServletRequest request)
    {
        int nApplicationId = Integer.parseInt(request.getParameter(PARAM_APPLICATION_ID));
        ModelService.removeApplication(_nPluginId, nApplicationId);
        return redirectView( request , VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * The removal action of a plugin application
     *
     * @param request The Http Request
     */
    @Action ( ACTION_REMOVE_BUSINESS_CLASS )
    public XPage doRemoveBusinessClass(HttpServletRequest request)
    {
        int nBusinessClassId = Integer.parseInt(request.getParameter(PARAM_BUSINESS_CLASS_ID));

        ModelService.removeBusinessClass(_nPluginId, nBusinessClassId);
        return redirectView(request, VIEW_MANAGE_BUSINESS_CLASSES );
    }

    /**
     * The creation action of the business class
     *
     * @param request The Http Request
     * @throws SiteMessageException Front office error handling
     * @return The business class id
     */
    @Action( ACTION_CREATE_BUSINESS_CLASS )
    public XPage doCreateBusinessClass(HttpServletRequest request)
    {
        BusinessClass businessClass = new BusinessClass();
        populate(businessClass, request);
        if (!validateBean(businessClass))
        {
            return redirectView(request, VIEW_CREATE_BUSINESS_CLASS);
        }
        ModelService.addBusinessClass(_nPluginId, businessClass);

        return redirectView(request, VIEW_MANAGE_BUSINESS_CLASSES);
    }

    /**
     * The modification action for the business class
     *
     * @param request The Http Request
     * @throws SiteMessageException Front office error handling
     */
    @Action(ACTION_MODIFY_BUSINESS_CLASS)
    public XPage doModifyBusinessClass(HttpServletRequest request)
    {
        int nBusinessClassId = Integer.parseInt(request.getParameter(PARAM_BUSINESS_CLASS_ID));
        BusinessClass businessClass = ModelService.getBusinessClass( _nPluginId, nBusinessClassId);

        String strFeatureId = request.getParameter(PARAM_FEATURE_ID);

        if ((strFeatureId == null) || strFeatureId.equals(""))
        {
            return redirectView(request, VIEW_MODIFY_BUSINESS_CLASS);
        }


        ModelService.updateBusinessClass( _nPluginId, businessClass);
        return redirectView(request, VIEW_MANAGE_BUSINESS_CLASSES);
    }
    

    /**
     * The creation action of the portlet
     *
     * @param request The Http Request
     * @throws SiteMessageException Front office error handling
     */
    @Action( ACTION_CREATE_PORTLET )
    public XPage doCreatePortlet(HttpServletRequest request)
    {
        Portlet portlet = new Portlet();
        populate( portlet , request);
        if( !validateBean( portlet ))
        {
            return redirectView(request, VIEW_CREATE_PORTLET );
        }

        ModelService.addPortlet(_nPluginId, portlet);
        return redirectView( request, VIEW_MANAGE_PORTLETS );
    }

    /**
     * The modification action of the portlet
     *
     * @param request The Http Request
     */
    @Action( ACTION_MODIFY_PORTLET )
    public XPage doModifyPluginPortlet(HttpServletRequest request)
    {
        Portlet portlet = new Portlet();
        populate( portlet , request );
        if( ! validateBean( portlet ))
        {
            return redirectView(request, ACTION_MODIFY_PORTLET );
        }
        ModelService.updatePortlet( _nPluginId, portlet);
        return redirectView(request, VIEW_MANAGE_PORTLETS );
    }

    /**
     * The creation action of an attribute
     *
     * @param request The Http Request
     */
    @Action( ACTION_CREATE_ATTRIBUTE )
    public XPage doCreateAttribute(HttpServletRequest request)
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ));
        Attribute attribute = new Attribute();
        populate( attribute , request );
        if( !validateBean( attribute ))
        {
            return redirectView(request, VIEW_CREATE_ATTRIBUTE );
        }
        
        ModelService.addAttribute(_nPluginId, nBusinessClassId, attribute);
        return redirectView( request, VIEW_MODIFY_BUSINESS_CLASS );
    }

    /**
     * The modification action for the attribute
     *
     * @param request The Http Request
     */
    @Action( ACTION_MODIFY_ATTRIBUTE )
    public XPage doModifyAttribute(HttpServletRequest request)
    {
        int nBusinessClassId = Integer.parseInt(request.getParameter(PARAM_BUSINESS_CLASS_ID));
        Attribute attribute = new Attribute();
        populate( attribute , request );
        if( !validateBean( attribute ))
        {
            return redirectView(request, VIEW_MODIFY_ATTRIBUTE );
        }

        ModelService.updateAttribute( _nPluginId, nBusinessClassId, attribute);
        return redirectView(request, VIEW_MODIFY_BUSINESS_CLASS );
    }

    /**
     * The confirmation of the attribute removal
     *
     * @param request The Http Request
     * @throws SiteMessageException The site message exception
     */
    private void getConfirmRemoveBusinessAttribute(HttpServletRequest request)
            throws SiteMessageException
    {
        UrlItem url = new UrlItem(JSP_PAGE_PORTAL);
        url.addParameter(PARAM_PAGE, PROPERTY_PLUGIN_NAME);
        url.addParameter(PARAM_ACTION, ACTION_DO_REMOVE_BUSINESS_ATTRIBUTE);
        url.addParameter(PARAM_ATTRIBUTE_ID, request.getParameter(PARAM_ATTRIBUTE_ID));
        url.addParameter(PARAM_BUSINESS_CLASS_ID, request.getParameter(PARAM_BUSINESS_CLASS_ID));
        SiteMessageService.setMessage(request, PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_ALERT_MESSAGE, null,
                PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_TITLE_MESSAGE, url.getUrl(), null, SiteMessage.TYPE_CONFIRMATION);
    }

    /**
     * The confirmation of the plugin removal
     *
     * @param request The Http Request
     * @throws SiteMessageException The site message exception
     */
    private void getConfirmRemovePluginPortlet(HttpServletRequest request)
            throws SiteMessageException
    {
        UrlItem url = new UrlItem(JSP_PAGE_PORTAL);
        url.addParameter(PARAM_PAGE, PROPERTY_PLUGIN_NAME);
        url.addParameter(PARAM_ACTION, ACTION_DO_REMOVE_PLUGIN_PORTLET);
        url.addParameter(PARAM_PORTLET_ID, request.getParameter(PARAM_PORTLET_ID));
        SiteMessageService.setMessage(request, PROPERTY_CONFIRM_REMOVE_PORTLET_ALERT_MESSAGE, null,
                PROPERTY_CONFIRM_REMOVE_PORTLET_TITLE_MESSAGE, url.getUrl(), null, SiteMessage.TYPE_CONFIRMATION);
    }

    /**
     * Remove Portlet Action
     *
     * @param request The Http Request
     */
    private void doRemovePluginPortlet(HttpServletRequest request)
    {
        int nPluginPortletId = Integer.parseInt(request.getParameter(PARAM_PORTLET_ID));
        ModelService.removePortlet( _nPluginId, nPluginPortletId);
    }

    /**
     * Remove Business Attribute
     *
     * @param request The Http Request
     */
    private void doRemoveAttribute(HttpServletRequest request)
    {
        int nBusinessClassId = Integer.parseInt(request.getParameter(PARAM_BUSINESS_CLASS_ID));
        int nAttributeId = Integer.parseInt(request.getParameter(PARAM_ATTRIBUTE_ID));
        ModelService.removeAttribute( _nPluginId, nBusinessClassId, nAttributeId);
    }

    /**
     * The get page of the plugin recapitulation
     *
     * @param request The Http Request
     * @return The Html code of the summary
     */
    @View(VIEW_RECAPITULATE)
    public XPage getPluginRecapitulate(HttpServletRequest request)
    {
        PluginModel pm = ModelService.getPluginModel(_nPluginId);

        Map<String, Object> model = getPluginModel();
        model.put(MARK_PLUGIN_ID, Integer.toString( _nPluginId));
        model.put(MARK_PLUGIN_APPLICATIONS, pm.getApplications()); // FIXME can be found in the model
        model.put(MARK_ADMIN_FEATURES, pm.getFeatures());
        model.put(MARK_PLUGIN_PORTLETS, pm.getPortlets());
        model.put(MARK_BUSINESS_CLASSES, pm.getBusinessClasses());
        model.put(MARK_SCHEMES_COMBO, GeneratorService.getGenerationSchemes());

        return getXPage(TEMPLATE_GET_RECAPITULATE, request.getLocale(), model);
    }


    private Map<String, Object> getPluginModel()
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(MARK_PLUGIN_MODEL, ModelService.getPluginModel(_nPluginId));
        return model;

    }

}
