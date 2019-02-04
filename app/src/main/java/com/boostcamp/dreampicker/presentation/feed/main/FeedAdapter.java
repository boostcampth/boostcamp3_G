package com.boostcamp.dreampicker.presentation.feed.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.presentation.BaseRecyclerViewAdapter;
import com.boostcamp.dreampicker.presentation.listener.VoteDragListener;
import com.boostcamp.dreampicker.presentation.listener.VoteIconTouchListener;
import com.boostcamp.dreampicker.utils.Constant;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends BaseRecyclerViewAdapter<Feed, FeedAdapter.FeedViewHolder> {

    interface OnVoteListener {
        void onVote(@NonNull VoteResult result);
    }

    private OnVoteListener onVoteListener = null;

    @Override
    protected void onBindView(@NonNull final FeedViewHolder holder, int position) {
        holder.binding.setFeed(getItem(holder.getAdapterPosition()));

        holder.binding.sbSelector.setOnTouchListener(new VoteIconTouchListener());

        holder.binding.flFeedLeft.setOnDragListener(new VoteDragListener(
                () -> {
                    if(onVoteListener != null) {
                        onVoteListener.onVote(
                                new VoteResult(getItem(holder.getAdapterPosition()), Constant.LEFT));
                    }
                }));

        holder.binding.flFeedRight.setOnDragListener(new VoteDragListener(
                () -> {
                    if(onVoteListener != null) {
                        onVoteListener.onVote(
                                new VoteResult(getItem(holder.getAdapterPosition()), Constant.RIGHT));
                    }
                }));
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false));
    }

    public void setOnVoteListener(@NonNull OnVoteListener onVoteListener) {
        this.onVoteListener = onVoteListener;
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        private ItemFeedBinding binding;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
