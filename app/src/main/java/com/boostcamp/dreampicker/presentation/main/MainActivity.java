package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.databinding.ActivityMainBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.notification.NotificationFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;
import com.boostcamp.dreampicker.presentation.search.SearchFragment;
import com.boostcamp.dreampicker.presentation.upload.UploadActivity;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
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

        Fragment fragment = FeedFragment.newInstance();

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = FeedFragment.newInstance();
                break;
            case R.id.navigation_search:
                fragment = SearchFragment.newInstance();
                break;
            case R.id.navigation_upload:
                startActivity(UploadActivity.getLaunchIntent(this));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.navigation_notifications:
                fragment = NotificationFragment.newInstance();
                break;
            case R.id.navigation_profile:
                fragment = ProfileFragment.newInstance(FirebaseManager.getCurrentUserId());
                break;
        }

        // 프래그먼트 전환
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();

        return true;
    }
}
