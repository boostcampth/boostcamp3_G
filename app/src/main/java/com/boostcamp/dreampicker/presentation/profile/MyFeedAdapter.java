package com.boostcamp.dreampicker.presentation.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.MyFeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class MyFeedAdapter extends ListAdapter<MyFeed, MyFeedItemViewHolder> {

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

        holder.binding().tvVoteState.setText(
                item.isEnded() ? R.string.profile_vote_ended : R.string.profile_vote_not_ended
        );

        holder.binding().btnEnd.setBackgroundResource(
                item.isEnded() ? R.drawable.line_round : R.drawable.line_round_red
        );

        holder.binding().btnEnd.setText(
                item.isEnded() ? R.string.profile_request_restart : R.string.profile_request_end
        );
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
