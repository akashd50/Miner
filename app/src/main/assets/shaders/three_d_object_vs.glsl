#version 300 es
in vec3 in_position;
in vec3 in_normal;
in vec2 in_uv;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec2 out_uv;
out vec3 out_ws_normal;
void main() {
    out_uv = in_uv;
    out_ws_normal = in_normal;
    gl_Position = projection * view * model *  vec4(in_position, 1.0);
}