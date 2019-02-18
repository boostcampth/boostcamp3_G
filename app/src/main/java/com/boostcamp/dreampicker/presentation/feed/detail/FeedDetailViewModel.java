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
    private static final int positionA = 0;
    private static final int positionB = 1;
    private static final int positionNotVoted = 3;

    // 0 : itemA, 1 : itemB
    @NonNull
    private final MutableLiveData<Integer> position = new MutableLiveData<>();
    // 투표한 위치
    @NonNull
    private final MutableLiveData<Integer> votePosition = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<FeedDetail> feedDetail = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository repository;

    FeedDetailViewModel(@NonNull final FeedRepository feedRepository) {
        this.repository = feedRepository;
        isLoading.setValue(false);
        // itemA로 초기값 설정
        position.setValue(positionA);
    }

    void loadFeedDetail(@NonNull final String userId,
                        @NonNull final String feedId) {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        isLoading.setValue(true);
        load(userId, feedId);
    }

    void vote(@NonNull final String userId) {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        final FeedDetail feedDetail = this.feedDetail.getValue();
        final Integer position = this.position.getValue();

        if (feedDetail == null || position == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        isLoading.setValue(true);

        final String feedId = feedDetail.getId();
        final String selectionId;

        if (position == positionA) {
            selectionId = feedDetail.getItemA().getId();
        } else {
            selectionId = feedDetail.getItemB().getId();
        }
        addDisposable(repository.vote(userId, feedId, selectionId)
                .observeOn(AndroidSchedulers.mainThread())
                .retry(ERROR_REPEAT_COUNT)
                .subscribe(() -> load(userId, feedId),
                        e -> {
                            isLoading.setValue(false);
                            this.error.setValue(e);
                        }));
    }

    private void load(@NonNull final String userId,
                      @NonNull final String feedId) {
        addDisposable(repository.getFeedDetail(userId, feedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feed -> {
                    this.feedDetail.setValue(feed);
                    setVotePosition(feed);
                    isLoading.setValue(false);
                }, e -> {
                    isLoading.setValue(false);
                    error.setValue(e);
                }));
    }

    private void setVotePosition(@NonNull final FeedDetail feedDetail) {
        final String selectionId = feedDetail.getSelectionId();
        final String imageAId = feedDetail.getItemA().getId();

        if (selectionId == null) {
            votePosition.setValue(positionNotVoted);
        } else {
            if (selectionId.equals(imageAId)) {
                votePosition.setValue(positionA);
            } else {
                votePosition.setValue(positionB);
            }
        }
    }

    void changePosition() {
        final Integer position = this.position.getValue();
        if (position == null) return;
        if (position == positionA) {
            this.position.setValue(positionB);
        } else {
            this.position.setValue(positionA);
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
    public MutableLiveData<Integer> getPosition() {
        return position;
    }

    @NonNull
    public LiveData<Integer> getVotePosition() {
        return votePosition;
    }

}
