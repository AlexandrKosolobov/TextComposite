package by.kosolobov.compositetask.entity;

import by.kosolobov.compositetask.type.ComponentType;

import java.util.List;

public abstract class Component {
    public abstract boolean add(Component component);
    public abstract boolean remove(Component component);
    public abstract ComponentType getType();
    public abstract List<Component> getChildren();
}
