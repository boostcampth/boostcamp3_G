package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserDetail;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class UserRemoteDataSource implements UserDataSource {

    private static UserRemoteDataSource userRemoteDataSource = null;

    private UserRemoteDataSource(){}

    public static UserRemoteDataSource getInstance() {
        if(userRemoteDataSource == null){
            synchronized (UserRemoteDataSource.class) {
                if(userRemoteDataSource == null) {
                    userRemoteDataSource = new UserRemoteDataSource();
                }
            }
        }
        return userRemoteDataSource;
    }

    @Override
    public Single<List<User>> searchAllUser(String searchKey) {

        // TODO. 임시 데이터
        List<User> userList = new ArrayList<>();
        User user = new User("" ,"yeseul",R.drawable.profile);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);

        return Single.just(userList);
    }

    @Override
    public Single<UserDetail> getUserInfo(String userId) {

        // TODO. 임시 데이터
        UserDetail user = new UserDetail();
        user.setName("yeseul");
        user.setProfileImageUrl("https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg");

        return Single.just(user);
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
        List<User> userList = new ArrayList<>();

        for(int i = 0 ; i < pageUnit ; i++) {
            User user = new User("userId",
                    "사용자 " + (i+1),
                    "https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg",
                    R.drawable.profile);

            userList.add(user);
        }

        return Single.just(userList);
    }
}
