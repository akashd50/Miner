#version 300 es
in vec3 in_position;
in vec2 in_uv;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec2 out_uv;
out vec3 out_vertex_pos_vs;
void main() {
    out_uv = in_uv;
    out_vertex_pos_vs = (view * model * vec4(in_position, 1.0)).xyz;
    gl_Position = projection * vec4(out_vertex_pos_vs, 1.0);
}