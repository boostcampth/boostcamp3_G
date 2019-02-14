package com.boostcamp.dreampicker.presentation.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.MyFeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

public class MyFeedAdapter extends PagedListAdapter<MyFeed, MyFeedItemViewHolder> {

    @Nullable
    private OnEndButtonClickListener onEndButtonClickListener;

    MyFeedAdapter(@Nullable OnEndButtonClickListener onEndButtonClickListener) {
        super(DIFF_CALLBACK);
        this.onEndButtonClickListener = onEndButtonClickListener;
    }

    @NonNull
    @Override
    public MyFeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFeedItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyFeedItemViewHolder holder, int position) {
        final MyFeed item = getItem(holder.getAdapterPosition());
        holder.bindTo(item);

        holder.binding().btnEnd.setOnClickListener(v -> {
            if (onEndButtonClickListener != null) {
                onEndButtonClickListener.onEndedButtonClick(item);
            }
        });
    }

    interface OnEndButtonClickListener {
        void onEndedButtonClick(MyFeed item);
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
