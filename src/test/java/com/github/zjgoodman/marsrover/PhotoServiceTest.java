package com.github.zjgoodman.marsrover;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class PhotoServiceTest {
    @Test
    public void testGetPhoto() throws Exception {
        WebClient webClient = new WebClient( "https://api.nasa.gov/mars-photos/api/v1", "gyiCAARMmpZikoFEinbhEwChPhXbRevM0AdA6s1f" );
        PhotoService photoService = new PhotoService( webClient );
        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Date date = dateFormat.parse( "2015-05-30" );
        WebResponse response = photoService.getPhoto( date ).get();
        Assert.assertEquals( 200, response.getStatus() );
    }
}
