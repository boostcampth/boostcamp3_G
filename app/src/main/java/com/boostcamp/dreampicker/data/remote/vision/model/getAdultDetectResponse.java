package com.boostcamp.dreampicker.data.remote.vision.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getAdultDetectResponse {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }
}
