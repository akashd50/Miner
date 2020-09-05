#version 300 es
in vec3 in_position;
in vec3 in_instance_translation;
in vec3 in_instance_translation_offset;
in vec3 in_instance_rotation;
in vec3 in_instance_scale;
in vec2 in_uv;

uniform mat4 view;
uniform mat4 projection;

out vec2 out_uv;
void main() {
    out_uv = in_uv;

    vec3 offsetTranslated = vec3(in_position.x + in_instance_translation_offset.x,
                                 in_position.y + in_instance_translation_offset.y,
                                 in_position.z + in_instance_translation_offset.z);

    vec3 scaled = vec3(offsetTranslated.x * in_instance_scale.x,
                        offsetTranslated.y * in_instance_scale.y,
                        offsetTranslated.z * in_instance_scale.z);

    //only rotation along z axis for now
    float rad = radians(in_instance_rotation.z);
    float cosine = cos(rad);
    float sine = sin(rad);
    vec3 rotated = vec3(scaled.x * cosine + scaled.y * -sine,
                        scaled.x * sine + scaled.y * cosine, scaled.z);

    vec3 translated = vec3(rotated.x + in_instance_translation.x,
                            rotated.y + in_instance_translation.y,
                            rotated.z + in_instance_translation.z);

    gl_Position = projection * view * vec4(translated, 1.0);
}