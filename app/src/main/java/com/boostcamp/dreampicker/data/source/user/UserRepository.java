package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserInfo;

import java.util.List;

import io.reactivex.Single;

public class UserRepository implements UserDataSource {

    private static UserRepository userRepository = null;

    private UserRemoteDataSource remoteDataSource;

    private UserRepository() {
        remoteDataSource = UserRemoteDataSource.getInstance();
    }

    public static UserRepository getInstance() {
        if(userRepository == null){
            synchronized (UserRepository.class) {
                if(userRepository == null) {
                    userRepository = new UserRepository();
                }
            }
        }
        return userRepository;
    }

    @Override
    public Single<List<User>> getUserList(String searchKey) {

        return remoteDataSource.getUserList(searchKey);
    }

    @Override
    public Single<UserInfo> getUserDetail(String userId) {

        return remoteDataSource.getUserDetail(userId);
    }
}
