package com.boostcamp.dreampicker.view.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityUploadBinding;
import com.boostcamp.dreampicker.view.BaseActivity;

import androidx.annotation.Nullable;

public class UploadActivity extends BaseActivity<ActivityUploadBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

    }

    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, UploadActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }
}
