package wang.armeria.symbol;

import wang.armeria.type.Type;

public class S_Number implements Symbol {

    private final Number value;
    private final Type type;

    public S_Number(Type type, Number value) {
        this.type = type;
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_NUMBER;
    }
}
