package com.boostcamp.dreampicker.presentation.upload;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.Image;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.Constant;

import java.util.Arrays;
import java.util.List;

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
            imageList.set(flag - 1, new Image("0", uri, ""));
            this.imageList.postValue(imageList);
        }
    }

    // Todo : Firebase에 업로드 연동 필요
    // private void upload() { }

    public LiveData<List<Image>> getImageList() {
        return imageList;
    }

    public ObservableField<String> getContent() {
        return content;
    }
}
