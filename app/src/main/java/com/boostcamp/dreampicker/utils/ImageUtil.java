package com.boostcamp.dreampicker.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class ImageUtil {

    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, String url){
        if (url == null) {
            return;
        }

        GlideApp.with(imageView).load(url).into(imageView);
    }

    @BindingAdapter({"circleImage"})
    public static void loadCircleImage(ImageView imageView, String url){
        if(url == null) {
            return;
        }

        GlideApp.with(imageView).load(url).circleCrop().into(imageView);
    }
}
