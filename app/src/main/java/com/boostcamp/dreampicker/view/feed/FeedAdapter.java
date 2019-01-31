package com.boostcamp.dreampicker.view.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.BR;
import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.view.adapter.BaseRecyclerViewAdapter;
import com.boostcamp.dreampicker.view.feed.drag.VoteContainerDragListener;
import com.boostcamp.dreampicker.view.feed.drag.VoteIconTouchListener;
import com.boostcamp.dreampicker.viewmodel.FeedVoteViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends BaseRecyclerViewAdapter<Feed, FeedAdapter.FeedViewHolder> {
    @Override
    protected void onBindView(@NonNull final FeedViewHolder holder, int position) {
        Feed feed = itemList.get(position);
        ItemFeedBinding binding = holder.binding;

        FeedVoteViewModel voteViewModel = new FeedVoteViewModel(feed);

        binding.setVariable(BR.item, feed);
        binding.setVariable(BR.vote, voteViewModel);

        // 투표 아이콘 터치 리스너
        binding.btnFinger.setOnTouchListener(new VoteIconTouchListener());
        // Todo : 이후 해당 피드에서만 투표 되도록 조정 필요
        binding.btnFinger.setTag(feed.getId());

        // 투표 FrameLayout의 드래그 리스너
        VoteContainerDragListener leftListener = new VoteContainerDragListener(feed.getId());
        leftListener.setOnDropListener(() -> voteViewModel.vote(FeedVoteViewModel.VotePosition.LEFT));

        VoteContainerDragListener rightListener = new VoteContainerDragListener(feed.getId());
        rightListener.setOnDropListener(() -> voteViewModel.vote(FeedVoteViewModel.VotePosition.RIGHT));

        binding.containerLeft.container.setOnDragListener(leftListener);
        binding.containerRight.container.setOnDragListener(rightListener);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        private ItemFeedBinding binding;
        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
