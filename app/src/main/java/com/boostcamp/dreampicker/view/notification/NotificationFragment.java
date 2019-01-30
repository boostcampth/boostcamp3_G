package com.boostcamp.dreampicker.view.notification;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentNotificationBinding;
import com.boostcamp.dreampicker.view.BaseFragment;

public class NotificationFragment extends BaseFragment<FragmentNotificationBinding> {

    public NotificationFragment() {}

    public static NotificationFragment newInstance(){

        // TODO. 필요한 argument 세팅
        return new NotificationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO. argument 넘겨 받은거 처리하는 작업
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {

        // TODO. 뷰 그리기
    }
}
