package com.boostcamp.dreampicker.data.source.remote.firebase;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class UserFirebaseService implements UserDataSource {

    private static final String COLLECTION_USERDETAIL = "userDetail";
    private static final String COLLECTION_USER = "user";

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

        // TODO. 삭제
        final List<User> userList = new ArrayList<>();
//        User user = new User("" ,"yeseul",R.drawable.profile);
//        userList.add(user);
//        userList.add(user);
//        userList.add(user);
//        userList.add(user);

        //return Single.just(userList);
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("name",searchKey)
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    userList.add(document.toObject(User.class));
                                }
                                emitter.onSuccess(userList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        }));

    }

    @Override
    public Single<UserDetail> getUserInfo(String userId) {

        // TODO. 삭제
        final UserDetail user = new UserDetail();
//        user.setName("yeseul");
//        user.setProfileImageUrl("https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg");
//
//        return Single.just(user);
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("id","userId")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            emitter.onSuccess(document.toObject(UserDetail.class));
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    @Override
    public Single<UserDetail> getProfileUserDetail(String userId) {
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("id",userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            emitter.onSuccess(document.toObject(UserDetail.class));
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    @Override
    public Single<List<User>> addProfileFollowingList(String userId, int pageIndex, int pageUnit) {
        final List<User> followingList = new ArrayList<>();
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("id",userId)
                .orderBy("date")
                .startAt(pageIndex)
                .limit(pageUnit)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            followingList.add(document.toObject(User.class));
                        }
                        emitter.onSuccess(followingList);
                    }else{
                        emitter.onError(task.getException());
                    }
                }));
    }

    @Override
    public Single<List<User>> addProfileFollowerList(String userId, int pageIndex, int pageUnit) {
        final List<User> followerList = new ArrayList<>();
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("id",userId)
                .get()
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    followerList.add(document.toObject(User.class));
                }
                emitter.onSuccess(followerList);
            }else{
                emitter.onError(task.getException());
            }
        }));
    }


    @Override
    public Single<List<User>> addSearchUserList(String searchKey, int pageIndex, int pageUnit) {
        // TODO :테스트 필요
        List<User> userList = new ArrayList<>();

//        for(int i = 0 ; i < pageUnit ; i++) {
//            User user = new User("userId",
//                    "사용자 " + (i+1),
//                    "https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg",
//                    R.drawable.profile);
//
//            userList.add(user);
//        }
// return Single.just(userList);
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                .whereEqualTo("name",searchKey)
                .get()
                .addOnSuccessListener(queryDocumentSnapshot -> {
                    DocumentSnapshot lastVisible = queryDocumentSnapshot.getDocuments()
                            .get(queryDocumentSnapshot.size() -1);
                    Query next = FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                            .orderBy("name")
                            .limit(pageUnit)
                            .startAt(lastVisible);
                    next.get()
                           .addOnSuccessListener(task -> {
                               for (DocumentSnapshot document : Objects.requireNonNull(task.getDocuments())) {
                                   userList.add(document.toObject(User.class));
                               }
                               emitter.onSuccess(userList);

                           })
                           .addOnFailureListener(emitter::onError);
                    emitter.onSuccess(userList);
                })
                .addOnFailureListener(emitter::onError));
    }
}
