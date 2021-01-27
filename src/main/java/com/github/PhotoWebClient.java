package com.github;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.github.zjgoodman.marsrover.WebResponse;

public class PhotoWebClient {
    public WebResponse getPhoto( String url ) {
        WebTarget endpoint = ClientBuilder.newBuilder().build().target( url );
        Invocation.Builder builder = endpoint.request();
        Response response = builder.get();
        return new WebResponse( response );
    }
}
