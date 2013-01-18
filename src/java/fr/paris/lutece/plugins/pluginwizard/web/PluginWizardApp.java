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

import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKey;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Attribute;
import fr.paris.lutece.plugins.pluginwizard.business.model.AttributeHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClassHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplication;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginApplicationHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginFeatureHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortlet;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginPortletHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.ResourceKeyHome;
import fr.paris.lutece.plugins.pluginwizard.service.ResourceKeyService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * The class manage pluginwizard Page
 */
public class PluginWizardApp implements XPageApplication
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
    private static final String TEMPLATE_MANAGE_ADMIN_FEATURES = "/skin/plugins/pluginwizard/pluginwizard_manage_plugin_admin_features.html";
    private static final String TEMPLATE_MANAGE_PLUGIN_PORTLETS = "/skin/plugins/pluginwizard/pluginwizard_manage_plugin_portlets.html";
    private static final String TEMPLATE_MANAGE_PLUGIN_APPLICATIONS = "/skin/plugins/pluginwizard/pluginwizard_manage_plugin_applications.html";
    private static final String TEMPLATE_MANAGE_BUSINESS_CLASSES = "/skin/plugins/pluginwizard/pluginwizard_manage_business_classes.html";
    private static final String TEMPLATE_GET_RECAPITULATE = "/skin/plugins/pluginwizard/pluginwizard_plugin_recapitulate.html";

    //CREATE
    private static final String TEMPLATE_CREATE_ADMIN_FEATURE = "/skin/plugins/pluginwizard/pluginwizard_create_admin_feature.html";
    private static final String TEMPLATE_CREATE_PLUGIN_PORTLET = "/skin/plugins/pluginwizard/pluginwizard_create_plugin_portlet.html";
    private static final String TEMPLATE_CREATE_PLUGIN_APPLICATION = "/skin/plugins/pluginwizard/pluginwizard_create_plugin_application.html";
    private static final String TEMPLATE_CREATE_BUSINESS_CLASS = "/skin/plugins/pluginwizard/pluginwizard_create_business_class.html";
    private static final String TEMPLATE_CREATE_ATTRIBUTE = "/skin/plugins/pluginwizard/pluginwizard_create_attribute.html";

    //MODIFY
    private static final String TEMPLATE_MODIFY_ATTRIBUTE = "/skin/plugins/pluginwizard/pluginwizard_modify_attribute.html";
    private static final String TEMPLATE_MODIFY_ADMIN_FEATURE = "/skin/plugins/pluginwizard/pluginwizard_modify_admin_feature.html";
    private static final String TEMPLATE_MODIFY_PLUGIN_PORTLET = "/skin/plugins/pluginwizard/pluginwizard_modify_plugin_portlet.html";
    private static final String TEMPLATE_MODIFY_PLUGIN_APPLICATION = "/skin/plugins/pluginwizard/pluginwizard_modify_plugin_application.html";
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_CLASSNAME = "class";
    private static final String PARAM_TABLE = "table";
    private static final String PARAM_PLUGIN_ID = "plugin_id";
    private static final String PARAM_PLUGIN_NAME = "plugin_name";
    private static final String PARAM_PLUGIN_DESCRIPTION = "plugin_description";
    private static final String PARAM_PLUGIN_PROVIDER = "plugin_provider";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_PLUGIN_COPYRIGHT = "plugin_copyright";
    private static final String PARAM_PLUGIN_DB_POOL_REQUIRED = "plugin_pool";
    private static final String PARAM_PLUGIN_PROVIDER_URL = "plugin_provider_url";
    private static final String PARAM_PLUGIN_VERSION = "version";
    private static final String PARAM_BUSINESS_CLASS_ID = "business_class_id";
    private static final String PARAM_ATTRIBUTE_ID = "attribute_id";
    private static final String PARAM_RESET = "reset";

    //Admin feature
    private static final String PARAM_FEATURE_ID = "feature_id";
    private static final String PARAM_FEATURE_RIGHT = "feature_right";
    private static final String PARAM_FEATURE_TITLE = "feature_title";
    private static final String PARAM_FEATURE_LEVEL = "feature_level";
    private static final String PARAM_FEATURE_NAME = "feature_name";
    private static final String PARAM_FEATURE_DESCRITPION = "feature_description";

    //Application
    private static final String PARAM_APPLICATION_ID = "application_id";
    private static final String PARAM_APPLICATION_NAME = "application_name";
    private static final String PARAM_APPLICATION_CLASS = "application_class";
    private static final String PARAM_PORTLET_UPDATE_URL = "portlet_update_url";
    private static final String PARAM_PORTLET_CREATION_URL = "portlet_creation_url";
    private static final String PARAM_PORTLET_TYPE_NAME = "portlet_type_name";
    private static final String PARAM_PORTLET_CLASS = "portlet_class";

    //Portlet
    private static final String PARAM_PORTLET_ID = "portlet_id";

    //Navigation
    private static final String PARAMETER_GOTO_PLUGIN_DESCRIPTION_MODIFICATION = "goToPluginModification";
    private static final String PARAMETER_GOTO_MANAGE_PLUGIN_APPLICATION = "goToManagePluginApplications";
    private static final String PARAMETER_GOTO_CREATE_ADMIN_FEATURE = "goToCreateAdminFeature";
    private static final String PARAMETER_GOTO_MANAGE_ADMIN_FEATURES = "goToManageAdminFeatures";
    private static final String PARAMETER_GOTO_MANAGE_BUSINESS_CLASSES = "goToManageBusinessClasses";
    private static final String PARAMETER_GOTO_CREATE_PLUGIN_APPLICATION = "goToCreatePluginApplication";
    private static final String PARAMETER_GOTO_MANAGE_PLUGIN_PORTLETS = "goToManagePluginPortlets";
    private static final String PARAMETER_GOTO_CREATE_PLUGIN_PORTLET = "goToCreatePluginPortlet";
    private static final String PARAMETER_GOTO_GET_RECAPITULATE = "goToGetRecapitulate";
    private static final String PARAMETER_GOTO_CREATE_BUSINESS_CLASS = "goToCreateBusinessClass";
    private static final String PARAMETER_GOTO_MANAGE_RESOURCE_KEYS = "goToManageResourceKeys";
    private static final String PARAMETER_GOTO_MODIFY_PLUGIN = "goToModifyPlugin";
    private static final String PARAMETER_GOTO_CREATE_ATTRIBUTE = "goToCreateAttribute";
    private static final String PARAMETER_GOTO_MODIFY_BUSINESS_CLASS = "goToModifyBusinessClass";
    private static final String PARAMETER_GOTO_MODIFY_ATTRIBUTE = "goToModifyAttribute";

    //Plugin
    private static final String PARAM_ATTRIBUTE_NAME = "attribute_name";
    private static final String PARAM_ATTRIBUTE_TYPE_ID = "id_attribute_type";
    private static final String PARAM_PRIMARY_KEY = "primary_key";
    private static final String PARAM_CLASS_DESCRIPTION = "class_description";
    private static final String ACTION_DO_CREATE_PLUGIN = "do_create_plugin";
    private static final String ACTION_GET_MODIFY_PLUGIN_DESCRIPTION = "get_modify_plugin_description";
    private static final String ACTION_GET_MODIFY_PLUGIN = "get_modify_plugin";
    private static final String ACTION_DO_MODIFY_PLUGIN_DESCRIPTION = "do_modify_plugin_description";
    private static final String ACTION_CREATE_ATTRIBUTE = "create_attribute";
    private static final String ACTION_DO_CREATE_ATTRIBUTE = "do_create_attribute";
    private static final String ACTION_MODIFY_ATTRIBUTE = "modify_attribute";
    private static final String ACTION_DO_MODIFY_ATTRIBUTE = "do_modify_attribute";

    //MANAGEMENT ACTIONS
    private static final String ACTION_MANAGE_ADMIN_FEATURES = "manage_admin_features";
    private static final String ACTION_MANAGE_PLUGIN_PORTLETS = "manage_plugin_portlets";
    private static final String ACTION_MANAGE_PLUGIN_APPLICATIONS = "manage_plugin_applications";
    private static final String ACTION_MANAGE_BUSINESS_CLASSES = "manage_business_classes";
    private static final String ACTION_MANAGE_RESOURCE_KEYS = "manage_resources_keys";

    //CREATE ACTIONS
    private static final String ACTION_CREATE_ADMIN_FEATURE = "create_admin_feature";
    private static final String ACTION_CREATE_PLUGIN_PORTLET = "create_plugin_portlet";
    private static final String ACTION_CREATE_PLUGIN_APPLICATION = "create_plugin_application";
    private static final String ACTION_CREATE_BUSINESS_CLASS = "create_business_class";

    //DO CREATE
    private static final String ACTION_DO_CREATE_FEATURE = "do_create_feature";
    private static final String ACTION_DO_CREATE_PLUGIN_APPLICATION = "do_create_application";
    private static final String ACTION_DO_CREATE_PLUGIN_PORTLET = "do_create_portlet";
    private static final String ACTION_DO_CREATE_BUSINESS_CLASS = "do_create_class";

    //MODIFY_ACTIONS
    private static final String ACTION_MODIFY_ADMIN_FEATURE = "modify_admin_feature";
    private static final String ACTION_MODIFY_PLUGIN_PORTLET = "modify_portlet";
    private static final String ACTION_MODIFY_PLUGIN_APPLICATION = "modify_plugin_application";
    private static final String ACTION_MODIFY_BUSINESS_CLASS = "modify_business_class";

    //DO MODIFY
    private static final String ACTION_DO_MODIFY_FEATURE = "do_modify_feature";
    private static final String ACTION_DO_MODIFY_PLUGIN_APPLICATION = "do_modify_application";
    private static final String ACTION_DO_MODIFY_PLUGIN_PORTLET = "do_modify_portlet";
    private static final String ACTION_DO_MODIFY_BUSINESS_CLASS = "do_modify_class";

    //REMOVE_ACTIONS
    private static final String ACTION_REMOVE_ADMIN_FEATURE = "remove_admin_feature";
    private static final String ACTION_REMOVE_BUSINESS_ATTRIBUTE = "remove_attribute";
    private static final String ACTION_REMOVE_PLUGIN_PORTLET = "remove_plugin_portlet";
    private static final String ACTION_REMOVE_PLUGIN_APPLICATION = "remove_plugin_application";
    private static final String ACTION_REMOVE_BUSINESS_CLASS = "remove_business_class";

    //REMOVE ACTION
    private static final String ACTION_DO_REMOVE_FEATURE = "do_remove_feature";
    private static final String ACTION_DO_REMOVE_PLUGIN_APPLICATION = "do_remove_application";
    private static final String ACTION_DO_REMOVE_PLUGIN_PORTLET = "do_remove_portlet";
    private static final String ACTION_DO_REMOVE_BUSINESS_CLASS = "do_remove_class";
    private static final String ACTION_DO_PLUGIN_EXISTS = "do_plugin_exists";
    private static final String ACTION_DO_REMOVE_BUSINESS_ATTRIBUTE = "do_remove_attribute";

    //Recapitulate
    private static final String ACTION_GET_RECAPITULATE = "get_recapitulate";
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
    private static final String PROPERTY_CANNOT_CREATE_CLASS_TITLE_MESSAGE = "pluginwizard.siteMessage.classExists.alertMessage";
    private static final String PROPERTY_FEATURE_NOT_DEFINE_TITLE_MESSAGE = "pluginwizard.siteMessage.featureNotExists.alertMessage";
    private static final String PROPERTY_CLASS_NOT_DEFINED_TITLE_MESSAGE = "pluginwizard.siteMessage.classNotDefined.alertMessage";
    private static final String PROPERTY_CLASS_NOT_BEGIN_CAPITAL_TITLE_MESSAGE = "pluginwizard.siteMessage.classNotBeginCapital.alertMessage";
    private static final String PROPERTY_ATTRIBUTE_NOT_DEFINED_TITLE_MESSAGE = "pluginwizard.siteMessage.attributeNotDefined.alertMessage";
    private static final String PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE = "pluginwizard.siteMessage.mandatoryFields.alertMessage";
    private static final String PROPERTY_BUSINESS_CLASS_HAS_A_DESCRIPTION = "pluginwizard.siteMessage.descriptionAlreadyPresent.alertMessage";
    private static final String PROPERTY_BUSINESS_CLASS_HAS_A_KEY = "pluginwizard.siteMessage.keyAlreadyPresent.alertMessage";
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
    private static final String PROPERTY_CLASS_DESCRIPTION_NOT_DEFINED = "pluginwizard.siteMessage.descriptionNotDefined.alertMessage";
    private static final String PROPERTY_CLASS_KEY_NOT_DEFINED = "pluginwizard.siteMessage.keyNotDefined.alertMessage";

    // Properties
    private static final String DEFAULT_PLUGIN_CLASS = "fr.paris.lutece.portal.service.plugin.PluginDefaultImplementation";

    /**
     * getPage
     *
     * @param request The Http Request HttpServletRequest
     * @param nMode int
     * @param plugin Plugin
     * @throws SiteMessageException The front office mechanism for handling the
     * warning
     * @return XPage
     */
    @Override
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException
    {
        XPage page = new XPage(  );

        page.setTitle( I18nService.getLocalizedString( PROPERTY_PAGE_TITLE, request.getLocale(  ) ) );
        page.setPathLabel( I18nService.getLocalizedString( PROPERTY_PAGE_PATH_LABEL, request.getLocale(  ) ) );

        String strContent = null;
        String strAction = getAction( request );

        if ( ( strAction == null ) || strAction.equals( "" ) )
        {
            strContent = getCreatePlugin( request );
            page.setContent( strContent );

            return page;
        }

        if ( strAction.equals( ACTION_DO_REMOVE_FEATURE ) )
        {
            doRemoveAdminFeature( request, plugin );
            strContent = getManageAdminFeatures( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_GET_MODIFY_PLUGIN_DESCRIPTION ) )
        {
            strContent = getModifyPluginDescription( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_GET_MODIFY_PLUGIN ) )
        {
            strContent = getModifyPlugin( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_REMOVE_PLUGIN_APPLICATION ) )
        {
            doRemovePluginApplication( request, plugin );
            strContent = getManagePluginApplications( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_REMOVE_PLUGIN_PORTLET ) )
        {
            doRemovePluginPortlet( request, plugin );
            strContent = getManagePluginPortlets( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_REMOVE_BUSINESS_ATTRIBUTE ) )
        {
            doRemoveAttribute( request, plugin );
            strContent = getModifyBusinessClass( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_REMOVE_BUSINESS_CLASS ) )
        {
            doRemoveBusinessClass( request, plugin );
            strContent = getManageBusinessClasses( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MANAGE_ADMIN_FEATURES ) )
        {
            strContent = getManageAdminFeatures( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MANAGE_BUSINESS_CLASSES ) )
        {
            strContent = getManageBusinessClasses( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_CREATE_FEATURE ) )
        {
            doCreateAdminFeature( request, plugin );
            strContent = getManageAdminFeatures( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_CREATE_PLUGIN_APPLICATION ) )
        {
            doCreatePluginApplication( request, plugin );
            strContent = getManagePluginApplications( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_CREATE_ATTRIBUTE ) )
        {
            doCreateAttribute( request, plugin );
            strContent = getModifyBusinessClass( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_CREATE_PLUGIN_PORTLET ) )
        {
            doCreatePluginPortlet( request, plugin );
            strContent = getManagePluginPortlets( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_CREATE_BUSINESS_CLASS ) )
        {
            int nId = doCreateBusinessClass( request, plugin );
            strContent = getModifyBusinessClass( nId, request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_MODIFY_FEATURE ) )
        {
            doModifyAdminFeature( request, plugin );
            strContent = getManageAdminFeatures( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_MODIFY_BUSINESS_CLASS ) )
        {
            doModifyBusinessClass( request, plugin );
            strContent = getManageBusinessClasses( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_MODIFY_ATTRIBUTE ) )
        {
            doModifyAttribute( request, plugin );
            strContent = getModifyBusinessClass( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_MODIFY_PLUGIN_APPLICATION ) )
        {
            doModifyPluginApplication( request, plugin );
            strContent = getManagePluginApplications( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_MODIFY_PLUGIN_PORTLET ) )
        {
            doModifyPluginPortlet( request, plugin );
            strContent = getManagePluginPortlets( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MODIFY_ADMIN_FEATURE ) )
        {
            strContent = getModifyAdminFeature( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MODIFY_PLUGIN_APPLICATION ) )
        {
            strContent = getModifyPluginApplication( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MODIFY_PLUGIN_PORTLET ) )
        {
            strContent = getModifyPluginPortlet( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MANAGE_PLUGIN_APPLICATIONS ) )
        {
            //If a business class has been defined, verify that key has
            //one and only one key also one and only one description
            strContent = getManagePluginApplications( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MANAGE_PLUGIN_PORTLETS ) )
        {
            strContent = getManagePluginPortlets( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_CREATE_ADMIN_FEATURE ) )
        {
            strContent = getCreateAdminFeature( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_CREATE_ATTRIBUTE ) )
        {
            strContent = getCreateAttribute( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MODIFY_ATTRIBUTE ) )
        {
            strContent = getModifyAttribute( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_CREATE_PLUGIN_APPLICATION ) )
        {
            strContent = getCreatePluginApplication( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_CREATE_PLUGIN_PORTLET ) )
        {
            strContent = getCreatePluginPortlet( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_REMOVE_ADMIN_FEATURE ) )
        {
            getConfirmRemoveAdminFeature( request );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_REMOVE_PLUGIN_APPLICATION ) )
        {
            getConfirmRemovePluginApplication( request );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_REMOVE_BUSINESS_CLASS ) )
        {
            getConfirmRemoveBusinessClass( request );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_REMOVE_BUSINESS_ATTRIBUTE ) )
        {
            getConfirmRemoveBusinessAttribute( request );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_REMOVE_PLUGIN_PORTLET ) )
        {
            getConfirmRemovePluginPortlet( request );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_GET_RECAPITULATE ) )
        {
            strContent = getPluginRecapitulate( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_CREATE_BUSINESS_CLASS ) )
        {
            //If no admin feature present warn user
            strContent = getCreateBusinessClass( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MODIFY_BUSINESS_CLASS ) )
        {
            strContent = getModifyBusinessClass( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_MODIFY_ATTRIBUTE ) )
        {
            strContent = getModifyAttribute( request, plugin );
            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_CREATE_PLUGIN ) )
        {
            String strPluginName = request.getParameter( PARAM_PLUGIN_NAME );

            if ( !PluginModelHome.pluginExists( strPluginName, plugin ) )
            {
                doCreatePlugin( request, plugin );
                page.setContent( getCreatePluginDescription( request, strPluginName, plugin ) );
            }
            else
            {
                page.setContent( getPluginExists( request, strPluginName, plugin ) );
            }

            page.setTitle( I18nService.getLocalizedString( Constants.PROPERTY_PAGE_TITLE_CREATE_PLUGIN_DESCRIPTION,
                    request.getLocale(  ) ) );
            page.setPathLabel( I18nService.getLocalizedString( 
                    Constants.PROPERTY_PAGE_TITLE_CREATE_PLUGIN_DESCRIPTION, request.getLocale(  ) ) );
        }
        else if ( strAction.equals( ACTION_DO_PLUGIN_EXISTS ) )
        {
            String strReset = request.getParameter( PARAM_RESET );

            if ( strReset != null )
            {
                doRemoveAllPluginRelated( request, plugin );
                doCreatePlugin( request, plugin );

                String strPluginName = request.getParameter( PARAM_PLUGIN_NAME );

                strContent = getCreatePluginDescription( request, strPluginName, plugin );
            }
            else
            {
                strContent = getModifyPluginDescription( request, plugin );
            }

            page.setContent( strContent );
        }
        else if ( strAction.equals( ACTION_DO_MODIFY_PLUGIN_DESCRIPTION ) )
        {
            doModifyPlugin( request, plugin );

            page.setContent( getManageAdminFeatures( request, plugin ) );
            page.setTitle( I18nService.getLocalizedString( Constants.PROPERTY_PAGE_TITLE_CREATE_PLUGIN_DESCRIPTION,
                    request.getLocale(  ) ) );
            page.setPathLabel( I18nService.getLocalizedString( 
                    Constants.PROPERTY_PAGE_TITLE_CREATE_PLUGIN_DESCRIPTION, request.getLocale(  ) ) );
        }
        else if ( strAction.equals( ACTION_MANAGE_PLUGIN_APPLICATIONS ) )
        {
            page.setContent( getManagePluginApplications( request, plugin ) );
            page.setTitle( I18nService.getLocalizedString( Constants.PROPERTY_PAGE_TITLE_CREATE_PLUGIN_DESCRIPTION,
                    request.getLocale(  ) ) );
            page.setPathLabel( I18nService.getLocalizedString( 
                    Constants.PROPERTY_PAGE_TITLE_CREATE_PLUGIN_DESCRIPTION, request.getLocale(  ) ) );
        }

        return page;
    }

    /**
     * Gets the action from the request
     * @param request The HTTP request
     * @return The action
     */
    private String getAction( HttpServletRequest request )
    {
        String strAction = request.getParameter( PARAM_ACTION );

        if ( request.getParameter( PARAMETER_GOTO_MANAGE_PLUGIN_APPLICATION ) != null )
        {
            strAction = ACTION_MANAGE_PLUGIN_APPLICATIONS;
        }

        if ( request.getParameter( PARAMETER_GOTO_PLUGIN_DESCRIPTION_MODIFICATION ) != null )
        {
            strAction = ACTION_GET_MODIFY_PLUGIN_DESCRIPTION;
        }

        if ( request.getParameter( PARAMETER_GOTO_CREATE_ADMIN_FEATURE ) != null )
        {
            strAction = ACTION_CREATE_ADMIN_FEATURE;
        }

        if ( request.getParameter( PARAMETER_GOTO_CREATE_ATTRIBUTE ) != null )
        {
            strAction = ACTION_CREATE_ATTRIBUTE;
        }

        if ( request.getParameter( PARAMETER_GOTO_MODIFY_ATTRIBUTE ) != null )
        {
            strAction = ACTION_MODIFY_ATTRIBUTE;
        }

        if ( request.getParameter( PARAMETER_GOTO_MODIFY_BUSINESS_CLASS ) != null )
        {
            strAction = ACTION_MODIFY_BUSINESS_CLASS;
        }

        if ( request.getParameter( PARAMETER_GOTO_MANAGE_ADMIN_FEATURES ) != null )
        {
            strAction = ACTION_MANAGE_ADMIN_FEATURES;
        }

        if ( request.getParameter( PARAMETER_GOTO_MANAGE_RESOURCE_KEYS ) != null )
        {
            strAction = ACTION_MANAGE_RESOURCE_KEYS;
        }

        if ( request.getParameter( PARAMETER_GOTO_MANAGE_BUSINESS_CLASSES ) != null )
        {
            strAction = ACTION_MANAGE_BUSINESS_CLASSES;
        }

        if ( request.getParameter( PARAMETER_GOTO_CREATE_PLUGIN_APPLICATION ) != null )
        {
            strAction = ACTION_CREATE_PLUGIN_APPLICATION;
        }

        if ( request.getParameter( PARAMETER_GOTO_MODIFY_PLUGIN ) != null )
        {
            strAction = ACTION_GET_MODIFY_PLUGIN;
        }

        if ( request.getParameter( PARAMETER_GOTO_MANAGE_PLUGIN_PORTLETS ) != null )
        {
            strAction = ACTION_MANAGE_PLUGIN_PORTLETS;
        }

        if ( request.getParameter( PARAMETER_GOTO_CREATE_PLUGIN_PORTLET ) != null )
        {
            strAction = ACTION_CREATE_PLUGIN_PORTLET;
        }

        if ( request.getParameter( PARAMETER_GOTO_CREATE_BUSINESS_CLASS ) != null )
        {
            strAction = ACTION_CREATE_BUSINESS_CLASS;
        }

        if ( request.getParameter( PARAMETER_GOTO_GET_RECAPITULATE ) != null )
        {
            strAction = ACTION_GET_RECAPITULATE;
        }

        return strAction;
    }

    /**
     * The Creation form of the plugin
     *
     * @param request The Http Request
     * @return The html code of the plugin
     */
    private String getCreatePlugin( HttpServletRequest request )
    {
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_PLUGIN, request.getLocale(  ) );

        return template.getHtml(  );
    }

    /**
     * The creation form of the plugin description
     *
     * @param request The Http Request
     * @param strPluginName The Plugin Name
     * @param plugin The Plugin
     * @return The creation form of the plugin description
     */
    private String getPluginExists( HttpServletRequest request, String strPluginName, Plugin plugin )
    {
        int nPluginId = PluginModelHome.getPluginModelId( plugin, strPluginName );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_NAME, strPluginName );
        model.put( MARK_PLUGIN_ID, nPluginId );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PLUGIN_EXISTS, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Gets the create plugin description page
     * @param request The HTTP request
     * @param strPluginName The plugin name
     * @param plugin The plugin
     * @return The page
     */
    private String getCreatePluginDescription( HttpServletRequest request, String strPluginName, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_PLUGIN_MODEL,
            PluginModelHome.findByPrimaryKey( PluginModelHome.getPluginModelId( plugin, strPluginName ), plugin ) );

        Collection<ConfigurationKey> listKeys = ConfigurationKeyHome.getConfigurationKeysList( plugin );

        for ( ConfigurationKey key : listKeys )
        {
            model.put( key.getKeyDescription(  ).trim(  ), key.getKeyValue(  ) );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_PLUGIN_DESCRIPTION,
                request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The modification form of the plugin description
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the creation of plugin description
     */
    private String getModifyPluginDescription( HttpServletRequest request, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_PLUGIN_DESCRIPTION,
                request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The modification form of the plugin
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the creation of plugin description
     */
    private String getModifyPlugin( HttpServletRequest request, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_PLUGIN, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The modification form of the plugin
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the creation of plugin description
     */
    private String getModifyBusinessClass( HttpServletRequest request, Plugin plugin )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );

        return getModifyBusinessClass( nBusinessClassId, request, plugin );
    }

    /**
     * Gets the modify business class page
     * @param nBusinessClassId The business class id
     * @param request The HTTP request
     * @param plugin The plugin
     * @return The page
     */
    private String getModifyBusinessClass( int nBusinessClassId, HttpServletRequest request, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        BusinessClass bClass = BusinessClassHome.findByPrimaryKey( nBusinessClassId, plugin );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );
        model.put( MARK_BUSINESS_CLASS, bClass );
        model.put( MARK_ADMIN_FEATURES_COMBO, PluginFeatureHome.getAdminFeaturesForPlugin( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_BUSINESS_CLASS, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The modification form of an attribute
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the creation of attribute description
     */
    private String getModifyAttribute( HttpServletRequest request, Plugin plugin )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        int nIdBusinessClass = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        int nIdAttribute = Integer.parseInt( request.getParameter( PARAM_ATTRIBUTE_ID ) );
        Attribute attribute = AttributeHome.findByPrimaryKey( nIdAttribute, plugin );

        model.put( MARK_PLUGIN_ID, nPluginId );
        model.put( MARK_BUSINESS_CLASS_ID, nIdBusinessClass );
        model.put( MARK_ATTRIBUTE_TYPE_COMBO, AttributeHome.getAttributeListCombo( plugin ) );
        model.put( MARK_ATTRIBUTE, attribute );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_ATTRIBUTE, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The modification action of the plugin
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doCreatePlugin( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        //Confirm for deletion of plugin if exists
        String strPluginName = request.getParameter( PARAM_PLUGIN_NAME );

        if ( !PluginModelHome.pluginExists( strPluginName, plugin ) )
        {
            verifyField( request, strPluginName, PROPERTY_DO_CREATE_PLUGIN_PARAM_PLUGIN_NAME,
                PROPERTY_DO_CREATE_PLUGIN_PARAM_PLUGIN_NAME_MESSAGE );

            PluginModel pluginModel = new PluginModel(  );
            pluginModel.setPluginName( strPluginName );
            PluginModelHome.create( pluginModel, plugin );
        }
    }

    /**
     * Removes all the artifacts ralated to the generated plugin
     *
     * @param request The http request
     * @param plugin The plugin
     */
    private void doRemoveAllPluginRelated( HttpServletRequest request, Plugin plugin )
    {
        String strPluginName = request.getParameter( PARAM_PLUGIN_NAME );
        int nIdPlugin = PluginModelHome.getPluginModelId( plugin, strPluginName );

        //Order Attribute,BusinessClass and PluginFeature must be in the same order
        PluginFeatureHome.removeFeaturesByPlugin( nIdPlugin, plugin );
        PluginApplicationHome.removeApplicationsByPlugin( nIdPlugin, plugin );
        PluginPortletHome.removePortletsByPlugin( nIdPlugin, plugin );
        ResourceKeyHome.deleteKeysByPlugin( nIdPlugin, plugin );
    }

    /**
     * The modification action of the plugin
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doModifyPlugin( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        PluginModel model = new PluginModel(  );

        //add the attributes from the request hash
        model.setIdPlugin( nId );

        String strPluginName = request.getParameter( PARAM_PLUGIN_NAME );
        String strDescription = request.getParameter( PARAM_PLUGIN_DESCRIPTION );
        String strProvider = request.getParameter( PARAM_PLUGIN_PROVIDER );

        String strPoolRequired = request.getParameter( PARAM_PLUGIN_DB_POOL_REQUIRED );
        verifyField( request, strPoolRequired, PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_DB_POOL_REQUIRED,
            PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_DB_POOL_REQUIRED_MESSAGE );
        model.setPluginDbPoolRequired( strPoolRequired );

        model.setPluginName( strPluginName );
        model.setPluginClass( DEFAULT_PLUGIN_CLASS );
        model.setPluginDescription( strDescription );
        model.setPluginProvider( strProvider );
        model.setPluginVersion( request.getParameter( PARAM_PLUGIN_VERSION ) );
        model.setPluginIconUrl( "images/admin/skin/plugins/" + strPluginName + "/" + strPluginName + ".png" );
        model.setPluginCopyright( request.getParameter( PARAM_PLUGIN_COPYRIGHT ) );

        String strPluginProviderUrl = request.getParameter( PARAM_PLUGIN_PROVIDER_URL );
        verifyField( request, strPluginProviderUrl, PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_PROVIDER_URL,
            PROPERTY_DO_MODIFY_PLUGIN_PARAM_PLUGIN_PROVIDER_URL_MESSAGE );

        model.setPluginProviderUrl( request.getParameter( PARAM_PLUGIN_PROVIDER_URL ) );

        // deprecated
        model.setPluginUserGuide( "" );
        model.setPluginDocumentation( "" );
        model.setPluginInstallation( "" );
        model.setPluginChanges( "" );

        PluginModelHome.update( model, plugin );
    }

    /**
     * The management screen of the admin features
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the admin features
     */
    private String getManageAdminFeatures( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_PLUGIN_ID, Integer.toString( nPluginId ) );
        model.put( MARK_ADMIN_FEATURES, PluginFeatureHome.findByPlugin( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_ADMIN_FEATURES, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The creation form of the admin feature
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the admin feature
     */
    private String getCreateAdminFeature( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_ADMIN_FEATURE, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The creation form of the attribute associated to a business class
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the admin feature
     */
    private String getCreateAttribute( HttpServletRequest request, Plugin plugin )
    {
        String strPluginId = request.getParameter( PARAM_PLUGIN_ID );
        String strBusinessClassId = request.getParameter( PARAM_BUSINESS_CLASS_ID );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_BUSINESS_CLASS_ID, strBusinessClassId );
        model.put( MARK_PLUGIN_ID, strPluginId );
        model.put( MARK_ATTRIBUTE_TYPE_COMBO, AttributeHome.getAttributeListCombo( plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_ATTRIBUTE, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The creation of an admin feature
     *
     * @param request The Http Request
     * @param plugin The Plugin
     */
    private void doCreateAdminFeature( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );

        String strPluginFeatureRight = request.getParameter( PARAM_FEATURE_RIGHT );
        String strPluginFeatureTitle = request.getParameter( PARAM_FEATURE_TITLE );
        String strPluginFeatureDescription = request.getParameter( PARAM_FEATURE_DESCRITPION );
        String strPluginFeatureLevel = request.getParameter( PARAM_FEATURE_LEVEL );
        String strPluginFeatureName = request.getParameter( PARAM_FEATURE_NAME );
        PluginFeature pluginFeature = new PluginFeature(  );

        pluginFeature.setIdPlugin( nPluginId );
        pluginFeature.setPluginFeatureRight( strPluginFeatureRight );
        pluginFeature.setPluginFeatureTitle( strPluginFeatureTitle );
        pluginFeature.setPluginFeatureDescription( strPluginFeatureDescription );
        pluginFeature.setPluginFeatureLevel( strPluginFeatureLevel );
        pluginFeature.setPluginFeatureName( strPluginFeatureName );
        PluginFeatureHome.create( pluginFeature, plugin );
    }

    /**
     * The modification screen of the admin feature
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the admin feature
     */
    private String getModifyAdminFeature( HttpServletRequest request, Plugin plugin )
    {
        int nIdPlugin = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        int nAdminFeature = Integer.parseInt( request.getParameter( PARAM_FEATURE_ID ) );
        PluginFeature feature = PluginFeatureHome.findByPrimaryKey( nAdminFeature, plugin );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_FEATURE, feature );
        model.put( MARK_PLUGIN_ID, nIdPlugin );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_ADMIN_FEATURE, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The modification action of an admin feature
     *
     * @param request The Http Request
     * @param plugin The Plugin
     */
    private void doModifyAdminFeature( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        int nIdPluginFeature = Integer.parseInt( request.getParameter( PARAM_FEATURE_ID ) );
        String strPluginFeatureDescription = request.getParameter( PARAM_FEATURE_DESCRITPION );

        String strPluginFeatureLabel = request.getParameter( PARAM_FEATURE_RIGHT );
        String strPluginFeatureLevel = request.getParameter( PARAM_FEATURE_LEVEL );
        String strPluginFeatureTitle = request.getParameter( PARAM_FEATURE_TITLE );
        String strPluginFeatureUrl = request.getParameter( PARAM_FEATURE_NAME );

        PluginFeature pluginFeature = new PluginFeature(  );
        pluginFeature.setIdPluginFeature( nIdPluginFeature );
        pluginFeature.setIdPlugin( nPluginId );
        pluginFeature.setPluginFeatureDescription( strPluginFeatureDescription );
        pluginFeature.setPluginFeatureRight( strPluginFeatureLabel );
        pluginFeature.setPluginFeatureLevel( strPluginFeatureLevel );
        pluginFeature.setPluginFeatureTitle( strPluginFeatureTitle );
        pluginFeature.setPluginFeatureName( strPluginFeatureUrl );
        PluginFeatureHome.update( pluginFeature, plugin );
    }

    /**
     * The confirmation of the removal of an admin feature
     *
     * @param request The Http Request
     * @throws SiteMessageException The front office mechanism for handling the
     * warning
     */
    private void getConfirmRemoveAdminFeature( HttpServletRequest request )
        throws SiteMessageException
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );

        url.addParameter( PARAM_PAGE, PROPERTY_PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_DO_REMOVE_FEATURE );
        url.addParameter( PARAM_FEATURE_ID, request.getParameter( PARAM_FEATURE_ID ) );
        url.addParameter( PARAM_PLUGIN_ID, request.getParameter( PARAM_PLUGIN_ID ) );

        SiteMessageService.setMessage( request, PROPERTY_CONFIRM_REMOVE_FEATURE_ALERT_MESSAGE, null,
            PROPERTY_CONFIRM_REMOVE_FEATURE_TITLE_MESSAGE, url.getUrl(  ), null, SiteMessage.TYPE_CONFIRMATION );
    }

    /**
     * The removal screen of an admin feature
     *
     * @param request The Http Request
     * @param plugin The Plugin
     */
    private void doRemoveAdminFeature( HttpServletRequest request, Plugin plugin )
    {
        int nFeatureId = Integer.parseInt( request.getParameter( PARAM_FEATURE_ID ) );
        PluginFeatureHome.remove( nFeatureId, plugin );
    }

    /**
    * The management of the plugin applications associated to the generated
    * plugin
    *
    * @param request The Http Request
    * @param plugin The Plugin
    * @return The html code of the management screen of the applications
    * @throws SiteMessageException if an error occurs
    */
    private String getManagePluginApplications( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );

        Collection<BusinessClass> listBusinessClass = BusinessClassHome.getBusinessClassesByPlugin( nPluginId, plugin );

        for ( BusinessClass businessClass : listBusinessClass )
        {
            if ( businessClass.getAttributes(  ).size(  ) < 2 )
            {
                SiteMessageService.setMessage( request, PROPERTY_CLASS_TWO_ATTRIBUTES_MINIMUM, SiteMessage.TYPE_STOP );
            }

            if ( !BusinessClassHome.hasAlreadyDescription( businessClass.getIdBusinessClass(  ), plugin ) )
            {
                SiteMessageService.setMessage( request, PROPERTY_CLASS_DESCRIPTION_NOT_DEFINED, SiteMessage.TYPE_STOP );
            }

            if ( !BusinessClassHome.hasAlreadyKey( businessClass.getIdBusinessClass(  ), plugin ) )
            {
                SiteMessageService.setMessage( request, PROPERTY_CLASS_KEY_NOT_DEFINED, SiteMessage.TYPE_STOP );
            }
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( nPluginId ) );
        model.put( MARK_PLUGIN_APPLICATIONS, PluginApplicationHome.findByPlugin( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_PLUGIN_APPLICATIONS,
                request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The management screen of business classes associated to the plugin which
     * is generated
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the management screen of the business classes
     */
    private String getManageBusinessClasses( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( nPluginId ) );
        model.put( MARK_BUSINESS_CLASSES, BusinessClassHome.getBusinessClassesByPlugin( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_BUSINESS_CLASSES,
                request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The creation screen of a plugin application
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of a plugin application
     */
    private String getCreatePluginApplication( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_PLUGIN_APPLICATION,
                request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The creation form of a business class
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the creation of a business class
     * @throws SiteMessageException Front office error handling
     */
    private String getCreateBusinessClass( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );

        if ( PluginFeatureHome.getAdminFeaturesForPlugin( nPluginId, plugin ).isEmpty(  ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_FEATURE_NOT_DEFINE_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );
        model.put( MARK_ADMIN_FEATURES_COMBO, PluginFeatureHome.getAdminFeaturesForPlugin( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_BUSINESS_CLASS, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The creation action of the plugin application
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doCreatePluginApplication( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        PluginApplication application = new PluginApplication(  );
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        String strApplicationClass = request.getParameter( PARAM_APPLICATION_CLASS );
        String strApplicationName = request.getParameter( PARAM_APPLICATION_NAME );

        if ( ( strApplicationClass == null ) || strApplicationClass.equals( "" ) || ( strApplicationName == null ) ||
                strApplicationName.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        application.setApplicationClass( strApplicationClass );
        application.setApplicationName( strApplicationName );
        application.setIdPlugin( nPluginId );
        PluginApplicationHome.create( application, plugin );
    }

    /**
     * The creation action of an attribute
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @exception SiteMessageException Front office error handling
     */
    private void doCreateAttribute( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        String strAttributeName = request.getParameter( PARAM_ATTRIBUTE_NAME );

        if ( ( strAttributeName == null ) || strAttributeName.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_ATTRIBUTE_NOT_DEFINED_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        String strAttributeTypeId = request.getParameter( PARAM_ATTRIBUTE_TYPE_ID );
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        Integer nAttributeTypeId = Integer.parseInt( strAttributeTypeId );

        //If Business class already has a primary key
        String strPrimaryKey = request.getParameter( PARAM_PRIMARY_KEY );
        String strDescription = request.getParameter( PARAM_CLASS_DESCRIPTION );

        //If primary key is not an int
        if ( strPrimaryKey.equals( "1" ) && !strAttributeTypeId.equals( "1" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_BUSINESS_PRIMARY_KEY_MUST_BE_INT, SiteMessage.TYPE_STOP );
        }

        //If description key is not a string
        if ( strDescription.equals( "1" ) && !strAttributeTypeId.equals( "2" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_BUSINESS_DESCRIPTION_MUST_BE_STRING, SiteMessage.TYPE_STOP );
        }

        //If Business class already has a description
        if ( strDescription.equals( "1" ) && BusinessClassHome.hasAlreadyDescription( nBusinessClassId, plugin ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_BUSINESS_CLASS_HAS_A_DESCRIPTION, SiteMessage.TYPE_STOP );
        }

        if ( strPrimaryKey.equals( "1" ) && BusinessClassHome.hasAlreadyKey( nBusinessClassId, plugin ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_BUSINESS_CLASS_HAS_A_KEY, SiteMessage.TYPE_STOP );
        }

        Attribute attribute = new Attribute(  );
        attribute.setAttributeName( strAttributeName );
        attribute.setAttributeTypeId( nAttributeTypeId );
        attribute.setIsPrimary( ( ( strPrimaryKey != null ) && strPrimaryKey.equals( "1" ) &&
            strDescription.equals( "0" ) ) ? true : false );
        attribute.setIsDescription( ( ( strDescription != null ) && strDescription.equals( "1" ) &&
            strPrimaryKey.equals( "0" ) ) ? true : false );
        attribute.setBusinessClassId( nBusinessClassId );

        AttributeHome.create( attribute, plugin );
    }

    /**
     * The modification screen of a plugin application
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the modification of an application associated to
     * the generated plugin
     */
    private String getModifyPluginApplication( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        int nPluginApplicationId = Integer.parseInt( request.getParameter( PARAM_APPLICATION_ID ) );
        PluginApplication application = PluginApplicationHome.findByPrimaryKey( nPluginApplicationId, plugin );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_APPLICATION, application );
        model.put( MARK_PLUGIN_ID, Integer.toString( nPluginId ) );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_PLUGIN_APPLICATION,
                request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * The modification action of the plugin application
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doModifyPluginApplication( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        int nApplicationId = Integer.parseInt( request.getParameter( PARAM_APPLICATION_ID ) );
        PluginApplication application = PluginApplicationHome.findByPrimaryKey( nApplicationId, plugin );
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        String strApplicationClass = request.getParameter( PARAM_APPLICATION_CLASS );
        String strApplicationName = request.getParameter( PARAM_APPLICATION_NAME );

        if ( ( strApplicationClass == null ) || strApplicationClass.equals( "" ) || ( strApplicationName == null ) ||
                strApplicationName.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        application.setApplicationClass( strApplicationClass );
        application.setApplicationName( strApplicationName );
        application.setIdPlugin( nPluginId );

        PluginApplicationHome.update( application, plugin );
    }

    /**
     * The confirmation of an application removal
     *
     * @param request The Http Request
     * @throws SiteMessageException The front office exception
     */
    private void getConfirmRemovePluginApplication( HttpServletRequest request )
        throws SiteMessageException
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PROPERTY_PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_DO_REMOVE_PLUGIN_APPLICATION );
        url.addParameter( PARAM_APPLICATION_ID, request.getParameter( PARAM_APPLICATION_ID ) );
        url.addParameter( PARAM_PLUGIN_ID, request.getParameter( PARAM_PLUGIN_ID ) );
        SiteMessageService.setMessage( request, PROPERTY_CONFIRM_REMOVE_APPLICATION_ALERT_MESSAGE, null,
            PROPERTY_CONFIRM_REMOVE_APPLICATION_TITLE_MESSAGE, url.getUrl(  ), null, SiteMessage.TYPE_CONFIRMATION );
    }

    /**
     * The confirmation of a business class removal
     *
     * @param request The Http Request
     * @throws SiteMessageException The front office exception
     */
    private void getConfirmRemoveBusinessClass( HttpServletRequest request )
        throws SiteMessageException
    {
        String strBusinessClassId = request.getParameter( PARAM_BUSINESS_CLASS_ID );
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PROPERTY_PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_DO_REMOVE_BUSINESS_CLASS );
        url.addParameter( PARAM_BUSINESS_CLASS_ID, strBusinessClassId );
        url.addParameter( PARAM_FEATURE_ID, request.getParameter( PARAM_FEATURE_ID ) );
        url.addParameter( PARAM_PLUGIN_ID, request.getParameter( PARAM_PLUGIN_ID ) );

        SiteMessageService.setMessage( request, PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_ALERT_MESSAGE, null,
            PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_TITLE_MESSAGE, url.getUrl(  ), null, SiteMessage.TYPE_CONFIRMATION );
    }

    /**
     * The removal action of a plugin application
     *
     * @param request The Http Request
     * @param plugin The Plugin
     */
    private void doRemovePluginApplication( HttpServletRequest request, Plugin plugin )
    {
        int nApplicationId = Integer.parseInt( request.getParameter( PARAM_APPLICATION_ID ) );
        PluginApplicationHome.remove( nApplicationId, plugin );
    }

    /**
     * The removal action of a plugin application
     *
     * @param request The Http Request
     * @param plugin The Plugin
     */
    private void doRemoveBusinessClass( HttpServletRequest request, Plugin plugin )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );

        BusinessClassHome.remove( nBusinessClassId, plugin );
    }

    /**
     * The screen for management of portlets associated to the generated plugin
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The main management screen of portlets
     */
    private String getManagePluginPortlets( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( nPluginId ) );
        model.put( MARK_PLUGIN_PORTLETS, PluginPortletHome.findByPlugin( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_PLUGIN_PORTLETS, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The creation screen of a portlet
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the creation of a portlet
     */
    private String getCreatePluginPortlet( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_PLUGIN_PORTLET, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The creation action of the portlet
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doCreatePluginPortlet( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        PluginPortlet portlet = new PluginPortlet(  );
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        portlet.setIdPlugin( nPluginId );

        String strPluginPortletClass = request.getParameter( PARAM_PORTLET_CLASS );
        String strPluginPortletCreationUrl = request.getParameter( PARAM_PORTLET_CREATION_URL );
        String strPluginPortletTypeName = request.getParameter( PARAM_PORTLET_TYPE_NAME );
        //verify that the type of portlet respects the pattern
        verifyField( request, strPluginPortletTypeName, PROPERTY_DO_CREATE_PORTLET_PARAM_PORTLET_TYPE_NAME,
            PROPERTY_DO_CREATE_PORTLET_PARAM_PORTLET_TYPE_NAME_MESSAGE );

        String strPluginPortletUpdateUrl = request.getParameter( PARAM_PORTLET_UPDATE_URL );

        if ( ( strPluginPortletClass == null ) || ( strPluginPortletCreationUrl == null ) ||
                ( strPluginPortletTypeName == null ) || ( strPluginPortletUpdateUrl == null ) ||
                strPluginPortletClass.equals( "" ) || strPluginPortletCreationUrl.equals( "" ) ||
                strPluginPortletTypeName.equals( "" ) || strPluginPortletUpdateUrl.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        portlet.setPluginPortletClass( strPluginPortletClass );
        portlet.setPluginPortletCreationUrl( strPluginPortletCreationUrl );
        portlet.setPluginPortletTypeName( strPluginPortletTypeName );
        portlet.setPluginPortletUpdateUrl( strPluginPortletUpdateUrl );

        PluginPortletHome.create( portlet, plugin );
    }

    /**
     * The creation action of the business class
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     * @return The business class id
     */
    private int doCreateBusinessClass( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        BusinessClass businessClass = new BusinessClass(  );
        String strFeatureId = request.getParameter( PARAM_FEATURE_ID );
        String strBusinessClassName = request.getParameter( PARAM_CLASSNAME );

        if ( ( strFeatureId == null ) || strFeatureId.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_FEATURE_NOT_DEFINE_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        if ( ( strBusinessClassName == null ) || strBusinessClassName.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_CLASS_NOT_DEFINED_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        if ( !beginsWithCapital( strBusinessClassName ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_CLASS_NOT_BEGIN_CAPITAL_TITLE_MESSAGE,
                SiteMessage.TYPE_STOP );
        }

        int nFeatureId = Integer.parseInt( strFeatureId );
        businessClass.setIdFeature( nFeatureId );

        String strTableName = request.getParameter( PARAM_TABLE );

        if ( ( strBusinessClassName == null ) || ( strTableName == null ) || strBusinessClassName.equals( "" ) ||
                strTableName.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        businessClass.setBusinessClass( strBusinessClassName );
        businessClass.setBusinessTableName( strTableName );

        //Verify if name already exists for the same admin feature
        if ( BusinessClassHome.keyExists( nFeatureId, strBusinessClassName, plugin ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_CANNOT_CREATE_CLASS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        businessClass = BusinessClassHome.create( businessClass, plugin );

        return businessClass.getIdBusinessClass(  );
    }

    /**
     * Tests whether string begins with a capital letter
     *
     * @param str String to test
     * @return a boolean value
     */
    private boolean beginsWithCapital( String str )
    {
        char cFirst = str.charAt( 0 );

        return Character.isUpperCase( cFirst );
    }

    /**
     * The modification page of the portlet
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The html code of the modification of the portlet
     */
    private String getModifyPluginPortlet( HttpServletRequest request, Plugin plugin )
    {
        int nPluginPortletId = Integer.parseInt( request.getParameter( PARAM_PORTLET_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_PORTLET, PluginPortletHome.findByPrimaryKey( nPluginPortletId, plugin ) );
        AppLogService.info( PluginPortletHome.findByPrimaryKey( nPluginPortletId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_PLUGIN_PORTLET, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * The modification action of the portlet
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doModifyPluginPortlet( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        String strPortletId = request.getParameter( PARAM_PORTLET_ID );
        int nPluginPortletId = Integer.parseInt( strPortletId );
        String strPluginPortletClass = request.getParameter( PARAM_PORTLET_CLASS );
        String strPluginPortletCreationUrl = request.getParameter( PARAM_PORTLET_CREATION_URL );
        String strPluginPortletTypeName = request.getParameter( PARAM_PORTLET_TYPE_NAME );
        String strPluginPortletUpdateUrl = request.getParameter( PARAM_PORTLET_UPDATE_URL );

        if ( ( strPluginPortletClass == null ) || ( strPluginPortletCreationUrl == null ) ||
                ( strPluginPortletTypeName == null ) || ( strPluginPortletUpdateUrl == null ) ||
                strPluginPortletClass.equals( "" ) || strPluginPortletCreationUrl.equals( "" ) ||
                strPluginPortletTypeName.equals( "" ) || strPluginPortletUpdateUrl.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        //verify that the type of portlet respects the pattern
        verifyField( request, strPluginPortletTypeName, PROPERTY_DO_MODIFY_PORTLET_PARAM_PORTLET_TYPE_NAME,
            PROPERTY_DO_MODIFY_PORTLET_PARAM_PORTLET_TYPE_NAME_MESSAGE );

        PluginPortlet portlet = new PluginPortlet(  );
        portlet.setPluginPortletClass( strPluginPortletClass );
        portlet.setPluginPortletCreationUrl( strPluginPortletCreationUrl );
        portlet.setPluginPortletId( nPluginPortletId );
        portlet.setPluginPortletTypeName( strPluginPortletTypeName );
        portlet.setPluginPortletUpdateUrl( strPluginPortletUpdateUrl );
        portlet.setIdPlugin( nPluginId );
        PluginPortletHome.update( portlet, plugin );
    }

    /**
     * The modification action for the business class
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doModifyBusinessClass( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        BusinessClass businessClass = BusinessClassHome.findByPrimaryKey( nBusinessClassId, plugin );

        String strFeatureId = request.getParameter( PARAM_FEATURE_ID );

        if ( ( strFeatureId == null ) || strFeatureId.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_FEATURE_NOT_DEFINE_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        int nFeatureId = Integer.parseInt( strFeatureId );
        businessClass.setIdFeature( nFeatureId );

        String strBusinessClassName = request.getParameter( PARAM_CLASSNAME );

        String strTableName = request.getParameter( PARAM_TABLE );

        if ( ( strBusinessClassName == null ) || ( strTableName == null ) || strBusinessClassName.equals( "" ) ||
                strTableName.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_MANDATORY_FIELDS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        businessClass.setBusinessClass( strBusinessClassName );
        businessClass.setBusinessTableName( strTableName );

        //Verify if name already exists for the same admin feature
        if ( BusinessClassHome.keyExists( nFeatureId, strBusinessClassName, plugin ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_CANNOT_CREATE_CLASS_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        BusinessClassHome.update( businessClass, plugin );
    }

    /**
     * The modification action for the attribute
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @throws SiteMessageException Front office error handling
     */
    private void doModifyAttribute( HttpServletRequest request, Plugin plugin )
        throws SiteMessageException
    {
        int nIdAttribute = Integer.parseInt( request.getParameter( PARAM_ATTRIBUTE_ID ) );

        String strAttributeName = request.getParameter( PARAM_ATTRIBUTE_NAME );

        if ( ( strAttributeName == null ) || strAttributeName.equals( "" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_ATTRIBUTE_NOT_DEFINED_TITLE_MESSAGE, SiteMessage.TYPE_STOP );
        }

        String strAttributeTypeId = request.getParameter( PARAM_ATTRIBUTE_TYPE_ID );
        Integer nAttributeTypeId = Integer.parseInt( strAttributeTypeId );

        //If Business class already has a primary key
        String strPrimaryKey = request.getParameter( PARAM_PRIMARY_KEY );
        String strDescription = request.getParameter( PARAM_CLASS_DESCRIPTION );

        //If primary key is not an int
        if ( strPrimaryKey.equals( "1" ) && !strAttributeTypeId.equals( "1" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_BUSINESS_PRIMARY_KEY_MUST_BE_INT, SiteMessage.TYPE_STOP );
        }

        //If description key is not a string
        if ( strDescription.equals( "1" ) && !strAttributeTypeId.equals( "2" ) )
        {
            SiteMessageService.setMessage( request, PROPERTY_BUSINESS_DESCRIPTION_MUST_BE_STRING, SiteMessage.TYPE_STOP );
        }

        Attribute attribute = AttributeHome.findByPrimaryKey( nIdAttribute, plugin );
        attribute.setAttributeName( strAttributeName );
        attribute.setAttributeTypeId( nAttributeTypeId );
        attribute.setIsPrimary( ( ( strPrimaryKey != null ) && strPrimaryKey.equals( "1" ) &&
            strDescription.equals( "0" ) ) ? true : false );
        attribute.setIsDescription( ( ( strDescription != null ) && strDescription.equals( "1" ) &&
            strPrimaryKey.equals( "0" ) ) ? true : false );
        AppLogService.error( "strAttributeName:" + strAttributeName + "\nAttributeTypeId:" + nAttributeTypeId +
            "\nstrPrimaryKey:" + strPrimaryKey + "\nstrDescription:" + strDescription );

        AttributeHome.update( attribute, plugin );
    }

    /**
     * The confirmation of the attribute removal
     *
     * @param request The Http Request
     * @throws SiteMessageException The site message exception
     */
    private void getConfirmRemoveBusinessAttribute( HttpServletRequest request )
        throws SiteMessageException
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PROPERTY_PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_DO_REMOVE_BUSINESS_ATTRIBUTE );
        url.addParameter( PARAM_ATTRIBUTE_ID, request.getParameter( PARAM_ATTRIBUTE_ID ) );
        url.addParameter( PARAM_BUSINESS_CLASS_ID, request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        url.addParameter( PARAM_PLUGIN_ID, request.getParameter( PARAM_PLUGIN_ID ) );
        SiteMessageService.setMessage( request, PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_ALERT_MESSAGE, null,
            PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_TITLE_MESSAGE, url.getUrl(  ), null, SiteMessage.TYPE_CONFIRMATION );
    }

    /**
     * The confirmation of the plugin removal
     *
     * @param request The Http Request
      * @throws SiteMessageException The site message exception
     */
    private void getConfirmRemovePluginPortlet( HttpServletRequest request )
        throws SiteMessageException
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PROPERTY_PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_DO_REMOVE_PLUGIN_PORTLET );
        url.addParameter( PARAM_PORTLET_ID, request.getParameter( PARAM_PORTLET_ID ) );
        url.addParameter( PARAM_PLUGIN_ID, request.getParameter( PARAM_PLUGIN_ID ) );
        SiteMessageService.setMessage( request, PROPERTY_CONFIRM_REMOVE_PORTLET_ALERT_MESSAGE, null,
            PROPERTY_CONFIRM_REMOVE_PORTLET_TITLE_MESSAGE, url.getUrl(  ), null, SiteMessage.TYPE_CONFIRMATION );
    }

    /**
     * Remove Portlet Action
     *
     * @param request The Http Request
     * @param plugin The Plugin
     */
    private void doRemovePluginPortlet( HttpServletRequest request, Plugin plugin )
    {
        int nPluginPortletId = Integer.parseInt( request.getParameter( PARAM_PORTLET_ID ) );
        PluginPortletHome.remove( nPluginPortletId, plugin );
    }

    /**
     * Remove Business Attribute
     *
     * @param request The Http Request
     * @param plugin The Plugin
     */
    private void doRemoveAttribute( HttpServletRequest request, Plugin plugin )
    {
        int nAttributeId = Integer.parseInt( request.getParameter( PARAM_ATTRIBUTE_ID ) );
        AttributeHome.remove( nAttributeId, plugin );
    }

    /**
     * The get page of the plugin recapitulation
     *
     * @param request The Http Request
     * @param plugin The Plugin
     * @return The Html code of the summary
     */
    private String getPluginRecapitulate( HttpServletRequest request, Plugin plugin )
    {
        int nPluginId = Integer.parseInt( request.getParameter( PARAM_PLUGIN_ID ) );
        //Deletes all the keys and regenerate the keys for the generated plugin
        ResourceKeyHome.deleteKeysByPlugin( nPluginId, plugin );
        ResourceKeyService.storeKeys( nPluginId, plugin );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( nPluginId ) );
        model.put( MARK_PLUGIN_MODEL, PluginModelHome.findByPrimaryKey( nPluginId, plugin ) );
        model.put( MARK_PLUGIN_APPLICATIONS, PluginApplicationHome.findByPlugin( nPluginId, plugin ) );
        model.put( MARK_ADMIN_FEATURES, PluginFeatureHome.findByPlugin( nPluginId, plugin ) );
        model.put( MARK_PLUGIN_PORTLETS, PluginPortletHome.findByPlugin( nPluginId, plugin ) );
        model.put( MARK_BUSINESS_CLASSES, BusinessClassHome.getBusinessClassesByPlugin( nPluginId, plugin ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_GET_RECAPITULATE, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Verifies a field by using a regex
     *
     * @param request The Http request
     * @param strFieldInputText The input field content
     * @param strRegexProperty The property corresponding to the regex
     * @param strErrorMessageProperty The error message property
     * @throws SiteMessageException Front office error handling
     */
    private void verifyField( HttpServletRequest request, String strFieldInputText, String strRegexProperty,
        String strErrorMessageProperty ) throws SiteMessageException
    {
        String strRegex = AppPropertiesService.getProperty( strRegexProperty );

        if ( ( strFieldInputText == null ) || !strFieldInputText.matches( strRegex ) )
        {
            SiteMessageService.setMessage( request, strErrorMessageProperty, SiteMessage.TYPE_STOP );
        }
    }
}
