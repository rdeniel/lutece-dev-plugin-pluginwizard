/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.service.generator;

import fr.paris.lutece.plugins.pluginwizard.business.model.BusinessClass;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.ModelService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author duchemia
 */
public class RestConstantsGenerator extends AbstractGenerator
{
    private static final String PATH = "src/java/fr/paris/lutece/plugins/{plugin_name}/rs/";
    private static final String CONSTANT_REST = "Constants.java";

    /**
     * {@inheritDoc }
     * @param pm
     */
    @Override
    public Map generate( PluginModel pm )
    {
        HashMap map = new HashMap(  );
        
        Collection<BusinessClass> listBusinessClasses = ModelService.getBusinessClassesByRest(pm);

        if ( !listBusinessClasses.isEmpty(  ) )
        {
            String strPath = getFilePath( pm, PATH, CONSTANT_REST );
            String strSourceCode = getPage( pm, listBusinessClasses );
            map.put( strPath, strSourceCode );
        }
        return map;
    }

    /**
    * Generates the Rest code
    * @param pm The plugin model
    * @param businessClasses the collection of business classes
    * @return The code of the Rest generated
    */
    private String getPage( PluginModel pm, Collection <BusinessClass> businessClasses )
    {
        Map<String, Object> model = getModel( pm );
        model.put( Markers.MARK_PLUGIN, pm );
        model.put( Markers.MARK_BUSINESS_CLASSES, businessClasses );

        return build( model );
    }
}