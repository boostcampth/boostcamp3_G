package com.boostcamp.dreampicker.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.boostcamp.dreampicker.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GuideDialog extends Dialog {
    private static final int NONE = 0, LEFT = 1, RIGHT = 2;
    private static final String AUTO_TAG = "AUTO_TAG";

    @IntDef({NONE, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Position {
    }

    private interface OnDropListener {
        void onDrop(@Position final int position);
    }

    private interface OnDropCallback {
        void onDrop();
    }

    private View selector;
    private String mainTag;

    @Nullable
    private OnDropListener onDropListener;

    public GuideDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guide);
        initView();
    }

    private void initView() {
        selector = findViewById(R.id.view_selector);
        final View left = findViewById(R.id.view_container_left);
        final View right = findViewById(R.id.view_container_right);

        findViewById(R.id.btn_close).setOnClickListener((v) -> dismiss());

        if (mainTag == null) {
            mainTag = AUTO_TAG;
        }
        selector.setOnTouchListener(new OnTouchListener(mainTag));

        left.setOnDragListener(new OnDragListener(mainTag, () -> {
            dropAnimation(LEFT);
            if (onDropListener != null) {
                onDropListener.onDrop(LEFT);
            }
        }));

        right.setOnDragListener(new OnDragListener(mainTag, () -> {
            dropAnimation(RIGHT);
            if (onDropListener != null) {
                onDropListener.onDrop(RIGHT);
            }
        }));
    }

    private void dropAnimation(@Position final int position) {
        selector.startAnimation(new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                final ConstraintLayout.LayoutParams params =
                        (ConstraintLayout.LayoutParams) selector.getLayoutParams();

                if (position == LEFT) {
                    params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.endToEnd = R.id.guideline;
                } else if (position == RIGHT) {
                    params.startToStart = R.id.guideline;
                    params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                }
                selector.setLayoutParams(params);
            }
        });
    }

    public void setOnDropListener(@Nullable OnDropListener onDropListener) {
        this.onDropListener = onDropListener;
    }

    public void setTag(@NonNull String mainTag) {
        this.mainTag = mainTag;
    }

    @Nullable
    public String getTag() {
        return mainTag;
    }

    private class OnTouchListener implements View.OnTouchListener {
        @NonNull
        private final String tag;

        private OnTouchListener(@NonNull final String tag) {
            this.tag = tag;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                final ClipData.Item item = new ClipData.Item(tag);
                final String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                final ClipData data = new ClipData(tag, mimeTypes, item);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, // data to be dragged
                            new View.DragShadowBuilder(v), // drag shadow
                            v, // 드래그 드랍할  Vew
                            0 // 필요없는 플래그
                    );
                } else {
                    v.startDrag(data, // data to be dragged
                            new View.DragShadowBuilder(v), // drag shadow
                            v, // 드래그 드랍할  Vew
                            0 // 필요없는 플래그
                    );
                }
                v.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class OnDragListener implements View.OnDragListener {
        @NonNull
        private final String tag;
        @NonNull
        private final OnDropCallback callback;

        private OnDragListener(@NonNull final String tag,
                               @NonNull OnDropCallback callback) {
            this.tag = tag;
            this.callback = callback;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                final ClipData data = event.getClipData();
                final String selectorTag = data.getItemAt(0).getText().toString();
                if (selectorTag.equals(tag)) callback.onDrop();
                else return false;
            } else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                selector.setVisibility(View.VISIBLE);
            }
            return true;
        }
    }
}
