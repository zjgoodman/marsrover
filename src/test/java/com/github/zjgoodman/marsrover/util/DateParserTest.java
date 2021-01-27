package com.github.zjgoodman.marsrover.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.zjgoodman.marsrover.Config;

import org.junit.Assert;
import org.junit.Test;

public class DateParserTest {
    @Test
    public void testSupportedFormats() throws Exception {
        DateParser parser = new DateParser();
        Date expectedDate = new SimpleDateFormat( Config.NASA_DATE_FORMAT ).parse( "2021-01-27" );
        Assert.assertEquals( expectedDate, parser.parseDate( "01/27/21" ) );
        Assert.assertEquals( expectedDate, parser.parseDate( "January 27, 2021" ) );
        Assert.assertEquals( expectedDate, parser.parseDate( "Jan-27-2021" ) );
        Assert.assertEquals( expectedDate, parser.parseDate( "2021-01-27" ) );
    }

    @Test
    public void testUnsupportedFormats() throws Exception {
        DateParser parser = new DateParser();
        try {
            parser.parseDate( "BOGUS" );
            Assert.fail( "Expected IllegalArgumentException since the provided date format is unsupported" );
        } catch ( IllegalArgumentException e ) {
            // expected
        }
        try {
            parser.parseDate( "2021 Jan 27" );
            Assert.fail( "Expected IllegalArgumentException since the provided date format is unsupported" );
        } catch ( IllegalArgumentException e ) {
            // expected
        }
    }
}
