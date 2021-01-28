# Mars Rover Photo Downloader
This application takes in one or more dates and uses the [NASA REST API](https://api.nasa.gov/) to download the images that were taken by the mars rover on the supplied date(s). Images are stored in the `build` folder. If an image is already downloaded, it won't be downloaded again.

There are two ways to use this application:
1. [Command line interface (default)](#command-line-interface)
2. [RESTful web service API](#restful-web-service-api)

This application was built using Java 11, open jdk. If you do not have Java 11 installed on your machine, I recomend running this application as a Docker container. 

Note that because the rovers take A LOT of photos each day, it takes time to download all of them. For example, on May 30, 2015, over 800 photos were taken by the rover `curiosity`.

Note that NASA for some reason stores lots of copies of the same image, with different IDs.

## Supported date formats
The app and the CLI both support the following date formats:

Format | Example
-- | --
MM/dd/yy | 02/27/17
MMM dd, yyyy | June 2, 2018
MMM-dd-yyyy | Jul-13-2016
yyyy-MM-dd | 2015-05-30

## Command line interface
To interact with the command line interface, use the following command:
```
./gradlew run --args="<your args>"
```

### Command line args
The CLI supports the following args

Arg | Example | Description
-- | -- | --
`--date` | `--date 2015-05-30 --date 2015-05-31` | Use the provided date. Can be repeated to provide multiple dates
`--date-file` | `--date-file path/to/file.txt` | A text file containing dates to use. Each date should be a new line
`--rover` | `--rover curiosity` | Specifies which rover to download photos from

For example, to download all photos from rover `curiosity` that were taken on May 30, 2015, use the following command:
```
./gradlew run --args="--date 2015-05-30 --rover curiosity"
```

## RESTful web service API
You can run the RESTful web service API in two ways:
1. [Docker (recommended)](#restful-web-service-api-docker)
2. [Directly in Java (requires Java 11)](#restful-web-service-api-java)

Once you have the service running, see [interacting with the app](#restful-web-service-api-interactions) for information on how to interact with the app.

### RESTful web service API Docker
There are two ways to use the docker image:
1. [Pulling from docker hub (recommended)](#docker-hub)
2. [Building the image from source](#building-the-docker-image)

#### Docker Hub
To run the RESTful web service API via Docker by pulling from docker hub, use the following command:
```
docker run --rm -d --name marsrover -p 8080:8080 zjgoodman/marsrover
```
This will pull the image from docker hub and run it. See [here](#restful-web-service-api-interactions) for information on how to interact with the app.

#### Building the docker image
To build the RESTful web service API docker image, you'll first need to build the source code [directly in Java](#restful-web-service-api-java), which requires Java 11.

Once you've built the source code, use the following command to build and run the docker image:
```
docker build . -f src/main/docker/Dockerfile -t zjgoodman/marsrover
docker run --rm -d --name marsrover -p 8080:8080 zjgoodman/marsrover
```
See [here](#restful-web-service-api-interactions) for information on how to interact with the app.

### RESTful web service API Java
Since the RESTful web service API is not the default method of interacting with the app, you need to change one line in the [`application` block of `build.gradle`](https://github.com/zjgoodman/marsrover/blob/main/build.gradle#L40-L46) to configure the app to run the RESTful web service API. 

Uncomment the `App` line and comment the `PhotoCLI` line, as shown below:
```groovy
application {
    // TO RUN VIA CLI:
    // mainClass = 'com.github.zjgoodman.marsrover.cli.PhotoCLI'
    
    // TO RUN VIA SPRINGBOOT:
    mainClass = 'com.github.zjgoodman.marsrover.App'
}
```

Now you can run the application. To run the application, use the following command:
```
./gradlew bootRun
```

See see [here](#restful-web-service-api-interactions) for information on how to interact with the app.

### RESTful web service API interactions
The API provides two endpoints:
1. [Rovers](#rovers-api)
2. [Photos](#photos-api)

#### Rovers API
Use `/rovers` to see the list of available rovers. For example: http://localhost:8080/rovers

#### Photos API
Use `/photos?date=yyyy-MM-dd` to see the photo metadata for all the photos taken on the provided date. For example: http://localhost:8080/photos?date=2015-05-30

Use the `?rover` param to see photos taken by other rovers. Use the [rovers API](#rovers-api) to see all available rovers. The default rover is `curiosity`. For example: http://localhost:8080/photos?date=2015-05-30&rover=spirit