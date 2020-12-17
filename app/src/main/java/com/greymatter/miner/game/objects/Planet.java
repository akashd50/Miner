package com.greymatter.miner.game.objects;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class Planet extends GameObject {
    private static final String ATMOSPHERE = "ATMOSPHERE";
    private static final String GRASS_LAYER = "GRASS_LAYER";
    private GroupMap<String, Vector3f> resources;
    private GenericObject planetAtmosphere, planetGrassLayer;
    public Planet(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public Planet(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public Planet(String id) {
        super(id, DrawableDef.create(DrawableDef.PLANET_1));
        initialize();
    }

    private void initialize() {
        planetAtmosphere = new GenericObject(DrawableDef.create(DrawableDef.ATMOSPHERE));
        planetAtmosphere.addTag(Tag.STATIC)
                        .scaleTo(2f,2f)
                        .moveTo(0f,0f, -10f);

        planetAtmosphere.getTransforms().copyTranslationFromParent(true);
        planetAtmosphere.getTransforms().copyScaleFromParent(true);

        planetGrassLayer = new GenericObject(DrawableDef.create(DrawableDef.PLANET_GRASS_LAYER));
        planetGrassLayer.addTag(Tag.STATIC)
                        .scaleTo(0.997f,0.997f)
                        .moveTo(0f,0f, 14f);
        planetGrassLayer.getTransforms().copyTranslationFromParent(true);
        planetGrassLayer.getTransforms().copyScaleFromParent(true);

        addChild(ATMOSPHERE, planetAtmosphere);
        addChild(GRASS_LAYER, planetGrassLayer);

        addTag(Tag.STATIC);
        addTag(Tag.STATIC_PHYSICS_OBJECT);
    }

    public void addResource(String tag, Vector3f location) {
        resources.add(tag, location);
    }
}
