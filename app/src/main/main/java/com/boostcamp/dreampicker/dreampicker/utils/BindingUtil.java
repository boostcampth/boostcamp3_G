package com.boostcamp.dreampicker.utils;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import androidx.databinding.BindingAdapter;

public class BindingUtil {
    @BindingAdapter({"rcProgress"})
    public static void setRcProgress(RoundCornerProgressBar progressBar, int value) {
        progressBar.setProgress(value);
    }

    @BindingAdapter({"rcMax"})
    public static void setRcMax(RoundCornerProgressBar progressBar, int value) {
        progressBar.setMax(value);
    }
}
