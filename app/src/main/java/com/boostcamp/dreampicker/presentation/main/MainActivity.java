package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityMainBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;
import com.boostcamp.dreampicker.presentation.upload.UploadActivity;
import com.boostcamp.dreampicker.utils.GuideDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends BaseActivity<ActivityMainBinding>
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Inject
    FeedFragment feedFragment;

    @Inject
    VotedFragment votedFragment;
    @Inject
    ProfileFragment profileFragment;
    private FragmentManager fm = getSupportFragmentManager();

    private Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initNavigation();
        showTutorial();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initNavigation() {

        // 내비게이션 바 생성
        binding.navigation.setOnNavigationItemSelectedListener(this);

        active = feedFragment;
        fm.beginTransaction().add(R.id.frame, profileFragment, "3").hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.frame, votedFragment, "2").hide(votedFragment).commit();
        fm.beginTransaction().add(R.id.frame, feedFragment, "1").commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fm.beginTransaction().hide(active).show(feedFragment).commit();
                active = feedFragment;
                return true;
            case R.id.navigation_voted:
                fm.beginTransaction().hide(active).show(votedFragment).commit();
                active = votedFragment;
                return true;
            case R.id.navigation_profile:
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                return true;
        }
        return false;
    }

    private void showTutorial() {
        final GuideDialog dialog = new GuideDialog(this);
        dialog.show();
    }

    public void upload(View view) {
        startActivity(UploadActivity.getLaunchIntent(this));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
