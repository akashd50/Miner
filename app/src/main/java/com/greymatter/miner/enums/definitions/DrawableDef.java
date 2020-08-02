package com.greymatter.miner.enums.definitions;

import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ShapeContainer;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.ShaderId;
import com.greymatter.miner.enums.ShapeId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Quad;
import com.greymatter.miner.opengl.objects.drawables.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;

public enum DrawableDef {
    ATMOSPHERE(DrawableType.OBJ, ShapeId.CIRCLE_SIMPLE, MatId.ATMOSPHERE_MATERIAL, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),
    PLANET(DrawableType.OBJ, ShapeId.CIRCLE_SUB_III, MatId.GROUND_MATERIAL, ShaderId.QUAD_SHADER),
    PLANET_GRASS_LAYER(DrawableType.TEXTURED_EDGED_POLYGON, ShapeId.CIRCLE_EDGE, MatId.PLANET_GRASS_MATERIAL_I, ShaderId.QUAD_SHADER),
    PLANET_TREE_LAYER(DrawableType.TEXTURED_EDGED_POLYGON, ShapeId.PLANET_TREE_EDGE, MatId.TREE_MATERIAL, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),

    MAIN_BASE(DrawableType.OBJ, ShapeId.UV_MAPPED_BOX, MatId.MAIN_BASE_MATERIAL, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),
    MAIN_BASE_LIGHT_I(DrawableType.OBJ, null, null, null),

    MAIN_CHARACTER(DrawableType.OBJ, ShapeId.COLLISION_BOX, MatId.GROUND_MATERIAL, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),

    TEST_LINE(DrawableType.LINE, null, null, ShaderId.LINE_SHADER),
    TREE_I(DrawableType.OBJ, ShapeId.UV_MAPPED_BOX, MatId.TREE_MATERIAL, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),

    SCANNER_I(DrawableType.OBJ, ShapeId.CIRCLE_SUB_I, MatId.GROUND_MATERIAL, ShaderId.QUAD_SHADER),
    PIE_GRADIENT_I(DrawableType.RADIANT_GRADIENT, ShapeId.PIE_45, MatId.GRADIENT_COLOR_MATERIAL, ShaderId.CIRCLE_GRADIENT_SHADER),

    NOT_BUTTON_I(DrawableType.OBJ, ShapeId.UV_MAPPED_BOX, MatId.BUTTON_MATERIAL_I, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),
    NOT_BUTTON_II(DrawableType.OBJ, ShapeId.UV_MAPPED_BOX, MatId.BUTTON_MATERIAL_I, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),
    NOT_BUTTON_III(DrawableType.OBJ, ShapeId.UV_MAPPED_BOX, MatId.BUTTON_MATERIAL_I, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER),
    OBJECT_NOTIFICATION(DrawableType.QUAD, ShapeId.SIMPLE_QUAD, MatId.DIALOG_MATERIAL, ShaderId.QUAD_SHADER),

    COAL_BLOCK_I(DrawableType.OBJ, ShapeId.COLLISION_BOX, MatId.GROUND_MATERIAL, ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER);

    public final DrawableType DRAWABLE_TYPE;
    public final ShapeId SHAPE_ID;
    public final MatId MAT_ID;
    public final ShaderId SHADER_ID;
    DrawableDef(DrawableType drawableType, ShapeId shapeId, MatId matId, ShaderId shaderId) {
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
