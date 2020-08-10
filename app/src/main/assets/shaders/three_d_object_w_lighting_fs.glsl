#version 300 es
precision highp float;
struct Material {
    sampler2D mainTexture;
    sampler2D lightTexture;
    int hasLightTexture;
};

#define MAX_LIGHTS 5
struct Light {
    mat4 model;
    vec3 light_pos;
    vec4 light_color;
    float light_radius, light_inner_cutoff, light_outer_cutoff, intensity;
};

//uniforms
uniform Light[MAX_LIGHTS] lights;
uniform Material material;
uniform mat4 view;

//variables from vertex shader
in vec2 out_uv;
in vec3 out_vertex_pos_vs;

//out variables
out vec4 FragColor;

//local variables
vec4 total_light_color;
float total_light_intensity;
void calculateLight(int index);

void main() {
    total_light_color = vec4(0.0,0.0,0.0,0.0);
    total_light_intensity = 0.0;
    int i = 0;
    for(i;i<MAX_LIGHTS;i++) {
        if(lights[i].light_radius == 0.0) {
            break;
        }else{
            calculateLight(i);
        }
    }

    vec4 textured = texture(material.mainTexture, out_uv);
    if(textured.w > 0.2) {
        if(material.hasLightTexture == 0) {
            FragColor = (vec4(total_light_color.xyz * total_light_intensity, 1.0)) + textured;
        }

        if(material.hasLightTexture == 1){
            vec4 lightTextured = texture(material.lightTexture, out_uv);
            if(lightTextured.w > 0.2) {
                FragColor = (vec4(total_light_color.xyz * total_light_intensity, 1.0)) + lightTextured;
            }else{
                FragColor = textured;
            }
        }
    }
}

void calculateLight(int index) {
    vec3 light_pos_cs = vec3(view * lights[index].model * vec4(lights[index].light_pos,1.0));
    vec2 lightVertexDirWS = light_pos_cs.xy - out_vertex_pos_vs.xy;
    float distance = length(lightVertexDirWS);

    if(distance < lights[index].light_radius) {
        float epsilon = lights[index].light_inner_cutoff - lights[index].light_outer_cutoff;
        total_light_intensity += clamp((distance - lights[index].light_outer_cutoff) / epsilon, 0.0, 1.0) * lights[index].intensity;
        total_light_color += lights[index].light_color;
    }
}
