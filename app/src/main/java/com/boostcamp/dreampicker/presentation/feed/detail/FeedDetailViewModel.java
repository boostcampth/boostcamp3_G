package com.boostcamp.dreampicker.presentation.feed.detail;

import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.data.model.FeedDetail;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedDetailViewModel extends BaseViewModel {

    private static final String ERROR_NOT_EXIST = "Not Exists user information";
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

    FeedDetailViewModel(@NonNull final FeedRepository feedRepository) {
        this.repository = feedRepository;
        isLoading.setValue(false);
        position.setValue(true);
    }

    void loadFeedDetail(@NonNull final String feedId) {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        isLoading.setValue(true);
        addDisposable(repository.getFeedDetail(userId, feedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feed -> {
                            this.feedDetail.setValue(feed);
                            isLoading.setValue(false);
                        },error->{
                    isLoading.setValue(false);
                    this.error.setValue(error);
                }));
    }

    void vote(@NonNull final String feedId, @NonNull final String selectionId) {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId == null) {
            error.setValue(new IllegalArgumentException(ERROR_NOT_EXIST));
            return;
        }
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        isLoading.setValue(true);
        addDisposable(repository.vote(userId, feedId, selectionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feed -> isLoading.setValue(false),
                        error::setValue));
    }

    void changePosition(){
        if(this.position.getValue()!=null){
            if(Boolean.TRUE.equals(this.position.getValue())){
                this.position.setValue(false);
            } else{
               this.position.setValue(true);
            }
        }
    }

    @NonNull
    public LiveData<FeedDetail> getFeedDetail() {
        return feedDetail;
    }


    @NonNull
    MutableLiveData<Boolean> getIsLoading() {
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
