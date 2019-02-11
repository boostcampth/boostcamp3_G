package com.boostcamp.dreampicker.presentation.legacyupload;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.databinding.ActivityUploadBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.utils.Constant;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import gun0912.tedbottompicker.TedBottomPicker;
import me.gujun.android.taggroup.TagGroup;

public class UploadActivity extends BaseActivity<ActivityUploadBinding> {
    private static final String CAMERA = Manifest.permission.CAMERA;
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String PERMISSION_DENIED_MESSAGE = "권한이 없습니다.";
    private static final String UPLOAD_DENIED_MESSAGE = "투표를 등록할 수 없습니다.";
    private static final String UPLOAD_GRANTED_MESSAGE = "투표 등록 완료";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initViews();
        subscribeValidate();
    }

    private void initViewModel() {
        final UploadViewModel viewModel = ViewModelProviders.of(this,
                new UploadViewModelFactory(
                        FeedRepository.getInstance(FeedFirebaseService.getInstance())))
                .get(UploadViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void initViews() {
        initToolbar();
        initImageViews();
        initTagGroup();
    }

    private void initToolbar() {
        final ImageButton btnClose = binding.toolbar.btnLeft;
        final ImageButton btnUpload = binding.toolbar.btnRight;

        btnClose.setImageResource(R.drawable.btn_toolbar_close);
        btnUpload.setImageResource(R.drawable.btn_toolbar_finger);

        btnClose.setOnClickListener((v) -> finish());
        btnUpload.setOnClickListener((v) -> binding.getViewModel().upload());
    }

    private void initImageViews() {
        binding.ivUploadLeft.setOnClickListener((v) -> onImageClick(Constant.IMAGE_LEFT));
        binding.ivUploadRight.setOnClickListener((v) -> onImageClick(Constant.IMAGE_RIGHT));
    }

    private void initTagGroup() {
        binding.tgUploadTagLeft.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            @Override
            public void onAppend(TagGroup tagGroup, String tag) {
                binding.getViewModel().setTag(tag, Constant.IMAGE_LEFT);
            }

            @Override
            public void onDelete(TagGroup tagGroup, String tag) {
                binding.getViewModel().deleteTag(tag, Constant.IMAGE_LEFT);
            }
        });

        binding.tgUploadTagRight.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            @Override
            public void onAppend(TagGroup tagGroup, String tag) {
                binding.getViewModel().setTag(tag, Constant.IMAGE_RIGHT);
            }

            @Override
            public void onDelete(TagGroup tagGroup, String tag) {
                binding.getViewModel().deleteTag(tag, Constant.IMAGE_RIGHT);
            }
        });
    }

    public void onImageClick(@Constant.ImageFlag String flag) {
        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showBottomPicker(flag);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        showToast(PERMISSION_DENIED_MESSAGE);
                    }
                })
                .setPermissions(CAMERA, WRITE_EXTERNAL_STORAGE)
                .check();
    }

    public void showBottomPicker(@Constant.ImageFlag String flag) {
        new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(uri -> binding.getViewModel().setImage(uri, flag))
                .setPeekHeight(800)
                .showTitle(true)
                .create()
                .show(getSupportFragmentManager());
    }

    private void subscribeValidate() {
        binding.getViewModel().getValidate().observe(this, v -> {
            if(v) {
                showToast(UPLOAD_GRANTED_MESSAGE);
                finish();
            } else {
                showToast(UPLOAD_DENIED_MESSAGE);
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, UploadActivity.class);
    }
}
