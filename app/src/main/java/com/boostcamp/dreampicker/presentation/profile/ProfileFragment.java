package com.boostcamp.dreampicker.presentation.profile;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.remote.firebase.UserFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    private final int NUM_OF_TAB_BUTTONS = 2;

    public ProfileFragment() { }

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView();
    }

    private void initViewModel() {
        ProfileViewModel.Factory factory = new ProfileViewModel.Factory(
                UserRepository.getInstance(UserFirebaseService.getInstance()));
        ProfileViewModel viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel.class);
    }

    private void initView() {

    }
}