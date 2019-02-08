package com.boostcamp.dreampicker.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

public class Constant {
    public static final int NONE = 0, LEFT = 1, RIGHT = 2;

    @IntDef({NONE, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VoteFlag { }

    public static final String IMAGE_LEFT = "left";
    public static final String IMAGE_RIGHT = "right";

    @StringDef({IMAGE_LEFT, IMAGE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageFlag { }
}
