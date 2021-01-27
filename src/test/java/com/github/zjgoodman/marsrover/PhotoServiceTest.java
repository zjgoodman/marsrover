package com.github.zjgoodman.marsrover;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class PhotoServiceTest {
    @Test
    public void testGetPhoto() throws Exception {
        WebClient webClient = new WebClient( Config.NASA_API_BASE_URL, Config.NASA_API_KEY );
        PhotoService photoService = new PhotoService( webClient );
        DateFormat dateFormat = new SimpleDateFormat( Config.NASA_DATE_FORMAT );
        Date date = dateFormat.parse( "2015-05-30" );
        Photo photo = photoService.getPhoto( date ).get();
        String expectedURL = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG";
        Assert.assertEquals( expectedURL, photo.getURL() );
    }
}
