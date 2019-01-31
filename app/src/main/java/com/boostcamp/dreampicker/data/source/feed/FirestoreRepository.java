package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.Image;
import com.boostcamp.dreampicker.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;


public class FirestoreRepository implements FeedDataSource {

    private static final String COLLECTION_FEED = "feed";

    private static FirestoreRepository firestoreRepository = null;
    private FirebaseFirestore db ;

    private FirestoreRepository(){db = FirebaseFirestore.getInstance();}

    public static FirestoreRepository getInstance() {
        if(firestoreRepository == null){
            synchronized (FirestoreRepository.class) {
                if(firestoreRepository == null) {
                    firestoreRepository = new FirestoreRepository();

                }
            }
        }
        return firestoreRepository;
    }


    @Override
    public Single<List<Feed>> getAllFeed() {
        final List<Feed> feeds = new ArrayList<>();

        Image image1 = new Image("image-0-up", R.drawable.image1, "카페");
        Image image2 = new Image("image-0-down", R.drawable.image2, "술집");
        List<Image> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        List<String> list = new ArrayList<>();
        list.add("asd");
        User user1 = new User("user-0", "박신혜","https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg" ,0,0,0);//R.drawable.profile
        Feed feed1 = new Feed(
                "feed-0",
                images,
                user1,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false,
                list,
                list
        );

        Image image3 = new Image("image-1-up", R.drawable.jajang, "짜장면");
        Image image4 = new Image("image-1-down", R.drawable.jambong, "짬뽕");
        List<Image> images2 = new ArrayList<>();
        images2.add(image3);
        images2.add(image4);
        User user2 = new User("user-1", "공유", "https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg",0,0,0);//R.drawable.profile2

        Feed feed2 = new Feed(
                "feed-0",
                images2,
                user2,
                "짬뽕, 짜장면",
                "2019.01.29",
                false,
                list,
                list
        );
        db.collection(COLLECTION_FEED).add(feed1);
        return Single.create(new SingleOnSubscribe<List<Feed>>() {
            @Override
            public void subscribe(SingleEmitter<List<Feed>> emitter) throws Exception {
                db.collection(COLLECTION_FEED).get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    feeds.add(document.toObject(Feed.class));
                                    emitter.onSuccess(feeds);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        });
            }
        });
    }

}
