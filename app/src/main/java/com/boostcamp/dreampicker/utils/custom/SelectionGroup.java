package com.boostcamp.dreampicker.utils.custom;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.boostcamp.dreampicker.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID;
import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.VERTICAL;
import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT;

public class SelectionGroup extends ConstraintLayout {
    protected static final int NONE = 0, LEFT = 1, RIGHT = 2;
    protected static final String AUTO_TAG = "AUTO_TAG";

    @IntDef({NONE, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Position {
    }

    protected int GUIDELINE;
    protected int CONTAINER_LEFT;
    protected int CONTAINER_RIGHT;
    protected int SELECTOR;

    protected int DEFAULT_SIZE = 56;

    private int index = 0;

    private String tag;

    public ImageView left, right;
    public View selector;

    @Nullable
    private OnDropListener onDropListener;
    private OnTouchListener onTouchListener = new OnTouchListener();

    private interface OnDropListener {
        void onDrop(@Position final int position);
    }

    private interface OnDropCallback {
        void onDrop();
    }

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

    protected void initView() {
        initViewId();
        initGuideline();
        initContainer();
        initSelector();
        initListener();
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

    protected void initSelector() {
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

    private void initListener() {
        selector.setOnTouchListener(onTouchListener);
        left.setOnDragListener(new OnDragListener(() -> {
            dropAnimation(LEFT);
            if (onDropListener != null) {
                onDropListener.onDrop(LEFT);
            }
        }));

        right.setOnDragListener(new OnDragListener(() -> {
            dropAnimation(RIGHT);
            if (onDropListener != null) {
                onDropListener.onDrop(RIGHT);
            }
        }));
    }

    /**
     * After dropdown, Selector move parameter's position.
     */
    protected void dropAnimation(@Position final int position) {
        selector.startAnimation(new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                final ConstraintLayout.LayoutParams params =
                        (ConstraintLayout.LayoutParams) selector.getLayoutParams();

                if (position == NONE) {
                    params.startToStart = PARENT_ID;
                    params.endToEnd = PARENT_ID;
                } else if (position == LEFT) {
                    params.startToStart = PARENT_ID;
                    params.endToEnd = GUIDELINE;
                } else if (position == RIGHT) {
                    params.startToStart = GUIDELINE;
                    params.endToEnd = PARENT_ID;
                }
                selector.setLayoutParams(params);
            }
        });
    }

    public void dropLeft() {
        dropAnimation(LEFT);
    }

    public void dropRight() {
        dropAnimation(RIGHT);
    }

    public void dropCancel() {
        dropAnimation(NONE);
    }

    /**
     * Apply selector size.
     */
    private void setSelectorSize(int size) {
        final LayoutParams params = (LayoutParams) selector.getLayoutParams();
        final int dpSize = dp2px(size);
        params.width = dpSize;
        params.height = dpSize;
    }

    /**
     * Apply selector background image by resource ID.
     */
    private void setSelectorBackgroundResource(@DrawableRes int resId) {
        selector.setBackgroundResource(resId);
    }

    private void setContainerBackgroundColor(ImageView container, @ColorInt int color) {
        container.setBackgroundColor(color);
    }

    private void setContainerImageResource(ImageView container, @DrawableRes int resId) {
        container.setImageResource(resId);
    }

    private void setContainerImageTint(ImageView container, @ColorInt int color) {
        container.setImageTintList(ColorStateList.valueOf(color));
    }

    /**
     * @param data Send clip data to drop view.
     */
    public void setData(@NonNull final String data) {
        onTouchListener.setData(data);
    }

    /**
     * This params decide drop range.
     * If "SelectionGroups" are same tag, There are possible to dropdown.
     */
    public void setTag(@NonNull final String tag) {
        this.tag = tag;
    }

    /**
     * Selector dropdown listener at outer.
     */
    public void setOnDropListener(@Nullable OnDropListener onDropListener) {
        this.onDropListener = onDropListener;
    }

    /**
     * If shadow mode, When dragging disappear selector.
     */
    public void setShadowMode(boolean isShadow) {
        onTouchListener.setShadowMode(isShadow);
    }

    protected void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SelectionGroup);
        setTypeArray(typedArray);
    }


    protected void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SelectionGroup, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        setSelectorBackgroundResource(typedArray.getResourceId(R.styleable.SelectionGroup_selector, 0));
        setSelectorSize(typedArray.getInt(R.styleable.SelectionGroup_selectorSize, DEFAULT_SIZE));

        setContainerImageResource(left, typedArray.getResourceId(R.styleable.SelectionGroup_leftImage, 0));
        setContainerBackgroundColor(left, typedArray.getColor(R.styleable.SelectionGroup_leftBgColor, 0));
        setContainerImageTint(left, typedArray.getColor(R.styleable.SelectionGroup_leftTint, 0));

        setContainerImageResource(right, typedArray.getResourceId(R.styleable.SelectionGroup_rightImage, 0));
        setContainerBackgroundColor(right, typedArray.getColor(R.styleable.SelectionGroup_rightBgColor, 0));
        setContainerImageTint(right, typedArray.getColor(R.styleable.SelectionGroup_rightTint, 0));

        final String tag = typedArray.getString(R.styleable.SelectionGroup_selectorTag);
        this.tag = tag == null ? AUTO_TAG : tag;
        onTouchListener.setLabel(this.tag);

        typedArray.recycle();
    }

    protected int getIndex() {
        return index++;
    }

    protected int dp2px(float dp) {
        return (int) (dp * getContext().getApplicationContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    private class OnTouchListener implements View.OnTouchListener {
        private String DEFAULT_DATA = "";
        private String label;
        private String data;

        private boolean isShadowMode = false;

        private void setLabel(@NonNull final String label) {
            this.label = label;
        }

        private void setData(@NonNull final String data) {
            this.data = data;
        }

        private void setShadowMode(boolean isShadowMode) {
            this.isShadowMode = isShadowMode;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (data == null) {
                    data = DEFAULT_DATA;
                }
                final ClipData.Item item = new ClipData.Item(data);
                final String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                final ClipData clipData = new ClipData(label, mimeTypes, item);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(clipData, // Data to be dragged
                            new View.DragShadowBuilder(v), // Drag shadow
                            v, // Dropdown  Vew
                            0 // No operation
                    );
                } else {
                    v.startDrag(clipData, // Data to be dragged
                            new View.DragShadowBuilder(v), // Drag shadow
                            v, // Dropdown view
                            0 // No operation
                    );
                }
                if (isShadowMode) {
                    v.setVisibility(View.INVISIBLE);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    private class OnDragListener implements View.OnDragListener {
        @NonNull
        private final OnDropCallback callback;

        private OnDragListener(@NonNull OnDropCallback callback) {
            this.callback = callback;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                final CharSequence label = event.getClipData().getDescription().getLabel();
                if (label != null && label.toString().equals(tag)) callback.onDrop();
                else return false;
            } else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                selector.setVisibility(View.VISIBLE);
            }
            return true;
        }
    }
}
