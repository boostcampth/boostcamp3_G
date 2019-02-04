package com.boostcamp.dreampicker.data.source.repository;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.data.source.local.test.UserMockDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.UserFirebaseService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Single;

public class UserRepository implements UserDataSource {
    private static volatile UserRepository INSTANCE;

    private UserRepository(@NonNull final UserDataSource firebaseService) {
        this.mockDataSource = UserMockDataSource.getInstance();
        this.firebaseService = firebaseService;
    }

    public static UserRepository getInstance(@NonNull final UserDataSource firebaseService) {
        if(INSTANCE == null){
            synchronized (UserRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new UserRepository(firebaseService);
                }
            }
        }
        return INSTANCE;
    }

    /** MOCK DATA 테스트용
     *  Firestore 연동 후 isTesting = false */
    private final boolean isTesting = true;
    private UserDataSource mockDataSource;

    @NonNull
    private UserDataSource firebaseService;

    @Override
    public Single<List<User>> searchAllUser(String searchKey) {
        if(isTesting){
            return mockDataSource.searchAllUser(searchKey);
        }

        return firebaseService.searchAllUser(searchKey);
    }

    @Override
    public Single<UserDetail> getUserInfo(String userId) {
        if(isTesting){
            return mockDataSource.getUserInfo(userId);
        }

        return firebaseService.getUserInfo(userId);
    }

    @Override
    public Single<UserDetail> getProfileUserDetail(String userId) {
        if(isTesting){
            return mockDataSource.getProfileUserDetail(userId);
        }

        return firebaseService.getProfileUserDetail(userId);
    }

    @Override
    public Single<List<User>> addProfileFollowingList(String userId, int pageIndex, int pageUnit) {
        if(isTesting){
            return mockDataSource.addProfileFollowingList(userId, pageIndex, pageUnit);
        }

        return firebaseService.addProfileFollowingList(userId, pageIndex, pageUnit);
    }

    @Override
    public Single<List<User>> addProfileFollowerList(String userId, int pageIndex, int pageUnit) {
        if(isTesting){
            return mockDataSource.addProfileFollowerList(userId, pageIndex, pageUnit);
        }

        return firebaseService.addProfileFollowerList(userId, pageIndex, pageUnit);
    }

    @Override
    public Single<List<User>> addSearchUserList(String searchKey, int pageIndex, int pageUnit) {
        if(isTesting){
            return mockDataSource.addSearchUserList(searchKey, pageIndex, pageUnit);
        }

        return firebaseService.addSearchUserList(searchKey, pageIndex, pageUnit);
    }
}
