package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.ContextMenu;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.materials.Material;

public class GameToggleButton extends GameButton {
    private boolean toggle;
    private Material onMaterial, offMaterial;
    public GameToggleButton(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public GameToggleButton(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
    }

    @Override
    public boolean onClick(IGameObject object) {
        ((ContextMenu)this.getParent()).clearSelectionExcept(this);
        setToggle(!toggle);
        return true;
    }

    public boolean isToggle() {
        return toggle;
    }

    public GameToggleButton setToggle(boolean toggle) {
        this.toggle = toggle;
        if (toggle) {
            getDrawable().setMaterial(onMaterial);
        }else{
            getDrawable().setMaterial(offMaterial);
        }
        return this;
    }

    public Material getOnMaterial() {
        return onMaterial;
    }

    public GameToggleButton setOnMaterial(Material onMaterial) {
        this.onMaterial = onMaterial;
        return this;
    }

    public Material getOffMaterial() {
        return offMaterial;
    }

    public GameToggleButton setOffMaterial(Material offMaterial) {
        this.offMaterial = offMaterial;
        return this;
    }

    public void clearSelection() {
        setToggle(false);
    }
}
