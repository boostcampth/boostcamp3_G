package com.boostcamp.dreampicker.data.source.local.test;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserMockDataSource implements UserDataSource {
    private static volatile UserMockDataSource INSTANCE;

    private UserMockDataSource() { }

    public static UserMockDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (UserMockDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserMockDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Single<UserDetail> getProfileUserDetail(@NonNull String userId) {
        return Single.just(new UserDetail(
                userId,
                "yeseul",
                "sdc01194@gmail.com",
                "https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg",
                101,
                207,
                314,
                false));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowingList(@NonNull String userId,
                                                                   int start,
                                                                   int display) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < display; i++) {
            User user = new User("", "yeseul", R.drawable.profile);
            userList.add(user);
        }
        return Single.just(new PagedListResponse<>(start, display, userList));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowerList(@NonNull String userId,
                                                                  int start,
                                                                  int display) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < display; i++) {
            User user = new User("", "yeseul", R.drawable.profile);
            userList.add(user);
        }
        return Single.just(new PagedListResponse<>(start, display, userList));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addSearchUserList(@NonNull String searchKey,
                                                             int start,
                                                             int display) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < display; i++) {
            User user = new User("", "yeseul", R.drawable.profile);
            userList.add(user);
        }
        return Single.just(new PagedListResponse<>(start, display, userList));
    }

    @Override
    @NonNull
    public Completable toggleUserFollow(@NonNull String userId,
                                        @NonNull String myUserId) {

        return Completable.create(emitter -> {});
    }

    @NonNull
    @Override
    public Single<User> getMyProfile() {
        User user = new User("yoon",
                "라이언",
                "http://monthly.chosun.com/up_fd/Mdaily/2017-09/bimg_thumb/2017042000056_0.jpg",
                R.drawable.profile);

        return Single.just(user).subscribeOn(Schedulers.io());
    }
}
