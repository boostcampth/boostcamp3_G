package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.model.FeedPrevious;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.Constant;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedViewModel extends BaseViewModel {
    @NonNull
    private final FeedRepository repository;

    @NonNull
    private MutableLiveData<List<FeedPrevious>> feedList = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    FeedViewModel(@NonNull FeedRepository repository) {
        this.repository = repository;
        loadFeedList();
    }

    void vote(@NonNull final VoteResult result) {
        final FeedPrevious feed = new FeedPrevious(result.getFeed());
        final int flag = result.getFlag();

        if (feed.getVoteFlag() != Constant.NONE) {
            if (feed.getVoteFlag() != flag) {
                addDisposable(repository.updateFeedVote(feed.getId(), flag)
                        .subscribe(this::loadFeedList, error::setValue));
            }
        } else {
            addDisposable(repository.updateFeedVote(feed.getId(), flag)
                    .subscribe(this::loadFeedList, error::setValue));
        }
    }

    void loadFeedList() {
        addDisposable(repository.addMainFeedList(1, 10)
                .map(PagedListResponse::getItemList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> feedList.postValue(list), error::setValue));
    }

    @NonNull
    public LiveData<List<FeedPrevious>> getFeedList() {
        return feedList;
    }

    @NonNull
    LiveData<Throwable> getError() {
        return error;
    }
}
