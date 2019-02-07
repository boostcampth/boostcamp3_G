package com.boostcamp.dreampicker.presentation.profile;

import android.annotation.SuppressLint;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ProfileFeedPagedDataSource extends PageKeyedDataSource<Integer, Feed> {

    @NonNull
    private CompositeDisposable disposable;
    @NonNull
    private FeedRepository repository;
    @NonNull
    private String userId;

    private ProfileFeedPagedDataSource(@NonNull FeedRepository repository,
                                       @NonNull CompositeDisposable disposable,
                                       @NonNull String userId) {
        this.repository = repository;
        this.disposable = disposable;
        this.userId = userId;
    }

    /**
     * 첫 페이지 로딩요청
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Feed> callback) {

        disposable.add(repository.addProfileFeedList(userId, 1, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                        callback.onResult(response.getFeedList(),
                                response.getDisplay(),
                                response.getStart() + response.getDisplay()))
        );
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

        disposable.add(repository.addProfileFeedList(userId, params.key, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                        callback.onResult(response.getFeedList(),
                                params.key + response.getDisplay()))
        );
    }


    public static class Factory extends DataSource.Factory<Integer, Feed> {

        @NonNull
        private CompositeDisposable disposable;
        @NonNull
        private final FeedRepository repository;
        @NonNull
        private final String userId;

        public Factory(@NonNull FeedRepository repository,
                       @NonNull CompositeDisposable disposable,
                       @NonNull String userId) {
            this.repository = repository;
            this.disposable = disposable;
            this.userId = userId;
        }

        // TODO. liveData.postValue() 왜 필요한지 알아보기
        private MutableLiveData<ProfileFeedPagedDataSource> liveData =
                new MutableLiveData<>();

        @NonNull
        @Override
        public DataSource<Integer, Feed> create() {
            ProfileFeedPagedDataSource source = new ProfileFeedPagedDataSource(repository, disposable, userId);
            liveData.postValue(source);
            return source;
        }
    }
}
