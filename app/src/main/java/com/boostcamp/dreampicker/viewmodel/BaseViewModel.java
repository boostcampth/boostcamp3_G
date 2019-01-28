package com.boostcamp.dreampicker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {
    CompositeDisposable compositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);

        compositeDisposable = new CompositeDisposable();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
