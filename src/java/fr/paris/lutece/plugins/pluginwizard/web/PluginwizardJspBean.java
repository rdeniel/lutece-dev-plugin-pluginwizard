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
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage  ConfigurationKey
 features ( manage, create, modify, remove )
 */
public class PluginwizardJspBean extends PluginAdminPageJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // Right
    /**
     *
     */
    public static final String RIGHT_MANAGE_PLUGINWIZARD = "PLUGINWIZARD_MANAGEMENT";

    // Parameters
    private static final String PARAMETER_PAGE_INDEX = "page_index";
    private static final String PARAMETER_ID_KEY = "configurationkey_id_key";
    private static final String PARAMETER_KEY_DESCRIPTION = "configurationkey_key_description";
    private static final String PARAMETER_KEY_VALUE = "configurationkey_key_value";

    // templates
    private static final String TEMPLATE_MANAGE_CONFIGURATIONKEYS = "/admin/plugins/pluginwizard/manage_configurationkey.html";
    private static final String TEMPLATE_CREATE_CONFIGURATIONKEY = "/admin/plugins/pluginwizard/create_configurationkey.html";
    private static final String TEMPLATE_MODIFY_CONFIGURATIONKEY = "/admin/plugins/pluginwizard/modify_configurationkey.html";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_CONFIGURATIONKEYS = "pluginwizard.manage_configurationkeys.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_CONFIGURATIONKEY = "pluginwizard.modify_configurationkey.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_CONFIGURATIONKEY = "pluginwizard.create_configurationkey.pageTitle";

    // Markers
    private static final String MARK_CONFIGURATIONKEY_LIST = "configurationkey_list";
    private static final String MARK_CONFIGURATIONKEY = "configurationkey";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";

    // Jsp Definition
    private static final String JSP_DO_REMOVE_CONFIGURATIONKEY = "jsp/admin/plugins/pluginwizard/DoRemoveConfigurationKey.jsp";
    private static final String JSP_MANAGE_CONFIGURATIONKEYS = "jsp/admin/plugins/pluginwizard/ManageConfigurationKeys.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_CONFIGURATIONKEYS = "ManageConfigurationKeys.jsp";

    // Properties
    private static final String PROPERTY_DEFAULT_LIST_CONFIGURATIONKEY_PER_PAGE = "pluginwizard.listConfigurationKeys.itemsPerPage";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_CONFIGURATIONKEY = "pluginwizard.message.confirmRemoveConfigurationKey";

    //Variables
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Returns the list of configurationkey
     *
     * @param request The Http request
     * @return the configurationkeys list
     */
    public String getManageConfigurationKeys( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_CONFIGURATIONKEYS );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_CONFIGURATIONKEY_PER_PAGE, 5 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        UrlItem url = new UrlItem( JSP_MANAGE_CONFIGURATIONKEYS );
        String strUrl = url.getUrl(  );
        Collection<ConfigurationKey> listCONFIGURATIONKEYs = ConfigurationKeyHome.getConfigurationKeysList(  );
        LocalizedPaginator paginator = new LocalizedPaginator( (List<ConfigurationKey>) listCONFIGURATIONKEYs,
                _nItemsPerPage, strUrl, PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_CONFIGURATIONKEY_LIST, paginator.getPageItems(  ) );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_CONFIGURATIONKEYS, getLocale(  ),
                model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Returns the form to create a configurationkey
     *
     * @param request The Http request
     * @return the html code of the configurationkey form
     */
    public String getCreateConfigurationKey( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_CONFIGURATIONKEY );

        Map<String, Object> model = new HashMap<String, Object>(  );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_CONFIGURATIONKEY, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new configurationkey
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateConfigurationKey( HttpServletRequest request )
    {
        ConfigurationKey configurationkey = new ConfigurationKey(  );
        String strKeyDescription = request.getParameter( PARAMETER_KEY_DESCRIPTION );
        String strKeyValue = request.getParameter( PARAMETER_KEY_VALUE );

        if ( strKeyDescription.equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        if ( strKeyValue.equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        configurationkey.setKeyDescription( strKeyDescription );
        configurationkey.setKeyValue( strKeyValue );

        ConfigurationKeyHome.create( configurationkey );

        return JSP_REDIRECT_TO_MANAGE_CONFIGURATIONKEYS;
    }

    /**
     * Manages the removal form of a configurationkey whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveConfigurationKey( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_KEY ) );
        UrlItem url = new UrlItem( JSP_DO_REMOVE_CONFIGURATIONKEY );
        url.addParameter( PARAMETER_ID_KEY, nId );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_CONFIGURATIONKEY, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Handles the removal form of a configurationkey
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage configurationkeys
     */
    public String doRemoveConfigurationKey( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_KEY ) );
        ConfigurationKeyHome.remove( nId );

        return JSP_REDIRECT_TO_MANAGE_CONFIGURATIONKEYS;
    }

    /**
     * Returns the form to update info about a configurationkey
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyConfigurationKey( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_CONFIGURATIONKEY );

        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_KEY ) );
        ConfigurationKey configurationkey = ConfigurationKeyHome.findByPrimaryKey( nId );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_CONFIGURATIONKEY, configurationkey );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_CONFIGURATIONKEY, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the change form of a configurationkey
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyConfigurationKey( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_KEY ) );
        String strKeyDescription = request.getParameter( PARAMETER_KEY_DESCRIPTION );
        String strValue = request.getParameter( PARAMETER_KEY_VALUE );
        ConfigurationKey configurationkey = ConfigurationKeyHome.findByPrimaryKey( nId );

        if ( ( nId == 0 ) || ( strValue == null ) || ( strKeyDescription == null ) || strValue.equals( "" ) ||
                strKeyDescription.equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        configurationkey.setIdKey( nId );
        configurationkey.setKeyDescription( strKeyDescription );
        configurationkey.setKeyValue( strValue );
        ConfigurationKeyHome.update( configurationkey );

        return JSP_REDIRECT_TO_MANAGE_CONFIGURATIONKEYS;
    }
}
