package com.thestoryofus;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import java.lang.annotation.Inherited;

public class SwipeSensitiveListView extends ListView {

    private GestureDetectorCompat gestureDetector = new GestureDetectorCompat(getContext(), new SwipeGestureListener(this));

    public SwipeSensitiveListView(Context context) {
        super(context);
    }

    public SwipeSensitiveListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeSensitiveListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwipeSensitiveListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
