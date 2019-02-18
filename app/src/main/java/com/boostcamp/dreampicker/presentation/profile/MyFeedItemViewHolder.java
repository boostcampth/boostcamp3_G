package com.boostcamp.dreampicker.presentation.profile;

import android.view.View;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.databinding.ItemMyFeedBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

class MyFeedItemViewHolder extends RecyclerView.ViewHolder {

    private final ItemMyFeedBinding binding;

    MyFeedItemViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    void bindTo(MyFeed feed) {
        binding.setItem(feed);
    }

    ItemMyFeedBinding binding(){
        return binding;
    }
}
