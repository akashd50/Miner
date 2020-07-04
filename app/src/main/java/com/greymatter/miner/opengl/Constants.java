package com.greymatter.miner.opengl;

public class Constants {
    public static int SIZE_OF_FLOAT = 4;

    //shader files
    public static final String SHADERS = "shaders/";
    public static final String VERTEX_SHADER = "_vs.glsl";
    public static final String FRAG_SHADER = "_fs.glsl";
    public static final String SIMPLE_TRIANGLE_SHADER = "simple_triangle";
    public static final String QUAD_SHADER = "quad";
    public static final String THREE_D_OBJECT_SHADER = "three_d_object";
    public static final String LINE_SHADER = "line";
    public static final String GRADIENT_SHADERS = "gradientshader/";
    public static final String CIRCLE_GRADIENT_SHADER = "circle_gradient";

    //textures
    public static final String TEXTURES = "textures/";
    public static final String GROUND_I = "ground_i.png";
    public static final String MAIN_BASE_P = "main_base_p.jpg";
    public static final String SCANNER_P = "scanner_p.jpg";
    public static final String ATM_RADIAL_II = "atm_radial_ii.png";
    public static final String ATM_RADIAL_I = "atm_radial_i.png";

    //obj files
    public static final String OBJECTS = "objects/";
    public static final String ATM_SIMPLE_CIRCLE = "atm_simple_circle.obj";
    public static final String CIRCLE_SUB_DIV_III = "circle_sub_div_iii.obj";
    public static final String BOX = "box.obj";
    public static final String UV_MAPPED_BOX = "uv_mapped_box.obj";
    public static final String CIRCLE_SUB_DIV_I = "circle_sub_div_i.obj";

    //shader constants
    //uniforms
    public static final String PROJECTION = "projection";
    public static final String VIEW = "view";
    public static final String MODEL = "model";
    public static final String TRANSLATION = "translation";
    public static final String U_COLOR = "u_color";
    public static final String CENTER_COLOR = "centerColor";
    public static final String EDGE_COLOR = "edgeColor";
    public static final String RADIUS = "radius";

    //attributes
    public static final String IN_POSITION = "in_position";
    public static final String IN_UV = "in_uv";
    public static final String IN_NORMAL = "in_normal";
}
