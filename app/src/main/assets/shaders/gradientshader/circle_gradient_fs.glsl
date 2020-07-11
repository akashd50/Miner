#version 300 es
precision highp float;

uniform mat4 model, view, projection;
uniform vec4 centerColor, midColor, edgeColor;
uniform float radius, midPoint;

in vec2 out_vertex_pos;
out vec4 FragColor;
void main() {
    vec2 translation = vec2(0.0,0.0);
    vec2 translationToPoint = out_vertex_pos - translation;
    float distance = length(translationToPoint);
    float unitDist = distance/radius;
    float nUnitDist = (distance-midPoint)/(radius-midPoint);
    vec4 lightColor;
    if(distance<midPoint) {
        lightColor = mix(centerColor, midColor, unitDist);
    }else {
        lightColor = mix(midColor, edgeColor, nUnitDist);
    }
    FragColor = lightColor;
}
