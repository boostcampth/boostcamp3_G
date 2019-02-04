package com.boostcamp.dreampicker.presentation.user.profile;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.remote.firebase.UserFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.feed.feedList.FeedListFragment;
import com.boostcamp.dreampicker.presentation.main.FrameActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {

    private final int NUM_OF_TAB_BUTTONS = 2;

    // TODO. ViewModel 로 이동
    private UserRepository repository = UserRepository.getInstance(UserFirebaseService.getInstance());

    private ProfilePagerAdapter adapter;

    public ProfileFragment() { }

    public static ProfileFragment newInstance(){

        return new ProfileFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        // TODO. ViewModel 로 이동
        loadData();
    }

    private void initView() {

        // 뷰페이저 생성
        adapter = new ProfilePagerAdapter(getChildFragmentManager());
        binding.vpProfile.setAdapter(adapter);

        // 탭 레이아웃 생성
        binding.tabProfile.setupWithViewPager(binding.vpProfile);
        for(int i = 0 ; i < NUM_OF_TAB_BUTTONS ; i++){
            TabLayout.Tab tab = binding.tabProfile.getTabAt(i);
            tab.setText(getResources().getStringArray(R.array.profile_tab_names)[i]);
        }

        // TODO. 임시코드
        binding.layoutFollower.container.setOnClickListener(this::startFrameActivity);
        binding.layoutFollowing.container.setOnClickListener(this::startFrameActivity);
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        repository.getUserInfo("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(binding::setUser,
                        error -> Log.d("", error.getLocalizedMessage()));
    }

    /** 임시입니다.
     * TODO. ViewModel 로 이동 */
    private void startFrameActivity(View view){
        Intent intent = FrameActivity.getLaunchIntent(getContext(),
                FrameActivity.FRAGMENT_USER_LIST,
                "");

        startActivity(intent);
    }

    class ProfilePagerAdapter extends FragmentPagerAdapter {

        Fragment[] fragments = new Fragment[NUM_OF_TAB_BUTTONS];

        public ProfilePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments[0] = FeedListFragment.newInstance();
            fragments[1] = FeedListFragment.newInstance();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

    @Override
    protected ProfileViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ProfileViewModel.class);
    }
}
