package com.boostcamp.dreampicker.presentation.search.user;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchUserPagedDataSource extends PageKeyedDataSource<Integer, User> {

    @NonNull
    private UserRepository repository;
    @NonNull
    private String searchKey;

    private boolean isPageEnd = false;

    private SearchUserPagedDataSource(@NonNull UserRepository repository,
                                      @NonNull String searchKey) {
        this.repository = repository;
        this.searchKey = searchKey;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, User> callback) {

        PagedListResponse<User> response = repository.addSearchUserList(searchKey, 1, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {})
                .blockingGet();

        callback.onResult(response.getItemList(),
                response.getDisplay(),
                response.getStart() + response.getDisplay());

        if(response.getDisplay() < params.requestedLoadSize){
            isPageEnd = true;
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, User> callback) {
        // ignore
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, User> callback) {

        if(!isPageEnd) {
            PagedListResponse<User> response = repository.addSearchUserList(searchKey, params.key, params.requestedLoadSize)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(error -> {
                    })
                    .blockingGet();

            callback.onResult(response.getItemList(),
                    params.key + response.getDisplay());

            if(response.getDisplay() < params.requestedLoadSize){
                isPageEnd = true;
            }
        }
    }


    static class Factory extends DataSource.Factory<Integer, User> {

        @NonNull
        private UserRepository repository;
        @NonNull
        private String searchKey;

        public Factory(@NonNull UserRepository repository,
                       @NonNull String searchKey) {
            this.repository = repository;
            this.searchKey = searchKey;
        }

        @NonNull
        private MutableLiveData<SearchUserPagedDataSource> liveData =
                new MutableLiveData<>();

        @NonNull
        @Override
        public DataSource<Integer, User> create() {
            SearchUserPagedDataSource source =
                    new SearchUserPagedDataSource(repository, searchKey);
            liveData.postValue(source);
            return source;
        }
    }
}
