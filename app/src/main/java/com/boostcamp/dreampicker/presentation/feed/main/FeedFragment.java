package com.boostcamp.dreampicker.presentation.feed.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class FeedFragment extends BaseFragment<FragmentFeedBinding> {
    private static final String TEXT_LAST_PAGE = "마지막 페이지입니다.";
    private static final String ERROR_MESSAGE = "에러가 발생했습니다.";

    public FeedFragment() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
        subscribeViewModel();
        binding.getVm().loadFeedList();
    }

    private void initViewModel() {
        final FeedViewModel vm = ViewModelProviders.of(this,
                new FeedViewModelFactory(FeedRepositoryImpl.getInstance(
                        FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())))
                .get(FeedViewModel.class);

        binding.setVm(vm);
    }

    private void initRecyclerView() {
        final FeedAdapter adapter = new FeedAdapter();
        binding.rvFeed.setAdapter(adapter);
        binding.getVm().getFeedList().observe(this, adapter::submitList);
    }
    private void subscribeViewModel() {
        binding.getVm().getIsLastPage().observe(this, isLastPage -> showToast(TEXT_LAST_PAGE));
        binding.getVm().getError().observe(this, e -> showToast(ERROR_MESSAGE));
    }
    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }
}
