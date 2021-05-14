package wang.armeria.symbol;

import wang.armeria.type.Type;

public class S_HDArray implements Symbol {

    private final Type type;
    private final String id;

    public S_HDArray(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_HD_ARRAY;
    }
}
