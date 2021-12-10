package by.kosolobov.compositetask.entity;

import by.kosolobov.compositetask.type.ComponentType;
import by.kosolobov.compositetask.type.SymbolComponentType;

import java.util.ArrayList;
import java.util.List;

import static by.kosolobov.compositetask.type.impl.DefaultComponentType.SYMBOL;


public class SymbolComponent extends Component {
    private ComponentType type = SYMBOL;
    private SymbolComponentType symbolType;
    private char symbol;

    public SymbolComponent() {
    }

    public SymbolComponent(SymbolComponentType symbolType, char symbol) {
        this.symbolType = symbolType;
        this.symbol = symbol;
    }

    public SymbolComponentType getSymbolType() {
        return symbolType;
    }

    public void setType(SymbolComponentType symbolType) {
        this.symbolType = symbolType;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean add(Component component) {
        return false;
    }

    @Override
    public boolean remove(Component component) {
        return false;
    }

    public ComponentType getType() {
        return type;
    }

    @Override
    public List<Component> getChildren() {
        List<Component> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolComponent that = (SymbolComponent) o;
        return symbol == that.symbol && type.getLevel() == that.type.getLevel();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += symbol;
        result += type.getLevel();
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
