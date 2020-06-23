#version 300 es
precision highp float;

uniform mat4 model, view, projection;
uniform vec4 centerColor, edgeColor;
uniform vec3 translation;
uniform float radius, offsetFromCenter, offsetFromEdge;

out vec4 FragColor;
void main() {
    vec3 translationVS = (projection * view * model *  vec4(translation, 1.0)).xyz;
    vec3 translationToPoint = gl_FragCoord.xyz - vec3(0.0,10.0,0.0);
    float dist = length(translationToPoint)/radius;
    FragColor = vec4(centerColor.x, centerColor.y, centerColor.z/dist, centerColor.w);//vec4(1.0,1.0,1.0,1.0);
}
