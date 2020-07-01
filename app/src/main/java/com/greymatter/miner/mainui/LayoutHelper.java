package com.greymatter.miner.mainui;

import android.view.View;
import android.widget.LinearLayout;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;

public class LayoutHelper {
    public static void hide(int viewID) {
        LinearLayout generalModeLL = AppServices.getAppContextAsActivity().findViewById(viewID);
        generalModeLL.setVisibility(View.INVISIBLE);
    }

    public static void show(int viewID) {
        LinearLayout generalModeLL = AppServices.getAppContextAsActivity().findViewById(viewID);
        generalModeLL.setVisibility(View.VISIBLE);
    }
}
