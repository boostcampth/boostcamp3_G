package com.boostcamp.dreampicker.presentation.feed.feedMain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.utils.Constant;
import com.boostcamp.dreampicker.presentation.BaseRecyclerViewAdapter;
import com.boostcamp.dreampicker.presentation.listener.VoteContainerDragListener;
import com.boostcamp.dreampicker.presentation.listener.VoteIconTouchListener;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends BaseRecyclerViewAdapter<Feed, FeedAdapter.FeedViewHolder> {
    private final FeedViewModel viewModel;
    private View.OnTouchListener touchListener = new VoteIconTouchListener();
    private View.OnDragListener leftDragListener;
    private View.OnDragListener rightDragListener;

    public FeedAdapter(@NonNull FeedViewModel viewModel) {
        this.viewModel = viewModel;
        leftDragListener = new VoteContainerDragListener((id) -> viewModel.vote(id, Constant.LEFT));
        rightDragListener = new VoteContainerDragListener((id) -> viewModel.vote(id, Constant.RIGHT));
    }

    @Override
    protected void onBindView(@NonNull final FeedViewHolder holder, int position) {
        holder.bind(viewModel, position);
        holder.binding.sbSelector.setOnTouchListener(touchListener);
        holder.binding.flFeedLeft.setOnDragListener(leftDragListener);
        holder.binding.flFeedRight.setOnDragListener(rightDragListener);
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
