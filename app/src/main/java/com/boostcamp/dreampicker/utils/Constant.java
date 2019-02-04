package com.boostcamp.dreampicker.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Constant {
    public static final int NONE = 0, LEFT = 1, RIGHT = 2;

    @IntDef({NONE, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VoteFlag { }
}
