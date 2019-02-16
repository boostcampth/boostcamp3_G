package com.boostcamp.dreampicker.presentation.feed.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedDetailImageBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class FeedDetailFragment extends Fragment {
    private static final String ARGUMENT_IMAGEURL = "ARGUMENT_IMAGEURL";
    private String imageUrl;
    private FragmentFeedDetailImageBinding binding;

    public FeedDetailFragment() {

    }

    static Fragment newInstance(@NonNull String imageUrl) {
        final FeedDetailFragment fragment = new FeedDetailFragment();
        final Bundle args = new Bundle();
        args.putString(ARGUMENT_IMAGEURL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            imageUrl = savedInstanceState.getString(ARGUMENT_IMAGEURL);
        } else {
            final Bundle args = getArguments();
            if (args != null) {
                imageUrl = args.getString(ARGUMENT_IMAGEURL);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_detail_image, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setImageUrl(imageUrl);
    }
}
