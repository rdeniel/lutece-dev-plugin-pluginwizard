/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.service;

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.portal.service.util.AppLogService;
import java.io.IOException;
import java.io.StringWriter;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pierre
 */
public class MapperService
{
    private static ObjectMapper _mapper = new ObjectMapper();
    
    
    public static String getJson( PluginModel model )
    {
        StringWriter sw = new StringWriter();
        try
        {
            _mapper.writeValue( sw ,  model );
        }
        catch (IOException ex)
        {
            AppLogService.error("Error while writing JSON " + ex.getMessage(), ex);
        }
        return sw.toString();
        
    }
    
    public static PluginModel readJson( String strJson )
    {
        try
        {
            PluginModel model = _mapper.readValue( strJson, PluginModel.class );
            return model;
        }
        catch (IOException ex)
        {
            AppLogService.error("Error while reading JSON " + ex.getMessage(), ex);
        }
        return null;
    }
}
