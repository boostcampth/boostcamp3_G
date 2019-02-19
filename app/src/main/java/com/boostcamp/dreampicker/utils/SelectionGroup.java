package com.boostcamp.dreampicker.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.boostcamp.dreampicker.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID;
import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.VERTICAL;
import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT;

public class SelectionGroup extends ConstraintLayout {
    private int GUIDELINE;
    private int CONTAINER_LEFT;
    private int CONTAINER_RIGHT;
    private int SELECTOR;

    private int DEFAULT_SIZE = 64;

    private int index = 0;

    public ImageView left, right;
    public View selector;

    public SelectionGroup(Context context) {
        super(context);
        initView();
    }

    public SelectionGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SelectionGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        initViewId();
        initGuideline();
        initContainer();
        createSelector();
    }

    private void initViewId() {
        GUIDELINE = View.generateViewId();
        CONTAINER_LEFT = View.generateViewId();
        CONTAINER_RIGHT = View.generateViewId();
        SELECTOR = View.generateViewId();
    }

    private void initGuideline() {
        final LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.orientation = VERTICAL;
        final Guideline guideLine = new Guideline(getContext());

        guideLine.setLayoutParams(params);
        guideLine.setGuidelinePercent(0.5f);
        guideLine.setId(GUIDELINE);
        addView(guideLine, getIndex());
    }

    private void initContainer() {
        left = createContainer(new LayoutParams(MATCH_CONSTRAINT, MATCH_CONSTRAINT),
                PARENT_ID, GUIDELINE);
        right = createContainer(new LayoutParams(MATCH_CONSTRAINT, MATCH_CONSTRAINT),
                GUIDELINE, PARENT_ID);

        left.setId(CONTAINER_LEFT);
        right.setId(CONTAINER_RIGHT);

        addView(left, getIndex());
        addView(right, getIndex());
    }

    @NonNull
    private ImageView createContainer(@NonNull LayoutParams params,
                                      @IdRes int startToStart,
                                      @IdRes int endToEnd) {
        final ImageView imageView = new ImageView(getContext());
        params.topToTop = PARENT_ID;
        params.bottomToBottom = PARENT_ID;

        params.startToStart = startToStart;
        params.endToEnd = endToEnd;


        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(params);

        return imageView;
    }

    private void createSelector() {
        selector = new View(getContext());

        final int size = dp2px(DEFAULT_SIZE);
        final LayoutParams params = new LayoutParams(size, size);

        params.startToStart = PARENT_ID;
        params.endToEnd = PARENT_ID;
        params.topToTop = PARENT_ID;
        params.bottomToBottom = PARENT_ID;

        selector.setLayoutParams(params);

        selector.setId(SELECTOR);
        addView(selector, getIndex());
    }

    public void setSelectorSize(int size) {
        final LayoutParams params = (LayoutParams) selector.getLayoutParams();
        final int dpSize = dp2px(size);
        params.width = dpSize;
        params.height = dpSize;
    }

    public void setSelectorBackgroundResource(@DrawableRes int resId) {
        selector.setBackgroundResource(resId);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SelectionGroup);
        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SelectionGroup, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        int selectorBgResId = typedArray.getResourceId(R.styleable.SelectionGroup_selector, 0);
        setSelectorBackgroundResource(selectorBgResId);

        int leftImageBgId = typedArray.getResourceId(R.styleable.SelectionGroup_leftImage, 0);
        left.setBackgroundResource(leftImageBgId);

        int rightImageBgId = typedArray.getResourceId(R.styleable.SelectionGroup_rightImage, 0);
        right.setBackgroundResource(rightImageBgId);

        int size = typedArray.getResourceId(R.styleable.SelectionGroup_selectorSize, DEFAULT_SIZE);
        setSelectorSize(size);

        typedArray.recycle();
    }

    private int getIndex() {
        return index++;
    }

    private int dp2px(float dp) {
        return (int) (dp * getContext().getApplicationContext().getResources().getDisplayMetrics().density + 0.5f);
    }
}
