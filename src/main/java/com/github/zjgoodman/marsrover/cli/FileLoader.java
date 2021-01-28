package com.github.zjgoodman.marsrover.cli;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class FileLoader {
    private FileLoader() {
    }

    public static String getFileContents( File file ) {
        try {
            return new String( Files.readAllBytes( file.toPath() ) );
        } catch ( IOException e ) {
            throw new UncheckedIOException( "Unable to load report file", e );
        }
    }
}
