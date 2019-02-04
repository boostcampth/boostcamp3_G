package com.boostcamp.dreampicker.data.source.remote.firebase;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class UserFirebaseService implements UserDataSource {

    private static volatile UserFirebaseService INSTANCE;

    private UserFirebaseService(){}

    public static UserFirebaseService getInstance() {
        if(INSTANCE == null){
            synchronized (UserFirebaseService.class) {
                if(INSTANCE == null) {
                    INSTANCE = new UserFirebaseService();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<List<User>> searchAllUser(String searchKey) {

        return null;
    }

    @Override
    public Single<UserDetail> getUserInfo(String userId) {

        return null;
    }

    @Override
    public Single<UserDetail> getProfileUserDetail(String userId) {

        return null;
    }

    @Override
    public Single<List<User>> addProfileFollowingList(String userId, int pageIndex, int pageUnit) {

        return null;
    }

    @Override
    public Single<List<User>> addProfileFollowerList(String userId, int pageIndex, int pageUnit) {

        return null;
    }

    @Override
    public Single<List<User>> addSearchUserList(String searchKey, int pageIndex, int pageUnit) {

        return null;
    }
}
