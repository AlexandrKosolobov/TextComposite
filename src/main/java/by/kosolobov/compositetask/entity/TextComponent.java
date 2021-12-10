package by.kosolobov.compositetask.entity;

import by.kosolobov.compositetask.type.ComponentType;

import java.util.ArrayList;
import java.util.List;

public class TextComponent extends Component {
    private final List<Component> children = new ArrayList<>();
    private ComponentType type;

    public TextComponent() {
    }

    public TextComponent(ComponentType type) {
        this.type = type;
    }

    @Override
    public boolean add(Component component) {
        return children.add(component);
    }

    @Override
    public boolean remove(Component component) {
        return children.remove(component);
    }

    @Override
    public ComponentType getType() {
        return type;
    }

    public List<Component> getChildren() {
        return children;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextComponent that = (TextComponent) o;
        return type.getLevel() == that.type.getLevel() && children.equals(that.children);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += type.getLevel();
        for (Component c : children) {
            result += c.getType().getLevel();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Component component : children) {
            builder.append(component);
        }

        return builder.toString();
    }
}
