package com.boostcamp.dreampicker.presentation.upload;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.FirebaseManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        if (getContent().getValue() != null &&
                getImagePathA().getValue() != null &&
                getImagePathB().getValue() != null) {
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
    public MutableLiveData<String> getContent() {
        return content;
    }

    @NonNull
    public MutableLiveData<String> getImagePathA() {
        return imagePathA;
    }

    @NonNull
    public MutableLiveData<String> getImagePathB() {
        return imagePathB;
    }

    @Nullable
    public MutableLiveData<List<String>> getTagListA() {
        return tagListA;
    }

    @Nullable
    public MutableLiveData<List<String>> getTagListB() {
        return tagListB;
    }

    @NonNull
    MutableLiveData<Boolean> getValidate() {
        return validate;
    }
}
