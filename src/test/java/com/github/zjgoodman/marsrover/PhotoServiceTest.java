package com.github.zjgoodman.marsrover;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PhotoServiceTest {
    @Test
    public void testGetPhotoMetadata() throws Exception {
        PhotoService photoService = new PhotoService();
        DateFormat dateFormat = new SimpleDateFormat( Config.NASA_DATE_FORMAT );
        Date date = dateFormat.parse( "2015-05-30" );
        List<PhotoMetadata> actualURLs = photoService.getPhotoMetadata( date, Config.NASA_DEFAULT_ROVER_NAME ).get();
        Assert.assertEquals( 856, actualURLs.size() );
    }
}
