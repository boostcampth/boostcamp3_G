package com.boostcamp.dreampicker.data.source.repository;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.data.source.local.test.UserMockDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class UserRepository implements UserDataSource {
    private static volatile UserRepository INSTANCE;

    private UserRepository(@NonNull final UserDataSource firebaseService) {
        this.mockDataSource = UserMockDataSource.getInstance();
        this.firebaseService = firebaseService;
    }

    public static UserRepository getInstance(@NonNull final UserDataSource firebaseService) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(firebaseService);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * MOCK DATA 테스트용
     * Firestore 연동 후 isTesting = false
     */
    private final boolean isTesting = true;
    private final UserDataSource mockDataSource;

    @NonNull
    private final UserDataSource firebaseService;

    @Override
    @NonNull
    public Single<UserDetail> getProfileUserDetail(@NonNull String userId) {
        if (isTesting) {
            return mockDataSource.getProfileUserDetail(userId);
        }

        return firebaseService.getProfileUserDetail(userId);
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowingList(@NonNull String userId,
                                                                   int start,
                                                                   int display) {
        if (isTesting) {
            return mockDataSource.addProfileFollowingList(userId, start, display);
        }

        return firebaseService.addProfileFollowingList(userId, start, display);
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowerList(@NonNull String userId,
                                                                  int start,
                                                                  int display) {
        if (isTesting) {
            return mockDataSource.addProfileFollowerList(userId, start, display);
        }

        return firebaseService.addProfileFollowerList(userId, start, display);
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addSearchUserList(@NonNull String searchKey,
                                                             int start,
                                                             int display) {
        if (isTesting) {
            return mockDataSource.addSearchUserList(searchKey, start, display);
        }

        return firebaseService.addSearchUserList(searchKey, start, display);
    }
}
