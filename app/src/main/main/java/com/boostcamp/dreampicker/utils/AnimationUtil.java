package com.boostcamp.dreampicker.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

public class AnimationUtil {
    @BindingAdapter({"slide"})
    public static void slideEffect(LinearLayout layout, boolean isVoted) {
        if (isVoted) {
            layout.setVisibility(View.VISIBLE);

            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(300);
            layout.setAnimation(anim);
        }
    }
}
