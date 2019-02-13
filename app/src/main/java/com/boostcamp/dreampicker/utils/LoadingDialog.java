package com.boostcamp.dreampicker.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;

import androidx.annotation.NonNull;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }
}
