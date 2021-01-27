package com.github.zjgoodman.marsrover.cli;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.github.zjgoodman.marsrover.Config;
import com.github.zjgoodman.marsrover.Photo;
import com.github.zjgoodman.marsrover.PhotoService;
import com.github.zjgoodman.marsrover.WebClient;
import com.github.zjgoodman.marsrover.util.DateParser;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command( name = "download", description = "Downloads a photo from NASA" )
public class PhotoCLI implements Runnable {
    @Option( names = "--endpoint", description = "token to authenticate with github", interactive = true, arity = "0..1" )
    private String endpoint = Config.NASA_API_BASE_URL;

    @Option( names = "--token", required = true, description = "token to authenticate with github", interactive = true, arity = "0..1" )
    private String apiKey;

    @Option( names = "--date", required = true, description = "the date to download" )
    private List<String> dateStrings;

    @Option( names = { "-h", "--help" }, usageHelp = true, description = "display a help message" )
    private boolean helpRequested = false;

    public static void main( String[] args ) {
        int exitCode = new CommandLine( new PhotoCLI() ).execute( args );
        System.exit( exitCode );
    }

    @Override
    public void run() {
        WebClient nasaClient = new WebClient( endpoint, apiKey );
        PhotoService photoService = new PhotoService( nasaClient );
        Set<Date> dates = new DateParser().parseDates( dateStrings );
        System.out.println( dates );
        for ( Date date : dates ) {
            try {
                Photo photo = photoService.getPhoto( date ).get();
                System.out.println( photo.getURL() );
            } catch ( InterruptedException | ExecutionException e ) {
                System.err.println( "Unable to download photo for date " + date );
            }
        }
    }
}
