package com.greymatter.miner.mainui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;
import com.greymatter.miner.opengl.MainGLRenderer;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView surface;
    private MainGLRenderer glRenderer;
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
    }

    private void init() {
        AppServices.init(this);
    }

    @Override
    protected void onDestroy() {
        glRenderer.onDestroy();
        super.onDestroy();
    }
}
