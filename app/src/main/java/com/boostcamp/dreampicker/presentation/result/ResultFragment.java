package com.boostcamp.dreampicker.presentation.result;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.databinding.FragmentResultBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

public class ResultFragment extends BaseFragment<FragmentResultBinding> {

    public ResultFragment() { }

    public static ResultFragment newInstance(){
        return new ResultFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView();
    }

    private void initViewModel() {
        final ResultViewModel viewModel = ViewModelProviders.of(this,
                new ResultViewModelFactory(AppDatabase.getDatabase(getContext()).votedFeedDao()))
                .get(ResultViewModel.class);
        // For test
        viewModel.getVotedFeedList().observe(this, list -> {
            Log.d("Melon", list.get(0).getId());
        });
    }

    private void initView() {
        // TODO. 뷰 그리기
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_result;
    }
}
