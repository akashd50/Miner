package com.greymatter.miner;

public class Path {
    public static int SIZE_OF_FLOAT = 4;
    public static final String SLASH =              "/";

    //shader files
    public static final String SHADERS_DIR =                        "shaders";
    public static final String GRADIENT_SHADERS_DIR =               "gradientshader";
    public static final String VERTEX_SHADER_EXT =                  "_vs.glsl";
    public static final String FRAG_SHADER_EXT =                    "_fs.glsl";
    public static final String SIMPLE_TRIANGLE_SHADER =             SHADERS_DIR + SLASH + "simple_triangle";
    public static final String QUAD_SHADER =                        SHADERS_DIR + SLASH + "quad";
    public static final String THREE_D_OBJECT_SHADER =              SHADERS_DIR + SLASH + "three_d_object";
    public static final String LIGHTING_SHADER =   SHADERS_DIR + SLASH + "three_d_object_w_lighting";
    public static final String LINE_SHADER =                        SHADERS_DIR + SLASH + "line";
    public static final String GRADIENT_SHADER =             SHADERS_DIR + SLASH + GRADIENT_SHADERS_DIR + SLASH + "circle_gradient";

    //textures
    public static final String TEXTURES_DIR =       "textures";
    public static final String TREE_ANIM_I_DIR =    TEXTURES_DIR + SLASH + "tree_anim_i";
    public static final String GROUND_I =           TEXTURES_DIR + SLASH + "ground_i.png";
    public static final String DIALOG_I =           TEXTURES_DIR + SLASH + "dialog_i.png";
    public static final String SIGNAL_I =           TEXTURES_DIR + SLASH + "signal_i.png";
    public static final String SIGNAL_EXCL =        TEXTURES_DIR + SLASH + "signal_excl.png";
    public static final String HARMAN =             TEXTURES_DIR + SLASH + "harman.jpg";
    public static final String MAIN_BASE_P =        TEXTURES_DIR + SLASH + "main_base_p.jpg";
    public static final String MAIN_BASE_FINAL =    TEXTURES_DIR + SLASH + "main_base.png";
    public static final String SPACE_SHUTTLE =      TEXTURES_DIR + SLASH + "space_shuttle.png";
    public static final String SCANNER_P =          TEXTURES_DIR + SLASH + "scanner_p.jpg";
    public static final String ATM_RADIAL_II =      TEXTURES_DIR + SLASH + "atm_radial_ii.png";
    public static final String ATM_RADIAL_I =       TEXTURES_DIR + SLASH + "atm_radial_i.png";
    public static final String GRASS_PATCH_I =      TEXTURES_DIR + SLASH + "grass_patch_gimp.png";
    public static final String BUTTON_I =           TEXTURES_DIR + SLASH + "button_i.png";

    //obj files
    public static final String OBJECTS_F = "objects/";
    public static final String CIRCLE_SIMPLE = "atm_simple_circle.obj";
    public static final String CIRCLE_SUB_DIV_III = "circle_sub_div_iii.obj";
    public static final String BOX = "box.obj";
    public static final String UV_MAPPED_BOX = "uv_mapped_box.obj";
    public static final String CIRCLE_SUB_DIV_I = "circle_sub_div_i.obj";
}
