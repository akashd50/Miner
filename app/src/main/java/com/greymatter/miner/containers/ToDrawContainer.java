package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.opengl.objects.Camera;
import java.util.ArrayList;
import java.util.Comparator;

public class ToDrawContainer {
    private static HashMapE<String, IGameObject> gameObjects = new HashMapE<>();
    private static Comparator<IGameObject> comparator = new Comparator<IGameObject>() {
        @Override
        public int compare(IGameObject o1, IGameObject o2) {
            return (int)(o1.getTransforms().getTranslation().z - o2.getTransforms().getTranslation().z);
        }
    };

    public static synchronized void add(IGameObject gameObject) {
        gameObjects.put(gameObject.getId(), gameObject);
        gameObjects.sort(comparator);
    }

    public static synchronized void remove(String id) {
        IGameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
    }

    public static synchronized void applyTransformations() {
        gameObjects.toList().forEach(object -> {object.getTransforms().applyTransformations();});
    }

    public static synchronized void onDrawFrame(Camera camera) {
        ToDrawContainer.applyTransformations();

        gameObjects.toList().forEach((gameObject) -> {
            onDrawFrame(gameObject, camera);
        });
    }

    private static synchronized void onDrawFrame(IGameObject gameObject, Camera camera) {
        gameObject.onFrameUpdate();
        //TODO: look into moving the frame update logic into a separate thread that is initialized
        // on app start from the main thread

        if(gameObject.shouldDraw()) {
            gameObject.getBackgroundChildren().forEach(child -> {
                onDrawFrame(child, camera);
            });

            gameObject.getDrawable().getRenderer().render(camera, gameObject);

            gameObject.getForegroundChildren().forEach(child -> {
                onDrawFrame(child, camera);
            });
        }
    }

    public static IGameObject get(String id) {
        return gameObjects.get(id);
    }

    public static ArrayList<IGameObject> getAll() {
        return gameObjects.toList();
    }

    public static ArrayList<IGameObject> getAllReversed() {
        return gameObjects.toReversedList();
    }

    public static ArrayList<IGameObject> getAllWithTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<IGameObject> getAllWithOnlyTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
