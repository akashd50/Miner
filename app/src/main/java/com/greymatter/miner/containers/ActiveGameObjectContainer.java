package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.opengl.objects.Camera;
import java.util.ArrayList;
import java.util.Comparator;

public class ActiveGameObjectContainer {
    private HashMapE<String, IGameObject> gameObjects;

    public ActiveGameObjectContainer() {
        gameObjects = new HashMapE<>();
    }

    private final Comparator<IGameObject> comparator = new Comparator<IGameObject>() {
        @Override
        public int compare(IGameObject o1, IGameObject o2) {
            return (int)(getZTranslationHelper(o1) - getZTranslationHelper(o2));
        }
    };

    private float getZTranslationHelper(IGameObject gameObject) {
        float translation = gameObject.getTransforms().getTranslation().z;
        IGameObject parent = gameObject.getParent();
        while (parent != null && gameObject.getTransforms().isCopyTranslationFromParent()) {
            translation += parent.getTransforms().getTranslation().z;
            parent = parent.getParent();
        }
        return translation;
    }

    public synchronized void add(IGameObject gameObject) {
        gameObjects.put(gameObject.getId(), gameObject);
        addHelper(gameObject, new StringBuilder());
        gameObjects.sort(comparator);
    }

    private synchronized void addHelper(IGameObject gameObject, final StringBuilder preID) {
        gameObject.getChildren().forEach((s, child) -> {
            if (child.getChildren().size() > 0) {
                gameObjects.put(preID.toString() + "_" + gameObject.getId() + "_" + child.getId(), child);
                preID.append(gameObject.getId());
                addHelper(child, preID);
            }else{
                gameObjects.put(preID.toString() + "_" + gameObject.getId() + "_" + child.getId(), child);
            }
        });
    }

    public synchronized IGameObject remove(String id) {
        IGameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
        return removed;
    }

    private synchronized void applyTransformations() {
        gameObjects.toList().forEach(gameObject -> {
            if (gameObject.getParent() == null) {
                gameObject.applyTransformations();
            }
        });
    }

    public synchronized void onDrawFrame(Camera camera) {
        applyTransformations();

        gameObjects.toList().forEach((gameObject) -> {
            gameObject.onDrawFrame(camera);
        });
    }

    public IGameObject get(String id) {
        return gameObjects.get(id);
    }

    public ArrayList<IGameObject> getAll() {
        return gameObjects.toList();
    }

    public ArrayList<IGameObject> getAllReversed() {
        return gameObjects.toReversedList();
    }

    public ArrayList<IGameObject> getAllWithTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public ArrayList<IGameObject> getAllWithOnlyTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
