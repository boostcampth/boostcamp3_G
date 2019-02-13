package com.boostcamp.dreampicker.presentation.feed.voted;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.databinding.FragmentVotedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

public class VotedFragment extends BaseFragment<FragmentVotedBinding> {

    public VotedFragment() { }

    public static VotedFragment newInstance(){
        return new VotedFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView();
    }

    private void initViewModel() {
        final VotedViewModel viewModel = ViewModelProviders.of(this,
                new VotedViewModelFactory(AppDatabase.getDatabase(getContext()).votedFeedDao()))
                .get(VotedViewModel.class);

        binding.setVm(viewModel);
    }

    private void initView() {
        // TODO. 뷰 그리기
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voted;
    }
}
