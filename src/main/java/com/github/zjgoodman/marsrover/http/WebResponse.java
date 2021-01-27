package com.github.zjgoodman.marsrover.http;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.core.Response;

public class WebResponse {
    private final Response response;

    public WebResponse( Response response ) {
        this.response = response;
    }

    public String getBodyAsString() {
        return response.readEntity( String.class );
    }

    public InputStream getBodyAsStream() {
        return response.readEntity( InputStream.class );
    }

    public URI getLocation() {
        return response.getLocation();
    }

    public int getStatus() {
        return response.getStatus();
    }

    public void assert200() {
        if ( getStatus() != 200 ) {
            throw new HttpException( this );
        }
    }
}
