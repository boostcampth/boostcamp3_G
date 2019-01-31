package com.boostcamp.dreampicker.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityMainBinding;
import com.boostcamp.dreampicker.view.BaseActivity;
import com.boostcamp.dreampicker.view.feed.FeedFragment;
import com.boostcamp.dreampicker.view.notification.NotificationFragment;
import com.boostcamp.dreampicker.view.profile.ProfileFragment;
import com.boostcamp.dreampicker.view.search.SearchFragment;
import com.boostcamp.dreampicker.view.upload.UploadActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        // 내비게이션 바 생성
        binding.navigation.setOnNavigationItemSelectedListener(this);

        // 시작 position home 으로 설정
        binding.navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = new FeedFragment();

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new FeedFragment();
                break;
            case R.id.navigation_search:
                fragment = SearchFragment.newInstance();
                break;
            case R.id.navigation_upload:
                UploadActivity.startActivity(this);
                return true;
            case R.id.navigation_notifications:
                fragment = NotificationFragment.newInstance();
                break;
            case R.id.navigation_profile:
                fragment = ProfileFragment.newInstance();
                break;
        }

        // 프래그먼트 전환
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();

        return true;
    }
}
