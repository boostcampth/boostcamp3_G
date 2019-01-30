package com.boostcamp.dreampicker.data.source;

import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserInfo;

import java.util.List;

public interface FirestoreDataSource {

    // loading
    List<Feed> getAllFeed();
    UserInfo getUserInfo(String userKey);

    //search
    List<User> getUserList(String searchKey);
    UserInfo getUserDetail(String userId);

    // upload
    void upLoadFeed(List<Feed> feed);

    // result
    List<Feed> getNotificationInfo();


}
