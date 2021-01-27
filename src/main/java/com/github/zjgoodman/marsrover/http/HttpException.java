package com.github.zjgoodman.marsrover.http;

public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HttpException( WebResponse response ) {
        super( String.format( "HTTP response %d: '%s' when communicating with %s",
            response.getStatus(),
            response.getBodyAsString(),
            response.getLocation() ) );
    }
}
