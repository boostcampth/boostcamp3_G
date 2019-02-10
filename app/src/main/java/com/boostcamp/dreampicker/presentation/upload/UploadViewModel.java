package com.boostcamp.dreampicker.presentation.upload;

import android.text.TextUtils;

import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.FirebaseManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UploadViewModel extends BaseViewModel {

    private ObservableField<String> content = new ObservableField<>();
    private ObservableField<String> imagePathA = new ObservableField<>();
    private ObservableField<String> imagePathB = new ObservableField<>();
    private ObservableField<List<String>> tagListA = new ObservableField<>();
    private ObservableField<List<String>> tagListB = new ObservableField<>();

    private MutableLiveData<Boolean> validate = new MutableLiveData<>();

    private final FeedRepository feedRepository;

    UploadViewModel(@NonNull FeedRepository feedRepository) {
        this.feedRepository = feedRepository;

    }

    void upload() {

        if (!TextUtils.isEmpty(imagePathA.get()) &&
                !TextUtils.isEmpty(imagePathB.get()) &&
                !TextUtils.isEmpty(content.get())) {

            addDisposable(feedRepository.uploadFeed(createFeed())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> validate.setValue(true),
                            e -> validate.setValue(false)));
        } else {
            validate.setValue(false);
        }
    }

    @NonNull
    private FeedUploadRequest createFeed() {

        return new FeedUploadRequest(FirebaseManager.getCurrentUserId(),
                content.get(),
                imagePathA.get(),
                imagePathB.get(),
                tagListA.get(),
                tagListB.get());
    }

    public ObservableField<String> getContent() {
        return content;
    }

    public ObservableField<String> getImagePathA() {
        return imagePathA;
    }

    public ObservableField<String> getImagePathB() {
        return imagePathB;
    }

    public ObservableField<List<String>> getTagListA() {
        return tagListA;
    }

    public ObservableField<List<String>> getTagListB() {
        return tagListB;
    }

    MutableLiveData<Boolean> getValidate() {
        return validate;
    }
}
