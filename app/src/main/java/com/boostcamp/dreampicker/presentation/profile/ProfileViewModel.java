package com.boostcamp.dreampicker.presentation.profile;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileViewModel extends BaseViewModel {
    private final int PAGE_SIZE = 10;

    @NonNull
    private final UserRepository repository;
    @NonNull
    private final String userId;

    @NonNull
    private MutableLiveData<UserDetail> user = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<List<MyFeed>> myFeedList = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();

    private Date startAfter;

    ProfileViewModel(@NonNull UserRepository repository,
                     @NonNull String userId) {
        this.repository = repository;
        this.userId = userId;
        this.isLoading.setValue(false);
        this.isLastPage.setValue(false);
        loadUserDetail();
        loadMyFeeds();
    }

    void loadUserDetail() {
        addDisposable(repository.getUserDetail(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.user::setValue, this.error::setValue));
    }

    void loadMyFeeds() {
        if (Boolean.TRUE.equals(isLoading.getValue()) ||
                Boolean.TRUE.equals(isLastPage.getValue())) {
            return;
        }
        this.isLoading.setValue(true);

        final List<MyFeed> newList = this.myFeedList.getValue() == null
                ? new ArrayList<>() : this.myFeedList.getValue();

        addDisposable(repository.getFeedListByUserId(userId,
                startAfter == null ? new Date() : startAfter,
                PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.size() > 0) {
                        newList.addAll(result);
                        this.myFeedList.setValue(newList);
                        this.startAfter = result.get(result.size() - 1).getDate();
                    }
                    if (result.size() < PAGE_SIZE) { // 마지막 페이지
                        isLastPage.setValue(true);
                    }
                    isLoading.setValue(false);
                }, error -> {
                    this.error.setValue(error);
                    isLoading.setValue(false);
                }));
    }

    void refreshMyFeeds(){
        myFeedList.setValue(new ArrayList<>());
        isLastPage.setValue(false);
        startAfter = null;
        loadMyFeeds();
    }

    @NonNull
    public LiveData<UserDetail> getUser() {
        return user;
    }

    @NonNull
    public LiveData<List<MyFeed>> getMyFeedList() {
        return myFeedList;
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
