package com.boostcamp.dreampicker.presentation.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.FeedPrevious;
import com.boostcamp.dreampicker.databinding.ItemProfileFeedBinding;
import com.boostcamp.dreampicker.presentation.BasePagedListAdapter;
import com.boostcamp.dreampicker.presentation.listener.OnItemClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileFeedAdapter extends BasePagedListAdapter<FeedPrevious, ProfileFeedAdapter.ViewHolder> {

    public ProfileFeedAdapter(@Nullable OnItemClickListener<FeedPrevious> onItemClickListener) {
        super(DIFF_CALLBACK, onItemClickListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile_feed, parent, false));
    }

    @Override
    public void onBindView(@NonNull ViewHolder holder, int position) {

        final FeedPrevious item = getItem(position);

        holder.bindTo(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemProfileFeedBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bindTo(FeedPrevious feed) {
            binding.setItem(feed);
        }
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
