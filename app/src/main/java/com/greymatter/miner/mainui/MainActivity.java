package com.greymatter.miner.mainui;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView surface;
    private MainGLRenderer glRenderer;
    private Button left, right;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        init();

        surface = (GLSurfaceView)findViewById(R.id.mainGLSurfaceView);
        surface.setEGLContextClientVersion(3);
        surface.setEGLConfigChooser(8,8,8,8,24,8);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE;
        surface.setSystemUiVisibility(uiOptions);

        glRenderer = new MainGLRenderer();
        surface.setRenderer(glRenderer);
        setOnTouchListener();
        setOnClickListener();
    }

    private void init() {
        AppServices.init(this);
        left = findViewById(R.id.move_left);
        right = findViewById(R.id.move_right);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchListener() {
        surface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.mainGLSurfaceView:
                        MainGLTouchHelper.onTouch(event);
                        return true;
                }
                return false;
            }
        });
    }

    private void setOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGLTouchHelper.onClick(v);
            }


        };
        left.setOnClickListener(listener);
        right.setOnClickListener(listener);


    }




    @Override
    protected void onDestroy() {
        glRenderer.onDestroy();
        super.onDestroy();
    }
}
