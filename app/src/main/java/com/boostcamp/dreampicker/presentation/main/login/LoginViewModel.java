package com.boostcamp.dreampicker.presentation.main.login;

import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginViewModel extends BaseViewModel {

    @NonNull
    private final UserRepository repository;

    @NonNull
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();

    LoginViewModel(@NonNull final UserRepository repository) {
        this.repository = repository;
        this.isLoggedIn.setValue(false);
    }

    public void processLogin(@NonNull String idToken) {
        isLoading.setValue(true);

        addDisposable(repository.signIn(idToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    isLoggedIn.setValue(true);
                    isLoading.setValue(false);
                }, error -> {
                    this.error.setValue(error);
                    isLoading.setValue(false);
                }));
    }

    @NonNull
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }

    @NonNull
    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }
}
