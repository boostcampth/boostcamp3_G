package com.boostcamp.dreampicker.presentation.result;

import com.boostcamp.dreampicker.data.local.room.dao.VotedFeedDao;
import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

class ResultViewModel extends BaseViewModel {
    private static final int PAGE_SIZE = 10;
    @NonNull
    private LiveData<PagedList<VotedFeed>> votedFeedList;
    @NonNull
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();

    ResultViewModel(@NonNull VotedFeedDao votedFeedDao) {
        this.votedFeedList =
                new LivePagedListBuilder<>(votedFeedDao.selectAll(), PAGE_SIZE).build();
    }

    @NonNull
    LiveData<PagedList<VotedFeed>> getVotedFeedList() {
        return votedFeedList;
    }

    @NonNull
    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @NonNull
    LiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    @NonNull
    LiveData<Throwable> getError() {
        return error;
    }
}
