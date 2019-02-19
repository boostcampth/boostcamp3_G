package com.boostcamp.dreampicker.data.remote.vision.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("normal")
    @Expose
    private float normal;

    @SerializedName("soft")
    @Expose
    private float soft;

    @SerializedName("adult")
    @Expose
    private float adult;

    public float getNormal() {
        return normal;
    }

    public float getSoft() {
        return soft;
    }

    public float getAdult() {
        return adult;
    }
}
