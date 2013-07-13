/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.service.generator;

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.test.LuteceTestCase;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author pierre
 */
public class PluginXmlGeneratorTest extends LuteceTestCase
{
 
    /**
     * Test of generate method, of class PluginXmlGenerator.
     */
    @Test
    public void testGenerate()
    {
        System.out.println("generate Plugin XML file");
        PluginModel pm = GeneratorUtils.getTestModel();
        PluginXmlGenerator instance = new PluginXmlGenerator();
        Map result = instance.generate(pm);
        GeneratorUtils.outputMap( result );

    }
}