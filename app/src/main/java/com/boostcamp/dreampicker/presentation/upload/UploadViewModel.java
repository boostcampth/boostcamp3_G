package com.boostcamp.dreampicker.presentation.upload;

import android.net.Uri;
import android.text.TextUtils;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.extension.common.StringExt;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UploadViewModel extends BaseViewModel {

    @NonNull
    private final MutableLiveData<String> content = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<String> imagePathA = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<String> imagePathB = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> validate = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final FeedRepository feedRepository;

    private static final int A = 1;

    @Inject
    UploadViewModel(@NonNull final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
        isLoading.setValue(false);
    }

    void upload(@Nullable final String[] tagStrA,
                @Nullable final String[] tagStrB) {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        final List<String> tagListA = StringExt.toNoEndLineList(tagStrA);
        final List<String> tagListB = StringExt.toNoEndLineList(tagStrB);

        if (!TextUtils.isEmpty(getContent().getValue()) &&
                !TextUtils.isEmpty(getImagePathA().getValue()) &&
                !TextUtils.isEmpty(getImagePathB().getValue())) {
            isLoading.setValue(true);
            addDisposable(feedRepository.uploadFeed(createFeedUploadRequest(tagListA, tagListB))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        isLoading.setValue(false);
                        validate.setValue(true);
                    }, e -> {
                        isLoading.setValue(false);
                        error.setValue(e);
                    }));
        } else {
            validate.setValue(false);
        }
    }

    @NonNull
    private FeedUploadRequest createFeedUploadRequest(@Nullable final List<String> tagListA,
                                                      @Nullable final List<String> tagListB) {

        return new FeedUploadRequest(
                content.getValue(),
                imagePathA.getValue(),
                imagePathB.getValue(),
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
    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @NonNull
    LiveData<Boolean> getValidate() {
        return validate;
    }

    @NonNull
    LiveData<Throwable> getError() {
        return error;
    }

    void setImagePath(@NonNull final Uri uri, final int flag) {
        if (flag == A) {
            imagePathA.setValue(uri.toString());
        } else {
            imagePathB.setValue(uri.toString());
        }
    }
}
