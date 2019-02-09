package com.boostcamp.dreampicker.data.source.repository;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;

import androidx.annotation.NonNull;
import io.reactivex.Completable;

public interface FeedUploadRequestRepository {

    // [업로드] 사용자가 작성한 피드를 서버에 업로드한다.
    @NonNull
    Completable uploadFeed(@NonNull FeedUploadRequest feed);
}
