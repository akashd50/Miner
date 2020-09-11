package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;

public class GamePad extends GameObject {
    private GenericObject padBackground;
    public GamePad(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public GamePad(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {
        this.addTag(Tag.UI_OBJECT);
        this.moveTo(0f,0f, ZHelper.OVER_FRONT+1f);

        this.padBackground = new GenericObject(DrawableDef.create(DrawableDef.GAME_PAD_BACKGROUND));
        this.padBackground.moveTo(0f,0f,ZHelper.OVER_FRONT);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
                return true;
            }

            @Override
            public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
                OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
                return true;
            }

            @Override
            public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
                return false;
            }
        });
    }
}
