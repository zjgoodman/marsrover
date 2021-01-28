package com.github.zjgoodman.marsrover.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.zjgoodman.marsrover.Config;

public class DateParser {
    /**
     * Per instructions, supported dates should be as follows:
     * 
     * 02/27/17
     * June 2, 2018
     * Jul-13-2016
     */
    private static final List<DateFormat> DATE_FORMATS = Arrays.asList(
        new SimpleDateFormat( "MM/dd/yy" ),
        new SimpleDateFormat( "MMM dd, yyyy" ),
        new SimpleDateFormat( "MMM-dd-yyyy" ),
        new SimpleDateFormat( Config.NASA_DATE_FORMAT ) );

    public Set<Date> parseDates( Collection<String> dateStrings ) {
        return dateStrings.stream().map( this::parseDate ).collect( Collectors.toSet() );
    }

    public Date parseDate( String dateString ) {
        for ( DateFormat dateFormat : DATE_FORMATS ) {
            try {
                return dateFormat.parse( dateString );
            } catch ( ParseException e ) {
                // try another date format
            }
        }
        throw new IllegalArgumentException( "Unsupported date format: " + dateString );
    }
}
