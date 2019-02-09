package com.boostcamp.dreampicker.data.paging.datasource;

import android.annotation.SuppressLint;

import com.boostcamp.dreampicker.data.model.LegacyFeed;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileFeedDataSource extends PageKeyedDataSource<Integer, LegacyFeed> {

    @NonNull
    private FeedRepository repository;
    @NonNull
    private String userId;

    private boolean isPageEnd = false;

    private ProfileFeedDataSource(@NonNull FeedRepository repository,
                                  @NonNull String userId) {
        this.repository = repository;
        this.userId = userId;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, LegacyFeed> callback) {

        PagedListResponse<LegacyFeed> response = repository.addProfileFeedList(userId, 1, params.requestedLoadSize)
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

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, LegacyFeed> callback) {
        // ignore
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, LegacyFeed> callback) {

        if(!isPageEnd){
            PagedListResponse<LegacyFeed> response = repository.addProfileFeedList(userId, params.key, params.requestedLoadSize)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(error -> {})
                    .blockingGet();

            callback.onResult(response.getItemList(),
                    params.key + response.getDisplay());

            if(response.getDisplay() < params.requestedLoadSize){
                isPageEnd = true;
            }
        }
    }


    public static class Factory extends DataSource.Factory<Integer, LegacyFeed> {

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
        private MutableLiveData<ProfileFeedDataSource> liveData =
                new MutableLiveData<>();

        @NonNull
        @Override
        public DataSource<Integer, LegacyFeed> create() {
            ProfileFeedDataSource source = new ProfileFeedDataSource(repository, userId);
            liveData.postValue(source);
            return source;
        }
    }
}
