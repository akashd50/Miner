package com.greymatter.miner.opengl.objects;

public class Texture {
	private int textureId, width, height, nrChannels, glTexType;

	public Texture() {}

	public float getRatio() { return this.width / this.height; }

	public int getTextureId() { return this.textureId; }

	public void setTextureId(int tex) { this.textureId = tex; }

	public int getWidth() { return this.width; }

	public int getHeight() { return this.height; }

	public void setWidth(int w) { this.width = w; }

	public void setHeight(int h) { this.height = h; }

	public void setCC(int c) { this.nrChannels = c; }

	public int getCC() { return this.nrChannels; }

	public int getGlTexType() {
		return glTexType;
	}

	public void setGlTexType(int glTexType) {
		this.glTexType = glTexType;
	}
}
