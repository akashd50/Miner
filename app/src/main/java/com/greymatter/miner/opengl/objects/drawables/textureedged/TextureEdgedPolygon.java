package com.greymatter.miner.opengl.objects.drawables.textureedged;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class TextureEdgedPolygon extends Drawable {
    private float _edgeWidth;
    private ArrayList<Vector3f> _vertices;
    private float[] _verticesArray, _uvsArray;
    private int _verticesIndex, _uvsIndex;
    public TextureEdgedPolygon(String id) {
        super(id);
    }

    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, getTransforms().getModelMatrix());
        ShaderHelper.setMaterialProperties(getShader(), getMaterial());

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, (_vertices.size()-1) * 2 /*triangles*/ * 3 /*vertices per triangle*/ );

        GLBufferHelper.glUnbindVertexArray();
    }

    public TextureEdgedPolygon load(ArrayList<Vector3f> vertices, float edgeWidth) {
        _vertices = vertices;
        _edgeWidth = edgeWidth;
        _verticesArray = new float[(_vertices.size()-1)*18];
        _uvsArray = new float[(_vertices.size()-1)*12];
        _verticesIndex = 0;
        _uvsIndex = 0;
        setUpVertexData();
        return this;
    }

    private void setUpVertexData() {
        Vector3f previousEdgeNormal =  null;
        Vector3f currentEdgeNormal = null;
        Vector3f previousNetNormal = null;

        for(int i=0; i < _vertices.size()-1; i++) {
            Vector3f nextB = null;
            Vector3f currA = _vertices.get(i);
            Vector3f currB = _vertices.get(i+1);
            if( i+2< _vertices.size()) nextB = _vertices.get(i+2);
            else nextB = _vertices.get(1);

            if(i==0) {
                Vector3f prevA = _vertices.get(_vertices.size()-2);

                previousEdgeNormal = VectorHelper.getNormal(VectorHelper.sub(currA, prevA));
                currentEdgeNormal = VectorHelper.getNormal(VectorHelper.sub(currB, currA));
                previousEdgeNormal.normalize();
                currentEdgeNormal.normalize();

                previousEdgeNormal.x = _edgeWidth*previousEdgeNormal.x;
                previousEdgeNormal.y = _edgeWidth*previousEdgeNormal.y;
                currentEdgeNormal.x = _edgeWidth*currentEdgeNormal.x;
                currentEdgeNormal.y = _edgeWidth*currentEdgeNormal.y;

                previousNetNormal = new Vector3f((previousEdgeNormal.x + currentEdgeNormal.x)/2,
                        (previousEdgeNormal.y + currentEdgeNormal.y)/2,0f);
            }

            Vector3f nextEdgeNormal = VectorHelper.getNormal(VectorHelper.sub(nextB, currB));
            nextEdgeNormal.normalize();
            nextEdgeNormal.x = _edgeWidth*nextEdgeNormal.x;
            nextEdgeNormal.y = _edgeWidth*nextEdgeNormal.y;

            Vector3f currentNetNormal = new Vector3f((nextEdgeNormal.x + currentEdgeNormal.x)/2,
                    (nextEdgeNormal.y + currentEdgeNormal.y)/2,0f);

            //set up vertices and uvs
            updateVerticesAndUvs(currA, currB, currentNetNormal, previousNetNormal);

            previousEdgeNormal = currentEdgeNormal;
            currentEdgeNormal = nextEdgeNormal;
            previousNetNormal = currentNetNormal;
        }
    }

    public TextureEdgedPolygon build() {
        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(_verticesArray, 3, super.getShader(), ShaderConst.IN_POSITION);
        int uvBufferObj = GLBufferHelper.putDataIntoArrayBuffer(_uvsArray,2,super.getShader(), ShaderConst.IN_UV);
        GLBufferHelper.glUnbindVertexArray();

        super.setVertexArrayObject(vertexArrayObj);
        super.setVertexBufferObject(vertexBufferObj);
        return this;
    }

    private void updateVerticesAndUvs(Vector3f currA, Vector3f currB, Vector3f currentNetNormal, Vector3f previousNetNormal) {
        _verticesArray[_verticesIndex++] = currB.x + currentNetNormal.x;
        _verticesArray[_verticesIndex++] = currB.y + currentNetNormal.y;
        _verticesArray[_verticesIndex++] = currentNetNormal.z;

        _uvsArray[_uvsIndex++] = 1.0f;
        _uvsArray[_uvsIndex++] = 0f;

        _verticesArray[_verticesIndex++] = currA.x + previousNetNormal.x;
        _verticesArray[_verticesIndex++] = currA.y + previousNetNormal.y;
        _verticesArray[_verticesIndex++] = previousNetNormal.z;

        _uvsArray[_uvsIndex++] = 0f;
        _uvsArray[_uvsIndex++] = 0f;

        _verticesArray[_verticesIndex++] = currA.x;
        _verticesArray[_verticesIndex++] = currA.y;
        _verticesArray[_verticesIndex++] = currA.z;

        _uvsArray[_uvsIndex++] = 0f;
        _uvsArray[_uvsIndex++] = 1.0f;

        //second
        _verticesArray[_verticesIndex++] = currB.x +currentNetNormal.x;
        _verticesArray[_verticesIndex++] = currB.y +currentNetNormal.y;
        _verticesArray[_verticesIndex++] = currentNetNormal.z;

        _uvsArray[_uvsIndex++] = 1.0f;
        _uvsArray[_uvsIndex++] = 0f;

        _verticesArray[_verticesIndex++] = currA.x;
        _verticesArray[_verticesIndex++] = currA.y;
        _verticesArray[_verticesIndex++] = currA.z;

        _uvsArray[_uvsIndex++] = 0f;
        _uvsArray[_uvsIndex++] = 1.0f;

        _verticesArray[_verticesIndex++] = currB.x;
        _verticesArray[_verticesIndex++] = currB.y;
        _verticesArray[_verticesIndex++] = currB.z;

        _uvsArray[_uvsIndex++] = 1.0f;
        _uvsArray[_uvsIndex++] = 1.0f;
    }

    @Override
    public TextureEdgedPolygon attachPolygonTouchChecker() {
        return this;
    }

    @Override
    public TextureEdgedPolygon attachPolygonCollider() {
        return this;
    }

    @Override
    public TextureEdgedPolygon setShader(Shader shader) {
        super.setShader(shader);
        return this;
    }

    @Override
    public TextureEdgedPolygon setMaterial(Material material) {
        super.setMaterial(material);
        return this;
    }
}
