

<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="pluginwizard" scope="session" class="fr.paris.lutece.plugins.pluginwizard.web.PluginwizardJspBean" />

<% pluginwizard.init( request, pluginwizard.RIGHT_MANAGE_PLUGINWIZARD ); 
response.sendRedirect( pluginwizard.getConfirmRemoveConfigurationKey ( request ) ); %>


