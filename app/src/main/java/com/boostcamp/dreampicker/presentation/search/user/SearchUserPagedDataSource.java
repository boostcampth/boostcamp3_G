package com.boostcamp.dreampicker.presentation.search.user;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class SearchUserPagedDataSource extends PageKeyedDataSource<Integer, User> {

    @NonNull
    private UserRepository repository;
    @NonNull
    private CompositeDisposable disposable;
    @NonNull
    private String searchKey;

    private SearchUserPagedDataSource(@NonNull UserRepository repository,
                                      @NonNull CompositeDisposable disposable,
                                      @NonNull String searchKey) {
        this.repository = repository;
        this.disposable = disposable;
        this.searchKey = searchKey;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, User> callback) {

        disposable.add(repository.addSearchUserList(searchKey, 1, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> callback.onResult(response.getItemList(),
                        response.getDisplay(),
                        response.getStart() + response.getDisplay()),
                        error -> { }
                )
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, User> callback) {
        // ignore
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, User> callback) {

        disposable.add(repository.addSearchUserList(searchKey, params.key, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                                callback.onResult(response.getItemList(),
                                        params.key + response.getDisplay()),
                        error -> { }
                )
        );
    }


    static class Factory extends DataSource.Factory<Integer, User> {

        @NonNull
        private UserRepository repository;
        @NonNull
        private CompositeDisposable disposable;
        @NonNull
        private String searchKey;

        public Factory(@NonNull UserRepository repository,
                       @NonNull CompositeDisposable disposable,
                       @NonNull String searchKey) {
            this.repository = repository;
            this.disposable = disposable;
            this.searchKey = searchKey;
        }

        @NonNull
        private MutableLiveData<SearchUserPagedDataSource> liveData =
                new MutableLiveData<>();

        @NonNull
        @Override
        public DataSource<Integer, User> create() {
            SearchUserPagedDataSource source =
                    new SearchUserPagedDataSource(repository, disposable, searchKey);
            liveData.postValue(source);
            return source;
        }
    }
}
