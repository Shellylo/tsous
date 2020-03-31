package com.thestoryofus;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final String TAG = "SwipeGestureListener";

    private static final int SWIPE_X_DISTANCE_THRESHOLD = 100;

    private ListView list;

    public SwipeGestureListener(ListView list) {
        this.list = list;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown called");
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // TODO: add check on velocity?
        boolean swipeDetected = false;
        float x_distance = e1.getX() - e2.getX();

        // Swipe detected - distance between gestures passes constant threshold
        if (Math.abs(x_distance) > SWIPE_X_DISTANCE_THRESHOLD) {

            // Right swipe - x_distance is positive value
            if (x_distance > 0) {
                Log.i(TAG, "Right swipe detected");
            }
            else {
                Log.i(TAG, "Left swipe detected");
            }

         /*   int pos = this.list.pointToPosition((int)e1.getX(), (int)e1.getY());
            View child = list.getChildAt(pos);
            if (child != null) {
                child.setVisibility(View.GONE);
            }*/

            swipeDetected = true;
        }

        return swipeDetected;
    }

}
