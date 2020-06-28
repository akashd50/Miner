package com.greymatter.miner.mainui.touch.touchviewmodes;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class TouchEventBundle {
    private Drawable drawable;

    public TouchEventBundle setDrawable(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
