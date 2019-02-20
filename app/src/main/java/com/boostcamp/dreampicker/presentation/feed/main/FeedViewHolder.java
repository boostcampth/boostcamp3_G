package com.boostcamp.dreampicker.presentation.feed.main;

import android.view.View;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;

import androidx.annotation.NonNull;
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
        binding.executePendingBindings();
    }

    public ItemFeedBinding getBinding() {
        return binding;
    }
}