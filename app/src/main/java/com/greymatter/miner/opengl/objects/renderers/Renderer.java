package com.greymatter.miner.opengl.objects.renderers;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.shader.Shader;

public abstract class Renderer {
    private Shader shader;
    public Renderer(Shader shader) {
        this.shader = shader;
    }

    public abstract void render(Camera camera, IGameObject gameObject);

    public Renderer setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    public Shader getShader() {
        return shader;
    }
}
