package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class UserRemoteDataSource implements UserDataSource {

    private static final String COLLECTION_USERDETAIL = "userDetail";
    private static final String COLLECTION_USER = "user";

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
        //유저아이디로 Info찾기
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .document(userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshot -> {
                    emitter.onSuccess(queryDocumentSnapshot.toObject(UserDetail.class));
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    public Single<List<User>> addProfileFollowingList(String userId, int pageIndex, int pageUnit) {
        final List<User> followingList = new ArrayList<>();
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .document(userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshot -> {

                    Query query = FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                            .orderBy("name")
                            .limit(pageUnit)
                            .startAt(queryDocumentSnapshot);
                    query.get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                Query next = FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                                        .orderBy("name")
                                        .limit(pageUnit)
                                        .startAt(queryDocumentSnapshot);
                            })
                            .addOnFailureListener(emitter::onError);
                    emitter.onSuccess(followingList);
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    public Single<List<User>> addProfileFollowerList(String userId, int pageIndex, int pageUnit) {
        final List<User> followerList = new ArrayList<>();
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .document(userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshot -> {

                    Query query = FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                            .orderBy("name")
                            .limit(pageUnit)
                            .startAt(queryDocumentSnapshot);
                    query.get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                Query next = FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                                    .orderBy("name")
                                    .limit(pageUnit)
                                    .startAt(queryDocumentSnapshot);
                                next.get()
                                        .addOnSuccessListener(queryDocumentSnapshots1 -> {

                                })
                                        .addOnFailureListener(emitter::onError);
                            })
                            .addOnFailureListener(emitter::onError);
                    emitter.onSuccess(followerList);
                })
                .addOnFailureListener(emitter::onError));
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
