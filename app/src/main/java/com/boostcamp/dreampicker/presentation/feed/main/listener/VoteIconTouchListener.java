package com.boostcamp.dreampicker.presentation.feed.main.listener;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

import com.boostcamp.dreampicker.R;

@SuppressLint("ClickableViewAccessibility")
public class VoteIconTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            // 태그 생성
            final ClipData.Item item = new ClipData.Item("");
            final String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            final ClipData data = new ClipData(v.getTag(R.id.sb_selector).toString(), mimeTypes, item);

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