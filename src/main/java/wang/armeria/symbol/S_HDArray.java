package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public abstract class S_HDArray implements Symbol, HasIdTable {

    private final boolean isRef;
    private final IdentifierTable identifierTable;

    protected S_HDArray(IdentifierTable identifierTable, boolean isRef) {
        this.isRef = isRef;
        this.identifierTable = identifierTable;
    }

    public boolean isRef() {
        return isRef;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_HD_ARRAY;
    }

}
