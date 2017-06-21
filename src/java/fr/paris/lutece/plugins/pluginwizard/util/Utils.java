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
package fr.paris.lutece.plugins.pluginwizard.util;


/**
 * Utils for plugin wizard
 */
public final class Utils
{
	public final static String MODULE="MODULE";
	
    /** private constructor */
    private Utils(  )
    {
    }

    /**
    * Returns the Proper Name
    * @param strSource the source
    * @return source name
    */
    public static String getProperName( String strSource )
    {
        int nIndex = 0;
        boolean bUpper = true;
        StringBuilder strBuffer = new StringBuilder(  );

        while ( nIndex < strSource.length(  ) )
        {
            char c = strSource.charAt( nIndex );

            if ( c == '_' )
            {
                // skip by reading the next char
                nIndex++;
                bUpper = true;
            }

            if ( bUpper )
            {
                String strChar = strSource.substring( nIndex, nIndex + 1 );
                c = strChar.toUpperCase(  ).charAt( 0 );
                bUpper = false;
            }

            strBuffer.append( c );
            nIndex++;
        }

        return strBuffer.toString(  );
    }

    /**
     * Convert first letter to lower case
     * @param strSource The source
     * @return the converted string
     */
    public static String firstLowerCase( String strSource )
    {
        return strSource.substring( 0, 1 ).toLowerCase(  ) + strSource.substring( 1, strSource.length(  ) );
    }
}
