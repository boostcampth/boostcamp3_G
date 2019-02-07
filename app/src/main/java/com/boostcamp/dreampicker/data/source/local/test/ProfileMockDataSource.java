package com.boostcamp.dreampicker.data.source.local.test;


import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.MyProfileDataSource;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

// Test용 임식 클래스
public class ProfileMockDataSource implements MyProfileDataSource {
    @Override
    public Single<User> getMyProfile() {
        User user = new User("yoon",
                "라이언",
                "http://monthly.chosun.com/up_fd/Mdaily/2017-09/bimg_thumb/2017042000056_0.jpg",
                R.drawable.profile);

        return Single.just(user).subscribeOn(Schedulers.io());
    }
}
