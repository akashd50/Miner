#version 300 es
precision mediump float;
struct Material {
    sampler2D diffuseTexture;
    sampler2D specularTexture;
    float specMultiplier;
    vec3 diffuse;
    vec3 specular;
    vec3 ambient;
};
uniform Material material;

in vec2 out_uv;

out vec4 FragColor;
void main() {
    FragColor = texture(material.diffuseTexture, out_uv);
}
