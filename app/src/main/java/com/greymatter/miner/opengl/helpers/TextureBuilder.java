package com.greymatter.miner.opengl.helpers;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.greymatter.miner.opengl.objects.Texture;

public class TextureBuilder {
//	private Texture texture;
//	private int[] currTexID;
//	private int currTexType;
//	private int currW;
//	private int currH;
//	private int imgTextureformat;

	public static Texture create(int glTexType) {
		Texture texture = new Texture();
		texture.setGlTexType(glTexType);

		int[] texID = new int[1];
		GLES30.glGenTextures(1, texID, 0);

		texture.setTextureId(texID[0]);
		return texture;
	}

	public static void attachImage(Texture texture, String imageRes) {
		Bitmap bitmap = ResourceLoader.loadImageResource(imageRes);

		texture.setWidth(bitmap.getWidth());
		texture.setHeight(bitmap.getHeight());
		texture.setCC(bitmap.getColorSpace().getComponentCount());

		GLES30.glBindTexture(texture.getGlTexType(), texture.getTextureId());
		GLUtils.texImage2D(texture.getGlTexType(), 0, bitmap, 0);
		GLES30.glGenerateMipmap(texture.getTextureId());
		GLES30.glBindTexture(texture.getGlTexType(), 0);
	}

	public static void finish(Texture texture) {
		GLES30.glBindTexture(texture.getGlTexType(), texture.getTextureId());
		int imgTextureFormat = -1;
		if (texture.getCC() == 1) { imgTextureFormat = GLES30.GL_RED; }
		else if (texture.getCC() == 3) { imgTextureFormat = GLES30.GL_RGB; }
		else if (texture.getCC() == 4) { imgTextureFormat = GLES30.GL_RGBA; }

		if (texture.getGlTexType() == GLES30.GL_TEXTURE_CUBE_MAP) {
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_R, GLES30.GL_CLAMP_TO_EDGE);

		} else if (texture.getGlTexType() == GLES30.GL_TEXTURE_2D) {
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, imgTextureFormat == GLES30.GL_RGBA ? GLES30.GL_CLAMP_TO_EDGE : GLES30.GL_REPEAT);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, imgTextureFormat == GLES30.GL_RGBA ? GLES30.GL_CLAMP_TO_EDGE : GLES30.GL_REPEAT);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
		}

		GLES30.glBindTexture(texture.getGlTexType(), 0);
	}

//	public TextureBuilder() {
//		texture = new Texture();
//		currTexID = new int[1];
//	}
//
//	public final TextureBuilder init(int texType) {
//		GLES30.glGenTextures(1, currTexID, 0);
//		this.currTexType = texType;
//
//		return this;
//	}
//
//	public TextureBuilder init(int texType, int w, int h) {
//		this.currTexType = texType;
//		currW = w;
//		currH = h;
//		return this;
//	}
//
//	public TextureBuilder attachImageTexture(String imageRes) {
//		Bitmap bitmap = ResourceLoader.loadImageResource(imageRes);
//		//Buffer pixelData = BufferHelper.asByteBuffer(bitmap);
//
//		texture.setWidth(bitmap.getWidth());
//		texture.setHeight(bitmap.getHeight());
//		texture.setCC(bitmap.getColorSpace().getComponentCount());
//
//		if (texture.getCC() == 1) {
//			imgTextureformat = GLES30.GL_RED;
//		}
//		else if (texture.getCC() == 3) {
//			imgTextureformat = GLES30.GL_RGB;
//		}
//		else if (texture.getCC() == 4) {
//			imgTextureformat = GLES30.GL_RGBA;
//		}
//
//		GLES30.glBindTexture(currTexType, currTexID[0]);
////		GLES30.glTexImage2D(currTexType, 0, imgTextureformat, texture.getWidth(), texture.getHeight(),
////				0, imgTextureformat, GLES30.GL_UNSIGNED_BYTE, pixelData);
//		GLUtils.texImage2D(currTexType, 0, bitmap, 0);
//
//		GLES30.glGenerateMipmap(currTexType);
//		GLES30.glBindTexture(currTexType, 0);
//
//		return this;
//	}
//
//	public Texture finish() {
//		GLES30.glBindTexture(currTexType, currTexID[0]);
//
//		if (currTexType == GLES30.GL_TEXTURE_CUBE_MAP) {
//
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_R, GLES30.GL_CLAMP_TO_EDGE);
//
//		} else if (currTexType == GLES30.GL_TEXTURE_2D) {
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, imgTextureformat == GLES30.GL_RGBA ? GLES30.GL_CLAMP_TO_EDGE : GLES30.GL_REPEAT);
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, imgTextureformat == GLES30.GL_RGBA ? GLES30.GL_CLAMP_TO_EDGE : GLES30.GL_REPEAT);
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
//			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
//		}
//
//		GLES30.glBindTexture(currTexType, 0);
//
//		texture.setTextureId(currTexID[0]);
//
//		return this.texture;
//	}
//
//	public Texture getTexture()
//	{
//		return this.texture;
//	}
}
