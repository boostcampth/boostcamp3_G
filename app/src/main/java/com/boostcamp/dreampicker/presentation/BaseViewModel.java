package com.boostcamp.dreampicker.presentation;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;

    // Todo : View에서 처리한다면 LiveData, 데이터바인딩만으로 해결 가능하면 Observable
    protected ObservableBoolean isLoading = new ObservableBoolean();

    protected BaseViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if(!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
