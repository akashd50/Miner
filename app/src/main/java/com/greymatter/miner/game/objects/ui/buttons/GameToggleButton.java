package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.ConfirmationDialog;
import com.greymatter.miner.game.objects.ui.GroupButtonsMenu;
import com.greymatter.miner.game.objects.ui.GameNotification;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.materials.Material;

public abstract class GameToggleButton extends GGroupButton {
    private static final String CONFIRMATION_DIALOG = "CONFIRMATION_DIALOG";

    private boolean toggle;
    private Material onMaterial, offMaterial;

    private ConfirmationDialog confirmationDialog;
    public GameToggleButton(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public GameToggleButton(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        confirmationDialog = new ConfirmationDialog(CONFIRMATION_DIALOG);
        addChild(CONFIRMATION_DIALOG, confirmationDialog);
        confirmationDialog.hide();
        //confirmationDialog.linkTo(GameNotification.TOP);
    }

    @Override
    public GameObject shouldDraw(boolean shouldDraw) {
        return super.shouldDraw(shouldDraw);
    }

    @Override
    public boolean onClick(IGameObject object) {
        ((GroupButtonsMenu)this.getParent()).clearSelectionExcept(this);
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
            confirmationDialog.show();
        }else{
            getDrawable().setMaterial(offMaterial);
            confirmationDialog.hide();
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
