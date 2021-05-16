package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.Manager;

public class S_ExpR implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;
    private final boolean isLogical;

    protected S_ExpR(IdentifierTable identifierTable, boolean isLogical) {
        this.identifierTable = identifierTable;
        this.isLogical = isLogical;
    }

    public boolean isArithmetic() {
        return !isLogical;
    }

    public boolean isLogical() {
        return isLogical;
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
