package com.boostcamp.dreampicker.presentation.feed.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.presentation.feed.main.listener.VoteDragListener;
import com.boostcamp.dreampicker.presentation.feed.main.listener.VoteIconTouchListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class FeedAdapter extends ListAdapter<Feed, FeedViewHolder> {
    interface OnItemClickListener {
        void onItemClick();
    }
    interface OnVoteListener {
        void onVote(@NonNull final String feedId,
                    @NonNull final String selectionId);
    }

    FeedAdapter(@NonNull final OnVoteListener onVoteListener,
                @NonNull final OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onVoteListener = onVoteListener;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    private final OnVoteListener onVoteListener;

    @NonNull
    private final OnItemClickListener onItemClickListener;

    @NonNull
    private final View.OnTouchListener touchListener = new VoteIconTouchListener();

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        final Feed feed = getItem(holder.getAdapterPosition());
        holder.bindTo(feed);

        if(feed.getSelectionId() != null) {
            holder.startVoteAnimation(feed.getItemA().getId(), feed.getItemB().getId(), feed.getSelectionId());
            holder.getBinding().voteResult.setVisibility(View.VISIBLE);
        } else {
            holder.getBinding().voteResult.setVisibility(View.GONE);
        }

        holder.getBinding().sbSelector.setOnTouchListener(touchListener);

        holder.getBinding().ivFeedImageA.setOnDragListener(new VoteDragListener(
                () -> {
                    if(feed.getSelectionId() == null || !feed.getSelectionId().equals(feed.getItemA().getId())) {
                        onVoteListener.onVote(feed.getId(), feed.getItemA().getId());
                    }
                }));

        holder.getBinding().ivFeedImageB.setOnDragListener(new VoteDragListener(
                () -> {
                    if(feed.getSelectionId() == null || !feed.getSelectionId().equals(feed.getItemB().getId())) {
                        onVoteListener.onVote(feed.getId(), feed.getItemB().getId());
                    }
                }));
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
