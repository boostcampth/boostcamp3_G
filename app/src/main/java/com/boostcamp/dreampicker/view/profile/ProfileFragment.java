package com.boostcamp.dreampicker.view.profile;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.user.UserRepository;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.utils.ImageUtil;
import com.boostcamp.dreampicker.view.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    private final int NUM_OF_TAB_BUTTONS = 2;
    // TODO. ViewModel 로 이동
    private UserRepository repository = UserRepository.getInstance();

    public ProfileFragment() {

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
        binding.viewPagerProfile.setAdapter(adapter);

        // 탭 레이아웃 생성
        binding.tabProfile.setupWithViewPager(binding.viewPagerProfile);
        for(int i = 0 ; i < NUM_OF_TAB_BUTTONS ; i++){
            TabLayout.Tab tab = binding.tabProfile.getTabAt(i);
            tab.setText(getResources().getStringArray(R.array.profile_tab_names)[i]);
        }
    }


    private class ProfilePagerAdapter extends FragmentPagerAdapter {
    @SuppressLint("CheckResult")
    private void loadData() {
        repository.getUserDetail("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> binding.setUser(user));
    }

    class ProfilePagerAdapter extends FragmentPagerAdapter {

        Fragment[] fragments = new Fragment[2];

        public ProfilePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments[0] = FeedListFragment.getInstance();
            fragments[1] = FeedListFragment.getInstance();
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
}
