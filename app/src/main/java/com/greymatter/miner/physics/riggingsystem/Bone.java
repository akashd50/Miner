package com.greymatter.miner.physics.riggingsystem;

import android.opengl.Matrix;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

public class Bone {
    private Drawable drawable;
    private Bone parent;
    private ArrayList<Bone> children;
    public Bone(Drawable drawable, Bone parent) {
        this.drawable = drawable;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public Bone(Bone parent) {
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public void applyTransformations(float[] modelMat) {
        if(this.parent == null) {
            modelMat = new float[16];
            Matrix.setIdentityM(modelMat, 0);
            drawable.applyTransformations(modelMat);

            for(Bone child : children) {
                child.applyTransformations(modelMat);
            }
        }else{
            drawable.applyTransformations(modelMat);
            for(Bone child : children) {
                child.applyTransformations(modelMat);
            }
        }
    }

    public void addChild(Bone child) {
        this.children.add(child);
    }

    public Bone getChild(int i) {
        return this.children.get(i);
    }

    public ArrayList<Bone> getChildren() {
        return this.children;
    }

    public void setParent(Bone parent) {
        this.parent = parent;
    }

    public Bone getParent() {
        return parent;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
