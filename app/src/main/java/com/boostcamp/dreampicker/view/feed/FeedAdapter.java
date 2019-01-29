package com.boostcamp.dreampicker.view.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.BR;
import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.model.Feed;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private List<Feed> feedList = new ArrayList<>();
    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.binding.setVariable(BR.item, feedList.get(position));
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public void addAll(List<Feed> feedList) {
        this.feedList = feedList;
        notifyDataSetChanged();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        ItemFeedBinding binding;
        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
