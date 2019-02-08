package com.boostcamp.dreampicker.presentation.search;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentSearchBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.search.feed.SearchFeedFragment;
import com.boostcamp.dreampicker.presentation.search.user.SearchUserFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    private final int NUM_OF_TAB_BUTTONS = 2;

    private List<OnSearchListener> onSearchListeners = new ArrayList<>();

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        initViewPagerTab();
        initSearchButton();
    }

    private void initViewPagerTab() {
        binding.vpSearch.setAdapter(new SearchPagerAdapter(getChildFragmentManager()));
        binding.tabSearch.setupWithViewPager(binding.vpSearch);
        for (int i = 0; i < NUM_OF_TAB_BUTTONS; i++) {
            TabLayout.Tab tab = binding.tabSearch.getTabAt(i);
            tab.setText(getResources().getStringArray(R.array.search_tab_names)[i]);
        }
    }

    private void initSearchButton() {
        binding.layoutSearchBar.btnSearch.setOnClickListener(__ ->
                onSearch(binding.layoutSearchBar.etSearch.getText().toString())
        );

        binding.layoutSearchBar.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearch(binding.layoutSearchBar.etSearch.getText().toString());
                return true;
            }
            return false;
        });
    }

    /**
     * TODO. 같은 검색어 요청시 예외처리 */
    private void onSearch(@Nullable final String searchKey) {
        if(searchKey != null && !TextUtils.isEmpty(searchKey)) {
            for (OnSearchListener listener : onSearchListeners) {
                listener.onSearch(searchKey);
            }
            closeKeyboard();
        }
    }

    private void closeKeyboard(){
        binding.layoutSearchBar.etSearch.clearFocus();
        InputMethodManager inputManager =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(binding.layoutSearchBar.etSearch.getWindowToken(), 0);
        }
    }


    class SearchPagerAdapter extends FragmentPagerAdapter {

        Fragment[] fragments = new Fragment[NUM_OF_TAB_BUTTONS];

        SearchPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments[0] = SearchUserFragment.newInstance();
            fragments[1] = SearchFeedFragment.newInstance();
            onSearchListeners.add((OnSearchListener) fragments[0]);
            onSearchListeners.add((OnSearchListener) fragments[1]);
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
