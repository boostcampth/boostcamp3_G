package com.boostcamp.dreampicker.data.source.firebase.model.mapper;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.source.firebase.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.source.firebase.model.FeedRemoteVoteItem;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.boostcamp.dreampicker.utils.IdCreator;

import java.util.Date;
import java.util.HashMap;

public class FeedMapper {

    public static FeedRemoteData setFeed(final FeedUploadRequest feedUploadRequest) {

        return new FeedRemoteData(IdCreator.createFeedId(),
                new FeedRemoteVoteItem(IdCreator.createImageId(), feedUploadRequest.getImagePathA(), feedUploadRequest.getTagListA()),
                new FeedRemoteVoteItem(IdCreator.createImageId(), feedUploadRequest.getImagePathB(), feedUploadRequest.getTagListB()),
                FirebaseManager.getCurrentUser(),
                feedUploadRequest.getContent(),
                new HashMap<String, String>(),
                new Date(),
                false);
    }
}
