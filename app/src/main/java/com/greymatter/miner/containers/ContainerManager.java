package com.greymatter.miner.containers;

public class ContainerManager {
    private static ActiveGameObjectContainer activeGameObjectsContainer;
    private static ActiveUIContainer activeUIContainer;
    private static ActiveResourcesContainer activeResourcesContainer;
    private static AllGameObjectsContainer allGameObjectsContainer;
    private static AllResourcesContainer allResourcesContainer;

    public static ActiveGameObjectContainer getActiveGameObjectsContainer() {
        if (activeGameObjectsContainer == null) {
            activeGameObjectsContainer = new ActiveGameObjectContainer();
        }
        return activeGameObjectsContainer;
    }

    public static ActiveUIContainer getActiveUIContainer() {
        if (activeUIContainer == null) {
            activeUIContainer = new ActiveUIContainer();
        }
        return activeUIContainer;
    }

    public static ActiveResourcesContainer getActiveResourceContainer() {
        if (activeResourcesContainer == null) {
            activeResourcesContainer = new ActiveResourcesContainer();
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
