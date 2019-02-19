package com.boostcamp.dreampicker.data.paging;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.remote.firestore.UserDataSource;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class MyFeedDataSourceFactory extends DataSource.Factory<Date, MyFeed> {

    @NonNull
    private final UserDataSource remoteDataSource;
    @NonNull
    private final String userId;

    public MyFeedDataSourceFactory(@NonNull UserDataSource remoteDataSource,
                                   @NonNull String userId) {
        this.remoteDataSource = remoteDataSource;
        this.userId = userId;
    }

    private final MutableLiveData<MyFeedDataSource> sourceLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Date, MyFeed> create() {
        MyFeedDataSource source = new MyFeedDataSource(remoteDataSource, userId);
        sourceLiveData.postValue(source);
        return source;
    }
}
