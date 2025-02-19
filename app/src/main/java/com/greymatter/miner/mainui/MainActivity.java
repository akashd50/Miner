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
import com.greymatter.miner.mainui.renderers.MainGLHelper;
import com.greymatter.miner.mainui.renderers.MainGLRenderer;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView surface;
    private MainGLRenderer glRenderer;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        surface = (GLSurfaceView)findViewById(R.id.mainGLSurfaceView);
        glRenderer = new MainGLRenderer();

        init();

        surface.setEGLContextClientVersion(3);
        surface.setEGLConfigChooser(8,8,8,8,24,8);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE;
        surface.setSystemUiVisibility(uiOptions);
        surface.setRenderer(glRenderer);
        setOnTouchListener();
        setOnClickListener();
    }

    private void init() {
        AppServices.setContext(this);
        AppServices.setGLSurfaceView(surface);
        AppServices.setMainGLRenderer(glRenderer);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchListener() {
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MainGLHelper.onTouch(v, event);
                return true;
            }
        };
        surface.setOnTouchListener(onTouchListener);
    }

    private void setOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGLHelper.onClick(v);
            }
        };

        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainGLHelper.onLongClick(v);
                return false;
            }
        };

        findViewById(R.id.items_menu).setOnClickListener(listener);
        findViewById(R.id.done_building_placement).setOnClickListener(listener);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE;
        surface.setSystemUiVisibility(uiOptions);
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onDestroy() {
        glRenderer.onDestroy();
        super.onDestroy();
    }
}
