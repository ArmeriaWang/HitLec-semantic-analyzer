package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_Program implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;

    public S_Program() {
        this.identifierTable = IdentifierTable.getGlobalTable();
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_PROGRAM;
    }
}
