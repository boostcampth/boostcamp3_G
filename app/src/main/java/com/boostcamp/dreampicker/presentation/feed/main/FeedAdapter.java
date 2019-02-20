package com.boostcamp.dreampicker.presentation.feed.main;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.utils.custom.ShineSelectionGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class FeedAdapter extends ListAdapter<Feed, FeedViewHolder> {
    interface OnItemClickListener {
        void onItemClick(@NonNull final String feedId,
                         @NonNull final String imageUrlA,
                         @NonNull final String imageUrlB);
    }

    interface OnProfileClickListener {
        void onProfileClick(@NonNull String writer);
    }

    interface OnVoteListener {
        void onVote(@NonNull final Pair<String, String> pair);
    }

    FeedAdapter(@NonNull final OnVoteListener onVoteListener,
                @NonNull final OnItemClickListener onItemClickListener,
                @NonNull final OnProfileClickListener onProfileClickListener) {
        super(DIFF_CALLBACK);
        this.onVoteListener = onVoteListener;
        this.onItemClickListener = onItemClickListener;
        this.onProfileClickListener = onProfileClickListener;
    }

    @NonNull
    private final OnVoteListener onVoteListener;

    @NonNull
    private final OnItemClickListener onItemClickListener;

    /*@NonNull
    private final View.OnTouchListener touchListener = new VoteIconTouchListener();*/

    @NonNull
    private final OnProfileClickListener onProfileClickListener;

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

        final ShineSelectionGroup group = holder.getBinding().selection;

        if (feed.getSelectionId() == null) {
            group.dropCancel();
            holder.getBinding().voteResult.setVisibility(View.GONE);
        } else {
            holder.getBinding().voteResult.setVisibility(View.VISIBLE);
            if (feed.getSelectionId().equals(feed.getItemA().getId())) {
                group.dropLeft();
            } else {
                group.dropRight();
            }
        }

        holder.itemView.setOnClickListener(__ ->
                onItemClickListener.onItemClick(
                        feed.getId(),
                        feed.getItemA().getImageUrl(),
                        feed.getItemB().getImageUrl()
                ));

        holder.getBinding().ivWriterImg.setOnClickListener(v ->
                onProfileClickListener.onProfileClick(feed.getWriter().getId()));
        /*
        holder.getBinding().ivFeedImageA.setOnDragListener(new VoteDragListener(
                () -> {
                    if (feed.getSelectionId() == null || !feed.getSelectionId().equals(feed.getItemA().getId())) {
                        onVoteListener.onVote(new Pair<>(feed.getId(), feed.getItemA().getId()));
                    }
                }));

        holder.getBinding().ivWriterImg.setOnClickListener(v ->
                onProfileClickListener.onProfileClick(feed.getWriter().getId()));*/
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
