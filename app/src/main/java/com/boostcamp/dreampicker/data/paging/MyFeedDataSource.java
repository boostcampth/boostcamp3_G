package com.boostcamp.dreampicker.data.paging;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.remote.firestore.UserDataSource;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

class MyFeedDataSource extends PageKeyedDataSource<Date, MyFeed> {

    @NonNull
    private final UserDataSource remoteDataSource;
    @NonNull
    private final String userId;

    private boolean isLastPage = false;

    MyFeedDataSource(@NonNull UserDataSource remoteDataSource,
                            @NonNull String userId) {
        this.remoteDataSource = remoteDataSource;
        this.userId = userId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Date> params,
                            @NonNull LoadInitialCallback<Date, MyFeed> callback) {

        final Date date = new Date();
        List<MyFeed> itemList = remoteDataSource
                .getFeedListByUserId(userId, date, params.requestedLoadSize)
                .blockingGet();

        if (itemList.size() > 0) {
            callback.onResult(itemList, date, itemList.get(itemList.size() - 1).getDate());
        }

        if (itemList.size() < params.requestedLoadSize) {
            isLastPage = true;
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Date> params,
                           @NonNull LoadCallback<Date, MyFeed> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Date> params,
                          @NonNull LoadCallback<Date, MyFeed> callback) {
        if (!isLastPage) {
            List<MyFeed> itemList = remoteDataSource
                    .getFeedListByUserId(userId, params.key, params.requestedLoadSize)
                    .blockingGet();

            if (itemList.size() > 0) {
                callback.onResult(itemList,
                        itemList.get(itemList.size() - 1).getDate());
            }

            if (itemList.size() < params.requestedLoadSize) {
                isLastPage = true;
            }
        }
    }
}
