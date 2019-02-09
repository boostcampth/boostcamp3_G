package com.boostcamp.dreampicker.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

public class Constant {
    public static final int NONE = 0, A = 1, B = 2;

    @IntDef({NONE, A, B})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Selection { }

    public static final String IMAGE_LEFT = "left";
    public static final String IMAGE_RIGHT = "right";

    @StringDef({IMAGE_LEFT, IMAGE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageFlag { }
}
