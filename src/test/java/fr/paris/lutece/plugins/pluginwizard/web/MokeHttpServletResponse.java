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
package fr.paris.lutece.plugins.pluginwizard.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author levy
 */
public class MokeHttpServletResponse implements HttpServletResponse
{

    private int _status;
    private long contentLength;
    @Override
    public void addCookie( Cookie cookie )
    {
    }

    @Override
    public boolean containsHeader( String string )
    {
        return true;
    }

    @Override
    public String encodeURL( String string )
    {
        return string;
    }

    @Override
    public String encodeRedirectURL( String string )
    {
        return string;
    }

    @Override
    public String encodeUrl( String string )
    {
        return string;
    }

    @Override
    public String encodeRedirectUrl( String string )
    {
        return string;
    }

    @Override
    public void sendError( int i, String string ) throws IOException
    {
    }

    @Override
    public void sendError( int i ) throws IOException
    {
    }

    @Override
    public void sendRedirect( String string ) throws IOException
    {
    }

    @Override
    public void setDateHeader( String string, long l )
    {
    }

    @Override
    public void addDateHeader( String string, long l )
    {
    }

    @Override
    public void setHeader( String string, String string1 )
    {
    }

    @Override
    public void addHeader( String string, String string1 )
    {
    }

    @Override
    public void setIntHeader( String string, int i )
    {
    }

    @Override
    public void addIntHeader( String string, int i )
    {
    }

    @Override
    public void setStatus( int status )
    {
        if ( !this.isCommitted( ) ) 
        {
            this._status = status;
        }
    }

    @Override
    public void setStatus( int status, String string )
    {
        setStatus(status);
    }

    @Override
    public String getCharacterEncoding(  )
    {
        return "UTF-8";
    }

    @Override
    public String getContentType(  )
    {
        return "text";
    }

    @Override
    public ServletOutputStream getOutputStream(  ) throws IOException
    {
        return null;
    }

    @Override
    public PrintWriter getWriter(  ) throws IOException
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCharacterEncoding( String string )
    {
    }

    @Override
    public void setContentLength( int i )
    {
    }

    @Override
    public void setContentType( String string )
    {
    }

    @Override
    public void setBufferSize( int i )
    {
    }

    @Override
    public int getBufferSize(  )
    {
        return 1024;
    }

    @Override
    public void flushBuffer(  ) throws IOException
    {
    }

    @Override
    public void resetBuffer(  )
    {
    }

    @Override
    public boolean isCommitted(  )
    {
        return true;
    }

    @Override
    public void reset(  )
    {
    }

    @Override
    public void setLocale( Locale locale )
    {
    }

    @Override
    public Locale getLocale(  )
    {
        return Locale.getDefault(  );
    }

    @Override
    public int getStatus() 
    {
        return 200;
    }

    @Override
    public String getHeader(String string) 
    {
        return "mock";
    }

    @Override
    public Collection<String> getHeaders( String string ) 
    {
        List<String> listHeaders = new ArrayList<String>( );
        listHeaders.add( "mock" );
        
        return listHeaders;
    }

    @Override
    public Collection<String> getHeaderNames() 
    {
        List<String> listHeaders = new ArrayList<String>( );
        listHeaders.add("mock");
        
        return listHeaders;
    }

    @Override
    public void setContentLengthLong(long contentLength) 
    {
        this.contentLength = contentLength;
	//doAddHeaderValue(HttpHeaders.CONTENT_LENGTH, contentLength, true);
    }
    
}
