package com.boostcamp.dreampicker.presentation.result;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentNotificationBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

public class ResultFragment extends BaseFragment<FragmentNotificationBinding> {

    public ResultFragment() {}

    public static ResultFragment newInstance(){
        return new ResultFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_result;
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
