package com.boostcamp.dreampicker.view.profile;


import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.view.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    public ProfileFragment() {

    }

    public static ProfileFragment getInstance(){

        return new ProfileFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        // TODO. view init
    }
}
