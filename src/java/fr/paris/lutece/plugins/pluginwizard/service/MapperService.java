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
package fr.paris.lutece.plugins.pluginwizard.service;

import org.codehaus.jackson.map.DeserializationConfig;
import fr.paris.lutece.plugins.pluginwizard.business.model.PluginModel;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.StringWriter;


/**
 * Mapper Service
 */
public final class MapperService
{
    private static ObjectMapper _mapper = new ObjectMapper(  );

    /** Private constructor */
    private MapperService(  )
    {
    }

    /**
     * Transform the model into a JSON String
     * @param model The model
     * @return A JSON String
     */
    public static String getJson( PluginModel model )
    {
        StringWriter sw = new StringWriter(  );

        try
        {
            _mapper.writeValue( sw, model );
        }
        catch ( Exception ex )
        {
            AppLogService.error( "Error while writing JSON " + ex.getMessage(  ), ex );
        }

        return sw.toString(  );
    }

    /**
     * Read a JSON String to fill a model
     * @param strJson The JSON String
     * @return The model
     */
    public static PluginModel readJson( String strJson )
    {
        try
        {
            PluginModel model = _mapper.readValue( strJson, PluginModel.class );

            return model;
        }
        catch ( Exception ex )
        {
            AppLogService.error( "Error while reading JSON " + ex.getMessage(  ) + "JSON = " + strJson, ex );
        }

        return null;
    }
}
