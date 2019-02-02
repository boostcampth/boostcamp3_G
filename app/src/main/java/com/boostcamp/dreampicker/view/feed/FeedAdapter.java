package com.boostcamp.dreampicker.view.feed;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.view.adapter.BaseRecyclerViewAdapter;
import com.boostcamp.dreampicker.viewmodel.FeedViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends BaseRecyclerViewAdapter<Feed, FeedAdapter.FeedViewHolder> {
    private final FeedViewModel viewModel;

    public FeedAdapter(@NonNull FeedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void onBindView(@NonNull final FeedViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemFeedBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_feed, parent, false);

        return new FeedViewHolder(binding);
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        private final ItemFeedBinding binding;

        FeedViewHolder(@NonNull ItemFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull FeedViewModel viewModel, int position) {
            binding.setViewModel(viewModel);
            binding.setPosition(position);
            binding.executePendingBindings();
        }
    }
}
