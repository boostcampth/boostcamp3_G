package com.boostcamp.dreampicker.data.remote.firestore.mapper;

import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.data.common.IdCreator;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.remote.firestore.model.FeedItemRemoteData;
import com.boostcamp.dreampicker.data.remote.firestore.model.FeedRemoteData;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class FeedRequestMapper {

    public static FeedRemoteData toFeed(@NonNull final FeedUploadRequest feedUploadRequest,
                                        @NonNull final String imageUrlA,
                                        @NonNull final String imageUrlB) {

        return new FeedRemoteData(
                new FeedItemRemoteData(IdCreator.createImageId(), imageUrlA, feedUploadRequest.getTagListA()),
                new FeedItemRemoteData(IdCreator.createImageId(), imageUrlB, feedUploadRequest.getTagListB()),
                FirebaseManager.getCurrentUser(),
                feedUploadRequest.getContent(),
                new HashMap<>(),
                null,
                false);
    }
}
