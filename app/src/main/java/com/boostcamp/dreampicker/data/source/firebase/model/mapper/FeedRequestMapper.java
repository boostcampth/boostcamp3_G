package com.boostcamp.dreampicker.data.source.firebase.model.mapper;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.source.firebase.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.source.firebase.model.FeedItemRemoteData;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.boostcamp.dreampicker.utils.IdCreator;

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
