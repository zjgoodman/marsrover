package com.github.zjgoodman.marsrover;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebClient {
    private static final String API_KEY_PARAM = "api_key";

    private final WebTarget rootEndpoint;

    public WebClient( String endpoint, String token ) {
        rootEndpoint = ClientBuilder.newBuilder().build().target( endpoint )
            .queryParam( API_KEY_PARAM, token );
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
