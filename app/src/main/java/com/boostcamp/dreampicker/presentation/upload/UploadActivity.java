package com.boostcamp.dreampicker.presentation.upload;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.databinding.ActivityUploadBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import gun0912.tedbottompicker.TedBottomPicker;

public class UploadActivity extends BaseActivity<ActivityUploadBinding> {

    private static final String CAMERA = Manifest.permission.CAMERA;
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final int A = 1;
    private static final int B = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initViews();
        subscribeValidate();

    }

    private void initViewModel() {
        final UploadViewModel vm = ViewModelProviders.of(this,
                new UploadViewModelFactory(
                        FeedRepositoryImpl.getInstance(FirebaseFirestore.getInstance(),
                                FirebaseStorage.getInstance())))
                .get(UploadViewModel.class);

        binding.setViewModel(vm);
        binding.setLifecycleOwner(this);
    }

    private void initViews() {
        initToolbar();
        initImageViews();
    }

    private void initImageViews() {
        binding.ivUploadA.setOnClickListener(__ -> onImageClick(A));
        binding.ivUploadB.setOnClickListener(__ -> onImageClick(B));
    }

    private void initToolbar() {
        final ImageButton btnClose = binding.toolbar.btnLeft;
        final ImageButton btnUpload = binding.toolbar.btnRight;
        final List<String> tagListA = Arrays.asList(binding.tgUploadTagA.getTags());
        final List<String> tagListB = Arrays.asList(binding.tgUploadTagB.getTags());

        btnClose.setImageResource(R.drawable.btn_toolbar_close);
        btnUpload.setImageResource(R.drawable.btn_toolbar_finger);

        btnClose.setOnClickListener(__ -> finish());
        btnUpload.setOnClickListener(__ -> binding.getViewModel().upload(tagListA, tagListB));
    }

    public void onImageClick(final int flag) {
        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showBottomPicker(flag);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        showToast(getString(R.string.permission_denied_message));
                    }
                })
                .setPermissions(CAMERA, WRITE_EXTERNAL_STORAGE)
                .check();
    }

    public void showBottomPicker(final int flag) {
        new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(uri -> binding.getViewModel().setImagePath(uri, flag))
                .setPeekHeight(800)
                .showTitle(true)
                .create()
                .show(getSupportFragmentManager());
    }


    private void subscribeValidate() {
        binding.getViewModel().getValidate().observe(this, v -> {
            if (v) {
                showToast(getString(R.string.upload_success_message));
                finish();
            } else {
                showToast(getString(R.string.upload_fail_message));
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, UploadActivity.class);
    }

    @Override
    public void onBackPressed() {
        binding.getViewModel().getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                showToast(getString(R.string.upload_backpress_denied_message));
            } else {
                super.onBackPressed();
            }
        });
    }
}
