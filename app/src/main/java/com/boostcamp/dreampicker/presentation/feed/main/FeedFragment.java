package com.boostcamp.dreampicker.presentation.feed.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.di.Injection;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;
import com.boostcamp.dreampicker.presentation.profile.ProfileActivity;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;

public class FeedFragment extends BaseFragment<FragmentFeedBinding> {

    private boolean isLastPage = false;

    private CompositeDisposable disposable = new CompositeDisposable();

    public FeedFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initViews();
        subscribeViewModel();
        binding.getVm().loadFeedList();
    }

    private void initViewModel() {
        final FeedViewModel vm = ViewModelProviders.of(this,
                Injection.provideFeedViewModelFactory(getContext())).get(FeedViewModel.class);

        binding.setVm(vm);
    }

    private void initViews() {
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initRecyclerView() {
        final FeedAdapter adapter = new FeedAdapter(
                (feedId, selectionId) -> binding.getVm().vote(feedId, selectionId),
                this::startFeedDetailActivity,
                writer -> startActivity(ProfileActivity.getLaunchIntent(getContext(), writer)));

        binding.rvFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!binding.rvFeed.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    if (isLastPage) {
                        showToast(getString(R.string.feed_last_page));
                    } else {
                        binding.getVm().loadFeedList();
                    }
                }
            }
        });
        binding.rvFeed.setItemAnimator(null);
        binding.rvFeed.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setOnRefreshListener(() -> {
            binding.getVm().refresh();
            binding.swipe.setRefreshing(false);
        });
    }

    private void subscribeViewModel() {
        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.loading.setVisibility(View.VISIBLE);
            } else {
                binding.loading.setVisibility(View.GONE);
            }
        });

        // Todo : Swipe 막기 처리 필요
        binding.getVm().getIsLastPage().observe(this, isLastPage -> this.isLastPage = isLastPage);

        binding.getVm().getError().observe(this, e -> showToast(getString(R.string.feed_error_message)));
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

    private void startFeedDetailActivity(@NonNull String feedId,
                                         @NonNull String imageUrlA,
                                         @NonNull String imageUrlB) {
        if (getContext() != null) {
            disposable.add(TedRxOnActivityResult.with(getContext())
                    .startActivityForResult(
                            FeedDetailActivity.getLaunchIntent(getContext(), feedId, imageUrlA, imageUrlB))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            binding.getVm().getFeed(feedId);
                        }
                    }));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
