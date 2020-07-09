#version 300 es
in vec3 in_position;

uniform mat4 model, view, projection;

out vec3 out_vertex_pos_vs;
void main() {
    out_vertex_pos_vs = (view * model * vec4(in_position, 1.0)).xyz;
    gl_Position = projection * view * model * vec4(in_position, 1.0);
}