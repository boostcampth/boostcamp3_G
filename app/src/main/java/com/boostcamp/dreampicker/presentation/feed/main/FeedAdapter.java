package com.boostcamp.dreampicker.presentation.feed.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class FeedAdapter extends ListAdapter<Feed, FeedViewHolder> {
    private Context context;
    FeedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new FeedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        final Feed feed = getItem(position);
        holder.bindTo(feed);
    }

    private static final DiffUtil.ItemCallback<Feed> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Feed>() {
                @Override
                public boolean areItemsTheSame(@NonNull Feed oldItem, @NonNull Feed newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Feed oldItem, @NonNull Feed newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
