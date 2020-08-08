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
    GROUND_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.GROUND_I),
    DIALOG_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.DIALOG_I),
    ATMOSPHERE_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.ATM_RADIAL_II),
    PLANET_GRASS_MATERIAL_I(STATIC_TEXTURED_MATERIAL, Path.GRASS_PATCH_I),
    TREE_MATERIAL(ANIMATED_TEXTURED_MATERIAL, Path.TREE_ANIM_I_DIR),
    GRADIENT_COLOR_MATERIAL(STATIC_COLORED_MATERIAL, null),
    BUTTON_MATERIAL_I(STATIC_TEXTURED_MATERIAL, Path.BUTTON_I),
    MAIN_BASE_MATERIAL(STATIC_TEXTURED_MATERIAL, Path.MAIN_BASE_FINAL);


    public final MaterialType MATERIAL_TYPE;
    public final String DIFF_PATH;
    MaterialDef(MaterialType matType, String diffusePath) {
        MATERIAL_TYPE = matType;
        DIFF_PATH = diffusePath;
    }

    public static Material create(MaterialDef id) {
        switch (id.MATERIAL_TYPE) {
            case STATIC_TEXTURED_MATERIAL:
                return new StaticTexturedMaterial(id).attachDiffuseTexture(id.DIFF_PATH);
            case ANIMATED_TEXTURED_MATERIAL:
                try {
                    return new AnimatedTexturedMaterial(id).addDiffuseTextureFrames(id.DIFF_PATH, AppServices.getAssetManager().list(id.DIFF_PATH));
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
