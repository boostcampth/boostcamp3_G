package com.boostcamp.dreampicker.view.feed.drag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.MotionEvent;
import android.view.View;

public class VoteIconTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            // 태그 생성
            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

            v.startDrag(data, // data to be dragged
                    new View.DragShadowBuilder(v), // drag shadow
                    v, // 드래그 드랍할  Vew
                    0 // 필요없는 플래그
            );
            v.setVisibility(View.INVISIBLE);

            return true;
        } else {
            return false;
        }
    }
}