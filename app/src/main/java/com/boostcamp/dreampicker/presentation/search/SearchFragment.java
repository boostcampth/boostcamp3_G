package com.boostcamp.dreampicker.presentation.search;


import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.databinding.FragmentSearchBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    private final int NUM_OF_TAB_BUTTONS = 2;

    // TODO. ViewModel 로 이동
    private FeedRepository repository = FeedRepository.getInstance(FeedFirebaseService.getInstance());

    private SearchPagerAdapter adapter;

    public SearchFragment() {}

    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO. argument 넘겨 받은거 처리하는 작업
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {

        // 뷰페이저 생성
        adapter = new SearchPagerAdapter(getChildFragmentManager());
        binding.vpSearch.setAdapter(adapter);

        // 탭 레이아웃 생성
        binding.tabSearch.setupWithViewPager(binding.vpSearch);
        for(int i = 0 ; i < NUM_OF_TAB_BUTTONS ; i++) {
            TabLayout.Tab tab = binding.tabSearch.getTabAt(i);
            tab.setText(getResources().getStringArray(R.array.search_tab_names)[i]);
        }
    }


    class SearchPagerAdapter extends FragmentPagerAdapter {

        Fragment[] fragments = new Fragment[NUM_OF_TAB_BUTTONS];

        public SearchPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments[0] = ProfileFragment.newInstance();
            fragments[1] = ProfileFragment.newInstance();
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
