package com.greymatter.miner.opengl.helpers;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.greymatter.miner.opengl.objects.Texture;

public class TextureBuilder {

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
		//GLES30.glGenerateMipmap(texture.getTextureId());
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

}
