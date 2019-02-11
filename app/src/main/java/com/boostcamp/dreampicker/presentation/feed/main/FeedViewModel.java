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
    private final MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository repository;
    private Date startAfter;

    FeedViewModel(@NonNull final FeedRepository repository) {
        this.repository = repository;
        feedList.setValue(new ArrayList<>());
        isLoading.setValue(false);
        isLastPage.setValue(false);
    }

    public void loadItems() {
        if(isLoading.getValue() == null || isLoading.getValue()) {
            return;
        }
        if(isLastPage.getValue() == null || isLastPage.getValue()) {
            return;
        }
        final String userId = FirebaseManager.getCurrentUserId();
        if(userId == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        loadFeedList(userId);
    }

    private void loadFeedList(@NonNull final String userId) {
        final List<Feed> feedList = this.feedList.getValue();
        if(feedList != null) {
            if(feedList.isEmpty()) {
                isLastPage.setValue(false);
                startAfter = new Date();
            }
            isLoading.setValue(true);
            addDisposable(repository.getNotEndedFeedList(userId, startAfter, PAGE_SIZE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        if(list.size() > 0) {
                            startAfter = list.get(list.size()-1).getDate();
                            feedList.addAll(list);
                            this.feedList.setValue(feedList);
                        }
                        if (list.size() < PAGE_SIZE) {
                            isLastPage.setValue(true);
                        }
                        isLoading.setValue(false);
                    }, error::setValue));
        } else { // feedList == null
            error.setValue(new IllegalStateException(ERROR_NULL_FEED));
        }
    }

    public void vote(@NonNull final String feedId, @NonNull final String selectionId) {
        if(isLoading.getValue() == null || isLoading.getValue()) {
            return;
        }
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
    public MutableLiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }
}
