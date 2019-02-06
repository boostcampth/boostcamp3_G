package com.boostcamp.dreampicker.presentation.feed.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedDetailImageBinding;
import com.boostcamp.dreampicker.utils.GlideApp;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class FeedDetailRightFragment extends Fragment {
    private FragmentFeedDetailImageBinding binding;

    public FeedDetailRightFragment() {

    }

    public static Fragment newInstance() {
        return new FeedDetailRightFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_detail_image, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GlideApp.with(this)
                .load(R.drawable.skin)
                .transform(new RoundedCorners(20))
                .into(binding.ivFeedDetailImage);
    }
}
