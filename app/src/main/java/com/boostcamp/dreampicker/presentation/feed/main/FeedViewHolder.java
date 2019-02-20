package com.boostcamp.dreampicker.presentation.feed.main;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.databinding.ItemFeedLegacyBinding;
import com.sackcentury.shinebuttonlib.ShineButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

class FeedViewHolder extends RecyclerView.ViewHolder {
    private final ItemFeedBinding binding;

    FeedViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    void bindTo(@NonNull final Feed feed) {
        binding.setFeed(feed);
        /*binding.ivFeedImageA.setTag(R.id.iv_feed_image_a, feed.getId());
        binding.ivFeedImageB.setTag(R.id.iv_feed_image_b, feed.getId());
        binding.sbSelector.setTag(R.id.sb_selector, feed.getId());*/
    }

    void startVoteAnimation(@NonNull final String itemAId,
                            @NonNull final String itemBId,
                            @Nullable final String selectionId) {
/*        final Context context = binding.getRoot().getContext();
        final int size = context.getResources().getDimensionPixelSize(R.dimen.vote_icon_size);

        final ShineButton button = binding.sbSelector;
        button.showAnim();

        button.startAnimation(new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                final ConstraintLayout.LayoutParams params =
                        new ConstraintLayout.LayoutParams(size, size);

                params.topToBottom = R.id.tv_feed_content;
                params.bottomToTop = R.id.vote_result;

                if(selectionId == null) {
                    params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                } else {
                    if (itemAId.equals(selectionId)) {
                        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                        params.endToStart = R.id.guideline_feed_image_horizontal;
                    } else if (itemBId.equals(selectionId)) {
                        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                        params.startToEnd = R.id.guideline_feed_image_horizontal;
                    }
                }
                button.setLayoutParams(params);
            }
        });*/
    }

    public ItemFeedBinding getBinding() {
        return binding;
    }
}