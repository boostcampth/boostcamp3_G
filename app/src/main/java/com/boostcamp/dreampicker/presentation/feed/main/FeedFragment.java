package com.boostcamp.dreampicker.presentation.feed.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class FeedFragment extends Fragment {
    private FragmentFeedBinding binding;

    public FeedFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
        subscribeLoading();
        subscribeError();
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
        binding.getVm().getFeedList().observe(this, list -> {
            if(list.size() > 0) {
                binding.rvFeed.setVisibility(View.VISIBLE);
            }
            adapter.submitList(list);
        });
    }
    private void subscribeLoading() {
        // Todo : DataBinding
        binding.getVm().getIsLoading().observe(this, loading -> Log.d("Melon", loading +""));
        // Todo : SnackBar
        binding.getVm().getIsLastPage().observe(this, isLastPage -> { });
    }

    private void subscribeError() {
        // Todo : 에러 처리 구현
        binding.getVm().getError().observe(this, e -> { });
    }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }
}
