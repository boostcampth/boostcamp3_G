package com.boostcamp.dreampicker.presentation.feed.voted;

import android.view.View;

import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;
import com.boostcamp.dreampicker.databinding.ItemVotedFeedBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class VotedViewHolder extends RecyclerView.ViewHolder {
    private final ItemVotedFeedBinding binding;
    VotedViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    void bindTo(@NonNull final VotedFeed feed) {
        binding.setFeed(feed);
    }

    public ItemVotedFeedBinding getBinding() {
        return binding;
    }
}