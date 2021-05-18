package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_TopStatements implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;

    public S_TopStatements() {
        this.identifierTable = IdentifierTable.getGlobalTable();
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_TOP_STATEMENTS;
    }

}
