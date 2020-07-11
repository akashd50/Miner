#version 300 es
in vec3 in_position;

uniform mat4 model, view, projection;

out vec2 out_vertex_pos;
void main() {
    out_vertex_pos = in_position.xy;
    gl_Position = projection * view * model * vec4(in_position, 1.0);
}