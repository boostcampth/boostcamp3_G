package com.boostcamp.dreampicker.presentation.upload;

import android.net.Uri;
import android.text.TextUtils;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.FirebaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UploadViewModel extends BaseViewModel {

    @NonNull
    private MutableLiveData<String> content = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<String> imagePathA = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<String> imagePathB = new MutableLiveData<>();
    @Nullable
    private MutableLiveData<List<String>> tagListA = new MutableLiveData<>();
    @Nullable
    private MutableLiveData<List<String>> tagListB = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Boolean> validate = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository feedRepository;

    private static final int A = 1;

    UploadViewModel(@NonNull final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
        tagListA.setValue(new ArrayList<>());
        tagListB.setValue(new ArrayList<>());
    }

    void upload() {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId == null) {
            error.setValue(new IllegalArgumentException());
            return;
        }
        if (!TextUtils.isEmpty(getContent().getValue()) &&
                !TextUtils.isEmpty(getImagePathA().getValue()) &&
                !TextUtils.isEmpty(getImagePathB().getValue())) {
            addDisposable(feedRepository.uploadFeed(createFeedUploadRequest(userId))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> validate.setValue(true),
                            e -> validate.setValue(false)));
        } else {
            validate.setValue(false);
        }
    }


    @NonNull
    private FeedUploadRequest createFeedUploadRequest(String userId) {

        return new FeedUploadRequest(userId,
                getContent().getValue(),
                getImagePathA().getValue(),
                getImagePathB().getValue(),
                Objects.requireNonNull(getTagListA()).getValue(),
                Objects.requireNonNull(getTagListB()).getValue());

    }

    @NonNull
    public MutableLiveData<String> getContent() {
        return content;
    }

    @NonNull
    public LiveData<String> getImagePathA() {
        return imagePathA;
    }

    @NonNull
    public LiveData<String> getImagePathB() {
        return imagePathB;
    }

    @Nullable
    public LiveData<List<String>> getTagListA() {
        return tagListA;
    }

    @Nullable
    public LiveData<List<String>> getTagListB() {
        return tagListB;
    }

    @NonNull
    MutableLiveData<Boolean> getValidate() {
        return validate;
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }

    public void setTagA(@NonNull final String tag) {
        final List<String> tagList = this.tagListA.getValue();
        if (tagList != null) tagList.add(tag);
    }

    public void setTagB(@NonNull final String tag) {
        final List<String> tagList = this.tagListB.getValue();
        if (tagList != null) tagList.add(tag);
    }

    public void deleteTagA(@NonNull final String tag) {
        final List<String> tagList = this.tagListA.getValue();
        if (tagList != null) tagList.remove(tag);
    }

    public void deleteTagB(@NonNull final String tag) {
        final List<String> tagList = this.tagListB.getValue();
        if (tagList != null) tagList.remove(tag);
    }

    public void setImagePath(@NonNull final Uri uri,
                             @NonNull final int flag) {
        if (flag == A) {
            imagePathA.setValue(uri.toString());
        } else {
            imagePathB.setValue(uri.toString());
        }
    }
}
