package com.boostcamp.dreampicker.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ViewProfileCountBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class ProfileCountView extends LinearLayout {

    ViewProfileCountBinding binding;

    public ProfileCountView(Context context) {
        super(context);
        initView();
    }

    public ProfileCountView(Context context,
                            @Nullable AttributeSet attrs) {

        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public ProfileCountView(Context context,
                            @Nullable AttributeSet attrs,
                            int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_profile_count, this, false);
        addView(view);

        binding = DataBindingUtil.bind(view);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProfileCountView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProfileCountView, defStyleAttr, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        String count = typedArray.getString(R.styleable.ProfileCountView_count);
        binding.textCount.setText(count);
        String title = typedArray.getString(R.styleable.ProfileCountView_title);
        binding.textTitle.setText(title);

        typedArray.recycle();
    }

    void setCount(String count){
        binding.textCount.setText(count);
    }

    void setTitle(String title){
        binding.textTitle.setText(title);
    }
}
