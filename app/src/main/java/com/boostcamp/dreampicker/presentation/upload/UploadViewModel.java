package com.boostcamp.dreampicker.presentation.upload;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;

import com.boostcamp.dreampicker.data.model.LegacyFeed;
import com.boostcamp.dreampicker.data.model.Image;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.Constant;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.boostcamp.dreampicker.utils.IdCreator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UploadViewModel extends BaseViewModel {
    private MutableLiveData<Map<String, Image>> imageMap = new MutableLiveData<>();
    private ObservableField<String> content = new ObservableField<>();

    private MutableLiveData<Boolean> validate = new MutableLiveData<>();

    private final FeedRepository feedRepository;

    UploadViewModel(@NonNull FeedRepository feedRepository) {
        this.feedRepository = feedRepository;

        final Image image = new Image();
        final Map<String, Image> map = new HashMap<>();
        map.put(Constant.IMAGE_LEFT, image);
        map.put(Constant.IMAGE_RIGHT, image);
        imageMap.setValue(map);
    }
    void setImage(@NonNull Uri uri, @Constant.ImageFlag String flag) {
        final Map<String, Image> imageMap = this.imageMap.getValue();
        if (imageMap != null) {
            final Image image = new Image(IdCreator.createImageId(), uri, new ArrayList<>());
            imageMap.put(flag, image);
            this.imageMap.postValue(imageMap);
        }
    }

    void setTag(@NonNull String tag, @Constant.ImageFlag String flag) {
        final Map<String, Image> imageMap = this.imageMap.getValue();
        if(imageMap != null) {
            final Image image = imageMap.get(flag);
            if(image != null) {
                image.getTagList().add(tag);
                imageMap.put(flag, image);
            }
        }
    }

    void deleteTag(@NonNull String tag, @Constant.ImageFlag String flag) {
        final Map<String, Image> imageMap = this.imageMap.getValue();
        if(imageMap != null) {
            final Image image = imageMap.get(flag);
            if(image != null) {
                image.getTagList().remove(tag);
            }
        }
    }

    // Todo : Firebase에 업로드 연동 필요
    // Todo : 유저 정보 가져오는법 파악 필요
    void upload() {
        final Map<String, Image> imageMap = this.imageMap.getValue();

        if(imageMap != null) {
            final Image leftImage = imageMap.get(Constant.IMAGE_LEFT);
            final Image rightImage = imageMap.get(Constant.IMAGE_RIGHT);

            if(leftImage != null && rightImage != null &&
                    !TextUtils.isEmpty(leftImage.getId()) &&
                    !TextUtils.isEmpty(rightImage.getId()) &&
                    !TextUtils.isEmpty(content.get())) {

                addDisposable(feedRepository.upLoadFeed(createFeed(imageMap))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> validate.setValue(true),
                                e -> validate.setValue(false)));
            } else {
                validate.setValue(false);
            }
        } else {
            validate.setValue(false);
        }
    }

    @NonNull
    @SuppressLint("SimpleDateFormat")
    private LegacyFeed createFeed(@NonNull final Map<String, Image> imageMap) {
        final LegacyFeed feed = new LegacyFeed();
        feed.setId(IdCreator.createFeedId());
        feed.setUser(FirebaseManager.getCurrentUser());
        feed.setContent(content.get());
        feed.setImageMap(imageMap);
        feed.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        return feed;
    }

    public LiveData<Map<String, Image>> getImageMap() {
        return imageMap;
    }

    public ObservableField<String> getContent() {
        return content;
    }

    MutableLiveData<Boolean> getValidate() {
        return validate;
    }
}
