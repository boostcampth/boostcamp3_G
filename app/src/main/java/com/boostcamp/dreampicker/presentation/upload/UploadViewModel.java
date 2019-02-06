package com.boostcamp.dreampicker.presentation.upload;

import android.net.Uri;
import android.util.Log;

import com.boostcamp.dreampicker.data.model.Image;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UploadViewModel extends BaseViewModel {
    private MutableLiveData<List<Image>> imageList = new MutableLiveData<>();
    private ObservableField<String> content = new ObservableField<>();

    UploadViewModel() {
        imageList.setValue(Arrays.asList(new Image(), new Image()));
    }

    void setImage(@NonNull Uri uri, @Constant.VoteFlag int flag) {
        final List<Image> imageList = this.imageList.getValue();
        if (imageList != null) {
            // Todo Image 생성시 계정 정보 가져와야 함
            imageList.set(flag - 1, new Image(getImageId(), uri, new ArrayList<>()));
            this.imageList.postValue(imageList);

            Log.d("Melon", imageList.get(flag-1).getId());
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
    void upload() {

    }

    private String getImageId() {
        return "image-" + UUID.randomUUID().toString();
    }

    public LiveData<List<Image>> getImageList() {
        return imageList;
    }

    public ObservableField<String> getContent() {
        return content;
    }
}
