package com.greymatter.miner.opengl;

import com.greymatter.miner.opengl.objects.drawables.Shape;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class ShapesHelper {
    public static void loadCircle(Shape shape, float radius) {
        shape.addVertex(new Vector3f(0f,0f,0f));

        for(int angle=0;angle<360;angle++) {
            shape.addVertex(new Vector3f(
                    radius * (float)Math.cos(Math.toRadians(angle)),
                    radius * (float)Math.sin(Math.toRadians(angle)),
                    0f));
        }

        shape.addVertex(new Vector3f(
                radius * (float)Math.cos(Math.toRadians(0)),
                radius * (float)Math.sin(Math.toRadians(0)),
                0f));
    }

    public static void getPie(Shape shape, float innerAngle, float radius) {
        shape.addVertex(new Vector3f(0f,0f,0f));

        for(float angle=0-innerAngle/2;angle<innerAngle/2;angle++) {
            shape.addVertex(new Vector3f(
                    radius * (float)Math.cos(Math.toRadians(angle)),
                    radius * (float)Math.sin(Math.toRadians(angle)),
                    0f));
        }
    }

    public static void getQuad(Shape shape, float xyRatio) {
        shape.addVertex(1.0f * xyRatio, 1.0f, 0.0f)
                .addVertex(-1.0f * xyRatio, 1.0f, 0.0f)
                .addVertex(-1.0f * xyRatio, -1.0f, 0.0f)
                .addVertex(1.0f * xyRatio, -1.0f, 0.0f);

        shape.addUV(1f,0f).addUV(0f,0f).addUV(0f,1f).addUV(1f,1f);
    }
}
