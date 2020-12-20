package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.ui.ConfirmationDialog;
import com.greymatter.miner.game.objects.ui.ContextMenu;
import com.greymatter.miner.game.objects.ui.GameNotification;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.materials.Material;

public abstract class GameToggleButton extends GameButton {
    private static final String CONFIRMATION_DIALOG = "CONFIRMATION_DIALOG";

    private boolean toggle;
    private Material onMaterial, offMaterial;
    private GameBuilding actionObject;

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
        confirmationDialog.linkTo(GameNotification.BOTTOM);
    }

    @Override
    public GameObject shouldDraw(boolean shouldDraw) {
        return super.shouldDraw(shouldDraw);
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
            confirmationDialog.show();
        }else{
            getDrawable().setMaterial(offMaterial);
            confirmationDialog.hide();
        }
        return this;
    }

    public GameBuilding getActionObject() {
        return actionObject;
    }

    public GameToggleButton setActionObject(GameBuilding actionObject) {
        this.actionObject = actionObject;
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
