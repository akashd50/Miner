#version 300 es
precision highp float;

uniform mat4 model, view, projection;
uniform vec4 centerColor, midColor, edgeColor;
uniform vec3 center;
uniform float radius, midPoint;

in vec3 out_vertex_pos_vs;

out vec4 FragColor;
void main() {
    vec3 centerCS = (view * model *  vec4(center, 1.0)).xyz;
    vec3 translationToPoint = out_vertex_pos_vs - centerCS;
    float distance = length(translationToPoint);
    float unitDist = distance/radius;
    float nUnitDist = (distance-midPoint)/(radius-midPoint);

    vec4 lightColor = mix(centerColor, midColor, unitDist);
    lightColor += mix(midColor, edgeColor, nUnitDist);

    FragColor = lightColor;
}
