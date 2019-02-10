package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.common.FeedPrinter;
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

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        FeedRepositoryImpl repository = FeedRepositoryImpl.getInstance(FirebaseFirestore.getInstance());

/*       repository.getNotEndedFeedList(new Date(), 10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> FeedPrinter.printFeed(list), e -> e.printStackTrace());*/

/*        repository.vote(FirebaseManager.getCurrentUserId(),"feed-cda3e1be-de33-4ba6-a400-4c041fc7bbf2", "image-9327270a-c3dc-4325-ab75-b3952abd5f1f")
                    .subscribe(feed -> FeedPrinter.print(feed), e -> e.printStackTrace());*/

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
