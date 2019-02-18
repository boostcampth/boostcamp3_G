package com.boostcamp.dreampicker.data.source.firestore.vision.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("label")
    @Expose
    private List<String> tagList;

    @SerializedName("label_kr")
    @Expose
    private List<String> tagListKr;

    public List<String> getTagList() {
        return tagList;
    }

    public List<String> getTagListKr() {
        return tagListKr;
    }
}
