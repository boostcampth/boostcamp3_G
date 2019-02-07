package com.boostcamp.dreampicker.presentation.upload;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.Image;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.Constant;
import com.boostcamp.dreampicker.utils.IdCreator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UploadViewModel extends BaseViewModel {
    private MutableLiveData<List<Image>> imageList = new MutableLiveData<>();
    private ObservableField<String> content = new ObservableField<>();

    private MutableLiveData<Boolean> validate = new MutableLiveData<>();

    private final FeedDataSource feedDataSource;
    private final UserDataSource userDataSource;

    UploadViewModel(FeedDataSource feedDataSource, UserDataSource userDataSource) {
        this.feedDataSource = feedDataSource;
        this.userDataSource = userDataSource;

        imageList.setValue(Arrays.asList(new Image(), new Image()));
    }
    void setImage(@NonNull Uri uri, @Constant.VoteFlag int flag) {
        final List<Image> imageList = this.imageList.getValue();
        if (imageList != null) {
            imageList.set(flag - 1, new Image(IdCreator.createImageId(), uri, new ArrayList<>()));
            this.imageList.postValue(imageList);
        }
    }

    void setTag(@NonNull String tag, @Constant.VoteFlag int flag) {
        final List<Image> imageList = this.imageList.getValue();
        if(imageList != null) {
            final Image image = imageList.get(flag-1);
            image.getTagList().add(tag);
            imageList.set(flag-1, image);
        }
    }

    void deleteTag(@NonNull String tag, @Constant.VoteFlag int flag) {
        final List<Image> imageList = this.imageList.getValue();
        if(imageList != null) {
            final Image image = imageList.get(flag-1);
            image.getTagList().remove(tag);
        }
    }

    // Todo : Firebase에 업로드 연동 필요
    // Todo : 유저 정보 가져오는법 파악 필요
    void upload() {
        final List<Image> imageList = this.imageList.getValue();

        if(imageList != null &&
                !TextUtils.isEmpty(imageList.get(0).getId()) &&
                !TextUtils.isEmpty(imageList.get(1).getId()) &&
                !TextUtils.isEmpty(content.get())) {

            addDisposable(userDataSource.getMyProfile()
                    .flatMapCompletable(user -> {
                        final Feed feed = createFeed(user, imageList);
                        return feedDataSource.upLoadFeed(feed);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> validate.setValue(true),
                            e -> validate.setValue(false)));
        } else {
            validate.setValue(false);
        }
    }

    @NonNull
    @SuppressLint("SimpleDateFormat")
    private Feed createFeed(@NonNull User user, @NonNull final List<Image> imageList) {
        final Feed feed = new Feed();
        feed.setId(IdCreator.createFeedId());
        feed.setUser(user);
        feed.setContent(content.get());
        feed.setImageList(imageList);
        feed.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        return feed;
    }

    public LiveData<List<Image>> getImageList() {
        return imageList;
    }

    public ObservableField<String> getContent() {
        return content;
    }

    MutableLiveData<Boolean> getValidate() {
        return validate;
    }
}
