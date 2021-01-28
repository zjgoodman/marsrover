package com.github.zjgoodman.marsrover.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import com.github.zjgoodman.marsrover.Config;
import com.github.zjgoodman.marsrover.PhotoService;
import com.github.zjgoodman.marsrover.http.NasaWebClient;
import com.github.zjgoodman.marsrover.util.DateParser;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command( name = "download", description = "Downloads a photo from NASA" )
public class PhotoCLI implements Runnable {
    @Option( names = "--endpoint", description = "token to authenticate with github" )
    private String endpoint = Config.NASA_API_BASE_URL;

    @Option( names = "--token", description = "token to authenticate with github", interactive = true, arity = "0..1" )
    private String apiKey = Config.NASA_API_KEY;

    @Option( names = "--date", description = "the date to download" )
    private Set<String> dateStrings = new HashSet<>();

    @Option( names = "--date-file", description = "a file containing dates" )
    private File dateFile;

    @Option( names = { "-h", "--help" }, usageHelp = true, description = "display a help message" )
    private boolean helpRequested = false;

    @Option( names = "--rover", description = "the rover whose photos should be pulled" )
    private String rover = Config.NASA_DEFAULT_ROVER_NAME;

    public static void main( String[] args ) {
        int exitCode = new CommandLine( new PhotoCLI() ).execute( args );
        System.exit( exitCode );
    }

    @Override
    public void run() {
        NasaWebClient nasaClient = new NasaWebClient( endpoint, apiKey );
        PhotoService photoService = new PhotoService( nasaClient );
        loadTextFile();
        if ( dateStrings.isEmpty() ) {
            throw new IllegalArgumentException( "Must specify --date-file or --date" );
        }
        Set<Date> dates = new DateParser().parseDates( dateStrings );
        for ( Date date : dates ) {
            try {
                photoService.downloadPhoto( date, rover ).get();
            } catch ( InterruptedException | ExecutionException e ) {
                throw new CompletionException( e );
            }
        }
    }

    private void loadTextFile() {
        if ( dateFile != null ) {
            try (Scanner scanner = new Scanner( dateFile )) {
                while ( scanner.hasNextLine() ) {
                    dateStrings.add( scanner.nextLine().trim() );
                }
            } catch ( FileNotFoundException e ) {
                throw new UncheckedIOException( e );
            }
        }
    }
}
