package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.model.Feed;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;


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
    public Single<List<Feed>> searchFeed(String searchKey) {
        return null;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {
        final List<Feed> feeds = new ArrayList<>();
        return Single.create(emitter -> db.collection(COLLECTION_FEED).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            feeds.add(document.toObject(Feed.class));
                            emitter.onSuccess(feeds);
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

}