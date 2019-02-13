package com.boostcamp.dreampicker.presentation.feed.voted;

import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class VotedViewModel extends BaseViewModel {
    private static final int PAGE_SIZE = 5;
    @NonNull
    private LiveData<PagedList<VotedFeed>> votedFeedList;

    VotedViewModel(@NonNull final FeedRepository repository) {
        this.votedFeedList =
                new LivePagedListBuilder<>(repository.getMyVotedFeedList(), PAGE_SIZE).build();
    }

    @NonNull
    LiveData<PagedList<VotedFeed>> getVotedFeedList() {
        return votedFeedList;
    }
}
