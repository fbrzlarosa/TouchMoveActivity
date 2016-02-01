package com.touchmovesample.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.touchmoveactivity.touchmoveactivity.TouchMoveActivity;
import com.touchmoveactivity.touchmoveactivity.onMoveActivity;

/**
 * Copyright 2016 {fabrizio.larosa@meedori.com}
 * com.touchmovesample.sample Created by Fabrizio La Rosa on 01/02/16.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.open_touch_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View frame= LayoutInflater.from(MainActivity.this).inflate(R.layout.touch_move_image,null);
                TouchMoveActivity.init(frame, new onMoveActivity() {
                    @Override
                    public void onMoveActivity(float offSetMove) {
                        frame.findViewById(R.id.background_for_image).setAlpha(1 - offSetMove);
                    }
                });
                startActivity(new Intent(MainActivity.this,TouchMoveActivity.class));

            }
        });



    }
}
