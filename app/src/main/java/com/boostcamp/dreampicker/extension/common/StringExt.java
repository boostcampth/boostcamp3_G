package com.boostcamp.dreampicker.extension.common;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class StringExt {
    @Nullable
    public static List<String> toNoEndLineList(final String[] list) {
        if (list == null || list.length == 0) {
            return null;
        }
        final List<String> newList = new ArrayList<>();
        for (String str : list) {
            newList.add(str.replace("\n", " "));
        }
        return newList;
    }
}