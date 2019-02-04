package com.boostcamp.dreampicker.data.source.repository;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.UserFirebaseService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Single;

public class UserRepository implements UserDataSource {

    private static volatile UserRepository INSTANCE;

    @NonNull
    private UserFirebaseService firebaseService;

    private UserRepository(@Nullable final UserFirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    public static UserRepository getInstance(@Nullable final UserFirebaseService firebaseService) {
        if(INSTANCE == null){
            synchronized (UserRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new UserRepository(firebaseService);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<List<User>> searchAllUser(String searchKey) {

        return firebaseService.searchAllUser(searchKey);
    }

    @Override
    public Single<UserDetail> getUserInfo(String userId) {

        return firebaseService.getUserInfo(userId);
    }

    @Override
    public Single<UserDetail> getProfileUserDetail(String userId) {

        return firebaseService.getProfileUserDetail(userId);
    }

    @Override
    public Single<List<User>> addProfileFollowingList(String userId, int pageIndex, int pageUnit) {

        return firebaseService.addProfileFollowingList(userId, pageIndex, pageUnit);
    }

    @Override
    public Single<List<User>> addProfileFollowerList(String userId, int pageIndex, int pageUnit) {

        return firebaseService.addProfileFollowerList(userId, pageIndex, pageUnit);
    }

    @Override
    public Single<List<User>> addSearchUserList(String searchKey, int pageIndex, int pageUnit) {

        return firebaseService.addSearchUserList(searchKey, pageIndex, pageUnit);
    }
}
