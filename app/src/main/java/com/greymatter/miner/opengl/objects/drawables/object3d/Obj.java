package com.greymatter.miner.opengl.objects.drawables.object3d;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.*;
import javax.vecmath.Vector3f;

public class Obj extends Drawable {
	private int uvBufferObject;
	public Obj(ObjId id) {
		super(id);
	}

	@Override
	public Obj build() {
		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		int vertexBufferObject = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3,
				getRenderer().getShader(), ShaderConst.IN_POSITION);
		super.setVertexBufferObject(vertexBufferObject);

		uvBufferObject = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(), 2,
				getRenderer().getShader(), ShaderConst.IN_UV);

		GLBufferHelper.glUnbindVertexArray();
		return this;
	}

	public ArrayList<Vector3f> getOuterMesh() {
		return getShape().getOrderedOuterMesh();
	}

	public RawObjData getRawObjData() {
		return getShape().getRawObjData();
	}
}
