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

import java.util.ArrayList;
import java.util.List;

/**
 * GeneratorsList
 */
public class GenerationScheme
{
    private String _strName;
    private String _strCoreVersion;
    private List<Generator> _listGenerators;
    private boolean _bIsDefault = false;
    
    /**
     * @return the _strName
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * @param strName
     *            the _strName to set
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * @return the _strCoreVersion
     */
    public String getCoreVersion( )
    {
        return _strCoreVersion;
    }

    /**
     * @param strCoreVersion
     *            the _strCoreVersion to set
     */
    public void setCoreVersion( String strCoreVersion )
    {
        _strCoreVersion = strCoreVersion;
    }

    /**
     * @return the _generatorsList
     */
    public List<Generator> getGeneratorsList( )
    {
        if( _listGenerators != null ) 
        {
            return ( List<Generator> )( ( ( ArrayList<Generator> )_listGenerators ).clone( ) );
        }
        else
        {
            return null;
        }
    }

    /**
     * @param generatorsList
     *            the _generatorsList to set
     */
    public void setGeneratorsList( List<Generator> generatorsList )
    {
        if( generatorsList != null ) 
        {
            _listGenerators = ( List<Generator> )( ( ( ArrayList<Generator> )generatorsList ).clone( ) );
        }
        else
        {
            _listGenerators = null;
        }
    }

    /**
     * get is default
     * 
     * @return true if default
     */
    public boolean isDefault( )
    {
        return _bIsDefault;
    }

    /**
     * set is default
     * 
     * @param _bIsDefault
     */
    public void setIsDefault( boolean _bIsDefault )
    {
        this._bIsDefault = _bIsDefault;
    }

}
