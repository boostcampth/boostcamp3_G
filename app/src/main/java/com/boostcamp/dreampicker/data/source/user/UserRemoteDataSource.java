package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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
    public Single<List<Feed>> searchAllFeed(String searchKey, @Nullable Boolean isEnded) {
        return null;
    }

    @Override
    public Single<UserInfo> getUserInfo(String userId) {

        // TODO. 임시 데이터
        UserInfo user = new UserInfo();
        user.setName("yeseul");
        user.setProfileImageUrl("https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg");

        return Single.just(user);
    }
}
