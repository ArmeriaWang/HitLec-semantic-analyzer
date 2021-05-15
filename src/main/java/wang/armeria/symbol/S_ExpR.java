package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.Manager;

public class S_ExpR implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;
    private final Type type;

    protected S_ExpR(IdentifierTable identifierTable, Type type) {
        this.identifierTable = identifierTable;
        this.type = type;
    }

    public S_ExpR(IdentifierTable identifierTable) {
        this.identifierTable = identifierTable;
        this.type = null;
    }

    public Type getType() {
        return type;
    }

    public boolean isArithmetic() {
        if (type == null) {
            return false;
        }
        return type.getTypeName() == Type.TypeName.INTEGER || type.getTypeName() == Type.TypeName.FLOAT;
    }

    public boolean isLogical() {
        if (type == null) {
            return false;
        }
        return type.getTypeName() == Type.TypeName.BOOLEAN;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_EXP_R;
    }

}
