package com.github.zjgoodman.marsrover;

import java.net.URI;

import javax.ws.rs.core.Response;

public class WebResponse {
    private final String body;

    private final URI location;

    private final int status;

    public WebResponse( String body, URI location, int status ) {
        this.body = body;
        this.location = location;
        this.status = status;
    }

    public WebResponse( Response response ) {
        this( response.readEntity( String.class ),
            response.getLocation(),
            response.getStatus() );
    }

    public String getBody() {
        return body;
    }

    public URI getLocation() {
        return location;
    }

    public int getStatus() {
        return status;
    }
}
