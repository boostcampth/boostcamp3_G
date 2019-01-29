package com.boostcamp.dreampicker.view.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.BR;
import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.viewmodel.FeedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class FeedFragment extends Fragment {
    private FeedAdapter adapter = new FeedAdapter();
    private FragmentFeedBinding binding;
    private FeedViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Todo : BaseFragment에 적용 필요
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        model = ViewModelProviders.of(this).get(FeedViewModel.class);
        binding.setVariable(BR.model, model);
        
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView rv = view.findViewById(R.id.rv_feed);
        rv.setAdapter(adapter);

        model.getFeeds().observe(this, adapter::addAll);
    }
}
