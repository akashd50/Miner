package com.greymatter.miner.opengl.objects;

import android.opengl.GLES30;
import com.greymatter.miner.AppServices;
import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Object3D extends Drawable {
	private static class config {
		int v1, v2, v3, n1, n2, n3, t1, t2, t3;
	}

	private ArrayList<Vector3f> vertices;
	private ArrayList<Vector3f> normals;
	private ArrayList<Vector2f> uvs;
	private ArrayList<config> faceConfiguration;

	private int vertexBufferObject;
	private int normalBufferObject;
	private int uvBufferObject;

	public Object3D(String file, Material material, Shader shader) {
		super();
		super.setShader(shader);
		super.setMaterial(material);

		readFile(file);

		float[] localVerts = new float[faceConfiguration.size() * 3 * 3];
		float[] localNormals = new float[faceConfiguration.size() * 3 * 3];
		float[] localUvs = new float[faceConfiguration.size() * 6];

		int tempIndex = 0;
		int vertsIndex = 0;
		int uvsIndex = 0;
		for (int i = 0; i < faceConfiguration.size(); i++) {
			config curr = faceConfiguration.get(i);

			localVerts[vertsIndex++] = (float) vertices.get(curr.v1).x;
			localVerts[vertsIndex++] = (float) vertices.get(curr.v1).y;
			localVerts[vertsIndex++] = (float) vertices.get(curr.v1).z;

			localNormals[tempIndex++] = (float) normals.get(curr.n1).x;
			localNormals[tempIndex++] = (float) normals.get(curr.n1).y;
			localNormals[tempIndex++] = (float) normals.get(curr.n1).z;

			localUvs[uvsIndex++] = (float) uvs.get(curr.t1).x;
			localUvs[uvsIndex++] = (float) uvs.get(curr.t1).y;

			localVerts[vertsIndex++] = (float) vertices.get(curr.v2).x;
			localVerts[vertsIndex++] = (float) vertices.get(curr.v2).y;
			localVerts[vertsIndex++] = (float) vertices.get(curr.v2).z;

			localNormals[tempIndex++] = (float) normals.get(curr.n2).x;
			localNormals[tempIndex++] = (float) normals.get(curr.n2).y;
			localNormals[tempIndex++] = (float) normals.get(curr.n2).z;

			localUvs[uvsIndex++] = (float) uvs.get(curr.t2).x;
			localUvs[uvsIndex++] = (float) uvs.get(curr.t2).y;

			localVerts[vertsIndex++] = (float) vertices.get(curr.v3).x;
			localVerts[vertsIndex++] = (float) vertices.get(curr.v3).y;
			localVerts[vertsIndex++] = (float) vertices.get(curr.v3).z;

			localNormals[tempIndex++] = (float) normals.get(curr.n3).x;
			localNormals[tempIndex++] = (float) normals.get(curr.n3).y;
			localNormals[tempIndex++] = (float) normals.get(curr.n3).z;

			localUvs[uvsIndex++] = (float) uvs.get(curr.t3).x;
			localUvs[uvsIndex++] = (float) uvs.get(curr.t3).y;
		}

		System.out.print("End of Loading");
		System.out.print("\n");

		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		vertexBufferObject = GLBufferHelper.putDataIntoArrayBuffer(localVerts, 3,
																getShader(), Constants.IN_POSITION);
		normalBufferObject = GLBufferHelper.putDataIntoArrayBuffer(localNormals, 3,
																getShader(), Constants.IN_NORMAL);
		uvBufferObject = GLBufferHelper.putDataIntoArrayBuffer(localUvs, 2,
																getShader(), Constants.IN_UV);
		GLBufferHelper.glUnbindVertexArray();


	}

	public void onDrawFrame() {
		super.onDrawFrame();

		GLES30.glBindVertexArray(getVertexArrayObject());

		if(getMaterial()!=null) {
			ShaderHelper.setMaterialProperties(getShader(), getMaterial());
		}

		ShaderHelper.setUniformMatrix4fv(getShader(), Constants.MODEL, getModelMatrix());

		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, faceConfiguration.size() * 3);

		GLBufferHelper.glUnbindVertexArray();
	}

	private void readFile(String filename) {
		vertices = new ArrayList<>();
		normals = new ArrayList<>();
		uvs = new ArrayList<>();
		faceConfiguration = new ArrayList<>();

		try {
			InputStream stream = AppServices.getAssetManager().open(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
			System.out.print("File Opened! Reading now...");
			System.out.print("\n");

			String line;
			while ((line=bufferedReader.readLine())!=null) {
				String[] lineTokens = line.split(" ");

				if (lineTokens[0].equals("v")) {
					Vector3f vert = new Vector3f(Float.parseFloat(lineTokens[1]), 
												Float.parseFloat(lineTokens[2]),
												Float.parseFloat(lineTokens[3]));
					
					vertices.add(vert);
				} else if (lineTokens[0].equals("vt")) {
					Vector2f uv = new Vector2f(Float.parseFloat(lineTokens[1]),
												Float.parseFloat(lineTokens[2]));
					uvs.add(uv);
				} else if (lineTokens[0].equals("f")) {
					String[] faceV1Tokens = lineTokens[1].split( "/");
					String[] faceV2Tokens = lineTokens[2].split( "/");
					String[] faceV3Tokens = lineTokens[3].split( "/");

					config c = new config();
					c.v1 = Integer.parseInt(faceV1Tokens[0]) - 1;
					c.v2 = Integer.parseInt(faceV2Tokens[0]) - 1;
					c.v3 = Integer.parseInt(faceV3Tokens[0]) - 1;

					c.t1 = Integer.parseInt(faceV1Tokens[1]) - 1;
					c.t2 = Integer.parseInt(faceV2Tokens[1]) - 1;
					c.t3 = Integer.parseInt(faceV3Tokens[1]) - 1;

					c.n1 = Integer.parseInt(faceV1Tokens[2]) - 1;
					c.n2 = Integer.parseInt(faceV2Tokens[2]) - 1;
					c.n3 = Integer.parseInt(faceV3Tokens[2]) - 1;

					faceConfiguration.add(c);
				} else if (lineTokens[0].equals("vn")) {
					Vector3f normal = new Vector3f(Float.parseFloat(lineTokens[1]),
													Float.parseFloat(lineTokens[2]),
													Float.parseFloat(lineTokens[3]));
					normals.add(normal);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
