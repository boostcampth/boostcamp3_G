package com.boostcamp.dreampicker.view.feed.drag;

import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.boostcamp.dreampicker.R;
import com.sackcentury.shinebuttonlib.ShineButton;

import androidx.annotation.NonNull;

public class VoteContainerDragListener implements View.OnDragListener {
    private final String feedKey;
    private OnDropListener onDropListener;

    public VoteContainerDragListener(@NonNull String feedKey) {
        this.feedKey = feedKey;
    }

    public void setOnDropListener(OnDropListener onDropListener) {
        this.onDropListener = onDropListener;
    }

    public boolean onDrag(View v, DragEvent event) {
        View view = (View) event.getLocalState();
        ShineButton button = (ShineButton) view;
        ViewGroup viewgroup = (ViewGroup) button.getParent();
        FrameLayout containView = (FrameLayout) v;

        // 이벤트 시작
        switch (event.getAction()) {
            // 이미지를 드래그 시작될때
            case DragEvent.ACTION_DRAG_STARTED:
                break;

            // 드래그한 이미지를 옮길려는 지역으로 들어왔을때
            case DragEvent.ACTION_DRAG_ENTERED:
                break;

            // 드래그한 이미지가 영역을 빠져 나갈때
            case DragEvent.ACTION_DRAG_EXITED:
                return false;

            // 이미지를 드래그해서 드랍시켰을때
            case DragEvent.ACTION_DROP :
                if(button.getTag().toString().equals(feedKey)) {
                    int iconSize = v.getResources().getDimensionPixelSize(R.dimen.vote_icon_size);

                    FrameLayout.LayoutParams params =
                            new FrameLayout.LayoutParams(iconSize, iconSize, Gravity.CENTER);

                    viewgroup.removeView(button);
                    button.setLayoutParams(params);
                    containView.addView(button);

                    if(!button.isChecked()) {
                        button.performClick();
                    } else {
                        button.performClick();
                        button.performClick();
                    }

                    onDropListener.onDrop();
                } else {
                    return false;
                }
                break;

            case DragEvent.ACTION_DRAG_ENDED :
                button.setVisibility(View.VISIBLE);
        }
        return true;
    }
}

