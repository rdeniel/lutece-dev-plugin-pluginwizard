/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generator Service
 */
public class GeneratorService
{
    private static List<GenerationScheme> _listSchemes;

    /**
     * Generate Sources
     * 
     * @param plugin
     *            The plugin (pluginwizard)
     * @param model
     *            The plugin model to generate
     * @param nScheme
     *            The Scheme
     * @return Map that contains sources
     */
    public Map<String, String> getGeneratedSources( Plugin plugin, PluginModel model, int nScheme )
    {
        Map<String, String> mapSources = new HashMap<String, String>( );

        GenerationScheme generationScheme = _listSchemes.get( nScheme );

        List<Generator> listGenerators = generationScheme.getGeneratorsList( );

        for ( Generator generator : listGenerators )
        {
            try
            {
                generator.setCoreVersion( generationScheme.getCoreVersion( ) );
                mapSources.putAll( generator.generate( model ) );
            }
            catch( Exception e )
            {
                AppLogService.error( e.getMessage( ), e );
            }
        }

        return mapSources;
    }

    /**
     * Returns Generation schemes
     * 
     * @return Generation schemes
     */
    public static ReferenceList getGenerationSchemes( )
    {
        _listSchemes = SpringContextService.getBeansOfType( GenerationScheme.class );

        ReferenceList list = new ReferenceList( );

        for ( int i = 0; i < _listSchemes.size( ); i++ )
        {
            list.addItem( i, _listSchemes.get( i ).getName( ) );
        }

        return list;
    }

}
