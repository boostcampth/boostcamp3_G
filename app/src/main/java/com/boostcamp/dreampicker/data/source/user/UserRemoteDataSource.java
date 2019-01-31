package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class UserRemoteDataSource implements UserDataSource {

    private static final String COLLECTION_USERINFO = "userInfo";
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
    public Single<UserInfo> getUserInfo(String userId) {

        // TODO. 유저아이디로 쿼리 수정 필요

        final UserInfo[] user = {new UserInfo()};
        return Single.create(emitter -> {
            FirebaseFirestore.getInstance().collection(COLLECTION_USERINFO)
                    .whereEqualTo("name", "yeseul")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user[0] = document.toObject(UserInfo.class);
                                emitter.onSuccess(user[0]);

                            }

                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }
}
