package com.github.zjgoodman.marsrover;

import com.google.gson.annotations.SerializedName;

public class GsonPhoto implements Photo {
    @SerializedName( "img_src" )
    private String url;

    @Override
    public String getURL() {
        return url;
    }
}
