package com.touchmoveactivity.touchmoveactivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Copyright 2016 {fabrizio.larosa@meedori.com}
 * com.swipeimageview.swipeimageview Created by Fabrizio La Rosa on 01/02/16.
 */
public class TouchMoveActivity extends Activity implements View.OnTouchListener{

    private static onMoveActivity listener_move;
    private int layoutPosition = 0;
    private int defaultViewHeight;
    private float offsetClose=1.6f;

    private boolean closing = false;
    private boolean scrollingUp = false;
    private boolean scrollingDown = false;
    private int prevFingerPosition = 0;

    private static View baseLayout;
    public static void init(View frontView){
        baseLayout=frontView;
    }
    public static void init(View frontView,onMoveActivity listener){
        baseLayout=frontView;
        listener_move=listener;
    }
    public static void setOnMoveActivityListener(onMoveActivity listener){
        listener_move=listener;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseLayout=null;
    }
    private FrameLayout layout_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ThemeTouchMoveActivity_Transparent);
        if(baseLayout!=null) {
            layout_root=new FrameLayout(this);
            layout_root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            layout_root.addView(baseLayout);
            setContentView(layout_root);
            layout_root.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (listener_move != null) {
                        if (Math.abs(v.getY()) < v.getHeight()) {
                            listener_move.onMoveActivity(1 - (Math.abs((v.getHeight() - Math.abs(v.getY())) / v.getHeight())));
                        } else {
                            listener_move.onMoveActivity(1);

                        }
                    }
                }
            });
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layout_root.setOnTouchListener(this);
        }
        else{
            Log.e("TouchMOveActivity","No BaseLayout found, try with TouchMoveActivity.init() to add a layout");
            finish();
        }
    }
    long timeTouchDown=0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                timeTouchDown = System.currentTimeMillis();
                defaultViewHeight = layout_root.getHeight();
                prevFingerPosition = Y;
                layoutPosition = (int) layout_root.getY();
                break;
            case MotionEvent.ACTION_UP:
                int currentYPositionUP = (int) layout_root.getY();
                if (scrollingUp) {
                    if ((System.currentTimeMillis() - timeTouchDown) < 100) {
                        if ((layoutPosition - currentYPositionUP) > 100) {
                            closeUpAndDismissDialog(currentYPositionUP);
                            return true;
                        }
                    }
                    if ((layoutPosition - currentYPositionUP) > defaultViewHeight / offsetClose) {
                        closeUpAndDismissDialog(currentYPositionUP);
                        return true;
                    }
                } else {
                    if ((System.currentTimeMillis() - timeTouchDown) < 100) {
                        if (Math.abs(layoutPosition - currentYPositionUP) > 100) {
                            closeDownAndDismissDialog(currentYPositionUP);
                            return true;
                        }
                    }
                    if (Math.abs(layoutPosition - currentYPositionUP) > defaultViewHeight / offsetClose) {
                        closeDownAndDismissDialog(currentYPositionUP);
                        return true;
                    }
                }
                if (scrollingUp) {
                    int currentYPosition = (int) layout_root.getY();
                    returnToPosition(currentYPosition);
                    scrollingUp = false;
                }
                if (scrollingDown) {
                    int currentYPosition = (int) layout_root.getY();
                    returnToPosition(currentYPosition);
                    layout_root.getLayoutParams().height = defaultViewHeight;
                    layout_root.requestLayout();
                    scrollingDown = false;
                }
                timeTouchDown = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!closing) {
                    int YPosition = (int) baseLayout.getY();
                    if (prevFingerPosition > Y) {
                        if (!scrollingUp) {
                            scrollingUp = true;
                        }
                        layout_root.setY(layout_root.getY() + (Y - prevFingerPosition));
                        layout_root.requestLayout();
                    }
                    else {
                            if (!scrollingDown) {
                                scrollingDown = true;
                            }
                            layout_root.setY(layout_root.getY() + (Y - prevFingerPosition));
                            layout_root.requestLayout();
                        }
                        prevFingerPosition = Y;
                    }
                    break;
                }
                return true;

    }


    private void returnToPosition(int currentPosition){
        timeTouchDown=0;
        closing = false;
        ValueAnimator positionAnimator= ValueAnimator.ofFloat(currentPosition,0);
        positionAnimator.setDuration(300);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();
                layout_root.setY(value);
                layout_root.requestLayout();
            }
        });
        positionAnimator.start();
    }
    private void closeUpAndDismissDialog(int currentPosition){
        timeTouchDown=0;
        closing = true;
        ValueAnimator positionAnimator= ValueAnimator.ofFloat(currentPosition,-layout_root.getHeight());
        positionAnimator.setDuration(300);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();
                layout_root.setY(value);
                layout_root.requestLayout();
            }
        });
        positionAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        positionAnimator.start();
    }
    private void closeDownAndDismissDialog(int currentPosition){
        timeTouchDown=0;
        closing = true;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        ValueAnimator positionAnimator= ValueAnimator.ofFloat(currentPosition,screenHeight+layout_root.getHeight());
        positionAnimator.setDuration(300);

        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();
                layout_root.setY(value);
                layout_root.requestLayout();

            }
        });
        positionAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        positionAnimator.start();
    }
}
