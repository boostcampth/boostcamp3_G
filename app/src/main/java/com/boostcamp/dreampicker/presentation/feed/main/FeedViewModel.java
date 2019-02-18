package com.boostcamp.dreampicker.presentation.feed.main;

import android.util.Pair;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class FeedViewModel extends BaseViewModel {
    private static final int PAGE_SIZE = 4;
    private static final int ERROR_REPEAT_COUNT = 3;
    private static final String ERROR_NOT_EXIST = "Not exists feed";

    @NonNull
    private final MutableLiveData<List<Feed>> feedList = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private Date startAfter;
    @NonNull
    private final FeedRepository repository;
    @NonNull
    private final PublishSubject<Pair<String, String>> voteSubject = PublishSubject.create();

    FeedViewModel(@NonNull final FeedRepository repository) {
        this.repository = repository;
    }

    void init(@NonNull final String userId) {
        isLoading.setValue(false);
        isLastPage.setValue(false);

        addDisposable(voteSubject.switchMap(pair -> {
            final Feed oldFeed = findFeedById(pair.first);
            if (oldFeed == null) {
                return Observable.error(new IllegalArgumentException(ERROR_NOT_EXIST));
            }

            final Feed newFeed = createVoteFeed(oldFeed, pair.second);
            if (newFeed == null) {
                return Observable.just(pair.first);
            } else {
                updateFeedList(newFeed);
                return repository.vote(userId, pair.first, pair.second).andThen(Observable.just(pair.first));
            }
        }).subscribe(feedId -> getFeed(userId, feedId), error::setValue));
    }

    void loadFeedList(@NonNull final String userId) {
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

    void getFeed(@NonNull final String userId, @NonNull final String feedId) {
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

    @Nullable
    private Feed findFeedById(@NonNull final String feedId) {
        final List<Feed> feedList = this.feedList.getValue();
        if (feedList != null) {
            for (final Feed feed : feedList) {
                if (feed.getId().equals(feedId)) {
                    return feed;
                }
            }
        }
        return null;
    }

    void refresh(@NonNull final String userId) {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        feedList.setValue(new ArrayList<>());
        startAfter = null;
        isLastPage.setValue(false);
        loadFeedList(userId);
    }

    @Nullable
    private Feed createVoteFeed(@NonNull final Feed oldFeed, @NonNull final String selectionId) {
        final Feed feed = new Feed(oldFeed);
        if (feed.getSelectionId() == null) {
            if (selectionId.equals(feed.getItemA().getId())) {
                feed.getItemA().setVoteCount(feed.getItemA().getVoteCount() + 1);
            } else {
                feed.getItemB().setVoteCount(feed.getItemB().getVoteCount() + 1);
            }
        } else {
            if (!feed.getSelectionId().equals(selectionId)) {
                if (selectionId.equals(feed.getItemA().getId())) {
                    feed.getItemA().setVoteCount(feed.getItemA().getVoteCount() + 1);
                    feed.getItemB().setVoteCount(feed.getItemB().getVoteCount() - 1);
                } else {
                    feed.getItemB().setVoteCount(feed.getItemB().getVoteCount() + 1);
                    feed.getItemA().setVoteCount(feed.getItemA().getVoteCount() - 1);
                }
            } else {
                return null;
            }
        }
        feed.setSelectionId(selectionId);
        return feed;
    }

    @NonNull
    PublishSubject<Pair<String, String>> getVoteSubject() {
        return voteSubject;
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
    LiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    @NonNull
    LiveData<Throwable> getError() {
        return error;
    }
}
