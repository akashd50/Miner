package com.greymatter.miner.loaders.enums.definitions;

import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShapeContainer;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;
import com.greymatter.miner.opengl.objects.drawables.Quad;
import com.greymatter.miner.opengl.objects.drawables.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.renderers.InstancedRenderer;
import com.greymatter.miner.opengl.objects.renderers.QuadLightingRenderer;
import com.greymatter.miner.opengl.objects.renderers.QuadRenderer;
import com.greymatter.miner.opengl.objects.renderers.RadialGradientRenderer;
import com.greymatter.miner.opengl.objects.renderers.Renderer;

public enum DrawableDef {
    ATMOSPHERE(DrawableType.OBJ, ShapeDef.CIRCLE_SIMPLE, MaterialDef.ATMOSPHERE_MATERIAL, ShaderDef.QUAD_SHADER),
    PLANET_1(DrawableType.OBJ, ShapeDef.CIRCLE_SUB_III, MaterialDef.GROUND_MATERIAL, ShaderDef.QUAD_SHADER),
    PLANET_GRASS_LAYER(DrawableType.TEXTURED_EDGED_POLYGON, ShapeDef.CIRCLE_EDGE, MaterialDef.PLANET_GRASS_MATERIAL_I, ShaderDef.QUAD_SHADER),
    //PLANET_TREE_LAYER(DrawableType.TEXTURED_EDGED_POLYGON, ShapeDef.PLANET_TREE_EDGE, MaterialDef.TREE_MATERIAL, ShaderDef.THREE_D_OBJECT_W_LIGHTING_SHADER),

    MAIN_BASE(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.MAIN_BASE_MATERIAL, ShaderDef.LIGHTING_SHADER),
    MAIN_BASE_LIGHT_I(DrawableType.OBJ, null, null, null),

    MAIN_CHARACTER(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.GROUND_MATERIAL, ShaderDef.QUAD_SHADER),
    TEST_OBJ_I(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.GROUND_MATERIAL, ShaderDef.QUAD_SHADER),
    TEST_OBJ_II(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.GROUND_MATERIAL, ShaderDef.QUAD_SHADER),

    TEST_LINE(DrawableType.LINE, null, null, ShaderDef.LINE_SHADER),
    TREE_I(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.TREE_MATERIAL, ShaderDef.QUAD_SHADER),

    SCANNER_1(DrawableType.OBJ, ShapeDef.CIRCLE_SUB_I, MaterialDef.GROUND_MATERIAL, ShaderDef.QUAD_SHADER),
    MINER_1(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.MINER_MATERIAL, ShaderDef.QUAD_SHADER),
    MINE_1(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.MINE_MATERIAL_I, ShaderDef.QUAD_SHADER),
    PIE_GRADIENT_I(DrawableType.RADIANT_GRADIENT, ShapeDef.PIE_45, MaterialDef.GRADIENT_COLOR_MATERIAL, ShaderDef.GRADIENT_SHADER),

    OIL_DRILL(DrawableType.OBJ, ShapeDef.SIMPLE_QUAD, MaterialDef.OIL_DRILL_MATERIAL, ShaderDef.QUAD_SHADER),
    OIL_DEPOSIT_OUTER(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.OIL_DEP_OUTER_MATERIAL, ShaderDef.QUAD_SHADER),
    OIL_DEPOSIT_INNER(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.OIL_DEP_INNER_MATERIAL, ShaderDef.QUAD_SHADER),

    DIALOG_BUTTON_I(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.BUTTON_MATERIAL_I, ShaderDef.LIGHTING_SHADER),
    DIALOG_BUTTON_II(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.BUTTON_MATERIAL_I, ShaderDef.LIGHTING_SHADER),
    DIALOG_BUTTON_III(DrawableType.OBJ, ShapeDef.UV_MAPPED_BOX, MaterialDef.BUTTON_MATERIAL_I, ShaderDef.LIGHTING_SHADER),

    OBJECT_DIALOG(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.DIALOG_MATERIAL, ShaderDef.QUAD_SHADER),
    OBJECT_SIGNAL(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.SIGNAL_MATERIAL, ShaderDef.LIGHTING_SHADER),

    GAME_PAD_FRONT(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.GAME_PAD_FRONT_MATERIAL, ShaderDef.QUAD_SHADER),
    GAME_PAD_BACKGROUND(DrawableType.QUAD, ShapeDef.SIMPLE_QUAD, MaterialDef.GAME_PAD_FRONT_MATERIAL, ShaderDef.QUAD_SHADER),

    PIPES_INSTANCE_GROUP(DrawableType.INSTANCE_GROUP, null, MaterialDef.GROUND_MATERIAL, ShaderDef.INSTANCE_SHADER),
    JOINTS_INSTANCE_GROUP(DrawableType.INSTANCE_GROUP, null, MaterialDef.ADD_MARKER_MATERIAL, ShaderDef.INSTANCE_SHADER),

    COAL_BLOCK_I(DrawableType.OBJ, ShapeDef.COLLISION_BOX, MaterialDef.ROCK_MATERIAL_I, ShaderDef.QUAD_SHADER);

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

    public static Drawable create(DrawableDef id) {
        DrawableDef objDef = DrawableDef.valueOf(id.toString());
        switch (objDef.DRAWABLE_TYPE) {
            case OBJ:
                return new Obj(id.name()).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setRenderer(getRenderer(objDef)).build();
            case QUAD:
                return new Quad(id.name()).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setRenderer(getRenderer(objDef)).build();
            case RADIANT_GRADIENT:
                return new RadialGradient(id.name()).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setRenderer(getRenderer(objDef)).build();
            case TEXTURED_EDGED_POLYGON:
                return new TextureEdgedPolygon(id.name()).setShape(ShapeContainer.get(objDef.SHAPE_ID))
                        .setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setRenderer(getRenderer(objDef)).build();
            case INSTANCE_GROUP:
                return new InstanceGroup(id.name()).setMaterial(MaterialContainer.get(objDef.MAT_ID))
                        .setRenderer(getRenderer(objDef)).build();
            case LINE:

        }
        return null;
    }

    private static Renderer getRenderer(DrawableDef drawableDef) {
        switch (drawableDef.SHADER_ID) {
            case QUAD_SHADER:
                return new QuadRenderer();
            case LIGHTING_SHADER:
                return new QuadLightingRenderer();
            case GRADIENT_SHADER:
                return new RadialGradientRenderer();
            case INSTANCE_SHADER:
                return new InstancedRenderer();
            default:
                return null;
        }
    }
}
