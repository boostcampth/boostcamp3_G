package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityLogInBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

public class LogInActivity extends BaseActivity<ActivityLogInBinding> {

    public static Intent getLaunchIntent(Context context) {

        return new Intent(context, LogInActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        // TODO. 뷰 그리기
    }

}
