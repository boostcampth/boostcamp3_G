package com.boostcamp.dreampicker.presentation.search.feed;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.databinding.FragmentSearchFeedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFeedAdapter;
import com.boostcamp.dreampicker.presentation.search.OnSearchListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SearchFeedFragment extends BaseFragment<FragmentSearchFeedBinding> implements OnSearchListener {

    public SearchFeedFragment() {
    }

    public static SearchFeedFragment newInstance() {
        return new SearchFeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_feed;
    }

    @Override
    public void onSearch(@Nullable String searchKey) {
        // TODO. 리스트 초기화, searchKey 로 검색 재요청
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
    }

    private void initViewModel() {
        final SearchFeedViewModel viewModel = ViewModelProviders.of(
                this,
                new SearchFeedViewModel.Factory(
                        FeedRepository.getInstance(FeedFirebaseService.getInstance())
                )
        ).get(SearchFeedViewModel.class);
        binding.setVm(viewModel);
        binding.getVm().getError().observe(this, Throwable::printStackTrace);
    }

    private void initRecyclerView() {
        ProfileFeedAdapter adapter = new ProfileFeedAdapter(this::startFeedDetailActivity);
        binding.recyclerSearchFeed.setAdapter(adapter);
    }

    private void startFeedDetailActivity(Feed feed) {
        // TODO. 유저 프로필 화면 띄우기
    }

}
