package com.boostcamp.dreampicker.view.feed.drag;

import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.boostcamp.dreampicker.R;
import com.sackcentury.shinebuttonlib.ShineButton;

public class VoteContainerDragListener implements View.OnDragListener {
    private final OnDropListener onDropListener;

    public VoteContainerDragListener(OnDropListener onDropListener) {
        this.onDropListener = onDropListener;
    }

    public boolean onDrag(View v, DragEvent event) {
        final View view = (View) event.getLocalState(); // <- 버튼 정보
        final ShineButton button = (ShineButton) view;
        final ViewGroup viewgroup = (ViewGroup) button.getParent(); // <- Constraint 레이아웃 정보
        final FrameLayout container = (FrameLayout) v;

        final String buttonTag = button.getTag().toString();
        final String containerTag = container.getTag().toString();

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
                if(buttonTag.equals(containerTag)) {
                    int iconSize = v.getResources().getDimensionPixelSize(R.dimen.vote_icon_size);

                    final FrameLayout.LayoutParams params =
                            new FrameLayout.LayoutParams(iconSize, iconSize, Gravity.CENTER);

                    viewgroup.removeView(button);
                    button.setLayoutParams(params);
                    container.addView(button);

                    if (!button.isChecked()) {
                        button.performClick();
                    } else {
                        button.performClick();
                        button.performClick();
                    }

                    onDropListener.onDrop(buttonTag);
                    break;
                }
                else {
                    return false;
                }
            case DragEvent.ACTION_DRAG_ENDED:
                button.setVisibility(View.VISIBLE);
        }
        return true;
    }
}

