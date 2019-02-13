package com.boostcamp.dreampicker.presentation.main;

import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.databinding.ActivitySplashBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2초후 액티비티 전환
        final int LAUNCHING_TIME_OUT = 0;
        disposable.add(Completable
                .timer(LAUNCHING_TIME_OUT, TimeUnit.SECONDS)
                .subscribe(() -> {
                    if (FirebaseManager.getCurrentUser() == null) {
                        startActivity(LogInActivity.getLaunchIntent(this));
                    } else {
                        startActivity(MainActivity.getLaunchIntent(this));
                    }
                    finish();
                })
        );
    }

    @Override
    public void onBackPressed() {
        // disable back button
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
