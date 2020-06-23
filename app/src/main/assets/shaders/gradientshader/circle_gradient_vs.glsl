#version 300 es
in vec3 in_position;

uniform mat4 model, view, projection;

void main() {
    gl_Position = projection * view * model *  vec4(in_position, 1.0);
}