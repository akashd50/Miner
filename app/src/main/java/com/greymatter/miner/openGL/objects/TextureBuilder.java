package com.greymatter.miner.openGL.objects;

import android.graphics.Bitmap;
import android.opengl.GLES30;

import com.greymatter.miner.openGL.helpers.BufferHelper;
import com.greymatter.miner.openGL.helpers.ResourceLoader;

import java.nio.ByteBuffer;

public class TextureBuilder {
	private Texture texture;
	private int[] currTexID;
	private int currTexType;
	private int currW;
	private int currH;
	private int imgTextureformat;

	public TextureBuilder() {
		texture = new Texture();
	}

	public final TextureBuilder init(int texType)
	{
		GLES30.glDeleteTextures(1, currTexID, 0);
		GLES30.glGenTextures(1, currTexID, 0);
		this.currTexType = texType;

		return this;
	}

	public final TextureBuilder init(int texType, int w, int h) {
		this.currTexType = texType;
		currW = w;
		currH = h;
		return this;
	}

	public final TextureBuilder attachImageTexture(String imageRes) {
		Bitmap bitmap = ResourceLoader.loadImageResource(imageRes);
		ByteBuffer pixelData = BufferHelper.asByteBuffer(bitmap);

		texture.setWidth(bitmap.getWidth());
		texture.setHeight(bitmap.getHeight());
		texture.setCC(bitmap.getColorSpace().getComponentCount());

		if (texture.getCC() == 1) {
			imgTextureformat = GLES30.GL_RED;
		}
		else if (texture.getCC() == 3) {
			imgTextureformat = GLES30.GL_RGB;
		}
		else if (texture.getCC() == 4) {
			imgTextureformat = GLES30.GL_RGBA;
		}

		GLES30.glBindTexture(currTexType, currTexID[0]);
		GLES30.glTexImage2D(currTexType, 0, imgTextureformat, texture.getWidth(), texture.getHeight(),
				0, imgTextureformat, GLES30.GL_UNSIGNED_BYTE, pixelData);
		GLES30.glGenerateMipmap(currTexType);
		GLES30.glBindTexture(currTexType, 0);

		return this;
	}

	public final Texture finish() {
		GLES30.glBindTexture(currTexType, currTexID[0]);
		if (currTexType == GLES30.GL_TEXTURE_CUBE_MAP) {

			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_R, GLES30.GL_CLAMP_TO_EDGE);

		} else if (currTexType == GLES30.GL_TEXTURE_2D) {
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, imgTextureformat == GLES30.GL_RGBA ? GLES30.GL_CLAMP_TO_EDGE : GLES30.GL_REPEAT);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, imgTextureformat == GLES30.GL_RGBA ? GLES30.GL_CLAMP_TO_EDGE : GLES30.GL_REPEAT);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

		}
		GLES30.glBindTexture(currTexType, 0);

		texture.setTexture(currTexID[0]);

		return this.texture;
	}

	public final Texture getTexture()
	{
		return this.texture;
	}
}
