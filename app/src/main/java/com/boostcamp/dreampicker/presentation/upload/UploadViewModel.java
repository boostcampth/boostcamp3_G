package com.boostcamp.dreampicker.presentation.upload;

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
    @Nullable
    private MutableLiveData<List<String>> tagListA = new MutableLiveData<>();
    @Nullable
    private MutableLiveData<List<String>> tagListB = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Boolean> validate = new MutableLiveData<>();

    @NonNull
    private final FeedRepository feedRepository;

    UploadViewModel(@NonNull final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    void upload() {
        if (!TextUtils.isEmpty(getContent().getValue()) &&
                !TextUtils.isEmpty(getImagePathA().getValue()) &&
                !TextUtils.isEmpty(getImagePathB().getValue())) {
            addDisposable(feedRepository.uploadFeed(createFeedUploadRequest())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> validate.setValue(true),
                            e -> validate.setValue(false)));
        }else{
            validate.setValue(false);
        }
    }

    @NonNull
    private FeedUploadRequest createFeedUploadRequest() {

        return new FeedUploadRequest(FirebaseManager.getCurrentUserId(),
                getContent().getValue(),
                getImagePathA().getValue(),
                getImagePathB().getValue(),
                getTagListA().getValue(),
                getTagListB().getValue());

    }

    @NonNull
    public LiveData<String> getContent() {
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
}
