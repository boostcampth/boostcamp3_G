package com.boostcamp.dreampicker.presentation.feed.main;

import android.view.View;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
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

    public ViewDataBinding getBinding() {
        return binding;
    }
}
