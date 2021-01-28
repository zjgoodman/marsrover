package com.github.zjgoodman.marsrover;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.github.zjgoodman.marsrover.http.NasaWebClient;
import com.github.zjgoodman.marsrover.http.PhotoWebClient;
import com.github.zjgoodman.marsrover.http.WebResponse;
import com.github.zjgoodman.marsrover.http.gson.GsonPhotoMetadata;
import com.github.zjgoodman.marsrover.http.gson.GsonPhotosList;
import com.google.gson.Gson;

public class PhotoService {
    private final NasaWebClient webClient;

    private final PhotoWebClient photoClient = new PhotoWebClient();

    public PhotoService( NasaWebClient webClient ) {
        this.webClient = webClient;
    }

    public CompletableFuture<List<PhotoMetadata>> downloadPhoto( Date date, String roverName ) {
        return getPhotoMetadata( date, roverName ).thenApply( metadata -> {
            for ( PhotoMetadata photo : metadata ) {
                File file = new File( "build/" + photo.getId() + ".jpg" );
                try {
                    if ( file.createNewFile() ) {
                        writePhotoPayloadToFile( photo, file );
                    }
                } catch ( IOException e ) {
                    throw new CompletionException( e );
                }
            }
            return metadata;
        } );
    }

    public CompletableFuture<List<PhotoMetadata>> getPhotoMetadata( Date date, String roverName ) {
        DateFormat dateFormat = new SimpleDateFormat( Config.NASA_DATE_FORMAT );
        String dateString = dateFormat.format( date );
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put( Config.NASA_DATE_QUERY_PARAM, dateString );
        return webClient.getJsonAsync( "/rovers/" + roverName + "/photos", queryParameters )
            .thenApply( this::extractPhotoMetadata );
    }

    private InputStream downloadPhotoPayload( PhotoMetadata photo ) {
        WebResponse response = photoClient.getPhoto( photo.getPayloadURL() );
        if ( response.getStatus() == 301 ) {
            String movedURL = response.getLocation().toString();
            response = photoClient.getPhoto( movedURL );
        }
        response.assert200();
        return response.getBodyAsStream();
    }

    private void writePhotoPayloadToFile( PhotoMetadata photo, File photoFile ) throws IOException {
        try (FileOutputStream fileWriter = new FileOutputStream( photoFile ); InputStream photoContents = downloadPhotoPayload( photo )) {
            byte[] photoBytes = photoContents.readAllBytes();
            fileWriter.write( photoBytes );
        }
    }

    private List<PhotoMetadata> extractPhotoMetadata( WebResponse webResponse ) {
        webResponse.assert200();
        List<GsonPhotoMetadata> photos = new Gson().fromJson( webResponse.getBodyAsString(), GsonPhotosList.class ).getPhotos();
        if ( photos.isEmpty() ) {
            throw new IllegalStateException( "No photos found for date" );
        }
        return new ArrayList<>( photos );
    }
}
