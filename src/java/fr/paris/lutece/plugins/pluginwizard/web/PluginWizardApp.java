/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice
 *   and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice
 *   and the following disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 *
 * 3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
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
import fr.paris.lutece.plugins.mvc.MVCMessageBox;
import fr.paris.lutece.plugins.mvc.annotations.Action;
import fr.paris.lutece.plugins.mvc.annotations.Controller;
import fr.paris.lutece.plugins.mvc.annotations.View;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKey;
import fr.paris.lutece.plugins.pluginwizard.business.ConfigurationKeyHome;
import fr.paris.lutece.plugins.pluginwizard.business.ModelHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.Application;
import fr.paris.lutece.plugins.pluginwizard.business.model.Attribute;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.business.model.Portlet;
import fr.paris.lutece.plugins.pluginwizard.service.ModelService;
import fr.paris.lutece.plugins.pluginwizard.service.generator.GeneratorService;
import fr.paris.lutece.plugins.pluginwizard.web.formbean.BusinessClassFormBean;
import fr.paris.lutece.plugins.pluginwizard.web.formbean.DescriptionFormBean;
import fr.paris.lutece.plugins.pluginwizard.web.formbean.PluginNameFormBean;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * The class manage pluginwizard Page
 */
@Controller( xpageName = "pluginwizard", pagePathI18nKey = "pluginwizard.pagePathLabel", pageTitleI18nKey = "pluginwizard.pageTitle" )
public class PluginWizardApp extends MVCApplication
{
    //Constants
    private static final String MARK_PLUGIN_ID = "plugin_id";
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
    private static final String MARK_ATTRIBUTES_LIST = "attributes_list";

    //Modification bookmarks
    private static final String MARK_FEATURE = "feature";
    private static final String MARK_ATTRIBUTE = "attribute";
    private static final String MARK_APPLICATION = "application";
    private static final String MARK_PORTLET = "portlet";
    private static final String TEMPLATE_CREATE_PLUGIN = "/skin/plugins/pluginwizard/pluginwizard_create_plugin.html";
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
    private static final String TEMPLATE_MESSAGE_BOX_CONFIRMATION = "/skin/plugins/pluginwizard/message_confirmation.html";
    private static final String TEMPLATE_MESSAGE_BOX_EXISTS = "/skin/plugins/pluginwizard/message_exists.html";
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_BUSINESS_CLASS_ID = "business_class_id";
    private static final String PARAM_ATTRIBUTE_ID = "attribute_id";
    private static final String PARAM_FEATURE_ID = "feature_id";
    private static final String PARAM_APPLICATION_ID = "application_id";
    private static final String PARAM_PORTLET_ID = "portletId";
    private static final String PARAM_REFRESH = "refresh";

    // PLUGIN DESCRIPTION
    private static final String VIEW_CREATE_PLUGIN = "createPlugin";
    private static final String VIEW_MODIFY_PLUGIN = "modifyPlugin";
    private static final String VIEW_CREATE_DESCRIPTION = "createDescription";
    private static final String VIEW_MODIFY_DESCRIPTION = "modifyDescription";
    private static final String ACTION_CREATE_PLUGIN = "createPlugin";
    private static final String ACTION_DESCRIPTION_PREVIOUS = "descriptionPrevious";
    private static final String ACTION_DESCRIPTION_NEXT = "descriptionNext";
    private static final String ACTION_RESET_DATA = "resetData";

    // ADMIN FEATURES
    private static final String VIEW_MANAGE_ADMIN_FEATURES = "manageAdminFeatures";
    private static final String VIEW_CREATE_ADMIN_FEATURE = "createAdminFeature";
    private static final String VIEW_MODIFY_ADMIN_FEATURE = "modifyAdminFeature";
    private static final String ACTION_CREATE_ADMIN_FEATURE = "createAdminFeature";
    private static final String ACTION_MODIFY_ADMIN_FEATURE = "modifyAdminFeature";
    private static final String ACTION_CONFIRM_REMOVE_ADMIN_FEATURE = "confirmRemoveAdminFeature";
    private static final String ACTION_REMOVE_ADMIN_FEATURE = "removeAdminFeature";
    private static final String PROPERTY_CONFIRM_REMOVE_FEATURE_MESSAGE = "pluginwizard.siteMessage.confirmRemoveFeature.alertMessage";

    // BUSINESS CLASS
    private static final String VIEW_MANAGE_BUSINESS_CLASSES = "manageBusinessClasses";
    private static final String VIEW_CREATE_BUSINESS_CLASS = "createBusinessClass";
    private static final String VIEW_MODIFY_BUSINESS_CLASS = "modifyBusinessClass";
    private static final String ACTION_CREATE_BUSINESS_CLASS = "createBusinessClass";
    private static final String ACTION_MODIFY_BUSINESS_CLASS = "modifyBusinessClass";
    private static final String ACTION_CONFIRM_REMOVE_BUSINESS_CLASS = "confirmRemoveBusinessClass";
    private static final String ACTION_REMOVE_BUSINESS_CLASS = "removeBusinessClass";
    private static final String PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_MESSAGE = "pluginwizard.siteMessage.confirmRemoveBusinessClass.title";

    // ATTRIBUTE
    private static final String VIEW_CREATE_ATTRIBUTE = "createAttribute";
    private static final String VIEW_MODIFY_ATTRIBUTE = "modifyAttribute";
    private static final String ACTION_CREATE_ATTRIBUTE = "createAttribute";
    private static final String ACTION_MODIFY_ATTRIBUTE = "modifyAttribute";
    private static final String ACTION_CONFIRM_REMOVE_ATTRIBUTE = "confirmRemoveAttribute";
    private static final String ACTION_REMOVE_ATTRIBUTE = "removeAttribute";
    private static final String ACTION_VALIDATE_ATTRIBUTES = "validateAttributes";
    private static final String PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_MESSAGE = "pluginwizard.siteMessage.confirmRemoveAttribute.alertMessage";

    // APPLICATION
    private static final String VIEW_MANAGE_APPLICATIONS = "manageApplications";
    private static final String VIEW_CREATE_APPLICATION = "createApplication";
    private static final String VIEW_MODIFY_APPLICATION = "modifyApplication";
    private static final String ACTION_CREATE_APPLICATION = "createApplication";
    private static final String ACTION_MODIFY_APPLICATION = "modifyApplication";
    private static final String ACTION_CONFIRM_REMOVE_APPLICATION = "confirmRemoveApplication";
    private static final String ACTION_REMOVE_APPLICATION = "removeApplication";
    private static final String PROPERTY_CONFIRM_REMOVE_APPLICATION_MESSAGE = "pluginwizard.siteMessage.confirmRemoveApplication.title";

    // PORTLET
    private static final String VIEW_MANAGE_PORTLETS = "managePortlets";
    private static final String VIEW_CREATE_PORTLET = "createPortlet";
    private static final String VIEW_MODIFY_PORTLET = "modifyPortlet";
    private static final String ACTION_CREATE_PORTLET = "createPortlet";
    private static final String ACTION_MODIFY_PORTLET = "modifyPortlet";
    private static final String ACTION_CONFIRM_REMOVE_PORTLET = "confirmRemovePortlet";
    private static final String ACTION_REMOVE_PORTLET = "removePortlet";
    private static final String PROPERTY_CONFIRM_REMOVE_PORTLET_MESSAGE = "pluginwizard.siteMessage.confirmRemovePortlet.alertMessage";

    // RECAPITULATE
    private static final String VIEW_RECAPITULATE = "recapitulate";
    private static final String JSP_PAGE_PORTAL = "jsp/site/Portal.jsp";
    private static final String PLUGIN_NAME = "pluginwizard";

    // ERRORS
    public static final String ERROR_TABLE_PREFIX = "pluginwizard.error.attribute.tablePrefix";
    public static final String ERROR_PRIMARY_TYPE = "pluginwizard.error.attribute.primaryType";
    public static final String ERROR_DESCRIPTION_TYPE = "pluginwizard.error.attribute.descriptionType";
    public static final String ERROR_ATTRIBUTE_COUNT = "pluginwizard.error.attributeCount";

    // NOTIFICATIONS
    public static final String INFO_SESSION_EXPIRED = "pluginwizard.info.sessionExpired";
    public static final String INFO_PLUGIN_CREATED = "pluginwizard.info.pluginCreated";
    public static final String INFO_DATA_RESET = "pluginwizard.info.dataReset";
    public static final String INFO_FEATURE_CREATED = "pluginwizard.info.feature.created";
    public static final String INFO_FEATURE_UPDATED = "pluginwizard.info.feature.updated";
    public static final String INFO_FEATURE_DELETED = "pluginwizard.info.feature.deleted";
    public static final String INFO_BUSINESS_CLASS_CREATED = "pluginwizard.info.businessClass.created";
    public static final String INFO_BUSINESS_CLASS_UPDATED = "pluginwizard.info.businessClass.updated";
    public static final String INFO_BUSINESS_CLASS_DELETED = "pluginwizard.info.businessClass.deleted";
    public static final String INFO_ATTRIBUTE_CREATED = "pluginwizard.info.attribute.created";
    public static final String INFO_ATTRIBUTE_UPDATED = "pluginwizard.info.attribute.updated";
    public static final String INFO_ATTRIBUTE_DELETED = "pluginwizard.info.attribute.deleted";
    public static final String INFO_APPLICATION_CREATED = "pluginwizard.info.application.created";
    public static final String INFO_APPLICATION_UPDATED = "pluginwizard.info.application.updated";
    public static final String INFO_APPLICATION_DELETED = "pluginwizard.info.application.deleted";
    public static final String INFO_PORTLET_CREATED = "pluginwizard.info.portlet.created";
    public static final String INFO_PORTLET_UPDATED = "pluginwizard.info.portlet.updated";
    public static final String INFO_PORTLET_DELETED = "pluginwizard.info.portlet.deleted";

    private int _nPluginId;
    private String _strPluginName;
    
    private DescriptionFormBean _description;
    private Feature _feature;
    private BusinessClassFormBean _businessClass;
    private Attribute _attribute;
    private Application _application;
    private Portlet _portlet;


    /**
     * {@inheritDoc } 
     */
    @Override
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException, UserNotSignedException
    {
        if ( _nPluginId == 0 )
        {
            String strAction = getAction( request );
            String strView = getView( request );

            if ( !ACTION_CREATE_PLUGIN.equals( strAction ) && !VIEW_CREATE_PLUGIN.equals( strView ))
            {
                if ( ( strAction != null ) || ( strView != null ) )
                {
                    addInfo( INFO_SESSION_EXPIRED, getLocale( request ) );
                }

                return getCreatePlugin( request );
            }
        }

        return super.getPage( request, nMode, plugin );
    }

    ////////////////////////////////////////////////////////////////////////////
    // PLUGIN CREATION
    
    /**
     * The Creation form of the plugin
     *
     * @param request The Http Request
     * @return The html code of the plugin
     */
    @View( value = VIEW_CREATE_PLUGIN, defaultView = true )
    public XPage getCreatePlugin( HttpServletRequest request )
    {
        return getXPage( TEMPLATE_CREATE_PLUGIN, request.getLocale(  ) );
    }

    /**
     * The modification form of the plugin
     *
     * @param request The Http Request
     * @return The html code of the creation of plugin description
     */
    @View( VIEW_MODIFY_PLUGIN )
    public XPage getModifyPlugin( HttpServletRequest request )
    {
        return getXPage( TEMPLATE_MODIFY_PLUGIN, request.getLocale(  ), getPluginModel(  ) );
    }

    /**
     * The modification action of the plugin
     *
     * @param request The Http Request
     * @param strPluginName The Plugin name
     * @return The plugin id
     */
    @Action( ACTION_CREATE_PLUGIN )
    public XPage doCreatePlugin( HttpServletRequest request )
    {
        PluginNameFormBean form = new PluginNameFormBean(  );
        populate( form, request );

        if ( !validateBean( form, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_CREATE_PLUGIN );
        }

        _strPluginName = form.getName(  );
        _nPluginId = ModelHome.exists( form.getName(  ) );

        if ( _nPluginId == -1 )
        {
            // The plugin doesn't exists
            addInfo( INFO_PLUGIN_CREATED, getLocale( request ) );
            _nPluginId = ModelService.createModel( form.getName(  ) );

            return redirectView( request, VIEW_CREATE_DESCRIPTION );
        }

        return redirectMessageBox( request, buildExistsMessageBox(  ) );
    }

    @Action( ACTION_RESET_DATA )
    public XPage doResetData( HttpServletRequest request )
    {
        ModelService.removeAll( _nPluginId );
        addInfo( INFO_DATA_RESET, getLocale( request ) );

        return redirectView( request, VIEW_CREATE_DESCRIPTION );
    }

    ////////////////////////////////////////////////////////////////////////////
    // DESCRIPTION
    
    /**
     * Gets the create plugin description page
     *
     * @param request The HTTP request
     * @param nPluginId The plugin ID
     * @return The page
     */
    @View( VIEW_CREATE_DESCRIPTION )
    public XPage getCreatePluginDescription( HttpServletRequest request )
    {
        Map<String, Object> model = getPluginModel(  );

        for ( ConfigurationKey key : ConfigurationKeyHome.getConfigurationKeysList(  ) )
        {
            model.put( key.getKeyDescription(  ).trim(  ), key.getKeyValue(  ) );
        }

        return getXPage( TEMPLATE_CREATE_PLUGIN_DESCRIPTION, request.getLocale(  ), model );
    }

    /**
     * The modification form of the plugin description
     *
     * @param request The Http Request
     * @return The html code of the creation of plugin description
     */
    @View( VIEW_MODIFY_DESCRIPTION )
    public XPage getModifyPluginDescription( HttpServletRequest request )
    {
        Map<String, Object> model = getPluginModel(  );
        _description = ( _description != null ) ? _description : ModelService.getDescription( _nPluginId );
        model.put( MARK_PLUGIN_MODEL, _description );

        return getXPage( TEMPLATE_MODIFY_PLUGIN_DESCRIPTION, request.getLocale(  ), model );
    }


    /**
     * Action for leaving description form with previous button
     *
     * @param request The Http Request
     */
    @Action( ACTION_DESCRIPTION_PREVIOUS )
    public XPage doDescritionPrevious( HttpServletRequest request )
    {
        return doModifyPlugin( request, VIEW_MODIFY_PLUGIN );
    }

    /**
     * Action for leaving description form with previous button
     *
     * @param request The Http Request
     */
    @Action( ACTION_DESCRIPTION_NEXT )
    public XPage doDescritionNext( HttpServletRequest request )
    {
        return doModifyPlugin( request, VIEW_MANAGE_ADMIN_FEATURES );
    }
    
    /**
     * Modify plugin's info
     * @param request The HTTP request
     * @param strView The view
     * @return The page
     */
    private XPage doModifyPlugin( HttpServletRequest request, String strView )
    {
        populate( _description, request );

        if ( !validateBean( _description, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MODIFY_DESCRIPTION );
        }

        _description.setPluginClass( "fr.paris.lutece.portal.service.plugin.PluginDefaultImplementation");
        _description.setPluginIconUrl( "images/admin/skin/plugins/" + _strPluginName + "/" + _strPluginName + ".png" );

        
        ModelService.updateDescription( _nPluginId , _description );

        return redirectView( request, strView );
    }

    ////////////////////////////////////////////////////////////////////////////
    // ADMIN FEATURES
    /**
     * The management screen of the admin features
     *
     * @param request The Http Request
     * @return The html code of the admin features
     */
    @View( VIEW_MANAGE_ADMIN_FEATURES )
    public XPage getManageAdminFeatures( HttpServletRequest request )
    {
        PluginModel pm = ModelService.getPluginModel( _nPluginId );

        Map<String, Object> model = getModel(  );

        model.put( MARK_PLUGIN_ID, Integer.toString( _nPluginId ) );
        model.put( MARK_ADMIN_FEATURES, pm.getFeatures(  ) );

        return getXPage( TEMPLATE_MANAGE_ADMIN_FEATURES, request.getLocale(  ), model );
    }

    /**
     * The creation form of the admin _feature
     *
     * @param request The Http Request
     * @return The html code of the admin _feature
     */
    @View( VIEW_CREATE_ADMIN_FEATURE )
    public XPage getCreateAdminFeature( HttpServletRequest request )
    {
        _feature = ( _feature != null ) ? _feature : new Feature();

        Map<String, Object> model = getPluginModel(  );
        model.put( MARK_FEATURE, _feature );
        return getXPage( TEMPLATE_CREATE_ADMIN_FEATURE, request.getLocale(  ), model );
    }

    /**
     * The modification screen of the admin _feature
     *
     * @param request The Http Request
     * @return The html code of the admin _feature
     */
    @View( VIEW_MODIFY_ADMIN_FEATURE )
    public XPage getModifyAdminFeature( HttpServletRequest request )
    {
        int nFeatureId = Integer.parseInt( request.getParameter( PARAM_FEATURE_ID ) );
        
        if( (_feature == null ) || ( _feature.getId() != nFeatureId ))
        {
            _feature = ModelService.getFeature( _nPluginId, nFeatureId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_FEATURE, _feature );

        return getXPage( TEMPLATE_MODIFY_ADMIN_FEATURE, request.getLocale(  ), model );
    }

    /**
     * The creation of an admin _feature
     *
     * @param request The Http Request
     */
    @Action( ACTION_CREATE_ADMIN_FEATURE )
    public XPage doCreateAdminFeature( HttpServletRequest request )
    {
        populate( _feature, request );

        if ( !validateBean( _feature, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_CREATE_ADMIN_FEATURE );
        }

        ModelService.addFeature( _nPluginId, _feature );
        _feature = null;
        addInfo( INFO_FEATURE_CREATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_ADMIN_FEATURES );
    }

    /**
     * The modification action of an admin _feature
     *
     * @param request The Http Request
     */
    @Action( ACTION_MODIFY_ADMIN_FEATURE )
    public XPage doModifyAdminFeature( HttpServletRequest request )
    {
        populate( _feature, request );

        if ( !validateBean( _feature, getLocale( request ) ) )
        {
            return redirect( request, VIEW_MODIFY_ADMIN_FEATURE, PARAM_FEATURE_ID, _feature.getId(  ) );
        }

        ModelService.updateFeature( _nPluginId, _feature );
        _feature = null;
        addInfo( INFO_FEATURE_UPDATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_ADMIN_FEATURES );
    }

    /**
     * The confirmation of the removal of an admin _feature
     *
     * @param request The Http Request
     */
    @Action( ACTION_CONFIRM_REMOVE_ADMIN_FEATURE )
    public XPage doConfirmRemoveAdminFeature( HttpServletRequest request )
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );

        url.addParameter( PARAM_PAGE, PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_REMOVE_ADMIN_FEATURE );
        url.addParameter( PARAM_FEATURE_ID, request.getParameter( PARAM_FEATURE_ID ) );

        return redirectMessageBox( request,
            buildConfirmMessageBox( PROPERTY_CONFIRM_REMOVE_FEATURE_MESSAGE, url.getUrl(  ),
                getViewFullUrl( VIEW_MANAGE_ADMIN_FEATURES ) ) );
    }

    /**
     * The removal screen of an admin _feature
     *
     * @param request The Http Request
     */
    @Action( ACTION_REMOVE_ADMIN_FEATURE )
    public XPage doRemoveAdminFeature( HttpServletRequest request )
    {
        int nFeatureId = Integer.parseInt( request.getParameter( PARAM_FEATURE_ID ) );
        ModelService.removeFeature( _nPluginId, nFeatureId );
        addInfo( INFO_FEATURE_DELETED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_ADMIN_FEATURES );
    }

    ////////////////////////////////////////////////////////////////////////////
    // BUSINESS CLASSES
    /**
     * The management screen of business classes associated to the plugin which
     * is generated
     *
     * @param request The Http Request
     * @return The html code of the management screen of the business classes
     */
    @View( VIEW_MANAGE_BUSINESS_CLASSES )
    public XPage getManageBusinessClasses( HttpServletRequest request )
    {
        Map<String, Object> model = getModel(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( _nPluginId ) );
        model.put( MARK_BUSINESS_CLASSES, ModelService.getPluginModel( _nPluginId ).getBusinessClasses(  ) );

        return getXPage( TEMPLATE_MANAGE_BUSINESS_CLASSES, request.getLocale(  ), model );
    }

    /**
     * The creation form of a business class
     *
     * @param request The Http Request
     * @return The html code of the creation of a business class
     */
    @View( VIEW_CREATE_BUSINESS_CLASS )
    public XPage getCreateBusinessClass( HttpServletRequest request )
    {
        _businessClass = ( _businessClass != null ) ? _businessClass : new BusinessClassFormBean();
        Map<String, Object> model = getPluginModel(  );
        
        model.put( MARK_ADMIN_FEATURES_COMBO, ModelService.getComboFeatures( _nPluginId ) );
        model.put( MARK_BUSINESS_CLASS, _businessClass );

        return getXPage( TEMPLATE_CREATE_BUSINESS_CLASS, request.getLocale(  ), model );
    }

    /**
     * Gets the modify business class page
     *
     * @param nBusinessClassId The business class id
     * @param request The HTTP request
     * @return The page
     */
    @View( VIEW_MODIFY_BUSINESS_CLASS )
    public XPage getModifyBusinessClass( HttpServletRequest request )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        String strRefresh = request.getParameter( PARAM_REFRESH );
        if( ( _businessClass == null ) || ( _businessClass.getId() != nBusinessClassId ) || ( strRefresh != null ))
        {
            _businessClass = ModelService.getFormBusinessClass( _nPluginId, nBusinessClassId );
        }
        Map<String, Object> model = getPluginModel(  );
        model.put( MARK_BUSINESS_CLASS, _businessClass );
        model.put( MARK_ATTRIBUTES_LIST, ModelService.getBusinessClass(_nPluginId, nBusinessClassId).getAttributes());
        model.put( MARK_ADMIN_FEATURES_COMBO, ModelService.getComboFeatures( _nPluginId ) );

        return getXPage( TEMPLATE_MODIFY_BUSINESS_CLASS, request.getLocale(  ), model );
    }

    /**
     * The creation action of the business class
     *
     * @param request The Http Request
     * @return The business class id
     */
    @Action( ACTION_CREATE_BUSINESS_CLASS )
    public XPage doCreateBusinessClass( HttpServletRequest request )
    {
        populate( _businessClass, request );

        boolean bValidateBean = validateBean( _businessClass, getLocale( request ) );
        boolean bValidateTablePrefix = validateTablePrefix( request, _businessClass );
        boolean bValid = bValidateBean && bValidateTablePrefix;

        if ( !bValid )
        {
            return redirectView( request, VIEW_CREATE_BUSINESS_CLASS );
        }

        BusinessClass businessClass = ModelService.addBusinessClass( _nPluginId, _businessClass );
        _businessClass = null;
        addInfo( INFO_BUSINESS_CLASS_CREATED, getLocale( request ) );

        return redirect( request, VIEW_MODIFY_BUSINESS_CLASS , PARAM_BUSINESS_CLASS_ID,  businessClass.getId() );
    }

    /**
     * The modification action for the business class
     *
     * @param request The Http Request
     */
    @Action( ACTION_MODIFY_BUSINESS_CLASS )
    public XPage doModifyBusinessClass( HttpServletRequest request )
    {
        populate( _businessClass, request );

        boolean bValidateBean = validateBean( _businessClass, getLocale( request ) );
        boolean bValidateTablePrefix = validateTablePrefix( request, _businessClass );
        boolean bValid = bValidateBean && bValidateTablePrefix;

        if ( !bValid )
        {
            return redirect( request, VIEW_MODIFY_BUSINESS_CLASS , PARAM_BUSINESS_CLASS_ID, _businessClass.getId() );
        }

        ModelService.updateBusinessClass( _nPluginId, _businessClass );
        _businessClass = null;
        addInfo( INFO_BUSINESS_CLASS_UPDATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_BUSINESS_CLASSES );
    }

    /**
     * Validate table prefix
     * @param request The HTTP request
     * @param businessClass The Business class
     * @return True if OK
     */
    private boolean validateTablePrefix( HttpServletRequest request, BusinessClassFormBean businessClass )
    {
        String strTablePrefix = _strPluginName + "_";
        boolean bValidate = businessClass.getBusinessTableName(  ).startsWith( strTablePrefix );

        if ( !bValidate )
        {
            addError( ERROR_TABLE_PREFIX, getLocale( request ) );
        }

        return bValidate;
    }

    /**
     * The confirmation of a business class removal
     *
     * @param request The Http Request
     */
    @Action( ACTION_CONFIRM_REMOVE_BUSINESS_CLASS )
    public XPage doConfirmRemoveBusinessClass( HttpServletRequest request )
    {
        String strBusinessClassId = request.getParameter( PARAM_BUSINESS_CLASS_ID );
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_REMOVE_BUSINESS_CLASS );
        url.addParameter( PARAM_BUSINESS_CLASS_ID, strBusinessClassId );
        url.addParameter( PARAM_FEATURE_ID, request.getParameter( PARAM_FEATURE_ID ) );

        return redirectMessageBox( request,
            buildConfirmMessageBox( PROPERTY_CONFIRM_REMOVE_BUSINESS_CLASS_MESSAGE, url.getUrl(  ),
                getViewFullUrl( VIEW_MANAGE_BUSINESS_CLASSES ) ) );
    }

    /**
     * The removal action of a plugin application
     *
     * @param request The Http Request
     */
    @Action( ACTION_REMOVE_BUSINESS_CLASS )
    public XPage doRemoveBusinessClass( HttpServletRequest request )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );

        ModelService.removeBusinessClass( _nPluginId, nBusinessClassId );
        addInfo( INFO_BUSINESS_CLASS_DELETED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_BUSINESS_CLASSES );
    }

    ////////////////////////////////////////////////////////////////////////////
    // ATTRIBUTE

    /**
     * The creation form of the attribute associated to a business class
     *
     * @param request The Http Request
     * @return The html code of the admin _feature
     */
    @View( VIEW_CREATE_ATTRIBUTE )
    public XPage getCreateAttribute( HttpServletRequest request )
    {
        String strBusinessClassId = request.getParameter( PARAM_BUSINESS_CLASS_ID );
        _attribute = ( _attribute != null ) ? _attribute : new Attribute();
        Map<String, Object> model = getModel(  );
        model.put( MARK_BUSINESS_CLASS_ID, strBusinessClassId );
        model.put( MARK_ATTRIBUTE_TYPE_COMBO, ModelService.getAttributeTypes(  ) );
        model.put( MARK_ATTRIBUTE, _attribute );

        return getXPage( TEMPLATE_CREATE_ATTRIBUTE, request.getLocale(  ), model );
    }

    /**
     * The modification form of an attribute
     *
     * @param request The Http Request
     * @return The html code of the creation of attribute description
     */
    @View( VIEW_MODIFY_ATTRIBUTE )
    public XPage getModifyAttribute( HttpServletRequest request )
    {
        Map<String, Object> model = getModel(  );
        int nIdBusinessClass = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        int nIdAttribute = Integer.parseInt( request.getParameter( PARAM_ATTRIBUTE_ID ) );
        if( (_attribute == null) || ( _attribute.getId() != nIdAttribute ) )
        {
            _attribute = ModelService.getAttribute( _nPluginId, nIdBusinessClass, nIdAttribute );
        }

        model.put( MARK_PLUGIN_ID, _nPluginId );
        model.put( MARK_BUSINESS_CLASS_ID, nIdBusinessClass );
        model.put( MARK_ATTRIBUTE_TYPE_COMBO, ModelService.getAttributeTypes(  ) );
        model.put( MARK_ATTRIBUTE, _attribute );

        return getXPage( TEMPLATE_MODIFY_ATTRIBUTE, request.getLocale(  ), model );
    }

    
    /**
     * The creation action of an attribute
     *
     * @param request The Http Request
     */
    @Action( ACTION_CREATE_ATTRIBUTE )
    public XPage doCreateAttribute( HttpServletRequest request )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        populate( _attribute, request );

        boolean bValidateBean = validateBean( _attribute, getLocale( request ) );
        boolean bValidatePrimary = validatePrimary( request, _attribute );
        boolean bValidateDescription = validateDescription( request, _attribute );
        boolean bValid = bValidateBean && bValidatePrimary && bValidateDescription;

        if ( !bValid )
        {
            return redirect( request, VIEW_CREATE_ATTRIBUTE , PARAM_BUSINESS_CLASS_ID, nBusinessClassId );
        }

        ModelService.addAttribute( _nPluginId, nBusinessClassId, _attribute );
        _attribute = null;
        addInfo( INFO_ATTRIBUTE_CREATED, getLocale( request ) );

        return redirect( request, VIEW_MODIFY_BUSINESS_CLASS , PARAM_BUSINESS_CLASS_ID, nBusinessClassId, PARAM_REFRESH , 1 );
    }

    /**
     * The modification action for the attribute
     *
     * @param request The Http Request
     */
    @Action( ACTION_MODIFY_ATTRIBUTE )
    public XPage doModifyAttribute( HttpServletRequest request )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        populate( _attribute, request );

        boolean bValidateBean = validateBean( _attribute, getLocale( request ) );
        boolean bValidatePrimary = validatePrimary( request, _attribute );
        boolean bValidateDescription = validateDescription( request, _attribute );
        boolean bValid = bValidateBean && bValidatePrimary && bValidateDescription;

        if ( !bValid )
        {
            return redirect( request, VIEW_MODIFY_ATTRIBUTE , PARAM_BUSINESS_CLASS_ID, nBusinessClassId , PARAM_ATTRIBUTE_ID , _attribute.getId() );
        }

        ModelService.updateAttribute( _nPluginId, nBusinessClassId, _attribute );
        _attribute = null;
        addInfo( INFO_ATTRIBUTE_UPDATED, getLocale( request ) );

        return redirect( request, VIEW_MODIFY_BUSINESS_CLASS , PARAM_BUSINESS_CLASS_ID, nBusinessClassId, PARAM_REFRESH , 1 );
    }

    /**
     * Validate primary type
     * @param request The HTTP request
     * @param attribute The attribute
     * @return true if OK
     */
    private boolean validatePrimary( HttpServletRequest request, Attribute attribute )
    {
        boolean bValidate = ( ( !attribute.getIsPrimary(  ) ) ||
            ( attribute.getIsPrimary(  ) && ( attribute.getAttributeTypeId(  ) == 1 ) ) );

        if ( !bValidate )
        {
            addError( ERROR_PRIMARY_TYPE, getLocale( request ) );
        }

        return bValidate;
    }

    /**
     * Validate Description type
     * @param request The HTTP request
     * @param attribute The attribute
     * @return True if OK
     */
    private boolean validateDescription( HttpServletRequest request, Attribute attribute )
    {
        boolean bValidate = ( ( !attribute.getIsDescription(  ) ) ||
            ( attribute.getIsDescription(  ) && ( attribute.getAttributeTypeId(  ) > 1 ) ) );

        if ( !bValidate )
        {
            addError( ERROR_DESCRIPTION_TYPE, getLocale( request ) );
        }

        return bValidate;
    }

    /**
     * The confirmation of the attribute removal
     *
     * @param request The Http Request
     */
    @Action( ACTION_CONFIRM_REMOVE_ATTRIBUTE )
    public XPage getConfirmRemoveAttribute( HttpServletRequest request )
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_REMOVE_ATTRIBUTE );
        url.addParameter( PARAM_ATTRIBUTE_ID, request.getParameter( PARAM_ATTRIBUTE_ID ) );
        url.addParameter( PARAM_BUSINESS_CLASS_ID, request.getParameter( PARAM_BUSINESS_CLASS_ID ) );

        return redirectMessageBox( request,
            buildConfirmMessageBox( PROPERTY_CONFIRM_REMOVE_ATTRIBUTE_MESSAGE, url.getUrl(  ),
                getViewFullUrl( VIEW_MODIFY_BUSINESS_CLASS ) ) );
    }

    /**
     * Remove Business Attribute
     *
     * @param request The Http Request
     */
    @Action( ACTION_REMOVE_ATTRIBUTE )
    public XPage doRemoveAttribute( HttpServletRequest request )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ) );
        int nAttributeId = Integer.parseInt( request.getParameter( PARAM_ATTRIBUTE_ID ) );
        ModelService.removeAttribute( _nPluginId, nBusinessClassId, nAttributeId );
        addInfo( INFO_ATTRIBUTE_DELETED, getLocale( request ) );

        return redirect( request, VIEW_MODIFY_BUSINESS_CLASS , PARAM_BUSINESS_CLASS_ID, nBusinessClassId, PARAM_REFRESH , 1 );
    }

    @Action( ACTION_VALIDATE_ATTRIBUTES )
    public XPage doValidateAttributes( HttpServletRequest request )
    {
        int nBusinessClassId = Integer.parseInt( request.getParameter( PARAM_BUSINESS_CLASS_ID ));
        Collection<BusinessClass> listBusinessClass = ModelService.getPluginModel( _nPluginId ).getBusinessClasses(  );

        for ( BusinessClass businessClass : listBusinessClass )
        {
            if ( businessClass.getAttributes(  ).size(  ) < 2 )
            {
                addError( ERROR_ATTRIBUTE_COUNT, getLocale( request ) );

                return redirect( request, VIEW_MODIFY_BUSINESS_CLASS , PARAM_BUSINESS_CLASS_ID, nBusinessClassId );
            }
        }

        return redirectView( request, VIEW_MANAGE_BUSINESS_CLASSES );
    }

    ////////////////////////////////////////////////////////////////////////////
    // APPLICATION
    
    /**
     * The management of the plugin applications associated to the generated
     * plugin
     *
     * @param request The Http Request
     * @return The html code of the management screen of the applications
     */
    @View( VIEW_MANAGE_APPLICATIONS )
    public XPage getManageApplications( HttpServletRequest request )
    {
        Map<String, Object> model = getModel(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( _nPluginId ) );
        model.put( MARK_PLUGIN_APPLICATIONS, ModelService.getPluginModel( _nPluginId ).getApplications(  ) );

        return getXPage( TEMPLATE_MANAGE_PLUGIN_APPLICATIONS, request.getLocale(  ), model );
    }

    /**
     * The creation screen of a plugin application
     *
     * @param request The Http Request
     * @return The html code of a plugin application
     */
    @View( VIEW_CREATE_APPLICATION )
    public XPage getCreateApplication( HttpServletRequest request )
    {
        _application = ( _application != null ) ? _application : new Application();
        Map<String,Object> model =  getPluginModel(  );
        model.put( MARK_APPLICATION, _application );
        return getXPage( TEMPLATE_CREATE_PLUGIN_APPLICATION, request.getLocale(  ), model );
    }

    /**
     * The modification screen of a plugin application
     *
     * @param request The Http Request
     * @return The html code of the modification of an application associated to
     * the generated plugin
     */
    @View( VIEW_MODIFY_APPLICATION )
    public XPage getModifyApplication( HttpServletRequest request )
    {
        int nApplicationId = Integer.parseInt( request.getParameter( PARAM_APPLICATION_ID ) );

        if ( _application == null || ( _application.getId() != nApplicationId ))
        {
             _application = ModelService.getApplication( _nPluginId, nApplicationId );
        }
        Map<String, Object> model = getPluginModel(  );
        model.put( MARK_APPLICATION, _application );
        model.put( MARK_PLUGIN_ID, Integer.toString( _nPluginId ) );

        return getXPage( TEMPLATE_MODIFY_PLUGIN_APPLICATION, request.getLocale(  ), model );
    }

    
    /**
     * The creation action of the plugin application
     *
     * @param request The Http Request
     */
    @Action( ACTION_CREATE_APPLICATION )
    public XPage doCreateApplication( HttpServletRequest request )
    {
        populate( _application, request );

        if ( !validateBean( _application, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_CREATE_APPLICATION );
        }

        ModelService.addApplication( _nPluginId, _application );
        _application = null;
        addInfo( INFO_APPLICATION_CREATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * The modification action of the plugin application
     *
     * @param request The Http Request
     */
    @Action( ACTION_MODIFY_APPLICATION )
    public XPage doModifyApplication( HttpServletRequest request )
    {
        populate( _application, request );

        if ( !validateBean( _application, getLocale( request ) ) )
        {
            return redirect( request, VIEW_MODIFY_APPLICATION , PARAM_APPLICATION_ID, _application.getId() );
        }

        ModelService.updateApplication( _nPluginId, _application );
        _application = null;
        addInfo( INFO_APPLICATION_UPDATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * The confirmation of an application removal
     *
     * @param request The Http Request
     */
    @Action( ACTION_CONFIRM_REMOVE_APPLICATION )
    public XPage doConfirmRemoveApplication( HttpServletRequest request )
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_REMOVE_APPLICATION );
        url.addParameter( PARAM_APPLICATION_ID, request.getParameter( PARAM_APPLICATION_ID ) );

        return redirectMessageBox( request,
            buildConfirmMessageBox( PROPERTY_CONFIRM_REMOVE_APPLICATION_MESSAGE, url.getUrl(  ),
                getViewFullUrl( VIEW_MANAGE_APPLICATIONS ) ) );
    }

    /**
     * The removal action of a plugin application
     *
     * @param request The Http Request
     */
    @Action( ACTION_REMOVE_APPLICATION )
    public XPage doRemoveApplication( HttpServletRequest request )
    {
        int nApplicationId = Integer.parseInt( request.getParameter( PARAM_APPLICATION_ID ) );
        ModelService.removeApplication( _nPluginId, nApplicationId );
        addInfo( INFO_APPLICATION_DELETED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    ////////////////////////////////////////////////////////////////////////////
    // PORTLET
    
    /**
     * The screen for management of portlets associated to the generated plugin
     *
     * @param request The Http Request
     * @return The main management screen of portlets
     */
    @View( VIEW_MANAGE_PORTLETS )
    public XPage getManagePortlets( HttpServletRequest request )
    {
        Map<String, Object> model = getModel(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( _nPluginId ) );
        model.put( MARK_PLUGIN_PORTLETS, ModelService.getPluginModel( _nPluginId ).getPortlets(  ) );

        return getXPage( TEMPLATE_MANAGE_PLUGIN_PORTLETS, request.getLocale(  ), model );
    }

    /**
     * The creation screen of a portlet
     *
     * @param request The Http Request
     * @return The html code of the creation of a portlet
     */
    @View( VIEW_CREATE_PORTLET )
    public XPage getCreatePortlet( HttpServletRequest request )
    {
        _portlet = ( _portlet != null ) ? _portlet : new Portlet();
        Map< String, Object> model = getPluginModel(  );
        model.put( MARK_PORTLET, _portlet );
        return getXPage( TEMPLATE_CREATE_PLUGIN_PORTLET, request.getLocale(  ), model );
    }

    /**
     * The modification page of the portlet
     *
     * @param request The Http Request
     * @return The html code of the modification of the portlet
     */
    @View( VIEW_MODIFY_PORTLET )
    public XPage getModifyPortlet( HttpServletRequest request )
    {
        int nPortletId = Integer.parseInt( request.getParameter( PARAM_PORTLET_ID ) );
        if( ( _portlet == null ) || ( _portlet.getId() != nPortletId ))
        {
            _portlet = ModelService.getPortlet( _nPluginId, nPortletId );
        }
        Map<String, Object> model = getModel(  );
        model.put( MARK_PORTLET, _portlet );

        return getXPage( TEMPLATE_MODIFY_PLUGIN_PORTLET, request.getLocale(  ), model );
    }

    /**
     * The creation action of the portlet
     *
     * @param request The Http Request
     */
    @Action( ACTION_CREATE_PORTLET )
    public XPage doCreatePortlet( HttpServletRequest request )
    {
        populate( _portlet, request );

        if ( !validateBean( _portlet, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_CREATE_PORTLET );
        }

        ModelService.addPortlet( _nPluginId, _portlet );
        _portlet = null;
        addInfo( INFO_PORTLET_CREATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_PORTLETS );
    }

    /**
     * The modification action of the portlet
     *
     * @param request The Http Request
     */
    @Action( ACTION_MODIFY_PORTLET )
    public XPage doModifyPluginPortlet( HttpServletRequest request )
    {
        populate( _portlet, request );

        if ( !validateBean( _portlet, getLocale( request ) ) )
        {
            return redirect( request, ACTION_MODIFY_PORTLET , PARAM_PORTLET_ID , _portlet.getId() );
        }

        ModelService.updatePortlet( _nPluginId, _portlet );
        _portlet = null;
        addInfo( INFO_PORTLET_UPDATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_PORTLETS );
    }

    /**
     * The confirmation of the plugin removal
     *
     * @param request The Http Request
     */
    @Action( ACTION_CONFIRM_REMOVE_PORTLET )
    public XPage doConfirmRemovePortlet( HttpServletRequest request )
    {
        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, PLUGIN_NAME );
        url.addParameter( PARAM_ACTION, ACTION_REMOVE_PORTLET );
        url.addParameter( PARAM_PORTLET_ID, request.getParameter( PARAM_PORTLET_ID ) );

        return redirectMessageBox( request,
            buildConfirmMessageBox( PROPERTY_CONFIRM_REMOVE_PORTLET_MESSAGE, url.getUrl(  ),
                getViewFullUrl( VIEW_MANAGE_PORTLETS ) ) );
    }

    /**
     * Remove Portlet Action
     *
     * @param request The Http Request
     */
    @Action( ACTION_REMOVE_PORTLET )
    public XPage doRemovePluginPortlet( HttpServletRequest request )
    {
        int nPluginPortletId = Integer.parseInt( request.getParameter( PARAM_PORTLET_ID ) );
        ModelService.removePortlet( _nPluginId, nPluginPortletId );
        addInfo( INFO_PORTLET_DELETED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_PORTLETS );
    }

    /**
     * The get page of the plugin recapitulation
     *
     * @param request The Http Request
     * @return The Html code of the summary
     */
    @View( VIEW_RECAPITULATE )
    public XPage getPluginRecapitulate( HttpServletRequest request )
    {
        PluginModel pm = ModelService.getPluginModel( _nPluginId );

        Map<String, Object> model = getPluginModel(  );
        model.put( MARK_PLUGIN_ID, Integer.toString( _nPluginId ) );
        model.put( MARK_PLUGIN_APPLICATIONS, pm.getApplications(  ) ); // FIXME can be found in the _model
        model.put( MARK_ADMIN_FEATURES, pm.getFeatures(  ) );
        model.put( MARK_PLUGIN_PORTLETS, pm.getPortlets(  ) );
        model.put( MARK_BUSINESS_CLASSES, pm.getBusinessClasses(  ) );
        model.put( MARK_SCHEMES_COMBO, GeneratorService.getGenerationSchemes(  ) );

        return getXPage( TEMPLATE_GET_RECAPITULATE, request.getLocale(  ), model );
    }

    /**
     * Get a default _model for template with the plugin object inside
     *
     * @return A default _model for template
     */
    private Map<String, Object> getPluginModel(  )
    {
        Map<String, Object> model = getModel(  );
        model.put( MARK_PLUGIN_MODEL, ModelService.getPluginModel( _nPluginId ) );

        return model;
    }

    /**
     * Build a message box object
     *
     * @param request The request
     * @param strMessageKey The message key
     * @param strUrlConfirm The Url to process confirmation
     * @param strUrlCancel The Url to cancel
     * @return
     */
    private MVCMessageBox buildConfirmMessageBox( String strMessageKey, String strUrlConfirm, String strUrlBack )
    {
        MVCMessageBox box = new MVCMessageBox(  );
        box.setTemplate( TEMPLATE_MESSAGE_BOX_CONFIRMATION );
        box.setMessageKey( strMessageKey );
        box.setStyle( MVCMessageBox.QUESTION );
        box.setUrlButton1( strUrlConfirm );
        box.setUrlButton2( strUrlBack );

        return box;
    }

    /**
     * Build plugin exists message box
     * @return The message box object
     */
    private MVCMessageBox buildExistsMessageBox(  )
    {
        MVCMessageBox box = new MVCMessageBox(  );
        box.setTemplate( TEMPLATE_MESSAGE_BOX_EXISTS );
        box.setMessage( _strPluginName );

        return box;
    }
    
}
