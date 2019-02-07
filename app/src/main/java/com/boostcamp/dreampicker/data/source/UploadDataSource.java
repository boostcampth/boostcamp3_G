package com.boostcamp.dreampicker.data.source;

import com.boostcamp.dreampicker.data.model.Feed;

import androidx.annotation.NonNull;
import io.reactivex.Completable;

public interface UploadDataSource {
    Completable upload(@NonNull Feed feed);
}
