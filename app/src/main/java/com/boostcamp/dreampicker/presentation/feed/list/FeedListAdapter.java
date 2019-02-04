package com.boostcamp.dreampicker.presentation.feed.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ItemFeedListBinding;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.presentation.BaseRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FeedListAdapter extends BaseRecyclerViewAdapter<Feed, FeedListAdapter.ViewHolder> {

    @Override
    protected void onBindView(ViewHolder holder, int position) {

        holder.binding.setFeed(itemList.get(position));

        holder.binding.ivLeft.setOnClickListener(view ->
                onItemClickListener.onItemClick(position));

        holder.binding.ivRight.setOnClickListener(view ->
                onItemClickListener.onItemClick(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed_list, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemFeedListBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
