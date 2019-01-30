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

    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, int resourceId){

        GlideApp.with(imageView).load(resourceId).into(imageView);
    }

    @BindingAdapter({"circleImage"})
    public static void loadCircleImage(ImageView imageView, int resourceId){

        GlideApp.with(imageView).load(resourceId).circleCrop().into(imageView);
    }

    @BindingAdapter({"circleImage"})
    public static void loadCircleImage(ImageView imageView, String url){
        if(url == null) {
            return;
        }

        GlideApp.with(imageView).load(url).circleCrop().into(imageView);
    }

}
