package com.boostcamp.dreampicker.data.source.local.test;

import android.util.Log;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.UploadDataSource;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class UploadMockDataSource implements UploadDataSource {
    private static final String TAG = "Melon";
    @NonNull
    @Override
    public Completable upload(@NonNull Feed feed) {
        return Completable.create(emitter -> {
            Log.d(TAG, "---------------------------------------");
            Log.d(TAG, "Feed 출력 정보");
            Log.d(TAG, "Feed id : " + feed.getId());
            Log.d(TAG, "Feed content : " + feed.getContent());
            Log.d(TAG, "Feed date : " + feed.getDate());
            Log.d(TAG, "Uploader id : " + feed.getUser().getId());
            Log.d(TAG, "Uploader name : " + feed.getUser().getName());
            Log.d(TAG, "Uploader name : " + feed.getUser().getProfileImageUrl());
            Log.d(TAG, "Image[0] uri : " + feed.getImageList().get(0).getUri());
            Log.d(TAG, "Image[0] tagList " + feed.getImageList().get(0).getTagList());
            Log.d(TAG, "Image[1] uri : " + feed.getImageList().get(1).getUri());
            Log.d(TAG, "Image[1] tagList " + feed.getImageList().get(1).getTagList());
            Log.d(TAG, "Feed leftCount : " + feed.getLeftCount());
            Log.d(TAG, "Feed rightCount : " + feed.getRightCount());
            Log.d(TAG, "Feed totalCount : " + feed.getVoteCount());
            Log.d(TAG, "Feed voteFlag : " + feed.getVoteFlag());
            Log.d(TAG, "---------------------------------------");

            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }
}
