package com.boostcamp.dreampicker.presentation.profile;

import com.boostcamp.dreampicker.data.model.LegacyUserDetail;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {

    @NonNull
    private final UserRepository repository;
    @NonNull
    private final String userId;

    @NonNull
    private MutableLiveData<LegacyUserDetail> user = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private ProfileViewModel(@NonNull UserRepository repository,
                             @NonNull String userId) {
        this.repository = repository;
        this.userId = userId;
        loadUserDetail();
    }

    /**
     * 사용자 정보 로딩 */
    private void loadUserDetail() {
        if (isLoading.get()) {
            return;
        }

        isLoading.set(true);

        addDisposable(repository.getProfileUserDetail(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            this.user.setValue(user);
                            this.isLoading.set(false);
                        }, error -> {
                            this.error.setValue(error);
                            this.isLoading.set(false);
                        }
                ));
    }

    /**
     * 팔로우 등록 & 취소 */
    public void toggleFollow() {

        final LegacyUserDetail user = this.user.getValue();

        if (user != null) {
            addDisposable(repository.toggleUserFollow(user.getId(), "me")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::loadUserDetail, error::setValue));
        }
    }

    @NonNull
    public LiveData<LegacyUserDetail> getUser() {
        return user;
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }

    static class Factory implements ViewModelProvider.Factory {
        @NonNull
        private final UserRepository repository;
        @NonNull
        private String userId;

        Factory(@NonNull UserRepository repository,
                @NonNull String userId) {
            this.repository = repository;
            this.userId = userId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
                //noinspection unchecked
                return (T) new ProfileViewModel(repository, userId);
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}