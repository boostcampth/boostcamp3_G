package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityFrameBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.presentation.feed.feedDetail.FeedDetailFragment;
import com.boostcamp.dreampicker.presentation.user.profile.ProfileFragment;
import com.boostcamp.dreampicker.presentation.user.userList.UserListFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FrameActivity extends BaseActivity<ActivityFrameBinding> {
    private static final String EXTRA_FRAGMENT_ID = "EXTRA_FRAGMENT_ID";
    private static final String EXTRA_ENTITY_ID = "EXTRA_ENTITY_ID";
    public static final int FRAGMENT_FEED_DETAIL = 0;
    public static final int FRAGMENT_PROFILE = 1;
    public static final int FRAGMENT_USER_LIST = 2;

    private int fragmentId;
    private String entityId;

    public static Intent getLaunchIntent(Context context,
                                       int fragmentId,
                                       String entityId){

        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_ID, fragmentId);
        intent.putExtra(EXTRA_ENTITY_ID, entityId);

        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_frame;
    }

    private void getExtra(Intent intent){
        fragmentId = intent.getIntExtra(EXTRA_FRAGMENT_ID, 0);
        entityId = intent.getStringExtra(EXTRA_ENTITY_ID);
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
            case FRAGMENT_USER_LIST:
                fragment = UserListFragment.newInstance();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }
}
