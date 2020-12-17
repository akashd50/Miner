package com.greymatter.miner.containers;

public class ContainerManager {
    private static ActiveGameObjectContainer activeGameObjectsContainer;
    private static ActiveResourcesGameObjectContainer activeResourcesContainer;
    private static AllGameObjectsContainer allGameObjectsContainer;
    private static AllResourcesContainer allResourcesContainer;

    public static ActiveGameObjectContainer getActiveGameObjectsContainer() {
        if (activeGameObjectsContainer == null) {
            activeGameObjectsContainer = new ActiveGameObjectContainer();
        }
        return activeGameObjectsContainer;
    }

    public static ActiveResourcesGameObjectContainer getActiveResourceContainer() {
        if (activeResourcesContainer == null) {
            activeResourcesContainer = new ActiveResourcesGameObjectContainer();
        }
        return activeResourcesContainer;
    }

    public static AllGameObjectsContainer getAllGameObjectsContainer() {
        if (allGameObjectsContainer == null) {
            allGameObjectsContainer = new AllGameObjectsContainer();
        }
        return allGameObjectsContainer;
    }

    public static AllResourcesContainer getAllResourcesContainer() {
        if (allResourcesContainer == null) {
            allResourcesContainer = new AllResourcesContainer();
        }
        return allResourcesContainer;
    }
}
