package com.boostcamp.dreampicker.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityFrameBinding;
import com.boostcamp.dreampicker.view.BaseActivity;
import com.boostcamp.dreampicker.view.feedDetail.FeedDetailFragment;
import com.boostcamp.dreampicker.view.profile.ProfileFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FrameActivity extends BaseActivity<ActivityFrameBinding> {
    private static final String EXTRA_FRAGMENT_ID = "EXTRA_FRAGMENT_ID";
    public static final int FRAGMENT_FEED_DETAIL = 0;
    public static final int FRAGMENT_PROFILE = 1;

    private int fragmentId;

    public static Intent getIntent(Context context, String fragmentId){
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_ID, fragmentId);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_frame;
    }

    private void getExtra(Intent intent){
        fragmentId = intent.getIntExtra(EXTRA_FRAGMENT_ID, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intent Extra 값 받아오기
        getExtra(getIntent());

        initView();
    }

    private void initView() {

        Fragment fragment = ProfileFragment.newInstance();

        switch (fragmentId){
            case FRAGMENT_FEED_DETAIL:
                fragment = FeedDetailFragment.newInstance("");
                break;
            case FRAGMENT_PROFILE:
                fragment = ProfileFragment.newInstance();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
