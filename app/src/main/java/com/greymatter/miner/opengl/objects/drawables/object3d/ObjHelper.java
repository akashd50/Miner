package com.greymatter.miner.opengl.objects.drawables.object3d;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.opengl.objects.drawables.Shape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class ObjHelper {
    public static ArrayList<Vector3f> roughShape, verticesG;

    public static RawObjData loadFromFile(Shape shape, String file) {
        RawObjData data = new RawObjData();
        try {
            InputStream stream = AppServices.getAssetManager().open(file);
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
                    updateShapeParams(shape, data, vert);
                    data.vertices.add(vert);
                } else if (lineTokens[0].equals("vt")) {
                    Vector2f uv = new Vector2f(Float.parseFloat(lineTokens[1]),
                            Float.parseFloat(lineTokens[2]));
                    data.uvs.add(uv);
                } else if (lineTokens[0].equals("f")) {
                    String[] faceV1Tokens = lineTokens[1].split( "/");
                    String[] faceV2Tokens = lineTokens[2].split( "/");
                    String[] faceV3Tokens = lineTokens[3].split( "/");

                    Config c = new Config();
                    c.v1 = Integer.parseInt(faceV1Tokens[0]) - 1;
                    c.v2 = Integer.parseInt(faceV2Tokens[0]) - 1;
                    c.v3 = Integer.parseInt(faceV3Tokens[0]) - 1;
                    //edges from v1 -> v2, v2 -> v3, v3 -> v1
                    c.t1 = Integer.parseInt(faceV1Tokens[1]) - 1;
                    c.t2 = Integer.parseInt(faceV2Tokens[1]) - 1;
                    c.t3 = Integer.parseInt(faceV3Tokens[1]) - 1;

                    c.n1 = Integer.parseInt(faceV1Tokens[2]) - 1;
                    c.n2 = Integer.parseInt(faceV2Tokens[2]) - 1;
                    c.n3 = Integer.parseInt(faceV3Tokens[2]) - 1;

                    data.faceConfiguration.add(c);
                }/* else if (lineTokens[0].equals("vn")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(lineTokens[1]),
                            Float.parseFloat(lineTokens[2]),
                            Float.parseFloat(lineTokens[3]));
                    data.normals.add(normal);
                }*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void updateShapeParams(Shape shape, RawObjData data, Vector3f vector) {
        if(vector.y > shape.getTop().y) {
            shape.setTop(vector);
            data.topIndex = data.vertices.size();
        }

        if(vector.y < shape.getBottom().y) {
            shape.setBottom(vector);
        }

        if(vector.x > shape.getRight().x) {
            shape.setRight(vector);
        }

        if(vector.x < shape.getLeft().x) {
            shape.setLeft(vector);
        }
    }

    public static ArrayList<Vector3f> generateRoughMeshByNumFaces(Obj object, ArrayList<Vector3f> vertices,
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

    public static ArrayList<Vector3f> generateRoughMesh2(RawObjData data) {
        roughShape = new ArrayList<>();
        verticesG = data.vertices;

        int topIndex = data.topIndex;
        ArrayList<Integer> indexesToCheck = new ArrayList<>();
        indexesToCheck.add(topIndex);

        while(!indexesToCheck.isEmpty()) {
            int current = indexesToCheck.remove(0);
            ArrayList<Integer> indexesAttachedToCurrent = allIndexesLinkedToCurrent(current, data.faceConfiguration);

            int indexLinkingToLeastFaces = indexLinkingToLeastFaces(indexesAttachedToCurrent, data.faceConfiguration);
            if(indexLinkingToLeastFaces!=-1) {
                Vector3f vertexToAdd = data.vertices.get(indexLinkingToLeastFaces);
                if(!roughShape.contains(vertexToAdd)) {
                    roughShape.add(vertexToAdd);
                    indexesToCheck.add(indexLinkingToLeastFaces);
                }
            }
        }

        //add the first point again to complete the mesh
        roughShape.add(roughShape.get(0));

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
