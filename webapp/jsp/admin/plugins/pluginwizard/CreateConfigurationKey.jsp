

<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="pluginwizard" scope="session" class="fr.paris.lutece.plugins.pluginwizard.web.PluginwizardJspBean" />

<% pluginwizard.init( request, pluginwizard.RIGHT_MANAGE_PLUGINWIZARD ); %>
<%= pluginwizard.getCreateConfigurationKey ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>

