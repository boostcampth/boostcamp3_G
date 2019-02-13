package com.boostcamp.dreampicker.presentation.feed.legacydetail;

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

public class FeedDetailLeftFragment extends BaseFragment<FragmentFeedDetailImageBinding> {

   public FeedDetailLeftFragment() {

   }

   static Fragment newInstance() {
       return new FeedDetailLeftFragment();
   }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Test
        GlideApp.with(this)
                .load(R.drawable.jajang)
                .transform(new RoundedCorners(20))
                .into(binding.ivFeedDetailImage);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed_detail_image;
    }
}
