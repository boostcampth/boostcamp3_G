package com.boostcamp.dreampicker.view.search;


import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.view.BaseFragment;
import com.boostcamp.dreampicker.databinding.FragmentSearchBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    public SearchFragment() {}

    public static SearchFragment newInstance(){

        // TODO. 필요한 argument 세팅
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
        // TODO. 뷰 그리기
    }
}
