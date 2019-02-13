package com.boostcamp.dreampicker.presentation.result;

import com.boostcamp.dreampicker.data.local.room.dao.VotedFeedDao;
import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

class ResultViewModel extends BaseViewModel {
    private static final int PAGE_SIZE = 10;
    @NonNull
    private LiveData<PagedList<VotedFeed>> votedFeedList;

    ResultViewModel(@NonNull final VotedFeedDao votedFeedDao) {
        this.votedFeedList =
                new LivePagedListBuilder<>(votedFeedDao.selectAll(), PAGE_SIZE).build();
    }

    @NonNull
    LiveData<PagedList<VotedFeed>> getVotedFeedList() {
        return votedFeedList;
    }
}
