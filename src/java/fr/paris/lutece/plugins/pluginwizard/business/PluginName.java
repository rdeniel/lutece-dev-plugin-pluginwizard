/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.business;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author levy
 */
public class PluginName
{
   // @NotEmpty
    @Pattern ( regexp = "[a-z]*")
    private String _strName;

    /**
     * @return the name
     */
    public String getName()
    {
        return _strName;
    }

    /**
     * @param strName the name to set
     */
    public void setName(String strName)
    {
        _strName = strName;
    }
}
