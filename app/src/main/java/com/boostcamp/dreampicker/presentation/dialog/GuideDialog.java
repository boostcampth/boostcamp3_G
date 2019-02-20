package com.boostcamp.dreampicker.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;

import androidx.annotation.NonNull;

public class GuideDialog extends Dialog {
    public GuideDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guide);

        findViewById(R.id.btn_close).setOnClickListener(v -> dismiss());
    }

}
