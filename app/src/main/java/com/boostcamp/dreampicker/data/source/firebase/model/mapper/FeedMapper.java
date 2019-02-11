package com.boostcamp.dreampicker.data.source.firebase.model.mapper;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.source.firebase.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.source.firebase.model.FeedRemoteVoteItem;
import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.data.common.IdCreator;

import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class FeedMapper {

    public static FeedRemoteData toFeed(@NonNull final FeedUploadRequest feedUploadRequest,
                                        @NonNull final String imageUrlA,
                                        @NonNull final String imageUrlB) {

        return new FeedRemoteData(
                new FeedRemoteVoteItem(IdCreator.createImageId(), imageUrlA, feedUploadRequest.getTagListA()),
                new FeedRemoteVoteItem(IdCreator.createImageId(), imageUrlB, feedUploadRequest.getTagListB()),
                FirebaseManager.getCurrentUser(),
                feedUploadRequest.getContent(),
                new HashMap<String, String>(),
                new Date(),
                false);
    }
}
