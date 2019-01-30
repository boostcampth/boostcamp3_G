package com.boostcamp.dreampicker.view.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.BR;
import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ItemFeedBinding;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.view.adapter.BaseRecyclerViewAdapter;
import com.boostcamp.dreampicker.viewmodel.FeedVoteViewModel;
import com.boostcamp.dreampicker.viewmodel.factory.FeedVoteViewModelFactory;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends BaseRecyclerViewAdapter<Feed, FeedAdapter.FeedViewHolder> {
    private final Fragment fragment; // Item ViewModel을 Fragment에 붙이는 용도

    FeedAdapter(@NonNull Fragment fragment) {
            this.fragment = fragment;
    }

    @Override
    protected void onBindView(@NonNull final FeedViewHolder holder, int position) {
        final Feed feed = itemList.get(position);
        FeedVoteViewModelFactory factory = new FeedVoteViewModelFactory(feed);

        FeedVoteViewModel voteViewModel = ViewModelProviders.of(fragment, factory)
                .get(FeedVoteViewModel.class);

        holder.binding.setVariable(BR.item, feed);
        holder.binding.setVariable(BR.vote, voteViewModel);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        ItemFeedBinding binding;
        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
