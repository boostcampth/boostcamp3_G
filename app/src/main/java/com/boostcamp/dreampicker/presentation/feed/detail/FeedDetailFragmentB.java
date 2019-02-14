package com.boostcamp.dreampicker.presentation.feed.detail;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedDetailImageBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.utils.GlideApp;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedDetailFragmentB extends BaseFragment<FragmentFeedDetailImageBinding> {


    private static final String ARGUMENT_IMAGEURL_B = "ARGUMENT_IMAGEURLB";

    public FeedDetailFragmentB() {
    }

    static Fragment newInstance(@NonNull String imageUrl) {
        FeedDetailFragmentB fragment = new FeedDetailFragmentB();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_IMAGEURL_B, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Todo
        if ((savedInstanceState != null ? savedInstanceState.getString(ARGUMENT_IMAGEURL_B) : null) != null) {
            GlideApp.with(this)
                    .load(savedInstanceState.getString(ARGUMENT_IMAGEURL_B))
                    .transform(new RoundedCorners(20))
                    .into(binding.ivFeedDetailImage);
        } else {
            GlideApp.with(this)
                    .load(R.drawable.skin)
                    .transform(new RoundedCorners(20))
                    .into(binding.ivFeedDetailImage);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed_detail_image;
    }
}
