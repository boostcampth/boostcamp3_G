package com.boostcamp.dreampicker.presentation.profile;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileViewModel extends BaseViewModel {
    private final int PAGE_SIZE = 4;

    @NonNull
    private final UserRepository repository;
    @NonNull
    private final String userId;

    @NonNull
    private MutableLiveData<UserDetail> user = new MutableLiveData<>();
    @NonNull
    private LiveData<PagedList<MyFeed>> myFeedListLiveData;
    @NonNull
    private MutableLiveData<PagedList<MyFeed>> myFeedList = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    ProfileViewModel(@NonNull UserRepository repository,
                     @NonNull String userId) {
        this.repository = repository;
        this.userId = userId;
        this.isLoading.setValue(false);
        myFeedListLiveData = Transformations.map(myFeedList, input -> myFeedList.getValue());
        loadUserDetail();
        loadMyFeeds();
    }

    private void loadUserDetail() {
        addDisposable(repository.getUserDetail(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.user::setValue, this.error::setValue));
    }

    public void loadMyFeeds() {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        this.isLoading.setValue(true);

        addDisposable(repository
                .getFeedListByUserId(userId, PAGE_SIZE)
                .subscribe(myFeedList -> {
                    this.myFeedList.setValue(myFeedList);
                    this.isLoading.setValue(false);
                }, error -> {
                    this.error.setValue(error);
                    this.isLoading.setValue(false);
                }));
    }

    void toggleVoteEnded(@NonNull final MyFeed item,
                         final boolean newEnded) {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        this.isLoading.setValue(true);

        item.setEnded(newEnded);

        addDisposable(repository.toggleVoteEnded(userId, item.getId(), newEnded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            this.myFeedList.setValue(this.myFeedList.getValue());
                            this.isLoading.setValue(false);
                        },
                        error -> {
                            this.error.setValue(error);
                            this.isLoading.setValue(false);
                        }));
    }

    @NonNull
    public LiveData<UserDetail> getUser() {
        return user;
    }

    @NonNull
    public LiveData<PagedList<MyFeed>> getMyFeedListLiveData() {
        return myFeedListLiveData;
    }

    @NonNull
    LiveData<Throwable> getError() {
        return error;
    }

    @NonNull
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
