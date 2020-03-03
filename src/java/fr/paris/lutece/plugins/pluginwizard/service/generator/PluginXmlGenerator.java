/*
 * Copyright (c) 2002-2019, Mairie de Paris
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
import fr.paris.lutece.plugins.pluginwizard.business.model.Feature;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.plugins.pluginwizard.service.ModelService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Class produces the xml file describing the generated plugin
 *
 */
public class PluginXmlGenerator extends AbstractFileGenerator
{
    private static final String PATH = "webapp/WEB-INF/plugins/";

    /**
     * {@inheritDoc }
     */
    @Override
    public Map generate( PluginModel pm, String generationSchemeName )
    {
        return generateFile( pm, generationSchemeName );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String getCode( PluginModel pm, String generationSchemeName )
    {
        Map<String, Object> model = new HashMap<>( );

        setJspName( pm );
        model.put( Markers.MARK_PLUGIN, pm );

        return build( model );
    }

    public static void setJspName( PluginModel pm )
    {
        for ( Feature feature : pm.getFeatures( ) )
        {
            List<BusinessClass> listBusiness = ModelService.getBusinessClassesByFeature( pm, feature.getId( ) );

            if ( ( listBusiness != null ) && ( listBusiness.size( ) > 0 ) )
            {
                List<String> BusinessNames = new ArrayList<>( );
                int i = 0;
                int id = 0;

                for ( BusinessClass bc : listBusiness )
                {
                    BusinessNames.add( bc.getBusinessClass( ) );
                }

                Collections.sort( BusinessNames );

                for ( BusinessClass bc : listBusiness )
                {
                    if ( bc.getBusinessClass( ).equals( BusinessNames.get( 0 ) ) )
                    {
                        id = i;
                    }

                    i++;
                }

                BusinessClass businessClass = listBusiness.get( id );
                feature.setJspName( "Manage" + businessClass.getBusinessClassCapsFirst( ) + "s.jsp" );
            }
            else
            {
                feature.setJspName( feature.getFeatureName( ) + ".jsp" );
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String getFilename( PluginModel pm )
    {
        return pm.getPluginName( ).toLowerCase( ) + ".xml";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getPath( )
    {
        return PATH;
    }
}
