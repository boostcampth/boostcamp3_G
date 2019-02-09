package com.boostcamp.dreampicker.data.paging.datasource;

import android.annotation.SuppressLint;

import com.boostcamp.dreampicker.data.model.FeedPrevious;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchFeedDataSource extends PageKeyedDataSource<Integer, FeedPrevious> {

    @NonNull
    private FeedRepository repository;
    @NonNull
    private String searchKey;

    private boolean isPageEnd = false;

    private SearchFeedDataSource(@NonNull FeedRepository repository,
                                 @NonNull String searchKey) {
        this.repository = repository;
        this.searchKey = searchKey;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, FeedPrevious> callback) {

        PagedListResponse<FeedPrevious> response =
                repository.addSearchFeedList(searchKey, 1, params.requestedLoadSize)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> {
                        })
                        .blockingGet();

        callback.onResult(response.getItemList(),
                response.getDisplay(),
                response.getStart() + response.getDisplay());

        if (response.getDisplay() < params.requestedLoadSize) {
            isPageEnd = true;
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, FeedPrevious> callback) {
        // ignore
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, FeedPrevious> callback) {

        if (!isPageEnd) {
            PagedListResponse<FeedPrevious> response =
                    repository.addProfileFeedList(searchKey, params.key, params.requestedLoadSize)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError(error -> {
                            })
                            .blockingGet();

            callback.onResult(response.getItemList(),
                    params.key + response.getDisplay());

            if (response.getDisplay() < params.requestedLoadSize) {
                isPageEnd = true;
            }
        }
    }


    public static class Factory extends DataSource.Factory<Integer, FeedPrevious> {

        @NonNull
        private final FeedRepository repository;
        @NonNull
        private final String searchKey;

        public Factory(@NonNull FeedRepository repository,
                       @NonNull String searchKey) {
            this.repository = repository;
            this.searchKey = searchKey;
        }

        public MutableLiveData<SearchFeedDataSource> sourceLiveData =
                new MutableLiveData<>();

        @NonNull
        @Override
        public DataSource<Integer, FeedPrevious> create() {
            SearchFeedDataSource source = new SearchFeedDataSource(repository, searchKey);
            sourceLiveData.postValue(source);
            return source;
        }
    }
}
