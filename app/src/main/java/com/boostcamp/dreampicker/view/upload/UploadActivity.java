package com.boostcamp.dreampicker.view.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityUploadBinding;
import com.boostcamp.dreampicker.view.BaseActivity;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.TransitionManager;

public class UploadActivity extends BaseActivity<ActivityUploadBinding> {
    private boolean leftImageAnimationToggle = false;
    private boolean rightImageAnimationToggle = false;

    private ConstraintLayout rootLayout;

    private ConstraintSet rootSet = new ConstraintSet();
    private ConstraintSet leftSet = new ConstraintSet();
    private ConstraintSet rightSet = new ConstraintSet();

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
    }

    public void onImageLeftClick(View view) {
        TransitionManager.beginDelayedTransition(rootLayout);
        ConstraintSet set;
        set = !leftImageAnimationToggle ? leftSet : rootSet;
        set.applyTo(rootLayout);
        leftImageAnimationToggle = !leftImageAnimationToggle;
    }

    public void onImageRightClick(View view) {
        TransitionManager.beginDelayedTransition(rootLayout);
        ConstraintSet set;
        set = !rightImageAnimationToggle ? rightSet : rootSet;
        set.applyTo(rootLayout);
        rightImageAnimationToggle = !rightImageAnimationToggle;
    }

    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, UploadActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }
}
