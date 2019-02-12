package com.boostcamp.dreampicker.presentation.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.MyFeed;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class MyFeedAdapter extends ListAdapter<MyFeed, MyFeedItemViewHolder> {
    protected MyFeedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyFeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFeedItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyFeedItemViewHolder holder, int position) {
        final MyFeed item = getItem(position);
        holder.bindTo(item);
    }

    private static final DiffUtil.ItemCallback<MyFeed> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MyFeed>() {
                @Override
                public boolean areItemsTheSame(@NonNull MyFeed oldItem, @NonNull MyFeed newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull MyFeed oldItem, @NonNull MyFeed newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
