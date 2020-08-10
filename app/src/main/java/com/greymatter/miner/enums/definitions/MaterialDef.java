package com.greymatter.miner.enums.definitions;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.Path;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.StaticTexturedMaterial;
import java.io.IOException;
import static com.greymatter.miner.enums.definitions.MaterialType.*;

public enum MaterialDef {
    GROUND_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.GROUND_I, null),
    DIALOG_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.DIALOG_I, null),
    ATMOSPHERE_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.ATM_RADIAL_II, null),
    PLANET_GRASS_MATERIAL_I(STATIC_TEXTURED_MATERIAL, Path.GRASS_PATCH_I, null),
    TREE_MATERIAL(ANIMATED_TEXTURED_MATERIAL, Path.TREE_ANIM_I_DIR, null),
    GRADIENT_COLOR_MATERIAL(STATIC_COLORED_MATERIAL, null, null),
    BUTTON_MATERIAL_I(STATIC_TEXTURED_MATERIAL, Path.BUTTON_I, null),
    MAIN_BASE_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.MAIN_BASE_FINAL, null),
    SIGNAL_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.SIGNAL_I, Path.SIGNAL_EXCL);


    public final MaterialType MATERIAL_TYPE;
    public final String MAIN_TEX_PATH;
    public final String LIGHT_TEX_PATH;
    MaterialDef(MaterialType matType, String mainTexPath, String lightTexPath) {
        MATERIAL_TYPE = matType;
        MAIN_TEX_PATH = mainTexPath;
        LIGHT_TEX_PATH = lightTexPath;
    }

    public static Material create(MaterialDef id) {
        switch (id.MATERIAL_TYPE) {
            case STATIC_TEXTURED_MATERIAL:
                return new StaticTexturedMaterial(id).attachMainTexture(id.MAIN_TEX_PATH);
            case ANIMATED_TEXTURED_MATERIAL:
                try {
                    return new AnimatedTexturedMaterial(id).addMainTextureFrames(id.MAIN_TEX_PATH, AppServices.getAssetManager().list(id.MAIN_TEX_PATH));
                }catch (IOException e) {
                    e.printStackTrace();
                }
            case STATIC_COLORED_MATERIAL:
                return new StaticColoredMaterial(id);
            default:
                return null;
        }
    }

    public static void loadAll() {
        MaterialDef[] ids = MaterialDef.values();
        for (int i = 0; i < ids.length; i++) {
            MaterialContainer.add(create(ids[i]));
        }
    }
}
