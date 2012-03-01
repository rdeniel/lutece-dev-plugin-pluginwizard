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
package fr.paris.lutece.plugins.pluginwizard.service.generator;

import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClassHome;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.SourceCodeGenerator;
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;


/**
 *
 * The generator produced the jsp for back office management
 *
 */
public class BackOfficeJspGenerator implements Visitor
{
    /**
     * Visits the path and verifies if Portlet templates is relevant to be generated
     * @param strPath The path representing the file structure of the zip
     * @param plugin The plugin
     * @param pluginModel the representation of the created plugin
     * @return The map with the name of the file and its corresponding content
     */
    public HashMap visitPath( String strPath, Plugin plugin, PluginModel pluginModel )
    {
        HashMap map = new HashMap(  );
        Collection<BusinessClass> listAllBusinessClasses = BusinessClassHome.getBusinessClassesByPlugin( pluginModel.getIdPlugin(  ),
                plugin );

        for ( BusinessClass businessClass : listAllBusinessClasses )
        {
            String strOldPath = new String( strPath );
            String strBasePath = new String( strPath );

            for ( int i = 1; i < 9; i++ )
            {
                String strJspFileName = getJspFileName( businessClass.getBusinessClass(  ),
                        pluginModel.getPluginName(  ), i );

                strBasePath = strBasePath + "\\" + strJspFileName;

                String strSourceCode = SourceCodeGenerator.getJspFile( businessClass, pluginModel.getPluginName(  ), i );
                strSourceCode = strSourceCode.replace( "&lt;", "<" );
                strSourceCode = strSourceCode.replace( "&gt;", ">" );
                map.put( strBasePath, strSourceCode );
                strBasePath = strOldPath;
            }
        }

        return map;
    }

    /**
    * Fetches the name of the Jsp file
    * @param strBusinessClass the business class name
    * @param strPluginName the name of the plugin
    * @param nJspType The type of Jsp to be generated
    * @return The name of the backoffice Jsp
    */
    private String getJspFileName( String strBusinessClass, String strPluginName, int nJspType )
    {
        String strReturn = strBusinessClass;

        switch ( nJspType )
        {
            case 1:
                strReturn = "Create" + strBusinessClass + ".jsp";

                break;

            case 2:
                strReturn = "DoCreate" + strBusinessClass + ".jsp";

                break;

            case 3:
                strReturn = "Remove" + strBusinessClass + ".jsp";

                break;

            case 4:
                strReturn = "DoRemove" + strBusinessClass + ".jsp";

                break;

            case 5:
                strReturn = "Manage" + strBusinessClass + "s" + ".jsp";

                break;

            case 6:
                strReturn = "Modify" + strBusinessClass + ".jsp";

                break;

            case 7:
                strReturn = "DoModify" + strBusinessClass + ".jsp";

                break;

            default:
                strReturn = "Manage" + getFirstCaps( strPluginName ) + "sHome.jsp";

                break;
        }

        return strReturn;
    }

    /**
     * Returns the value of a string with first letter in caps
     * @param strValue The value to be transformed
     * @return The first letter is in Capital
     */
    private String getFirstCaps( String strValue )
    {
        String strFirstLetter = strValue.substring( 0, 1 );
        String strLettersLeft = strValue.substring( 1 );
        String strValueCap = strFirstLetter.toUpperCase(  ) + strLettersLeft.toLowerCase(  );

        return strValueCap;
    }
}
