package com.boostcamp.dreampicker.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
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

    @BindingAdapter({"fade"})
    public static void setFade(ImageView imageView, boolean isFade) {
        if(isFade) {
            imageView.setColorFilter(Color.parseColor("#8C8C8C"), PorterDuff.Mode.MULTIPLY);
        } else {
            imageView.setColorFilter(null);
        }
    }
}
