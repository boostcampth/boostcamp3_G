package com.boostcamp.dreampicker.presentation.feed.main;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.utils.Constant;
import com.sackcentury.shinebuttonlib.ShineButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

class FeedViewHolder extends RecyclerView.ViewHolder {
    private ItemFeedBinding binding;

    FeedViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    void bindTo(@NonNull Feed feed) {
        binding.setFeed(feed);
        binding.ivFeedImageLeft.setTag(R.id.iv_feed_image_left, feed.getId());
        binding.ivFeedImageRight.setTag(R.id.iv_feed_image_right, feed.getId());
        binding.sbSelector.setTag(R.id.sb_selector, feed.getId());
    }

    public ItemFeedBinding getBinding() {
        return binding;
    }

    void startVoteAnimation(Context context, @Constant.VoteFlag int flag) {
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

                if(flag == Constant.LEFT) {
                    params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.endToStart = R.id.guideline_feed_image_ho;
                } else if(flag == Constant.RIGHT) {
                    params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.startToEnd = R.id.guideline_feed_image_ho;
                }
                button.setLayoutParams(params);
            }
        });
    }
}
