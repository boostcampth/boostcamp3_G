package com.boostcamp.dreampicker.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this,getLayoutId());
    }
    public void makeToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
    protected abstract int getLayoutId();
}
