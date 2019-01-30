package com.boostcamp.dreampicker.view.main;

import android.os.Bundle;
import android.os.Handler;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivitySplashBinding;
import com.boostcamp.dreampicker.view.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2초후 액티비티 전환
        new Handler().postDelayed(this::startMainActivity, 2000);
    }

    private void startMainActivity() {
        MainActivity.startActivity(this);
        finish();
    }
}
