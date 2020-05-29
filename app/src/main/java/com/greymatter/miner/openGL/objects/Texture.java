package com.greymatter.miner.openGL.objects;

public class Texture {
	private int texture, width, height, nrChannels;

	public Texture() {}

	public float getRatio() { return this.width / this.height; }

	public int getTexture() { return this.texture; }

	public void setTexture(int tex) { this.texture = tex; }

	public int getWidth() { return this.width; }

	public int getHeight() { return this.height; }

	public void setWidth(int w) { this.width = w; }

	public void setHeight(int h) { this.height = h; }

	public void setCC(int c) { this.nrChannels = c; }

	public int getCC() { return this.nrChannels; }
}
