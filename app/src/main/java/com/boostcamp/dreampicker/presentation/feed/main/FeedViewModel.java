package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.FirebaseManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedViewModel extends BaseViewModel {
    private static final int PAGE_SIZE = 1;
    private static final String ERROR_NOT_EXIST ="Not Exists user information";
    private static final String ERROR_NULL_FEED ="FeedList is Null";
    @NonNull
    private final MutableLiveData<List<Feed>> feedList = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository repository;

    FeedViewModel(@NonNull final FeedRepository repository) {
        this.repository = repository;
        feedList.setValue(new ArrayList<>());
    }

    public void loadFeedList() {
        final String userId = FirebaseManager.getCurrentUserId();
        if(userId == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        final List<Feed> feedList = this.feedList.getValue();
        if(feedList != null) {
            Date startAfter;
            if(feedList.isEmpty()) {
                startAfter = new Date();
            } else {
                startAfter = feedList.get(feedList.size()-1).getDate();
            }
            isLoading.setValue(true);
            addDisposable(repository.getNotEndedFeedList(userId, startAfter, PAGE_SIZE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        feedList.addAll(list);
                        this.feedList.setValue(list);
                        isLoading.setValue(false);
                    }, error::setValue));
        } else { // feedList == null
            error.setValue(new IllegalStateException(ERROR_NULL_FEED));
        }
    }

    public void vote(@NonNull final String feedId, @NonNull final String selectionId) {
        final String userId = FirebaseManager.getCurrentUserId();
        if(userId == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        addDisposable(repository.vote(userId, feedId, selectionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateFeedList, error::setValue));
    }

    private void updateFeedList(@NonNull final Feed feed) {
        final List<Feed> feedList = this.feedList.getValue();
        if(feedList == null) {
            return;
        }
        for(int i=0; i<feedList.size(); i++) {
            final Feed item = feedList.get(i);
            if(item.getId().equals(feed.getId())) {
                feedList.set(i, feed);
                break;
            }
        }
        this.feedList.setValue(feedList);
    }

    @NonNull
    public LiveData<List<Feed>> getFeedList() {
        return feedList;
    }

    @NonNull
    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }
}
