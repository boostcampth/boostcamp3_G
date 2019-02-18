package com.boostcamp.dreampicker.presentation.feed.voted;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

class VotedAdapter extends PagedListAdapter<VotedFeed, VotedViewHolder> {
    // Todo : 상세보기 이동
    interface OnitemClickListener {
        void onClick(@NonNull String feedId);
    }

    VotedAdapter() {
        super(DIFF_CALLBACK);
    }

    @Nullable
    private AdapterView.OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public VotedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VotedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voted_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VotedViewHolder holder, int position) {
        final VotedFeed feed = getItem(holder.getAdapterPosition());
        if (feed != null) {
            holder.bindTo(feed);
        }
    }

    public void setOnItemClickListener(@Nullable final AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private static final DiffUtil.ItemCallback<VotedFeed> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<VotedFeed>() {
                @Override
                public boolean areItemsTheSame(@NonNull VotedFeed oldItem, @NonNull VotedFeed newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull VotedFeed oldItem, @NonNull VotedFeed newItem) {
                    return oldItem.equals(newItem);
                }
            };

}
