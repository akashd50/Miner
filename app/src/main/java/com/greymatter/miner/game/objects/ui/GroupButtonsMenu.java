package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.buttons.GGroupButton;
import com.greymatter.miner.game.objects.ui.buttons.GMoveButton;
import com.greymatter.miner.game.objects.ui.buttons.GUpgradeButton;
import com.greymatter.miner.game.objects.ui.buttons.GameButton;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GroupButtonsMenu extends ButtonsMenu {
    public GroupButtonsMenu(String id, Drawable drawable) {
        super(id, drawable);
    }

    public GroupButtonsMenu(String id) {
        super(id);
    }

    public GGroupButton addMoveButton() {
        GMoveButton button = new GMoveButton(BUTTON + getNextButtonID());
        super.addNewButton(button);
        return button;
    }

    public GGroupButton addUpgradeButton() {
        GUpgradeButton button = new GUpgradeButton(BUTTON + getNextButtonID());
        super.addNewButton(button);
        return button;
    }

    public GMoveButton getMoveButton() {
        for (IGameObject child : getChildren().toList()) {
            if (child instanceof GMoveButton) {
                return (GMoveButton) child;
            }
        }
        return null;
    }

    public GUpgradeButton getUpgradeButton() {
        for (IGameObject child : getChildren().toList()) {
            if (child instanceof GUpgradeButton) {
                return (GUpgradeButton) child;
            }
        }
        return null;
    }

    public void clearSelectionExcept(GameButton exceptThis) {
        for(IGameObject button : getChildren().toList()) {
            if (button.getId().compareTo(exceptThis.getId()) != 0) {
                ((GGroupButton) button).clearSelection();
            }
        }
    }

    public int getNumButtons() {
        return (int) getChildren().toList().stream().filter(child -> ((GGroupButton) child).isApplicable()).count();
    }

    @Override
    public boolean shouldAdd(GameButton gameButton) {
        return ((GGroupButton)gameButton).isApplicable();
    }
}
