package com.github.zjgoodman.marsrover.http.gson;

import com.github.zjgoodman.marsrover.PhotoMetadata;
import com.google.gson.annotations.SerializedName;

public class GsonPhotoMetadata implements PhotoMetadata {
    @SerializedName( "img_src" )
    private String url;

    @SerializedName( "id" )
    private String id;

    @Override
    public String getPayloadURL() {
        return url;
    }

    @Override
    public String getId() {
        return id;
    }
}
