#version 300 es
in vec3 in_position;
in vec2 in_uv;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec2 out_uv;
void main() {
    out_uv = in_uv;
    gl_Position = projection * view * model *  vec4(in_position, 1.0);
}