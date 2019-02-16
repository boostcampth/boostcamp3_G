package com.boostcamp.dreampicker.presentation.feed.detail;

import com.boostcamp.dreampicker.data.model.FeedDetail;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedDetailViewModel extends BaseViewModel {

    private static final String ERROR_NOT_EXIST = "Not Exists user information";
    private static final int ERROR_REPEAT_COUNT = 3;
    // true itemA, false itemB
    @NonNull
    private final MutableLiveData<Boolean> position = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<FeedDetail> feedDetail = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository repository;
    @NonNull
    private final String userId;
    @NonNull
    private final String feedId;

    FeedDetailViewModel(@NonNull final FeedRepository feedRepository,
                        @NonNull final String userId,
                        @NonNull final String feedId) {
        this.repository = feedRepository;
        this.userId = userId;
        this.feedId = feedId;
        isLoading.setValue(false);
        position.setValue(true); // itemA로 초기값 설정
    }

    void loadFeedDetail() {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        isLoading.setValue(true);
        load();
    }

    void vote() {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        final FeedDetail feedDetail = this.feedDetail.getValue();
        final Boolean position = this.position.getValue();
        if (feedDetail == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
        }
        isLoading.setValue(true);
        final String selectionId;
        if (Boolean.TRUE.equals(position)) {
            selectionId = feedDetail.getItemA().getId();
        } else {
            selectionId = feedDetail.getItemB().getId();
        }
        addDisposable(repository.vote(userId, feedId, selectionId)
                .observeOn(AndroidSchedulers.mainThread())
                .retry(ERROR_REPEAT_COUNT)
                .subscribe(this::load,
                        error -> {
                            isLoading.setValue(false);
                            this.error.setValue(error);
                        }));
    }

    private void load() {
        addDisposable(repository.getFeedDetail(userId, feedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feed -> {
                    this.feedDetail.setValue(feed);
                    isLoading.setValue(false);
                }, e -> {
                    isLoading.setValue(false);
                    error.setValue(e);
                }));
    }

    void changePosition() {
        if (Boolean.TRUE.equals(this.position.getValue())) {
            this.position.setValue(false);
        } else {
            this.position.setValue(true);
        }

    }

    @NonNull
    public LiveData<FeedDetail> getFeedDetail() {
        return feedDetail;
    }

    @NonNull
    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @NonNull
    MutableLiveData<Throwable> getError() {
        return error;
    }

    @NonNull
    public MutableLiveData<Boolean> getPosition() {
        return position;
    }
}
