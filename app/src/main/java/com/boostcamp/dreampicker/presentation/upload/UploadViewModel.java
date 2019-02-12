package com.boostcamp.dreampicker.presentation.upload;

import android.net.Uri;
import android.text.TextUtils;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.FirebaseManager;

import java.util.List;

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
    @NonNull
    private MutableLiveData<Boolean> validate = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository feedRepository;

    private static final int A = 1;

    UploadViewModel(@NonNull final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    void upload(@Nullable final List<String> tagListA,
                @Nullable final List<String> tagListB) {

        final String userId = FirebaseManager.getCurrentUserId();
        if (userId == null) {
            error.setValue(new IllegalArgumentException());
            return;
        }
        if (!TextUtils.isEmpty(getContent().getValue()) &&
                !TextUtils.isEmpty(getImagePathA().getValue()) &&
                !TextUtils.isEmpty(getImagePathB().getValue())) {
            addDisposable(feedRepository.uploadFeed(createFeedUploadRequest(userId, tagListA, tagListB))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> validate.setValue(true),
                            e -> validate.setValue(false)));
        } else {
            validate.setValue(false);
        }
    }


    @NonNull
    private FeedUploadRequest createFeedUploadRequest(@NonNull final String userId,
                                                      @Nullable final List<String> tagListA,
                                                      @Nullable final List<String> tagListB) {

        return new FeedUploadRequest(userId,
                getContent().getValue(),
                getImagePathA().getValue(),
                getImagePathB().getValue(),
                tagListA,
                tagListB);

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

    @NonNull
    MutableLiveData<Boolean> getValidate() {
        return validate;
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }

    public void setImagePath(@NonNull final Uri uri,
                             final int flag) {
        if (flag == A) {
            imagePathA.setValue(uri.toString());
        } else {
            imagePathB.setValue(uri.toString());
        }
    }
}
