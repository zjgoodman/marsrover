package com.github.zjgoodman.marsrover.http.gson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GsonPhotosList {
    @SerializedName( "photos" )
    private List<GsonPhotoMetadata> photos;

    public List<GsonPhotoMetadata> getPhotos() {
        return photos;
    }
}
