package com.github.zjgoodman.marsrover;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NasaWebClient {
    private static final String API_KEY_PARAM = "api_key";

    private final WebTarget rootEndpoint;

    public NasaWebClient( String endpoint ) {
        this( endpoint, null );
    }

    public NasaWebClient( String url, String token ) {
        WebTarget endpoint = ClientBuilder.newBuilder().build().target( url );
        if ( token != null ) {
            endpoint = endpoint.queryParam( API_KEY_PARAM, token );
        }
        this.rootEndpoint = endpoint;
    }

    public WebResponse getJson( String path, Map<String, String> queryParameters ) {
        WebTarget endpoint = rootEndpoint.path( path );
        for ( Map.Entry<String, String> queryParam : queryParameters.entrySet() ) {
            endpoint = endpoint.queryParam( queryParam.getKey(), queryParam.getValue() );
        }
        Invocation.Builder builder = endpoint.request( MediaType.APPLICATION_JSON );
        Response response = builder.get();
        return new WebResponse( response );
    }

    public CompletableFuture<WebResponse> getJsonAsync( String path, Map<String, String> queryParameters ) {
        return CompletableFuture.supplyAsync( () -> getJson( path, queryParameters ) );
    }
}
