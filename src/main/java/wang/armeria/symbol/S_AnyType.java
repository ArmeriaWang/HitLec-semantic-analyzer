package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

public class S_AnyType implements Symbol {

    private final Type type;  // S

    public S_AnyType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_ANY_TYPE;
    }

}
