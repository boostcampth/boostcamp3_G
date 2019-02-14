package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.data.common.FirebaseManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedViewModel extends BaseViewModel {
    private static final int PAGE_SIZE = 4;
    private static final int ERROR_REPEAT_COUNT = 3;
    private static final String ERROR_NOT_EXIST = "Not Exists user information";
    @NonNull
    private final MutableLiveData<List<Feed>> feedList = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository repository;
    private Date startAfter;

    FeedViewModel(@NonNull final FeedRepository repository) {
        this.repository = repository;
        isLoading.setValue(false);
        isLastPage.setValue(false);
    }

    void loadFeedList() {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        if (Boolean.TRUE.equals(isLoading.getValue()) || Boolean.TRUE.equals(isLastPage.getValue())) {
            return;
        }
        isLoading.setValue(true);
        final List<Feed> feedList = this.feedList.getValue() == null
                ? new ArrayList<>() : this.feedList.getValue();
        addDisposable(repository.getNotEndedFeedList(userId, startAfter == null
                ? new Date() : startAfter, PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .retry(ERROR_REPEAT_COUNT)
                .subscribe(list -> {
                    if (list.size() > 0) {
                        feedList.addAll(list);
                        this.feedList.setValue(feedList);
                        this.startAfter = list.get(list.size() - 1).getDate();
                    }
                    if (list.size() < PAGE_SIZE) {
                        isLastPage.setValue(true);
                    }
                    isLoading.setValue(false);
                }, e -> {
                    isLoading.setValue(true);
                    error.setValue(e);
                }));
    }

    void vote(@NonNull final String feedId, @NonNull final String selectionId) {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        isLoading.setValue(true);
        addDisposable(repository.vote(userId, feedId, selectionId)
                .observeOn(AndroidSchedulers.mainThread())
                .retry(ERROR_REPEAT_COUNT)
                .subscribe(() -> getFeed(userId, feedId),
                        e -> {
                            isLoading.setValue(false);
                            error.setValue(e);
                        }));
    }

    private void getFeed(@NonNull final String userId, @NonNull final String feedId) {
        addDisposable(repository.getFeed(userId, feedId)
                .observeOn(AndroidSchedulers.mainThread())
                .retry(ERROR_REPEAT_COUNT)
                .subscribe(feed -> {
                    isLoading.setValue(false);
                    updateFeedList(feed);
                }, e -> {
                    isLoading.setValue(false);
                    error.setValue(e);
                }));
    }

    private void updateFeedList(@NonNull final Feed feed) {
        final List<Feed> feedList = this.feedList.getValue();
        if (feedList == null) {
            return;
        }
        for (int i = 0; i < feedList.size(); i++) {
            final Feed item = feedList.get(i);
            if (item.getId().equals(feed.getId())) {
                feedList.set(i, feed);
                break;
            }
        }
        this.feedList.setValue(feedList);
    }

    void refresh() {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        feedList.setValue(new ArrayList<>());
        startAfter = null;
        isLastPage.setValue(false);
        loadFeedList();
    }

    @NonNull
    public LiveData<List<Feed>> getFeedList() {
        return feedList;
    }

    @NonNull
    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @NonNull
    MutableLiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    @NonNull
    LiveData<Throwable> getError() {
        return error;
    }
}
