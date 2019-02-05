package com.boostcamp.dreampicker.presentation.user.profile;

import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
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
    private MutableLiveData<UserDetail> userDetail = new MutableLiveData<>();

    public ProfileViewModel(@NonNull UserRepository repository){
        this.repository = repository;
    }

    public LiveData<UserDetail> getUserDetail(){
        return userDetail;
    }

    public void loadUserDetail(String userId){
        addDisposable(repository.getProfileUserDetail(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDetail::setValue));
    }


    static class Factory implements ViewModelProvider.Factory {
        @NonNull
        private final UserRepository repository;

        Factory(@NonNull UserRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
                //noinspection unchecked
                return (T) new ProfileViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}