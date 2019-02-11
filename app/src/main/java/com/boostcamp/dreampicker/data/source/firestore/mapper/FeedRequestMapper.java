package com.boostcamp.dreampicker.data.source.firestore.mapper;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedItemRemoteData;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.data.common.IdCreator;

import java.util.Date;
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
                new Date(),
                false);
    }
}
