package com.boostcamp.dreampicker.view.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedListBinding;
import com.boostcamp.dreampicker.view.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

public class FeedListFragment extends BaseFragment<FragmentFeedListBinding> {

    public FeedListFragment(){}

    public static FeedListFragment getInstance(){

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
    }

    private void initView() {
        FeedListAdapter adapter = new FeedListAdapter(context);
        // dummy data
        adapter.addItem("");
        adapter.addItem("");
        adapter.addItem("");
        adapter.addItem("");
        adapter.addItem("");

        binding.recyclerFeedList.setLayoutManager(new GridLayoutManager(context, 2));
        binding.recyclerFeedList.setAdapter(adapter);
    }
}
