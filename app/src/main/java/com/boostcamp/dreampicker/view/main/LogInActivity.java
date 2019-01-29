package com.boostcamp.dreampicker.view.main;

import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityLogInBinding;
import com.boostcamp.dreampicker.view.BaseActivity;

public class LogInActivity extends BaseActivity<ActivityLogInBinding> {

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
