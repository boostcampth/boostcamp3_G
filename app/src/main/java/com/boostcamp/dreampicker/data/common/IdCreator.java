package com.boostcamp.dreampicker.data.common;

import java.util.UUID;

public class IdCreator {
    private static final String TYPE_FEED = "feed-";
    private static final String TYPE_IMAGE = "image-";

    public static String createFeedId() {
        return TYPE_FEED + createUUID();
    }

    public static String createImageId() {
        return TYPE_IMAGE + createUUID();
    }

    private static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
