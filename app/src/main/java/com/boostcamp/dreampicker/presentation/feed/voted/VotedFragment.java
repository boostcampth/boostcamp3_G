package com.boostcamp.dreampicker.presentation.feed.voted;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.databinding.FragmentVotedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class VotedFragment extends BaseFragment<FragmentVotedBinding> {

    public VotedFragment() {
    }

    public static VotedFragment newInstance() {
        return new VotedFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
    }

    private void initViewModel() {
        final VotedViewModel viewModel = ViewModelProviders.of(this,
                new VotedViewModelFactory(FeedRepositoryImpl.getInstance(
                        FirebaseFirestore.getInstance(),
                        FirebaseStorage.getInstance(),
                        AppDatabase.getDatabase(getContext()).votedFeedDao())))
                .get(VotedViewModel.class);

        binding.setVm(viewModel);
    }

    private void initRecyclerView() {
        final VotedAdapter adapter = new VotedAdapter();
        binding.rvVotedFeed.setAdapter(adapter);
        binding.getVm().getVotedFeedList().observe(this, list -> {
            if (list.isEmpty()) {
                binding.ivVotedFeedEmptyFinger.setVisibility(View.VISIBLE);
                binding.tvVotedFeedEmptyText.setVisibility(View.VISIBLE);
            }
            adapter.submitList(list);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voted;
    }
}
