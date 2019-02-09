package com.boostcamp.dreampicker.presentation.feed.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.FeedPrevious;
import com.boostcamp.dreampicker.presentation.listener.OnItemClickListener;
import com.boostcamp.dreampicker.presentation.listener.VoteDragListener;
import com.boostcamp.dreampicker.presentation.listener.VoteIconTouchListener;
import com.boostcamp.dreampicker.utils.Constant;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class FeedAdapter extends ListAdapter<FeedPrevious, FeedViewHolder> {
    private Context context;

    FeedAdapter(@NonNull OnVoteListener onVoteListener,
                @NonNull OnItemClickListener<FeedPrevious> onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onVoteListener = onVoteListener;
        this.onItemClickListener = onItemClickListener;
    }

    interface OnVoteListener {
        void onVote(VoteResult result);
    }

    @NonNull
    private final OnVoteListener onVoteListener;
    @NonNull
    private final OnItemClickListener<FeedPrevious> onItemClickListener;

    private View.OnTouchListener voteTouchListener = new VoteIconTouchListener();

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        final FeedPrevious feed = getItem(position);
        holder.bindTo(feed);

        if(feed.getVoteFlag() != Constant.NONE) {
            holder.startVoteAnimation(context, feed.getVoteFlag());
        }

        holder.itemView.setOnClickListener((v) ->
                onItemClickListener.onItemClick(getItem((holder.getAdapterPosition()))));

        holder.getBinding().sbSelector.setOnTouchListener(voteTouchListener);

        holder.getBinding().ivFeedImageLeft.setOnDragListener(new VoteDragListener(
                () -> onVoteListener.onVote(
                        new VoteResult(getItem(holder.getAdapterPosition()), Constant.LEFT))));

        holder.getBinding().ivFeedImageRight.setOnDragListener(new VoteDragListener(
                () -> onVoteListener.onVote(
                        new VoteResult(getItem(holder.getAdapterPosition()), Constant.RIGHT))));
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new FeedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false));
    }

    private static final DiffUtil.ItemCallback<FeedPrevious> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<FeedPrevious>() {
                @Override
                public boolean areItemsTheSame(@NonNull FeedPrevious oldItem, @NonNull FeedPrevious newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull FeedPrevious oldItem, @NonNull FeedPrevious newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
