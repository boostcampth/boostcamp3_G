package com.boostcamp.dreampicker.presentation.upload;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityUploadBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.utils.Constant;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import gun0912.tedbottompicker.TedBottomPicker;

public class UploadActivity extends BaseActivity<ActivityUploadBinding> {
    private static final String CAMERA = Manifest.permission.CAMERA;
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERMISSION_DENIED_MESSAGE = "권한이 없습니다.";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initView();
    }

    private void initViewModel() {
        final UploadViewModel viewModel = ViewModelProviders.of(this).get(UploadViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void initView() {
        initImageViews();
    }

    private void initImageViews() {
        binding.ivUploadLeft.setOnClickListener((v) -> onImageClick(Constant.LEFT));
        binding.ivUploadRight.setOnClickListener((v) -> onImageClick(Constant.RIGHT));
    }

    public void onImageClick(@Constant.VoteFlag int flag) {
        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showBottomPicker(flag);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(
                                getApplicationContext(),
                                PERMISSION_DENIED_MESSAGE,
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setPermissions(CAMERA, WRITE_EXTERNAL_STORAGE)
                .check();
    }

    public void showBottomPicker(@Constant.VoteFlag int flag) {
        new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(uri -> binding.getViewModel().setImage(uri, flag))
                .setPeekHeight(800)
                .showTitle(true)
                .create()
                .show(getSupportFragmentManager());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }


    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, UploadActivity.class);
    }
}
