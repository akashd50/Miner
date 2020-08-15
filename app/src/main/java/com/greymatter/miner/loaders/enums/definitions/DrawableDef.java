package com.greymatter.miner.loaders.enums.definitions;

import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ShapeContainer;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Quad;
import com.greymatter.miner.opengl.objects.drawables.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;

public enum DrawableDef {
    ATMOSPHERE(DrawableType.OBJ, ShapeDef.CIRCLE_SIMPLE, MaterialDef.ATMOSPHERE_MATERIAL, ShaderDef.LIGHTING_SHADER),
    PLANET(DrawableType.OBJ, ShapeDef.CIRCLE_SUB_III, MaterialDef.GROUND_MATERIAL, ShaderDef.QUAD_SHADER),
    PLANET_GRASS_LAYER(DrawableType.TEXTURED_EDGED_POLYGON, ShapeDef.CIRCLE_EDGE, MaterialDef.PLANET_GRASS_MATERIAL_I, ShaderDef.QUAD_SHADER),
    //PLANET_TREE_LAYER(DrawableType.TEXTURED_EDGED_POLYGON, ShapeDef.PLANET_TREE_EDGE, MaterialDef.TREE_MATERIAL, ShaderDef.THREE_D_OBJECT_W_LIGHTING_SHADER),

    MAIN_BASE(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.MAIN_BASE_MATERIAL, ShaderDef.LIGHTING_SHADER),
    MAIN_BASE_LIGHT_I(DrawableType.OBJ, null, null, null),

    MAIN_CHARACTER(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.GROUND_MATERIAL, ShaderDef.LIGHTING_SHADER),
    TEST_OBJ_I(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.GROUND_MATERIAL, ShaderDef.LIGHTING_SHADER),
    TEST_OBJ_II(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.GROUND_MATERIAL, ShaderDef.LIGHTING_SHADER),

    TEST_LINE(DrawableType.LINE, null, null, ShaderDef.LINE_SHADER),
    TREE_I(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.TREE_MATERIAL, ShaderDef.LIGHTING_SHADER),

    SCANNER_I(DrawableType.OBJ, ShapeDef.CIRCLE_SUB_I, MaterialDef.GROUND_MATERIAL, ShaderDef.QUAD_SHADER),
    PIE_GRADIENT_I(DrawableType.RADIANT_GRADIENT, ShapeDef.PIE_45, MaterialDef.GRADIENT_COLOR_MATERIAL, ShaderDef.GRADIENT_SHADER),

    DIALOG_BUTTON_I(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.BUTTON_MATERIAL_I, ShaderDef.LIGHTING_SHADER),
    DIALOG_BUTTON_II(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.BUTTON_MATERIAL_I, ShaderDef.LIGHTING_SHADER),
    DIALOG_BUTTON_III(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.BUTTON_MATERIAL_I, ShaderDef.LIGHTING_SHADER),

    OBJECT_DIALOG(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.DIALOG_MATERIAL, ShaderDef.QUAD_SHADER),
    OBJECT_SIGNAL(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.SIGNAL_MATERIAL, ShaderDef.LIGHTING_SHADER),

    COAL_BLOCK_I(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.BUTTON_MATERIAL_I, ShaderDef.THREE_D_OBJECT_SHADER);

    public final DrawableType DRAWABLE_TYPE;
    public final ShapeDef SHAPE_ID;
    public final MaterialDef MAT_ID;
    public final ShaderDef SHADER_ID;
    DrawableDef(DrawableType drawableType, ShapeDef shapeId, MaterialDef matId, ShaderDef shaderId) {
        DRAWABLE_TYPE = drawableType;
        SHAPE_ID = shapeId;
        MAT_ID = matId;
        SHADER_ID = shaderId;
    }

    public static Drawable create(ObjId id) {
        DrawableDef objDef = DrawableDef.valueOf(id.toString());
        switch (objDef.DRAWABLE_TYPE) {
            case OBJ:
                return new Obj(id).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setShader(ShaderContainer.get(objDef.SHADER_ID)).build();
            case QUAD:
                return new Quad(id).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setShader(ShaderContainer.get(objDef.SHADER_ID)).build();
            case RADIANT_GRADIENT:
                return new RadialGradient(id).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setShader(ShaderContainer.get(objDef.SHADER_ID)).build();
            case TEXTURED_EDGED_POLYGON:
                return new TextureEdgedPolygon(id).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setShader(ShaderContainer.get(objDef.SHADER_ID)).build();
            case LINE:

        }
        return null;
    }
}
