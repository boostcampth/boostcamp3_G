package com.boostcamp.dreampicker.presentation.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;
import com.boostcamp.dreampicker.data.source.remote.firebase.UserFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    private static final String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(@NonNull String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    private String userId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            userId = savedInstanceState.getString(ARGUMENT_USER_ID);
        } else {
            final Bundle args = getArguments();
            if (args != null) {
                userId = args.getString(ARGUMENT_USER_ID);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initViews();
    }

    private void initViewModel() {
        // 프로필 정보 담고있는 뷰모델
        ProfileViewModel viewModel = ViewModelProviders.of(this,
                new ProfileViewModel.Factory(
                        UserRepository.getInstance(UserFirebaseService.getInstance()),
                        userId
                )).get(ProfileViewModel.class);
        binding.setVm(viewModel);
        binding.getVm().getError().observe(this, Throwable::printStackTrace);

        // 피드 목록 담고있는 뷰모델
        ProfileFeedViewModel feedViewModel = ViewModelProviders.of(this,
                new ProfileFeedViewModel.Factory(
                        FeedRepository.getInstance(FeedFirebaseService.getInstance()),
                        userId
                )).get(ProfileFeedViewModel.class);
        binding.setFeedVm(feedViewModel);
        binding.getVm().getError().observe(this, Throwable::printStackTrace);
    }

    private void initViews() {
        initTabLayout();
        initRecyclerView();
        initCountingView();
    }

    private void initTabLayout() {

        final int NUM_OF_TAB_BUTTONS = 2;

        for (int i = 0; i < NUM_OF_TAB_BUTTONS; i++) {
            TabLayout.Tab tab = binding.tabProfile.newTab();
            tab.setText(getResources().getStringArray(R.array.profile_tab_names)[i]);
            binding.tabProfile.addTab(tab);
        }
    }

    private void initRecyclerView() {
        ProfileFeedAdapter adapter = new ProfileFeedAdapter(this::startFeedDetailActivity);
        binding.recyclerProfileFeed.setAdapter(adapter);

        binding.getFeedVm().feedPagedList.observe(this, adapter::submitList);
    }

    private void initCountingView() {
        binding.layoutFollower.container.setOnClickListener(__ -> startProfileFollowActivity());
        binding.layoutFollowing.container.setOnClickListener(__ -> startProfileFollowActivity());
    }

    private void startProfileFollowActivity() {
        // TODO. 팔로워, 팔로잉 리스트 화면 띄우기
    }

    private void startFeedDetailActivity(Feed feed) {
        Toast.makeText(getContext(), "item Clicked: " + feed.getId(), Toast.LENGTH_SHORT).show();
    }
}
