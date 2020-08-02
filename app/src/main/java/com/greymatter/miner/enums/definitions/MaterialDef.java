package com.greymatter.miner.enums.definitions;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.Path;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.StaticTexturedMaterial;

import java.io.IOException;

public enum MaterialDef {
    GROUND_MATERIAL(MaterialType.STATIC_TEXTURED_MATERIAL, Path.GROUND_I),
    DIALOG_MATERIAL(MaterialType.STATIC_TEXTURED_MATERIAL, Path.DIALOG_I),
    ATMOSPHERE_MATERIAL(MaterialType.STATIC_TEXTURED_MATERIAL, Path.ATM_RADIAL_II),
    PLANET_GRASS_MATERIAL_I(MaterialType.STATIC_TEXTURED_MATERIAL, Path.GRASS_PATCH_I),
    TREE_MATERIAL(MaterialType.ANIMATED_TEXTURED_MATERIAL, Path.TREE_ANIM_I_DIR),
    GRADIENT_COLOR_MATERIAL(MaterialType.STATIC_COLORED_MATERIAL, null),
    BUTTON_MATERIAL_I(MaterialType.STATIC_TEXTURED_MATERIAL, Path.BUTTON_I),
    MAIN_BASE_MATERIAL(MaterialType.STATIC_TEXTURED_MATERIAL, Path.MAIN_BASE_FINAL);


    public final MaterialType MATERIAL_TYPE;
    public final String DIFF_PATH;
    MaterialDef(MaterialType matType, String diffusePath) {
        MATERIAL_TYPE = matType;
        DIFF_PATH = diffusePath;
    }

    public static Material create(MatId id) {
        MaterialDef matDef = MaterialDef.valueOf(id.toString());
        switch (matDef.MATERIAL_TYPE) {
            case STATIC_TEXTURED_MATERIAL:
                return new StaticTexturedMaterial(id).attachDiffuseTexture(matDef.DIFF_PATH);
            case ANIMATED_TEXTURED_MATERIAL:
                try {
                    return new AnimatedTexturedMaterial(id).addDiffuseTextureFrames(matDef.DIFF_PATH, AppServices.getAssetManager().list(matDef.DIFF_PATH));
                }catch (IOException e) {
                    e.printStackTrace();
                }
            default:
                return null;
        }
    }
}
