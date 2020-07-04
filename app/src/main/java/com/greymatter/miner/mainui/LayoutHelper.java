package com.greymatter.miner.mainui;

import android.view.View;
import com.greymatter.miner.AppServices;

public class LayoutHelper {
    public static void hide(int viewID) {
        View generalModeLL = AppServices.getAppContextAsActivity().findViewById(viewID);
        generalModeLL.setVisibility(View.INVISIBLE);
    }

    public static void show(int viewID) {
        View generalModeLL = AppServices.getAppContextAsActivity().findViewById(viewID);
        generalModeLL.setVisibility(View.VISIBLE);
    }
}
