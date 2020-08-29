package com.greymatter.miner.loaders.enums.definitions;

import com.greymatter.miner.Path;
import com.greymatter.miner.containers.ShapeContainer;
import com.greymatter.miner.opengl.objects.drawables.Shape;

public enum ShapeDef {
    COLLISION_BOX(ShapeType.OBJ, Path.BOX, null,0f,0f),
    CIRCLE_SIMPLE(ShapeType.OBJ, Path.CIRCLE_SIMPLE, null, 0f,0f),
    UV_MAPPED_BOX(ShapeType.OBJ, Path.UV_MAPPED_BOX, null, 0f,0f),
    CIRCLE_SUB_III(ShapeType.OBJ, Path.CIRCLE_SUB_DIV_III, null, 0f,0f),
    CIRCLE_SUB_I(ShapeType.OBJ, Path.CIRCLE_SUB_DIV_I, null, 0f,0f),
    PIE_45(ShapeType.PIE, null, null, 45f,1f),
    CIRCLE_EDGE(ShapeType.SHAPE_OUTLINE, null, ShapeDef.CIRCLE_SUB_III, 0.01f,0f),
    //PLANET_TREE_EDGE(Path.BOX, null, 0f,0f),
    SIMPLE_QUAD(ShapeType.QUAD, null, null, 1.0f,0f);

    public final ShapeType SHAPE_TYPE;
    public final String SHAPE_PATH;
    public final ShapeDef SHAPE_PARAM_I;
    public final float FLOAT_PARAM_I;
    public final float FLOAT_PARAM_II;
    ShapeDef(ShapeType shapeType, String path, ShapeDef shapeId, float p1, float p2) {
        SHAPE_TYPE = shapeType;
        SHAPE_PATH = path;
        SHAPE_PARAM_I = shapeId;
        FLOAT_PARAM_I = p1;
        FLOAT_PARAM_II = p2;
    }

    public static Shape create(ShapeDef id) {
        switch (id.SHAPE_TYPE) {
            case OBJ:
                return new Shape(id).loadObj(id.SHAPE_PATH).build();
            case QUAD:
                return new Shape(id).loadQuad(id.FLOAT_PARAM_I).build();
            case PIE:
                return new Shape(id).loadPie(id.FLOAT_PARAM_I, id.FLOAT_PARAM_II).build();
            case SHAPE_OUTLINE:
                return new Shape(id).loadEdgeOutline(ShapeContainer.get(id.SHAPE_PARAM_I), id.FLOAT_PARAM_I).build();
        }
        return null;
    }
}
