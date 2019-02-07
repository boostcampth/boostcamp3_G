package com.boostcamp.dreampicker.presentation.search.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.remote.firebase.UserFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.databinding.FragmentSearchUserBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.search.OnSearchListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SearchUserFragment extends BaseFragment<FragmentSearchUserBinding> implements OnSearchListener {

    public SearchUserFragment() { }

    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_user;
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
        final SearchUserViewModel viewModel = ViewModelProviders.of(
                this,
                new SearchUserViewModel.Factory(
                        UserRepository.getInstance(UserFirebaseService.getInstance())
                )
        ).get(SearchUserViewModel.class);
        binding.setVm(viewModel);
        binding.getVm().getError().observe(this, Throwable::printStackTrace);
    }

    private void initRecyclerView() {
        SearchUserAdapter adapter = new SearchUserAdapter(this::startUserProfileActivity);
        binding.recyclerSearchUser.setAdapter(adapter);
    }

    private void startUserProfileActivity(User user) {
        // TODO. 유저 프로필 화면 띄우기
    }

}
