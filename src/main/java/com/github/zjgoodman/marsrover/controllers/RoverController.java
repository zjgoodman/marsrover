package com.github.zjgoodman.marsrover.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RoverController {
    @CrossOrigin( origins = "*" ) // NOTE: would not allow * in production
    @RequestMapping( "/rovers" )
    public List<String> getPhotoMetaData() {
        return Arrays.asList( "curiosity", "opportunity", "spirit" );
    }
}