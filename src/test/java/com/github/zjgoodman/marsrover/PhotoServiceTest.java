package com.github.zjgoodman.marsrover;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.github.zjgoodman.marsrover.http.NasaWebClient;

import org.junit.Assert;
import org.junit.Test;

public class PhotoServiceTest {
    @Test
    public void testGetPhotoMetadata() throws Exception {
        NasaWebClient webClient = new NasaWebClient( Config.NASA_API_BASE_URL, Config.NASA_API_KEY );
        PhotoService photoService = new PhotoService( webClient );
        DateFormat dateFormat = new SimpleDateFormat( Config.NASA_DATE_FORMAT );
        Date date = dateFormat.parse( "2015-05-30" );
        List<PhotoMetadata> actualURLs = photoService.getPhotoMetadata( date, Config.NASA_DEFAULT_ROVER_NAME ).get();
        Assert.assertEquals( 856, actualURLs.size() );
    }
}
