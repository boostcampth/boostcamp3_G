package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.databinding.ActivityMainBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedFragment;
import com.boostcamp.dreampicker.presentation.upload.UploadActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initView() {

        // 내비게이션 바 생성
        binding.navigation.setOnNavigationItemSelectedListener(this);

        // 시작 position home 으로 설정
        binding.navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = FeedFragment.newInstance();

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = FeedFragment.newInstance();
                break;
            case R.id.navigation_notifications:
                fragment = VotedFragment.newInstance();
                break;
            case R.id.navigation_profile:
                final String userId = FirebaseManager.getCurrentUserId();
                if (userId != null) {
                    fragment = ProfileFragment.newInstance(userId);
                } else {
                    startActivity(LogInActivity.getLaunchIntent(this));
                    finish();
                }
                break;
        }

        // 프래그먼트 전환
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();

        return true;
    }

    public void upload(View view) {
        startActivity(UploadActivity.getLaunchIntent(this));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
