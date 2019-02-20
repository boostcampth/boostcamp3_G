package com.boostcamp.dreampicker.presentation.upload;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityUploadBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.utils.LoadingDialog;
import com.boostcamp.dreampicker.utils.NetworkUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import gun0912.tedbottompicker.TedBottomPicker;

public class UploadActivity extends BaseActivity<ActivityUploadBinding> {

    private static final String CAMERA = Manifest.permission.CAMERA;
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final int A = 1;
    private static final int B = 2;

    private Dialog loadingDialog;

    @Inject
    UploadViewModelFactory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initViews();
        subscribeViewModel();
    }

    private void initViewModel() {
        final UploadViewModel vm =
                ViewModelProviders.of(this, factory).get(UploadViewModel.class);

        binding.setVm(vm);
        binding.setLifecycleOwner(this);
    }

    private void initViews() {
        initToolbar();
        initImageViews();
        initDialog();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeAsUpIndicator(R.drawable.btn_toolbar_close);
            toolbar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initImageViews() {
        binding.ivUploadA.setOnClickListener(__ -> onImageClick(A));
        binding.ivUploadB.setOnClickListener(__ -> onImageClick(B));
    }

    private void initDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
    }

    private void onImageClick(final int flag) {
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

    private void showBottomPicker(final int flag) {
        TedBottomPicker.with(this)
                .setOnImageSelectedListener(uri -> binding.getVm().setImagePath(uri, flag))
                .setOnErrorListener(message ->
                        showToast(getString(R.string.common_error_message, message)))
                .setPeekHeight(800)
                .showTitle(true)
                .create()
                .show(getSupportFragmentManager());
    }

    private void subscribeViewModel() {
        binding.getVm().getValidate().observe(this, v -> {
            if (v) {
                showToast(getString(R.string.upload_success_message));
                finish();
            } else {
                showToast(getString(R.string.upload_fail_message));
            }
        });

        binding.getVm().getIsLoading().observe(this, loading -> {
            if (loading) {
                loadingDialog.show();
            } else {
                loadingDialog.hide();
            }
        });

        binding.getVm().getError().observe(this,
                e -> showToast(getString(R.string.upload_error_message)));
        binding.getVm().getAdultImageError().observe(this,
                e -> showToast(getString(R.string.upload_adult_image_error_message)));
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, UploadActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_upload) {
            if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                binding.getVm().upload(
                        binding.tgUploadTagA.getTags(),
                        binding.tgUploadTagB.getTags());
            } else {
                showToast(getString(R.string.network_connection_state_notification));
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
