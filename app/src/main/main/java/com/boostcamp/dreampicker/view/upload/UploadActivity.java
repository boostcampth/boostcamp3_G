package com.boostcamp.dreampicker.view.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.view.BaseActivity;
import com.boostcamp.dreampicker.view.main.LogInActivity;

import androidx.annotation.Nullable;

public class UploadActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        // TODO. 뷰 그리기
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UploadActivity.class);
        context.startActivity(intent);
    }

}
