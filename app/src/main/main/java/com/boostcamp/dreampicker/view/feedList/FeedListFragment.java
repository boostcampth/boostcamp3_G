package com.boostcamp.dreampicker.view.feedList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.feed.FeedRepository;
import com.boostcamp.dreampicker.databinding.FragmentFeedListBinding;
import com.boostcamp.dreampicker.view.BaseFragment;
import com.boostcamp.dreampicker.view.main.FrameActivity;
import com.boostcamp.dreampicker.viewmodel.BaseViewModel;
import com.boostcamp.dreampicker.viewmodel.FeedListViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedListFragment extends BaseFragment<FragmentFeedListBinding, FeedListViewModel> {

    // TODO. ViewModel 로 이동
    private FeedRepository repository = FeedRepository.getInstance();

    private FeedListAdapter adapter;

    public FeedListFragment(){}

    public static FeedListFragment newInstance() {

        return new FeedListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        // TODO. ViewModel 로 이동
        loadData();
    }

    private void initView() {

        // 리사이클러 뷰 생성
        adapter = new FeedListAdapter();
        adapter.setOnItemClickListener(position ->
                startFrameActivity(adapter.getItemList().get(position).getId()));
        binding.recyclerFeedList.setAdapter(adapter);
        binding.recyclerFeedList.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    }

    /**
     * Frame Activity 에 Feed Detail Fragment 띄우기 */
    private void startFrameActivity(String feedId) {
        FrameActivity.startActivity(getContext(),
                FrameActivity.FRAGMENT_FEED_DETAIL,
                feedId);
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        repository.searchAllFeed("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.adapter::addItems,
                        error -> Log.d("", error.getLocalizedMessage()));
    }

    @Override
    protected FeedListViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FeedListViewModel.class);
    }

    @Override
    protected int getVariableId() {
        return 0;
    }
}
