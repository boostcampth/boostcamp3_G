package com.boostcamp.dreampicker.presentation.upload;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.Image;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UploadViewModel extends BaseViewModel {
    private static final String TYPE_FEED = "feed-";
    private static final String TYPE_IMAGE = "image-";
    private static final String TYPE_USER = "user-";

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private MutableLiveData<List<Image>> imageList = new MutableLiveData<>();
    private ObservableField<String> content = new ObservableField<>();

    private MutableLiveData<Boolean> validate = new MutableLiveData<>();

    UploadViewModel() {
        imageList.setValue(Arrays.asList(new Image(), new Image()));
    }

    void setImage(@NonNull Uri uri, @Constant.VoteFlag int flag) {
        final List<Image> imageList = this.imageList.getValue();
        if (imageList != null) {
            imageList.set(flag - 1, new Image(createId(TYPE_IMAGE), uri, new ArrayList<>()));
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
    // Todo : 유저를 이곳에서 생성할지 중간 매개가 있을지 고민 필요
    // Todo : 유저를 중간에서 생성한다면 피드도 그곳에서 같이 생성됨 !!
    // Todo : 넘겨줘야 한다면 FeedRequestData 안에 이미지, 피드정보 담아서 보낼 예정
    void upload() {
        final List<Image> imageList = this.imageList.getValue();

        if(imageList != null &&
                !TextUtils.isEmpty(imageList.get(0).getId()) &&
                !TextUtils.isEmpty(imageList.get(1).getId()) &&
                !TextUtils.isEmpty(content.get())) {

            final Feed feed = new Feed();
            feed.setId(createId(TYPE_FEED));
            feed.setUser(new User(TYPE_USER, "윤OO", R.drawable.profile3));
            feed.setContent(content.get());
            feed.setImageList(imageList);
            feed.setDate(dateFormat.format(new Date()));
            validate.setValue(true);
        } else {
            validate.setValue(false);
        }
    }

    private String createId(String type) {
        return type + UUID.randomUUID().toString();
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
