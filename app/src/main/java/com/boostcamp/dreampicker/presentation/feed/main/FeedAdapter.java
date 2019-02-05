package com.boostcamp.dreampicker.presentation.feed.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.presentation.BaseRecyclerViewAdapter;
import com.boostcamp.dreampicker.presentation.listener.VoteDragListener;
import com.boostcamp.dreampicker.presentation.listener.VoteIconTouchListener;
import com.boostcamp.dreampicker.utils.Constant;

import androidx.annotation.NonNull;

public class FeedAdapter extends BaseRecyclerViewAdapter<Feed, FeedViewHolder> {
    interface OnVoteListener {
        void onVote(@NonNull VoteResult result);
    }

    private View.OnTouchListener voteTouchListener = new VoteIconTouchListener();
    private OnVoteListener onVoteListener = null;

    @Override
    protected void onBindView(@NonNull final FeedViewHolder holder, int position) {
        holder.bindTo(getItem(holder.getAdapterPosition()));

        holder.getBinding().sbSelector.setOnTouchListener(voteTouchListener);

        holder.getBinding().flFeedLeft.setOnDragListener(new VoteDragListener(
                () -> {
                    if(onVoteListener != null) {
                        onVoteListener.onVote(
                                new VoteResult(getItem(holder.getAdapterPosition()), Constant.LEFT));
                    }
                }));
        holder.getBinding().flFeedRight.setOnDragListener(new VoteDragListener(
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

    void setOnVoteListener(@NonNull OnVoteListener onVoteListener) {
        this.onVoteListener = onVoteListener;
    }
}
