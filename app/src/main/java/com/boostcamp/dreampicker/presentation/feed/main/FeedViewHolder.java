package com.boostcamp.dreampicker.presentation.feed.main;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.utils.Constant;
import com.sackcentury.shinebuttonlib.ShineButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
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
    }

    public ItemFeedBinding getBinding() {
        return binding;
    }

    void startVoteAnimation(Context context, @Constant.VoteFlag int flag) {
        final ShineButton button = binding.sbSelector;
        final ConstraintSet set = new ConstraintSet();

        TransitionManager.beginDelayedTransition(binding.root);

        if(flag == Constant.LEFT) {
            set.clone(context, R.layout.item_feed_vote_left);
        } else if(flag == Constant.RIGHT) {
            set.clone(context, R.layout.item_feed_vote_right);
        }
        set.applyTo(binding.root);

        if (!button.isChecked()) {
            button.performClick();
        } else {
            button.performClick();
            button.performClick();
        }
    }
}
