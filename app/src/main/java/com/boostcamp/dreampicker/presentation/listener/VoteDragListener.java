package com.boostcamp.dreampicker.presentation.listener;

import android.view.DragEvent;
import android.view.View;

import com.sackcentury.shinebuttonlib.ShineButton;

import androidx.annotation.NonNull;

public class VoteDragListener implements View.OnDragListener {
    public interface DropCallback {
        void onDrop();
    }

    private final DropCallback dropCallback;

    public VoteDragListener(@NonNull DropCallback dropCallback) {
        this.dropCallback = dropCallback;
    }

    public boolean onDrag(View v, DragEvent event) {
        final ShineButton button = (ShineButton) event.getLocalState();
        final String buttonTag = button.getTag().toString();
        final String containerTag = v.getTag().toString();

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
                    dropCallback.onDrop();
                    break;
                } else {
                    return false;
                }
            case DragEvent.ACTION_DRAG_ENDED:
                button.setVisibility(View.VISIBLE);
        }
        return true;
    }
}

