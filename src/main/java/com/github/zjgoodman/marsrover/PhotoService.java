package com.github.zjgoodman.marsrover;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PhotoService {
    private final WebClient webClient;

    public PhotoService( WebClient webClient ) {
        this.webClient = webClient;
    }

    public CompletableFuture<WebResponse> getPhoto( Date date ) {
        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        String dateString = dateFormat.format( date );
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put( "earth_date", dateString );
        return webClient.getJsonAsync( "/rovers/curiosity/photos", queryParameters );
    }
}
