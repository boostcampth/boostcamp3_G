package com.boostcamp.dreampicker.presentation.search.feed;

import android.annotation.SuppressLint;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchFeedPagedDataSource extends PageKeyedDataSource<Integer, Feed> {

    @NonNull
    private FeedRepository repository;
    @NonNull
    private String userId;

    private SearchFeedPagedDataSource(@NonNull FeedRepository repository,
                                      @NonNull String userId) {
        this.repository = repository;
        this.userId = userId;
    }

    /**
     * 첫 페이지 로딩요청
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Feed> callback) {

        PagedListResponse<Feed> response = repository.addProfileFeedList(userId, 1, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {})
                .blockingGet();

        callback.onResult(response.getItemList(),
                response.getDisplay(),
                response.getStart() + response.getDisplay());
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Feed> callback) {
        // ignore
    }

    /**
     * 첫 번째 페이지 제외 그 다음 페이지부터 로딩요청
     * TODO. 마지막 페이지 limit 처리 필요함
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Feed> callback) {

        PagedListResponse<Feed> response = repository.addProfileFeedList(userId, params.key, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {})
                .blockingGet();

        callback.onResult(response.getItemList(),
                params.key + response.getDisplay());
    }


    public static class Factory extends DataSource.Factory<Integer, Feed> {

        @NonNull
        private final FeedRepository repository;
        @NonNull
        private final String userId;

        public Factory(@NonNull FeedRepository repository,
                       @NonNull String userId) {
            this.repository = repository;
            this.userId = userId;
        }

        // TODO. liveData.postValue() 왜 필요한지 알아보기
        private MutableLiveData<SearchFeedPagedDataSource> liveData =
                new MutableLiveData<>();

        @NonNull
        @Override
        public DataSource<Integer, Feed> create() {
            SearchFeedPagedDataSource source = new SearchFeedPagedDataSource(repository, userId);
            liveData.postValue(source);
            return source;
        }
    }
}