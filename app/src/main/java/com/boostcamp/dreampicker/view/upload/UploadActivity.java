package com.boostcamp.dreampicker.view.upload;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityUploadBinding;
import com.boostcamp.dreampicker.utils.Util;
import com.boostcamp.dreampicker.view.BaseActivity;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import io.reactivex.disposables.Disposable;

public class UploadActivity extends BaseActivity<ActivityUploadBinding> {
    private boolean leftImageAnimationToggle = false;
    private boolean rightImageAnimationToggle = false;

    private ConstraintLayout rootLayout;

    private ConstraintSet rootSet = new ConstraintSet();
    private ConstraintSet leftSet = new ConstraintSet();
    private ConstraintSet rightSet = new ConstraintSet();

    private Disposable disposable;

    private static final String CAMERA = Manifest.permission.CAMERA;
    private static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        rootLayout = findViewById(R.id.cl_upload_root);
        rootSet.clone(rootLayout);
        leftSet.clone(this, R.layout.activity_upload_detail_left);
        rightSet.clone(this, R.layout.activity_upload_detail_right);

        ImageView leftImage = findViewById(R.id.iv_upload_left);
        ImageView rightImage = findViewById(R.id.iv_upload_right);

        leftImage.setOnClickListener((v) -> onImageClick(Util.LEFT));
        rightImage.setOnClickListener((v) -> onImageClick(Util.RIGHT));
    }

    public void onImageClick(@Util.VoteFlag int flag) {
        disposable = TedRx2Permission.with(this)
                .setPermissions(CAMERA, READ_EXTERNAL_STORAGE)
                .request()
                .filter(TedPermissionResult::isGranted)
                .map(result -> flag)
                .subscribe(this::showBottomPicker);
    }

    public void showBottomPicker(@Util.VoteFlag int flag) {
        Log.d("Melon", flag + "");
        /*TransitionManager.beginDelayedTransition(rootLayout);
        ConstraintSet set;
        set = !leftImageAnimationToggle ? leftSet : rootSet;
        set.applyTo(rootLayout);
        leftImageAnimationToggle = !leftImageAnimationToggle;*/
    }

    public void onImageRightClick(@Util.VoteFlag int flag) {
        /*TransitionManager.beginDelayedTransition(rootLayout);
        ConstraintSet set;
        set = !rightImageAnimationToggle ? rightSet : rootSet;
        set.applyTo(rootLayout);
        rightImageAnimationToggle = !rightImageAnimationToggle;*/
    }

    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, UploadActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
