package com.boostcamp.dreampicker.data.source.local.test;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class UserMockDataSource implements UserDataSource {
    private static volatile UserMockDataSource INSTANCE;

    private UserMockDataSource() {}

    public static UserMockDataSource getInstance(){
        if(INSTANCE == null){
            synchronized (UserMockDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new UserMockDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<List<User>> searchAllUser(String searchKey) {

        List<User> userList = new ArrayList<>();
        User user = new User("" ,"yeseul", R.drawable.profile);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);

        return Single.just(userList);
    }

    @Override
    public Single<UserDetail> getUserInfo(String userId) {
        return Single.just(new UserDetail(
                userId,
                userId,
                userId,
                userId,
                R.drawable.profile,
                101,
                207,
                314));
    }

    @Override
    public Single<UserDetail> getProfileUserDetail(String userId) {
        return Single.just(new UserDetail(
                userId,
                userId,
                userId,
                userId,
                R.drawable.profile,
                101,
                207,
                314));
    }

    @Override
    public Single<List<User>> addProfileFollowingList(String userId, int pageIndex, int pageUnit) {

        List<User> userList = new ArrayList<>();
        User user = new User("" ,"yeseul", R.drawable.profile);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);

        return Single.just(userList);
    }

    @Override
    public Single<List<User>> addProfileFollowerList(String userId, int pageIndex, int pageUnit) {

        List<User> userList = new ArrayList<>();
        User user = new User("" ,"yeseul", R.drawable.profile);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);

        return Single.just(userList);
    }

    @Override
    public Single<List<User>> addSearchUserList(String searchKey, int pageIndex, int pageUnit) {

        List<User> userList = new ArrayList<>();
        User user = new User("" ,"yeseul", R.drawable.profile);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);

        return Single.just(userList);
    }
}
