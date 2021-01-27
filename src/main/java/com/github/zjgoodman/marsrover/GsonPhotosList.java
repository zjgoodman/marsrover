package com.github.zjgoodman.marsrover;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GsonPhotosList {
    @SerializedName( "photos" )
    private List<GsonPhoto> photos;

    public List<GsonPhoto> getPhotos() {
        return photos;
    }
}
