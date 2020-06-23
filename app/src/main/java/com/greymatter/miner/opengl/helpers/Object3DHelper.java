package com.greymatter.miner.opengl.helpers;

import com.greymatter.miner.opengl.objects.Config;
import com.greymatter.miner.opengl.objects.drawables.Object3D;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class Object3DHelper {
    public static ArrayList<Vector3f> roughShape, verticesG;
    public static ArrayList<Config> faceConfigurationG;

    public static ArrayList<Vector3f> generateRoughMeshByNumFaces(Object3D object, ArrayList<Vector3f> vertices,
                                                                  ArrayList<Config> faceConfiguration) {
        ArrayList<Vector3f> roughShape = new ArrayList<>();

        for(int i=0;i<vertices.size();i++) {
            if(findAllConfigsUsingVertex(i, faceConfiguration).size() <= 3) {
                roughShape.add(vertices.get(i));
            }
        }
        return roughShape;
    }

    public static ArrayList<Vector3f> simplify(ArrayList<Vector3f> vertices, float simplificationFactor) {
        int reducedVerticesSize = (int)Math.ceil(vertices.size() * simplificationFactor);
        ArrayList<Vector3f> toReturn = new ArrayList<>();
        for(int i=0;i<vertices.size();i+=(int)(vertices.size()/reducedVerticesSize)) {
            toReturn.add(vertices.get(i));
        }
        return toReturn;
    }

    public static ArrayList<Vector3f> generateRoughMesh2(Object3D object, ArrayList<Vector3f> vertices,
                                                                  ArrayList<Config> faceConfiguration) {
        roughShape = new ArrayList<>();
        verticesG = vertices;
        faceConfigurationG = faceConfiguration;

        int topIndex = object.topIndex;
        Vector3f topVertex = object.top;
        ArrayList<Integer> indexesToCheck = new ArrayList<>();
        indexesToCheck.add(topIndex);

        while(!indexesToCheck.isEmpty()) {
            int current = indexesToCheck.remove(0);
            ArrayList<Integer> indexesAttachedToCurrent = allIndexesLinkedToCurrent(current, faceConfiguration);

            int indexLinkingToLeastFaces = indexLinkingToLeastFaces(indexesAttachedToCurrent, faceConfiguration);
            if(indexLinkingToLeastFaces!=-1) {
                Vector3f vertexToAdd = vertices.get(indexLinkingToLeastFaces);
                if(!roughShape.contains(vertexToAdd)) {
                    roughShape.add(vertexToAdd);
                    indexesToCheck.add(indexLinkingToLeastFaces);
                }
            }
        }

        return roughShape;
    }

    public static int indexLinkingToLeastFaces(ArrayList<Integer> indexes, ArrayList<Config> configs) {
        int leastFaces = 999;
        int leastFacesIndex = -1;
        for(int i : indexes) {
            int currentNumConfigs = findAllConfigsUsingVertex(i, configs).size();
            if(currentNumConfigs<=5 && currentNumConfigs < leastFaces && !roughShape.contains(verticesG.get(i))) {
                leastFacesIndex = i;
                leastFaces = currentNumConfigs;
            }
        }
        return leastFacesIndex;
    }

    public static ArrayList<Integer> allIndexesLinkedToCurrent(int index, ArrayList<Config> Configs) {
        ArrayList<Integer> toReturn = new ArrayList<>();
        for(Config c : Configs) {
            if(c.v1 == index) {
                toReturn.add(c.v2);
                toReturn.add(c.v3);
            }else if(c.v2 == index) {
                toReturn.add(c.v1);
                toReturn.add(c.v3);
            }else if(c.v3 == index) {
                toReturn.add(c.v1);
                toReturn.add(c.v2);
            }
        }
        return toReturn;
    }

    public static ArrayList<Config> findAllConfigsUsingVertex(int index, ArrayList<Config> Configs) {
        ArrayList<Config> toReturn = new ArrayList<>();
        for(Config c : Configs) {
            if(c.v1 == index || c.v2 == index || c.v3 == index) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }
}
