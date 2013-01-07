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

import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppLogService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pierre
 */
public class GeneratorService
{

    private List<Generator> _listGenerators = new ArrayList<Generator>();

    public Map<String, String> getGeneratedSources( Plugin plugin, PluginModel model )
    {
        initGenerators();
        return generateSources( plugin, model );
    }

    private void initGenerators()
    {
        _listGenerators.add( new BackOfficeJspGenerator() );
        _listGenerators.add( new BackOfficeTemplateCodeGenerator() );
        _listGenerators.add( new BusinessClassCodeGenerator() );
        _listGenerators.add( new JspBeanCodeGenerator() );
        _listGenerators.add( new PluginXmlGenerator() );
        _listGenerators.add( new PomGenerator() );
        _listGenerators.add( new PortletGenerator() );
        _listGenerators.add( new PortletJspBeanGenerator() );
        _listGenerators.add( new PortletJspFilesGenerator() );
        _listGenerators.add( new PortletTemplateGenerator() );
        _listGenerators.add( new PortletXslGenerator() );
        _listGenerators.add( new PropertiesGenerator() );
        _listGenerators.add( new ResourcesCodeGenerator() );
        _listGenerators.add( new SpringContextXmlGenerator() );
        _listGenerators.add( new SqlCodeGenerator() );
        _listGenerators.add( new XPageGenerator() );

    }

    private Map<String, String> generateSources( Plugin plugin, PluginModel model )
    {
        Map<String, String> mapSources = new HashMap<String, String>();

        for (Generator generator : _listGenerators)
        {
            try
            {
                mapSources.putAll( generator.generate( plugin, model ) );
            }
            catch (Exception e)
            {
                AppLogService.error( e );
            }
        }
        return mapSources;
    }
}

