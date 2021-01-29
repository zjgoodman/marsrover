package com.github.zjgoodman.marsrover.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.github.zjgoodman.marsrover.Config;
import com.github.zjgoodman.marsrover.PhotoMetadata;
import com.github.zjgoodman.marsrover.PhotoService;
import com.github.zjgoodman.marsrover.util.DateParser;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PhotoController {
    private final PhotoService photoService = new PhotoService();

    private final DateParser dateParser = new DateParser();

    @CrossOrigin( origins = "*" ) // NOTE: would not allow * in production
    @RequestMapping( "/photos" )
    public List<PhotoMetadata> getPhotoMetaData( @RequestParam( name = "date" ) String dateString,
        @RequestParam( name = "rover", defaultValue = Config.NASA_DEFAULT_ROVER_NAME ) String rover ) throws InterruptedException, ExecutionException {
        Date date = dateParser.parseDate( dateString );
        return photoService.getPhotoMetadata( date, Config.NASA_DEFAULT_ROVER_NAME ).get();
    }
}