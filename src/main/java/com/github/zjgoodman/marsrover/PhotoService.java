package com.github.zjgoodman.marsrover;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

public class PhotoService {
    private final WebClient webClient;

    public PhotoService( WebClient webClient ) {
        this.webClient = webClient;
    }

    public CompletableFuture<Photo> getPhoto( Date date ) {
        return getPhoto( date, Config.NASA_DEFAULT_ROVER_NAME );
    }

    public CompletableFuture<Photo> getPhoto( Date date, String roverName ) {
        DateFormat dateFormat = new SimpleDateFormat( Config.NASA_DATE_FORMAT );
        String dateString = dateFormat.format( date );
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put( Config.NASA_DATE_QUERY_PARAM, dateString );
        return webClient.getJsonAsync( "/rovers/" + roverName + "/photos", queryParameters ).thenApply( webResponse -> {
            if ( webResponse.getStatus() != 200 ) {
                throw new HttpException( webResponse );
            }
            List<GsonPhoto> photos = new Gson().fromJson( webResponse.getBody(), GsonPhotosList.class ).getPhotos();
            if ( photos.isEmpty() ) {
                throw new IllegalStateException( "No photos found for date " + dateString );
            }
            return photos.get( 0 ); // TODO return the first photo?
        } );
    }
}
