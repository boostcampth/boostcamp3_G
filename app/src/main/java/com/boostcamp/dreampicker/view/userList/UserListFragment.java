package com.boostcamp.dreampicker.view.userList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.user.UserRepository;
import com.boostcamp.dreampicker.databinding.FragmentUserListBinding;
import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.view.BaseFragment;
import com.boostcamp.dreampicker.view.main.FrameActivity;
import com.boostcamp.dreampicker.viewmodel.UserListViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UserListFragment extends BaseFragment<FragmentUserListBinding, UserListViewModel> {

    // TODO. ViewModel 로 이동
    private UserRepository repository = UserRepository.getInstance();

    private UserListAdapter adapter;

    public UserListFragment() {}

    public static UserListFragment newInstance(){

        return new UserListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        // TODO. ViewModel 로 이동
        loadData();
    }

    private void initView() {

        // 리사이클러뷰
        adapter = new UserListAdapter();
        adapter.setOnItemClickListener(position -> startFrameActivity(adapter.getItemList().get(position)));
        binding.recyclerUserList.setAdapter(adapter);
        binding.recyclerUserList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        repository.searchAllUser("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.adapter::addItems,
                        error -> Log.d("", error.getLocalizedMessage()));
    }

    private void startFrameActivity(User user) {
        FrameActivity.startActivity(getContext(), FrameActivity.FRAGMENT_PROFILE, "");
    }

    @Override
    protected UserListViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserListViewModel.class);
    }
}
