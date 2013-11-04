/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.service.generator;

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;

import junit.framework.TestCase;


/**
 *
 * @author pierre
 */
public class GeneratorUtilsTest extends TestCase
{
    public void testGetTestModel(  )
    {
        System.out.println( "test Generators Utils" );

        PluginModel pm = GeneratorUtils.getTestModel(  );
        assertTrue( pm.getApplications(  ).size(  ) > 0 );
    }
}
