package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.paging.MyFeedDataSourceFactory;
import com.boostcamp.dreampicker.data.remote.firestore.UserDataSource;
import com.boostcamp.dreampicker.data.remote.firestore.model.UserDetailRemoteData;
import com.boostcamp.dreampicker.data.remote.google.AccountApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    @NonNull
    private final UserDataSource remoteDataSource;
    @NonNull
    private final AccountApi accountApi;

    @Inject
    UserRepositoryImpl(@NonNull final UserDataSource remoteDataSource,
                       @NonNull final AccountApi accountApi) {
        this.remoteDataSource = remoteDataSource;
        this.accountApi = accountApi;
    }

    @NonNull
    @Override
    public Single<UserDetail> getUserDetail(@NonNull final String userId) {
        return remoteDataSource.getUserDetail(userId);
    }

    @NonNull
    @Override
    public Observable<PagedList<MyFeed>> getFeedListByUserId(@NonNull final String userId,
                                                             final int pageSize) {
        MyFeedDataSourceFactory factory =
                new MyFeedDataSourceFactory(remoteDataSource, userId);

        final PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(pageSize)
                .setPageSize(pageSize)
                .setPrefetchDistance(2)
                .setEnablePlaceholders(false)
                .build();

        return new RxPagedListBuilder<>(factory, config)
                .buildObservable()
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Completable toggleVoteEnded(@NonNull final String userId,
                                       @NonNull final String feedId,
                                       final boolean isEnded) {
        return remoteDataSource.toggleVoteEnded(userId, feedId, isEnded);
    }

    @NonNull
    @Override
    public Completable signIn(@NonNull final String userIdToken) {
        return accountApi.signIn(userIdToken)
                .flatMapCompletable(user -> remoteDataSource.insertNewUser(
                        user.getUid(),
                        new UserDetailRemoteData(
                                user.getDisplayName(),
                                user.getPhotoUrl() == null
                                        ? null : user.getPhotoUrl().toString(),
                                0
                        )).subscribeOn(Schedulers.io()));
    }

    @NonNull
    @Override
    public Completable signOut() {
        return accountApi.signOut();
    }

}
