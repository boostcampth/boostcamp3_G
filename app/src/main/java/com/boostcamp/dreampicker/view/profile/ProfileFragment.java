package com.boostcamp.dreampicker.view.profile;


import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.view.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    public ProfileFragment() {

    }

    public static ProfileFragment getInstance(){

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
    }

    private void initView() {

        binding.viewPagerProfile.setAdapter(new ProfilePagerAdapter(getChildFragmentManager()));
        binding.tabProfile.setupWithViewPager(binding.viewPagerProfile);

        binding.tabProfile.getTabAt(0).setText("진행중인 투표");
        binding.tabProfile.getTabAt(1).setText("완료된 투표");

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
