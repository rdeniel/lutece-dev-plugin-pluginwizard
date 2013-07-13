/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.pluginwizard.service.generator;

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pierre
 */
public class PomGeneratorTest
{
 
    /**
     * Test of generate method, of class PomGenerator.
     */
    @Test
    public void testGenerate()
    {
        System.out.println("generate");
        PluginModel pm = GeneratorUtils.getTestModel();
        PomGenerator instance = new PomGenerator();
        Map result = instance.generate(pm);
        GeneratorUtils.outputMap( result );
    }
}